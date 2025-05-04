package VistaVoluntia.Dtos;

/**
 * Clase DTO que representa las claves asociadas a los usuarios o entidades en
 * el sistema. Incluye un identificador único de la clave, su nombre y una
 * descripción detallada.
 * 
 * @author DMN
 * @version 1.0 - 26/04/2025
 */

public class ClaveDtos {

	private Long idClave;

	private String nombreClave;

	private String descripcionClave;

	public ClaveDtos(Long idClave, String nombreClave, String descripcionClave) {
		super();
		this.idClave = idClave;
		this.nombreClave = nombreClave;
		this.descripcionClave = descripcionClave;
	}

	public ClaveDtos() {
	}

	public Long getIdClave() {
		return idClave;
	}

	public void setIdClave(Long idClave) {
		this.idClave = idClave;
	}

	public String getNombreClave() {
		return nombreClave;
	}

	public void setNombreClave(String nombreClave) {
		this.nombreClave = nombreClave;
	}

	public String getDescripcionClave() {
		return descripcionClave;
	}

	public void setDescripcionClave(String descripcionClave) {
		this.descripcionClave = descripcionClave;
	}

}
