package VistaVoluntia.Controlador;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.UUID;

import VistaVoluntia.Dtos.OrganizacionDtos;
import VistaVoluntia.Dtos.ContratoDtos;
import VistaVoluntia.Dtos.UsuarioDtos;
import VistaVoluntia.Servicios.UsuarioServicios;
import VistaVoluntia.Utilidades.Utilidades;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

@MultipartConfig
@WebServlet("/contrato")
public class ContratoControlador extends HttpServlet {

	private UsuarioServicios servicio;
	private Utilidades utilidades;

	private long idPlan;

	@Override
	public void init() throws ServletException {
		this.servicio = new UsuarioServicios();
		this.utilidades = new Utilidades();
		utilidades.ficheroLog(1, "ContratoControlador - init() - Inicialización completada", 0);
	}

	/**
	 * Obtiene el valor de un parámetro, ya sea mediante getParameter o, en caso de
	 * formularios multipart, leyendo el Part.
	 * 
	 * @param request   La solicitud HTTP.
	 * @param paramName Nombre del parámetro.
	 * @return El valor del parámetro o null.
	 * @throws IOException
	 * @throws ServletException
	 * 
	 * @author DMN - 22/02/2025
	 */
	private String getParameter(HttpServletRequest request, String paramName) throws IOException, ServletException {
		String param = request.getParameter(paramName);
		if (param == null && request.getContentType() != null
				&& request.getContentType().toLowerCase().startsWith("multipart/")) {
			Part part = request.getPart(paramName);
			if (part != null) {
				try (InputStream is = part.getInputStream()) {
					param = new String(is.readAllBytes(), StandardCharsets.UTF_8);
				}
			}
		}
		return param;
	}

	/**
	 * Retorna el valor recibido o un valor por defecto si es nulo o vacío.
	 * 
	 * @param value        Valor obtenido.
	 * @param defaultValue Valor por defecto.
	 * @return El valor original o el valor por defecto.
	 * 
	 * @author DMN - 22/02/2025
	 */
	private String getValueOrDefault(String value, String defaultValue) {
		return (value == null || value.trim().isEmpty()) ? defaultValue : value;
	}

	/**
	 * Lee la imagen de perfil del usuario, ya sea del formulario multipart o del
	 * parámetro "imagenActual", y si no se encuentra, carga una imagen por defecto.
	 * 
	 * @param request La solicitud HTTP.
	 * @return Array de bytes de la imagen.
	 * @throws IOException
	 * @throws ServletException
	 * 
	 * @author DMN - 22/02/2025
	 */
	private byte[] obtenerImagenPerfil(HttpServletRequest request) throws IOException, ServletException {
		byte[] imagenBytes = null;
		if (request.getContentType() != null && request.getContentType().toLowerCase().startsWith("multipart/")) {
			Part imagenPart = request.getPart("imagenPerfilUsuario");
			if (imagenPart != null && imagenPart.getSize() > 0) {
				try (InputStream is = imagenPart.getInputStream()) {
					imagenBytes = is.readAllBytes();
				}
			}
		}
		if (imagenBytes == null) {
			String imagenActualBase64 = request.getParameter("imagenActual");
			if (imagenActualBase64 != null && !imagenActualBase64.isEmpty()) {
				imagenBytes = Base64.getDecoder().decode(imagenActualBase64);
			} else {
				String rutaImagen = getServletContext().getRealPath("/Imagenes/Anonimo.jpg");
				imagenBytes = Files.readAllBytes(Paths.get(rutaImagen));
			}
		}
		return imagenBytes;
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String accion = getParameter(request, "accion");
		utilidades.ficheroLog(1, "ContratoControlador - doPost() - Acción recibida: " + accion, 0);
		ContratoDtos nuevoContrato = new ContratoDtos();
		byte[] pdfBytes = null;
		String nombreUsuario = "Nombre Defecto";
		String correoUsuario = "correo@default.com";
		UsuarioDtos nuevoUsuario = null;

		if (accion != null && accion.equals("aniadir")) {
			procesarAniadir(request, response, nuevoContrato);
		} else if (accion != null && accion.equals("validar")) {
			procesarValidar(request, response);
		} else {
			utilidades.ficheroLog(3, "ContratoControlador - doPost() - Acción no válida o parámetros faltantes", 0);
		}
	}

	/**
	 * Procesa la acción "aniadir" sin alterar el flujo original. Se recogen datos,
	 * se generan PDF y correos, y se guarda la información en sesión.
	 * 
	 * @param request       La solicitud HTTP.
	 * @param response      La respuesta HTTP.
	 * @param nuevoContrato Objeto ContratoDtos a configurar.
	 * @throws ServletException
	 * @throws IOException
	 * 
	 * @author DMN - 22/02/2025
	 */
	private void procesarAniadir(HttpServletRequest request, HttpServletResponse response, ContratoDtos nuevoContrato)
			throws ServletException, IOException {
		utilidades.ficheroLog(1, "ContratoControlador - procesarAniadir() - Iniciando proceso de añadido", 0);
		byte[] pdfBytes = null;
		String nombreUsuario = "Nombre Defecto";
		String correoUsuario = "correo@default.com";
		UsuarioDtos nuevoUsuario = null;
		String idPlanStr = request.getParameter("idPlan");
		if (idPlanStr != null && !idPlanStr.isEmpty()) {
			try {
				idPlan = Long.parseLong(idPlanStr);
			} catch (NumberFormatException e) {
				utilidades.ficheroLog(3, "ContratoControlador - procesarAniadir() - idPlan inválido: " + idPlanStr, 0);
			}
		} else {
			utilidades.ficheroLog(3, "ContratoControlador - procesarAniadir() - No se recibió idPlan", 0);
		}
		request.getSession().setAttribute("idPlan", idPlan);

		String passwordUsuario = utilidades.generarContraseña();

		String nombreCliente = request.getParameter("nombreCliente");
		String direccionCliente = request.getParameter("direccionCliente");
		String cifEmpresa = request.getParameter("cifEmpresa");
		String importe = request.getParameter("importe");
		String dniFirmante = request.getParameter("dniFirmante");
		String emailEmpresa = request.getParameter("emailUsuario");

		String nombreOrganizacion = getValueOrDefault(request.getParameter("nombreOrganizacion"), "Sin Especificar");
		String nifOrganizacion = getValueOrDefault(request.getParameter("nifOrganizacion"), "9A9B9C9D9F9G");
		String direccionOrganizacion = getValueOrDefault(request.getParameter("direccionOrganizacion"),
				"Sin Especificar");
		String ciudadOrganizacion = getValueOrDefault(request.getParameter("ciudadOrganizacion"), "Sin Especificar");
		String provinciaOrganizacion = getValueOrDefault(request.getParameter("provinciaOrganizacion"),
				"Sin Especificar");
		String emailOrganizacion = getValueOrDefault(request.getParameter("emailOrganizacion"),
				"correo@organizacion.com");
		String telefonoOrganizacion = getValueOrDefault(request.getParameter("telefonoOrganizacion"),
				"+34 999 99 99 99");
		String tipoInstitucionOrganizacion = getValueOrDefault(request.getParameter("tipoInstitucionOrganizacion"),
				"Sin Especificar");

		if (servicio.validarCorreo(correoUsuario)) {
			utilidades.ficheroLog(3,
					"ContratoControlador - procesarAniadir() - El correo ya existe. No se puede añadir el usuario.", 0);
			request.setAttribute("status", "error");
			request.setAttribute("mensaje", "El correo ya existe. No se puede añadir el usuario.");
			request.getRequestDispatcher("/Contrato.html").forward(request, response);
			return;
		}

		byte[] imagenBytes = obtenerImagenPerfil(request);

		String identificadorFirma = UUID.randomUUID().toString();
		nuevoContrato.setNombreCliente(nombreCliente);
		nuevoContrato.setDireccionCliente(direccionCliente);
		nuevoContrato.setDniFirmante(dniFirmante);
		nuevoContrato.setCifEmpresa(cifEmpresa);
		nuevoContrato.setImporte(importe);
		nuevoContrato.setIdentificador(identificadorFirma);

		OrganizacionDtos nuevaOrganizacion = new OrganizacionDtos();

		nuevaOrganizacion.setNombreOrganizacion(nombreOrganizacion);
		nuevaOrganizacion.setNifOrganizacion(nifOrganizacion);
		nuevaOrganizacion.setDireccionOrganizacion(direccionOrganizacion);
		nuevaOrganizacion.setCiudadOrganizacion(ciudadOrganizacion);
		nuevaOrganizacion.setProvinciaOrganizacion(provinciaOrganizacion);
		nuevaOrganizacion.setEmailOrganizacion(emailOrganizacion);
		nuevaOrganizacion.setTelefonoOrganizacion(telefonoOrganizacion);
		nuevaOrganizacion.setTipoInstitucionOrganizacion("Publica");

		System.out.println("Datos Contrato:");
		System.out.println("Nombre Cliente: " + nombreCliente);
		System.out.println("Dirección Cliente: " + direccionCliente);
		System.out.println("DNI Firmante: " + dniFirmante);
		System.out.println("CIF Empresa: " + cifEmpresa);
		System.out.println("Importe: " + importe);
		System.out.println("Identificador: " + identificadorFirma);

		request.getSession().setAttribute("nuevoContrato", nuevoContrato);

		request.getSession().setAttribute("nuevaOrganizacion", nuevaOrganizacion);

		File pdfFile = servicio.generarContratoPDF(nombreCliente, direccionCliente, cifEmpresa, importe, dniFirmante,
				identificadorFirma);
		try {
			pdfBytes = Files.readAllBytes(pdfFile.toPath());
			System.out.println("PDF convertido a bytes con éxito.");
		} catch (IOException e) {
			utilidades.ficheroLog(3,
					"ContratoControlador - procesarAniadir() - Error al convertir PDF a bytes: " + e.getMessage(), 0);
			e.printStackTrace();
		}
		nuevoContrato.setArchivoPDF(pdfBytes);

		String asuntoEmail = "Identificador Generado";
		String mensajeEmail = "<html>" + "<head>" + "  <meta charset='UTF-8'>" + "  <style type='text/css'>"
				+ "    body { margin: 0; padding: 0; background-color: #f9f9f9; font-family: 'Helvetica Neue', Helvetica, Arial, sans-serif; }"
				+ "    .container { width: 100%; max-width: 600px; margin: 30px auto; background-color: #ffffff; border-radius: 8px; overflow: hidden; box-shadow: 0 2px 5px rgba(0,0,0,0.1); }"
				+ "    .header { background-color: #28a745; padding: 20px; text-align: center; color: #ffffff; }"
				+ "    .content { padding: 30px; color: #333333; }"
				+ "    .identifier-box { margin: 20px 0; background-color: #eeeeee; padding: 15px; text-align: center; font-size: 18px; font-weight: bold; border-radius: 4px; }"
				+ "    .footer { background-color: #f0f0f0; padding: 10px; text-align: center; font-size: 12px; color: #777777; }"
				+ "  </style>" + "</head>" + "<body>" + "  <div class='container'>" + "    <div class='header'>"
				+ "      <h1>Identificador Generado</h1>" + "    </div>" + "    <div class='content'>"
				+ "      <p>Estimado usuario,</p>" + "      <p>Este es tu identificador único:</p>"
				+ "      <div class='identifier-box'>" + identificadorFirma + "</div>"
				+ "      <p>Guarda este identificador en un lugar seguro, ya que será necesario para futuras validaciones.</p>"
				+ "    </div>" + "    <div class='footer'>"
				+ "      <p>© 2025 VistaVoluntia. Todos los derechos reservados.</p>" + "    </div>" + "  </div>"
				+ "</body>" + "</html>";

		servicio.enviarCorreo(emailEmpresa, asuntoEmail, mensajeEmail);
		utilidades.ficheroLog(1,
				"ContratoControlador - procesarAniadir() - Correo con identificador enviado a " + emailEmpresa, 0);

		utilidades.ficheroLog(1,
				"ContratoControlador - procesarAniadir() - Contrato y usuario creados correctamente. Identificador: "
						+ identificadorFirma,
				0);
		request.setAttribute("status", "info");
		request.setAttribute("mensaje", "Se ha enviado un correo con el identificador. Proceda a validar.");
		request.getRequestDispatcher("/Contrato.html").forward(request, response);
	}

	/**
	 * Procesa la acción "validar" sin alterar el flujo original. Compara el
	 * identificador firmado con el almacenado y, de coincidir, envía un correo de
	 * bienvenida y guarda el contrato.
	 * 
	 * @param request  La solicitud HTTP.
	 * @param response La respuesta HTTP.
	 * @throws ServletException
	 * @throws IOException
	 * 
	 * @author DMN - 22/02/2025
	 */
	private void procesarValidar(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		utilidades.ficheroLog(1, "ContratoControlador - procesarValidar() - Iniciando proceso de validación", 0);
		String identificadorUsuarioFirmado = request.getParameter("identificadorUsuarioFirmado");

		String idPlanStr = request.getParameter("idPlan");
		if (idPlanStr != null && !idPlanStr.isEmpty()) {
			try {
				idPlan = Long.parseLong(idPlanStr);
			} catch (NumberFormatException nfe) {
				utilidades.ficheroLog(3, "ContratoControlador - procesarValidar() - idPlan inválido: " + idPlanStr, 0);
			}
		} else {
			Object sessionIdPlan = request.getSession().getAttribute("idPlan");
			if (sessionIdPlan != null) {
				idPlan = (Long) sessionIdPlan;
			} else {
				utilidades.ficheroLog(3, "ContratoControlador - procesarValidar() - No se recibió idPlan", 0);
			}
		}

		ContratoDtos contratoGuardado = (ContratoDtos) request.getSession().getAttribute("nuevoContrato");
		OrganizacionDtos nuevaOrganizacion = (OrganizacionDtos) request.getSession().getAttribute("nuevaOrganizacion");


		if (contratoGuardado != null && identificadorUsuarioFirmado != null
				&& identificadorUsuarioFirmado.equals(contratoGuardado.getIdentificador())) {
			System.out.println("Validación exitosa. Se procede a guardar el contrato.");
			utilidades.ficheroLog(1, "ContratoControlador - procesarValidar() - Validación exitosa. Identificador coincide.", 0);
					
			String correoEmpresa = "voluntiaatencion@gmail.com";

	        String asunto = "[INFO] Nueva empresa registrada: " + nuevaOrganizacion.getNombreOrganizacion();
	        String cuerpoMensaje = "<html>"
	                + "<head>"
	                + "<meta charset='UTF-8'>"
	                + "<style type='text/css'>"
	                + "body { font-family: Arial, sans-serif; margin: 0; padding: 0; background-color: #f4f4f4; }"
	                + ".container { max-width: 600px; margin: 30px auto; background: #ffffff; border-radius: 8px; overflow: hidden; box-shadow: 0 2px 5px rgba(0,0,0,0.15); }"
	                + ".header { background-color: #6c757d; color: #ffffff; padding: 20px; text-align: center; }"
	                + ".content { padding: 30px; color: #333333; }"
	                + ".footer { background-color: #f4f4f4; padding: 10px; text-align: center; font-size: 12px; color: #777777; }"
	                + "</style>"
	                + "</head>"
	                + "<body>"
	                + "<div class='container'>"
	                + "<div class='header'><h1>Notificación interna: Nueva empresa registrada</h1></div>"
	                + "<div class='content'>"
	                + "<p>Se ha registrado una nueva organización en el sistema con los siguientes datos:</p>"
	                + "<ul>"
	                + "<li><strong>Nombre:</strong> " + nuevaOrganizacion.getNombreOrganizacion() + "</li>"
	                + "<li><strong>Correo:</strong> " + nuevaOrganizacion.getEmailOrganizacion() + "</li>"
	                + "<li><strong>Teléfono:</strong> " + nuevaOrganizacion.getTelefonoOrganizacion() + "</li>"
	                + "</ul>"
	                + "</div>"
	                + "<div class='footer'><p>© 2025 VistaVoluntia. Todos los derechos reservados.</p></div>"
	                + "</div>"
	                + "</body>"
	                + "</html>";
	        
			servicio.enviarCorreo(correoEmpresa, asunto, cuerpoMensaje);
			
			servicio.enviarCorreo(correoEmpresa, asunto, cuerpoMensaje);
			utilidades.ficheroLog(1, "ContratoControlador - procesarValidar() - Correo de bienvenida enviado a "
					+ correoEmpresa, 0);

			request.setAttribute("status", "success");
			request.setAttribute("mensaje", "Usuario añadido correctamente y correo enviado.");

			servicio.guardarContrato(contratoGuardado, idPlan, nuevaOrganizacion);
			utilidades.ficheroLog(1, "ContratoControlador - procesarValidar() - Contrato guardado correctamente", 0);
			request.getRequestDispatcher("/Contrato.html").forward(request, response);
		} else {
			System.out.println("Validación fallida. El identificador no coincide.");
			utilidades.ficheroLog(3,
					"ContratoControlador - procesarValidar() - Validación fallida. Identificador no coincide.", 0);
			request.setAttribute("status", "error");
			request.setAttribute("mensaje", "El identificador no coincide.");
			request.getRequestDispatcher("/Contrato.html").forward(request, response);
		}
	}
}
