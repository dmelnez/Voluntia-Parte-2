package ApiVoluntia.ApiVoluntia.Controladores;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

import ApiVoluntia.ApiVoluntia.Dtos.OrganizacionDtos;
import ApiVoluntia.ApiVoluntia.Dtos.ServicioDtos;
import ApiVoluntia.ApiVoluntia.Repositorios.OrganizacionInterfaz;
import ApiVoluntia.ApiVoluntia.Repositorios.ServicioInterfaz;
import ApiVoluntia.ApiVoluntia.Servicios.ServicioFuncionalidades;
import ApiVoluntia.ApiVoluntia.Utilidades.Utilidades;

/**
 * Controlador REST para la gestión de servicios.
 * <p>
 * Proporciona endpoints para obtener, guardar, eliminar y modificar servicios,
 * así como para obtener el total de servicios registrados.
 * </p>
 * 
 * @author DMN
 */
@RestController
@RequestMapping("/api/servicio")
public class ServicioControlador {

	@Autowired
	ServicioFuncionalidades servicios;

	@Autowired
	ServicioInterfaz servicioInterfaz;

	@Autowired
	OrganizacionInterfaz organizacionInterfaz;

	@Autowired
	private Utilidades utilidades;

	/**
	 * Endpoint para obtener la lista de todos los servicios.
	 *
	 * @return ArrayList de ServicioDtos que contiene todos los servicios
	 *         registrados.
	 */
	@GetMapping("/servicios")
	public ArrayList<ServicioDtos> listaServicios() {
		utilidades.ficheroLog(1, "ServicioControlador - listaServicios() - Entrada");
		try {
			return servicios.getServicio();
		} catch (Exception e) {
			utilidades.ficheroLog(3, "ServicioControlador - listaServicios() - " + e.getMessage());
			// En caso de error se retorna una lista vacía.
			return new ArrayList<>();
		}
	}

	@PostMapping("/guardarservicio")
	public ServicioDtos guardarServicio(@RequestBody ServicioDtos servicio) {
		utilidades.ficheroLog(1, "ServicioControlador - guardarServicio() - Entrada");
		try {
			return servicios.guardarServicio(servicio);
		} catch (Exception e) {
			utilidades.ficheroLog(3, "ServicioControlador - guardarServicio() - " + e.getMessage());
			throw e;
		}
	}

	@GetMapping("/organizacion/{id_org}")
	public List<ServicioDtos> listarPorOrganizacion(@PathVariable("id_org") Long idOrg) {
		return servicios.listarPorOrganizacion(idOrg);
	}

	/**
	 * Endpoint para eliminar un servicio por su ID.
	 *
	 * @param id_servicio ID del servicio a eliminar.
	 * @return true si el servicio fue eliminado correctamente, false en caso de
	 *         error.
	 */
	@DeleteMapping("/eliminarservicio/{id_servicio}")
	public boolean deleteServicio(@PathVariable("id_servicio") String idServicio) {
		utilidades.ficheroLog(1, "ServicioControlador - deleteServicio() - Entrada");
		try {
			return servicios.eliminarServicio(idServicio);
		} catch (Exception e) {
			utilidades.ficheroLog(3, "ServicioControlador - deleteServicio() - " + e.getMessage());
			return false;
		}
	}

	/**
	 * Endpoint para modificar los datos de un servicio existente.
	 *
	 * @param idServicio ID del servicio a modificar.
	 * @param servicio   Objeto ServicioDtos con los nuevos datos.
	 * @return true si el servicio fue modificado correctamente, false en caso de
	 *         error.
	 */
	@PutMapping("/modificarservicio/{id_servicio}")
	public boolean putServicio(@PathVariable("id_servicio") String idServicio, @RequestBody ServicioDtos servicio) {
		utilidades.ficheroLog(1, "ServicioControlador - putServicio() - Entrada");
		try {
			return servicios.modificarServicio(idServicio, servicio);
		} catch (Exception e) {
			utilidades.ficheroLog(3, "ServicioControlador - putServicio() - " + e.getMessage());
			return false;
		}
	}

	/**
	 * Endpoint para obtener el número total de servicios registrados en la base de
	 * datos.
	 *
	 * @return ResponseEntity con el total de servicios y el código HTTP
	 *         correspondiente.
	 */

	@GetMapping("/total")
	public ResponseEntity<Long> contarUsuarios() {
		utilidades.ficheroLog(1, "ServicioControlador - contarUsuarios() - Entrada");
		try {
			long totalServicios = servicios.contarServicios();
			return new ResponseEntity<>(totalServicios, HttpStatus.OK);
		} catch (Exception e) {
			utilidades.ficheroLog(3, "ServicioControlador - contarUsuarios() - " + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@GetMapping("/total/{orgId}")
	public ResponseEntity<Long> contarServicios(@PathVariable Long orgId) {
		utilidades.ficheroLog(1, "ServicioControlador - contarUsuarios() - Entrada");
		try {
			long totalServicios = servicios.contarServicios(orgId);
			return new ResponseEntity<>(totalServicios, HttpStatus.OK);
		} catch (Exception e) {
			utilidades.ficheroLog(3, "ServicioControlador - contarUsuarios() - " + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

}
