package VistaVoluntia.Controlador;

import java.io.IOException;
import java.net.URL;
import java.util.Base64;

import org.json.JSONObject;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import VistaVoluntia.Dtos.UsuarioDtos;
import VistaVoluntia.Servicios.InicioServicio;
import VistaVoluntia.Servicios.UsuarioServicios;
import VistaVoluntia.Utilidades.Utilidades;

/**
 * Controlador de Inicio de Sesión.
 * 
 * @author DMN - 22/02/2025
 */
@WebServlet("/login")
public class InicioSesion extends HttpServlet {

	private InicioServicio servicio;
	private UsuarioServicios usuarioServicios;
	private Utilidades utilidadesServicio;

	@Override
	public void init() throws ServletException {
		servicio = new InicioServicio();
		usuarioServicios = new UsuarioServicios();
		utilidadesServicio = new Utilidades();
	}

	/**
	 * Procesa las solicitudes POST según la acción solicitada.
	 * 
	 * @author DMN - 22/02/2025
	 */
	@Override
	protected void doPost(HttpServletRequest solicitud, HttpServletResponse respuesta)
			throws ServletException, IOException {
		try {
			String accion = solicitud.getParameter("accion");
			switch (accion) {
			case "validar":
				procesarValidar(solicitud, respuesta);
				break;
			case "cerrar sesion":
				procesarCerrarSesion(solicitud, respuesta);
				break;
			case "modificarContrasenia":
				procesarModificarContrasenia(solicitud, respuesta);
				break;
			case "hasOlvidadoContrasenia":
				procesarHasOlvidadoContrasenia(solicitud, respuesta);
				break;
			case "recuperarPassword":
				procesarRecuperarPassword(solicitud, respuesta);
				break;
			case "sesion":
				procesarSesion(solicitud, respuesta);
				break;
			default:
				procesarAccionNoReconocida(solicitud, respuesta, accion);
				break;
			}
		} catch (Exception e) {
			respuesta.setContentType("application/json");
			respuesta.getWriter().write("{\"error\": \"Error interno en doPost: " + e.getMessage() + "\"}");
			utilidadesServicio.ficheroLog(3, "InicioSesion: Error interno en doPost - " + e.getMessage(), 0);
			e.printStackTrace();
		}
	}

	/**
	 * Redirige las solicitudes GET a doPost.
	 * 
	 * @author DMN - 22/02/2025
	 */
	@Override
	protected void doGet(HttpServletRequest solicitud, HttpServletResponse respuesta)
			throws ServletException, IOException {
		doPost(solicitud, respuesta);
	}

	/**
	 * Procesa la acción de validación de usuario.
	 * 
	 * @param solicitud Solicitud HTTP.
	 * @param respuesta Respuesta HTTP.
	 * 
	 * @author DMN - 22/02/2025
	 */
	private void procesarValidar(HttpServletRequest solicitud, HttpServletResponse respuesta)
	        throws ServletException, IOException {
	    String correo = solicitud.getParameter("correoUsuario");
	    String password = solicitud.getParameter("passwordUsuario");

	    System.out.println("Correo recibido: " + correo);
	    System.out.println("Password recibida: " + password);

	    String redireccion = servicio.verificarUsuario(solicitud, respuesta, correo, password);
	    HttpSession sesion = solicitud.getSession(false);
	    Long idUsuario = sesion != null ? (Long) sesion.getAttribute("idUsuario") : null;

	    if (idUsuario != null) {
	        System.out.println("ID del Usuario en sesión: " + idUsuario);
	    } else {
	        System.out.println("No hay ID de usuario en la sesión.");
	    }

	    if (redireccion != null) {
	        respuesta.sendRedirect(redireccion);
	        utilidadesServicio.ficheroLog(
	            1,
	            "InicioSesion: Usuario con correo " + correo + " validado correctamente.",
	            idUsuario != null ? idUsuario : 0
	        );
	    } else {
	        utilidadesServicio.ficheroLog(
	            3,
	            "InicioSesion: Fallo en validación para usuario con correo " + correo,
	            idUsuario != null ? idUsuario : 0
	        );
	        respuesta.sendRedirect("login.html?error=1");
	    }
	}


	/**
	 * Procesa la acción de cerrar sesión.
	 * 
	 * @param solicitud Solicitud HTTP.
	 * @param respuesta Respuesta HTTP.
	 * 
	 * @author DMN - 22/02/2025
	 */
	private void procesarCerrarSesion(HttpServletRequest solicitud, HttpServletResponse respuesta)
			throws ServletException, IOException {
		servicio.cerrarSesion(solicitud, respuesta);
		utilidadesServicio.ficheroLog(1, "InicioSesion: Sesión cerrada.", 0);
	}

	/**
	 * Procesa la acción de modificar contraseña (funcionalidad no implementada).
	 * 
	 * @param solicitud Solicitud HTTP.
	 * @param respuesta Respuesta HTTP.
	 * 
	 * @author DMN - 22/02/2025
	 */
	private void procesarModificarContrasenia(HttpServletRequest solicitud, HttpServletResponse respuesta)
			throws ServletException, IOException {
		utilidadesServicio.ficheroLog(2, "InicioSesion: Funcionalidad modificarContrasenia no implementada.", 0);
	}

	/**
	 * Procesa la acción de gestionar olvido de contraseña (funcionalidad no
	 * implementada).
	 * 
	 * @param solicitud Solicitud HTTP.
	 * @param respuesta Respuesta HTTP.
	 * 
	 * @author DMN - 22/02/2025
	 */
	private void procesarHasOlvidadoContrasenia(HttpServletRequest solicitud, HttpServletResponse respuesta)
			throws ServletException, IOException {
		utilidadesServicio.ficheroLog(2, "InicioSesion: Funcionalidad hasOlvidadoContrasenia no implementada.", 0);
	}

	/**
	 * Procesa la acción de recuperación de contraseña. Se maneja tanto el envío del
	 * correo de recuperación como la actualización de la contraseña vía token.
	 * 
	 * @param solicitud Solicitud HTTP.
	 * @param respuesta Respuesta HTTP.
	 * 
	 * @author DMN - 22/02/2025
	 */
	private void procesarRecuperarPassword(HttpServletRequest solicitud, HttpServletResponse respuesta)
			throws ServletException, IOException {
		String newPassword = solicitud.getParameter("newPassword");
		if (newPassword != null && !newPassword.isEmpty()) {
			String token = solicitud.getParameter("token");
			String confirmPassword = solicitud.getParameter("confirmPassword");
			System.out.println("Token recibido: " + token);

			if (token != null && token.startsWith("http://")) {
				try {
					URL urlObj = new URL(token);
					String query = urlObj.getQuery();
					if (query != null && query.startsWith("token=")) {
						token = query.substring("token=".length());
					}
				} catch (Exception e) {
					e.printStackTrace();
					utilidadesServicio.ficheroLog(3, "InicioSesion: Error extrayendo token de URL - " + e.getMessage(),
							0);
				}
			}

			if (token == null || confirmPassword == null || !newPassword.equals(confirmPassword)) {
				solicitud.setAttribute("errorMessage",
						"Error: Los datos ingresados son inválidos o las contraseñas no coinciden.");
				utilidadesServicio.ficheroLog(3,
						"InicioSesion: Error en recuperación de contraseña, datos inválidos o contraseñas no coinciden.",
						0);
				solicitud.getRequestDispatcher("NuevaContrasenia.jsp").forward(solicitud, respuesta);
				return;
			}

			boolean actualizado = usuarioServicios.actualizarContraseniaPorToken(token, newPassword);
			if (actualizado) {
				solicitud.setAttribute("message", "Contraseña actualizada exitosamente. Ahora puedes iniciar sesión.");
				utilidadesServicio.ficheroLog(1,
						"InicioSesion: Contraseña actualizada exitosamente para token: " + token, 0);
				solicitud.getRequestDispatcher("login.jsp").forward(solicitud, respuesta);
			} else {
				solicitud.setAttribute("errorMessage",
						"Token inválido o expirado. No se pudo actualizar la contraseña.");
				utilidadesServicio.ficheroLog(3,
						"InicioSesion: Fallo al actualizar contraseña, token inválido o expirado: " + token, 0);
				solicitud.getRequestDispatcher("NuevaContrasenia.jsp").forward(solicitud, respuesta);
			}
		} else {
			String correoUsuario = solicitud.getParameter("correoUsuario");
			if (correoUsuario != null && !correoUsuario.isEmpty()) {
				String tokenGenerado = utilidadesServicio.generarToken(correoUsuario);
				String urlRecuperacion = "http://localhost:8080/VistaVoluntia/NuevaContrasenia.jsp?token="
						+ tokenGenerado;
				String cuerpoMensaje = "<html>" + "  <head>" + "    <meta charset='UTF-8'>"
						+ "    <style type='text/css'>"
						+ "      body { font-family: 'Helvetica Neue', Helvetica, Arial, sans-serif; margin: 0; padding: 0; background-color: #f9f9f9; }"
						+ "      .container { max-width: 600px; margin: 40px auto; background: #ffffff; border: 1px solid #e0e0e0; border-radius: 8px; overflow: hidden; }"
						+ "      .header { background-color: #2c3e50; color: #ffffff; padding: 20px; text-align: center; }"
						+ "      .content { padding: 30px; color: #2c3e50; font-size: 16px; line-height: 1.5; }"
						+ "      .button { display: inline-block; padding: 12px 20px; font-size: 16px; color: #ffffff; background-color: #e74c3c; text-decoration: none; border-radius: 4px; margin-top: 20px; }"
						+ "      .footer { background-color: #ecf0f1; padding: 10px; text-align: center; font-size: 12px; color: #95a5a6; }"
						+ "    </style>" + "  </head>" + "  <body>" + "    <div class='container'>"
						+ "      <div class='header'>" + "        <h1>Recuperación de Contraseña</h1>" + "      </div>"
						+ "      <div class='content'>" + "        <p>Estimado usuario,</p>"
						+ "        <p>Hemos recibido una solicitud para restablecer la contraseña de tu cuenta en Voluntia.</p>"
						+ "        <p>Para proceder, haz clic en el siguiente botón y sigue las instrucciones:</p>"
						+ "        <p style='text-align: center;'><a class='button' href='" + urlRecuperacion
						+ "'>Restablecer Contraseña</a></p>"
						+ "        <p>Si no solicitaste este cambio, ignora este mensaje.</p>"
						+ "        <p>Saludos cordiales,<br/>El equipo de Voluntia</p>" + "      </div>"
						+ "      <div class='footer'>"
						+ "        <p>© 2025 Voluntia. Todos los derechos reservados.</p>" + "      </div>"
						+ "    </div>" + "  </body>" + "</html>";

				utilidadesServicio.enviarToken(correoUsuario, cuerpoMensaje);
				solicitud.setAttribute("message",
						"Se ha enviado un correo con el enlace para recuperar la contraseña.");
				utilidadesServicio.ficheroLog(1, "InicioSesion: Correo de recuperación enviado a " + correoUsuario, 0);
				solicitud.getRequestDispatcher("recuperacionExito.jsp").forward(solicitud, respuesta);
			} else {
				solicitud.setAttribute("errorMessage", "El correo es obligatorio.");
				utilidadesServicio.ficheroLog(3,
						"InicioSesion: Fallo en recuperación de contraseña, correo no proporcionado.", 0);
				solicitud.getRequestDispatcher("recuperacionError.jsp").forward(solicitud, respuesta);
			}
		}
	}

	/**
	 * Procesa la acción de obtener los datos de sesión del usuario. Devuelve la
	 * información en formato JSON.
	 * 
	 * @param solicitud Solicitud HTTP.
	 * @param respuesta Respuesta HTTP.
	 * 
	 * @author DMN - 22/02/2025
	 */
	private void procesarSesion(HttpServletRequest solicitud, HttpServletResponse respuesta)
			throws ServletException, IOException {
		HttpSession sess = solicitud.getSession(false);
		if (sess != null) {
			UsuarioDtos usuarioSesion = servicio.obtenerDatosUsuario(sess);
			JSONObject jsonResponse = new JSONObject();
			jsonResponse.put("idUsuario", usuarioSesion.getIdUsuario());
			jsonResponse.put("nombreUsuario", usuarioSesion.getNombreUsuario());
			jsonResponse.put("apellidosUsuario", usuarioSesion.getApellidosUsuario());
			jsonResponse.put("telefonoUsuario", usuarioSesion.getTelefonoUsuario());
			jsonResponse.put("dniUsuario", usuarioSesion.getDniUsuario());
			jsonResponse.put("generoUsuario", usuarioSesion.getGeneroUsuario());
			jsonResponse.put("fechaNacimientoUsuario", usuarioSesion.getFechaNacimientoUsuario().toString());
			jsonResponse.put("provinciaUsuario", usuarioSesion.getProvinciaUsuario());
			jsonResponse.put("localidadUsuario", usuarioSesion.getLocalidadUsuario());
			jsonResponse.put("direccionUsuario", usuarioSesion.getDireccionUsuario());
			jsonResponse.put("codigoPostalUsuario", usuarioSesion.getCodigoPostalUsuario());
			jsonResponse.put("fechaIngresoUsuario", usuarioSesion.getFechaIngresoUsuario());
			jsonResponse.put("numeroIdentificativoUsuario", usuarioSesion.getNumeroIdentificativoUsuario());
			jsonResponse.put("indicativoUsuario", usuarioSesion.getIndicativoUsuario());
			jsonResponse.put("rolUsuario", usuarioSesion.getRolUsuario());
			jsonResponse.put("correoUsuario", usuarioSesion.getCorreoUsuario());
			jsonResponse.put("passwordUsuario", usuarioSesion.getPasswordUsuario());

			if (usuarioSesion.getImagenPerfilUsuario() != null) {
				String imagenBase64 = Base64.getEncoder().encodeToString(usuarioSesion.getImagenPerfilUsuario());
				jsonResponse.put("imagenUsuarioPerfil", imagenBase64);
			} else {
				jsonResponse.put("imagenUsuarioPerfil", "");
			}

			respuesta.setContentType("application/json");
			respuesta.getWriter().write(jsonResponse.toString());
		} else {
			respuesta.setContentType("application/json");
			respuesta.getWriter().write("{\"error\": \"No hay sesión activa.\"}");
			utilidadesServicio.ficheroLog(3, "InicioSesion: No hay sesión activa.", 0);
		}
	}

	/**
	 * Procesa una acción no reconocida, enviando un error en formato JSON.
	 * 
	 * @param solicitud Solicitud HTTP.
	 * @param respuesta Respuesta HTTP.
	 * @param accion    Acción recibida.
	 * 
	 * @author DMN - 22/02/2025
	 */
	private void procesarAccionNoReconocida(HttpServletRequest solicitud, HttpServletResponse respuesta, String accion)
			throws ServletException, IOException {
		respuesta.setContentType("application/json");
		respuesta.getWriter().write("{\"error\": \"Acción no reconocida: " + accion + "\"}");
		utilidadesServicio.ficheroLog(3, "InicioSesion: Acción no reconocida: " + accion, 0);
	}
}
