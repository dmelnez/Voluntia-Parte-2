package VistaVoluntia.Servicios;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
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

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.properties.TextAlignment;

import VistaVoluntia.Dtos.ContratoDtos;
import VistaVoluntia.Dtos.OrganizacionDtos;
import VistaVoluntia.Dtos.UsuarioDtos;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

/**
 * Clase que gestiona las operaciones relacionadas con los usuarios y contratos,
 * incluyendo la generación de contraseñas, envío de correos, creación de PDFs,
 * y comunicación con la API para guardar, modificar, eliminar y validar
 * usuarios.
 * 
 * @author DMN - 14/02/2025
 */
public class UsuarioServicios {

	/**
	 * Genera una contraseña aleatoria de 12 caracteres compuesta de mayúsculas,
	 * minúsculas, dígitos y símbolos.
	 * 
	 * @return La contraseña generada.
	 * @author DMN - 14/02/2025
	 */
	public String generarContraseña() {
		final String caracteres = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()";
		SecureRandom random = new SecureRandom();
		StringBuilder password = new StringBuilder();
		for (int i = 0; i < 12; i++) {
			int index = random.nextInt(caracteres.length());
			password.append(caracteres.charAt(index));
		}
		return password.toString();
	}

	/**
	 * Envía un correo electrónico con formato HTML utilizando SMTP (configurado
	 * para Gmail).
	 * 
	 * @param correoUsuario Destinatario del correo.
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
	 * Guarda un usuario en la API enviando un JSON construido a partir del objeto
	 * UsuarioDtos.
	 * 
	 * @param usuario El objeto UsuarioDtos a guardar.
	 * @author DMN - 14/02/2025
	 */
	/*
	public void guardarUsuario(UsuarioDtos usuario, HttpServletRequest request) {
		try {
			HttpSession session = request.getSession(false);
			if (session == null || session.getAttribute("idOrganizacion") == null) {
				System.err.println("No hay sesión o idOrganizacion no disponible.");
				return;
			}

			Long idOrganizacion = (Long) session.getAttribute("idOrganizacion");

			JSONObject json = new JSONObject();
			json.put("nombreUsuario", usuario.getNombreUsuario());
			json.put("apellidosUsuario", usuario.getApellidosUsuario());
			json.put("telefonoUsuario", usuario.getTelefonoUsuario());
			json.put("dniUsuario", usuario.getDniUsuario());
			json.put("generoUsuario", usuario.getGeneroUsuario());
			json.put("fechaNacimientoUsuario", usuario.getFechaNacimientoUsuario());
			json.put("provinciaUsuario", usuario.getProvinciaUsuario());
			json.put("localidadUsuario", usuario.getLocalidadUsuario());
			json.put("direccionUsuario", usuario.getDireccionUsuario());
			json.put("codigoPostalUsuario", usuario.getCodigoPostalUsuario());
			json.put("numeroIdentificativoUsuario", usuario.getNumeroIdentificativoUsuario());
			json.put("indicativoUsuario", usuario.getIndicativoUsuario());
			json.put("rolUsuario", usuario.getRolUsuario());
			json.put("correoUsuario", usuario.getCorreoUsuario());
			json.put("passwordUsuario", usuario.getPasswordUsuario());
			json.put("imagenPerfilUsuario", usuario.getImagenPerfilUsuario());

			JSONObject orgJson = new JSONObject();
			orgJson.put("idOrganizacion", idOrganizacion);
			json.put("organizacion", orgJson);

			String urlApi = "http://localhost:9526/api/usuario/guardarusuario";
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
				System.out.println("Usuario guardado correctamente.");
			} else {
				System.out.println("Error al guardar usuario: " + responseCode);
			}
		} catch (Exception e) {
			System.out.println("ERROR- [ServiciosUsuario] " + e);
			e.printStackTrace();
		}
	}*/
	

public void guardarUsuario(UsuarioDtos usuario, HttpServletRequest request) {
    try {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("idOrganizacion") == null) {
            System.err.println("No hay sesión o idOrganizacion no disponible.");
            return;
        }
        Long idOrganizacion = (Long) session.getAttribute("idOrganizacion");
        JSONObject json = new JSONObject();
        json.put("nombreUsuario", usuario.getNombreUsuario());
        json.put("apellidosUsuario", usuario.getApellidosUsuario());
        json.put("telefonoUsuario", usuario.getTelefonoUsuario());
        json.put("dniUsuario", usuario.getDniUsuario());
        json.put("generoUsuario", usuario.getGeneroUsuario());
        json.put("fechaNacimientoUsuario", usuario.getFechaNacimientoUsuario());
        json.put("provinciaUsuario", usuario.getProvinciaUsuario());
        json.put("localidadUsuario", usuario.getLocalidadUsuario());
        json.put("direccionUsuario", usuario.getDireccionUsuario());
        json.put("codigoPostalUsuario", usuario.getCodigoPostalUsuario());
        json.put("numeroIdentificativoUsuario", usuario.getNumeroIdentificativoUsuario());
        json.put("indicativoUsuario", usuario.getIndicativoUsuario());
        json.put("rolUsuario", usuario.getRolUsuario());
        json.put("correoUsuario", usuario.getCorreoUsuario());
        json.put("passwordUsuario", usuario.getPasswordUsuario());
        json.put("imagenPerfilUsuario", usuario.getImagenPerfilUsuario());
        JSONObject orgJson = new JSONObject();
        orgJson.put("idOrganizacion", idOrganizacion);
        json.put("organizacion", orgJson);
        String urlApi = "http://localhost:9526/api/usuario/guardarusuario";
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
            System.out.println("Usuario guardado correctamente.");
        } else {
            System.out.println("Error al guardar usuario: " + responseCode);
        }
    } catch (Exception e) {
        System.out.println("ERROR- [ServiciosUsuario] " + e);
        e.printStackTrace();
    }
}

	/**
	 * Genera un contrato en formato PDF con los datos proporcionados.
	 * 
	 * @param nombreCliente      Nombre del cliente.
	 * @param direccionCliente   Dirección del cliente.
	 * @param cifEmpresa         CIF de la empresa.
	 * @param importe            Importe del contrato (en formato numérico String).
	 * @param dniFirmante        DNI del firmante.
	 * @param identificadorFirma Identificador único para la firma.
	 * @return El archivo PDF generado.
	 * @throws IOException Si ocurre un error al generar el PDF.
	 * @author DMN - 14/02/2025
	 */
	public static File generarContratoPDF(String nombreCliente, String direccionCliente, String cifEmpresa,
			String importe, String dniFirmante, String identificadorFirma) throws IOException {
		LocalDate fechaInicio = LocalDate.now();
		LocalDate fechaFin = fechaInicio.plusYears(1);
		LocalDateTime fechaInstante = LocalDateTime.now();
		DateTimeFormatter fechaFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		DateTimeFormatter fechaHoraFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
		String carpetaPath = "C:\\Users\\david\\Desktop\\FicheroLog\\Contratos";
		String fileName = "Contrato_" + nombreCliente.replace(" ", "_") + ".pdf";
		File carpeta = new File(carpetaPath);
		if (!carpeta.exists()) {
			carpeta.mkdirs();
		}
		File pdfFile = new File(carpeta, fileName);

		PdfWriter writer = new PdfWriter(pdfFile);
		PdfDocument pdf = new PdfDocument(writer);
		Document document = new Document(pdf);

		document.add(new Paragraph("Contrato de Servicio de Gestión de Equipos").setBold()
				.setTextAlignment(TextAlignment.CENTER).setFontSize(16).setMarginBottom(20));
		document.add(new Paragraph("Entre las partes:").setBold().setMarginBottom(10));
		document.add(new Paragraph(String.format(
				"De una parte, Voluntia S.L., con domicilio social en Calle barbero, inscrita en el Registro Mercantil con CIF B12345678, "
						+ "representada en este acto por David Melendez, en calidad de Propietario de Voluntia S.L, en adelante \"El Proveedor\".\n\n"
						+ "Y de otra parte, %s, con domicilio en %s, inscrita en el Registro Mercantil con CIF %s, representada en este acto por [NOMBRE DEL REPRESENTANTE], "
						+ "en calidad de [CARGO], en adelante \"El Cliente\".",
				nombreCliente, direccionCliente, cifEmpresa)).setTextAlignment(TextAlignment.JUSTIFIED)
				.setMarginBottom(15));

		document.add(new Paragraph("Cláusulas").setBold().setFontSize(14).setMarginBottom(10));
		document.add(new Paragraph(String.format("Primera: Objeto del contrato\n"
				+ "El presente contrato tiene como objeto la prestación de un servicio de gestión de equipos por parte de Voluntia S.L., "
				+ "que incluye la administración, seguimiento y optimización de los recursos y equipos proporcionados al Cliente, con el objetivo de garantizar un funcionamiento eficiente y seguro.\n\n"
				+ "Segunda: Duración\n"
				+ "El contrato tendrá una duración inicial de un año, comenzando el %s y finalizando el %s, salvo que se prorrogue mediante acuerdo mutuo por escrito o se resuelva conforme a lo dispuesto en las cláusulas de terminación.\n\n"
				+ "Tercera: Obligaciones del Proveedor\n"
				+ "1. Proveer un sistema de gestión de equipos funcional, accesible y actualizado.\n"
				+ "2. Garantizar la disponibilidad de soporte técnico durante el horario establecido: De lunes a viernes de 09:00 a 18:00 horas.\n"
				+ "3. Realizar mantenimientos preventivos y correctivos según sea necesario.\n"
				+ "4. Proteger la confidencialidad de la información proporcionada por el Cliente.\n\n"
				+ "Cuarta: Obligaciones del Cliente\n"
				+ "1. Proporcionar la información necesaria para el correcto desempeño del servicio.\n"
				+ "2. Respetar las condiciones de uso del sistema establecido por el Proveedor.\n"
				+ "3. Realizar los pagos en tiempo y forma según lo estipulado en la cláusula quinta.\n\n"
				+ "Quinta: Condiciones económicas\n"
				+ "1. El Cliente abonará al Proveedor la cantidad de %.2f euros más IVA mensualmente, mediante transferencia bancaria a la cuenta indicada por el Proveedor.\n"
				+ "2. Los pagos se realizarán dentro de los primeros 10 días naturales de cada mes.\n"
				+ "3. En caso de retraso en el pago, se aplicará un interés de demora del 15%% anual sobre la cantidad adeudada.\n\n",
				fechaFormatter.format(fechaInicio), fechaFormatter.format(fechaFin), Double.parseDouble(importe)))
				.setTextAlignment(TextAlignment.JUSTIFIED));
		document.add(new Paragraph(String.format(
				"Y en prueba de conformidad, ambas partes firman el presente contrato en duplicado ejemplar y a un solo efecto, en [LOCALIDAD], a %s.\n\n"
						+ "Por Voluntia S.L.:                                 Por %s:\n\n"
						+ "[Nombre y Firma]                            [Nombre y Firma]",
				fechaFormatter.format(fechaInicio), nombreCliente)).setTextAlignment(TextAlignment.JUSTIFIED));

		document.add(new Paragraph("\n\nInformación del Firmante:").setBold().setFontSize(12).setMarginTop(20));
		document.add(new Paragraph(String.format(
				"Nombre y Apellidos: %s\n" + "DNI: %s\n" + "Fecha de Firma: %s\n" + "Identificador de Firma: %s",
				nombreCliente, dniFirmante, fechaHoraFormatter.format(fechaInstante), identificadorFirma))
				.setTextAlignment(TextAlignment.LEFT).setMarginTop(10));

		document.close();
		return pdfFile;
	}

	/**
	 * Obtiene la lista de usuarios desde la API y la convierte a un ArrayList de
	 * UsuarioDtos.
	 * 
	 * @return Lista de usuarios.
	 * @author DMN - 14/02/2025
	 */
	public ArrayList<UsuarioDtos> listaUsuario(HttpServletRequest request) {
		ArrayList<UsuarioDtos> lista = new ArrayList<>();
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
				urlApi = "http://localhost:9526/api/usuario/usuarios";
				break;
			default:
				urlApi = "http://localhost:9526/api/usuario/organizacion/" + idOrganizacion;
				break;
			}

			URL url = new URL(urlApi);
			HttpURLConnection conex = (HttpURLConnection) url.openConnection();
			conex.setRequestMethod("GET");
			conex.setRequestProperty("Accept", "application/json");

			int responseCode = conex.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) {
				try (BufferedReader in = new BufferedReader(
						new InputStreamReader(conex.getInputStream(), StandardCharsets.UTF_8))) {
					StringBuilder response = new StringBuilder();
					String inputLine;
					while ((inputLine = in.readLine()) != null) {
						response.append(inputLine);
					}

					JSONArray jsonlista = new JSONArray(response.toString());
					System.out.println("Usuarios obtenidos: " + jsonlista);

					for (int i = 0; i < jsonlista.length(); i++) {
						JSONObject jsonUsuario = jsonlista.getJSONObject(i);
						UsuarioDtos usuario = new UsuarioDtos();

						usuario.setIdUsuario(jsonUsuario.getLong("idUsuario"));
						usuario.setNombreUsuario(jsonUsuario.getString("nombreUsuario"));
						usuario.setApellidosUsuario(jsonUsuario.getString("apellidosUsuario"));
						usuario.setTelefonoUsuario(jsonUsuario.getString("telefonoUsuario"));
						usuario.setDniUsuario(jsonUsuario.getString("dniUsuario"));
						usuario.setGeneroUsuario(jsonUsuario.getString("generoUsuario"));
						usuario.setFechaNacimientoUsuario(
								LocalDate.parse(jsonUsuario.getString("fechaNacimientoUsuario")));
						usuario.setProvinciaUsuario(jsonUsuario.getString("provinciaUsuario"));
						usuario.setLocalidadUsuario(jsonUsuario.getString("localidadUsuario"));
						usuario.setDireccionUsuario(jsonUsuario.getString("direccionUsuario"));
						usuario.setCodigoPostalUsuario(jsonUsuario.getString("codigoPostalUsuario"));
						usuario.setFechaIngresoUsuario(jsonUsuario.getString("fechaIngresoUsuario"));
						usuario.setRolUsuario(jsonUsuario.getString("rolUsuario"));
						usuario.setNumeroIdentificativoUsuario(jsonUsuario.getString("numeroIdentificativoUsuario"));
						usuario.setIndicativoUsuario(jsonUsuario.getString("indicativoUsuario"));
						usuario.setCorreoUsuario(jsonUsuario.getString("correoUsuario"));
						usuario.setPasswordUsuario(jsonUsuario.getString("passwordUsuario"));

						String imagenPerfilBase64 = jsonUsuario.getString("imagenPerfilUsuario");
						byte[] imagenBytes = Base64.getDecoder().decode(imagenPerfilBase64);
						usuario.setImagenUsuarioPerfil(imagenBytes);

						lista.add(usuario);
					}
				}
			} else {
				System.out.println("Error al obtener usuarios. Código de respuesta: " + responseCode);
				try (BufferedReader in = new BufferedReader(
						new InputStreamReader(conex.getErrorStream(), StandardCharsets.UTF_8))) {
					String inputLine;
					StringBuilder response = new StringBuilder();
					while ((inputLine = in.readLine()) != null) {
						response.append(inputLine);
					}
					System.out.println("Respuesta de error del servidor: " + response.toString());
				}
			}
		} catch (Exception e) {
			System.out.println("ERROR - ServiciosUsuarios - ListaUsuario: " + e);
			e.printStackTrace();
		}

		System.out.println("Número total de usuarios: " + lista.size());
		return lista;
	}

	/**
	 * Elimina un usuario identificado por su ID mediante una petición DELETE a la
	 * API.
	 * 
	 * @param idUsuario El ID del usuario a eliminar.
	 * @return {@code true} si la eliminación fue exitosa, {@code false} en caso de
	 *         error.
	 * @author DMN - 14/02/2025
	 */
	public boolean eliminarUsuario(String idUsuario) {
		try {
			String urlApi = "http://localhost:9526/api/usuario/eliminarusuario/" + idUsuario;
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
	 * Valida si un correo ya existe mediante una petición GET a la API.
	 * 
	 * @param correoUsuario El correo a validar.
	 * @return {@code true} si el correo existe, {@code false} en caso contrario.
	 * @author DMN - 14/02/2025
	 */
	public boolean validarCorreo(String correoUsuario) {
		try {
			// Codificar el correo para evitar problemas con caracteres especiales
			String urlApi = "http://localhost:9526/api/usuario/validarcorreo?correo="
					+ URLEncoder.encode(correoUsuario, "UTF-8");
			URL url = new URL(urlApi);

			HttpURLConnection conex = (HttpURLConnection) url.openConnection();
			conex.setRequestMethod("GET");
			conex.setRequestProperty("Content-Type", "application/json");

			int responseCode = conex.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) {
				try (BufferedReader in = new BufferedReader(
						new InputStreamReader(conex.getInputStream(), StandardCharsets.UTF_8))) {
					String response = in.readLine();
					return Boolean.parseBoolean(response);
				}
			} else {
				System.out.println("Error en la validación del correo. Código de respuesta: " + responseCode);
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Modifica un usuario existente identificándolo por su ID y enviando los nuevos
	 * datos a la API mediante una petición PUT.
	 * 
	 * @param idUsuario El ID del usuario a modificar.
	 * @param usuario   Objeto UsuarioDtos con los nuevos datos.
	 * @return {@code true} si la modificación fue exitosa, {@code false} en caso de
	 *         error.
	 * @author DMN - 14/02/2025
	 */
	public boolean modificarUsuario(String idUsuario, UsuarioDtos usuario) {
		try {
			JSONObject json = new JSONObject();
			json.put("nombreUsuario", usuario.getNombreUsuario());
			json.put("apellidosUsuario", usuario.getApellidosUsuario());
			json.put("telefonoUsuario", usuario.getTelefonoUsuario());
			json.put("dniUsuario", usuario.getDniUsuario());
			json.put("generoUsuario", usuario.getGeneroUsuario());
			json.put("fechaNacimientoUsuario", usuario.getFechaNacimientoUsuario());
			json.put("provinciaUsuario", usuario.getProvinciaUsuario());
			json.put("localidadUsuario", usuario.getLocalidadUsuario());
			json.put("direccionUsuario", usuario.getDireccionUsuario());
			json.put("codigoPostalUsuario", usuario.getCodigoPostalUsuario());
			json.put("numeroIdentificativoUsuario", usuario.getNumeroIdentificativoUsuario());
			json.put("indicativoUsuario", usuario.getIndicativoUsuario());
			json.put("rolUsuario", usuario.getRolUsuario());
			json.put("correoUsuario", usuario.getCorreoUsuario());
			json.put("imagenPerfilUsuario", usuario.getImagenPerfilUsuario());

			String urlApi = "http://localhost:9526/api/usuario/modificarusuario/" + idUsuario;
			URL url = new URL(urlApi);

			HttpURLConnection conex = (HttpURLConnection) url.openConnection();
			conex.setRequestMethod("PUT");
			conex.setRequestProperty("Content-Type", "application/json");
			conex.setDoOutput(true);

			try (OutputStream os = conex.getOutputStream()) {
				byte[] input = json.toString().getBytes(StandardCharsets.UTF_8);
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
	 * Guarda un contrato en la API asociado a un plan y a un usuario.
	 * <p>
	 * Se construye un JSON que incluye los datos del contrato y la información del
	 * usuario.
	 * </p>
	 * 
	 * @param contrato El objeto ContratoDtos con los datos del contrato.
	 * @param idPlan   El ID del plan asociado.
	 * @param usuario  El objeto UsuarioDtos del usuario asociado.
	 * @author DMN - 14/02/2025
	 */
	public void guardarContrato(ContratoDtos contrato, long idPlan,
			OrganizacionDtos organizacion) {
		try {
			JSONObject json = new JSONObject();

			json.put("archivoPDF", Base64.getEncoder().encodeToString(contrato.getArchivoPDF()));
			json.put("cifEmpresa", contrato.getCifEmpresa());
			json.put("importe", contrato.getImporte());
			json.put("dniFirmante", contrato.getDniFirmante());
			json.put("identificador", contrato.getIdentificador());
			json.put("direccionCliente", contrato.getDireccionCliente());
			json.put("nombreCliente", contrato.getNombreCliente());


			JSONObject organizacionJson = new JSONObject();
			organizacionJson.put("ciudadOrganizacion", organizacion.getCiudadOrganizacion());
			organizacionJson.put("direccionOrganizacion", organizacion.getDireccionOrganizacion());
			organizacionJson.put("emailOrganizacion", organizacion.getEmailOrganizacion());
			organizacionJson.put("nifOrganizacion", organizacion.getNifOrganizacion());
			organizacionJson.put("nombreOrganizacion", organizacion.getNombreOrganizacion());
			organizacionJson.put("provinciaOrganizacion", organizacion.getProvinciaOrganizacion());
			organizacionJson.put("telefonoOrganizacion", organizacion.getTelefonoOrganizacion());
			organizacionJson.put("tipoInstitucionOrganizacion", organizacion.getTipoInstitucionOrganizacion());

			json.put("organizacion", organizacionJson);

			String urlApi = "http://localhost:9526/api/usuario/contrato/plan/" + idPlan;
			URL url = new URL(urlApi);
			HttpURLConnection conex = (HttpURLConnection) url.openConnection();
			conex.setRequestMethod("POST");
			conex.setRequestProperty("Content-Type", "application/json; charset=utf-8");
			conex.setDoOutput(true);

			try (OutputStream os = conex.getOutputStream()) {
				byte[] input = json.toString().getBytes("utf-8");
				os.write(input, 0, input.length);
			}

			int responseCode = conex.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_CREATED || responseCode == HttpURLConnection.HTTP_OK) {
				System.out.println("Contrato guardado correctamente en la API.");
			} else {
				System.out.println("Error al guardar contrato: " + responseCode);
			}
		} catch (Exception e) {
			System.out.println("ERROR- [UsuarioServicios.guardarContrato] " + e);
		}
	}

	/**
	 * Obtiene el número total de usuarios desde la API.
	 * 
	 * @return El número total de usuarios.
	 * @author DMN - 14/02/2025
	 */
	public long obtenerTotalUsuarios(HttpServletRequest request) {
		long totalUsuarios = 0;
		HttpURLConnection conex = null;

		try {
			HttpSession session = request.getSession(false);
			if (session == null || session.getAttribute("idOrganizacion") == null) {
				System.err.println("No hay sesión activa o falta idOrganizacion.");
				return 0;
			}

			Long idOrganizacion = (Long) session.getAttribute("idOrganizacion");

			String urlApi = (idOrganizacion == 1L) ? "http://localhost:9526/api/usuario/total"
					: "http://localhost:9526/api/usuario/total/" + idOrganizacion;

			URL url = new URL(urlApi);
			conex = (HttpURLConnection) url.openConnection();
			conex.setRequestMethod("GET");
			conex.setRequestProperty("Accept", "application/json");

			int responseCode = conex.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) {
				try (BufferedReader in = new BufferedReader(
						new InputStreamReader(conex.getInputStream(), StandardCharsets.UTF_8))) {
					String response = in.readLine();
					totalUsuarios = Long.parseLong(response.trim());
					System.out.println("Total usuarios (org " + idOrganizacion + "): " + totalUsuarios);
				}
			} else {
				System.err.println("Error al obtener usuarios. Código de respuesta: " + responseCode);
			}

		} catch (Exception e) {
			System.err.println("ERROR - UsuarioServicio - obtenerTotalUsuarios: " + e.getMessage());
			e.printStackTrace();
		} finally {
			if (conex != null) {
				conex.disconnect();
			}
		}

		return totalUsuarios;
	}

	/**
	 * Actualiza la contraseña del usuario a través de la API utilizando un token.
	 *
	 * @param token       Token de validación.
	 * @param newPassword La nueva contraseña a establecer.
	 * @return true si la actualización fue exitosa, false en caso de error.
	 */
	public boolean actualizarContraseniaPorToken(String token, String newPassword) {
		try {
			String urlApi = "http://localhost:9526/api/usuario/actualizar-password";
			URL url = new URL(urlApi);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();

			con.setRequestMethod("POST");
			con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
			con.setRequestProperty("Accept", "application/json");
			con.setDoOutput(true);

			String data = "token=" + URLEncoder.encode(token, "UTF-8") + "&nuevaContrasena="
					+ URLEncoder.encode(newPassword, "UTF-8");

			try (OutputStream os = con.getOutputStream()) {
				byte[] input = data.getBytes("UTF-8");
				os.write(input, 0, input.length);
			}

			int responseCode = con.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) {
				try (BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"))) {
					StringBuilder response = new StringBuilder();
					String responseLine;
					while ((responseLine = br.readLine()) != null) {
						response.append(responseLine.trim());
					}
					System.out.println("Respuesta de la API: " + response.toString());
				}
				return true;
			} else {
				System.out.println("Error al actualizar la contraseña. Código de respuesta: " + responseCode);
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Actualiza la contraseña del usuario en la API, filtrando por el id. Se envía
	 * la nueva contraseña (en texto plano) para que la API la encripte y la
	 * actualice.
	 *
	 * @param id          El ID del usuario (en formato String)
	 * @param newPassword La nueva contraseña en texto plano.
	 * @return true si la actualización fue exitosa, false en caso contrario.
	 */
	public boolean actualizarContraseniaPorId(String id, String newPassword) {
		try {
			String urlApi = "http://localhost:9526/api/usuario/actualizar-password-por-id";
			URL url = new URL(urlApi);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();

			con.setRequestMethod("PUT");
			con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
			con.setRequestProperty("Accept", "application/json");
			con.setDoOutput(true);

			String data = "id=" + URLEncoder.encode(id, "UTF-8") + "&nuevaContrasena="
					+ URLEncoder.encode(newPassword, "UTF-8");

			try (OutputStream os = con.getOutputStream()) {
				byte[] input = data.getBytes("UTF-8");
				os.write(input, 0, input.length);
			}

			int responseCode = con.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) {
				try (BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"))) {
					StringBuilder response = new StringBuilder();
					String responseLine;
					while ((responseLine = br.readLine()) != null) {
						response.append(responseLine.trim());
					}
					System.out.println("Respuesta de la API: " + response.toString());
				}
				return true;
			} else {
				System.out.println("Error al actualizar la contraseña por ID. Código de respuesta: " + responseCode);
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

}
