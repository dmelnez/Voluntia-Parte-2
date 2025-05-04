package ApiVoluntia.ApiVoluntia.Dtos;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "emergencias", schema = "sch")
public class EmergenciaDtos {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_emergencia")
	private long idEmergencia;

	@Column(name = "titulo_emergencia")
	private String tituloEmergencia;

	@Column(name = "categoria_emergencia")
	private String categoriaEmergencia;

	@Column(name = "descripcion_emergencia")
	private String descripcionEmergencia;

	@Column(name = "fecha_lanzamiento_emergencia")
	private LocalDate fechaLanzamientoEmergencia;

	@Column(name = "hora_lanzamiento_emergencia")
	private LocalTime horaLanzamientoEmergencia;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "idOrganizacion")
	private OrganizacionDtos organizacion;

	public EmergenciaDtos(long idEmergencia, String tituloEmergencia, String categoriaEmergencia,
			String descripcionEmergencia, LocalDate fechaLanzamientoEmergencia, LocalTime horaLanzamientoEmergencia) {
		super();
		this.idEmergencia = idEmergencia;
		this.tituloEmergencia = tituloEmergencia;
		this.categoriaEmergencia = categoriaEmergencia;
		this.descripcionEmergencia = descripcionEmergencia;
		this.fechaLanzamientoEmergencia = fechaLanzamientoEmergencia;
		this.horaLanzamientoEmergencia = horaLanzamientoEmergencia;
	}

	public EmergenciaDtos() {
	}

	public long getIdEmergencia() {
		return idEmergencia;
	}

	public void setIdEmergencia(long idEmergencia) {
		this.idEmergencia = idEmergencia;
	}

	public String getTituloEmergencia() {
		return tituloEmergencia;
	}

	public void setTituloEmergencia(String tituloEmergencia) {
		this.tituloEmergencia = tituloEmergencia;
	}

	public String getCategoriaEmergencia() {
		return categoriaEmergencia;
	}

	public void setCategoriaEmergencia(String categoriaEmergencia) {
		this.categoriaEmergencia = categoriaEmergencia;
	}

	public String getDescripcionEmergencia() {
		return descripcionEmergencia;
	}

	public void setDescripcionEmergencia(String descripcionEmergencia) {
		this.descripcionEmergencia = descripcionEmergencia;
	}

	public LocalDate getFechaLanzamientoEmergencia() {
		return fechaLanzamientoEmergencia;
	}

	public void setFechaLanzamientoEmergencia(LocalDate fechaLanzamientoEmergencia) {
		this.fechaLanzamientoEmergencia = fechaLanzamientoEmergencia;
	}

	public LocalTime getHoraLanzamientoEmergencia() {
		return horaLanzamientoEmergencia;
	}

	public void setHoraLanzamientoEmergencia(LocalTime horaLanzamientoEmergencia) {
		this.horaLanzamientoEmergencia = horaLanzamientoEmergencia;
	}

	public OrganizacionDtos getOrganizacion() {
		return organizacion;
	}

	public void setOrganizacion(OrganizacionDtos organizacion) {
		this.organizacion = organizacion;
	}
}
