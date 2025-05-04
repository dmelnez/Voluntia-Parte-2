package VistaVoluntia.Controlador;

import java.io.IOException;
import java.util.ArrayList;

import com.fasterxml.jackson.databind.ObjectMapper;

import ApiVoluntia.ApiVoluntia.Dtos.PlanDtos;
import VistaVoluntia.Servicios.PlanServicios;
import VistaVoluntia.Utilidades.Utilidades;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Controlador de Planes.
 * 
 * @author DMN - 22/02/2025
 */
@MultipartConfig
@WebServlet("/plan")
public class PlanControlador extends HttpServlet {

	private PlanServicios planServicios;
	private Utilidades utilidades;
	private static final String PAGINA_REDIRECT = "/RolAdministradorEmpresa/EmpresaAdminNuevoPlan.jsp";
    private static final String PAGINA_TABLA = "/RolAdministradorEmpresa/EmpresaAdminTablaPlanes.html";

	/**
	 * Inicializa el controlador de planes y sus servicios asociados.
	 *
	 * @throws ServletException Si ocurre un error durante la inicialización.
	 *
	 * @author DMN - 04/03/2025
	 */
	@Override
	public void init() throws ServletException {
		planServicios = new PlanServicios();
		utilidades = new Utilidades();
	}

	/**
	 * Procesa solicitudes POST para añadir, modificar o eliminar un plan.
	 *
	 * @param solicitud La solicitud HTTP entrante.
	 * @param respuesta La respuesta HTTP a enviar.
	 * @throws ServletException Si ocurre un error de servlet durante el
	 *                          procesamiento.
	 * @throws IOException      Si ocurre un error de E/S durante el procesamiento.
	 *
	 * @author DMN - 09/04/2025
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

			String idPlan = solicitud.getParameter("idPlan");
			String tipoPlan = obtenerValorPorDefecto(solicitud.getParameter("tipoPlan"), "Sin Especificar");
			String precioPlan = obtenerValorPorDefecto(solicitud.getParameter("precioPlan"), "99999");
			String tiempoPlan = obtenerValorPorDefecto(solicitud.getParameter("tiempoPlan"), "30 días");
			String descripcionPlan = obtenerValorPorDefecto(solicitud.getParameter("descripcionPlan"),
					"Sin descripción");
			String numeroUsuariosPlan = obtenerValorPorDefecto(solicitud.getParameter("numeroUsuariosPlan"), "0");

			Double precioPlanParseado;
			try {
				precioPlanParseado = Double.parseDouble(precioPlan);
			} catch (NumberFormatException e) {
				manejarAccionInvalida(solicitud, respuesta, "El precio debe ser un número válido.");
				return;
			}

			PlanDtos nuevoPlan = new PlanDtos();
			nuevoPlan.setTipoPlan(tipoPlan);
			nuevoPlan.setPrecioPlan(precioPlanParseado);
			nuevoPlan.setTiempoPlan(tiempoPlan);
			nuevoPlan.setDescripcionPlan(descripcionPlan);
			nuevoPlan.setNumeroUsuariosPlan(numeroUsuariosPlan);
			switch (accion) {
			case "aniadir":
				procesarAgregar(solicitud, respuesta, nuevoPlan);
				break;
			case "modificar":
				procesarModificar(solicitud, respuesta, idPlan, nuevoPlan);
				break;
			case "eliminar":
				procesarEliminar(solicitud, respuesta, idPlan);
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
	 * Procesa solicitudes GET devolviendo la lista de planes en formato JSON.
	 *
	 * @param solicitud La solicitud HTTP entrante.
	 * @param respuesta La respuesta HTTP a enviar.
	 * @throws ServletException Si ocurre un error de servlet durante el
	 *                          procesamiento.
	 * @throws IOException      Si ocurre un error de E/S durante el procesamiento.
	 *
	 * @author DMN - 21/04/2025
	 */
	@Override
	protected void doGet(HttpServletRequest solicitud, HttpServletResponse respuesta)
			throws ServletException, IOException {
		try {
			ArrayList<PlanDtos> listaPlanes = planServicios.listaPlanes();
			respuesta.setContentType("application/json");
			respuesta.setCharacterEncoding("UTF-8");

			ObjectMapper mapeador = new ObjectMapper();
			String json = mapeador.writeValueAsString(listaPlanes);
			respuesta.getWriter().write(json);
			utilidades.ficheroLog(1, "PlanControlador: Lista de planes enviada correctamente.", 0);
		} catch (Exception e) {
			respuesta.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			respuesta.getWriter().write("{\"error\": \"Ocurrió un error en el servidor\"}");
			utilidades.ficheroLog(3, "PlanControlador: Error interno - " + e.getMessage(), 0);
			e.printStackTrace();
		}
	}

	/**
	 * Retorna el valor recibido o un valor por defecto si es nulo o vacío.
	 *
	 * @param value           Valor recibido.
	 * @param valorPorDefecto Valor por defecto a utilizar.
	 * @return El valor original o el valor por defecto.
	 *
	 * @author DMN - 15/03/2025
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
	 * @author DMN - 28/03/2025
	 */
	private void manejarAccionInvalida(HttpServletRequest solicitud, HttpServletResponse respuesta, String mensaje)
			throws ServletException, IOException {
		solicitud.setAttribute("status", "error");
		solicitud.setAttribute("mensaje", mensaje);
		utilidades.ficheroLog(3, "PlanControlador: " + mensaje, 0);
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
	 * @author DMN - 02/04/2025
	 */
	private void registrarError(HttpServletRequest solicitud, HttpServletResponse respuesta, String mensajeError)
			throws ServletException, IOException {
		solicitud.setAttribute("status", "error");
		solicitud.setAttribute("mensaje", mensajeError);
		utilidades.ficheroLog(3, "PlanControlador: " + mensajeError, 0);
		solicitud.getRequestDispatcher(PAGINA_REDIRECT).forward(solicitud, respuesta);
	}

	/**
	 * Procesa la acción de agregar un plan.
	 *
	 * @param solicitud La solicitud HTTP con los datos del nuevo plan.
	 * @param respuesta La respuesta HTTP.
	 * @param nuevoPlan Objeto PlanDtos a agregar.
	 * @throws ServletException Si ocurre un error de servlet durante el forward.
	 * @throws IOException      Si ocurre un error de E/S durante el forward.
	 *
	 * @author DMN - 18/04/2025
	 */
	private void procesarAgregar(HttpServletRequest solicitud, HttpServletResponse respuesta, PlanDtos nuevoPlan)
			throws ServletException, IOException {
		try {
			planServicios.guardarPlan(nuevoPlan);
			solicitud.setAttribute("status", "success");
			solicitud.setAttribute("mensaje", "Plan añadido correctamente.");
			utilidades.ficheroLog(1, "PlanControlador: Plan añadido correctamente.", 0);
			solicitud.getRequestDispatcher(PAGINA_REDIRECT).forward(solicitud, respuesta);
		} catch (Exception e) {
			registrarError(solicitud, respuesta, "Error al agregar plan: " + e.getMessage());
		}
	}

	/**
	 * Procesa la acción de modificar un plan.
	 *
	 * @param solicitud       La solicitud HTTP con datos de modificación.
	 * @param respuesta       La respuesta HTTP.
	 * @param idPlan          ID del plan a modificar.
	 * @param planActualizado Objeto PlanDtos con los datos actualizados.
	 * @throws ServletException Si ocurre un error de servlet durante el forward.
	 * @throws IOException      Si ocurre un error de E/S durante el forward.
	 *
	 * @author DMN - 24/04/2025
	 */
	private void procesarModificar(HttpServletRequest solicitud, HttpServletResponse respuesta, String idPlan,
			PlanDtos planActualizado) throws ServletException, IOException {
		try {
			if (idPlan == null || idPlan.isEmpty()) {
				manejarAccionInvalida(solicitud, respuesta, "ID del Plan no proporcionado para modificar.");
				return;
			}
			boolean actualizado = planServicios.modificarPlan(idPlan, planActualizado);
			if (actualizado) {
				solicitud.setAttribute("status", "success");
				solicitud.setAttribute("mensaje", "Plan actualizado correctamente.");
				utilidades.ficheroLog(1, "PlanControlador: Plan con ID " + idPlan + " actualizado correctamente.", 0);
			} else {
				solicitud.setAttribute("status", "error");
				solicitud.setAttribute("mensaje", "No se pudo actualizar el Plan.");
				utilidades.ficheroLog(3, "PlanControlador: No se pudo actualizar el Plan con ID " + idPlan + ".", 0);
			}
			respuesta.sendRedirect(solicitud.getContextPath() + PAGINA_TABLA);
		} catch (Exception e) {
			registrarError(solicitud, respuesta, "Error al modificar plan: " + e.getMessage());
		}
	}

	/**
	 * Procesa la acción de eliminar un plan.
	 *
	 * @param solicitud La solicitud HTTP.
	 * @param respuesta La respuesta HTTP.
	 * @param idPlan    ID del plan a eliminar.
	 * @throws ServletException Si ocurre un error de servlet durante el forward.
	 * @throws IOException      Si ocurre un error de E/S durante el forward.
	 *
	 * @author DMN - 26/04/2025
	 */
	private void procesarEliminar(HttpServletRequest solicitud, HttpServletResponse respuesta, String idPlan)
			throws ServletException, IOException {
		try {
			if (idPlan == null || idPlan.isEmpty()) {
				manejarAccionInvalida(solicitud, respuesta, "ID del Plan no proporcionado para eliminar.");
				return;
			}
			boolean eliminado = planServicios.eliminarPlan(idPlan);
			if (eliminado) {
				solicitud.setAttribute("status", "success");
				solicitud.setAttribute("mensaje", "Plan eliminado correctamente.");
				utilidades.ficheroLog(1, "PlanControlador: Plan con ID " + idPlan + " eliminado correctamente.", 0);
			} else {
				solicitud.setAttribute("status", "error");
				solicitud.setAttribute("mensaje", "No se pudo eliminar el Plan.");
				utilidades.ficheroLog(3, "PlanControlador: No se pudo eliminar el Plan con ID " + idPlan + ".", 0);
			}
			solicitud.getRequestDispatcher(PAGINA_REDIRECT).forward(solicitud, respuesta);
		} catch (Exception e) {
			registrarError(solicitud, respuesta, "Error al eliminar plan: " + e.getMessage());
		}
	}

}
