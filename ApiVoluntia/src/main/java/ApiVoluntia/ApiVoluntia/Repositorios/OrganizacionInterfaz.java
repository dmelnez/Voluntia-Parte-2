package ApiVoluntia.ApiVoluntia.Repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import ApiVoluntia.ApiVoluntia.Dtos.OrganizacionDtos;

public interface OrganizacionInterfaz extends JpaRepository<OrganizacionDtos, Long> {

	OrganizacionDtos findByIdOrganizacion(long id_organizacion);

	void deleteByIdOrganizacion(long id);

}
