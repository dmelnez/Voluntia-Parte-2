package ApiVoluntia.ApiVoluntia.Repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import ApiVoluntia.ApiVoluntia.Dtos.UsuarioDtos;

public interface UsuarioInterfaz extends JpaRepository<UsuarioDtos, Long> {
	UsuarioDtos findByTokenUsuario(String tokenUsuario);

	UsuarioDtos findByCorreoUsuario(String correo_usuario);

	UsuarioDtos findByIdUsuario(long id_usuario);

	void deleteByIdUsuario(long id);

	boolean existsByTelefonoUsuario(String telefonoUsuario);

	boolean existsByCorreoUsuario(String correoUsuario);

	int countByCorreoUsuarioIgnoreCase(String correoUsuario);

	List<UsuarioDtos> findByOrganizacion_IdOrganizacion(Long idOrganizacion);

	long countByOrganizacion_IdOrganizacion(Long idOrganizacion);

	long countByOrganizacion_IdOrganizacionAndRolUsuario(Long idOrganizacion, String rolUsuario);
}