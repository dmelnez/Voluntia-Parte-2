package VistaVoluntia.Servicios;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Servicio para gestionar la inscripción de usuarios en servicios y el conteo
 * de usuarios inscritos.
 * <p>
 * Provee métodos para inscribir a un usuario en un servicio y para contar la
 * cantidad de usuarios inscritos.
 * </p>
 * 
 * @author DMN - 14/02/2025
 */
public class UsuarioServicioServicios {

	/**
	 * Inscribe a un usuario en un servicio enviando una petición POST a la API.
	 * <p>
	 * Se construye la URL con los parámetros en la query string y se envía la
	 * solicitud. La respuesta se analiza para determinar si la inscripción fue
	 * exitosa.
	 * </p>
	 *
	 * @param idServicio El identificador del servicio.
	 * @param idUsuario  El identificador del usuario.
	 * @return {@code true} si la inscripción fue exitosa, {@code false} en caso
	 *         contrario.
	 */
	public boolean inscribirUsuarioEnServicio(Long idServicio, Long idUsuario) {
		try {
			String urlApi = "http://localhost:9526/api/usuario-servicio/inscribir?usuarioId=" + idUsuario
					+ "&servicioId=" + idServicio;
			URL url = new URL(urlApi);

			HttpURLConnection conex = (HttpURLConnection) url.openConnection();
			conex.setRequestMethod("POST");
			conex.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			conex.setDoOutput(true);

			int responseCode = conex.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) {
				try (BufferedReader in = new BufferedReader(new InputStreamReader(conex.getInputStream()))) {
					String inputLine;
					StringBuilder response = new StringBuilder();
					while ((inputLine = in.readLine()) != null) {
						response.append(inputLine);
					}
					return response.toString().toLowerCase().contains("con éxito");
				}
			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Cuenta la cantidad de usuarios inscritos en un servicio mediante una petición
	 * GET a la API.
	 * <p>
	 * La API puede retornar la cantidad de usuarios en formato JSON (por ejemplo,
	 * {"count":5}) o como un número en formato texto. Este método intenta parsear
	 * la respuesta adecuadamente.
	 * </p>
	 *
	 * @param idServicio El identificador del servicio.
	 * @return El número de usuarios inscritos o 0 en caso de error.
	 */
	public long contarUsuariosEnServicio(Long idServicio) {
		try {
			String urlApi = "http://localhost:9526/api/usuario-servicio/contar/" + idServicio;
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
					String resStr = response.toString().trim();
					if (resStr.startsWith("{")) {
						ObjectMapper mapper = new ObjectMapper();
						JsonNode root = mapper.readTree(resStr);
						if (root.has("count")) {
							return root.get("count").asLong();
						} else {
							return Long.parseLong(resStr);
						}
					} else {
						return Long.parseLong(resStr);
					}
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
}
