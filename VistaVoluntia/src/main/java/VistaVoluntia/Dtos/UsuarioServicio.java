package VistaVoluntia.Dtos;

/**
 * Clase DTO que representa los datos de un usuario registrado en el servicio.
 * Incluye información general, administrativa y multimedia del usuario.
 * 
 * @author DMN
 * @version 1.0 - 26/04/2025
 */
public class UsuarioServicio {

	private Long id;

	private UsuarioDtos usuario;

	private ServicioDtos servicio;

	public UsuarioServicio() {
	}

	public UsuarioServicio(UsuarioDtos usuario, ServicioDtos servicio) {
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
