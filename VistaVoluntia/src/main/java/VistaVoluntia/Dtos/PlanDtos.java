package VistaVoluntia.Dtos;

/**
 * Clase DTO que representa los datos de un plan disponible en el sistema.
 * Incluye información sobre el tipo, precio, duración, descripción y número de
 * usuarios permitidos en el plan.
 * 
 * @author DMN
 * @version 1.0 - 26/04/2025
 */

public class PlanDtos {

	long idPlan;

	String tipoPlan;

	double precioPlan;

	String tiempoPlan;

	String descripcionPlan;

	String numeroUsuariosPlan;

	public PlanDtos(long idPlan, String tipoPlan, double precioPlan, String tiempoPlan, String descripcionPlan,
			String numeroUsuariosPlan) {
		super();
		this.idPlan = idPlan;
		this.tipoPlan = tipoPlan;
		this.precioPlan = precioPlan;
		this.tiempoPlan = tiempoPlan;
		this.descripcionPlan = descripcionPlan;
		this.numeroUsuariosPlan = numeroUsuariosPlan;
	}

	public PlanDtos() {

	}

	public long getIdPlan() {
		return idPlan;
	}

	public void setIdPlan(long idPlan) {
		this.idPlan = idPlan;
	}

	public String getTipoPlan() {
		return tipoPlan;
	}

	public void setTipoPlan(String tipoPlan) {
		this.tipoPlan = tipoPlan;
	}

	public double getPrecioPlan() {
		return precioPlan;
	}

	public void setPrecioPlan(double precioPlan) {
		this.precioPlan = precioPlan;
	}

	public String getTiempoPlan() {
		return tiempoPlan;
	}

	public void setTiempoPlan(String tiempoPlan) {
		this.tiempoPlan = tiempoPlan;
	}

	public String getDescripcionPlan() {
		return descripcionPlan;
	}

	public void setDescripcionPlan(String descripcionPlan) {
		this.descripcionPlan = descripcionPlan;
	}

	public String getNumeroUsuariosPlan() {
		return numeroUsuariosPlan;
	}

	public void setNumeroUsuariosPlan(String numeroUsuariosPlan) {
		this.numeroUsuariosPlan = numeroUsuariosPlan;
	}

}
