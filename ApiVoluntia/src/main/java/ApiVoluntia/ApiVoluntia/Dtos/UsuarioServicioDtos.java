package ApiVoluntia.ApiVoluntia.Dtos;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "usuarios_servicio", schema = "sch")

public class UsuarioServicioDtos {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "usuario_id", nullable = false)
	private UsuarioDtos usuario;

	@ManyToOne
	@JoinColumn(name = "servicio_id", nullable = false)
	private ServicioDtos servicio;

	public UsuarioServicioDtos() {
	}

	public UsuarioServicioDtos(UsuarioDtos usuario, ServicioDtos servicio) {
		this.usuario = usuario;
		this.servicio = servicio;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public UsuarioDtos getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioDtos usuario) {
		this.usuario = usuario;
	}

	public ServicioDtos getServicio() {
		return servicio;
	}

	public void setServicio(ServicioDtos servicio) {
		this.servicio = servicio;
	}

}
