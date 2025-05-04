package ApiVoluntia.ApiVoluntia.Dtos;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "servicios", schema = "sch")
public class ServicioDtos {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idServicio")
	long idServicio;

	@Column(name = "numeracionServicio")
	String numeracionServicio;

	@Column(name = "tituloServicio")
	String tituloServicio;

	@Column(name = "fechaInicioServicio")
	LocalDate fechaInicioServicio;

	@Column(name = "fechaFinServicio")
	LocalDate fechaFinServicio;

	@Column(name = "fechaLimiteInscripcionServicio")
	LocalDate fechaLimiteInscripcionServicio;

	@Column(name = "horaBaseServicio")
	LocalTime horaBaseServicio;

	@Column(name = "horaSalidaServicio")
	LocalTime horaSalidaServicio;

	@Column(name = "maximoAsistentesServicio")
	Integer maximoAsistentesServicio;

	@Column(name = "tipoServicio")
	String tipoServicio;

	@Column(name = "categoriaServicio")
	String categoriaServicio;

	@Column(name = "descripcionServicio")
	String descripcionServicio;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "usuario_servicio", joinColumns = @JoinColumn(name = "idServicio"), inverseJoinColumns = @JoinColumn(name = "idUsuario"))
	private Set<UsuarioDtos> usuarios = new HashSet<>();

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_organizacion")
	private OrganizacionDtos organizacion;

	public ServicioDtos(long idServicio, String numeracionServicio, String tituloServicio,
			LocalDate fechaInicioServicio, LocalDate fechaFinServicio, LocalDate fechaLimiteInscripcionServicio,
			LocalTime horaBaseServicio, LocalTime horaSalidaServicio, Integer maximoAsistentesServicio,
			String tipoServicio, String categoriaServicio, String descripcionServicio) {
		super();
		this.idServicio = idServicio;
		this.numeracionServicio = numeracionServicio;
		this.tituloServicio = tituloServicio;
		this.fechaInicioServicio = fechaInicioServicio;
		this.fechaFinServicio = fechaFinServicio;
		this.fechaLimiteInscripcionServicio = fechaLimiteInscripcionServicio;
		this.horaBaseServicio = horaBaseServicio;
		this.horaSalidaServicio = horaSalidaServicio;
		this.maximoAsistentesServicio = maximoAsistentesServicio;
		this.tipoServicio = tipoServicio;
		this.categoriaServicio = categoriaServicio;
		this.descripcionServicio = descripcionServicio;
	}

	public ServicioDtos() {
	}

	public long getIdServicio() {
		return idServicio;
	}

	public void setIdServicio(long idServicio) {
		this.idServicio = idServicio;
	}

	public String getNumeracionServicio() {
		return numeracionServicio;
	}

	public void setNumeracionServicio(String numeracionServicio) {
		this.numeracionServicio = numeracionServicio;
	}

	public String getTituloServicio() {
		return tituloServicio;
	}

	public void setTituloServicio(String tituloServicio) {
		this.tituloServicio = tituloServicio;
	}

	public LocalDate getFechaInicioServicio() {
		return fechaInicioServicio;
	}

	public void setFechaInicioServicio(LocalDate fechaInicioServicio) {
		this.fechaInicioServicio = fechaInicioServicio;
	}

	public LocalDate getFechaFinServicio() {
		return fechaFinServicio;
	}

	public void setFechaFinServicio(LocalDate fechaFinServicio) {
		this.fechaFinServicio = fechaFinServicio;
	}

	public LocalDate getFechaLimiteInscripcionServicio() {
		return fechaLimiteInscripcionServicio;
	}

	public void setFechaLimiteInscripcionServicio(LocalDate fechaLimiteInscripcionServicio) {
		this.fechaLimiteInscripcionServicio = fechaLimiteInscripcionServicio;
	}

	public LocalTime getHoraBaseServicio() {
		return horaBaseServicio;
	}

	public void setHoraBaseServicio(LocalTime horaBaseServicio) {
		this.horaBaseServicio = horaBaseServicio;
	}

	public LocalTime getHoraSalidaServicio() {
		return horaSalidaServicio;
	}

	public void setHoraSalidaServicio(LocalTime horaSalidaServicio) {
		this.horaSalidaServicio = horaSalidaServicio;
	}

	public Integer getMaximoAsistentesServicio() {
		return maximoAsistentesServicio;
	}

	public void setMaximoAsistentesServicio(Integer maximoAsistentesServicio) {
		this.maximoAsistentesServicio = maximoAsistentesServicio;
	}

	public String getTipoServicio() {
		return tipoServicio;
	}

	public void setTipoServicio(String tipoServicio) {
		this.tipoServicio = tipoServicio;
	}

	public String getCategoriaServicio() {
		return categoriaServicio;
	}

	public void setCategoriaServicio(String categoriaServicio) {
		this.categoriaServicio = categoriaServicio;
	}

	public String getDescripcionServicio() {
		return descripcionServicio;
	}

	public void setDescripcionServicio(String descripcionServicio) {
		this.descripcionServicio = descripcionServicio;
	}

	public Set<UsuarioDtos> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(Set<UsuarioDtos> usuarios) {
		this.usuarios = usuarios;
	}

	public OrganizacionDtos getOrganizacion() {
		return organizacion;
	}

	public void setOrganizacion(OrganizacionDtos organizacion) {
		this.organizacion = organizacion;
	}

}
