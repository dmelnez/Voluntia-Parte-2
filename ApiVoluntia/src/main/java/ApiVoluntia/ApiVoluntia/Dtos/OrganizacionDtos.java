package ApiVoluntia.ApiVoluntia.Dtos;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "organizacion", schema = "sch")
@JsonIgnoreProperties("usuarios")
public class OrganizacionDtos {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_organizacion")
	private long idOrganizacion;

	@Column(name = "ciudad_organizacion")
	private String ciudadOrganizacion;

	@Column(name = "direccion_organizacion")
	private String direccionOrganizacion;

	@Column(name = "email_organizacion")
	private String emailOrganizacion;

	@Column(name = "nif_organizacion")
	private String nifOrganizacion;

	@Column(name = "nombre_organizacion")
	private String nombreOrganizacion;

	@Column(name = "provincia_organizacion")
	private String provinciaOrganizacion;

	@Column(name = "telefono_organizacion")
	private String telefonoOrganizacion;

	@Column(name = "tipo_intitucion_organizacion")
	private String tipoInstitucionOrganizacion;

	@Column(name = "fecha_alta_organizacion")
	private LocalDateTime fechaAltaIntitucion = LocalDateTime.now();

	@OneToMany(mappedBy = "organizacion", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<UsuarioDtos> usuarios = new HashSet<>();

	@OneToOne(mappedBy = "organizacion", cascade = CascadeType.PERSIST)
	@JsonManagedReference(value = "organizacion-contrato")
	private ContratoDtos contrato;

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

	public ContratoDtos getContrato() {
		return contrato;
	}

	public void setContrato(ContratoDtos contrato) {
		this.contrato = contrato;
	}

	public Set<UsuarioDtos> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(Set<UsuarioDtos> usuarios) {
		this.usuarios = usuarios;
	}
}
