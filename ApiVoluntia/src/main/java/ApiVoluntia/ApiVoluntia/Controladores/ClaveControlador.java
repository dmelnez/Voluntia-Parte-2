package ApiVoluntia.ApiVoluntia.Controladores;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ApiVoluntia.ApiVoluntia.Dtos.ClaveDtos;
import ApiVoluntia.ApiVoluntia.Dtos.EmergenciaDtos;
import ApiVoluntia.ApiVoluntia.Servicios.ClaveFuncionalidades;
import ApiVoluntia.ApiVoluntia.Utilidades.Utilidades;

@RestController
@RequestMapping("/api/clave")
public class ClaveControlador {

	@Autowired
	ClaveFuncionalidades claveFuncionalidades;

	/*
	 * Endpoint para obtener la lista de todas las claves existentes
	 * 
	 * @author - DMN 20/03/2025
	 */
	@GetMapping("/claves")
	public ArrayList<ClaveDtos> listaClaves() {

		Utilidades.ficheroLog(1, "ClaveControlador - listClaves() - Entrada");
		try {
			return claveFuncionalidades.getClaves();
		} catch (Exception e) {

			Utilidades.ficheroLog(3, "ClaveControlador - listClaves() - " + e.getMessage());
			return new ArrayList<>();
		}

	}

	/**
	 * Endpoint para guardar una nueva clave.
	 *
	 * @param clave Objeto claveDtos con los datos de la clave a guardar
	 * @return La Clave guardada o null en caso de error.
	 */
	@PostMapping("/guardarclave")
	public ClaveDtos guardarClave(@RequestBody ClaveDtos clave) {
		Utilidades.ficheroLog(1, "ClaveControlador - guardarClave() - Entrada");
		try {
			return claveFuncionalidades.guardarClave(clave);
		} catch (Exception e) {
			Utilidades.ficheroLog(3, "ClaveControlador - guardarClave() - " + e.getMessage());
			throw e;
		}
	}

	@GetMapping("/organizacion/{id_org}")
	public List<ClaveDtos> listarPorOrganizacion(@PathVariable("id_org") Long idOrg) {
		Utilidades.ficheroLog(1, "ClaveControlador - listarPorOrganizacion() - Entrada");
		return claveFuncionalidades.listarPorOrganizacion(idOrg);
	}

	/**
	 * Endpoint para eliminar una clave por su ID.
	 *
	 * @param id_clave ID de la clave a eliminar en formato String.
	 * @return true si la clave fue eliminada correctamente, false en caso de error.
	 */
	@DeleteMapping("/eliminarclave/{id_clave}")
	public boolean deleteClave(@PathVariable("id_clave") String idClave) {
		Utilidades.ficheroLog(1, "ClaveControlador - eliminarClave() - Entrada");
		try {
			return claveFuncionalidades.eliminarClave(idClave);
		} catch (Exception e) {
			Utilidades.ficheroLog(3, "ClaveControlador - eliminarClave() - " + e.getMessage());
			return false;
		}
	}

	/**
	 * Endpoint para modificar los datos de una clave.
	 *
	 * @param idClave ID de la clave a modificar en formato String.
	 * @param clave   Objeto claveDtos con los nuevos datos de la emergencia.
	 * @return true si la clave fue modificada correctamente, false en caso de
	 *         error.
	 */
	@PutMapping("/modificarclave/{id_clave}")
	public boolean modificarClave(@PathVariable("id_clave") String idClave, @RequestBody ClaveDtos clave) {
		Utilidades.ficheroLog(1, "ClaveControlador - modificarClave() - Entrada");
		try {
			return claveFuncionalidades.modificarClave(idClave, clave);
		} catch (Exception e) {
			Utilidades.ficheroLog(3, "ClaveControlador - modificarClave() - " + e.getMessage());
			return false;
		}
	}

}
