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

import ApiVoluntia.ApiVoluntia.Servicios.VehiculoServicioFuncionalidades;
import ApiVoluntia.ApiVoluntia.Utilidades.Utilidades;

/**
 * Controlador REST para la gestión de inscripciones de vehículos a servicios.
 * <p>
 * Proporciona endpoints para inscribir un vehículo en un servicio y para contar
 * cuántos vehículos están inscritos en un servicio.
 * </p>
 * 
 * @author DMN
 */
@RestController
@RequestMapping("/api/vehiculo-servicio")
public class VehiculoServicioControlador {

	@Autowired
	private VehiculoServicioFuncionalidades vehiculoServicioFuncionalidades;

	/**
	 * Endpoint para inscribir un vehículo en un servicio.
	 * 
	 * @param vehiculoId ID del vehículo a inscribir.
	 * @param servicioId ID del servicio en el que se inscribe el vehículo.
	 * @return ResponseEntity con un mensaje indicando el resultado de la
	 *         inscripción.
	 */
	@PostMapping("/inscribir")
	public ResponseEntity<String> inscribirVehiculo(@RequestParam Long vehiculoId, @RequestParam Long servicioId) {
		Utilidades.ficheroLog(1, "VehiculoServicioControlador - inscribirVehiculo() - Entrada");
		try {
			String mensaje = vehiculoServicioFuncionalidades.apuntarVehiculoAServicio(vehiculoId, servicioId);
			return ResponseEntity.ok(mensaje);
		} catch (Exception e) {
			Utilidades.ficheroLog(3, "VehiculoServicioControlador - inscribirVehiculo() - " + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error al inscribir vehículo en el servicio.");
		}
	}

	/**
	 * Endpoint para contar cuántos vehículos están inscritos en un servicio.
	 * 
	 * @param servicioId ID del servicio del cual se contará el número de vehículos
	 *                   inscritos.
	 * @return ResponseEntity con la cantidad de vehículos inscritos.
	 */
	@GetMapping("/contar/{servicioId}")
	public ResponseEntity<Long> contarVehiculos(@PathVariable Long servicioId) {
		Utilidades.ficheroLog(1, "VehiculoServicioControlador - contarVehiculos() - Entrada");
		try {
			long cantidad = vehiculoServicioFuncionalidades.contarVehiculosEnServicio(servicioId);
			return ResponseEntity.ok(cantidad);
		} catch (Exception e) {
			Utilidades.ficheroLog(3, "VehiculoServicioControlador - contarVehiculos() - " + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(0L);
		}
	}
}
