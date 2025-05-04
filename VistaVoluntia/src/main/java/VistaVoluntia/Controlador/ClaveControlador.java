package VistaVoluntia.Controlador;

import java.io.IOException;
import java.util.ArrayList;

import com.fasterxml.jackson.databind.ObjectMapper;

import ApiVoluntia.ApiVoluntia.Dtos.ClaveDtos;
import VistaVoluntia.Servicios.ClaveServicios;
import VistaVoluntia.Utilidades.Utilidades;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@MultipartConfig
@WebServlet("/clave")
public class ClaveControlador extends HttpServlet {

	private ClaveServicios claveServicios;
	private Utilidades utilidades;

	private static final String PAGINA_REDIRECT = "/RolAdministradorEmpresa/EmpresaAdminNuevoCargo.jsp";
    private static final String PAGINA_TABLA = "/RolAdministradorEmpresa/EmpresaAdminTablaCargos.html";

	/**
	 * Inicializa el servlet y sus servicios asociados.
	 *
	 * @throws ServletException Si ocurre un error durante la inicialización.
	 *
	 * @author DMN - 02/03/2025
	 */
	@Override
	public void init() throws ServletException {
		claveServicios = new ClaveServicios();
		utilidades = new Utilidades();
	}

	/**
	 * Procesa solicitudes POST para añadir, modificar o eliminar una clave.
	 *
	 * @param solicitud La solicitud HTTP entrante.
	 * @param respuesta La respuesta HTTP a enviar.
	 * @throws ServletException Si ocurre un error de servlet durante el
	 *                          procesamiento.
	 * @throws IOException      Si ocurre un error de E/S durante el procesamiento.
	 *
	 * @author DMN - 12/04/2025
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

			String nombreClave = obtenerValorPorDefecto(solicitud.getParameter("nombreClave"), "Sin especificar");
			String descripcionClave = obtenerValorPorDefecto(solicitud.getParameter("descripcionClave"),
					"Sin especificar");
			String idClave = solicitud.getParameter("idClave");

			ClaveDtos nuevaClave = new ClaveDtos();
			nuevaClave.setNombreClave(nombreClave);
			nuevaClave.setDescripcionClave(descripcionClave);

			switch (accion) {
			case "aniadir":
				procesarAgregar(solicitud, respuesta, nuevaClave);
				break;
			case "modificar":
				procesarModificar(solicitud, respuesta, idClave, nuevaClave);
				break;
			case "eliminar":
				procesarEliminar(solicitud, respuesta, idClave);
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
	 * Procesa solicitudes GET devolviendo la lista de claves en formato JSON.
	 *
	 * @param solicitud La solicitud HTTP entrante.
	 * @param respuesta La respuesta HTTP a enviar.
	 * @throws ServletException Si ocurre un error de servlet durante el
	 *                          procesamiento.
	 * @throws IOException      Si ocurre un error de E/S durante el procesamiento.
	 *
	 * @author DMN - 15/03/2025
	 */
	@Override
	protected void doGet(HttpServletRequest solicitud, HttpServletResponse respuesta)
			throws ServletException, IOException {
		try {
			ArrayList<ClaveDtos> listaClaves = claveServicios.listaClaves(solicitud);
			respuesta.setContentType("application/json");
			respuesta.setCharacterEncoding("UTF-8");

			ObjectMapper mapeador = new ObjectMapper();
			String json = mapeador.writeValueAsString(listaClaves);
			respuesta.getWriter().write(json);

			utilidades.ficheroLog(1, "ClaveControlador: Lista de claves enviada correctamente.", 0);
		} catch (Exception e) {
			respuesta.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			respuesta.getWriter().write("{\"error\": \"Ocurrió un error en el servidor\"}");
			utilidades.ficheroLog(3, "ClaveControlador: Error interno - " + e.getMessage(), 0);
			e.printStackTrace();
		}
	}

	/**
	 * Retorna el valor recibido o un valor por defecto si es nulo o está vacío.
	 *
	 * @param value           Valor recibido.
	 * @param valorPorDefecto Valor por defecto a retornar.
	 * @return El valor original o el valor por defecto.
	 *
	 * @author DMN - 18/03/2025
	 */
	private String obtenerValorPorDefecto(String value, String valorPorDefecto) {
		return (value == null || value.trim().isEmpty()) ? valorPorDefecto : value;
	}

	/**
	 * Maneja acciones inválidas configurando atributos de error, registrando en log
	 * y reenviando a la página.
	 *
	 * @param solicitud La solicitud HTTP.
	 * @param respuesta La respuesta HTTP.
	 * @param mensaje   Mensaje de error a mostrar.
	 * @throws ServletException Si ocurre un error de servlet durante el forward.
	 * @throws IOException      Si ocurre un error de E/S durante el forward.
	 *
	 * @author DMN - 20/03/2025
	 */
	private void manejarAccionInvalida(HttpServletRequest solicitud, HttpServletResponse respuesta, String mensaje)
			throws ServletException, IOException {
		solicitud.setAttribute("status", "error");
		solicitud.setAttribute("mensaje", mensaje);
		utilidades.ficheroLog(3, "ClaveControlador: " + mensaje, 0);
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
	 * @author DMN - 25/03/2025
	 */
	private void registrarError(HttpServletRequest solicitud, HttpServletResponse respuesta, String mensajeError)
			throws ServletException, IOException {
		solicitud.setAttribute("status", "error");
		solicitud.setAttribute("mensaje", mensajeError);
		utilidades.ficheroLog(3, "ClaveControlador: " + mensajeError, 0);
		solicitud.getRequestDispatcher(PAGINA_REDIRECT).forward(solicitud, respuesta);
	}

	/**
	 * Procesa la acción de agregar una clave.
	 *
	 * @param solicitud  La solicitud HTTP.
	 * @param respuesta  La respuesta HTTP.
	 * @param nuevaClave Objeto ClaveDtos a agregar.
	 * @throws ServletException Si ocurre un error de servlet durante el forward.
	 * @throws IOException      Si ocurre un error de E/S durante el forward.
	 *
	 * @author DMN - 28/03/2025
	 */
	private void procesarAgregar(HttpServletRequest solicitud, HttpServletResponse respuesta, ClaveDtos nuevaClave)
			throws ServletException, IOException {
		try {
			claveServicios.guardarClave(nuevaClave, solicitud);
			solicitud.setAttribute("status", "success");
			solicitud.setAttribute("mensaje", "Clave añadida correctamente.");
			utilidades.ficheroLog(1, "ClaveControlador: Clave añadida correctamente.", 0);
			solicitud.getRequestDispatcher(PAGINA_REDIRECT).forward(solicitud, respuesta);
		} catch (Exception e) {
			registrarError(solicitud, respuesta, "Error al agregar clave: " + e.getMessage());
		}
	}

	/**
	 * Procesa la acción de modificar una clave.
	 *
	 * @param solicitud        La solicitud HTTP.
	 * @param respuesta        La respuesta HTTP.
	 * @param idClave          ID de la clave a modificar.
	 * @param claveActualizada Objeto ClaveDtos con los datos actualizados.
	 * @throws ServletException Si ocurre un error de servlet durante el forward.
	 * @throws IOException      Si ocurre un error de E/S durante el forward.
	 *
	 * @author DMN - 04/04/2025
	 */
	private void procesarModificar(HttpServletRequest solicitud, HttpServletResponse respuesta, String idClave,
			ClaveDtos claveActualizada) throws ServletException, IOException {
		try {
			if (idClave == null || idClave.isEmpty()) {
				manejarAccionInvalida(solicitud, respuesta, "ID de la clave no proporcionado para modificar.");
				return;
			}
			boolean actualizado = claveServicios.modificarClave(idClave, claveActualizada);
			if (actualizado) {
				solicitud.setAttribute("status", "success");
				solicitud.setAttribute("mensaje", "Clave actualizada correctamente.");
				utilidades.ficheroLog(1, "ClaveControlador: Clave con ID " + idClave + " actualizada correctamente.",
						0);
			} else {
				solicitud.setAttribute("status", "error");
				solicitud.setAttribute("mensaje", "No se pudo actualizar la clave.");
				utilidades.ficheroLog(3, "ClaveControlador: No se pudo actualizar la clave con ID " + idClave + ".", 0);
			}
			respuesta.sendRedirect(solicitud.getContextPath() + PAGINA_TABLA);
		} catch (Exception e) {
			registrarError(solicitud, respuesta, "Error al modificar clave: " + e.getMessage());
		}
	}

	/**
	 * Procesa la acción de eliminar una clave.
	 *
	 * @param solicitud La solicitud HTTP.
	 * @param respuesta La respuesta HTTP.
	 * @param idClave   ID de la clave a eliminar.
	 * @throws ServletException Si ocurre un error de servlet durante el forward.
	 * @throws IOException      Si ocurre un error de E/S durante el forward.
	 *
	 * @author DMN - 26/04/2025
	 */
	private void procesarEliminar(HttpServletRequest solicitud, HttpServletResponse respuesta, String idClave)
			throws ServletException, IOException {
		try {
			if (idClave == null || idClave.isEmpty()) {
				manejarAccionInvalida(solicitud, respuesta, "ID de la clave no proporcionado para eliminar.");
				return;
			}
			boolean eliminado = claveServicios.eliminarClave(idClave);
			if (eliminado) {
				solicitud.setAttribute("status", "success");
				solicitud.setAttribute("mensaje", "Clave eliminada correctamente.");
				utilidades.ficheroLog(1, "ClaveControlador: Clave con ID " + idClave + " eliminada correctamente.", 0);
			} else {
				solicitud.setAttribute("status", "error");
				solicitud.setAttribute("mensaje", "No se pudo eliminar la clave.");
				utilidades.ficheroLog(3, "ClaveControlador: No se pudo eliminar la clave con ID " + idClave + ".", 0);
			}
			solicitud.getRequestDispatcher(PAGINA_REDIRECT).forward(solicitud, respuesta);
		} catch (Exception e) {
			registrarError(solicitud, respuesta, "Error al eliminar clave: " + e.getMessage());
		}
	}
}
