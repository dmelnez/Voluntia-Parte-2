package ApiVoluntia.ApiVoluntia.Controladores;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ApiVoluntia.ApiVoluntia.Dtos.EmergenciaDtos;
import ApiVoluntia.ApiVoluntia.Servicios.EmergenciaFuncionalidades;
import ApiVoluntia.ApiVoluntia.Utilidades.Utilidades;

/**
 * Controlador REST para la gestión de emergencias.
 * <p>
 * Proporciona endpoints para obtener, guardar, eliminar y modificar
 * emergencias, contar el total de emergencias registradas y guardar una
 * emergencia asociada a un usuario.
 * </p>
 * 
 * @author DMN
 */
@RestController
@RequestMapping("/api/emergencia")
public class EmergenciaControlador {

	@Autowired
	private EmergenciaFuncionalidades emergencias;

	/**
	 * Endpoint para obtener la lista de todas las emergencias existentes.
	 *
	 * @return ArrayList de EmergenciaDtos con todas las emergencias registradas. En
	 *         caso de error, retorna una lista vacía.
	 */
	@GetMapping("/emergencias")
	public ArrayList<EmergenciaDtos> listaEmergencias() {
		Utilidades.ficheroLog(1, "EmergenciaControlador - listaEmergencias() - Entrada");
		try {
			return emergencias.getEmergencia();
		} catch (Exception e) {
			Utilidades.ficheroLog(3, "EmergenciaControlador - listaEmergencias() - " + e.getMessage());
			return new ArrayList<>();
		}
	}

	/**
	 * Endpoint para guardar una nueva emergencia.
	 *
	 * @param emergencia Objeto EmergenciaDtos con los datos de la emergencia a
	 *                   guardar.
	 * @return La emergencia guardada o null en caso de error.
	 */
	@PostMapping("/guardaremergencia")
	public EmergenciaDtos guardarEmergencia(@RequestBody EmergenciaDtos emergencia) {
		Utilidades.ficheroLog(1, "EmergenciaControlador - guardarEmergencia() - Entrada");
		try {
			return emergencias.guardarEmergencia(emergencia);
		} catch (Exception e) {
			Utilidades.ficheroLog(3, "EmergenciaControlador - guardarEmergencia() - " + e.getMessage());
			throw e;
		}
	}

	@GetMapping("/organizacion/{id_org}")
	public List<EmergenciaDtos> listarPorOrganizacion(@PathVariable("id_org") Long idOrg) {
		Utilidades.ficheroLog(1, "EmergenciaControlador - listarPorOrganizacion() - Entrada");
		return emergencias.listarPorOrganizacion(idOrg);
	}

	/**
	 * Endpoint para eliminar una emergencia por su ID.
	 *
	 * @param id_emergencia ID de la emergencia a eliminar en formato String.
	 * @return true si la emergencia fue eliminada correctamente, false en caso de
	 *         error.
	 */
	@DeleteMapping("/eliminaremergencia/{id_emergencia}")
	public boolean deleteEmergencia(@PathVariable("id_emergencia") String idEmergencia) {
		Utilidades.ficheroLog(1, "EmergenciaControlador - deleteEmergencia() - Entrada");
		try {
			return emergencias.eliminarEmergencia(idEmergencia);
		} catch (Exception e) {
			Utilidades.ficheroLog(3, "EmergenciaControlador - deleteEmergencia() - " + e.getMessage());
			return false;
		}
	}

	/**
	 * Endpoint para modificar los datos de una emergencia.
	 *
	 * @param idEmergencia ID de la emergencia a modificar en formato String.
	 * @param emergencia   Objeto EmergenciaDtos con los nuevos datos de la
	 *                     emergencia.
	 * @return true si la emergencia fue modificada correctamente, false en caso de
	 *         error.
	 */
	@PutMapping("/modificaremergencia/{id_emergencia}")
	public boolean putEmergencia(@PathVariable("id_emergencia") String idEmergencia,
			@RequestBody EmergenciaDtos emergencia) {
		Utilidades.ficheroLog(1, "EmergenciaControlador - putEmergencia() - Entrada");
		try {
			return emergencias.modificarEmergencia(idEmergencia, emergencia);
		} catch (Exception e) {
			Utilidades.ficheroLog(3, "EmergenciaControlador - putEmergencia() - " + e.getMessage());
			return false;
		}
	}

	/**
	 * Endpoint para obtener el número total de emergencias registradas en la base
	 * de datos.
	 *
	 * @return ResponseEntity con el total de emergencias y el código HTTP
	 *         correspondiente.
	 */

	@GetMapping("/total")
	public ResponseEntity<Long> contarUsuarios() {
		Utilidades.ficheroLog(1, "EmergenciaControlador - contarUsuarios() - Entrada");
		try {
			long totalEmergencias = emergencias.contarEmergencias();
			return new ResponseEntity<>(totalEmergencias, HttpStatus.OK);
		} catch (Exception e) {
			Utilidades.ficheroLog(3, "EmergenciaControlador - contarUsuarios() - " + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@GetMapping("/total/{orgId}")
	public ResponseEntity<Long> contarEmergencias(@PathVariable Long orgId) {
		Utilidades.ficheroLog(1, "ServicioControlador - contarUsuarios() - Entrada");
		try {
			long totalServicios = emergencias.contarEmergencias(orgId);
			return new ResponseEntity<>(totalServicios, HttpStatus.OK);
		} catch (Exception e) {
			Utilidades.ficheroLog(3, "ServicioControlador - contarUsuarios() - " + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

}
