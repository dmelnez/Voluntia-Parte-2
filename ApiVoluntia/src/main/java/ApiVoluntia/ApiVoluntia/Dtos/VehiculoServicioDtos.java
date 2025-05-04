package ApiVoluntia.ApiVoluntia.Dtos;

import jakarta.persistence.Id;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "vehiculo_servicio", schema = "sch")
public class VehiculoServicioDtos {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "vehiculo_id", nullable = false)
	private VehiculoDtos vehiculo;

	@ManyToOne
	@JoinColumn(name = "servicio_id", nullable = false)
	private ServicioDtos servicio;

	public VehiculoServicioDtos() {
	}

	public VehiculoServicioDtos(VehiculoDtos vehiculo, ServicioDtos servicio) {
		this.vehiculo = vehiculo;
		this.servicio = servicio;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public VehiculoDtos getVehiculo() {
		return vehiculo;
	}

	public void setVehiculo(VehiculoDtos vehiculo) {
		this.vehiculo = vehiculo;
	}

	public ServicioDtos getServicio() {
		return servicio;
	}

	public void setServicio(ServicioDtos servicio) {
		this.servicio = servicio;
	}

}
