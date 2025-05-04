package ApiVoluntia.ApiVoluntia.Repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ApiVoluntia.ApiVoluntia.Dtos.ServicioDtos;

public interface ServicioInterfaz extends JpaRepository<ServicioDtos, Long> {

	ServicioDtos findByIdServicio(long id_servicio);

	void deleteByIdServicio(long id);

	List<ServicioDtos> findByOrganizacion_IdOrganizacion(Long idOrganizacion);

	long countByOrganizacion_IdOrganizacion(Long idOrganizacion);

}
