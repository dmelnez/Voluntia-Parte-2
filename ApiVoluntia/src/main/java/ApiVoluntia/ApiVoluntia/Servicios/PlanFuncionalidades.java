package ApiVoluntia.ApiVoluntia.Servicios;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ApiVoluntia.ApiVoluntia.Dtos.PlanDtos;
import ApiVoluntia.ApiVoluntia.Repositorios.PlanInterfaz;
import jakarta.transaction.Transactional;

@Service
public class PlanFuncionalidades {

	@Autowired
	PlanInterfaz planInterfaz;

	/**
	 * Método encargado de buscar todos los planes existentes en la base de datos.
	 *
	 * @return ArrayList de PlanDtos con todos los planes.
	 * @author DMN - 14/02/2025
	 */
	public ArrayList<PlanDtos> getPlanes() {
		return (ArrayList<PlanDtos>) planInterfaz.findAll();
	}

	/**
	 * Método encargado de guardar un nuevo plan en la base de datos.
	 *
	 * @param plan Objeto PlanDtos a guardar.
	 * @return El plan guardado.
	 * @author DMN - 14/02/2025
	 */
	public PlanDtos guardarPlan(PlanDtos plan) {
		return planInterfaz.save(plan);
	}

	/**
	 * Método encargado de eliminar un plan en base a su ID.
	 *
	 * @param id ID del plan a eliminar en formato String.
	 * @return true si el plan fue eliminado correctamente, false en caso contrario.
	 * @author DMN - 14/02/2025
	 */
	@Transactional
	public boolean eliminarPlan(String id) {
		try {
			long idPlan = Long.parseLong(id);
			boolean estaBorrado = false;

			PlanDtos planDtos = planInterfaz.findByIdPlan(idPlan);

			if (planDtos == null) {
				System.out.println("El ID del plan no existe");
				estaBorrado = false;
				return estaBorrado;
			}

			boolean coincide = false;
			if (planDtos.getIdPlan() == idPlan) {
				coincide = true;
			} else {
				System.out.println("El ID del plan no coincide");
				estaBorrado = false;
				return estaBorrado;
			}

			if (coincide) {
				planInterfaz.deleteByIdPlan(idPlan);
				System.out.println("El plan ha sido eliminado correctamente");
				estaBorrado = true;
				return estaBorrado;
			}

			return estaBorrado;

		} catch (NumberFormatException e) {
			System.out.println("Error: El ID proporcionado no es válido. " + e.getMessage());
			return false;
		} catch (Exception e) {
			System.out.println("Ocurrió un error inesperado: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Método encargado de modificar los datos de un plan en base a su ID.
	 *
	 * @param id   ID del plan a modificar en formato String.
	 * @param plan Objeto PlanDtos con los nuevos datos.
	 * @return true si el plan fue modificado con éxito, false en caso contrario.
	 * @author DMN - 14/02/2025
	 */
	public boolean modificarPlan(String id, PlanDtos plan) {
		boolean esModificado = false;
		Long idPlan = Long.parseLong(id);

		PlanDtos planDtos = planInterfaz.findByIdPlan(idPlan);

		if (planDtos == null) {
			esModificado = false;
			System.out.println("El plan no existe");
		} else {
			planDtos.setTipoPlan(plan.getTipoPlan());
			planDtos.setPrecioPlan(plan.getPrecioPlan());
			planDtos.setTiempoPlan(plan.getTiempoPlan());
			planDtos.setDescripcionPlan(plan.getDescripcionPlan());
			planDtos.setNumeroUsuariosPlan(plan.getNumeroUsuariosPlan());

			planInterfaz.save(planDtos);
			esModificado = true;
			System.out.println("El plan ha sido modificado con éxito");
		}

		return esModificado;
	}
}
