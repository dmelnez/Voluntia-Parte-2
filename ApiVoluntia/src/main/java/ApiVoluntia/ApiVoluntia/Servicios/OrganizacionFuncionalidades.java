package ApiVoluntia.ApiVoluntia.Servicios;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ApiVoluntia.ApiVoluntia.Dtos.OrganizacionDtos;
import ApiVoluntia.ApiVoluntia.Repositorios.ContratoInterfaz;
import ApiVoluntia.ApiVoluntia.Repositorios.OrganizacionInterfaz;

import jakarta.transaction.Transactional;

@Service
public class OrganizacionFuncionalidades {

	@Autowired
	OrganizacionInterfaz organizacionInterfaz;

	@Autowired
	ContratoInterfaz contratoInterfaz;
	
	/*
	 * Metodo encargado de guardar todos los atributos que componen a una
	 * organizacion
	 * 
	 * @author DMN - 25/03/2025
	 */
	public OrganizacionDtos guardarOrganizacion(OrganizacionDtos organizacion) {
		return organizacionInterfaz.save(organizacion);
	}

	/*
	 * Metodo encargado de devolcer todas las organizaciones
	 * 
	 * @author DMN - 25/03/2025
	 */
	public ArrayList<OrganizacionDtos> getOrganizaciones() {
		return (ArrayList<OrganizacionDtos>) organizacionInterfaz.findAll();
	}

	/**
	 * Método encargado de eliminar una organizacion en base a su ID.
	 *
	 * @param id ID de la organizacion a eliminar en formato String.
	 * @return true si la organizacion fue eliminado correctamente, false en caso
	 *         contrario.
	 * @author DMN - 25/03/2025
	 */
	@Transactional
	public boolean eliminarOrganizacion(String id) {
	    try {
	        long idOrganizacion = Long.parseLong(id);

	        // Buscar la organización en la base de datos
	        OrganizacionDtos organizacion = organizacionInterfaz.findById(idOrganizacion)
	                .orElse(null);

	        if (organizacion == null) {
	            System.out.println("No se encontró la organización con el ID proporcionado.");
	            return false;
	        }

	        // Eliminar el contrato relacionado si existe
	        if (organizacion.getContrato() != null) {
	            // Aquí eliminamos el contrato asociado a la organización
	            contratoInterfaz.delete(organizacion.getContrato());
	            System.out.println("Contrato eliminado correctamente.");
	        }

	        // Eliminar las relaciones de los usuarios (si existen)
	        if (organizacion.getUsuarios() != null && !organizacion.getUsuarios().isEmpty()) {
	            organizacion.getUsuarios().clear();  // Eliminar las relaciones de los usuarios
	            System.out.println("Usuarios desvinculados de la organización.");
	        }

	        // Ahora eliminamos la organización
	        organizacionInterfaz.deleteById(idOrganizacion);
	        System.out.println("Organización eliminada correctamente.");

	        return true;

	    } catch (NumberFormatException e) {
	        System.out.println("Error: El ID proporcionado no es válido. " + e.getMessage());
	        return false;
	    } catch (Exception e) {
	        System.out.println("Ocurrió un error inesperado: " + e.getMessage());
	        e.printStackTrace();
	        return false;
	    }
	}





	/**
	 * Método encargado de modificar los datos de una Organizacion en base a su ID.
	 *
	 * @param id           ID de la Organizacion a modificar en formato String.
	 * @param Organizacion Objeto OrganizacionDtOS con los nuevos datos.
	 * @return true si la clave fue modificado con éxito, false en caso contrario.
	 * @author DMN - 26/03/2025
	 */
	public boolean modificarOrganizacion(String id, OrganizacionDtos organizacion) {
		boolean esModificado = false;
		Long idOrganizacion = Long.parseLong(id);

		OrganizacionDtos organizacionDtos = organizacionInterfaz.findByIdOrganizacion(idOrganizacion);

		if (organizacionDtos == null) {
			esModificado = false;
			System.out.println("La organizacion no existe");
		} else {

			organizacionDtos.setCiudadOrganizacion(organizacion.getCiudadOrganizacion());
			organizacionDtos.setDireccionOrganizacion(organizacion.getCiudadOrganizacion());
			organizacionDtos.setEmailOrganizacion(organizacion.getEmailOrganizacion());
			organizacionDtos.setFechaAltaIntitucion(organizacion.getFechaAltaIntitucion());
			organizacionDtos.setNifOrganizacion(organizacion.getNifOrganizacion());
			organizacionDtos.setNombreOrganizacion(organizacion.getNombreOrganizacion());
			organizacionDtos.setProvinciaOrganizacion(organizacion.getProvinciaOrganizacion());
			organizacionDtos.setTelefonoOrganizacion(organizacion.getTelefonoOrganizacion());
			organizacionDtos.setTipoInstitucionOrganizacion(organizacion.getTipoInstitucionOrganizacion());
			organizacionInterfaz.save(organizacionDtos);
			esModificado = true;
			System.out.println("La organizacion ha sido modificada con éxito");
		}

		return esModificado;
	}

}
