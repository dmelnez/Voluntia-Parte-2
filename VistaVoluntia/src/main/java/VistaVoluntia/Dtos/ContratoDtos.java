package VistaVoluntia.Dtos;

import ApiVoluntia.ApiVoluntia.Dtos.UsuarioDtos;

/**
 * Clase DTO que representa los datos de un contrato firmado en el sistema.
 * Incluye información sobre el archivo PDF del contrato, el CIF de la empresa,
 * el importe, el DNI del firmante, el identificador del contrato, el nombre y
 * dirección del cliente, y un objeto `UsuarioDtos` asociado al contrato.
 * 
 * @author DMN
 * @version 1.0 - 26/04/2025
 */

public class ContratoDtos {

	private Long idContrato;

	private byte[] archivoPDF;

	private String cifEmpresa;

	private String importe;

	private String dniFirmante;

	private String identificador;

	private String nombreCliente;

	private String direccionCliente;

	private UsuarioDtos usuario;

	public ContratoDtos(byte[] archivoPDF, String cifEmpresa, String importe, String dniFirmante, String identificador,
			String nombreCliente, String direccionCliente) {
		this.archivoPDF = archivoPDF;
		this.cifEmpresa = cifEmpresa;
		this.importe = importe;
		this.dniFirmante = dniFirmante;
		this.identificador = identificador;
		this.nombreCliente = nombreCliente;
		this.direccionCliente = direccionCliente;
	}

	public ContratoDtos() {
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

	public UsuarioDtos getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioDtos usuario) {
		this.usuario = usuario;
	}

}
