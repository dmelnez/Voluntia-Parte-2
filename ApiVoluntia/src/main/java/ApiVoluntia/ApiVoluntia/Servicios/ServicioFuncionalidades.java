package ApiVoluntia.ApiVoluntia.Servicios;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ApiVoluntia.ApiVoluntia.Dtos.OrganizacionDtos;
import ApiVoluntia.ApiVoluntia.Dtos.ServicioDtos;
import ApiVoluntia.ApiVoluntia.Repositorios.OrganizacionInterfaz;
import ApiVoluntia.ApiVoluntia.Repositorios.ServicioInterfaz;
import ApiVoluntia.ApiVoluntia.Repositorios.UsuarioServicioInterfaz;
import ApiVoluntia.ApiVoluntia.Repositorios.VehiculoServicioInterfaz;
import jakarta.transaction.Transactional;

@Service
public class ServicioFuncionalidades {

	@Autowired
	ServicioInterfaz servicioInferfaz;

	@Autowired
	UsuarioServicioInterfaz usuarioServicioInterfaz;

	@Autowired
	VehiculoServicioInterfaz vehiculoServicioInterfaz;

	@Autowired
	OrganizacionInterfaz organizacionInterfaz;

	/**
	 * Método encargado de buscar todos los servicios existentes en la base de
	 * datos.
	 * 
	 * @return ArrayList de ServicioDtos con todos los servicios.
	 * @author DMN - 14/02/2025
	 */
	public ArrayList<ServicioDtos> getServicio() {
		return (ArrayList<ServicioDtos>) servicioInferfaz.findAll();
	}

	/**
	 * Método encargado de guardar un nuevo servicio en la base de datos.
	 * 
	 * @param servicio Objeto ServicioDtos a guardar.
	 * @return El servicio guardado.
	 * @author DMN - 14/02/2025
	 */
	/** Guarda un servicio asociándolo a la organización indicada */
	@Transactional
	public ServicioDtos guardarServicio(ServicioDtos servicio) {
		Long idOrg = servicio.getOrganizacion() != null ? servicio.getOrganizacion().getIdOrganizacion() : null;

		if (idOrg == null) {
			throw new IllegalArgumentException("Debe indicar idOrganizacion en el JSON");
		}

		OrganizacionDtos org = organizacionInterfaz.findById(idOrg)
				.orElseThrow(() -> new IllegalArgumentException("No existe organización con ID " + idOrg));

		servicio.setOrganizacion(org);
		return servicioInferfaz.save(servicio);
	}

	/**
	 * Método encargado de eliminar un servicio de la base de datos.
	 * <p>
	 * Convierte el ID a tipo long, verifica si el servicio existe y, en caso
	 * afirmativo, elimina las relaciones en la tabla intermedia y finalmente
	 * elimina el servicio.
	 * </p>
	 * 
	 * @param id ID del servicio a eliminar en formato String.
	 * @return true si el servicio fue eliminado correctamente, false en caso
	 *         contrario.
	 * @author DMN - 14/02/2025
	 */
	@Transactional
	public boolean eliminarServicio(String idServicioStr) {
		long idServicio;
		try {
			idServicio = Long.parseLong(idServicioStr);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("ID de servicio no válido: " + idServicioStr);
		}

		ServicioDtos servicio = servicioInferfaz.findById(idServicio)
				.orElseThrow(() -> new IllegalArgumentException("No existe servicio con ID " + idServicio));
		vehiculoServicioInterfaz.deleteByServicio(servicio);

		usuarioServicioInterfaz.deleteByServicio(servicio);

		servicio.setOrganizacion(null);
		servicioInferfaz.save(servicio);

		servicioInferfaz.delete(servicio);

		return true;
	}

	@Transactional
	public List<ServicioDtos> listarPorOrganizacion(Long idOrganizacion) {
		return servicioInferfaz.findByOrganizacion_IdOrganizacion(idOrganizacion);
	}

	/**
	 * Método encargado de eliminar todos los servicios de la base de datos.
	 * 
	 * @return true si se eliminaron correctamente, false en caso de error.
	 * @author DMN - 14/02/2025
	 */
	public boolean eliminarTodosServicios() {
		try {
			servicioInferfaz.deleteAll();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Método encargado de modificar los datos de un servicio en base a su ID.
	 * 
	 * @param id       ID del servicio a modificar en formato String.
	 * @param servicio Objeto ServicioDtos con los nuevos datos.
	 * @return true si el servicio fue modificado exitosamente, false en caso
	 *         contrario.
	 * @author DMN - 14/02/2025
	 */
	public boolean modificarServicio(String id, ServicioDtos servicio) {
		boolean esModificado = false;
		Long idServicio = Long.parseLong(id);

		ServicioDtos servicioDtos = servicioInferfaz.findByIdServicio(idServicio);

		if (servicioDtos == null) {
			esModificado = false;
			System.out.println("El Servicio no existe");
		} else {
			servicioDtos.setNumeracionServicio(servicio.getNumeracionServicio());
			servicioDtos.setTituloServicio(servicio.getTituloServicio());
			servicioDtos.setMaximoAsistentesServicio(servicio.getMaximoAsistentesServicio());
			servicioDtos.setTipoServicio(servicio.getTipoServicio());
			servicioDtos.setCategoriaServicio(servicio.getCategoriaServicio());
			servicioDtos.setDescripcionServicio(servicio.getDescripcionServicio());

			servicioInferfaz.save(servicioDtos);
			esModificado = true;
			System.out.println("El Servicio ha sido modificado con éxito");
		}
		return esModificado;
	}

	/**
	 * Método encargado de obtener la cantidad total de servicios registrados en la
	 * base de datos.
	 * 
	 * @return long - Total de servicios.
	 * @author DMN - 01/02/2025
	 */

	public long contarServicios() {
		return servicioInferfaz.count();
	}

	public long contarServicios(Long orgId) {
		return servicioInferfaz.countByOrganizacion_IdOrganizacion(orgId);
	}

}
