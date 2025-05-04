package VistaVoluntia.Controlador;

import java.io.IOException;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import VistaVoluntia.Utilidades.Utilidades;

@WebFilter("/*")
public class SeguridadControlador implements Filter {

	private Utilidades utilidades;

	/**
	 * Inicializa el filtro y crea la utilidad de log.
	 *
	 * @param filterConfig Configuración del filtro proporcionada por el contenedor.
	 * @throws ServletException Si ocurre un error durante la inicialización.
	 * 
	 * @author DMN - 05/04/2025
	 */
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		this.utilidades = new Utilidades();
	}

	/**
	 * Intercepta las peticiones HTTP y aplica la lógica de seguridad según la URI y
	 * rol de usuario.
	 *
	 * @param request  La petición genérica, que se castea a HttpServletRequest.
	 * @param response La respuesta genérica, que se castea a HttpServletResponse.
	 * @param chain    Cadena de filtros para continuar el procesamiento.
	 * @throws IOException      Si ocurre un error de E/S al continuar la cadena de
	 *                          filtros.
	 * @throws ServletException Si ocurre un error de servlet al continuar la cadena
	 *                          de filtros.
	 * 
	 * @author DMN - 18/04/2025
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;

		String contextPath = req.getContextPath();
		String uri = req.getRequestURI();

		// Eliminar la barra final (si no es la raíz) para evitar discrepancias
		if (!uri.equals(contextPath + "/") && uri.endsWith("/")) {
			uri = uri.substring(0, uri.length() - 1);
		}

		// Páginas públicas y recursos (sin autenticación)
		boolean paginasPublicas = uri.equals(contextPath + "/") || uri.endsWith("login.html") || uri.endsWith("login")
				|| uri.endsWith("index.html") || uri.endsWith("NuevaContrasenia.jsp") || uri.endsWith("/login")
				|| uri.endsWith("recuperarContrasenia.html") || uri.endsWith("Contrato.html") || uri.endsWith(".mp4")
				|| uri.endsWith("prueba.jsp") || uri.endsWith("/usuario") || uri.endsWith("404.html") || uri.endsWith(".ico")
				|| uri.endsWith("contratoValidacion.html") || uri.endsWith("Nosotros.html") || uri.endsWith("/contrato")
				|| uri.endsWith("Sesion.html") || uri.endsWith("/plan") || uri.endsWith(".css") || uri.endsWith(".js")
				|| uri.endsWith(".png") || uri.endsWith(".jpg") || uri.endsWith("ManualUsuario.html") || uri.endsWith(".jpeg");
		if (paginasPublicas) {
			utilidades.ficheroLog(1, "SeguridadControlador: Acceso a recurso público: " + uri, 0);
			chain.doFilter(request, response);
			return;
		}

		// Verificar que exista la sesión y el atributo "rolUsuario"
		HttpSession session = req.getSession(false);
		if (session == null || session.getAttribute("rolUsuario") == null) {
			utilidades.ficheroLog(3,
					"SeguridadControlador: Sesión no iniciada o rolUsuario no encontrado para URI: " + uri, 0);
			res.sendRedirect(contextPath + "/login.html");
			return;
		}
		String rol = (String) session.getAttribute("rolUsuario");

		// --- LÓGICA DE ACCESO SEGÚN ROL ---

		// Para adminEmpresa:
		if ("adminEmpresa".equalsIgnoreCase(rol)) {
			// Bloquear acceso a rutas que pertenezcan a otros roles:
			if ((uri.startsWith(contextPath + "/RolAdministrador/")
					&& !uri.startsWith(contextPath + "/RolAdministradorEmpresa/"))
					|| uri.startsWith(contextPath + "/RolVoluntario/")) {
				utilidades.ficheroLog(3,
						"SeguridadControlador: Acceso denegado a URI " + uri + " para rol adminEmpresa", 0);
				res.sendRedirect(contextPath + "/RolAdministradorEmpresa/PanelAdministradorEmpresa.html");
				return;
			}
			// Permitir acceso si la URI es una de las permitidas:
			if (uri.equals(contextPath + "/RolAdministradorEmpresa/PanelAdministradorEmpresa.html")
					|| uri.equals(contextPath + "/RolAdministradorEmpresa/EmpresaAdminNuevoUsuario.jsp")
					|| uri.equals(contextPath + "/RolAdministradorEmpresa/EmpresaAdminTablaUsuarios.html")
					|| uri.equals(contextPath + "/RolAdministradorEmpresa/EmpresaAdminNuevoServicio.jsp")
					|| uri.equals(contextPath + "/RolAdministradorEmpresa/EmpresaAdminTablaServicios.html")
					|| uri.equals(contextPath + "/RolAdministradorEmpresa/EmpresaAdminNuevaEmergencia.jsp")
					|| uri.equals(contextPath + "/RolAdministradorEmpresa/EmpresaAdminTablaEmergencias.html")
					|| uri.equals(contextPath + "/RolAdministradorEmpresa/EmpresaAdminNuevoVehiculo.jsp")
					|| uri.equals(contextPath + "/RolAdministradorEmpresa/EmpresaAdminTablaVehiculos.html")
					|| uri.equals(contextPath + "/RolAdministradorEmpresa/EmpresaAdminNuevoPlan.jsp")
					|| uri.equals(contextPath + "/RolAdministradorEmpresa/EmpresaAdminTablaPlanes.html")
					|| uri.equals(contextPath + "/RolAdministradorEmpresa/EmpresaAdminModificarUsuario.jsp")
					|| uri.equals(contextPath + "/RolAdministradorEmpresa/EmpresaAdminModificarVehiculo.jsp")
					|| uri.equals(contextPath + "/RolAdministradorEmpresa/EmpresaAdminModificarServicio.jsp")
					|| uri.equals(contextPath + "/RolAdministradorEmpresa/EmpresaAdminModificarEmergencia.jsp")
					|| uri.equals(contextPath + "/RolAdministradorEmpresa/EmpresaAdminModificarPlan.jsp")
					|| uri.equals(contextPath + "/RolAdministradorEmpresa/EmpresaAdminPerfilUsuario.jsp")
					|| uri.equals(contextPath + "/RolAdministradorEmpresa/EmpresaAdminNuevaClave.jsp")
					|| uri.equals(contextPath + "/RolAdministradorEmpresa/EmpresaAdminTablaClaves.html")
					|| uri.equals(contextPath + "/RolAdministradorEmpresa/EmpresaAdminNuevaOrganizacion.jsp")
					|| uri.equals(contextPath + "/RolAdministradorEmpresa/EmpresaAdminTablaOrganizaciones.html")
					|| uri.equals(contextPath + "/RolAdministradorEmpresa/EmpresaAdminModificarClave.jsp")
					|| uri.equals(contextPath + "/RolAdministradorEmpresa/EmpresaAdminModificarOrganizacion.jsp")
					|| uri.endsWith(".ico")
					|| uri.startsWith(contextPath + "/usuario") || uri.startsWith(contextPath + "/servicio")
					|| uri.startsWith(contextPath + "/emergencia") || uri.startsWith(contextPath + "/vehiculo")
					|| uri.startsWith(contextPath + "/organizacion") || uri.startsWith(contextPath + "/clave")
					|| uri.startsWith(contextPath + "/plan")) {
				utilidades.ficheroLog(1,
						"SeguridadControlador: Acceso permitido a URI " + uri + " para rol adminEmpresa", 0);
				chain.doFilter(request, response);
				return;
			} else {
				utilidades.ficheroLog(3,
						"SeguridadControlador: Acceso denegado a URI " + uri + " para rol adminEmpresa", 0);
				res.sendRedirect(contextPath + "/RolAdministradorEmpresa/PanelAdministradorEmpresa.html");
				return;
			}
		}
		// Para Admin:
		else if ("Admin".equalsIgnoreCase(rol)) {
			// Bloquear acceso a rutas de adminEmpresa y de Voluntario
			if (uri.startsWith(contextPath + "/RolAdministradorEmpresa/")
					|| uri.startsWith(contextPath + "/RolVoluntario/")) {
				utilidades.ficheroLog(3, "SeguridadControlador: Acceso denegado a URI " + uri + " para rol Admin", 0);
				res.sendRedirect(contextPath + "/RolAdministrador/PanelAdministrador.html");
				return;
			}
			// Permitir acceso si la URI pertenece a Admin o a prefijos comunes permitidos
			if (uri.equals(contextPath + "/RolAdministrador/PanelAdministrador.html")
					|| uri.equals(contextPath + "/RolAdministrador/AdminNuevoUsuario.jsp")
					|| uri.equals(contextPath + "/RolAdministrador/AdminTablaUsuarios.html")
					|| uri.equals(contextPath + "/RolAdministrador/AdminNuevoServicio.jsp")
					|| uri.equals(contextPath + "/RolAdministrador/AdminTablaServicios.html")
					|| uri.equals(contextPath + "/RolAdministrador/AdminNuevaEmergencia.jsp")
					|| uri.equals(contextPath + "/RolAdministrador/AdminTablaEmergencias.html")
					|| uri.equals(contextPath + "/RolAdministrador/AdminNuevoVehiculo.jsp")
					|| uri.equals(contextPath + "/RolAdministrador/AdminTablaVehiculos.html")
					|| uri.equals(contextPath + "/RolAdministrador/AdminTablaPlanes.html")
					|| uri.equals(contextPath + "/RolAdministrador/AdminModificarUsuario.jsp")
					|| uri.equals(contextPath + "/RolAdministrador/AdminModificarVehiculo.jsp")
					|| uri.equals(contextPath + "/RolAdministrador/AdminModificarServicio.jsp")
					|| uri.equals(contextPath + "/RolAdministrador/AdminModificarEmergencia.jsp")
					|| uri.equals(contextPath + "/RolAdministrador/AdminPerfilUsuario.jsp")
					|| uri.equals(contextPath + "/RolAdministrador/AdminNuevaClave.jsp")
					|| uri.equals(contextPath + "/RolAdministrador/AdminModificarClave.jsp")
					|| uri.equals(contextPath + "/RolAdministrador/AdminTablaClaves.html")
					|| uri.endsWith(".ico")
					|| uri.startsWith(contextPath + "/usuario") || uri.startsWith(contextPath + "/servicio")
					|| uri.startsWith(contextPath + "/emergencia") || uri.startsWith(contextPath + "/clave")
					|| uri.startsWith(contextPath + "/vehiculo")) {
				utilidades.ficheroLog(1, "SeguridadControlador: Acceso permitido a URI " + uri + " para rol Admin", 0);
				chain.doFilter(request, response);
				return;
			} else {
				utilidades.ficheroLog(3, "SeguridadControlador: Acceso denegado a URI " + uri + " para rol Admin", 0);
				res.sendRedirect(contextPath + "/RolAdministrador/PanelAdministrador.html");
				return;
			}
		}
		// Para Voluntario:
		else if ("Voluntario".equalsIgnoreCase(rol)) {
			// Bloquear acceso a rutas de adminEmpresa y de Admin
			if (uri.startsWith(contextPath + "/RolAdministradorEmpresa/")
					|| uri.startsWith(contextPath + "/RolAdministrador/")) {
				utilidades.ficheroLog(3, "SeguridadControlador: Acceso denegado a URI " + uri + " para rol Voluntario",
						0);
				res.sendRedirect(contextPath + "/RolVoluntario/VoluntarioTablaServicios.html");
				return;
			}
			// Permitir acceso a rutas específicas para voluntario y prefijos comunes
			if (uri.startsWith(contextPath + "/RolVoluntario/VoluntarioTablaServicios.html")
					|| uri.startsWith(contextPath + "/RolVoluntario/VoluntarioPerfilUsuario.jsp")
					|| uri.startsWith(contextPath + "/RolVoluntario/VoluntarioTablaEmergencias.html")
					|| uri.startsWith(contextPath + "/RolVoluntario/VoluntarioTablaClaves.html") 
					|| uri.endsWith(".ico")
					|| uri.startsWith(contextPath + "/servicio") || uri.startsWith(contextPath + "/emergencia")
					|| uri.startsWith(contextPath + "/login") || uri.startsWith(contextPath + "/usuario") || uri.startsWith(contextPath + "/clave")
					|| uri.startsWith(contextPath + "/inscripcion")) {
				utilidades.ficheroLog(1, "SeguridadControlador: Acceso permitido a URI " + uri + " para rol Voluntario",
						0);
				chain.doFilter(request, response);
				return;
			} else {
				utilidades.ficheroLog(3, "SeguridadControlador: Acceso denegado a URI " + uri + " para rol Voluntario",
						0);
				res.sendRedirect("/RolVoluntario/VoluntarioTablaServicios.html");
				return;
			}
		}
		// Si el rol no coincide con ninguno conocido:
		else {
			utilidades.ficheroLog(3, "SeguridadControlador: Rol desconocido (" + rol + ") para URI " + uri, 0);
			res.sendRedirect(contextPath + "/login.html");
			return;
		}
	}

	/**
	 * Libera recursos al destruir el filtro.
	 *
	 * @author DMN - 26/04/2025
	 */
	@Override
	public void destroy() {
		// Liberar recursos si es necesario.
	}

}
