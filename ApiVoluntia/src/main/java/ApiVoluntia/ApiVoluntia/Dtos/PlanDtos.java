package ApiVoluntia.ApiVoluntia.Dtos;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "planes", schema = "sch")
public class PlanDtos {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_plan")
	private long idPlan;

	@Column(name = "tipo_plan")
	private String tipoPlan;

	@Column(name = "precio_plan")
	private double precioPlan;

	@Column(name = "tiempo_plan")
	private String tiempoPlan;

	@Column(name = "descripcion_plan")
	private String descripcionPlan;

	@Column(name = "numero_usuarios_plan")
	private String numeroUsuariosPlan;

	@JsonIgnore
	@OneToMany(mappedBy = "plan", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<ContratoDtos> contratos;

	// Constructores
	public PlanDtos() {
	}

	public PlanDtos(long idPlan, String tipoPlan, double precioPlan, String tiempoPlan, String descripcionPlan,
			String numeroUsuariosPlan) {
		this.idPlan = idPlan;
		this.tipoPlan = tipoPlan;
		this.precioPlan = precioPlan;
		this.tiempoPlan = tiempoPlan;
		this.descripcionPlan = descripcionPlan;
		this.numeroUsuariosPlan = numeroUsuariosPlan;
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

	public List<ContratoDtos> getContratos() {
		return contratos;
	}

	public void setContratos(List<ContratoDtos> contratos) {
		this.contratos = contratos;
	}
}
