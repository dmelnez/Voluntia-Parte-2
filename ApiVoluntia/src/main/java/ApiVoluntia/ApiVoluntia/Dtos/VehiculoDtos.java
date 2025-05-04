package ApiVoluntia.ApiVoluntia.Dtos;

import java.time.LocalDate;

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
@Table(name = "vehiculos", schema = "sch")
public class VehiculoDtos {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idVehiculo")
	private long idVehiculo;

	@Column(name = "marcaVehiculo")
	private String marcaVehiculo;

	@Column(name = "modeloVehiculo")
	private String modeloVehiculo;

	@Column(name = "fechaMatriculacionVehiculo")
	private LocalDate fechaMatriculacionVehiculo;

	@Column(name = "matriculaVehiculo")
	private String matriculaVehiculo;

	@Column(name = "combustibleVehiculo")
	private String combustibleVehiculo;

	@Column(name = "tipoVehiculo")
	private String tipoVehiculo;

	@Column(name = "proximaITVehiculo")
	private LocalDate proximaITVehiculo;

	@Column(name = "indicativoVehiculo")
	private String indicativoVehiculo;

	@Column(name = "recursoVehiculo")
	private String recursoVehiculo;

	@Column(name = "vencimientoSeguroVehiculo")
	private LocalDate vencimientoSeguroVehiculo;

	@Column(name = "tipoCabinaVehiculo")
	private String tipoCabinaVehiculo;

	@Column(name = "imagen_vehiculo")
	private byte[] imagenVehiculo;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "idOrganizacion")
	private OrganizacionDtos organizacion;

	public VehiculoDtos() {
	}

	public VehiculoDtos(long idVehiculo, String marcaVehiculo, String modeloVehiculo,
			LocalDate fechaMatriculacionVehiculo, String matriculaVehiculo, String combustibleVehiculo,
			String tipoVehiculo, LocalDate proximaITVehiculo, String indicativoVehiculo, String recursoVehiculo,
			LocalDate vencimientoSeguroVehiculo, String tipoCabinaVehiculo) {
		super();
		this.idVehiculo = idVehiculo;
		this.marcaVehiculo = marcaVehiculo;
		this.modeloVehiculo = modeloVehiculo;
		this.fechaMatriculacionVehiculo = fechaMatriculacionVehiculo;
		this.matriculaVehiculo = matriculaVehiculo;
		this.combustibleVehiculo = combustibleVehiculo;
		this.tipoVehiculo = tipoVehiculo;
		this.proximaITVehiculo = proximaITVehiculo;
		this.indicativoVehiculo = indicativoVehiculo;
		this.recursoVehiculo = recursoVehiculo;
		this.vencimientoSeguroVehiculo = vencimientoSeguroVehiculo;
		this.tipoCabinaVehiculo = tipoCabinaVehiculo;
	}

	public long getIdVehiculo() {
		return idVehiculo;
	}

	public void setIdVehiculo(long idVehiculo) {
		this.idVehiculo = idVehiculo;
	}

	public String getMarcaVehiculo() {
		return marcaVehiculo;
	}

	public void setMarcaVehiculo(String marcaVehiculo) {
		this.marcaVehiculo = marcaVehiculo;
	}

	public String getModeloVehiculo() {
		return modeloVehiculo;
	}

	public void setModeloVehiculo(String modeloVehiculo) {
		this.modeloVehiculo = modeloVehiculo;
	}

	public LocalDate getFechaMatriculacionVehiculo() {
		return fechaMatriculacionVehiculo;
	}

	public void setFechaMatriculacionVehiculo(LocalDate fechaMatriculacionVehiculo) {
		this.fechaMatriculacionVehiculo = fechaMatriculacionVehiculo;
	}

	public String getMatriculaVehiculo() {
		return matriculaVehiculo;
	}

	public void setMatriculaVehiculo(String matriculaVehiculo) {
		this.matriculaVehiculo = matriculaVehiculo;
	}

	public String getCombustibleVehiculo() {
		return combustibleVehiculo;
	}

	public void setCombustibleVehiculo(String combustibleVehiculo) {
		this.combustibleVehiculo = combustibleVehiculo;
	}

	public String getTipoVehiculo() {
		return tipoVehiculo;
	}

	public void setTipoVehiculo(String tipoVehiculo) {
		this.tipoVehiculo = tipoVehiculo;
	}

	public LocalDate getProximaITVehiculo() {
		return proximaITVehiculo;
	}

	public void setProximaITVehiculo(LocalDate proximaITVehiculo) {
		this.proximaITVehiculo = proximaITVehiculo;
	}

	public String getIndicativoVehiculo() {
		return indicativoVehiculo;
	}

	public void setIndicativoVehiculo(String indicativoVehiculo) {
		this.indicativoVehiculo = indicativoVehiculo;
	}

	public String getRecursoVehiculo() {
		return recursoVehiculo;
	}

	public void setRecursoVehiculo(String recursoVehiculo) {
		this.recursoVehiculo = recursoVehiculo;
	}

	public LocalDate getVencimientoSeguroVehiculo() {
		return vencimientoSeguroVehiculo;
	}

	public void setVencimientoSeguroVehiculo(LocalDate vencimientoSeguroVehiculo) {
		this.vencimientoSeguroVehiculo = vencimientoSeguroVehiculo;
	}

	public String getTipoCabinaVehiculo() {
		return tipoCabinaVehiculo;
	}

	public void setTipoCabinaVehiculo(String tipoCabinaVehiculo) {
		this.tipoCabinaVehiculo = tipoCabinaVehiculo;
	}

	public byte[] getImagenVehiculo() {
		return imagenVehiculo;
	}

	public void setImagenVehiculo(byte[] imagenVehiculo) {
		this.imagenVehiculo = imagenVehiculo;
	}

	public OrganizacionDtos getOrganizacion() {
		return organizacion;
	}

	public void setOrganizacion(OrganizacionDtos organizacion) {
		this.organizacion = organizacion;
	}

}
