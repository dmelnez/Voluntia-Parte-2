package VistaVoluntia.Controlador;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import VistaVoluntia.Dtos.EmergenciaDtos;
import VistaVoluntia.Servicios.EmergenciaServicios;
import VistaVoluntia.Utilidades.Utilidades;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet encargado de gestionar operaciones de emergencias: creación,
 * modificación, eliminación y consulta.
 * 
 * @author DMN - 22/02/2025
 */
@WebServlet("/emergencia")
public class EmergenciaControlador extends HttpServlet {

	private EmergenciaServicios emergencia;
	private Utilidades utilidades;
	private static final String ADMIN_EMERGENCIA_PAGE = "/RolAdministradorEmpresa/EmpresaAdminNuevaEmergencia.jsp";
    private static final String PAGINA_TABLA = "/RolAdministradorEmpresa/EmpresaAdminTablaEmergencias.html";

	@Override
	public void init() throws ServletException {
		this.emergencia = new EmergenciaServicios();
		this.utilidades = new Utilidades();
		utilidades.ficheroLog(1, "EmergenciaControlador - init() - Inicialización completada", 0);
	}

	/**
	 * Procesa las solicitudes POST para crear, modificar o eliminar una emergencia.
	 * 
	 * @param request  La solicitud HTTP.
	 * @param response La respuesta HTTP.
	 * @throws ServletException En caso de error en el procesamiento del servlet.
	 * @throws IOException      En caso de error de entrada/salida.
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String accion = request.getParameter("accion");

		if (accion == null || accion.trim().isEmpty()) {
			utilidades.ficheroLog(3, "EmergenciaControlador - doPost() - Acción no válida o parámetros faltantes", 0);
			forwardResponse(request, response, "error", "Acción no válida o parámetros faltantes.");
			return;
		}

		switch (accion) {
		case "aniadir":
			procesarAniadirEmergencia(request, response);
			break;
		case "modificar":
			procesarModificarEmergencia(request, response);
			break;
		case "eliminar":
			procesarEliminarEmergencia(request, response);
			break;
		default:
			utilidades.ficheroLog(3, "EmergenciaControlador - doPost() - Acción no válida o parámetros faltantes", 0);
			forwardResponse(request, response, "error", "Acción no válida o parámetros faltantes.");
			break;
		}
	}

	/**
	 * Procesa las solicitudes GET para obtener información de emergencias en
	 * formato JSON. Soporta:
	 * <ul>
	 * <li>"listaEmergencias": Retorna la lista de emergencias.</li>
	 * <li>"numeroEmergencias": Retorna el número total de emergencias.</li>
	 * </ul>
	 * 
	 * @param request  La solicitud HTTP.
	 * @param response La respuesta HTTP.
	 * @throws ServletException En caso de error en el procesamiento del servlet.
	 * @throws IOException      En caso de error de entrada/salida.
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			final String accion = request.getParameter("accion");

			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.registerModule(new com.fasterxml.jackson.datatype.jsr310.JavaTimeModule());

			if ("listaEmergencias".equals(accion)) {
				ArrayList<EmergenciaDtos> listaEmergencias = emergencia.listaEmergencias(request);
				String json = objectMapper.writeValueAsString(listaEmergencias);
				response.getWriter().write(json);
				utilidades.ficheroLog(1, "EmergenciaControlador - doGet() - Lista de emergencias enviada correctamente",
						0);
			} else if ("numeroEmergencias".equals(accion)) {
				Long numeroEmergencias = emergencia.obtenerTotalEmergencias(request);
				String json = objectMapper.writeValueAsString(numeroEmergencias);
				response.getWriter().write(json);
				utilidades.ficheroLog(1,
						"EmergenciaControlador - doGet() - Número total de emergencias enviado correctamente", 0);
			} else {
				String errorJson = objectMapper.writeValueAsString("{\"error\": \"Acción no válida\"}");
				response.getWriter().write(errorJson);
				utilidades.ficheroLog(3, "EmergenciaControlador - doGet() - Acción no válida", 0);
			}
		} catch (Exception e) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			response.getWriter().write("{\"error\": \"Ocurrió un error en el servidor\"}");
			utilidades.ficheroLog(3, "EmergenciaControlador - doGet() - Error interno - " + e.getMessage(), 0);
			e.printStackTrace();
		}
	}

	/**
	 * Procesa la acción de añadir una emergencia. Recoge los parámetros, envía
	 * correos de alerta y guarda la emergencia.
	 * 
	 * @param request  La solicitud HTTP.
	 * @param response La respuesta HTTP.
	 * @throws ServletException En caso de error en el procesamiento.
	 * @throws IOException      En caso de error de entrada/salida.
	 */
	private void procesarAniadirEmergencia(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			final String tituloEmergencia = getValueOrDefault(request.getParameter("tituloEmergencia"),
					"Sin Especificar");
			final String categoriaEmergencia = getValueOrDefault(request.getParameter("categoriaEmergencia"),
					"Sin Especificar");
			final String descripcionEmergencia = getValueOrDefault(request.getParameter("descripcionEmergencia"),
					"Sin Especificar");
			final String fechaLanzamientoStr = getValueOrDefault(request.getParameter("fechaLanzamientoEmergencia"),
					"2000-01-01");
			final LocalDate fechaLanzamientoEmergencia = LocalDate.parse(fechaLanzamientoStr);
			final String horaLanzamientoStr = getValueOrDefault(request.getParameter("horaLanzamientoEmergencia"),
					"00:00");
			final LocalTime horaLanzamientoEmergencia = LocalTime.parse(horaLanzamientoStr);
			final List<String> correosUsuarios = emergencia.obtenerCorreosDesdeAPI(request);
			final String asuntoEmergencia = "ALERTA DE EMERGENCIA: " + tituloEmergencia;
			final String mensajeEmergencia = "<html>" + "<head>" + "  <meta charset='UTF-8'>" + "  <style>"
					+ "    body { font-family: Arial, sans-serif; background-color: #f4f4f4; margin: 0; padding: 0; }"
					+ "    .container { max-width: 600px; margin: 20px auto; background: #fff; border-radius: 8px; overflow: hidden; box-shadow: 0 2px 8px rgba(0,0,0,0.15); }"
					+ "    .header { background-color: #d9534f; color: #fff; padding: 20px; text-align: center; }"
					+ "    .content { padding: 20px; }" + "    .content h2 { color: #d9534f; margin-top: 0; }"
					+ "    .content p { font-size: 16px; line-height: 1.5; }"
					+ "    .footer { background: #f9f9f9; padding: 10px; text-align: center; font-size: 12px; color: #777; }"
					+ "  </style>" + "</head>" + "<body>" + "  <div class='container'>" + "    <div class='header'>"
					+ "      <h1>¡ALERTA DE EMERGENCIA!</h1>" + "    </div>" + "    <div class='content'>"
					+ "      <h2>" + tituloEmergencia + "</h2>" + "      <p>" + descripcionEmergencia + "</p>"
					+ "      <p><strong>Instrucciones:</strong> Siga las medidas de seguridad establecidas y proceda a evacuar el área.</p>"
					+ "    </div>" + "    <div class='footer'>"
					+ "      <p>© 2025 VistaVoluntia. Todos los derechos reservados.</p>" + "    </div>" + "  </div>"
					+ "</body>" + "</html>";

			EmergenciaDtos nuevaEmergencia = new EmergenciaDtos();
			nuevaEmergencia.setTituloEmergencia(tituloEmergencia);
			nuevaEmergencia.setCategoriaEmergencia(categoriaEmergencia);
			nuevaEmergencia.setDescripcionEmergencia(descripcionEmergencia);
			nuevaEmergencia.setFechaLanzamientoEmergencia(fechaLanzamientoEmergencia);
			nuevaEmergencia.setHoraLanzamientoEmergencia(horaLanzamientoEmergencia);

			HttpSession session = request.getSession();
			Object idUsuarioObj = session.getAttribute("idUsuario");
			if (idUsuarioObj == null) {
				throw new Exception("El usuario no está autenticado, no se encontró el idUsuario en la sesión.");
			}
			String idUsuarioStr = idUsuarioObj.toString();
			emergencia.guardarEmergencia(nuevaEmergencia, request);

			Thread emailThread = new Thread(() -> {
				try {
					emergencia.enviarCorreosATodos(correosUsuarios, asuntoEmergencia, mensajeEmergencia);
				} catch (Exception ex) {
					utilidades.ficheroLog(3,
							"EmergenciaControlador - procesarAniadirEmergencia() - Error al enviar correos de emergencia: "
									+ ex.getMessage(),
							0);
					ex.printStackTrace();
				}
			});
			emailThread.start();
			emailThread.join();

			utilidades.ficheroLog(1,
					"EmergenciaControlador - procesarAniadirEmergencia() - Emergencia creada correctamente por usuario con id "
							+ idUsuarioStr,
					Long.parseLong(idUsuarioStr));
			forwardResponse(request, response, "success", "Emergencia creada correctamente.");
		} catch (Exception e) {
			utilidades.ficheroLog(3,
					"EmergenciaControlador - procesarAniadirEmergencia() - Error al crear la emergencia - "
							+ e.getMessage(),
					0);
			forwardResponse(request, response, "error", "Error al crear la emergencia.");
			e.printStackTrace();
		}
	}

	/**
	 * Procesa la acción de modificar una emergencia. Recoge los parámetros
	 * actualizados y actualiza la emergencia.
	 * 
	 * @param request  La solicitud HTTP.
	 * @param response La respuesta HTTP.
	 * @throws ServletException En caso de error en el procesamiento.
	 * @throws IOException      En caso de error de entrada/salida.
	 */

	private void procesarModificarEmergencia(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {
	    try {
	        final String idEmergencia = request.getParameter("idEmergencia");
	        if (idEmergencia == null || idEmergencia.trim().isEmpty()) {
	            throw new Exception("ID de emergencia no proporcionado.");
	        }

	        final String tituloEmergencia = getValueOrDefault(request.getParameter("tituloEmergencia"),
	                "Título por Defecto");
	        final String categoriaEmergencia = getValueOrDefault(request.getParameter("categoriaEmergencia"),
	                "Categoría por Defecto");
	        final String descripcionEmergencia = getValueOrDefault(request.getParameter("descripcionEmergencia"),
	                "Descripción por Defecto");

	        EmergenciaDtos emergenciaActualizada = new EmergenciaDtos();
	        emergenciaActualizada.setTituloEmergencia(tituloEmergencia);
	        emergenciaActualizada.setCategoriaEmergencia(categoriaEmergencia);
	        emergenciaActualizada.setDescripcionEmergencia(descripcionEmergencia);

	        boolean actualizado = emergencia.modificarEmergencia(idEmergencia, emergenciaActualizada);
	        if (actualizado) {
	            utilidades.ficheroLog(1, "EmergenciaControlador - procesarModificarEmergencia() - Emergencia con ID "
	                    + idEmergencia + " actualizada correctamente", 0);
	            forwardResponse(request, response, "success", "Emergencia actualizada correctamente.");
	            response.sendRedirect(request.getContextPath() + PAGINA_TABLA);
	        } else {
	            throw new Exception("No se pudo actualizar la emergencia.");
	        }
	    } catch (Exception e) {
	        utilidades.ficheroLog(3,
	                "EmergenciaControlador - procesarModificarEmergencia() - Error al actualizar la emergencia - "
	                        + e.getMessage(),
	                0);
	        forwardResponse(request, response, "error", "Error al actualizar la emergencia: " + e.getMessage());
	        e.printStackTrace();
	    }
	}


	/**
	 * Procesa la acción de eliminar una emergencia. Elimina la emergencia indicada
	 * por su ID.
	 * 
	 * @param request  La solicitud HTTP.
	 * @param response La respuesta HTTP.
	 * @throws ServletException En caso de error en el procesamiento.
	 * @throws IOException      En caso de error de entrada/salida.
	 */
	private void procesarEliminarEmergencia(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			final String idEmergencia = request.getParameter("idEmergencia");
			if (idEmergencia == null || idEmergencia.trim().isEmpty()) {
				throw new Exception("ID de emergencia no proporcionado.");
			}

			boolean eliminado = emergencia.eliminarEmergencia(idEmergencia);
			if (eliminado) {
				utilidades.ficheroLog(1, "EmergenciaControlador - procesarEliminarEmergencia() - Emergencia con ID "
						+ idEmergencia + " eliminada correctamente", 0);
				forwardResponse(request, response, "success", "Emergencia eliminada correctamente.");
			} else {
				throw new Exception("No se pudo eliminar la emergencia.");
			}
		} catch (Exception e) {
			utilidades.ficheroLog(3,
					"EmergenciaControlador - procesarEliminarEmergencia() - Error al eliminar la emergencia - "
							+ e.getMessage(),
					0);
			forwardResponse(request, response, "error", "Error al eliminar la emergencia: " + e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * Retorna el valor recibido si no es nulo o vacío; de lo contrario, retorna el
	 * valor por defecto.
	 *
	 * @param value        El valor obtenido del request.
	 * @param defaultValue El valor por defecto a utilizar en caso de que value sea
	 *                     nulo o vacío.
	 * @return El valor original o el valor por defecto.
	 */
	private String getValueOrDefault(String value, String defaultValue) {
		return (value == null || value.trim().isEmpty()) ? defaultValue : value;
	}

	/**
	 * Método auxiliar para establecer atributos de respuesta y reenviar la petición
	 * a la vista de administración.
	 *
	 * @param request  La solicitud HTTP.
	 * @param response La respuesta HTTP.
	 * @param status   Estado de la operación ("success" o "error").
	 * @param mensaje  Mensaje a mostrar en la vista.
	 * @throws ServletException En caso de error en el procesamiento del servlet.
	 * @throws IOException      En caso de error de entrada/salida.
	 */
	private void forwardResponse(HttpServletRequest request, HttpServletResponse response, String status,
			String mensaje) throws ServletException, IOException {
		request.setAttribute("status", status);
		request.setAttribute("mensaje", mensaje);
		request.getRequestDispatcher(ADMIN_EMERGENCIA_PAGE).forward(request, response);
	}
}
