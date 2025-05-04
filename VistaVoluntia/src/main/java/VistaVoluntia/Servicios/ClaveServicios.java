package VistaVoluntia.Servicios;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.json.JSONArray;
import org.json.JSONObject;

import ApiVoluntia.ApiVoluntia.Dtos.ClaveDtos;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class ClaveServicios {

	/**
	 * Guarda un cargo enviando una solicitud POST a la API.
	 * 
	 * @param cargo Objeto CargoDtos que contiene los datos del cargo a guardar.
	 * @author DMN - 27/03/2025
	 */
	public void guardarClave(ClaveDtos clave, HttpServletRequest request) {
		try {
			HttpSession session = request.getSession(false);
			if (session == null || session.getAttribute("idOrganizacion") == null) {
				System.err.println("No hay sesión o idOrganizacion no disponible.");
				return;
			}

			Long idOrganizacion = (Long) session.getAttribute("idOrganizacion");

			JSONObject json = new JSONObject();
			json.put("nombreClave", clave.getNombreClave());
			json.put("descripcionClave", clave.getDescripcionClave());

			JSONObject orgJson = new JSONObject();
			orgJson.put("idOrganizacion", idOrganizacion);
			json.put("organizacion", orgJson);

			String urlApi = "http://localhost:9526/api/clave/guardarclave";
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
			if (responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_CREATED) {
				System.out.println("✅ Clave guardada correctamente.");
			} else {
				System.out.println("❌ Error al guardar clave: " + responseCode);
			}
		} catch (Exception e) {
			System.out.println("ERROR- [ServicioClave] " + e);
			e.printStackTrace();
		}
	}

	/**
	 * Obtiene la lista de emergencias desde la API.
	 *
	 * @return Lista de objetos EmergenciaDtos.
	 * @author DMN - 14/02/2025
	 */
	public ArrayList<ClaveDtos> listaClaves(HttpServletRequest request) {
		ArrayList<ClaveDtos> lista = new ArrayList<>();

		try {
			HttpSession session = request.getSession(false);
			if (session == null || session.getAttribute("idOrganizacion") == null) {
				System.err.println("No hay sesión activa o idOrganizacion no está disponible.");
				return lista;
			}

			Long idOrganizacion = (Long) session.getAttribute("idOrganizacion");

			String urlApi;
			switch (idOrganizacion.intValue()) {
			case 1:
				urlApi = "http://localhost:9526/api/clave/claves";
				break;
			default:
				urlApi = "http://localhost:9526/api/clave/organizacion/" + idOrganizacion;
				break;
			}

			// Realizar la petición
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
				System.out.println("Claves obtenidas: " + jsonlista);

				for (int i = 0; i < jsonlista.length(); i++) {
					JSONObject jsonClaves = jsonlista.getJSONObject(i);
					ClaveDtos claves = new ClaveDtos();

					claves.setIdClave(jsonClaves.getLong("idClave"));
					claves.setNombreClave(jsonClaves.getString("nombreClave"));
					claves.setDescripcionClave(jsonClaves.optString("descripcionClave"));

					lista.add(claves);
				}

			} else {
				System.out.println("Error al obtener las Claves. Código de respuesta: " + responseCode);
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
			System.out.println("ERROR - ClaveServicios - ListaClaves: " + e);
			e.printStackTrace();
		}

		return lista;
	}

	/**
	 * Elimina la emergencia con el identificador especificado.
	 *
	 * @param idEmergencia Identificador de la emergencia a eliminar.
	 * @return true si la eliminación fue exitosa, false en caso contrario.
	 * @author DMN - 14/02/2025
	 */
	public boolean eliminarClave(String idClave) {
		try {
			String urlApi = "http://localhost:9526/api/clave/eliminarclave/" + idClave;
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
	 * Modifica los detalles de una emergencia existente.
	 *
	 * @param idEmergencia Identificador de la emergencia a modificar.
	 * @param emergencia   Objeto EmergenciaDtos con los nuevos datos de la
	 *                     emergencia.
	 * @return true si la modificación fue exitosa, false en caso contrario.
	 * @author DMN - 14/02/2025
	 */
	public boolean modificarClave(String idClave, ClaveDtos clave) {
		try {
			JSONObject json = new JSONObject();

			json.put("nombreClave", clave.getNombreClave());
			json.put("descripcionClave", clave.getDescripcionClave());

			String urlApi = "http://localhost:9526/api/clave/modificarclave/" + idClave;
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
	 * Obtiene el número total de emergencias desde la API.
	 *
	 * @return Número total de emergencias.
	 * @author DMN - 14/02/2025
	 */
	public long obtenerTotalClaves() {
		long totalClaves = 0;

		try {
			String urlApi = "http://localhost:9526/api/clave/total";
			URL url = new URL(urlApi);
			HttpURLConnection conex = (HttpURLConnection) url.openConnection();
			conex.setRequestMethod("GET");
			conex.setRequestProperty("Accept", "application/json");

			int responseCode = conex.getResponseCode();

			if (responseCode == HttpURLConnection.HTTP_OK) {
				BufferedReader in = new BufferedReader(new InputStreamReader(conex.getInputStream()));
				String response = in.readLine();
				in.close();
				totalClaves = Long.parseLong(response.trim());

				System.out.println(totalClaves);

			} else {
				System.out.println("Error al obtener claves. Código de respuesta: " + responseCode);
			}

		} catch (Exception e) {
			System.out.println("ERROR - ClaveServicios - obtenerTotalClaves: " + e.getMessage());
			e.printStackTrace();
		}

		return totalClaves;
	}

}
