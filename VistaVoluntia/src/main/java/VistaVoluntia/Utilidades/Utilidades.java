package VistaVoluntia.Utilidades;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Properties;
import java.util.UUID;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.Multipart;
import javax.mail.internet.MimeMultipart;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.properties.TextAlignment;

import VistaVoluntia.Dtos.UsuarioDtos;

/**
 * Clase de utilidades para generar contraseñas, enviar correos, encriptar
 * datos, escribir logs, generar PDFs y gestionar tokens.
 *
 * @author DMN
 * @since 2025-01-18
 */
public class Utilidades {

	/**
	 * Genera una contraseña aleatoria de 12 caracteres que incluye letras
	 * mayúsculas, minúsculas, dígitos y símbolos especiales.
	 *
	 * @return La contraseña generada.
	 */
	public static String generarContraseña() {
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
	 * Envía un correo electrónico utilizando SMTP (configurado para Gmail).
	 *
	 * @param correoUsuario Destinatario del correo.
	 * @param asunto        Asunto del correo.
	 * @param mensaje       Mensaje en formato HTML.
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
	 * Encripta la contraseña utilizando el algoritmo SHA-256.
	 *
	 * @param contraseña La contraseña a encriptar.
	 * @return La contraseña encriptada en formato hexadecimal.
	 */
	public static String encriptarContrasenya(String contraseña) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] hash = digest.digest(contraseña.getBytes());
			StringBuilder hexString = new StringBuilder();
			for (byte b : hash) {
				String hex = String.format("%02x", b);
				hexString.append(hex);
			}
			return hexString.toString();
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Escribe un mensaje en un fichero log en formato _append_.
	 *
	 * @param nivelLog   Nivel de log (1: INFO, 2: WARN, 3: ERROR).
	 * @param mensajeLog Mensaje a registrar.
	 * @param idUsuario  Identificador del usuario (para incluirlo en el nombre del
	 *                   log).
	 */
	public void ficheroLog(int nivelLog, String mensajeLog, long idUsuario) {
		LocalDate fecha = LocalDate.now();
		SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");

		//String ficheroLogRuta = "C:\\Users\\dmelendezn\\Desktop\\Fichero Log\\log-" + idUsuario + "-" + fecha + ".txt";
		//String ficheroLogRuta = "C:\\Users\\david\\Desktop\\Prueba Log\\log-" + idUsuario + "-" + fecha + ".txt";
		String ficheroLogRuta = "\\home\\ubuntu\\FicheroLoglog-" + idUsuario + "-" + fecha + ".txt";

		
		
		String prefijoLog;
		switch (nivelLog) {
		case 1:
			prefijoLog = "[INFO] -> " + mensajeLog;
			break;
		case 2:
			prefijoLog = "[WARN] -> " + mensajeLog;
			break;
		case 3:
			prefijoLog = "[ERROR] -> " + mensajeLog;
			break;
		default:
			System.out.println("[ALERTA] -> Nivel de log no válido.");
			return;
		}

		try (FileWriter escribir = new FileWriter(ficheroLogRuta, true)) {
			escribir.write(prefijoLog + "\n");
		} catch (IOException e) {
			System.out.println("[ALERTA] -> Se ha producido un error al escribir en el archivo de log.");
		}
	}

	/**
	 * Genera un contrato en formato PDF utilizando los datos proporcionados.
	 *
	 * @param nombreCliente      Nombre del cliente.
	 * @param direccionCliente   Dirección del cliente.
	 * @param cifEmpresa         CIF de la empresa.
	 * @param importe            Importe del contrato (en formato numérico String).
	 * @param dniFirmante        DNI del firmante.
	 * @param identificadorFirma Identificador único para la firma.
	 * @return El archivo PDF generado.
	 * @throws IOException Si ocurre un error al escribir el archivo.
	 */
	public static File generarContratoPDF(String nombreCliente, String direccionCliente, String cifEmpresa,
			String importe, String dniFirmante, String identificadorFirma) throws IOException {
		// Fechas para el contrato
		LocalDate fechaInicio = LocalDate.now();
		LocalDate fechaFin = fechaInicio.plusYears(1);
		LocalDateTime fechaInstante = LocalDateTime.now();
		DateTimeFormatter fechaFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		DateTimeFormatter fechaHoraFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

		String carpetaPath = "C:\\Users\\david\\Desktop\\Fichero Log\\Contratos";
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
				+

				"Segunda: Duración\n"
				+ "El contrato tendrá una duración inicial de un año, comenzando el %s y finalizando el %s, salvo que se prorrogue mediante acuerdo mutuo por escrito o se resuelva conforme a lo dispuesto en las cláusulas de terminación.\n\n"
				+

				"Tercera: Obligaciones del Proveedor\n"
				+ "1. Proveer un sistema de gestión de equipos funcional, accesible y actualizado.\n"
				+ "2. Garantizar la disponibilidad de soporte técnico durante el horario establecido: De lunes a viernes de 09:00 a 18:00 horas.\n"
				+ "3. Realizar mantenimientos preventivos y correctivos según sea necesario.\n"
				+ "4. Proteger la confidencialidad de la información proporcionada por el Cliente.\n\n" +

				"Cuarta: Obligaciones del Cliente\n"
				+ "1. Proporcionar la información necesaria para el correcto desempeño del servicio.\n"
				+ "2. Respetar las condiciones de uso del sistema establecido por el Proveedor.\n"
				+ "3. Realizar los pagos en tiempo y forma según lo estipulado en la cláusula quinta.\n\n" +

				"Quinta: Condiciones económicas\n"
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
	 * Genera un token único para un usuario a partir de su email y un UUID. El
	 * token se codifica en Base64 y se establece una fecha de expiración (10
	 * minutos a partir de ahora). Además, se crea un objeto UsuarioDtos con el
	 * token y se envían los datos a la API.
	 *
	 * @param email Correo del usuario.
	 * @return El token generado.
	 */
	public static String generarToken(String email) {
		String uniqueString = email + UUID.randomUUID().toString();
		String token = Base64.getEncoder().encodeToString(uniqueString.getBytes());
		LocalDateTime fechaExpiracion = LocalDateTime.now().plusMinutes(10);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		System.out.println("Token generado: " + token);
		System.out.println("Fecha de expiración: " + fechaExpiracion.format(formatter));

		UsuarioDtos usuarioDto = new UsuarioDtos();
		usuarioDto.setCorreoUsuario(email);
		usuarioDto.setTokenUsuario(token);
		usuarioDto.setFechaGeneradaTokenUsuario(fechaExpiracion);

		enviarTokenAApi(usuarioDto);

		return token;
	}

	/**
	 * Envía el token generado a la API mediante una petición PUT.
	 *
	 * @param usuarioDto Objeto que contiene el correo, token y fecha de expiración.
	 */
	private static void enviarTokenAApi(UsuarioDtos usuarioDto) {
		String apiUrl = "http://localhost:9526/api/usuario/actualizartokencorreo";
		RestTemplate restTemplate = new RestTemplate();
		try {
			ResponseEntity<UsuarioDtos> response = restTemplate.exchange(apiUrl, HttpMethod.PUT,
					new HttpEntity<>(usuarioDto), UsuarioDtos.class);

			if (response.getStatusCode().is2xxSuccessful()) {
				System.out.println("Token actualizado con éxito para el usuario " + usuarioDto.getCorreoUsuario());
			} else {
				System.out.println("Error al actualizar token: " + response.getStatusCode());
			}
		} catch (Exception e) {
			System.out.println("Excepción al llamar a la API: " + e.getMessage());
		}
	}

	/**
	 * Envía un correo electrónico al usuario con un enlace para recuperar la
	 * contraseña.
	 *
	 * @param userEmail Correo del usuario.
	 * @param token     Token generado para la recuperación.
	 */
	public static void enviarToken(String userEmail, String token) {
		// Construir el enlace de recuperación con el token
		String resetLink = "http://localhost:8080/VistaVoluntia/NuevaContrasenia.jsp?token=" + token;
		String subject = "Recuperación de contraseña";
		String messageBody = "Haz clic en el siguiente enlace para recuperar tu contraseña: \n" + resetLink;
		enviarCorreo(userEmail, subject, messageBody);
	}

	/**
	 * Método alternativo para encriptar una contraseña utilizando SHA-256. Este
	 * método es funcionalmente idéntico a {@link #encriptarContrasenya(String)}.
	 *
	 * @param contraseña La contraseña a encriptar.
	 * @return La contraseña encriptada.
	 */
	public static String encriptarContrasenya2(String contraseña) {
		return encriptarContrasenya(contraseña);
	}
}
