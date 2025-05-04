package VistaVoluntia.Dtos;

import java.time.LocalDate;
import java.util.Base64;

/**
 * Clase DTO que representa los datos de un vehículo registrado en el sistema.
 * Incluye información general, administrativa y multimedia del vehículo.
 * 
 * @author DMN
 * @version 1.0 - 26/04/2025
 */
public class VehiculoDtos {

	private long idVehiculo;

	private String marcaVehiculo;

	private String modeloVehiculo;

	private LocalDate fechaMatriculacionVehiculo;

	private String matriculaVehiculo;

	private String combustibleVehiculo;

	private String tipoVehiculo;

	private LocalDate proximaITVehiculo;

	private String indicativoVehiculo;

	private String recursoVehiculo;

	private LocalDate vencimientoSeguroVehiculo;

	private String tipoCabinaVehiculo;

	byte[] imagenVehiculo;

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

	public VehiculoDtos() {

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

	public String getImagenVehiculoBase64() {
		return Base64.getEncoder().encodeToString(imagenVehiculo);
	}
}
