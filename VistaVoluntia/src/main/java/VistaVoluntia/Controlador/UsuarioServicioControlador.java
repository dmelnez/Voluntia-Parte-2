package VistaVoluntia.Controlador;

import VistaVoluntia.Servicios.UsuarioServicioServicios;
import VistaVoluntia.Utilidades.Utilidades;
import jakarta.persistence.Query;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import org.bouncycastle.est.ESTException;

import com.fasterxml.jackson.databind.ObjectMapper;

@WebServlet("/inscripcion")
public class UsuarioServicioControlador extends HttpServlet {

	private UsuarioServicioServicios usuarioServicioServicios;
	private Utilidades utilidades;

	/**
	 * Inicializa el servlet y sus dependencias.
	 * 
	 * @author DMN - 22/02/2025
	 */
	@Override
	public void init() throws ServletException {
		this.usuarioServicioServicios = new UsuarioServicioServicios();
		this.utilidades = new Utilidades();
		utilidades.ficheroLog(1, "UsuarioServicioControlador - init() - Inicialización completada", 0);
	}

	/**
	 * Procesa la solicitud POST.
	 * 
	 * @author DMN - 22/02/2025
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String accion = request.getParameter("accion");

		if (accion != null) {
			if (accion.equals("inscribir")) {
				procesarInscripcion(request, response);
			} else if (accion.equals("contar")) {
				procesarConteoUsuarios(request, response);
			} else {
				response.getWriter().write("Acción no reconocida.");
				utilidades.ficheroLog(2, "UsuarioServicioControlador - doPost() - Acción no reconocida", 0);
			}
		} else {
			response.getWriter().write("Acción no válida o parámetros faltantes.");
			utilidades.ficheroLog(3, "UsuarioServicioControlador - doPost() - Acción no válida o parámetros faltantes",
					0);
		}
	}

	/**
	 * Procesa la inscripción de un usuario en un servicio. Obtiene los parámetros
	 * necesarios, realiza la conversión a Long, llama al servicio de inscripción y
	 * envía la respuesta correspondiente.
	 * 
	 * @param request  La solicitud HTTP.
	 * @param response La respuesta HTTP.
	 * @throws ServletException
	 * @throws IOException
	 * 
	 * @author DMN - 22/02/2025
	 */
	private void procesarInscripcion(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			String idServicio = request.getParameter("idServicio");
			String idUsuario = request.getParameter("idUsuario");
			if (idServicio == null || idUsuario == null) {
				response.getWriter().write("Faltan parámetros para la inscripción.");
				utilidades.ficheroLog(3,
						"UsuarioServicioControlador - procesarInscripcion() - Faltan parámetros para la inscripción",
						0);
				return;
			}

			Long idServicioLong = Long.parseLong(idServicio);
			Long idUsuarioLong = Long.parseLong(idUsuario);

			boolean inscripcionExitosa = usuarioServicioServicios.inscribirUsuarioEnServicio(idServicioLong,
					idUsuarioLong);

			if (inscripcionExitosa) {
				response.getWriter().write("Usuario inscrito exitosamente.");
				utilidades.ficheroLog(1, "UsuarioServicioControlador - procesarInscripcion() - Usuario " + idUsuarioLong
						+ " inscrito en el servicio " + idServicioLong + " exitosamente", idUsuarioLong);
			} else {
				response.getWriter().write("Error al inscribir al usuario.");
				utilidades.ficheroLog(3,
						"UsuarioServicioControlador - procesarInscripcion() - Error al inscribir al usuario "
								+ idUsuarioLong + " en el servicio " + idServicioLong,
						idUsuarioLong);
			}
		} catch (NumberFormatException e) {
			response.getWriter().write("Parámetros inválidos: los IDs deben ser números.");
			utilidades.ficheroLog(3,
					"UsuarioServicioControlador - procesarInscripcion() - Parámetros inválidos: " + e.getMessage(), 0);
		}
	}

	/**
	 * Procesa la acción de contar los usuarios inscritos en un servicio mediante
	 * POST. Obtiene el parámetro idServicio, lo convierte a Long y llama al
	 * servicio correspondiente.
	 * 
	 * @param request  La solicitud HTTP.
	 * @param response La respuesta HTTP.
	 * @throws ServletException
	 * @throws IOException
	 * 
	 * @author DMN - 22/02/2025
	 */
	private void procesarConteoUsuarios(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			String idServicio = request.getParameter("idServicio");

			if (idServicio == null) {
				response.getWriter().write("Falta el parámetro idServicio.");
				utilidades.ficheroLog(3,
						"UsuarioServicioControlador - procesarConteoUsuarios() - Falta el parámetro idServicio", 0);
				return;
			}

			Long idServicioLong = Long.parseLong(idServicio);

			long cantidadUsuarios = usuarioServicioServicios.contarUsuariosEnServicio(idServicioLong);

			response.getWriter().write(String.valueOf(cantidadUsuarios));
			utilidades.ficheroLog(1, "UsuarioServicioControlador - procesarConteoUsuarios() - Contado "
					+ cantidadUsuarios + " usuarios en el servicio " + idServicioLong, 0);
		} catch (NumberFormatException e) {
			response.getWriter().write("Parámetro inválido: el ID del servicio debe ser un número.");
			utilidades.ficheroLog(3,
					"UsuarioServicioControlador - procesarConteoUsuarios() - Parámetro inválido: " + e.getMessage(), 0);
		}
	}

	/**
	 * Procesa la solicitud GET para contar los usuarios inscritos en un servicio y
	 * retorna la respuesta en formato JSON.
	 * 
	 * @param request  La solicitud HTTP.
	 * @param response La respuesta HTTP.
	 * @throws ServletException
	 * @throws IOException
	 * 
	 * @author DMN - 22/02/2025
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		procesarConteoUsuariosGET(request, response);
	}

	/**
	 * Método privado que procesa la solicitud GET para contar los usuarios
	 * inscritos en un servicio y retorna la respuesta en formato JSON.
	 * 
	 * @param request  La solicitud HTTP.
	 * @param response La respuesta HTTP.
	 * @throws ServletException
	 * @throws IOException
	 * 
	 * @author DMN - 22/02/2025
	 */
	private void procesarConteoUsuariosGET(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			String idServicio = request.getParameter("idServicio");

			if (idServicio == null) {
				response.getWriter().write("Falta el parámetro idServicio.");
				utilidades.ficheroLog(3, "UsuarioServicioControlador - doGet() - Falta el parámetro idServicio", 0);
				return;
			}

			Long idServicioLong = Long.parseLong(idServicio);
			long cantidadUsuarios = usuarioServicioServicios.contarUsuariosEnServicio(idServicioLong);

			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");

			ObjectMapper objectMapper = new ObjectMapper();
			String jsonResponse = objectMapper.writeValueAsString(cantidadUsuarios);

			response.getWriter().write(jsonResponse);
			utilidades.ficheroLog(1, "UsuarioServicioControlador - doGet() - Contado " + cantidadUsuarios
					+ " usuarios en el servicio " + idServicioLong, 0);
		} catch (NumberFormatException e) {
			response.getWriter().write("El ID del servicio debe ser un número válido.");
			utilidades.ficheroLog(3,
					"UsuarioServicioControlador - doGet() - Parámetro idServicio inválido: " + e.getMessage(), 0);
		}
	}
}
