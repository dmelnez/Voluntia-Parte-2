package VistaVoluntia.Controlador;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Base64;

import com.fasterxml.jackson.databind.ObjectMapper;

import VistaVoluntia.Dtos.VehiculoDtos;
import VistaVoluntia.Servicios.VehiculoServicios;
import VistaVoluntia.Utilidades.Utilidades;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

@MultipartConfig
@WebServlet("/vehiculo")
public class VehiculoControlador extends HttpServlet {

    private static final String PAGINA_TABLA = "/RolAdministradorEmpresa/EmpresaAdminTablaVehiculos.html";

	
	private VehiculoServicios vehiculo;
	private Utilidades utilidades;

	/**
	 * Inicializa el controlador de vehículos y los servicios asociados.
	 *
	 * @throws ServletException Si ocurre un error durante la inicialización.
	 *
	 * @author DMN - 05/03/2025
	 */
	@Override
	public void init() throws ServletException {
		this.vehiculo = new VehiculoServicios();
		this.utilidades = new Utilidades();
		utilidades.ficheroLog(1, "VehiculoControlador - init() - Inicialización completada", 0);
	}

	/**
	 * Maneja las peticiones POST para operaciones sobre vehículos: añadir,
	 * modificar y eliminar.
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
		Long idUsuario = (Long) session.getAttribute("idUsuario");
		if (idUsuario == null) {
			idUsuario = 0L;
		}
		try {
			String accion = request.getParameter("accion");
			if (accion == null || accion.isEmpty()) {
				mensajeLog(request, response, 3,
						"VehiculoControlador - doPost() - Acción no válida o parámetros faltantes", "error",
						"Acción no válida o parámetros faltantes.", idUsuario);
				return;
			}
			switch (accion) {
			case "aniadir":
				VehiculoDtos nuevoVehiculo = procesarAniadir(request, idUsuario);
				vehiculo.guardarVehiculo(nuevoVehiculo);
				mensajeLog(request, response, 1, "VehiculoControlador - doPost() - Vehículo añadido correctamente",
						"success", "Vehículo añadido correctamente.", idUsuario);
				break;
			case "modificar":
				String idVehiculo = request.getParameter("idVehiculo");
				if (idVehiculo != null && !idVehiculo.isEmpty()) {
					VehiculoDtos vehiculoDto = procesarModificar(request, idUsuario);
					boolean actualizado = vehiculo.modificarVehiculo(idVehiculo, vehiculoDto);
					if (actualizado) {
						mensajeLog(request, response, 1,
								"VehiculoControlador - doPost() - Vehículo con ID " + idVehiculo
										+ " actualizado correctamente",
								"success", "Vehículo actualizado correctamente.", idUsuario);
					} else {
						mensajeLog(request, response, 3,
								"VehiculoControlador - doPost() - No se pudo actualizar el vehículo con ID "
										+ idVehiculo,
								"error", "No se pudo actualizar el vehículo.", idUsuario);
					}
				} else {
					mensajeLog(request, response, 3,
							"VehiculoControlador - doPost() - ID de Vehículo no proporcionado para modificación",
							"error", "ID de Vehículo no proporcionado.", idUsuario);
				}
				break;
			case "eliminar":
				String idVehiculoEliminar = request.getParameter("idVehiculo");
				if (idVehiculoEliminar != null && !idVehiculoEliminar.isEmpty()) {
					boolean eliminado = vehiculo.eliminarVehiculo(idVehiculoEliminar);
					if (eliminado) {
						mensajeLog(request, response, 1,
								"VehiculoControlador - doPost() - Vehículo con ID " + idVehiculoEliminar
										+ " eliminado correctamente",
								"success", "Vehículo eliminado correctamente.", idUsuario);
					} else {
						mensajeLog(request, response, 3,
								"VehiculoControlador - doPost() - No se pudo eliminar el vehículo con ID "
										+ idVehiculoEliminar,
								"error", "No se pudo eliminar el vehículo.", idUsuario);
					}
				} else {
					mensajeLog(request, response, 3,
							"VehiculoControlador - doPost() - ID de Vehículo no proporcionado para eliminación",
							"error", "ID de Vehículo no proporcionado.", idUsuario);
				}
				break;
			default:
				mensajeLog(request, response, 3,
						"VehiculoControlador - doPost() - Acción no válida o parámetros faltantes", "error",
						"Acción no válida o parámetros faltantes.", idUsuario);
				break;
			}
		} catch (Exception e) {
			request.setAttribute("status", "error");
			request.setAttribute("mensaje", "Se produjo un error interno.");
			utilidades.ficheroLog(3, "VehiculoControlador - doPost() - Error interno: " + e.getMessage(), idUsuario);
			request.getRequestDispatcher("/RolAdministradorEmpresa/EmpresaAdminNuevoVehiculo.jsp").forward(request,
					response);
			e.printStackTrace();
		}
	}

	/**
	 * Procesa los datos recibidos para añadir un nuevo vehículo.
	 *
	 * @param request   La solicitud HTTP con los datos del vehículo.
	 * @param idUsuario ID del usuario que realiza la operación.
	 * @return Un objeto VehiculoDtos con los datos procesados.
	 * @throws Exception Si ocurre un error al parsear o procesar los datos.
	 *
	 * @author DMN - 10/03/2025
	 */
	private VehiculoDtos procesarAniadir(HttpServletRequest request, Long idUsuario) throws Exception {
		try {
			String marcaVehiculo = getValueOrDefault(request.getParameter("marcaVehiculo"), "Marca desconocida");
			String modeloVehiculo = getValueOrDefault(request.getParameter("modeloVehiculo"), "Modelo desconocido");
			LocalDate fechaMatriculacion = LocalDate.parse(request.getParameter("fechaMatriculacionVehiculo"));
			String matriculaVehiculo = getValueOrDefault(request.getParameter("matriculaVehiculo"), "Sin matrícula");
			String combustibleVehiculo = getValueOrDefault(request.getParameter("combustibleVehiculo"),
					"No especificado");
			String tipoVehiculo = getValueOrDefault(request.getParameter("tipoVehiculo"), "No especificado");
			LocalDate proximaIT = LocalDate.parse(request.getParameter("proximaITVehiculo"));
			String indicativoVehiculo = getValueOrDefault(request.getParameter("indicativoVehiculo"), "Ninguno");
			String recursoVehiculo = getValueOrDefault(request.getParameter("recursoVehiculo"), "Ninguno");
			LocalDate vencimientoSeguro = LocalDate.parse(request.getParameter("vencimientoSeguroVehiculo"));
			String tipoCabinaVehiculo = getValueOrDefault(request.getParameter("tipoCabinaVehiculo"),
					"Tipo de cabina no especificado");

			byte[] imagenVehiculoBytes = obtenerImagen(request, true);
			VehiculoDtos nuevoVehiculo = new VehiculoDtos();
			nuevoVehiculo.setMarcaVehiculo(marcaVehiculo);
			nuevoVehiculo.setModeloVehiculo(modeloVehiculo);
			nuevoVehiculo.setFechaMatriculacionVehiculo(fechaMatriculacion);
			nuevoVehiculo.setMatriculaVehiculo(matriculaVehiculo);
			nuevoVehiculo.setCombustibleVehiculo(combustibleVehiculo);
			nuevoVehiculo.setTipoVehiculo(tipoVehiculo);
			nuevoVehiculo.setProximaITVehiculo(proximaIT);
			nuevoVehiculo.setIndicativoVehiculo(indicativoVehiculo);
			nuevoVehiculo.setRecursoVehiculo(recursoVehiculo);
			nuevoVehiculo.setVencimientoSeguroVehiculo(vencimientoSeguro);
			nuevoVehiculo.setTipoCabinaVehiculo(tipoCabinaVehiculo);
			nuevoVehiculo.setImagenVehiculo(imagenVehiculoBytes);

			utilidades.ficheroLog(1,
					"VehiculoControlador - procesarAniadir() - Datos del vehículo procesados correctamente", idUsuario);
			return nuevoVehiculo;
		} catch (Exception ex) {
			utilidades.ficheroLog(3,
					"VehiculoControlador - procesarAniadir() - Error al procesar los datos: " + ex.getMessage(),
					idUsuario);
			throw ex;
		}
	}

	/**
	 * Procesa los datos recibidos para modificar un vehículo existente.
	 *
	 * @param request   La solicitud HTTP con los datos del vehículo.
	 * @param idUsuario ID del usuario que realiza la operación.
	 * @return Un objeto VehiculoDtos con los datos modificados.
	 * @throws Exception Si ocurre un error al parsear o procesar los datos.
	 *
	 * @author DMN - 12/04/2025
	 */
	private VehiculoDtos procesarModificar(HttpServletRequest request, Long idUsuario) throws Exception {
		try {
			String marcaVehiculo = request.getParameter("marcaVehiculo");
			String modeloVehiculo = request.getParameter("modeloVehiculo");
			String matriculaVehiculo = request.getParameter("matriculaVehiculo");
			String combustibleVehiculo = request.getParameter("combustibleVehiculo");
			String tipoVehiculo = request.getParameter("tipoVehiculo");
			String indicativoVehiculo = request.getParameter("indicativoVehiculo");
			String recursoVehiculo = request.getParameter("recursoVehiculo");
			String tipoCabinaVehiculo = request.getParameter("tipoCabinaVehiculo");

			byte[] imagenVehiculoBytes = obtenerImagen(request, false);

			VehiculoDtos vehiculoDto = new VehiculoDtos();
			vehiculoDto.setMarcaVehiculo(marcaVehiculo);
			vehiculoDto.setModeloVehiculo(modeloVehiculo);
			vehiculoDto.setMatriculaVehiculo(matriculaVehiculo);
			vehiculoDto.setCombustibleVehiculo(combustibleVehiculo);
			vehiculoDto.setTipoVehiculo(tipoVehiculo);
			vehiculoDto.setIndicativoVehiculo(indicativoVehiculo);
			vehiculoDto.setRecursoVehiculo(recursoVehiculo);
			vehiculoDto.setTipoCabinaVehiculo(tipoCabinaVehiculo);
			vehiculoDto.setImagenVehiculo(imagenVehiculoBytes);

			utilidades.ficheroLog(1,
					"VehiculoControlador - procesarModificar() - Datos del vehículo para modificación procesados correctamente",
					idUsuario);
			return vehiculoDto;
		} catch (Exception ex) {
			utilidades.ficheroLog(3,
					"VehiculoControlador - procesarModificar() - Error al procesar los datos: " + ex.getMessage(),
					idUsuario);
			throw ex;
		}
	}

	/**
	 * Obtiene la imagen del vehículo de la solicitud. Si se envía imagen, la
	 * procesa; si no, utiliza la imagen actual o una imagen por defecto.
	 *
	 * @param request    La solicitud HTTP.
	 * @param useDefault Indica si se debe usar una imagen por defecto en caso de
	 *                   faltar la imagen.
	 * @return Un arreglo de bytes con la imagen procesada.
	 * @throws IOException      Si ocurre un error de E/S al leer la imagen.
	 * @throws ServletException Si ocurre un error de servlet al procesar el
	 *                          multipart.
	 *
	 * @author DMN - 22/03/2025
	 */
	private byte[] obtenerImagen(HttpServletRequest request, boolean useDefault) throws IOException, ServletException {
		byte[] imagenBytes = null;
		try {
			if (request.getContentType() != null && request.getContentType().toLowerCase().startsWith("multipart/")) {
				Part imagenPart = request.getPart("imagenVehiculo");
				if (imagenPart != null && imagenPart.getSize() > 0) {
					try (InputStream inputStream = imagenPart.getInputStream()) {
						imagenBytes = inputStream.readAllBytes();
					}
				}
			}
			if ((imagenBytes == null || imagenBytes.length == 0)) {
				String imagenActualBase64 = request.getParameter("imagenActual");
				if (imagenActualBase64 != null && !imagenActualBase64.isEmpty()) {
					imagenBytes = Base64.getDecoder().decode(imagenActualBase64);
				} else if (useDefault) {
					String rutaImagen = getServletContext().getRealPath("/Imagenes/FotoVehiculo.jpg");
					imagenBytes = Files.readAllBytes(Paths.get(rutaImagen));
				}
			}
			utilidades.ficheroLog(1, "VehiculoControlador - obtenerImagen() - Imagen procesada correctamente", 0);
		} catch (Exception ex) {
			utilidades.ficheroLog(3,
					"VehiculoControlador - obtenerImagen() - Error al procesar la imagen: " + ex.getMessage(), 0);
			throw ex;
		}
		return imagenBytes;
	}

	/**
	 * Asigna un valor por defecto en caso de que el valor recibido sea nulo o
	 * vacío.
	 *
	 * @param value        El valor recibido.
	 * @param defaultValue El valor por defecto.
	 * @return El valor original o el valor por defecto.
	 *
	 * @author DMN - 30/03/2025
	 */
	private String getValueOrDefault(String value, String defaultValue) {
		return (value == null || value.trim().isEmpty()) ? defaultValue : value;
	}

	/**
	 * Registra un mensaje en el log, establece los atributos de la solicitud y
	 * redirige a la página correspondiente.
	 *
	 * @param request         La solicitud HTTP.
	 * @param response        La respuesta HTTP.
	 * @param nivel           El nivel de log (1 para éxito, 3 para error).
	 * @param mensajeLog      El mensaje a registrar en el log.
	 * @param status          El estado de la respuesta ("success" o "error").
	 * @param mensajeResponse El mensaje que se mostrará en la respuesta.
	 * @param idUsuario       ID del usuario para el log.
	 * @throws ServletException Si ocurre un error de servlet al reenviar.
	 * @throws IOException      Si ocurre un error de E/S al reenviar.
	 *
	 * @author DMN - 15/04/2025
	 */
	private void mensajeLog(HttpServletRequest request, HttpServletResponse response, int nivel, String mensajeLog,
			String status, String mensajeResponse, Long idUsuario) throws ServletException, IOException {
		request.setAttribute("status", status);
		request.setAttribute("mensaje", mensajeResponse);
		utilidades.ficheroLog(nivel, mensajeLog, idUsuario);
		request.getRequestDispatcher("/RolAdministradorEmpresa/EmpresaAdminNuevoVehiculo.jsp").forward(request,
				response);
	}

	/**
	 * Maneja las peticiones GET para obtener la lista de vehículos en formato JSON.
	 *
	 * @param request  La solicitud HTTP.
	 * @param response La respuesta HTTP.
	 * @throws ServletException Si ocurre un error de servlet.
	 * @throws IOException      Si ocurre un error de E/S.
	 *
	 * @author DMN - 25/04/2025
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			ArrayList<VehiculoDtos> listaVehiculos = vehiculo.listaVehiculos(request);

			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");

			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.registerModule(new com.fasterxml.jackson.datatype.jsr310.JavaTimeModule());
			String json = objectMapper.writeValueAsString(listaVehiculos);
			response.getWriter().write(json);
			utilidades.ficheroLog(1, "VehiculoControlador - doGet() - Lista de vehículos enviada correctamente", 0);
		} catch (Exception ex) {
			utilidades.ficheroLog(3,
					"VehiculoControlador - doGet() - Error al obtener la lista de vehículos: " + ex.getMessage(), 0);
			throw new ServletException("Error al obtener la lista de vehículos", ex);
		}
	}
}