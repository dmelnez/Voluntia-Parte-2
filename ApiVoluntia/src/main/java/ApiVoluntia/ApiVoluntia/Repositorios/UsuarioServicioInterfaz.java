package ApiVoluntia.ApiVoluntia.Repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ApiVoluntia.ApiVoluntia.Dtos.ServicioDtos;
import ApiVoluntia.ApiVoluntia.Dtos.UsuarioDtos;
import ApiVoluntia.ApiVoluntia.Dtos.UsuarioServicioDtos;

public interface UsuarioServicioInterfaz extends JpaRepository<UsuarioServicioDtos, Long> {

	boolean existsByUsuarioAndServicio(UsuarioDtos usuario, ServicioDtos servicio);

	@Query("SELECT COUNT(us) FROM UsuarioServicioDtos us WHERE us.servicio.idServicio = :servicioId")
	long countUsuariosByServicio(@Param("servicioId") Long servicioId);

	void deleteByUsuario(UsuarioDtos usuario);

	void deleteByServicio(ServicioDtos servicio);
}
