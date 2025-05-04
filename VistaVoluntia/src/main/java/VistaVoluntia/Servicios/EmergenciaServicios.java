package VistaVoluntia.Servicios;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
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

import VistaVoluntia.Dtos.EmergenciaDtos;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class EmergenciaServicios {

	/**
	 * Envía un correo con el asunto y mensaje proporcionados al correoUsuario.
	 *
	 * @param correoUsuario Correo destinatario.
	 * @param asunto        Asunto del correo.
	 * @param mensaje       Contenido del correo en formato HTML.
	 * @author DMN - 14/02/2025
	 */
	public static void enviarCorreo(String correoUsuario, String asunto, String mensaje) {
		String host = "smtp.gmail.com";
		final String username = "voluntiaatencion@gmail.com";
		final String password = "jxnkoqhlppagaivt";

		Properties properties = System.getProperties();
		properties.put("mail.smtp.host", host);
		properties.put("mail.smtp.port", "587");
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");

		Session session = Session.getInstance(properties, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		try {
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(username));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(correoUsuario));
			message.setSubject(asunto);

			MimeBodyPart htmlPart = new MimeBodyPart();
			htmlPart.setContent(mensaje, "text/html; charset=utf-8");

			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(htmlPart);

			message.setContent(multipart);

			Transport.send(message);
			System.out.println("Correo enviado con éxito.");
		} catch (MessagingException mex) {
			mex.printStackTrace();
		}
	}

	/**
	 * Envía correos a todos los usuarios contenidos en la lista de correos.
	 *
	 * @param correos Lista de correos destinatarios.
	 * @param asunto  Asunto del correo.
	 * @param mensaje Mensaje en formato HTML.
	 * @author DMN - 14/02/2025
	 */
	public void enviarCorreosATodos(List<String> correos, String asunto, String mensaje) {
		for (String correo : correos) {
			enviarCorreo(correo, asunto, mensaje);
		}
	}

	/**
	 * Obtiene una lista de correos desde la API.
	 *
	 * @return Lista de correos obtenidos de la API.
	 * @throws IOException Si ocurre un error durante la comunicación con la API.
	 * @author DMN - 14/02/2025
	 */
	public List<String> obtenerCorreosDesdeAPI(HttpServletRequest request) throws IOException {
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("idOrganizacion") == null) {
			System.err.println("No hay sesión o idOrganizacion no disponible.");
			return Collections.emptyList();
		}
		Long idOrganizacion = (Long) session.getAttribute("idOrganizacion");

		String endpoint = "http://localhost:9526/api/usuario/correos/" + idOrganizacion;
		URL url = new URL(endpoint);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("GET");

		List<String> correos = new ArrayList<>();
		try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
			StringBuilder response = new StringBuilder();
			String inputLine;
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			String[] arr = response.toString().replaceAll("[\\[\\]\"]", "").split(",");
			for (String correo : arr) {
				if (!correo.trim().isEmpty()) {
					correos.add(correo.trim());
				}
			}
		} catch (IOException e) {
			System.err.println("Error leyendo correos desde API: " + e.getMessage());
			throw e;
		}

		return correos;
	}

	/**
	 * Guarda una emergencia en el sistema para el usuario especificado.
	 *
	 * @param emergencia Objeto EmergenciaDtos con los datos de la emergencia.
	 * @param idUsuario  Identificador del usuario.
	 * @author DMN - 14/02/2025
	 */
	public void guardarEmergencia(EmergenciaDtos emergencia, HttpServletRequest request) {
		try {
			HttpSession session = request.getSession(false);
			if (session == null || session.getAttribute("idOrganizacion") == null) {
				System.err.println("No hay sesión activa o falta idOrganizacion.");
				return;
			}

			Long idOrganizacion = (Long) session.getAttribute("idOrganizacion");
			JSONObject json = new JSONObject();
			json.put("tituloEmergencia", emergencia.getTituloEmergencia());
			json.put("categoriaEmergencia", emergencia.getCategoriaEmergencia());
			json.put("descripcionEmergencia", emergencia.getDescripcionEmergencia());
			json.put("fechaLanzamientoEmergencia", emergencia.getFechaLanzamientoEmergencia());
			json.put("horaLanzamientoEmergencia", emergencia.getHoraLanzamientoEmergencia());
			JSONObject orgJson = new JSONObject();
			orgJson.put("idOrganizacion", idOrganizacion);
			json.put("organizacion", orgJson);

			String urlApi = "http://localhost:9526/api/emergencia/guardaremergencia";
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
				System.out.println("✅ Emergencia guardada correctamente.");
			} else {
				System.out.println("❌ Error al guardar la Emergencia: " + responseCode);
			}

		} catch (Exception e) {
			System.out.println("ERROR- [EmergenciaServicio]: " + e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * Obtiene la lista de emergencias desde la API.
	 *
	 * @return Lista de objetos EmergenciaDtos.
	 * @author DMN - 14/02/2025
	 */
	public ArrayList<EmergenciaDtos> listaEmergencias(HttpServletRequest request) {
		ArrayList<EmergenciaDtos> lista = new ArrayList<>();

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
				urlApi = "http://localhost:9526/api/emergencia/emergencias";
				break;
			default:
				urlApi = "http://localhost:9526/api/emergencia/organizacion/" + idOrganizacion;
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
				System.out.println("Emergencias obtenidas: " + jsonlista);

				for (int i = 0; i < jsonlista.length(); i++) {
					JSONObject jsonEmergencia = jsonlista.getJSONObject(i);
					EmergenciaDtos emergencia = new EmergenciaDtos();

					emergencia.setIdEmergencia(jsonEmergencia.getLong("idEmergencia"));
					emergencia.setTituloEmergencia(jsonEmergencia.getString("tituloEmergencia"));
					emergencia.setCategoriaEmergencia(jsonEmergencia.optString("categoriaEmergencia"));
					emergencia.setDescripcionEmergencia(jsonEmergencia.optString("descripcionEmergencia"));
					emergencia.setFechaLanzamientoEmergencia(
							LocalDate.parse(jsonEmergencia.optString("fechaLanzamientoEmergencia")));
					emergencia.setHoraLanzamientoEmergencia(
							LocalTime.parse(jsonEmergencia.optString("horaLanzamientoEmergencia")));

					lista.add(emergencia);
				}

			} else {
				System.out.println("Error al obtener las Emergencias. Código de respuesta: " + responseCode);
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
			System.out.println("ERROR - ServiciosEmergencias - ListaEmergencias: " + e);
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
	public boolean eliminarEmergencia(String idEmergencia) {
		try {
			String urlApi = "http://localhost:9526/api/emergencia/eliminaremergencia/" + idEmergencia;
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
	public boolean modificarEmergencia(String idEmergencia, EmergenciaDtos emergencia) {
		try {
			JSONObject json = new JSONObject();

			json.put("tituloEmergencia", emergencia.getTituloEmergencia());
			json.put("categoriaEmergencia", emergencia.getCategoriaEmergencia());
			json.put("descripcionEmergencia", emergencia.getDescripcionEmergencia());
			json.put("fechaLanzamientoEmergencia", emergencia.getFechaLanzamientoEmergencia());
			json.put("horaLanzamientoEmergencia", emergencia.getHoraLanzamientoEmergencia());

			String urlApi = "http://localhost:9526/api/emergencia/modificaremergencia/" + idEmergencia;
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
	public long obtenerTotalEmergencias(HttpServletRequest request) {
		long totalEmergencias = 0;
		HttpURLConnection conex = null;

		try {
			HttpSession session = request.getSession(false);
			if (session == null || session.getAttribute("idOrganizacion") == null) {
				System.err.println("No hay sesión activa o falta idOrganizacion.");
				return 0;
			}

			Long idOrganizacion = (Long) session.getAttribute("idOrganizacion");

			String urlApi = (idOrganizacion == 1L) ? "http://localhost:9526/api/emergencia/total"
					: "http://localhost:9526/api/emergencia/total/" + idOrganizacion;

			URL url = new URL(urlApi);
			conex = (HttpURLConnection) url.openConnection();
			conex.setRequestMethod("GET");
			conex.setRequestProperty("Accept", "application/json");

			int responseCode = conex.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) {
				try (BufferedReader in = new BufferedReader(
						new InputStreamReader(conex.getInputStream(), StandardCharsets.UTF_8))) {
					String response = in.readLine();
					totalEmergencias = Long.parseLong(response.trim());
					System.out.println("Total emergencias (org " + idOrganizacion + "): " + totalEmergencias);
				}
			} else {
				System.err.println("Error al obtener emergencias. Código de respuesta: " + responseCode);
			}

		} catch (Exception e) {
			System.err.println("ERROR - EmergenciaServicio - obtenerTotalEmergencias: " + e.getMessage());
			e.printStackTrace();
		} finally {
			if (conex != null) {
				conex.disconnect();
			}
		}

		return totalEmergencias;
	}

}
