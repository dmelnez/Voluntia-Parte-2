package ApiVoluntia.ApiVoluntia.Repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ApiVoluntia.ApiVoluntia.Dtos.VehiculoDtos;

public interface VehiculoInterfaz extends JpaRepository<VehiculoDtos, Long> {

	VehiculoDtos findByIdVehiculo(long id_vehiculo);

	void deleteByIdVehiculo(long id);

	List<VehiculoDtos> findByOrganizacion_IdOrganizacion(Long idOrganizacion);

}
