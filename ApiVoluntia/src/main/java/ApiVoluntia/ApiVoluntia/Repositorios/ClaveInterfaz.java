package ApiVoluntia.ApiVoluntia.Repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ApiVoluntia.ApiVoluntia.Dtos.ClaveDtos;

public interface ClaveInterfaz extends JpaRepository<ClaveDtos, Long> {

	ClaveDtos findByIdClave(long id_clave);

	void deleteByIdClave(long id);

	List<ClaveDtos> findByOrganizacion_IdOrganizacion(Long idOrganizacion);

}
