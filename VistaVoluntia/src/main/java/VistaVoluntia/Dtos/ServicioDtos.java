package VistaVoluntia.Dtos;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Clase DTO que representa los datos de un servicio gestionado en el sistema.
 * Incluye información sobre la planificación, inscripción, horario y
 * características específicas del servicio.
 * 
 * @author DMN
 * @version 1.0 - 26/04/2025
 */

public class ServicioDtos {

	private long idServicio;

	private String numeracionServicio;

	private String tituloServicio;

	private LocalDate fechaInicioServicio;

	private LocalDate fechaFinServicio;

	private LocalDate fechaLimiteInscripcionServicio;

	private LocalTime horaBaseServicio;

	private LocalTime horaSalidaServicio;

	private Integer maximoAsistentesServicio;

	private String tipoServicio;

	private String categoriaServicio;

	private String descripcionServicio;

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

}
