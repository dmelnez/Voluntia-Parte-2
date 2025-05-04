package VistaVoluntia.Servicios;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

import org.json.JSONObject;

import VistaVoluntia.Dtos.OrganizacionDtos;
import VistaVoluntia.Dtos.UsuarioDtos;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servicio de inicio de sesión y gestión de usuario.
 * 
 * <p>
 * Contiene métodos para encriptar contraseñas, verificar la existencia y
 * validez de un usuario a través de una API, gestionar la sesión y obtener los
 * datos del usuario.
 * </p>
 * 
 * @author DMN - 14/02/2025
 */
public class InicioServicio {

	/**
	 * Encripta la contraseña usando el algoritmo SHA-256.
	 *
	 * @param contraseña La contraseña a encriptar.
	 * @return La cadena hexadecimal resultante del hash.
	 * @author DMN - 14/02/2025
	 */
	public String encriptarContrasenya(String contraseña) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] hash = digest.digest(contraseña.getBytes(StandardCharsets.UTF_8));
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
	 * Verifica el usuario realizando una solicitud POST a la API y, si es correcto,
	 * crea la sesión con los datos del usuario.
	 *
	 * @param request  Objeto HttpServletRequest.
	 * @param response Objeto HttpServletResponse.
	 * @param correo   Correo electrónico del usuario.
	 * @param password Contraseña introducida por el usuario.
	 * @return La URL de redirección según el rol del usuario.
	 * @author DMN - 14/02/2025
	 */

	public String verificarUsuario(HttpServletRequest request, HttpServletResponse response, String correo,
			String password) {
		String redireccion = null;

		try {
			URL url = new URL("http://localhost:9526/api/usuario/login");
			HttpURLConnection conexion = (HttpURLConnection) url.openConnection();
			conexion.setRequestMethod("POST");
			conexion.setRequestProperty("Content-Type", "application/json; utf-8");
			conexion.setDoOutput(true);

			JSONObject payload = new JSONObject().put("correoUsuario", correo).put("passwordUsuario", password);

			try (OutputStream ot = conexion.getOutputStream()) {
				ot.write(payload.toString().getBytes(StandardCharsets.UTF_8));
				ot.flush();
			}

			if (conexion.getResponseCode() == HttpURLConnection.HTTP_OK) {
				String responseStr;
				try (BufferedReader in = new BufferedReader(
						new InputStreamReader(conexion.getInputStream(), StandardCharsets.UTF_8))) {
					responseStr = in.lines().collect(Collectors.joining());
				}

				JSONObject jsonResponse = new JSONObject(responseStr);
				UsuarioDtos usuario = new UsuarioDtos();

				usuario.setIdUsuario(jsonResponse.getLong("idUsuario"));
				usuario.setNombreUsuario(jsonResponse.getString("nombreUsuario"));
				usuario.setApellidosUsuario(jsonResponse.getString("apellidosUsuario"));
				usuario.setTelefonoUsuario(jsonResponse.getString("telefonoUsuario"));
				usuario.setDniUsuario(jsonResponse.getString("dniUsuario"));
				usuario.setGeneroUsuario(jsonResponse.getString("generoUsuario"));
				usuario.setFechaNacimientoUsuario(LocalDate.parse(jsonResponse.getString("fechaNacimientoUsuario")));
				usuario.setProvinciaUsuario(jsonResponse.getString("provinciaUsuario"));
				usuario.setLocalidadUsuario(jsonResponse.getString("localidadUsuario"));
				usuario.setDireccionUsuario(jsonResponse.getString("direccionUsuario"));
				usuario.setCodigoPostalUsuario(jsonResponse.getString("codigoPostalUsuario"));
				usuario.setFechaIngresoUsuario(jsonResponse.getString("fechaIngresoUsuario"));
				usuario.setNumeroIdentificativoUsuario(jsonResponse.getString("numeroIdentificativoUsuario"));
				usuario.setIndicativoUsuario(jsonResponse.getString("indicativoUsuario"));
				usuario.setRolUsuario(jsonResponse.getString("rolUsuario"));
				usuario.setCorreoUsuario(jsonResponse.getString("correoUsuario"));

				// String imgB64 = jsonResponse.getString("imagenPerfilUsuario");
				// usuario.setImagenUsuarioPerfil(Base64.getDecoder().decode(imgB64));

				JSONObject orgJson = jsonResponse.optJSONObject("organizacion");
				if (orgJson != null) {
					OrganizacionDtos o = new OrganizacionDtos();
					o.setIdOrganizacion(orgJson.getLong("idOrganizacion"));
					o.setCiudadOrganizacion(orgJson.getString("ciudadOrganizacion"));
					o.setDireccionOrganizacion(orgJson.getString("direccionOrganizacion"));
					o.setEmailOrganizacion(orgJson.getString("emailOrganizacion"));
					o.setNifOrganizacion(orgJson.getString("nifOrganizacion"));
					o.setNombreOrganizacion(orgJson.getString("nombreOrganizacion"));
					o.setProvinciaOrganizacion(orgJson.getString("provinciaOrganizacion"));
					o.setTelefonoOrganizacion(orgJson.getString("telefonoOrganizacion"));
					o.setTipoInstitucionOrganizacion(orgJson.getString("tipoInstitucionOrganizacion"));
					o.setFechaAltaIntitucion(LocalDateTime.parse(orgJson.getString("fechaAltaIntitucion")));
					usuario.setOrganizacion(o);
				}

				HttpSession session = request.getSession(true);
				setUsuarioSessionAttributes(session, usuario);

				switch (usuario.getRolUsuario()) {
				case "admin":
					redireccion = "RolAdministrador/PanelAdministrador.html";
					break;
				case "Voluntario":
					redireccion = "RolVoluntario/VoluntarioTablaServicios.html";
					break;
				case "adminEmpresa":
					redireccion = "RolAdministradorEmpresa/PanelAdministradorEmpresa.html";
					break;
				default:
					redireccion = "404.html";
				}

			} else if (conexion.getResponseCode() == HttpURLConnection.HTTP_UNAUTHORIZED) {
				System.out.println("Credenciales inválidas.");
			} else {
				System.out.println("Error HTTP al autenticar: " + conexion.getResponseCode());
			}

			conexion.disconnect();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return redireccion;
	}

	/**
	 * Método auxiliar para asignar los atributos del usuario a la sesión.
	 *
	 * @param session El objeto HttpSession.
	 * @param usuario Objeto UsuarioDtos con los datos del usuario.
	 * @author DMN - 14/02/2025
	 */
	private void setUsuarioSessionAttributes(HttpSession session, UsuarioDtos u) {
		// Usuario
		session.setAttribute("idUsuario", u.getIdUsuario());
		session.setAttribute("nombreUsuario", u.getNombreUsuario());
		session.setAttribute("apellidosUsuario", u.getApellidosUsuario());
		session.setAttribute("telefonoUsuario", u.getTelefonoUsuario());
		session.setAttribute("dniUsuario", u.getDniUsuario());
		session.setAttribute("generoUsuario", u.getGeneroUsuario());
		session.setAttribute("fechaNacimientoUsuario", u.getFechaNacimientoUsuario());
		session.setAttribute("provinciaUsuario", u.getProvinciaUsuario());
		session.setAttribute("localidadUsuario", u.getLocalidadUsuario());
		session.setAttribute("direccionUsuario", u.getDireccionUsuario());
		session.setAttribute("codigoPostalUsuario", u.getCodigoPostalUsuario());
		session.setAttribute("fechaIngresoUsuario", u.getFechaIngresoUsuario());
		session.setAttribute("numeroIdentificativoUsuario", u.getNumeroIdentificativoUsuario());
		session.setAttribute("indicativoUsuario", u.getIndicativoUsuario());
		session.setAttribute("rolUsuario", u.getRolUsuario());
		session.setAttribute("correoUsuario", u.getCorreoUsuario());
		session.setAttribute("passwordUsuario", u.getPasswordUsuario());
		session.setAttribute("imagenPerfilUsuario", u.getImagenPerfilUsuario());

		OrganizacionDtos o = u.getOrganizacion();
		if (o != null) {
			session.setAttribute("idOrganizacion", o.getIdOrganizacion());
			session.setAttribute("ciudadOrganizacion", o.getCiudadOrganizacion());
			session.setAttribute("direccionOrganizacion", o.getDireccionOrganizacion());
			session.setAttribute("emailOrganizacion", o.getEmailOrganizacion());
			session.setAttribute("nifOrganizacion", o.getNifOrganizacion());
			session.setAttribute("nombreOrganizacion", o.getNombreOrganizacion());
			session.setAttribute("provinciaOrganizacion", o.getProvinciaOrganizacion());
			session.setAttribute("telefonoOrganizacion", o.getTelefonoOrganizacion());
			session.setAttribute("tipoInstitucionOrganizacion", o.getTipoInstitucionOrganizacion());
			session.setAttribute("fechaAltaOrganizacion", o.getFechaAltaIntitucion());

			System.out.println("Organización en sesión:");
			System.out.println("  idOrganizacion:              " + o.getIdOrganizacion());
			System.out.println("  nombreOrganizacion:          " + o.getNombreOrganizacion());
			System.out.println("  nifOrganizacion:             " + o.getNifOrganizacion());
			System.out.println("  emailOrganizacion:           " + o.getEmailOrganizacion());
			System.out.println("  telefonoOrganizacion:        " + o.getTelefonoOrganizacion());
			System.out.println("  ciudadOrganizacion:          " + o.getCiudadOrganizacion());
			System.out.println("  provinciaOrganizacion:       " + o.getProvinciaOrganizacion());
			System.out.println("  direccionOrganizacion:       " + o.getDireccionOrganizacion());
			System.out.println("  tipoInstitucionOrganizacion: " + o.getTipoInstitucionOrganizacion());
			System.out.println("  fechaAltaOrganizacion:       " + o.getFechaAltaIntitucion());
		} else {
			System.out.println("⚠ El usuario no tiene organización asociada.");
		}
	}

	/**
	 * Cierra la sesión actual invalidándola y redirige al index.
	 *
	 * @param request  Objeto HttpServletRequest.
	 * @param response Objeto HttpServletResponse.
	 * @throws IOException En caso de error al redirigir.
	 * @author DMN - 14/02/2025
	 */
	public void cerrarSesion(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession(false);
		if (session != null) {
			session.invalidate();
		}
		response.sendRedirect("../index.html");
	}

	/**
	 * Obtiene los datos del usuario almacenados en la sesión y los retorna en un
	 * objeto UsuarioDtos.
	 *
	 * @param session La sesión actual.
	 * @return Objeto UsuarioDtos con los datos del usuario.
	 * @author DMN - 14/02/2025
	 */
	public UsuarioDtos obtenerDatosUsuario(HttpSession session) {
		UsuarioDtos usuario = new UsuarioDtos();
		usuario.setIdUsuario((Long) session.getAttribute("idUsuario"));
		usuario.setNombreUsuario((String) session.getAttribute("nombreUsuario"));
		usuario.setApellidosUsuario((String) session.getAttribute("apellidosUsuario"));
		usuario.setTelefonoUsuario((String) session.getAttribute("telefonoUsuario"));
		usuario.setDniUsuario((String) session.getAttribute("dniUsuario"));
		usuario.setGeneroUsuario((String) session.getAttribute("generoUsuario"));

		Object fn = session.getAttribute("fechaNacimientoUsuario");
		if (fn instanceof LocalDate) {
			usuario.setFechaNacimientoUsuario((LocalDate) fn);
		} else if (fn instanceof String) {
			usuario.setFechaNacimientoUsuario(LocalDate.parse((String) fn));
		}

		usuario.setProvinciaUsuario((String) session.getAttribute("provinciaUsuario"));
		usuario.setLocalidadUsuario((String) session.getAttribute("localidadUsuario"));
		usuario.setDireccionUsuario((String) session.getAttribute("direccionUsuario"));
		usuario.setCodigoPostalUsuario((String) session.getAttribute("codigoPostalUsuario"));
		usuario.setFechaIngresoUsuario((String) session.getAttribute("fechaIngresoUsuario"));
		usuario.setNumeroIdentificativoUsuario((String) session.getAttribute("numeroIdentificativoUsuario"));
		usuario.setIndicativoUsuario((String) session.getAttribute("indicativoUsuario"));
		usuario.setRolUsuario((String) session.getAttribute("rolUsuario"));
		usuario.setCorreoUsuario((String) session.getAttribute("correoUsuario"));
		usuario.setPasswordUsuario((String) session.getAttribute("passwordUsuario"));
		usuario.setImagenUsuarioPerfil((byte[]) session.getAttribute("imagenPerfilUsuario"));

		Object idOrg = session.getAttribute("idOrganizacion");
		if (idOrg != null) {
			OrganizacionDtos o = new OrganizacionDtos();
			o.setIdOrganizacion((Long) idOrg);
			o.setCiudadOrganizacion((String) session.getAttribute("ciudadOrganizacion"));
			o.setDireccionOrganizacion((String) session.getAttribute("direccionOrganizacion"));
			o.setEmailOrganizacion((String) session.getAttribute("emailOrganizacion"));
			o.setNifOrganizacion((String) session.getAttribute("nifOrganizacion"));
			o.setNombreOrganizacion((String) session.getAttribute("nombreOrganizacion"));
			o.setProvinciaOrganizacion((String) session.getAttribute("provinciaOrganizacion"));
			o.setTelefonoOrganizacion((String) session.getAttribute("telefonoOrganizacion"));
			o.setTipoInstitucionOrganizacion((String) session.getAttribute("tipoInstitucionOrganizacion"));
			Object fa = session.getAttribute("fechaAltaOrganizacion");
			if (fa instanceof LocalDateTime) {
				o.setFechaAltaIntitucion((LocalDateTime) fa);
			} else if (fa instanceof String) {
				o.setFechaAltaIntitucion(LocalDateTime.parse((String) fa));
			}

			usuario.setOrganizacion(o);
		}

		return usuario;
	}
}
