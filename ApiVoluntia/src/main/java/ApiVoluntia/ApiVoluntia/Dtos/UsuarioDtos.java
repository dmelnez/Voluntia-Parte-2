package ApiVoluntia.ApiVoluntia.Dtos;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "usuarios", schema = "sch")
public class UsuarioDtos {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_usuario")
	private long idUsuario;

	@Column(name = "nombre_usuario")
	private String nombreUsuario;

	@Column(name = "apellidos_usuario")
	private String apellidosUsuario;

	@Column(name = "telefono_usuario")
	private String telefonoUsuario;

	@Column(name = "dni_usuario")
	private String dniUsuario;

	@Column(name = "genero_usuario")
	private String generoUsuario;

	@Column(name = "fecha_nacimiento_usuario")
	private LocalDate fechaNacimientoUsuario;

	@Column(name = "provincia_usuario")
	private String provinciaUsuario;

	@Column(name = "localidad_usuario")
	private String localidadUsuario;

	@Column(name = "direccion_usuario")
	private String direccionUsuario;

	@Column(name = "codigo_postal_usuario")
	private String codigoPostalUsuario;

	@Column(name = "fecha_ingreso_usuario")
	private LocalDateTime fechaIngresoUsuario = LocalDateTime.now();

	@Column(name = "numero_identificativo_usuario")
	private String numeroIdentificativoUsuario;

	@Column(name = "indicativo_usuario")
	private String indicativoUsuario;

	@Column(name = "rol_usuario")
	private String rolUsuario;

	@Column(name = "correo_usuario")
	private String correoUsuario;

	@Column(name = "password_usuario")
	private String passwordUsuario;

	@Column(name = "imagen_perfil_usuario")
	private byte[] imagenPerfilUsuario;

	@Column(name = "token_suario")
	private String tokenUsuario;

	@Column(name = "fecha_generada_token_usuario")
	private LocalDateTime fechaGeneradaTokenUsuario;

	@ManyToMany(mappedBy = "usuarios")
	@JsonBackReference
	private Set<ServicioDtos> servicios = new HashSet<>();

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_organizacion", nullable = false)
	private OrganizacionDtos organizacion;

	public UsuarioDtos(long idUsuario, String nombreUsuario, String apellidosUsuario, String telefonoUsuario,
			String dniUsuario, String generoUsuario, LocalDate fechaNacimientoUsuario, String provinciaUsuario,
			String localidadUsuario, String direccionUsuario, String codigoPostalUsuario,
			LocalDateTime fechaIngresoUsuario, String numeroIdentificativoUsuario, String indicativoUsuario,
			String rolUsuario, String correoUsuario, String passwordUsuario, byte[] imagenPerfilUsuario,
			String tokenUsuario, LocalDateTime fechaGeneradaTokenUsuario) {
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
		this.imagenPerfilUsuario = imagenPerfilUsuario;
		this.tokenUsuario = tokenUsuario;
		this.fechaGeneradaTokenUsuario = fechaGeneradaTokenUsuario;
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

	public LocalDateTime getFechaIngresoUsuario() {
		return fechaIngresoUsuario;
	}

	public void setFechaIngresoUsuario(LocalDateTime fechaIngresoUsuario) {
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

	public void setImagenPerfilUsuario(byte[] imagenPerfilUsuario) {
		this.imagenPerfilUsuario = imagenPerfilUsuario;
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

	public Set<ServicioDtos> getServicios() {
		return servicios;
	}

	public void setServicios(Set<ServicioDtos> servicios) {
		this.servicios = servicios;
	}

	@JsonProperty("organizacion")
	public OrganizacionDtos getOrganizacion() {
		return organizacion;
	}

	public void setOrganizacion(OrganizacionDtos organizacion) {
		this.organizacion = organizacion;
	}
}
