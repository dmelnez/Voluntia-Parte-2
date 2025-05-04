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

import ApiVoluntia.ApiVoluntia.Dtos.PlanDtos;
import ApiVoluntia.ApiVoluntia.Servicios.PlanFuncionalidades;
import ApiVoluntia.ApiVoluntia.Utilidades.Utilidades;

/**
 * Controlador REST para la gestión de planes.
 * <p>
 * Proporciona endpoints para obtener, guardar, eliminar y modificar planes.
 * </p>
 * 
 * @author DMN
 */
@RestController
@RequestMapping("/api/plan")
public class PlanControlador {

	@Autowired
	private PlanFuncionalidades planes;

	@Autowired
	private Utilidades utilidades;

	/**
	 * Endpoint para obtener la lista de todos los planes existentes.
	 *
	 * @return ArrayList de PlanDtos con todos los planes registrados. En caso de
	 *         error, retorna una lista vacía.
	 */
	@GetMapping("/planes")
	public ArrayList<PlanDtos> listaContratos() {
		utilidades.ficheroLog(1, "PlanControlador - listaContratos() - Entrada");
		try {
			return planes.getPlanes();
		} catch (Exception e) {
			utilidades.ficheroLog(3, "PlanControlador - listaContratos() - " + e.getMessage());
			return new ArrayList<>();
		}
	}

	/**
	 * Endpoint para guardar un nuevo plan.
	 *
	 * @param plan Objeto PlanDtos con los datos del plan a guardar.
	 * @return El plan guardado o null en caso de error.
	 */
	@PostMapping("/guardarplan")
	public PlanDtos guardarPlan(@RequestBody PlanDtos plan) {
		utilidades.ficheroLog(1, "PlanControlador - guardarPlan() - Entrada");
		try {
			return planes.guardarPlan(plan);
		} catch (Exception e) {
			utilidades.ficheroLog(3, "PlanControlador - guardarPlan() - " + e.getMessage());
			return null;
		}
	}

	/**
	 * Endpoint para eliminar un plan por su ID.
	 *
	 * @param id_plan ID del plan a eliminar en formato String.
	 * @return true si el plan fue eliminado correctamente, false en caso de error.
	 */
	@DeleteMapping("/eliminarplan/{id_plan}")
	public boolean deletePlan(@PathVariable("id_plan") String id_plan) {
		utilidades.ficheroLog(1, "PlanControlador - deletePlan() - Entrada");
		try {
			return planes.eliminarPlan(id_plan);
		} catch (Exception e) {
			utilidades.ficheroLog(3, "PlanControlador - deletePlan() - " + e.getMessage());
			return false;
		}
	}

	/**
	 * Endpoint para modificar los datos de un plan existente.
	 *
	 * @param idPlan ID del plan a modificar en formato String.
	 * @param plan   Objeto PlanDtos con los nuevos datos del plan.
	 * @return true si el plan fue modificado correctamente, false en caso de error.
	 */
	@PutMapping("/modificarplan/{id_plan}")
	public boolean putPlan(@PathVariable("id_plan") String idPlan, @RequestBody PlanDtos plan) {
		utilidades.ficheroLog(1, "PlanControlador - putPlan() - Entrada");
		try {
			return planes.modificarPlan(idPlan, plan);
		} catch (Exception e) {
			utilidades.ficheroLog(3, "PlanControlador - putPlan() - " + e.getMessage());
			return false;
		}
	}
}
