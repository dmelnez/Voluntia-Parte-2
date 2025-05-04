package VistaVoluntia.Servicios;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;

import VistaVoluntia.Dtos.ServicioDtos;
import VistaVoluntia.Utilidades.Utilidades;

/**
 * Clase que gestiona las operaciones relacionadas con los servicios (guardar,
 * listar, eliminar y modificar) utilizando la API correspondiente.
 * 
 * @author DMN - 14/02/2025
 */
public class ServicioServicios {

	private Utilidades utilidades;

	/**
	 * Guarda un servicio en la API enviando un JSON construido a partir del objeto
	 * ServicioDtos.
	 * 
	 * @param servicio El objeto ServicioDtos que se desea guardar.
	 * @author DMN - 14/02/2025
	 */
	public void guardarServicio(ServicioDtos servicio, Long idOrganizacion) {
		try {
			JSONObject json = new JSONObject();
			json.put("numeracionServicio", servicio.getNumeracionServicio());
			json.put("tituloServicio", servicio.getTituloServicio());
			json.put("fechaInicioServicio", servicio.getFechaInicioServicio());
			json.put("fechaFinServicio", servicio.getFechaFinServicio());
			json.put("fechaLimiteInscripcionServicio", servicio.getFechaLimiteInscripcionServicio());
			json.put("horaBaseServicio", servicio.getHoraBaseServicio());
			json.put("horaSalidaServicio", servicio.getHoraSalidaServicio());
			json.put("maximoAsistentesServicio", servicio.getMaximoAsistentesServicio());
			json.put("tipoServicio", servicio.getTipoServicio());
			json.put("categoriaServicio", servicio.getCategoriaServicio());
			json.put("descripcionServicio", servicio.getDescripcionServicio());

			JSONObject orgJson = new JSONObject();
			orgJson.put("idOrganizacion", idOrganizacion);
			json.put("organizacion", orgJson);

			String urlApi = "http://localhost:9526/api/servicio/guardarservicio";
			URL url = new URL(urlApi);
			HttpURLConnection conex = (HttpURLConnection) url.openConnection();
			conex.setRequestMethod("POST");
			conex.setRequestProperty("Content-Type", "application/json");
			conex.setDoOutput(true);

			try (OutputStream os = conex.getOutputStream()) {
				byte[] input = json.toString().getBytes(StandardCharsets.UTF_8);
				os.write(input, 0, input.length);
			}

			int responseCode = conex.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) {
				System.out.println("✅ Servicio guardado correctamente.");
			} else {
				System.out.println("❌ Error al guardar Servicio: " + responseCode);
			}
			conex.disconnect();
		} catch (Exception e) {
			System.out.println("❌ ERROR - [ServiciosServicio] " + e.getMessage());
		}
	}

	/**
	 * Obtiene la lista de servicios desde la API y la convierte a un ArrayList de
	 * ServicioDtos.
	 * 
	 * @return Un ArrayList con todos los servicios obtenidos.
	 * @author DMN - 14/02/2025
	 */
	public ArrayList<ServicioDtos> listaServicios(HttpServletRequest request) {
		ArrayList<ServicioDtos> lista = new ArrayList<>();

		try {
			HttpSession session = request.getSession(false);
			if (session == null || session.getAttribute("idOrganizacion") == null) {
				System.err.println("No hay sesión o idOrganizacion no disponible.");
				return lista;
			}

			Long idOrganizacion = (Long) session.getAttribute("idOrganizacion");

			// Selección dinámica de URL según idOrganizacion
			String urlApi;
			switch (idOrganizacion.intValue()) {
			case 1:
				urlApi = "http://localhost:9526/api/servicio/servicios";
				break;
			default:
				urlApi = "http://localhost:9526/api/servicio/organizacion/" + idOrganizacion;
				break;
			}

			URL url = new URL(urlApi);
			HttpURLConnection conex = (HttpURLConnection) url.openConnection();
			conex.setRequestMethod("GET");
			conex.setRequestProperty("Accept", "application/json");

			int responseCode = conex.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) {
				try (BufferedReader in = new BufferedReader(new InputStreamReader(conex.getInputStream(), "utf-8"))) {
					String inputLine;
					StringBuilder response = new StringBuilder();
					while ((inputLine = in.readLine()) != null) {
						response.append(inputLine);
					}

					JSONArray jsonlista = new JSONArray(response.toString());
					System.out.println("Servicios obtenidos: " + jsonlista);

					for (int i = 0; i < jsonlista.length(); i++) {
						JSONObject jsonServicio = jsonlista.getJSONObject(i);
						ServicioDtos servicio = new ServicioDtos();

						servicio.setIdServicio(jsonServicio.getLong("idServicio"));
						servicio.setNumeracionServicio(jsonServicio.optString("numeracionServicio"));
						servicio.setTituloServicio(jsonServicio.optString("tituloServicio"));

						String fechaInicioServicioStr = jsonServicio.optString("fechaInicioServicio");
						if (!fechaInicioServicioStr.isEmpty()) {
							servicio.setFechaInicioServicio(LocalDate.parse(fechaInicioServicioStr));
						}

						String fechaFinServicioStr = jsonServicio.optString("fechaFinServicio");
						if (!fechaFinServicioStr.isEmpty()) {
							servicio.setFechaFinServicio(LocalDate.parse(fechaFinServicioStr));
						}

						String fechaLimiteInscripcionServicioStr = jsonServicio
								.optString("fechaLimiteInscripcionServicio");
						if (!fechaLimiteInscripcionServicioStr.isEmpty()) {
							servicio.setFechaLimiteInscripcionServicio(
									LocalDate.parse(fechaLimiteInscripcionServicioStr));
						}

						String horaBaseServicioStr = jsonServicio.optString("horaBaseServicio");
						if (!horaBaseServicioStr.isEmpty()) {
							servicio.setHoraBaseServicio(LocalTime.parse(horaBaseServicioStr));
						}

						String horaSalidaServicioStr = jsonServicio.optString("horaSalidaServicio");
						if (!horaSalidaServicioStr.isEmpty()) {
							servicio.setHoraSalidaServicio(LocalTime.parse(horaSalidaServicioStr));
						}

						String maximoAsistentesServicioStr = jsonServicio.optString("maximoAsistentesServicio");
						if (!maximoAsistentesServicioStr.isEmpty()) {
							servicio.setMaximoAsistentesServicio(Integer.parseInt(maximoAsistentesServicioStr));
						}

						servicio.setTipoServicio(jsonServicio.optString("tipoServicio"));
						servicio.setCategoriaServicio(jsonServicio.optString("categoriaServicio"));
						servicio.setDescripcionServicio(jsonServicio.optString("descripcionServicio"));

						lista.add(servicio);
					}
				}
			} else {
				System.out.println("Error al obtener los Servicios. Código de respuesta: " + responseCode);
				try (BufferedReader in = new BufferedReader(new InputStreamReader(conex.getErrorStream(), "utf-8"))) {
					String inputLine;
					StringBuilder response = new StringBuilder();
					while ((inputLine = in.readLine()) != null) {
						response.append(inputLine);
					}
					System.out.println("Respuesta de error del servidor: " + response.toString());
				}
			}
		} catch (Exception e) {
			System.out.println("ERROR - ServicioService - listaServicios: " + e);
			e.printStackTrace();
		}

		System.out.println("Número total de servicios: " + lista.size());
		return lista;
	}

	/**
	 * Elimina un servicio identificado por su ID mediante una petición DELETE a la
	 * API.
	 * 
	 * @param idServicio El identificador del servicio a eliminar.
	 * @return {@code true} si la eliminación fue exitosa, {@code false} en caso
	 *         contrario.
	 * @author DMN - 14/02/2025
	 */
	public boolean eliminarServicio(String idServicio) {
		try {
			String urlApi = "http://localhost:9526/api/servicio/eliminarservicio/" + idServicio;
			URL url = new URL(urlApi);

			HttpURLConnection conex = (HttpURLConnection) url.openConnection();
			conex.setRequestMethod("DELETE");
			conex.setRequestProperty("Content-Type", "application/json");

			int responseCode = conex.getResponseCode();
			return responseCode == HttpURLConnection.HTTP_OK;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Modifica un servicio existente identificado por su ID, enviando los nuevos
	 * datos a la API mediante una petición PUT.
	 * 
	 * @param idServicio El identificador del servicio a modificar.
	 * @param servicio   Objeto ServicioDtos con los datos actualizados.
	 * @return {@code true} si la modificación fue exitosa, {@code false} en caso de
	 *         error.
	 * @author DMN - 14/02/2025
	 */
	public boolean modificarServicio(String idServicio, ServicioDtos servicio) {
		try {
			JSONObject json = new JSONObject();
			json.put("numeracionServicio", servicio.getNumeracionServicio());
			json.put("tituloServicio", servicio.getTituloServicio());
			json.put("fechaInicioServicio", servicio.getFechaInicioServicio());
			json.put("fechaFinServicio", servicio.getFechaFinServicio());
			json.put("fechaLimiteInscripcionServicio", servicio.getFechaLimiteInscripcionServicio());
			json.put("horaBaseServicio", servicio.getHoraBaseServicio());
			json.put("horaSalidaServicio", servicio.getHoraSalidaServicio());
			json.put("maximoAsistentesServicio", servicio.getMaximoAsistentesServicio());
			json.put("tipoServicio", servicio.getTipoServicio());
			json.put("categoriaServicio", servicio.getCategoriaServicio());
			json.put("descripcionServicio", servicio.getDescripcionServicio());

			String urlApi = "http://localhost:9526/api/servicio/modificarservicio/" + idServicio;
			URL url = new URL(urlApi);

			HttpURLConnection conex = (HttpURLConnection) url.openConnection();
			conex.setRequestMethod("PUT");
			conex.setRequestProperty("Content-Type", "application/json");
			conex.setDoOutput(true);

			try (OutputStream os = conex.getOutputStream()) {
				byte[] input = json.toString().getBytes("utf-8");
				os.write(input, 0, input.length);
			}

			int responseCode = conex.getResponseCode();
			return responseCode == HttpURLConnection.HTTP_OK;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Obtiene el número total de servicios desde la API.
	 *
	 * @return El número total de servicios.
	 * @author DMN - 01/02/2025
	 */
	public long obtenerTotalServicios(HttpServletRequest request) {
		long totalServicios = 0;
		HttpSession session = request.getSession(false);
		if (session == null) {
			System.err.println("No hay sesión activa.");
			return 0;
		}

		Long idOrganizacion = (Long) session.getAttribute("idOrganizacion");
		if (idOrganizacion == null) {
			System.err.println("La sesión no contiene idOrganizacion.");
			return 0;
		}

		HttpURLConnection conex = null;
		try {
			String urlApi = (idOrganizacion == 1L) ? "http://localhost:9526/api/servicio/total"
					: "http://localhost:9526/api/servicio/total/" + idOrganizacion;

			URL url = new URL(urlApi);
			conex = (HttpURLConnection) url.openConnection();
			conex.setRequestMethod("GET");
			conex.setRequestProperty("Accept", "application/json");

			int code = conex.getResponseCode();
			if (code == HttpURLConnection.HTTP_OK) {
				try (BufferedReader in = new BufferedReader(
						new InputStreamReader(conex.getInputStream(), StandardCharsets.UTF_8))) {
					String line = in.readLine();
					totalServicios = Long.parseLong(line.trim());
					System.out.println("Total servicios (org " + idOrganizacion + "): " + totalServicios);
				}
			} else {
				System.err.println("Error al obtener servicios. Código HTTP: " + code);
			}
		} catch (Exception e) {
			System.err.println("ERROR - ServicioService - obtenerTotalServicios: " + e.getMessage());
			e.printStackTrace();
		} finally {
			if (conex != null)
				conex.disconnect();
		}
		return totalServicios;
	}
}