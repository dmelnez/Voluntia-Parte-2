package VistaVoluntia.Dtos;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Base64;

/**
 * Clase DTO que representa los datos de un usuario registrado en el sistema.
 * Contiene información personal, de contacto, organizativa y de seguridad del
 * usuario. También maneja datos relacionados con la imagen de perfil y el
 * control de acceso mediante tokens.
 * 
 * @author DMN
 * @version 1.0 - 26/04/2025
 */

public class UsuarioDtos {

	private long idUsuario;
	private String nombreUsuario;
	private String apellidosUsuario;
	private String telefonoUsuario;
	private String dniUsuario;
	private String generoUsuario;
	private LocalDate fechaNacimientoUsuario;
	private String provinciaUsuario;
	private String localidadUsuario;
	private String direccionUsuario;
	private String codigoPostalUsuario;
	private String fechaIngresoUsuario;
	private String numeroIdentificativoUsuario;
	private String indicativoUsuario;
	private String rolUsuario;
	private String correoUsuario;
	private String passwordUsuario;

	private byte[] imagenPerfilUsuario;

	private String tokenUsuario;
	private LocalDateTime fechaGeneradaTokenUsuario;

	private OrganizacionDtos organizacion;

	public UsuarioDtos(long idUsuario, String nombreUsuario, String apellidosUsuario, String telefonoUsuario,
			String dniUsuario, String generoUsuario, LocalDate fechaNacimientoUsuario, String provinciaUsuario,
			String localidadUsuario, String direccionUsuario, String codigoPostalUsuario, String fechaIngresoUsuario,
			String numeroIdentificativoUsuario, String indicativoUsuario, String rolUsuario, String correoUsuario,
			String passwordUsuario, String tokenUsuario, LocalDateTime fechaGeneradaTokenUsuario) {
		super();
		this.idUsuario = idUsuario;
		this.nombreUsuario = nombreUsuario;
		this.apellidosUsuario = apellidosUsuario;
		this.telefonoUsuario = telefonoUsuario;
		this.dniUsuario = dniUsuario;
		this.generoUsuario = generoUsuario;
		this.fechaNacimientoUsuario = fechaNacimientoUsuario;
		this.provinciaUsuario = provinciaUsuario;
		this.localidadUsuario = localidadUsuario;
		this.direccionUsuario = direccionUsuario;
		this.codigoPostalUsuario = codigoPostalUsuario;
		this.fechaIngresoUsuario = fechaIngresoUsuario;
		this.numeroIdentificativoUsuario = numeroIdentificativoUsuario;
		this.indicativoUsuario = indicativoUsuario;
		this.rolUsuario = rolUsuario;
		this.correoUsuario = correoUsuario;
		this.passwordUsuario = passwordUsuario;
		this.tokenUsuario = tokenUsuario;
		this.fechaGeneradaTokenUsuario = fechaGeneradaTokenUsuario;
	}

	public UsuarioDtos(long idUsuario, String nombreUsuario, String correoUsuario, String passwordUsuario,
			String telefonoUsuario, String rolUsuario) {
		this.idUsuario = idUsuario;
		this.nombreUsuario = nombreUsuario;
		this.correoUsuario = correoUsuario;
		this.passwordUsuario = passwordUsuario;
		this.telefonoUsuario = telefonoUsuario;
		this.rolUsuario = rolUsuario;
	}

	public UsuarioDtos() {
	}

	public long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(long idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	public String getApellidosUsuario() {
		return apellidosUsuario;
	}

	public void setApellidosUsuario(String apellidosUsuario) {
		this.apellidosUsuario = apellidosUsuario;
	}

	public String getTelefonoUsuario() {
		return telefonoUsuario;
	}

	public void setTelefonoUsuario(String telefonoUsuario) {
		this.telefonoUsuario = telefonoUsuario;
	}

	public String getDniUsuario() {
		return dniUsuario;
	}

	public void setDniUsuario(String dniUsuario) {
		this.dniUsuario = dniUsuario;
	}

	public String getGeneroUsuario() {
		return generoUsuario;
	}

	public void setGeneroUsuario(String generoUsuario) {
		this.generoUsuario = generoUsuario;
	}

	public LocalDate getFechaNacimientoUsuario() {
		return fechaNacimientoUsuario;
	}

	public void setFechaNacimientoUsuario(LocalDate fechaNacimientoUsuario) {
		this.fechaNacimientoUsuario = fechaNacimientoUsuario;
	}

	public String getProvinciaUsuario() {
		return provinciaUsuario;
	}

	public void setProvinciaUsuario(String provinciaUsuario) {
		this.provinciaUsuario = provinciaUsuario;
	}

	public String getLocalidadUsuario() {
		return localidadUsuario;
	}

	public void setLocalidadUsuario(String localidadUsuario) {
		this.localidadUsuario = localidadUsuario;
	}

	public String getDireccionUsuario() {
		return direccionUsuario;
	}

	public void setDireccionUsuario(String direccionUsuario) {
		this.direccionUsuario = direccionUsuario;
	}

	public String getCodigoPostalUsuario() {
		return codigoPostalUsuario;
	}

	public void setCodigoPostalUsuario(String codigoPostalUsuario) {
		this.codigoPostalUsuario = codigoPostalUsuario;
	}

	public String getFechaIngresoUsuario() {
		return fechaIngresoUsuario;
	}

	public void setFechaIngresoUsuario(String fechaIngresoUsuario) {
		this.fechaIngresoUsuario = fechaIngresoUsuario;
	}

	public String getNumeroIdentificativoUsuario() {
		return numeroIdentificativoUsuario;
	}

	public void setNumeroIdentificativoUsuario(String numeroIdentificativoUsuario) {
		this.numeroIdentificativoUsuario = numeroIdentificativoUsuario;
	}

	public String getIndicativoUsuario() {
		return indicativoUsuario;
	}

	public void setIndicativoUsuario(String indicativoUsuario) {
		this.indicativoUsuario = indicativoUsuario;
	}

	public String getRolUsuario() {
		return rolUsuario;
	}

	public void setRolUsuario(String rolUsuario) {
		this.rolUsuario = rolUsuario;
	}

	public String getCorreoUsuario() {
		return correoUsuario;
	}

	public void setCorreoUsuario(String correoUsuario) {
		this.correoUsuario = correoUsuario;
	}

	public String getPasswordUsuario() {
		return passwordUsuario;
	}

	public void setPasswordUsuario(String passwordUsuario) {
		this.passwordUsuario = passwordUsuario;
	}

	public byte[] getImagenPerfilUsuario() {
		return imagenPerfilUsuario;
	}

	public void setImagenUsuarioPerfil(byte[] imagenPerfilUsuario) {
		this.imagenPerfilUsuario = imagenPerfilUsuario;
	}

	public String getImagenBase64() {
		if (imagenPerfilUsuario == null) {
			return null;
		}
		return Base64.getEncoder().encodeToString(imagenPerfilUsuario);
	}

	public String getTokenUsuario() {
		return tokenUsuario;
	}

	public void setTokenUsuario(String tokenUsuario) {
		this.tokenUsuario = tokenUsuario;
	}

	public LocalDateTime getFechaGeneradaTokenUsuario() {
		return fechaGeneradaTokenUsuario;
	}

	public void setFechaGeneradaTokenUsuario(LocalDateTime fechaGeneradaTokenUsuario) {
		this.fechaGeneradaTokenUsuario = fechaGeneradaTokenUsuario;
	}

	public OrganizacionDtos getOrganizacion() {
		return organizacion;
	}

	public void setOrganizacion(OrganizacionDtos organizacion) {
		this.organizacion = organizacion;
	}
}
