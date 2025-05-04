package VistaVoluntia.Controlador;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import com.fasterxml.jackson.databind.ObjectMapper;

import VistaVoluntia.Dtos.ServicioDtos;
import VistaVoluntia.Servicios.ServicioServicios;
import VistaVoluntia.Servicios.VehiculoServicios;
import VistaVoluntia.Utilidades.Utilidades;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Controlador de Servicios.
 * 
 * @author DMN - 22/02/2025
 */
@MultipartConfig
@WebServlet("/servicio")
public class ServicioControlador extends HttpServlet {

	private ServicioServicios servicio;
	private VehiculoServicios vehiculoServicio;
	private Utilidades utilidades;
	private static final String PAGINA_REDIRECT = "/RolAdministradorEmpresa/EmpresaAdminNuevoServicio.jsp";
    private static final String PAGINA_TABLA = "/RolAdministradorEmpresa/EmpresaAdminTablaServicios.html";

	/**
	 * Inicializa el servlet y sus servicios asociados.
	 *
	 * @throws ServletException Si ocurre un error durante la inicialización.
	 *
	 * @author DMN - 07/03/2025
	 */
	@Override
	public void init() throws ServletException {
		servicio = new ServicioServicios();
		vehiculoServicio = new VehiculoServicios();
		utilidades = new Utilidades();
	}

	/**
	 * Procesa solicitudes POST para agregar, modificar o eliminar servicios.
	 *
	 * @param solicitud La solicitud HTTP entrante.
	 * @param respuesta La respuesta HTTP a enviar.
	 * @throws ServletException Si ocurre un error de servlet durante el
	 *                          procesamiento.
	 * @throws IOException      Si ocurre un error de E/S durante el procesamiento.
	 *
	 * @author DMN - 11/04/2025
	 */
	@Override
	protected void doPost(HttpServletRequest solicitud, HttpServletResponse respuesta)
			throws ServletException, IOException {
		HttpSession sesion = solicitud.getSession(false);
		Long idUsuario = (Long) sesion.getAttribute("idUsuario");
		String accion = solicitud.getParameter("accion");

		if (accion == null || accion.isEmpty()) {
			manejarAccionInvalida(solicitud, respuesta, "Acción no válida o parámetros faltantes.", idUsuario,
					"doPost()");
			return;
		}

		try {
			ServicioDtos nuevoServicio = construirServicioDto(solicitud);

			switch (accion) {
			case "aniadir":
				procesarAgregar(solicitud, respuesta, nuevoServicio, idUsuario);
				break;
			case "modificar":
				procesarModificar(solicitud, respuesta, nuevoServicio, idUsuario);
				break;
			case "eliminar":
				procesarEliminar(solicitud, respuesta, idUsuario);
				break;
			default:
				manejarAccionInvalida(solicitud, respuesta, "Acción no válida.", idUsuario, "doPost()");
			}
		} catch (IllegalArgumentException e) {
			registrarError(solicitud, respuesta, "Error en los parámetros: " + e.getMessage(), idUsuario, "doPost()");
		} catch (Exception e) {
			registrarError(solicitud, respuesta, "Se produjo un error interno: " + e.getMessage(), idUsuario,
					"doPost()");
			e.printStackTrace();
		}
	}

	/**
	 * Procesa solicitudes GET para obtener información en formato JSON.
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
		respuesta.setContentType("application/json");
		respuesta.setCharacterEncoding("UTF-8");
		ObjectMapper mapeadorObjetos = new ObjectMapper();
		mapeadorObjetos.registerModule(new com.fasterxml.jackson.datatype.jsr310.JavaTimeModule());

		try {
			String accion = solicitud.getParameter("accion");
			if ("listaServicios".equals(accion)) {
				procesarObtenerListaServicios(solicitud, respuesta, mapeadorObjetos);
			} else if ("numeroServicios".equals(accion)) {
				procesarObtenerNumeroServicios(solicitud, respuesta, mapeadorObjetos);
			} else {
				procesarAccionInvalida(respuesta, mapeadorObjetos, accion);
			}
		} catch (Exception e) {
			respuesta.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			respuesta.getWriter().write("{\"error\": \"Ocurrió un error en el servidor\"}");
			utilidades.ficheroLog(3, "doGet: Error interno - " + e.getMessage(), 0);
			e.printStackTrace();
		}
	}

	/**
	 * Construye y retorna un objeto ServicioDtos a partir de los parámetros de la
	 * solicitud.
	 *
	 * @param solicitud Solicitud HTTP que contiene los parámetros del servicio.
	 * @return Objeto ServicioDtos construido con los valores parseados.
	 * @throws IllegalArgumentException Si ocurre un error en la conversión de
	 *                                  parámetros.
	 *
	 * @author DMN - 12/03/2025
	 */
	private ServicioDtos construirServicioDto(HttpServletRequest solicitud) {
		try {
			String numeracionServicio = obtenerValorPorDefecto(solicitud.getParameter("numeracionServicio"), "0001");
			String tituloServicio = obtenerValorPorDefecto(solicitud.getParameter("tituloServicio"),
					"Servicio General");
			String fechaInicioServicioStr = obtenerValorPorDefecto(solicitud.getParameter("fechaInicioServicio"),
					"2000-01-01");
			String fechaFinServicioStr = obtenerValorPorDefecto(solicitud.getParameter("fechaFinServicio"),
					"2000-01-01");
			String fechaLimiteInscripcionServicioStr = obtenerValorPorDefecto(
					solicitud.getParameter("fechaLimiteInscripcionServicio"), "2000-01-01");
			String horaBaseServicioStr = obtenerValorPorDefecto(solicitud.getParameter("horaBaseServicio"), "00:00");
			String horaSalidaServicioStr = obtenerValorPorDefecto(solicitud.getParameter("horaSalidaServicio"),
					"00:00");
			String maximoAsistentesServicioStr = obtenerValorPorDefecto(
					solicitud.getParameter("maximoAsistentesServicio"), "0");
			String tipoServicio = obtenerValorPorDefecto(solicitud.getParameter("tipoServicio"), "General");
			String categoriaServicio = obtenerValorPorDefecto(solicitud.getParameter("categoriaServicio"),
					"Sin categoría");
			String descripcionServicio = obtenerValorPorDefecto(solicitud.getParameter("descripcionServicio"),
					"Descripción no proporcionada");

			LocalDate fechaInicioServicio = LocalDate.parse(fechaInicioServicioStr);
			LocalDate fechaFinServicio = LocalDate.parse(fechaFinServicioStr);
			LocalDate fechaLimiteInscripcionServicio = LocalDate.parse(fechaLimiteInscripcionServicioStr);
			LocalTime horaBaseServicio = LocalTime.parse(horaBaseServicioStr);
			LocalTime horaSalidaServicio = LocalTime.parse(horaSalidaServicioStr);
			Integer maximoAsistentesServicio = Integer.parseInt(maximoAsistentesServicioStr);

			ServicioDtos servicioDto = new ServicioDtos();
			servicioDto.setNumeracionServicio(numeracionServicio);
			servicioDto.setTituloServicio(tituloServicio);
			servicioDto.setFechaInicioServicio(fechaInicioServicio);
			servicioDto.setFechaFinServicio(fechaFinServicio);
			servicioDto.setFechaLimiteInscripcionServicio(fechaLimiteInscripcionServicio);
			servicioDto.setHoraBaseServicio(horaBaseServicio);
			servicioDto.setHoraSalidaServicio(horaSalidaServicio);
			servicioDto.setMaximoAsistentesServicio(maximoAsistentesServicio);
			servicioDto.setTipoServicio(tipoServicio);
			servicioDto.setCategoriaServicio(categoriaServicio);
			servicioDto.setDescripcionServicio(descripcionServicio);

			return servicioDto;
		} catch (Exception e) {
			throw new IllegalArgumentException("Error al construir el objeto ServicioDtos: " + e.getMessage());
		}
	}

	/**
	 * Procesa la acción de agregar un servicio.
	 *
	 * @param solicitud     Solicitud HTTP que contiene los datos del servicio.
	 * @param respuesta     Respuesta HTTP para redirigir o informar al cliente.
	 * @param nuevoServicio Objeto ServicioDtos a agregar.
	 * @param idUsuario     ID del usuario que realiza la operación.
	 * @throws ServletException Si ocurre un error de servlet durante el forward.
	 * @throws IOException      Si ocurre un error de E/S durante el forward.
	 *
	 * @author DMN - 19/04/2025
	 */
	private void procesarAgregar(HttpServletRequest solicitud, HttpServletResponse respuesta,
			ServicioDtos nuevoServicio, Long idUsuario) throws ServletException, IOException {
		HttpSession sesion = solicitud.getSession(false);
		Long idOrganizacion = null;
		if (sesion != null) {
			idOrganizacion = (Long) sesion.getAttribute("idOrganizacion");
		}
		if (idOrganizacion == null) {
			registrarError(solicitud, respuesta, "No existe idOrganizacion en la sesión.", idUsuario,
					"procesarAgregar()");
			return;
		}

		try {
			servicio.guardarServicio(nuevoServicio, idOrganizacion);

			solicitud.setAttribute("status", "success");
			solicitud.setAttribute("mensaje", "Servicio añadido correctamente.");
			utilidades.ficheroLog(1, "ServicioServicio - guardarServicio() - Servicio añadido correctamente",
					idUsuario);
			solicitud.getRequestDispatcher(PAGINA_REDIRECT).forward(solicitud, respuesta);
		} catch (Exception e) {
			registrarError(solicitud, respuesta, "Error al añadir servicio: " + e.getMessage(), idUsuario,
					"procesarAgregar()");
		}
	}

	/**
	 * Procesa la acción de modificar un servicio.
	 *
	 * @param solicitud     Solicitud HTTP que contiene los datos actualizados.
	 * @param respuesta     Respuesta HTTP para redirigir o informar al cliente.
	 * @param nuevoServicio Objeto ServicioDtos con datos actualizados.
	 * @param idUsuario     ID del usuario que realiza la operación.
	 * @throws ServletException Si ocurre un error de servlet durante el forward.
	 * @throws IOException      Si ocurre un error de E/S durante el forward.
	 *
	 * @author DMN - 24/03/2025
	 */
	private void procesarModificar(HttpServletRequest solicitud, HttpServletResponse respuesta,
			ServicioDtos nuevoServicio, Long idUsuario) throws ServletException, IOException {
		try {
			String idServicio = solicitud.getParameter("idServicio");
			if (idServicio != null && !idServicio.isEmpty()) {
				boolean actualizado = servicio.modificarServicio(idServicio, nuevoServicio);
				if (actualizado) {
					solicitud.getSession().setAttribute("status", "success");
					solicitud.getSession().setAttribute("mensaje", "Servicio actualizado correctamente.");
					utilidades.ficheroLog(1, "ServicioServicio - modificarServicio() - Servicio con ID " + idServicio
							+ " actualizado correctamente", idUsuario);
				} else {
					solicitud.getSession().setAttribute("status", "error");
					solicitud.getSession().setAttribute("mensaje", "No se pudo actualizar el servicio.");
					utilidades.ficheroLog(3,
							"ServicioServicio - modificarServicio() - No se pudo actualizar el servicio con ID "
									+ idServicio,
							idUsuario);
				}
				String idVehiculo = solicitud.getParameter("idVehiculo");
				if (idVehiculo != null && !idVehiculo.isEmpty()) {
					boolean asignado = vehiculoServicio.asignarVehiculoAServicio(idVehiculo, idServicio);
					if (asignado) {
						solicitud.getSession().setAttribute("mensajeRelacion", "Vehículo asignado correctamente.");
						utilidades.ficheroLog(1, "ServicioServicio - asignarVehiculo() - Vehículo con ID " + idVehiculo
								+ " asignado al servicio " + idServicio, idUsuario);
					} else {
						solicitud.getSession().setAttribute("mensajeRelacion", "Error al asignar vehículo al servicio.");
						utilidades.ficheroLog(3,
								"ServicioServicio - asignarVehiculo() - Error al asignar vehículo con ID " + idVehiculo
										+ " al servicio " + idServicio,
								idUsuario);
					}
				}
			} else {
				solicitud.getSession().setAttribute("status", "error");
				solicitud.getSession().setAttribute("mensaje", "ID de servicio no proporcionado.");
				utilidades.ficheroLog(3, "ServicioServicio - modificarServicio() - ID de servicio no proporcionado",
						idUsuario);
			}

			respuesta.sendRedirect(solicitud.getContextPath() + PAGINA_TABLA);

		} catch (Exception e) {
			registrarError(solicitud, respuesta, "Error al modificar servicio: " + e.getMessage(), idUsuario,
					"procesarModificar()");
		}
	}


	/**
	 * Procesa la acción de eliminar un servicio.
	 *
	 * @param solicitud Solicitud HTTP.
	 * @param respuesta Respuesta HTTP.
	 * @param idUsuario ID del usuario que realiza la operación.
	 * @throws ServletException Si ocurre un error de servlet durante el forward.
	 * @throws IOException      Si ocurre un error de E/S durante el forward.
	 *
	 * @author DMN - 14/04/2025
	 */
	private void procesarEliminar(HttpServletRequest solicitud, HttpServletResponse respuesta, Long idUsuario)
			throws ServletException, IOException {
		try {
			String idServicio = solicitud.getParameter("idServicio");
			if (idServicio != null && !idServicio.isEmpty()) {
				boolean eliminado = servicio.eliminarServicio(idServicio);
				if (eliminado) {
					solicitud.setAttribute("status", "success");
					solicitud.setAttribute("mensaje", "Servicio eliminado correctamente.");
					utilidades.ficheroLog(1, "ServicioServicio - eliminarServicio() - Servicio con ID " + idServicio
							+ " eliminado correctamente", idUsuario);
				} else {
					solicitud.setAttribute("status", "error");
					solicitud.setAttribute("mensaje", "No se pudo eliminar el servicio.");
					utilidades.ficheroLog(3,
							"ServicioServicio - eliminarServicio() - No se pudo eliminar el servicio con ID "
									+ idServicio,
							idUsuario);
				}
			} else {
				solicitud.setAttribute("status", "error");
				solicitud.setAttribute("mensaje", "ID de servicio no proporcionado.");
				utilidades.ficheroLog(3, "ServicioServicio - eliminarServicio() - ID de servicio no proporcionado",
						idUsuario);
			}
			solicitud.getRequestDispatcher(PAGINA_REDIRECT).forward(solicitud, respuesta);
		} catch (Exception e) {
			registrarError(solicitud, respuesta, "Error al eliminar servicio: " + e.getMessage(), idUsuario,
					"procesarEliminar()");
		}
	}

	/**
	 * Envía la lista de servicios en formato JSON.
	 *
	 * @param solicitud       Solicitud HTTP.
	 * @param respuesta       Respuesta HTTP.
	 * @param mapeadorObjetos ObjectMapper configurado con el módulo de JavaTime.
	 * @throws IOException Si ocurre un error de E/S al escribir la respuesta.
	 *
	 * @author DMN - 16/03/2025
	 */
	private void procesarObtenerListaServicios(HttpServletRequest solicitud, HttpServletResponse respuesta,
			ObjectMapper mapeadorObjetos) throws IOException {
		ArrayList<ServicioDtos> lista = servicio.listaServicios(solicitud);
		String jsonRespuesta = mapeadorObjetos.writeValueAsString(lista);
		respuesta.getWriter().write(jsonRespuesta);
	}

	/**
	 * Envía el número total de servicios en formato JSON.
	 *
	 * @param solicitud       Solicitud HTTP.
	 * @param respuesta       Respuesta HTTP.
	 * @param mapeadorObjetos ObjectMapper configurado con el módulo de JavaTime.
	 * @throws IOException Si ocurre un error de E/S al escribir la respuesta.
	 *
	 * @author DMN - 20/04/2025
	 */
	private void procesarObtenerNumeroServicios(HttpServletRequest solicitud, HttpServletResponse respuesta,
			ObjectMapper mapeadorObjetos) throws IOException {
		long numeroServicios = servicio.obtenerTotalServicios(solicitud);
		String json = mapeadorObjetos.writeValueAsString(numeroServicios);
		respuesta.getWriter().write(json);
		utilidades.ficheroLog(1, "doGet: Número de servicios enviado correctamente.", 0);
	}

	/**
	 * Envía un mensaje de error en formato JSON para una acción GET inválida.
	 *
	 * @param respuesta       Respuesta HTTP.
	 * @param mapeadorObjetos ObjectMapper configurado con el módulo de JavaTime.
	 * @param accion          Acción inválida recibida.
	 * @throws IOException Si ocurre un error de E/S al escribir la respuesta.
	 *
	 * @author DMN - 12/03/2025
	 */
	private void procesarAccionInvalida(HttpServletResponse respuesta, ObjectMapper mapeadorObjetos, String accion)
			throws IOException {
		String errorJson = mapeadorObjetos.writeValueAsString("{\"error\": \"Acción no válida\"}");
		respuesta.getWriter().write(errorJson);
		utilidades.ficheroLog(3, "doGet: Acción no válida: " + accion, 0);
	}

	/**
	 * Retorna el valor recibido o un valor por defecto si es nulo o está vacío.
	 *
	 * @param value           Valor recibido.
	 * @param valorPorDefecto Valor por defecto a utilizar.
	 * @return El valor original o el valor por defecto.
	 *
	 * @author DMN - 28/03/2025
	 */
	private String obtenerValorPorDefecto(String value, String valorPorDefecto) {
		return (value == null || value.trim().isEmpty()) ? valorPorDefecto : value;
	}

	/**
	 * Maneja acciones inválidas en solicitudes POST, registrando el error y
	 * redirigiendo al usuario.
	 *
	 * @param solicitud Solicitud HTTP.
	 * @param respuesta Respuesta HTTP.
	 * @param mensaje   Mensaje de error a mostrar.
	 * @param idUsuario ID del usuario que realiza la operación.
	 * @param metodo    Nombre del método donde ocurrió el error.
	 * @throws ServletException Si ocurre un error de servlet durante el forward.
	 * @throws IOException      Si ocurre un error de E/S durante el forward.
	 *
	 * @author DMN - 03/04/2025
	 */
	private void manejarAccionInvalida(HttpServletRequest solicitud, HttpServletResponse respuesta, String mensaje,
			Long idUsuario, String metodo) throws ServletException, IOException {
		solicitud.setAttribute("status", "error");
		solicitud.setAttribute("mensaje", mensaje);
		utilidades.ficheroLog(3, "ServicioServicio - " + metodo + " - " + mensaje, idUsuario);
		solicitud.getRequestDispatcher(PAGINA_REDIRECT).forward(solicitud, respuesta);
	}

	/**
	 * Registra y maneja errores en la solicitud, enviando un mensaje de error a la
	 * vista.
	 *
	 * @param solicitud    Solicitud HTTP.
	 * @param respuesta    Respuesta HTTP.
	 * @param mensajeError Mensaje de error a mostrar.
	 * @param idUsuario    ID del usuario que realiza la operación.
	 * @param metodo       Nombre del método donde ocurrió el error.
	 * @throws ServletException Si ocurre un error de servlet durante el forward.
	 * @throws IOException      Si ocurre un error de E/S durante el forward.
	 *
	 * @author DMN - 25/04/2025
	 */
	private void registrarError(HttpServletRequest solicitud, HttpServletResponse respuesta, String mensajeError,
			Long idUsuario, String metodo) throws ServletException, IOException {
		solicitud.setAttribute("status", "error");
		solicitud.setAttribute("mensaje", mensajeError);
		utilidades.ficheroLog(3, "ServicioServicio - " + metodo + " - " + mensajeError, idUsuario);
		solicitud.getRequestDispatcher(PAGINA_REDIRECT).forward(solicitud, respuesta);
	}

}
