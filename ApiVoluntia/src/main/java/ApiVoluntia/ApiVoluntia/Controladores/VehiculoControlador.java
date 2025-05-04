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

import ApiVoluntia.ApiVoluntia.Dtos.VehiculoDtos;
import ApiVoluntia.ApiVoluntia.Servicios.VehiculoFuncionalidades;
import ApiVoluntia.ApiVoluntia.Utilidades.Utilidades;

/**
 * Controlador REST para la gestión de vehículos.
 * <p>
 * Proporciona endpoints para obtener, guardar, eliminar y modificar vehículos,
 * así como para obtener el total de vehículos registrados.
 * </p>
 * 
 * @author DMN
 */
@RestController
@RequestMapping("/api/vehiculo")
public class VehiculoControlador {

	@Autowired
	VehiculoFuncionalidades vehiculos;

	/**
	 * Endpoint para obtener la lista de vehículos.
	 *
	 * @return ArrayList de VehiculoDtos que contiene todos los vehículos
	 *         registrados.
	 */
	@GetMapping("/vehiculos")
	public ArrayList<VehiculoDtos> listaVehiculos() {
		Utilidades.ficheroLog(1, "VehiculoControlador - listaVehiculos() - Entrada");
		try {
			return vehiculos.getVehiculo();
		} catch (Exception e) {
			Utilidades.ficheroLog(3, "VehiculoControlador - listaVehiculos() - " + e.getMessage());
			return new ArrayList<>();
		}
	}

	/**
	 * Endpoint para guardar un nuevo vehículo.
	 *
	 * @param vehiculo Objeto VehiculoDtos con los datos del vehículo a guardar.
	 * @return VehiculoDtos el vehículo guardado o null en caso de error.
	 */
	@PostMapping("/guardarvehiculo")
	public VehiculoDtos guardarVehiculo(@RequestBody VehiculoDtos vehiculo) {
		Utilidades.ficheroLog(1, "VehiculoControlador - guardarVehiculo() - Entrada");
		try {
			return vehiculos.guardarVehiculo(vehiculo);
		} catch (Exception e) {
			Utilidades.ficheroLog(3, "VehiculoControlador - guardarVehiculo() - " + e.getMessage());
			throw e;
		}
	}

	/**
	 * Endpoint para eliminar un vehículo por su ID.
	 *
	 * @param id_vehiculo ID del vehículo a eliminar.
	 * @return true si el vehículo fue eliminado exitosamente, false en caso de
	 *         error.
	 */
	@DeleteMapping("/eliminarvehiculo/{id_vehiculo}")
	public boolean deleteVehiculo(@PathVariable("id_vehiculo") String idVehiculo) {
		Utilidades.ficheroLog(1, "VehiculoControlador - deleteVehiculo() - Entrada");
		try {
			return vehiculos.eliminarVehiculo(idVehiculo);
		} catch (Exception e) {
			Utilidades.ficheroLog(3, "VehiculoControlador - deleteVehiculo() - " + e.getMessage());
			return false;
		}
	}

	@GetMapping("/organizacion/{id_org}")
	public List<VehiculoDtos> listarPorOrganizacion(@PathVariable("id_org") Long idOrg) {
		Utilidades.ficheroLog(1, "VehiculoControlador - listarPorOrganizacion() - Entrada");
		return vehiculos.listarPorOrganizacion(idOrg);
	}

	/**
	 * Endpoint para actualizar los datos de un vehículo.
	 *
	 * @param idVehiculo ID del vehículo a modificar.
	 * @param vehiculo   Objeto VehiculoDtos con los nuevos datos del vehículo.
	 * @return true si el vehículo fue modificado correctamente, false en caso de
	 *         error.
	 */
	@PutMapping("/modificarvehiculo/{id_vehiculo}")
	public boolean putVehiculo(@PathVariable("id_vehiculo") String idVehiculo, @RequestBody VehiculoDtos vehiculo) {
		Utilidades.ficheroLog(1, "VehiculoControlador - putVehiculo() - Entrada");
		try {
			return vehiculos.modificarVehiculo(idVehiculo, vehiculo);
		} catch (Exception e) {
			Utilidades.ficheroLog(3, "VehiculoControlador - putVehiculo() - " + e.getMessage());
			return false;
		}
	}

	/**
	 * Endpoint para obtener el número total de vehículos registrados en la base de
	 * datos.
	 *
	 * @return ResponseEntity con el total de vehículos y el código HTTP
	 *         correspondiente.
	 */
	@GetMapping("/total")
	public ResponseEntity<Long> contarVehiculos() {
		Utilidades.ficheroLog(1, "VehiculoControlador - contarVehiculos() - Entrada");
		try {
			long totalVehiculos = vehiculos.contarVehiculos();
			return new ResponseEntity<>(totalVehiculos, HttpStatus.OK);
		} catch (Exception e) {
			Utilidades.ficheroLog(3, "VehiculoControlador - contarVehiculos() - " + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(0L);
		}
	}
}
