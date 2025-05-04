package VistaVoluntia.Servicios;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import VistaVoluntia.Dtos.OrganizacionDtos;

public class OrganizacionServicios {

	/**
	 * Guarda una organización enviando una solicitud POST a la API.
	 * 
	 * @param organizacion Objeto OrganizacionDtos con los datos a guardar.
	 * 
	 * @author DMN - 27/03/2025
	 */
	public void guardarOrganizacion(OrganizacionDtos organizacion) {
		try {
			JSONObject json = new JSONObject();
			json.put("ciudadOrganizacion", organizacion.getCiudadOrganizacion());
			json.put("direccionOrganizacion", organizacion.getDireccionOrganizacion());
			json.put("emailOrganizacion", organizacion.getEmailOrganizacion());
			json.put("nifOrganizacion", organizacion.getNifOrganizacion());
			json.put("nombreOrganizacion", organizacion.getNombreOrganizacion());
			json.put("provinciaOrganizacion", organizacion.getProvinciaOrganizacion());
			json.put("telefonoOrganizacion", organizacion.getTelefonoOrganizacion());
			json.put("tipoInstitucionOrganizacion", organizacion.getTipoInstitucionOrganizacion());

			URL url = new URL("http://localhost:9526/api/organizacion/guardarorganizacion");
			HttpURLConnection conex = (HttpURLConnection) url.openConnection();
			conex.setRequestMethod("POST");
			conex.setRequestProperty("Content-Type", "application/json");
			conex.setDoOutput(true);

			try (OutputStream os = conex.getOutputStream()) {
				byte[] input = json.toString().getBytes("utf-8");
				os.write(input, 0, input.length);
			}

			int responseCode = conex.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) {
				System.out.println("Organización guardada correctamente.");
			} else {
				System.out.println("Error al guardar Organización: " + responseCode);
			}

		} catch (Exception e) {
			System.err.println("ERROR - OrganizacionServicios - guardarOrganizacion: " + e);
			e.printStackTrace();
		}
	}

	/**
	 * Obtiene la lista de organizaciones desde la API.
	 * 
	 * @return ArrayList de objetos OrganizacionDtos.
	 * 
	 * @author DMN - 14/04/2025
	 */
	public ArrayList<OrganizacionDtos> listaOrganizaciones() {
		ArrayList<OrganizacionDtos> lista = new ArrayList<>();
		try {
			URL url = new URL("http://localhost:9526/api/organizacion/organizaciones");
			HttpURLConnection conex = (HttpURLConnection) url.openConnection();
			conex.setRequestMethod("GET");
			conex.setRequestProperty("Accept", "application/json");

			int responseCode = conex.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) {
				try (BufferedReader in = new BufferedReader(new InputStreamReader(conex.getInputStream()))) {
					StringBuilder response = new StringBuilder();
					String inputLine;
					while ((inputLine = in.readLine()) != null) {
						response.append(inputLine);
					}
					JSONArray jsonlista = new JSONArray(response.toString());
					for (int i = 0; i < jsonlista.length(); i++) {
						JSONObject obj = jsonlista.getJSONObject(i);
						OrganizacionDtos org = new OrganizacionDtos();
						org.setIdOrganizacion(obj.getLong("idOrganizacion"));
						org.setCiudadOrganizacion(obj.getString("ciudadOrganizacion"));
						org.setDireccionOrganizacion(obj.getString("direccionOrganizacion"));
						org.setEmailOrganizacion(obj.getString("emailOrganizacion"));
						org.setNifOrganizacion(obj.getString("nifOrganizacion"));
						org.setNombreOrganizacion(obj.getString("nombreOrganizacion"));
						org.setProvinciaOrganizacion(obj.getString("provinciaOrganizacion"));
						org.setTelefonoOrganizacion(obj.getString("telefonoOrganizacion"));
						org.setTipoInstitucionOrganizacion(obj.getString("tipoInstitucionOrganizacion"));
						lista.add(org);
					}
				}
			} else {
				System.out.println("Error al obtener Organizaciones. Código de respuesta: " + responseCode);
				try (BufferedReader in = new BufferedReader(new InputStreamReader(conex.getErrorStream()))) {
					StringBuilder err = new StringBuilder();
					String line;
					while ((line = in.readLine()) != null) {
						err.append(line);
					}
					System.out.println("Respuesta de error del servidor: " + err);
				}
			}

		} catch (Exception e) {
			System.err.println("ERROR - OrganizacionServicios - listaOrganizaciones: " + e);
			e.printStackTrace();
		}
		return lista;
	}

	/**
	 * Elimina la organización con el identificador especificado.
	 * 
	 * @param idOrganizacion Identificador de la organización a eliminar.
	 * @return true si la eliminación fue exitosa, false en caso contrario.
	 * 
	 * @author DMN - 16/03/2025
	 */
	public boolean eliminarOrganizacion(String idOrganizacion) {
		try {
			URL url = new URL("http://localhost:9526/api/organizacion/eliminarorganizacion/" + idOrganizacion);
			HttpURLConnection conex = (HttpURLConnection) url.openConnection();
			conex.setRequestMethod("DELETE");
			conex.setRequestProperty("Content-Type", "application/json");
			int responseCode = conex.getResponseCode();
			return responseCode == HttpURLConnection.HTTP_OK;
		} catch (Exception e) {
			System.err.println("ERROR - OrganizacionServicios - eliminarOrganizacion: " + e);
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Modifica los datos de una organización existente.
	 * 
	 * @param idOrganizacion Identificador de la organización a modificar.
	 * @param organizacion   Objeto OrganizacionDtos con los nuevos datos.
	 * @return true si la modificación fue exitosa, false en caso contrario.
	 * 
	 * @author DMN - 19/04/2025
	 */
	public boolean modificarOrganizacion(String idOrganizacion, OrganizacionDtos organizacion) {
		try {
			JSONObject json = new JSONObject();
			json.put("ciudadOrganizacion", organizacion.getCiudadOrganizacion());
			json.put("direccionOrganizacion", organizacion.getDireccionOrganizacion());
			json.put("emailOrganizacion", organizacion.getEmailOrganizacion());
			json.put("nifOrganizacion", organizacion.getNifOrganizacion());
			json.put("nombreOrganizacion", organizacion.getNombreOrganizacion());
			json.put("provinciaOrganizacion", organizacion.getProvinciaOrganizacion());
			json.put("telefonoOrganizacion", organizacion.getTelefonoOrganizacion());
			json.put("tipoInstitucionOrganizacion", organizacion.getTipoInstitucionOrganizacion());

			URL url = new URL("http://localhost:9526/api/organizacion/modificarorganizacion/" + idOrganizacion);
			HttpURLConnection conex = (HttpURLConnection) url.openConnection();
			conex.setRequestMethod("PUT");
			conex.setRequestProperty("Content-Type", "application/json");
			conex.setDoOutput(true);

			try (OutputStream os = conex.getOutputStream()) {
				byte[] input = json.toString().getBytes("utf-8");
				os.write(input, 0, input.length);
			}

			int responseCode = conex.getResponseCode();
			return responseCode == HttpURLConnection.HTTP_OK;
		} catch (Exception e) {
			System.err.println("ERROR - OrganizacionServicios - modificarOrganizacion: " + e);
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Obtiene el número total de organizaciones desde la API.
	 * 
	 * @return Número total de organizaciones.
	 * 
	 * @author DMN - 10/04/2025
	 */
	public long obtenerTotalOrganizaciones() {
		long total = 0;
		try {
			URL url = new URL("http://localhost:9526/api/organizacion/total");
			HttpURLConnection conex = (HttpURLConnection) url.openConnection();
			conex.setRequestMethod("GET");
			conex.setRequestProperty("Accept", "application/json");

			int responseCode = conex.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) {
				try (BufferedReader in = new BufferedReader(new InputStreamReader(conex.getInputStream()))) {
					String line = in.readLine();
					total = Long.parseLong(line.trim());
				}
			} else {
				System.out.println("Error al obtener total de organizaciones. Código: " + responseCode);
			}
		} catch (Exception e) {
			System.err.println("ERROR - OrganizacionServicios - obtenerTotalOrganizaciones: " + e);
			e.printStackTrace();
		}
		return total;
	}

}
