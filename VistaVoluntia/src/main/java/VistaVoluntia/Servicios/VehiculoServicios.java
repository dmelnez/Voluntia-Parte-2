package VistaVoluntia.Servicios;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Base64;

import org.json.JSONArray;
import org.json.JSONObject;

import VistaVoluntia.Dtos.VehiculoDtos;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

/**
 * Servicio para la gestión de vehículos. Provee métodos para guardar, listar,
 * eliminar y modificar vehículos.
 * 
 * @author DMN - 14/02/2025
 */
public class VehiculoServicios {

	/**
	 * Guarda un vehículo en la API enviando un JSON que representa el objeto
	 * VehiculoDtos.
	 * 
	 * @param vehiculo El vehículo a guardar.
	 * @author DMN - 14/02/2025
	 */
	public void guardarVehiculo(VehiculoDtos vehiculo) {
		try {
			JSONObject json = new JSONObject();
			json.put("marcaVehiculo", vehiculo.getMarcaVehiculo());
			json.put("modeloVehiculo", vehiculo.getModeloVehiculo());
			json.put("fechaMatriculacionVehiculo", vehiculo.getFechaMatriculacionVehiculo());
			json.put("matriculaVehiculo", vehiculo.getMatriculaVehiculo());
			json.put("combustibleVehiculo", vehiculo.getCombustibleVehiculo());
			json.put("tipoVehiculo", vehiculo.getTipoVehiculo());
			json.put("proximaITVehiculo", vehiculo.getProximaITVehiculo());
			json.put("indicativoVehiculo", vehiculo.getIndicativoVehiculo());
			json.put("recursoVehiculo", vehiculo.getRecursoVehiculo());
			json.put("vencimientoSeguroVehiculo", vehiculo.getVencimientoSeguroVehiculo());
			json.put("tipoCabinaVehiculo", vehiculo.getTipoCabinaVehiculo());
			json.put("imagenVehiculo", vehiculo.getImagenVehiculo());

			JSONObject orgJson = new JSONObject();
			orgJson.put("idOrganizacion", 1);
			json.put("organizacion", orgJson);

			String urlApi = "http://localhost:9526/api/vehiculo/guardarvehiculo";
			URL url = new URL(urlApi);

			HttpURLConnection conex = (HttpURLConnection) url.openConnection();
			conex.setRequestMethod("POST");
			conex.setRequestProperty("Content-Type", "application/json");
			conex.setDoOutput(true);

			try (OutputStream os = conex.getOutputStream()) {
				byte[] input = json.toString().getBytes("utf-8");
				os.write(input, 0, input.length);
			}

			int responseCode = conex.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) {
				System.out.println("Vehiculo guardado correctamente.");
			} else {
				System.out.println("Error al guardar Vehiculo: " + responseCode);
			}
		} catch (Exception e) {
			System.out.println("ERROR- [VehiculoServicio] " + e);
		}
	}

	/**
	 * Obtiene la lista de vehículos desde la API y los convierte en un ArrayList de
	 * VehiculoDtos.
	 * 
	 * @return Una lista de vehículos.
	 * @author DMN - 14/02/2025
	 */
	public ArrayList<VehiculoDtos> listaVehiculos(HttpServletRequest request) {
		ArrayList<VehiculoDtos> lista = new ArrayList<>();
		try {
			HttpSession session = request.getSession(false);
			if (session == null || session.getAttribute("idOrganizacion") == null) {
				System.err.println("No hay sesión o idOrganizacion no disponible.");
				return lista;
			}

			Long idOrganizacion = (Long) session.getAttribute("idOrganizacion");

			String urlApi;
			switch (idOrganizacion.intValue()) {
			case 1:
				urlApi = "http://localhost:9526/api/vehiculo/vehiculos";
				break;
			default:
				urlApi = "http://localhost:9526/api/vehiculo/organizacion/" + idOrganizacion;
				break;
			}

			URL url = new URL(urlApi);
			HttpURLConnection conex = (HttpURLConnection) url.openConnection();
			conex.setRequestMethod("GET");
			conex.setRequestProperty("Accept", "application/json");

			int responseCode = conex.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) {
				BufferedReader in = new BufferedReader(new InputStreamReader(conex.getInputStream()));
				String inputLine;
				StringBuilder response = new StringBuilder();

				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();

				JSONArray jsonlista = new JSONArray(response.toString());
				System.out.println("Vehículos obtenidos: " + jsonlista);

				for (int i = 0; i < jsonlista.length(); i++) {
					JSONObject jsonVehiculo = jsonlista.getJSONObject(i);
					VehiculoDtos vehiculo = new VehiculoDtos();

					vehiculo.setIdVehiculo(jsonVehiculo.getLong("idVehiculo"));
					vehiculo.setMarcaVehiculo(jsonVehiculo.getString("marcaVehiculo"));
					vehiculo.setModeloVehiculo(jsonVehiculo.getString("modeloVehiculo"));
					vehiculo.setFechaMatriculacionVehiculo(
							LocalDate.parse(jsonVehiculo.getString("fechaMatriculacionVehiculo")));
					vehiculo.setMatriculaVehiculo(jsonVehiculo.getString("matriculaVehiculo"));
					vehiculo.setCombustibleVehiculo(jsonVehiculo.getString("combustibleVehiculo"));
					vehiculo.setTipoVehiculo(jsonVehiculo.getString("tipoVehiculo"));
					vehiculo.setProximaITVehiculo(LocalDate.parse(jsonVehiculo.getString("proximaITVehiculo")));
					vehiculo.setIndicativoVehiculo(jsonVehiculo.getString("indicativoVehiculo"));
					vehiculo.setRecursoVehiculo(jsonVehiculo.getString("recursoVehiculo"));
					vehiculo.setVencimientoSeguroVehiculo(
							LocalDate.parse(jsonVehiculo.getString("vencimientoSeguroVehiculo")));
					vehiculo.setTipoCabinaVehiculo(jsonVehiculo.getString("tipoCabinaVehiculo"));

					String imagenVehiculoBase64 = jsonVehiculo.optString("imagenVehiculo", null);
					if (imagenVehiculoBase64 != null && !imagenVehiculoBase64.isEmpty()) {
						try {
							byte[] imagenBytes = Base64.getDecoder().decode(imagenVehiculoBase64);
							vehiculo.setImagenVehiculo(imagenBytes);
						} catch (IllegalArgumentException e) {
							System.out.println("Error al decodificar la imagen Base64.");
							vehiculo.setImagenVehiculo(new byte[0]);
						}
					} else {
						vehiculo.setImagenVehiculo(new byte[0]);
					}

					lista.add(vehiculo);
				}
			} else {
				System.out.println("Error al obtener los Vehiculos. Código de respuesta: " + responseCode);
				try (BufferedReader in = new BufferedReader(new InputStreamReader(conex.getErrorStream()))) {
					String inputLine;
					StringBuilder response = new StringBuilder();
					while ((inputLine = in.readLine()) != null) {
						response.append(inputLine);
					}
					System.out.println("Respuesta de error del servidor: " + response.toString());
				}
			}
		} catch (Exception e) {
			System.out.println("ERROR - VehiculoServicios - ListaVehiculos: " + e);
			e.printStackTrace();
		}
		return lista;
	}

	/**
	 * Elimina un vehículo identificado por su id a través de una petición DELETE a
	 * la API.
	 * 
	 * @param idVehiculo El identificador del vehículo a eliminar.
	 * @return true si la eliminación fue exitosa, false en caso de error.
	 * @author DMN - 14/02/2025
	 */
	public boolean eliminarVehiculo(String idVehiculo) {
		try {
			String urlApi = "http://localhost:9526/api/vehiculo/eliminarvehiculo/" + idVehiculo;
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
	 * Modifica un vehículo existente identificado por su id, enviando los nuevos
	 * datos a la API mediante una petición PUT.
	 * 
	 * @param idVehiculo El identificador del vehículo a modificar.
	 * @param vehiculo   El objeto VehiculoDtos con los nuevos datos.
	 * @return true si la modificación fue exitosa, false en caso de error.
	 * @author DMN - 14/02/2025
	 */
	public boolean modificarVehiculo(String idVehiculo, VehiculoDtos vehiculo) {
		try {
			// Construir el objeto JSON a partir del DTO
			JSONObject json = new JSONObject();
			json.put("marcaVehiculo", vehiculo.getMarcaVehiculo());
			json.put("modeloVehiculo", vehiculo.getModeloVehiculo());
			json.put("matriculaVehiculo", vehiculo.getMatriculaVehiculo());
			json.put("combustibleVehiculo", vehiculo.getCombustibleVehiculo());
			json.put("tipoVehiculo", vehiculo.getTipoVehiculo());
			json.put("indicativoVehiculo", vehiculo.getIndicativoVehiculo());
			json.put("recursoVehiculo", vehiculo.getRecursoVehiculo());
			json.put("tipoCabinaVehiculo", vehiculo.getTipoCabinaVehiculo());

			String urlApi = "http://localhost:9526/api/vehiculo/modificarvehiculo/" + idVehiculo;
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
	 * Apuntar un vehiculo a un servicio, enviando los datos asignar a la API.
	 * 
	 * @param idVehiculo El identificador del vehículo a apuntar al servicio.
	 * @param idServicio El identificador del servicio al que se le va asignar un
	 *                   vehiculo.
	 * @return true si la modificación fue exitosa, false en caso de error.
	 * @author DMN - 18/02/2025
	 */
	public void apuntarVehiculoAServicio(Long vehiculoId, Long servicioId) {
		try {
			JSONObject json = new JSONObject();
			json.put("vehiculoId", vehiculoId);
			json.put("servicioId", servicioId);

			String urlApi = "http://localhost:9526/api/vehiculoServicio/apuntarVehiculoAServicio";
			URL url = new URL(urlApi);

			HttpURLConnection conexion = (HttpURLConnection) url.openConnection();
			conexion.setRequestMethod("POST");
			conexion.setRequestProperty("Content-Type", "application/json");
			conexion.setDoOutput(true);

			try (OutputStream os = conexion.getOutputStream()) {
				byte[] input = json.toString().getBytes("utf-8");
				os.write(input, 0, input.length);
			}

			int responseCode = conexion.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) {
				System.out.println("Vehículo inscrito en el servicio correctamente.");
			} else {
				System.out.println("Error al apuntar el vehículo al servicio. Código de respuesta: " + responseCode);
			}
		} catch (Exception e) {
			System.out.println("ERROR - [apuntarVehiculoAServicio]: " + e);
		}
	}

	public long contarVehiculosEnServicio(Long idServicio) {
		try {
			String urlApi = "http://localhost:9526/api/vehiculo-servicio/contar/" + idServicio;
			URL url = new URL(urlApi);

			HttpURLConnection conex = (HttpURLConnection) url.openConnection();
			conex.setRequestMethod("GET");
			conex.setRequestProperty("Accept", "application/json");

			int responseCode = conex.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) {
				try (BufferedReader in = new BufferedReader(new InputStreamReader(conex.getInputStream()))) {
					String inputLine;
					StringBuilder response = new StringBuilder();
					while ((inputLine = in.readLine()) != null) {
						response.append(inputLine);
					}
					return Long.parseLong(response.toString());
				}
			} else {
				System.out.println("Error en la solicitud. Código de error: " + responseCode);
				return 0;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	/**
	 * Envía una petición a la API para asignar un vehículo a un servicio.
	 * 
	 * @param idVehiculo El ID del vehículo a asignar.
	 * @param idServicio El ID del servicio.
	 * @return true si la asignación fue exitosa, false en caso contrario.
	 */
	public boolean asignarVehiculoAServicio(String idVehiculo, String idServicio) {
		try {
			String urlApi = "http://localhost:9526/api/vehiculo-servicio/inscribir";
			URL url = new URL(urlApi);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("POST");
			con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
			con.setRequestProperty("Accept", "application/json");
			con.setDoOutput(true);

			String data = "vehiculoId=" + URLEncoder.encode(idVehiculo, "UTF-8") + "&servicioId="
					+ URLEncoder.encode(idServicio, "UTF-8");

			try (OutputStream os = con.getOutputStream()) {
				os.write(data.getBytes("UTF-8"));
			}

			int responseCode = con.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) {
				try (BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"))) {
					StringBuilder response = new StringBuilder();
					String responseLine;
					while ((responseLine = br.readLine()) != null) {
						response.append(responseLine.trim());
					}
					System.out.println("Respuesta API (asignar vehículo): " + response.toString());
				}
				return true;
			} else {
				System.out.println("Error asignando vehículo. Código de respuesta: " + responseCode);
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

}
