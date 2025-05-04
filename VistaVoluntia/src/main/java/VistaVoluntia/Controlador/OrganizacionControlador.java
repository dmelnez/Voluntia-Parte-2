package VistaVoluntia.Controlador;

import java.io.IOException;
import java.util.ArrayList;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import VistaVoluntia.Dtos.OrganizacionDtos;
import VistaVoluntia.Servicios.OrganizacionServicios;
import VistaVoluntia.Utilidades.Utilidades;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@MultipartConfig
@WebServlet("/organizacion")
public class OrganizacionControlador extends HttpServlet {

	private OrganizacionServicios organizacionServicios;
	private Utilidades utilidades;

	private static final String PAGINA_REDIRECT = "/RolAdministradorEmpresa/EmpresaAdminNuevoCargo.jsp";
    private static final String PAGINA_TABLA = "/RolAdministradorEmpresa/EmpresaAdminTablaOrganizaciones.html";

	/**
	 * Inicializa el controlador de organizaciones y sus servicios asociados.
	 *
	 * @throws ServletException Si ocurre un error durante la inicialización.
	 *
	 * @author DMN - 10/03/2025
	 */
	@Override
	public void init() throws ServletException {
		organizacionServicios = new OrganizacionServicios();
		utilidades = new Utilidades();
	}

	/**
	 * Procesa solicitudes POST para añadir, modificar o eliminar una organización.
	 *
	 * @param solicitud La solicitud HTTP entrante.
	 * @param respuesta La respuesta HTTP a enviar.
	 * @throws ServletException Si ocurre un error de servlet durante el
	 *                          procesamiento.
	 * @throws IOException      Si ocurre un error de E/S durante el procesamiento.
	 *
	 * @author DMN - 20/04/2025
	 */
	@Override
	protected void doPost(HttpServletRequest solicitud, HttpServletResponse respuesta)
			throws ServletException, IOException {
		try {
			String accion = solicitud.getParameter("accion");

			if (accion == null || accion.isEmpty()) {
				manejarAccionInvalida(solicitud, respuesta, "Acción no válida o parámetros faltantes.");
				return;
			}

			String idOrganizacion = solicitud.getParameter("idOrganizacion");
			String ciudadOrganizacion = obtenerValorPorDefecto(solicitud.getParameter("ciudadOrganizacion"),
					"Sin Especificar");
			String direccionOrganizacion = obtenerValorPorDefecto(solicitud.getParameter("direccionOrganizacion"),
					"Sin Especificar");
			String emailOrganizacion = obtenerValorPorDefecto(solicitud.getParameter("emailOrganizacion"),
					"correo@gmail.com");
			String nifOrganizacion = obtenerValorPorDefecto(solicitud.getParameter("nifOrganizacion"),
					"9A9B9C9D9E9F9G");
			String nombreOrganizacion = obtenerValorPorDefecto(solicitud.getParameter("nombreOrganizacion"),
					"Sin Especificar");
			String provinciaOrganizacion = obtenerValorPorDefecto(solicitud.getParameter("provinciaOrganizacion"),
					"Sin Especificar");
			String telefonoOrganizacion = obtenerValorPorDefecto(solicitud.getParameter("telefonoOrganizacion"),
					"Sin Especificar");
			String tipoInstitucionOrganizacion = obtenerValorPorDefecto(
					solicitud.getParameter("tipoInstitucionOrganizacion"), "Sin Especificar");

			OrganizacionDtos nuevaOrganizacionDtos = new OrganizacionDtos();
			nuevaOrganizacionDtos.setCiudadOrganizacion(ciudadOrganizacion);
			nuevaOrganizacionDtos.setDireccionOrganizacion(direccionOrganizacion);
			nuevaOrganizacionDtos.setEmailOrganizacion(emailOrganizacion);
			nuevaOrganizacionDtos.setNifOrganizacion(nifOrganizacion);
			nuevaOrganizacionDtos.setNombreOrganizacion(nombreOrganizacion);
			nuevaOrganizacionDtos.setProvinciaOrganizacion(provinciaOrganizacion);
			nuevaOrganizacionDtos.setTelefonoOrganizacion(telefonoOrganizacion);
			nuevaOrganizacionDtos.setTipoInstitucionOrganizacion(tipoInstitucionOrganizacion);

			switch (accion) {
			case "aniadir":
				procesarAgregar(solicitud, respuesta, nuevaOrganizacionDtos);
				break;
			case "modificar":
				procesarModificar(solicitud, respuesta, idOrganizacion, nuevaOrganizacionDtos);
				break;
			case "eliminar":
				procesarEliminar(solicitud, respuesta, idOrganizacion);
				break;
			default:
				manejarAccionInvalida(solicitud, respuesta, "Acción no válida o parámetros faltantes.");
				break;
			}
		} catch (Exception e) {
			registrarError(solicitud, respuesta, "Se produjo un error interno: " + e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * Procesa solicitudes GET para devolver la lista de organizaciones en formato
	 * JSON.
	 *
	 * @param solicitud La solicitud HTTP entrante.
	 * @param respuesta La respuesta HTTP a enviar.
	 * @throws ServletException Si ocurre un error de servlet durante el
	 *                          procesamiento.
	 * @throws IOException      Si ocurre un error de E/S durante el procesamiento.
	 *
	 * @author DMN - 18/04/2025
	 */
	@Override
	protected void doGet(HttpServletRequest solicitud, HttpServletResponse respuesta)
			throws ServletException, IOException {
		String accion = solicitud.getParameter("accion");
		if ("listaOrganizaciones".equals(accion)) {
			try {
				ArrayList<OrganizacionDtos> lista = organizacionServicios.listaOrganizaciones();
				respuesta.setContentType("application/json");
				respuesta.setCharacterEncoding("UTF-8");

				ObjectMapper mapper = new ObjectMapper();
				mapper.registerModule(new JavaTimeModule());
				mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

				String json = mapper.writeValueAsString(lista);
				respuesta.getWriter().write(json);

				utilidades.ficheroLog(1, "OrganizacionControlador: Lista de organizaciones enviada.", 0);
			} catch (Exception e) {
				respuesta.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				respuesta.getWriter().write("{\"error\": \"Error en el servidor\"}");
				utilidades.ficheroLog(3, "OrganizacionControlador: Error interno - " + e.getMessage(), 0);
				e.printStackTrace();
			}
		} else {
			respuesta.sendError(HttpServletResponse.SC_BAD_REQUEST, "Parámetro 'accion' no válido");
		}
	}

	/**
	 * Retorna el valor recibido o un valor por defecto si es nulo o está vacío.
	 *
	 * @param value           Valor recibido.
	 * @param valorPorDefecto Valor por defecto a utilizar.
	 * @return El valor original o el valor por defecto.
	 *
	 * @author DMN - 12/03/2025
	 */
	private String obtenerValorPorDefecto(String value, String valorPorDefecto) {
		return (value == null || value.trim().isEmpty()) ? valorPorDefecto : value;
	}

	/**
	 * Maneja acciones inválidas configurando atributos de error, registrando en log
	 * y reenviando a la página de redirección.
	 *
	 * @param solicitud La solicitud HTTP.
	 * @param respuesta La respuesta HTTP.
	 * @param mensaje   Mensaje de error a mostrar.
	 * @throws ServletException Si ocurre un error de servlet durante el forward.
	 * @throws IOException      Si ocurre un error de E/S durante el forward.
	 *
	 * @author DMN - 15/04/2025
	 */
	private void manejarAccionInvalida(HttpServletRequest solicitud, HttpServletResponse respuesta, String mensaje)
			throws ServletException, IOException {
		solicitud.setAttribute("status", "error");
		solicitud.setAttribute("mensaje", mensaje);
		utilidades.ficheroLog(3, "OrganizacionControlador: " + mensaje, 0);
		solicitud.getRequestDispatcher(PAGINA_REDIRECT).forward(solicitud, respuesta);
	}

	/**
	 * Registra un error, configura los atributos de error y reenvía la solicitud.
	 *
	 * @param solicitud    La solicitud HTTP.
	 * @param respuesta    La respuesta HTTP.
	 * @param mensajeError Mensaje de error a mostrar.
	 * @throws ServletException Si ocurre un error de servlet durante el forward.
	 * @throws IOException      Si ocurre un error de E/S durante el forward.
	 *
	 * @author DMN - 17/04/2025
	 */
	private void registrarError(HttpServletRequest solicitud, HttpServletResponse respuesta, String mensajeError)
			throws ServletException, IOException {
		solicitud.setAttribute("status", "error");
		solicitud.setAttribute("mensaje", mensajeError);
		utilidades.ficheroLog(3, "OrganizacionControlador: " + mensajeError, 0);
		solicitud.getRequestDispatcher(PAGINA_REDIRECT).forward(solicitud, respuesta);
	}

	/**
	 * Procesa la acción de agregar una organización.
	 *
	 * @param solicitud         La solicitud HTTP con los datos de la organización.
	 * @param respuesta         La respuesta HTTP.
	 * @param nuevaOrganizacion Objeto OrganizacionDtos a agregar.
	 * @throws ServletException Si ocurre un error de servlet durante la respuesta.
	 * @throws IOException      Si ocurre un error de E/S durante la respuesta.
	 *
	 * @author DMN - 22/04/2025
	 */
	private void procesarAgregar(HttpServletRequest solicitud, HttpServletResponse respuesta,
			OrganizacionDtos nuevaOrganizacion) throws ServletException, IOException {
		try {
			organizacionServicios.guardarOrganizacion(nuevaOrganizacion);
			utilidades.ficheroLog(1, "OrganizacionControlador: Organización añadida correctamente.", 0);

			String json = "{\"status\":\"success\",\"mensaje\":\"Organización añadida correctamente.\"}";
			respuesta.setStatus(HttpServletResponse.SC_OK);
			respuesta.setContentType("application/json");
			respuesta.setCharacterEncoding("UTF-8");
			respuesta.getWriter().write(json);
		} catch (Exception e) {
			utilidades.ficheroLog(3, "OrganizacionControlador: Error al agregar organización: " + e.getMessage(), 0);
			String mensaje = e.getMessage() != null ? e.getMessage().replace("\"", "\\\"") : "Error desconocido";
			String json = String.format("{\"status\":\"error\",\"mensaje\":\"%s\"}", mensaje);
			respuesta.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			respuesta.setContentType("application/json");
			respuesta.setCharacterEncoding("UTF-8");
			respuesta.getWriter().write(json);
		}
	}

	/**
	 * Procesa la acción de modificar una organización.
	 *
	 * @param solicitud               La solicitud HTTP con los datos actualizados.
	 * @param respuesta               La respuesta HTTP.
	 * @param idOrganizacion          ID de la organización a modificar.
	 * @param organizacionActualizada Objeto OrganizacionDtos con los datos
	 *                                actualizados.
	 * @throws ServletException Si ocurre un error de servlet durante la
	 *                          redirección.
	 * @throws IOException      Si ocurre un error de E/S durante la redirección.
	 *
	 * @author DMN - 14/04/2025
	 */
	private void procesarModificar(HttpServletRequest solicitud, HttpServletResponse respuesta, String idOrganizacion,
			OrganizacionDtos organizacionActualizada) throws ServletException, IOException {
		try {
			if (idOrganizacion == null || idOrganizacion.isEmpty()) {
				manejarAccionInvalida(solicitud, respuesta, "ID de la organización no proporcionado para modificar.");
				return;
			}
			boolean actualizado = organizacionServicios.modificarOrganizacion(idOrganizacion, organizacionActualizada);
			if (actualizado) {
				utilidades.ficheroLog(1,
						"OrganizacionControlador: Organización con ID " + idOrganizacion + " modificada correctamente.",
						0);
			} else {
				utilidades.ficheroLog(3,
						"OrganizacionControlador: No se pudo modificar la organización con ID " + idOrganizacion + ".",
						0);
			}
			respuesta.sendRedirect(solicitud.getContextPath() + PAGINA_TABLA);

		} catch (Exception e) {
			registrarError(solicitud, respuesta, "Error al modificar organización: " + e.getMessage());
		}
	}

	/**
	 * Procesa la acción de eliminar una organización.
	 *
	 * @param solicitud      La solicitud HTTP.
	 * @param respuesta      La respuesta HTTP.
	 * @param idOrganizacion ID de la organización a eliminar.
	 * @throws ServletException Si ocurre un error de servlet durante la respuesta.
	 * @throws IOException      Si ocurre un error de E/S durante la respuesta.
	 *
	 * @author DMN - 26/03/2025
	 */
	private void procesarEliminar(HttpServletRequest solicitud, HttpServletResponse respuesta, String idOrganizacion)
			throws ServletException, IOException {
		if (idOrganizacion == null || idOrganizacion.isEmpty()) {
			respuesta.setContentType("application/json");
			respuesta.setCharacterEncoding("UTF-8");
			respuesta.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			respuesta.getWriter().write("{\"status\":\"error\",\"mensaje\":\"ID de organización no proporcionado.\"}");
			return;
		}
		boolean eliminado = organizacionServicios.eliminarOrganizacion(idOrganizacion);
		respuesta.setContentType("application/json");
		respuesta.setCharacterEncoding("UTF-8");
		if (eliminado) {
			utilidades.ficheroLog(1,
					"OrganizacionControlador: Organización con ID " + idOrganizacion + " eliminada correctamente.", 0);
			respuesta.setStatus(HttpServletResponse.SC_OK);
			respuesta.getWriter()
					.write("{\"status\":\"success\",\"mensaje\":\"Organización eliminada correctamente.\"}");
		} else {
			utilidades.ficheroLog(3,
					"OrganizacionControlador: No se pudo eliminar la organización con ID " + idOrganizacion + ".", 0);
			respuesta.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			respuesta.getWriter().write("{\"status\":\"error\",\"mensaje\":\"No se pudo eliminar la organización.\"}");
		}
	}

}
