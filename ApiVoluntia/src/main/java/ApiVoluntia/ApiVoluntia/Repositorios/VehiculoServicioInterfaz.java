package ApiVoluntia.ApiVoluntia.Repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ApiVoluntia.ApiVoluntia.Dtos.ServicioDtos;
import ApiVoluntia.ApiVoluntia.Dtos.VehiculoDtos;
import ApiVoluntia.ApiVoluntia.Dtos.VehiculoServicioDtos;

public interface VehiculoServicioInterfaz extends JpaRepository<VehiculoServicioDtos, Long> {

	boolean existsByVehiculoAndServicio(VehiculoDtos vehiculo, ServicioDtos servicio);

	@Query("SELECT COUNT(vs) FROM VehiculoServicioDtos vs WHERE vs.servicio.idServicio = :servicioId")
	long countVehiculosByServicio(@Param("servicioId") Long servicioId);

	void deleteByVehiculo(VehiculoDtos vehiculo);

	void deleteByServicio(ServicioDtos servicio);
}
