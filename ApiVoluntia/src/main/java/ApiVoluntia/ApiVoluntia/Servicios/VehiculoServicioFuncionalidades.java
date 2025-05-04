package ApiVoluntia.ApiVoluntia.Servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ApiVoluntia.ApiVoluntia.Dtos.ServicioDtos;
import ApiVoluntia.ApiVoluntia.Dtos.VehiculoDtos;
import ApiVoluntia.ApiVoluntia.Dtos.VehiculoServicioDtos;
import ApiVoluntia.ApiVoluntia.Repositorios.ServicioInterfaz;
import ApiVoluntia.ApiVoluntia.Repositorios.VehiculoInterfaz;
import ApiVoluntia.ApiVoluntia.Repositorios.VehiculoServicioInterfaz;

@Service
public class VehiculoServicioFuncionalidades {

	@Autowired
	private VehiculoServicioInterfaz vehiculoServicioInterfaz;

	@Autowired
	private VehiculoInterfaz vehiculoInterfaz;

	@Autowired
	private ServicioInterfaz servicioInterfaz;

	/**
	 * Apunta un vehículo a un servicio.
	 * <p>
	 * Se buscan el vehículo y el servicio por sus IDs. Si se encuentran y el
	 * vehículo no está ya inscrito en el servicio, se crea la relación.
	 * </p>
	 * 
	 * @param vehiculoId ID del vehículo.
	 * @param servicioId ID del servicio.
	 * @return Mensaje indicando el resultado de la inscripción.
	 */
	public String apuntarVehiculoAServicio(Long vehiculoId, Long servicioId) {
		VehiculoDtos vehiculo = vehiculoInterfaz.findById(vehiculoId)
				.orElseThrow(() -> new RuntimeException("Vehículo no encontrado"));
		ServicioDtos servicio = servicioInterfaz.findById(servicioId)
				.orElseThrow(() -> new RuntimeException("Servicio no encontrado"));

		if (vehiculoServicioInterfaz.existsByVehiculoAndServicio(vehiculo, servicio)) {
			return "El vehículo ya está inscrito en este servicio.";
		}

		VehiculoServicioDtos vehiculoServicio = new VehiculoServicioDtos(vehiculo, servicio);
		vehiculoServicioInterfaz.save(vehiculoServicio);

		return "Vehículo inscrito con éxito al servicio.";
	}

	/**
	 * Cuenta la cantidad de vehículos inscritos en un servicio.
	 * 
	 * @param servicioId ID del servicio.
	 * @return Número de vehículos inscritos.
	 */
	public long contarVehiculosEnServicio(Long servicioId) {
		return vehiculoServicioInterfaz.countVehiculosByServicio(servicioId);
	}
}
