package ApiVoluntia.ApiVoluntia.Repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ApiVoluntia.ApiVoluntia.Dtos.EmergenciaDtos;

public interface EmergenciaInterfaz extends JpaRepository<EmergenciaDtos, Long> {

	EmergenciaDtos findByIdEmergencia(long id_emergencia);

	void deleteByIdEmergencia(long id);

	List<EmergenciaDtos> findByOrganizacion_IdOrganizacion(Long idOrganizacion);

	long countByOrganizacion_IdOrganizacion(Long idOrganizacion);

}
