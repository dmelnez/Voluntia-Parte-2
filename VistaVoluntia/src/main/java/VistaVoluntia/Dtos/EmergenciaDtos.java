package VistaVoluntia.Dtos;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Clase DTO que representa los datos de una emergencia lanzada en el sistema.
 * Incluye información sobre el título, categoría, descripción y la fecha y hora
 * de lanzamiento de la emergencia.
 * 
 * @author DMN
 * @version 1.0 - 26/04/2025
 */

public class EmergenciaDtos {

	private long idEmergencia;

	private String tituloEmergencia;

	private String categoriaEmergencia;

	private String descripcionEmergencia;

	private LocalDate fechaLanzamientoEmergencia;

	private LocalTime horaLanzamientoEmergencia;

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

}
