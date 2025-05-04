package VistaVoluntia.Controlador;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import VistaVoluntia.Dtos.UsuarioDtos;
import VistaVoluntia.Servicios.UsuarioServicios;
import VistaVoluntia.Utilidades.Utilidades;

@MultipartConfig
@WebServlet("/usuario")
public class UsuarioControlador extends HttpServlet {

	private UsuarioServicios servicio;
	private Utilidades utilidades;
	private static final String REDIRECT_PAGE = "/RolAdministradorEmpresa/EmpresaAdminNuevoUsuario.jsp";
    private static final String PAGINA_TABLA = "/RolAdministradorEmpresa/EmpresaAdminTablaUsuarios.html";

	/**
	 * Inicializa el servlet y sus dependencias.
	 *
	 * @throws ServletException Si ocurre un error durante la inicialización.
	 *
	 * @author DMN - 04/03/2025
	 */
	@Override
	public void init() throws ServletException {
		try {
			this.servicio = new UsuarioServicios();
			this.utilidades = new Utilidades();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Maneja las peticiones POST para operaciones sobre usuarios: añadir,
	 * modificar, eliminar y cerrar sesión.
	 *
	 * @param request  La solicitud HTTP.
	 * @param response La respuesta HTTP.
	 * @throws ServletException Si ocurre un error de servlet.
	 * @throws IOException      Si ocurre un error de E/S.
	 *
	 * @author DMN - 18/04/2025
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		Long idUsuarioLong = (Long) session.getAttribute("idUsuario");
		String nombreServicioLog = "UsuarioServicio - ";
		response.setContentType("application/json");
		try {
			String accion = request.getParameter("accion");
			if (accion == null || accion.isEmpty()) {
				String json = "{\"status\":\"error\", \"mensaje\":\"Acción no válida o parámetros faltantes.\"}";
				response.getWriter().write(json);
				utilidades.ficheroLog(3, nombreServicioLog + "doPost() - Acción no válida o parámetros faltantes",
						idUsuarioLong);
				return;
			}
			if ("cerrar sesion".equals(accion)) {
				session.invalidate();
				response.sendRedirect("http://localhost:8080/VistaVoluntia/");
				return;
			}
			switch (accion) {
			case "aniadir":
				procesarAniadir(request, response, idUsuarioLong);
				break;
			case "modificar":
				procesarModificar(request, response, idUsuarioLong);
				break;
			case "eliminar":
				procesarEliminar(request, response, idUsuarioLong);
				break;
			default:
				String json = "{\"status\":\"error\", \"mensaje\":\"Acción no válida o parámetros faltantes.\"}";
				response.getWriter().write(json);
				break;
			}
		} catch (IllegalArgumentException e) {
			String json = "{\"status\":\"error\", \"mensaje\":\"Error en los parámetros: " + e.getMessage() + "\"}";
			response.getWriter().write(json);
			utilidades.ficheroLog(3, nombreServicioLog + "doPost() - " + e.getMessage(), idUsuarioLong);
		} catch (Exception e) {
			String json = "{\"status\":\"error\", \"mensaje\":\"Se produjo un error interno.\"}";
			response.getWriter().write(json);
			e.printStackTrace();
		}
	}

	/**
	 * Procesa la acción "aniadir" de usuario. Extrae parámetros, valida correo,
	 * procesa la imagen, genera contraseña, guarda el usuario y envía el correo de
	 * bienvenida.
	 *
	 * @param request       La solicitud HTTP.
	 * @param response      La respuesta HTTP.
	 * @param idUsuarioLong ID del usuario para log.
	 * @throws ServletException Si ocurre un error de servlet.
	 * @throws IOException      Si ocurre un error de E/S.
	 *
	 * @author DMN - 12/04/2025
	 */
	private void procesarAniadir(HttpServletRequest request, HttpServletResponse response, Long idUsuarioLong)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		String idOrgParam = request.getParameter("idOrganizacion");
		if (idOrgParam != null && !idOrgParam.isEmpty()) {
			session.setAttribute("idOrganizacion", Long.parseLong(idOrgParam));
		}
		try {
			UsuarioDtos nuevoUsuario = extraerDatosUsuario(request);
			if (servicio.validarCorreo(nuevoUsuario.getCorreoUsuario())) {
				String json = "{\"status\":\"error\", \"mensaje\":\"El correo ya existe. No se puede añadir el usuario.\"}";
				response.getWriter().write(json);
				utilidades.ficheroLog(3, "Intento de añadir usuario fallido: el correo ya existe", idUsuarioLong);
				return;
			}
			byte[] imagenBytes = procesarImagen(request);
			imagenBytes = getImagenBytes(request, imagenBytes);
			String passwordUsuario = utilidades.generarContraseña();
			nuevoUsuario.setPasswordUsuario(passwordUsuario);
			nuevoUsuario.setImagenUsuarioPerfil(imagenBytes);
			servicio.guardarUsuario(nuevoUsuario, request);
			enviarCorreoBienvenida(nuevoUsuario.getCorreoUsuario(), nuevoUsuario.getNombreUsuario(), passwordUsuario);
			String json = "{\"status\":\"success\", \"mensaje\":\"Usuario añadido correctamente y correo enviado.\"}";
			response.getWriter().write(json);
			utilidades.ficheroLog(2, "Usuario añadido correctamente y correo enviado", idUsuarioLong);
		} catch (Exception e) {
			String json = "{\"status\":\"error\", \"mensaje\":\"Se produjo un error interno.\"}";
			response.getWriter().write(json);
			utilidades.ficheroLog(3, "Error al añadir usuario: " + e.getMessage(), idUsuarioLong);
			throw new ServletException(e);
		}
	}

	/**
	 * Procesa la acción "modificar" de usuario. Extrae parámetros, actualiza la
	 * contraseña si se proporciona, procesa la imagen, y modifica el usuario.
	 *
	 * @param request       La solicitud HTTP.
	 * @param response      La respuesta HTTP.
	 * @param idUsuarioLong ID del usuario para log.
	 * @throws ServletException Si ocurre un error de servlet.
	 * @throws IOException      Si ocurre un error de E/S.
	 *
	 * @author DMN - 08/04/2025
	 */

	private void procesarModificar(HttpServletRequest request, HttpServletResponse response, Long idUsuarioLong)
	        throws ServletException, IOException {
	    try {
	        String idUsuario = request.getParameter("idUsuario");
	        if (idUsuario == null || idUsuario.isEmpty()) {
	            establecerAtributos(request, "error", "ID de usuario no proporcionado para modificar.");
	            redirigirPagina(request, response);
	            return;
	        }
	        String newPassword = request.getParameter("newPassword");
	        if (newPassword != null && !newPassword.isEmpty()) {
	            boolean passUpdated = servicio.actualizarContraseniaPorId(idUsuario, newPassword);
	            if (!passUpdated) {
	                establecerAtributos(request, "error", "No se pudo actualizar la contraseña.");
	                redirigirPagina(request, response);
	                return;
	            }
	        }
	        UsuarioDtos usuario = extraerDatosUsuario(request);
	        byte[] imagenBytes = procesarImagen(request);
	        imagenBytes = getImagenBytes(request, imagenBytes);
	        usuario.setImagenUsuarioPerfil(imagenBytes);
	        boolean actualizado = servicio.modificarUsuario(idUsuario, usuario);
	        if (actualizado) {
	            establecerAtributos(request, "success", "Usuario actualizado correctamente.");
	            utilidades.ficheroLog(2, "Usuario actualizado correctamente", idUsuarioLong);
	            response.sendRedirect(request.getContextPath() + PAGINA_TABLA);
	        } else {
	            establecerAtributos(request, "error", "No se pudo actualizar el usuario.");
	            utilidades.ficheroLog(3, "No se pudo actualizar el usuario", idUsuarioLong);
	            redirigirPagina(request, response);
	        }
	    } catch (Exception e) {
	        throw new ServletException(e);
	    }
	}


	/**
	 * Procesa la acción "eliminar" de usuario. Valida el ID y elimina el usuario.
	 *
	 * @param request       La solicitud HTTP.
	 * @param response      La respuesta HTTP.
	 * @param idUsuarioLong ID del usuario para log.
	 * @throws ServletException Si ocurre un error de servlet.
	 * @throws IOException      Si ocurre un error de E/S.
	 *
	 * @author DMN - 14/04/2025
	 */
	private void procesarEliminar(HttpServletRequest request, HttpServletResponse response, Long idUsuarioLong)
			throws ServletException, IOException {
		try {
			String idUsuario = request.getParameter("idUsuario");
			if (idUsuario == null || idUsuario.isEmpty()) {
				establecerAtributos(request, "error", "ID de usuario no proporcionado para eliminar.");
				redirigirPagina(request, response);
				return;
			}
			boolean eliminado = servicio.eliminarUsuario(idUsuario);
			if (eliminado) {
				establecerAtributos(request, "success", "Usuario eliminado correctamente.");
				utilidades.ficheroLog(2, "Usuario eliminado correctamente", idUsuarioLong);
			} else {
				establecerAtributos(request, "error", "No se pudo eliminar el usuario.");
				utilidades.ficheroLog(3, "No se pudo eliminar el usuario", idUsuarioLong);
			}
			redirigirPagina(request, response);
		} catch (Exception e) {
			throw new ServletException(e);
		}
	}

	/**
	 * Extrae los parámetros comunes de usuario de la solicitud y los retorna en un
	 * objeto UsuarioDtos.
	 *
	 * @param request La solicitud HTTP.
	 * @return Objeto UsuarioDtos con los datos extraídos.
	 * @throws Exception En caso de error en la conversión de la fecha.
	 *
	 * @author DMN - 18/03/2025
	 */
	private UsuarioDtos extraerDatosUsuario(HttpServletRequest request) throws Exception {
		UsuarioDtos usuario = new UsuarioDtos();
		usuario.setNombreUsuario(getValueOrDefault(request.getParameter("nombreUsuario"), "Nombre Defecto"));
		usuario.setApellidosUsuario(getValueOrDefault(request.getParameter("apellidosUsuario"), "Apellidos Defecto"));
		usuario.setTelefonoUsuario(getValueOrDefault(request.getParameter("telefonoUsuario"), "+34 611 11 11 11"));
		usuario.setDniUsuario(getValueOrDefault(request.getParameter("dniUsuario"), "00000000A"));
		usuario.setGeneroUsuario(getValueOrDefault(request.getParameter("generoUsuario"), "Indefinido"));
		String fechaNacimientoUsuario = getValueOrDefault(request.getParameter("fechaNacimientoUsuario"), "2000-01-01");
		LocalDate fechaNacimiento = LocalDate.parse(fechaNacimientoUsuario, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		usuario.setFechaNacimientoUsuario(fechaNacimiento);
		usuario.setProvinciaUsuario(getValueOrDefault(request.getParameter("provinciaUsuario"), "Sin Especificar"));
		usuario.setLocalidadUsuario(getValueOrDefault(request.getParameter("localidadUsuario"), "Sin Especificar"));
		usuario.setDireccionUsuario(getValueOrDefault(request.getParameter("direccionUsuario"), "Sin Especificar"));
		usuario.setCodigoPostalUsuario(getValueOrDefault(request.getParameter("codigoPostalUsuario"), "00000"));
		usuario.setFechaIngresoUsuario(getValueOrDefault(request.getParameter("fechaIngresoUsuario"), "2025-01-01"));
		usuario.setNumeroIdentificativoUsuario(
				getValueOrDefault(request.getParameter("numeroIdentificativoUsuario"), "00001"));
		usuario.setIndicativoUsuario(getValueOrDefault(request.getParameter("indicativoUsuario"), "ES00"));
		usuario.setRolUsuario(getValueOrDefault(request.getParameter("rolUsuario"), "Voluntario"));
		usuario.setCorreoUsuario(getValueOrDefault(request.getParameter("correoUsuario"), "correo@gmail.com"));
		return usuario;
	}

	/**
	 * Envía un correo de bienvenida al usuario con su contraseña generada.
	 *
	 * @param correo   Correo del usuario.
	 * @param nombre   Nombre del usuario.
	 * @param password Contraseña generada.
	 *
	 * @author DMN - 15/04/2025
	 */
	private void enviarCorreoBienvenida(String correo, String nombre, String password) {
		try {
			String asunto = "Bienvenido a Voluntia";
			String cuerpoMensaje = "<html>" + "  <head>" + "    <meta charset='UTF-8'>" + "    <style type='text/css'>"
					+ "      body { font-family: Arial, sans-serif; margin: 0; padding: 0; background-color: #f4f4f4; }"
					+ "      .container { max-width: 600px; margin: 30px auto; background: #ffffff; border-radius: 8px; overflow: hidden; box-shadow: 0 2px 5px rgba(0,0,0,0.15); }"
					+ "      .header { background-color: #007BFF; color: #ffffff; padding: 20px; text-align: center; }"
					+ "      .content { padding: 30px; color: #333333; }"
					+ "      .password-box { background-color: #e0e0e0; padding: 15px; text-align: center; font-size: 18px; border-radius: 4px; margin: 20px 0; }"
					+ "      .footer { background-color: #f4f4f4; padding: 10px; text-align: center; font-size: 12px; color: #777777; }"
					+ "    </style>" + "  </head>" + "  <body>" + "    <div class='container'>"
					+ "      <div class='header'>" + "        <h1>Bienvenido a VistaVoluntia</h1>" + "      </div>"
					+ "      <div class='content'>" + "        <p>Hola " + nombre + ",</p>"
					+ "        <p>Tu cuenta ha sido creada exitosamente en Voluntia.</p>"
					+ "        <p>Tu contraseña generada es:</p>" + "        <div class='password-box'><strong>"
					+ password + "</strong></div>"
					+ "        <p>Por favor, cambia esta contraseña después de iniciar sesión.</p>"
					+ "        <p>¡Bienvenido!</p>" + "      </div>" + "      <div class='footer'>"
					+ "        <p>© 2025 VistaVoluntia. Todos los derechos reservados.</p>" + "      </div>"
					+ "    </div>" + "  </body>" + "</html>";
			servicio.enviarCorreo(correo, asunto, cuerpoMensaje);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Procesa la imagen enviada en la solicitud.
	 *
	 * @param request La solicitud HTTP.
	 * @return Un arreglo de bytes con la imagen, o nulo si no se subió ninguna
	 *         imagen.
	 *
	 * @author DMN - 05/03/2025
	 */
	private byte[] procesarImagen(HttpServletRequest request) {
		byte[] imagenBytes = null;
		try {
			Part imagenPart = request.getPart("imagenPerfilUsuario");
			if (imagenPart != null && imagenPart.getSize() > 0) {
				try (InputStream inputStream = imagenPart.getInputStream()) {
					imagenBytes = inputStream.readAllBytes();
				}
				System.out.println("Imagen recibida (nuevo archivo): " + imagenPart.getSize() + " bytes.");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return imagenBytes;
	}

	/**
	 * Retorna el valor proporcionado o un valor por defecto si el primero es nulo o
	 * está vacío.
	 *
	 * @param value        El valor a evaluar.
	 * @param defaultValue El valor por defecto a retornar.
	 * @return El valor original o el valor por defecto.
	 *
	 * @author DMN - 18/03/2025
	 */
	private String getValueOrDefault(String value, String defaultValue) {
		return (value == null || value.trim().isEmpty()) ? defaultValue : value;
	}

	/**
	 * Obtiene los bytes de la imagen. Si no se ha subido una nueva imagen, se
	 * intenta utilizar la imagen existente (campo oculto "imagenActual") o se carga
	 * una imagen por defecto (Anonimo.jpg).
	 *
	 * @param request    La solicitud HTTP.
	 * @param imageBytes Los bytes de la imagen subidos (puede ser nulo o vacío).
	 * @return Un arreglo de bytes con la imagen.
	 *
	 * @author DMN - 26/03/2025
	 */
	private byte[] getImagenBytes(HttpServletRequest request, byte[] imageBytes) {
		try {
			if (imageBytes == null || imageBytes.length == 0) {
				String imagenActualBase64 = request.getParameter("imagenActual");
				System.out.println("Valor recibido en imagenActual: " + imagenActualBase64);
				if (imagenActualBase64 != null && !imagenActualBase64.isEmpty()) {
					return Base64.getDecoder().decode(imagenActualBase64);
				} else {
					String rutaImagen = getServletContext().getRealPath("/Imagenes/Anonimo.jpg");
					return Files.readAllBytes(Paths.get(rutaImagen));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return imageBytes;
	}

	/**
	 * Establece los atributos "status" y "mensaje" en la solicitud.
	 *
	 * @param request La solicitud HTTP.
	 * @param status  El estado ("success" o "error").
	 * @param mensaje El mensaje a mostrar.
	 *
	 * @author DMN - 10/04/2025
	 */
	private void establecerAtributos(HttpServletRequest request, String status, String mensaje) {
		request.setAttribute("status", status);
		request.setAttribute("mensaje", mensaje);
	}

	/**
	 * Redirige la solicitud a la página de redirección definida.
	 *
	 * @param request  La solicitud HTTP.
	 * @param response La respuesta HTTP.
	 * @throws ServletException Si ocurre un error de servlet al reenviar.
	 * @throws IOException      Si ocurre un error de E/S al reenviar.
	 *
	 * @author DMN - 22/04/2025
	 */
	private void redirigirPagina(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher(REDIRECT_PAGE).forward(request, response);
	}

	/**
	 * Maneja las peticiones GET para obtener información de usuarios.
	 *
	 * @param request  La solicitud HTTP.
	 * @param response La respuesta HTTP.
	 * @throws ServletException Si ocurre un error de servlet.
	 * @throws IOException      Si ocurre un error de E/S.
	 *
	 * @author DMN - 24/04/2025
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			String accion = request.getParameter("accion");
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");

			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.registerModule(new com.fasterxml.jackson.datatype.jsr310.JavaTimeModule());

			if ("listaUsuarios".equals(accion)) {
				ArrayList<UsuarioDtos> listaUsuario = servicio.listaUsuario(request);
				String json = objectMapper.writeValueAsString(listaUsuario);
				response.getWriter().write(json);
			} else if ("numeroUsuarios".equals(accion)) {
				Long numeroUsuarios = servicio.obtenerTotalUsuarios(request);
				String json = objectMapper.writeValueAsString(numeroUsuarios);
				response.getWriter().write(json);
			} else {
				String errorJson = objectMapper.writeValueAsString("{\"error\": \"Acción no válida\"}");
				response.getWriter().write(errorJson);
			}
		} catch (Exception e) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			response.getWriter().write("{\"error\": \"Ocurrió un error en el servidor\"}");
			e.printStackTrace();
		}
	}

}
