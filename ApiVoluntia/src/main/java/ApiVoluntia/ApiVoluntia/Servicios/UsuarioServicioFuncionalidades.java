package ApiVoluntia.ApiVoluntia.Servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ApiVoluntia.ApiVoluntia.Dtos.ServicioDtos;
import ApiVoluntia.ApiVoluntia.Dtos.UsuarioDtos;
import ApiVoluntia.ApiVoluntia.Dtos.UsuarioServicioDtos;
import ApiVoluntia.ApiVoluntia.Repositorios.ServicioInterfaz;
import ApiVoluntia.ApiVoluntia.Repositorios.UsuarioInterfaz;
import ApiVoluntia.ApiVoluntia.Repositorios.UsuarioServicioInterfaz;

/**
 * Servicio para gestionar la inscripción de usuarios a servicios.
 * 
 * @author DMN - 14/02/2025
 */
@Service
public class UsuarioServicioFuncionalidades {

	@Autowired
	private UsuarioServicioInterfaz usuarioServicioInterfaz;

	@Autowired
	private UsuarioInterfaz usuarioInterfaz;

	@Autowired
	private ServicioInterfaz servicioInterfaz;

	/**
	 * Permite que un usuario se apunte a un servicio.
	 * <p>
	 * Busca al usuario y al servicio por sus IDs. Si ambos existen y el usuario aún
	 * no está inscrito en el servicio, se crea la relación y se guarda.
	 * </p>
	 *
	 * @param usuarioId  ID del usuario.
	 * @param servicioId ID del servicio.
	 * @return Mensaje indicando el resultado de la inscripción.
	 * @author DMN - 14/02/2025
	 */
	public String apuntarUsuarioAServicio(Long usuarioId, Long servicioId) {
		UsuarioDtos usuario = usuarioInterfaz.findById(usuarioId)
				.orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

		ServicioDtos servicio = servicioInterfaz.findById(servicioId)
				.orElseThrow(() -> new RuntimeException("Servicio no encontrado"));

		if (usuarioServicioInterfaz.existsByUsuarioAndServicio(usuario, servicio)) {
			return "El usuario ya está inscrito en este servicio.";
		}

		UsuarioServicioDtos usuarioServicio = new UsuarioServicioDtos(usuario, servicio);
		usuarioServicioInterfaz.save(usuarioServicio);

		return "Usuario inscrito con éxito al servicio.";
	}

	/**
	 * Obtiene el número de usuarios inscritos en un servicio.
	 *
	 * @param servicioId ID del servicio.
	 * @return Número de usuarios inscritos en el servicio.
	 * @author DMN - 14/02/2025
	 */
	public long contarUsuariosEnServicio(Long servicioId) {
		return usuarioServicioInterfaz.countUsuariosByServicio(servicioId);
	}
}
