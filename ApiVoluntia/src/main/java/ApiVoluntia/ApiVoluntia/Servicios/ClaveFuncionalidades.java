package ApiVoluntia.ApiVoluntia.Servicios;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ApiVoluntia.ApiVoluntia.Dtos.ClaveDtos;
import ApiVoluntia.ApiVoluntia.Dtos.OrganizacionDtos;
import ApiVoluntia.ApiVoluntia.Repositorios.ClaveInterfaz;
import ApiVoluntia.ApiVoluntia.Repositorios.OrganizacionInterfaz;
import jakarta.transaction.Transactional;

@Service
/*
 * Clase encargada de contener todos los metodos relacionados con la entidad
 * Clave
 */
public class ClaveFuncionalidades {

	@Autowired
	ClaveInterfaz claveInterfaz;

	@Autowired
	OrganizacionInterfaz organizacionInterfaz;

	/*
	 * Metodo encargado de guardar todos los atributos que componen a una clave
	 * 
	 * @author DMN - 20/03/2025
	 */
	@Transactional
	public ClaveDtos guardarClave(ClaveDtos clave) {
		Long idOrg = clave.getOrganizacion() != null ? clave.getOrganizacion().getIdOrganizacion() : null;
		if (idOrg == null) {
			throw new IllegalArgumentException("Debe indicar idOrganizacion en el JSON");
		}
		OrganizacionDtos org = organizacionInterfaz.findById(idOrg)
				.orElseThrow(() -> new IllegalArgumentException("No existe organización con ID " + idOrg));
		clave.setOrganizacion(org);
		return claveInterfaz.save(clave);
	}

	@Transactional
	public List<ClaveDtos> listarPorOrganizacion(Long idOrg) {
		return claveInterfaz.findByOrganizacion_IdOrganizacion(idOrg);
	}

	/*
	 * Metodo encargado de devolcer todas las claves
	 * 
	 * @author DMN - 20/03/2025
	 */
	public ArrayList<ClaveDtos> getClaves() {
		return (ArrayList<ClaveDtos>) claveInterfaz.findAll();
	}

	/**
	 * Método encargado de eliminar una clave en base a su ID.
	 *
	 * @param id ID de la clvae a eliminar en formato String.
	 * @return true si la clave fue eliminado correctamente, false en caso
	 *         contrario.
	 * @author DMN - 25/03/2025
	 */
	@Transactional
	public boolean eliminarClave(String idClaveStr) {
		long idClave;
		try {
			idClave = Long.parseLong(idClaveStr);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("ID de clave no válido: " + idClaveStr);
		}
		ClaveDtos clave = claveInterfaz.findById(idClave)
				.orElseThrow(() -> new IllegalArgumentException("No existe clave con ID " + idClave));
		clave.setOrganizacion(null);

		claveInterfaz.delete(clave);
		return true;
	}

	/**
	 * Método encargado de modificar los datos de una clave en base a su ID.
	 *
	 * @param id    ID de la clave a modificar en formato String.
	 * @param clave Objeto ClaveDtos con los nuevos datos.
	 * @return true si la clave fue modificado con éxito, false en caso contrario.
	 * @author DMN - 25/03/2025
	 */
	public boolean modificarClave(String id, ClaveDtos clave) {
		boolean esModificado = false;
		Long idClave = Long.parseLong(id);

		ClaveDtos claveDtos = claveInterfaz.findByIdClave(idClave);

		if (claveDtos == null) {
			esModificado = false;
			System.out.println("La clave no existe");
		} else {

			claveDtos.setNombreClave(clave.getNombreClave());
			claveDtos.setDescripcionClave(clave.getDescripcionClave());
			claveInterfaz.save(claveDtos);
			esModificado = true;
			System.out.println("La clave ha sido modificada con éxito");
		}

		return esModificado;
	}

}
