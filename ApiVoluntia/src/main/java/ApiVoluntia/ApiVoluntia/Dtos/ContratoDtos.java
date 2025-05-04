package ApiVoluntia.ApiVoluntia.Dtos;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
@Table(name = "contratos", schema = "sch")
public class ContratoDtos {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_contrato")
	private Long idContrato;

	@Column(name = "pdf_contrato")
	private byte[] archivoPDF;

	@Column(name = "cif_empresa_contrato")
	private String cifEmpresa;

	@Column(name = "importe_contrato")
	private String importe;

	@Column(name = "dni_firmante_contrato")
	private String dniFirmante;

	@Column(name = "identificador_contrato")
	private String identificador;

	@Column(name = "nombre_cliente_contrato")
	private String nombreCliente;

	@Column(name = "direcion_cliente_contrato")
	private String direccionCliente;

	@ManyToOne
	@JoinColumn(name = "id_plan", nullable = false)
	private PlanDtos plan;

	@OneToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "id_organizacion", nullable = false)
	@JsonBackReference(value = "organizacion-contrato")
	private OrganizacionDtos organizacion;

	public ContratoDtos() {
	}

	public ContratoDtos(Long idContrato, byte[] archivoPDF, String cifEmpresa, String importe, String dniFirmante,
			String identificador, String nombreCliente, String direccionCliente) {
		this.idContrato = idContrato;
		this.archivoPDF = archivoPDF;
		this.cifEmpresa = cifEmpresa;
		this.importe = importe;
		this.dniFirmante = dniFirmante;
		this.identificador = identificador;
		this.nombreCliente = nombreCliente;
		this.direccionCliente = direccionCliente;
	}

	public Long getIdContrato() {
		return idContrato;
	}

	public void setIdContrato(Long idContrato) {
		this.idContrato = idContrato;
	}

	public byte[] getArchivoPDF() {
		return archivoPDF;
	}

	public void setArchivoPDF(byte[] archivoPDF) {
		this.archivoPDF = archivoPDF;
	}

	public String getCifEmpresa() {
		return cifEmpresa;
	}

	public void setCifEmpresa(String cifEmpresa) {
		this.cifEmpresa = cifEmpresa;
	}

	public String getImporte() {
		return importe;
	}

	public void setImporte(String importe) {
		this.importe = importe;
	}

	public String getDniFirmante() {
		return dniFirmante;
	}

	public void setDniFirmante(String dniFirmante) {
		this.dniFirmante = dniFirmante;
	}

	public String getIdentificador() {
		return identificador;
	}

	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}

	public String getNombreCliente() {
		return nombreCliente;
	}

	public void setNombreCliente(String nombreCliente) {
		this.nombreCliente = nombreCliente;
	}

	public String getDireccionCliente() {
		return direccionCliente;
	}

	public void setDireccionCliente(String direccionCliente) {
		this.direccionCliente = direccionCliente;
	}

	public PlanDtos getPlan() {
		return plan;
	}

	public void setPlan(PlanDtos plan) {
		this.plan = plan;
	}

	public OrganizacionDtos getOrganizacion() {
		return organizacion;
	}

	public void setOrganizacion(OrganizacionDtos organizacion) {
		this.organizacion = organizacion;
	}
}
