package ApiVoluntia.ApiVoluntia.Servicios;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ApiVoluntia.ApiVoluntia.Dtos.OrganizacionDtos;
import ApiVoluntia.ApiVoluntia.Dtos.VehiculoDtos;
import ApiVoluntia.ApiVoluntia.Repositorios.OrganizacionInterfaz;
import ApiVoluntia.ApiVoluntia.Repositorios.VehiculoInterfaz;
import ApiVoluntia.ApiVoluntia.Repositorios.VehiculoServicioInterfaz;
import jakarta.transaction.Transactional;

@Service
public class VehiculoFuncionalidades {

	@Autowired
	VehiculoInterfaz vehiculoInferfaz;

	@Autowired
	VehiculoServicioInterfaz vehiculoServicioInterfaz;

	@Autowired
	OrganizacionInterfaz organizacionInterfaz;

	/**
	 * Método encargado de buscar todos los vehículos existentes en la base de
	 * datos.
	 * 
	 * @return Lista de vehículos (ArrayList de VehiculoDtos).
	 * @author DMN - 14/02/2025
	 */
	public ArrayList<VehiculoDtos> getVehiculo() {
		return (ArrayList<VehiculoDtos>) vehiculoInferfaz.findAll();
	}

	/**
	 * Método encargado de guardar un nuevo vehículo en la base de datos.
	 * 
	 * @param vehiculo Objeto VehiculoDtos a guardar.
	 * @return Objeto VehiculoDtos guardado.
	 * @author DMN - 14/02/2025
	 */
	@Transactional
	public VehiculoDtos guardarVehiculo(VehiculoDtos vehiculo) {
		Long idOrg = vehiculo.getOrganizacion() != null ? vehiculo.getOrganizacion().getIdOrganizacion() : null;

		if (idOrg == null) {
			throw new IllegalArgumentException("Debe indicar idOrganizacion en el JSON");
		}

		OrganizacionDtos org = organizacionInterfaz.findById(idOrg)
				.orElseThrow(() -> new IllegalArgumentException("No existe organización con ID " + idOrg));

		vehiculo.setOrganizacion(org);
		return vehiculoInferfaz.save(vehiculo);
	}

	/**
	 * Método encargado de eliminar un vehículo de la base de datos a partir de su
	 * ID.
	 * 
	 * @param id Identificador del vehículo a eliminar.
	 * @return true si el vehículo fue eliminado correctamente, false en caso
	 *         contrario.
	 * @author DMN - 14/02/2025
	 */
	@Transactional
	public boolean eliminarVehiculo(String idVehiculoStr) {
		long idVehiculo;
		try {
			idVehiculo = Long.parseLong(idVehiculoStr);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("ID de vehículo no válido: " + idVehiculoStr);
		}

		VehiculoDtos vehiculo = vehiculoInferfaz.findById(idVehiculo)
				.orElseThrow(() -> new IllegalArgumentException("No existe vehículo con ID " + idVehiculo));

		vehiculoServicioInterfaz.deleteByVehiculo(vehiculo);

		vehiculo.setOrganizacion(null);
		vehiculoInferfaz.save(vehiculo);

		vehiculoInferfaz.delete(vehiculo);

		return true;
	}

	@Transactional
	public List<VehiculoDtos> listarPorOrganizacion(Long idOrganizacion) {
		return vehiculoInferfaz.findByOrganizacion_IdOrganizacion(idOrganizacion);
	}

	/**
	 * Método encargado de modificar los datos de un vehículo en base a su ID.
	 * 
	 * @param id       Identificador del vehículo a modificar.
	 * @param vehiculo Objeto VehiculoDtos con los nuevos datos.
	 * @return true si el vehículo fue modificado exitosamente, false en caso
	 *         contrario.
	 * @author DMN - 14/02/2025
	 */
	public boolean modificarVehiculo(String id, VehiculoDtos vehiculo) {
		boolean esModificado = false;
		Long idVehiculo = Long.parseLong(id);

		VehiculoDtos vehiculoDtos = vehiculoInferfaz.findByIdVehiculo(idVehiculo);

		if (vehiculoDtos == null) {
			esModificado = false;
			System.out.println("El vehículo no existe");
		} else {
			vehiculoDtos.setMarcaVehiculo(vehiculo.getMarcaVehiculo());
			vehiculoDtos.setModeloVehiculo(vehiculo.getModeloVehiculo());
			vehiculoDtos.setMatriculaVehiculo(vehiculo.getMatriculaVehiculo());
			vehiculoDtos.setCombustibleVehiculo(vehiculo.getCombustibleVehiculo());
			vehiculoDtos.setTipoVehiculo(vehiculo.getTipoVehiculo());
			vehiculoDtos.setIndicativoVehiculo(vehiculo.getIndicativoVehiculo());
			vehiculoDtos.setRecursoVehiculo(vehiculo.getRecursoVehiculo());
			vehiculoDtos.setTipoCabinaVehiculo(vehiculo.getTipoCabinaVehiculo());

			vehiculoInferfaz.save(vehiculoDtos);
			esModificado = true;
			System.out.println("El vehículo ha sido modificado con éxito");
		}

		return esModificado;
	}

	/**
	 * Método encargado de obtener la cantidad total de vehículos registrados en la
	 * base de datos.
	 * 
	 * @return Total de vehículos (long).
	 * @author DMN - 14/02/2025
	 */
	public long contarVehiculos() {
		return vehiculoInferfaz.count();
	}
}
