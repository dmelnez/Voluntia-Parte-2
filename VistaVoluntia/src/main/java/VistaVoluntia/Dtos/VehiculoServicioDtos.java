package VistaVoluntia.Dtos;

import ApiVoluntia.ApiVoluntia.Dtos.ServicioDtos;
import ApiVoluntia.ApiVoluntia.Dtos.VehiculoDtos;

/**
 * Clase DTO que representa la relación entre un vehículo y un servicio.
 * Contiene los datos necesarios para la asociación de ambas entidades.
 * 
 * @author DMN
 * @version 1.0 - 26/04/2025
 */
public class VehiculoServicioDtos {

	private Long idVehiculoServicio;

	private VehiculoDtos vehiculo;

	private ServicioDtos servicio;

	public VehiculoServicioDtos() {
	}

	public VehiculoServicioDtos(VehiculoDtos vehiculo, ServicioDtos servicio) {
		this.vehiculo = vehiculo;
		this.servicio = servicio;
	}

	public Long getIdVehiculoServicio() {
		return idVehiculoServicio;
	}

	public void setIdVehiculoServicio(Long idVehiculoServicio) {
		this.idVehiculoServicio = idVehiculoServicio;
	}

	public VehiculoDtos getVehiculo() {
		return vehiculo;
	}

	public void setVehiculo(VehiculoDtos vehiculo) {
		this.vehiculo = vehiculo;
	}

	public ServicioDtos getServicio() {
		return servicio;
	}

	public void setServicio(ServicioDtos servicio) {
		this.servicio = servicio;
	}

}
