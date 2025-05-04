package ApiVoluntia.ApiVoluntia.Controladores;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ApiVoluntia.ApiVoluntia.Dtos.OrganizacionDtos;
import ApiVoluntia.ApiVoluntia.Servicios.OrganizacionFuncionalidades;
import ApiVoluntia.ApiVoluntia.Utilidades.Utilidades;

@RestController
@RequestMapping("/api/organizacion")
public class OrganizacionControlador {

	@Autowired
	private OrganizacionFuncionalidades organizacionFuncionalidades;

	/*
	 * Endpoint para obtener la lista de todas las organizaciones existentes
	 * 
	 * @author - DMN 25/03/2025
	 */
	@GetMapping("/organizaciones")
	public ArrayList<OrganizacionDtos> listaOrganizaciones() {

		Utilidades.ficheroLog(1, "OrganizacionControlador - listaOrganizaciones() - Entrada");
		try {
			return organizacionFuncionalidades.getOrganizaciones();
		} catch (Exception e) {

			Utilidades.ficheroLog(3, "OrganizacionControlador - listaOrganizaciones() - " + e.getMessage());
			return new ArrayList<>();
		}

	}

	/**
	 * Endpoint para guardar una nueva clave.
	 *
	 * @param organizacion Objeto organizacionDtos con los datos de la organizacion
	 *                     a guardar
	 * @return La Organizacion guardada o null en caso de error.
	 */
	@PostMapping("/guardarorganizacion")
	public OrganizacionDtos guardarOrganizacion(@RequestBody OrganizacionDtos organizacion) {
		Utilidades.ficheroLog(1, "OrganizacionControlador - guardarOrganizacion() - Entrada");
		try {
			return organizacionFuncionalidades.guardarOrganizacion(organizacion);
		} catch (Exception e) {
			Utilidades.ficheroLog(3, "OrganizacionControlador - guardarOrganizacion() - " + e.getMessage());
			return null;
		}
	}

	/**
	 * Endpoint para eliminar una organizacion por su ID.
	 *
	 * @param id_organizacion ID de la organizacion a eliminar en formato String.
	 * @return true si la organizacion fue eliminada correctamente, false en caso de
	 *         error.
	 */
	@DeleteMapping("/eliminarorganizacion/{id_organizacion}")
	public boolean eliminarOrganizacion(@PathVariable("id_organizacion") String id_organizacion) {
	    Utilidades.ficheroLog(1, "OrganizacionControlador - eliminarOrganizacion() - Entrada");
	    try {
	        return organizacionFuncionalidades.eliminarOrganizacion(id_organizacion);
	    } catch (Exception e) {
	        Utilidades.ficheroLog(3, "OrganizacionControlador - eliminarOrganizacion() - " + e.getMessage());
	        return false;
	    }
	}

	/**
	 * Endpoint para modificar los datos de una clave.
	 *
	 * @param idOrganizacion ID de la organizacion a modificar en formato String.
	 * @param organizacion   Objeto organizacionDtos con los nuevos datos de la
	 *                       organizacion.
	 * @return true si la organizacion fue modificada correctamente, false en caso
	 *         de error.
	 */
	@PutMapping("/modificarorganizacion/{id_organizacion}")
	public boolean modificarOrganizacion(@PathVariable("id_organizacion") String idOrganizacion,
			@RequestBody OrganizacionDtos organizacion) {
		Utilidades.ficheroLog(1, "OrganizacionControlador - modificarOrganizacion() - Entrada");
		try {
			return organizacionFuncionalidades.modificarOrganizacion(idOrganizacion, organizacion);
		} catch (Exception e) {
			Utilidades.ficheroLog(3, "OrganizacionControlador - modificarOrganizacion() - " + e.getMessage());
			return false;
		}
	}

}
