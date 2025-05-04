package VistaVoluntia.Dtos;

import java.time.LocalDateTime;

/**
 * Clase DTO que representa los datos de una organización registrada en el
 * sistema. Incluye información de contacto, ubicación, identificación fiscal y
 * fecha de alta de la organización.
 * 
 * @author DMN
 * @version 1.0 - 26/04/2025
 */

public class OrganizacionDtos {

	private long idOrganizacion;

	private String ciudadOrganizacion;

	private String direccionOrganizacion;

	private String emailOrganizacion;

	private String nifOrganizacion;

	private String nombreOrganizacion;

	private String provinciaOrganizacion;

	private String telefonoOrganizacion;

	private String tipoInstitucionOrganizacion;

	private LocalDateTime fechaAltaIntitucion = LocalDateTime.now();

	public OrganizacionDtos(long idOrganizacion, String ciudadOrganizacion, String direccionOrganizacion,
			String emailOrganizacion, String nifOrganizacion, String nombreOrganizacion, String provinciaOrganizacion,
			String telefonoOrganizacion, String tipoInstitucionOrganizacion, LocalDateTime fechaAltaIntitucion) {
		super();
		this.idOrganizacion = idOrganizacion;
		this.ciudadOrganizacion = ciudadOrganizacion;
		this.direccionOrganizacion = direccionOrganizacion;
		this.emailOrganizacion = emailOrganizacion;
		this.nifOrganizacion = nifOrganizacion;
		this.nombreOrganizacion = nombreOrganizacion;
		this.provinciaOrganizacion = provinciaOrganizacion;
		this.telefonoOrganizacion = telefonoOrganizacion;
		this.tipoInstitucionOrganizacion = tipoInstitucionOrganizacion;
		this.fechaAltaIntitucion = fechaAltaIntitucion;
	}

	public OrganizacionDtos() {
	}

	public long getIdOrganizacion() {
		return idOrganizacion;
	}

	public void setIdOrganizacion(long idOrganizacion) {
		this.idOrganizacion = idOrganizacion;
	}

	public String getCiudadOrganizacion() {
		return ciudadOrganizacion;
	}

	public void setCiudadOrganizacion(String ciudadOrganizacion) {
		this.ciudadOrganizacion = ciudadOrganizacion;
	}

	public String getDireccionOrganizacion() {
		return direccionOrganizacion;
	}

	public void setDireccionOrganizacion(String direccionOrganizacion) {
		this.direccionOrganizacion = direccionOrganizacion;
	}

	public String getEmailOrganizacion() {
		return emailOrganizacion;
	}

	public void setEmailOrganizacion(String emailOrganizacion) {
		this.emailOrganizacion = emailOrganizacion;
	}

	public String getNifOrganizacion() {
		return nifOrganizacion;
	}

	public void setNifOrganizacion(String nifOrganizacion) {
		this.nifOrganizacion = nifOrganizacion;
	}

	public String getNombreOrganizacion() {
		return nombreOrganizacion;
	}

	public void setNombreOrganizacion(String nombreOrganizacion) {
		this.nombreOrganizacion = nombreOrganizacion;
	}

	public String getProvinciaOrganizacion() {
		return provinciaOrganizacion;
	}

	public void setProvinciaOrganizacion(String provinciaOrganizacion) {
		this.provinciaOrganizacion = provinciaOrganizacion;
	}

	public String getTelefonoOrganizacion() {
		return telefonoOrganizacion;
	}

	public void setTelefonoOrganizacion(String telefonoOrganizacion) {
		this.telefonoOrganizacion = telefonoOrganizacion;
	}

	public String getTipoInstitucionOrganizacion() {
		return tipoInstitucionOrganizacion;
	}

	public void setTipoInstitucionOrganizacion(String tipoInstitucionOrganizacion) {
		this.tipoInstitucionOrganizacion = tipoInstitucionOrganizacion;
	}

	public LocalDateTime getFechaAltaIntitucion() {
		return fechaAltaIntitucion;
	}

	public void setFechaAltaIntitucion(LocalDateTime fechaAltaIntitucion) {
		this.fechaAltaIntitucion = fechaAltaIntitucion;
	}

}
