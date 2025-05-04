package ApiVoluntia.ApiVoluntia.Controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ApiVoluntia.ApiVoluntia.Servicios.UsuarioServicioFuncionalidades;
import ApiVoluntia.ApiVoluntia.Utilidades.Utilidades;

/**
 * Controlador REST para la gestión de inscripciones de usuarios a servicios.
 * <p>
 * Proporciona endpoints para inscribir a un usuario en un servicio y para
 * contar cuántos usuarios están inscritos en un servicio.
 * </p>
 * 
 * @author DMN
 */
@RestController
@RequestMapping("/api/usuario-servicio")
public class UsuarioServicioControlador {

	@Autowired
	private UsuarioServicioFuncionalidades usuarioServicioFuncionalidades;

	/**
	 * Endpoint para inscribir a un usuario en un servicio.
	 * 
	 * @param usuarioId  ID del usuario a inscribir.
	 * @param servicioId ID del servicio al que se inscribe el usuario.
	 * @return ResponseEntity con un mensaje indicando el resultado de la
	 *         inscripción.
	 */
	@PostMapping("/inscribir")
	public ResponseEntity<String> inscribirUsuario(@RequestParam Long usuarioId, @RequestParam Long servicioId) {
		Utilidades.ficheroLog(1, "UsuarioServicioControlador - inscribirUsuario() - Entrada");
		try {
			String mensaje = usuarioServicioFuncionalidades.apuntarUsuarioAServicio(usuarioId, servicioId);
			return ResponseEntity.ok(mensaje);
		} catch (Exception e) {
			Utilidades.ficheroLog(3, "UsuarioServicioControlador - inscribirUsuario() - " + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error al inscribir usuario en el servicio.");
		}
	}

	/**
	 * Endpoint para contar cuántos usuarios están inscritos en un servicio.
	 * 
	 * @param servicioId ID del servicio del cual se contará la cantidad de usuarios
	 *                   inscritos.
	 * @return ResponseEntity con la cantidad de usuarios inscritos.
	 */
	@GetMapping("/contar/{servicioId}")
	public ResponseEntity<Long> contarUsuarios(@PathVariable Long servicioId) {
		Utilidades.ficheroLog(1, "UsuarioServicioControlador - contarUsuarios() - Entrada");
		try {
			long cantidad = usuarioServicioFuncionalidades.contarUsuariosEnServicio(servicioId);
			return ResponseEntity.ok(cantidad);
		} catch (Exception e) {
			Utilidades.ficheroLog(3, "UsuarioServicioControlador - contarUsuarios() - " + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(0L);
		}
	}
}
