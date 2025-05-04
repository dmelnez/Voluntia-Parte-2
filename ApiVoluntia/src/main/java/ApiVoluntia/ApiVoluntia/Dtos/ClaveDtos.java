package ApiVoluntia.ApiVoluntia.Dtos;

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
@Table(name = "claves", schema = "sch")
/*
 * Atributos encargados de componer la entidad Clave
 * 
 * @Author DMN - 20/03/2025
 */
public class ClaveDtos {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_clave")
	private Long idClave;

	@Column(name = "nombre_clave")
	private String nombreClave;

	@Column(name = "descripcion_clave")
	private String descripcionClave;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "idOrganizacion")
	private OrganizacionDtos organizacion;

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

	public OrganizacionDtos getOrganizacion() {
		return organizacion;
	}

	public void setOrganizacion(OrganizacionDtos organizacion) {
		this.organizacion = organizacion;
	}

}
