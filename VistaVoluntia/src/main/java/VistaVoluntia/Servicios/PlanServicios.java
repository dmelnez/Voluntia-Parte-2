package VistaVoluntia.Servicios;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import ApiVoluntia.ApiVoluntia.Dtos.PlanDtos;

/**
 * Clase para la gestión de Planes, que provee métodos para guardar, eliminar,
 * modificar y listar planes.
 * 
 * @author DMN - 14/02/2025
 */
public class PlanServicios {

	/**
	 * Guarda un plan enviando una solicitud POST a la API.
	 * 
	 * @param plan Objeto PlanDtos que contiene los datos del plan a guardar.
	 * @author DMN - 14/02/2025
	 */
	public void guardarPlan(PlanDtos plan) {
		try {
			JSONObject json = new JSONObject();
			json.put("tipoPlan", plan.getTipoPlan());
			json.put("precioPlan", plan.getPrecioPlan());
			json.put("tiempoPlan", plan.getTiempoPlan());
			json.put("descripcionPlan", plan.getDescripcionPlan());
			json.put("numeroUsuariosPlan", plan.getNumeroUsuariosPlan());

			String urlApi = "http://localhost:9526/api/plan/guardarplan";
			URL url = new URL(urlApi);

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
				System.out.println("Plan guardado correctamente.");
			} else {
				System.out.println("Error al guardar Plan: " + responseCode);
			}
		} catch (Exception e) {
			System.out.println("ERROR- [ServicioPlan] " + e);
		}
	}

	/**
	 * Elimina un plan identificado por su ID enviando una solicitud DELETE a la
	 * API.
	 * 
	 * @param idPlan El ID del plan a eliminar.
	 * @return {@code true} si la eliminación fue exitosa, {@code false} en caso
	 *         contrario.
	 * @author DMN - 14/02/2025
	 */
	public boolean eliminarPlan(String idPlan) {
		try {
			String urlApi = "http://localhost:9526/api/plan/eliminarplan/" + idPlan;
			URL url = new URL(urlApi);

			HttpURLConnection conex = (HttpURLConnection) url.openConnection();
			conex.setRequestMethod("DELETE");
			conex.setRequestProperty("Content-Type", "application/json");
			int responseCode = conex.getResponseCode();
			return responseCode == HttpURLConnection.HTTP_OK;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Modifica un plan existente identificado por su ID enviando una solicitud PUT
	 * a la API.
	 * 
	 * @param idPlan El ID del plan a modificar.
	 * @param plan   Objeto PlanDtos que contiene los nuevos datos del plan.
	 * @return {@code true} si la modificación fue exitosa, {@code false} en caso de
	 *         error.
	 * @author DMN - 14/02/2025
	 */
	public boolean modificarPlan(String idPlan, PlanDtos plan) {
		try {
			JSONObject json = new JSONObject();
			json.put("tipoPlan", plan.getTipoPlan());
			json.put("precioPlan", plan.getPrecioPlan());
			json.put("tiempoPlan", plan.getTiempoPlan());
			json.put("descripcionPlan", plan.getDescripcionPlan());
			json.put("numeroUsuariosPlan", plan.getNumeroUsuariosPlan());

			String urlApi = "http://localhost:9526/api/plan/modificarplan/" + idPlan;
			URL url = new URL(urlApi);

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
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Obtiene la lista de planes desde la API y la convierte a un ArrayList de
	 * PlanDtos.
	 * 
	 * @return Un ArrayList que contiene todos los planes obtenidos.
	 * @author DMN - 14/02/2025
	 */
	public ArrayList<PlanDtos> listaPlanes() {
		ArrayList<PlanDtos> lista = new ArrayList<>();

		try {
			String urlApi = "http://localhost:9526/api/plan/planes";
			URL url = new URL(urlApi);

			HttpURLConnection conex = (HttpURLConnection) url.openConnection();
			conex.setRequestMethod("GET");
			conex.setRequestProperty("Accept", "application/json");

			int responseCode = conex.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) {
				try (BufferedReader in = new BufferedReader(new InputStreamReader(conex.getInputStream(), "utf-8"))) {
					String inputLine;
					StringBuilder response = new StringBuilder();
					while ((inputLine = in.readLine()) != null) {
						response.append(inputLine);
					}

					JSONArray jsonlista = new JSONArray(response.toString());
					System.out.println("Planes obtenidos: " + jsonlista);

					for (int i = 0; i < jsonlista.length(); i++) {
						JSONObject jsonPlan = jsonlista.getJSONObject(i);
						PlanDtos planes = new PlanDtos();

						planes.setIdPlan(Long.parseLong(jsonPlan.optString("idPlan")));
						planes.setTipoPlan(jsonPlan.getString("tipoPlan"));
						planes.setPrecioPlan(Double.parseDouble(jsonPlan.optString("precioPlan")));
						planes.setDescripcionPlan(jsonPlan.getString("descripcionPlan"));
						planes.setTiempoPlan(jsonPlan.optString("tiempoPlan"));
						planes.setNumeroUsuariosPlan(jsonPlan.optString("numeroUsuariosPlan"));
						lista.add(planes);
					}
				}
			} else {
				System.out.println("Error al obtener los Planes. Código de respuesta: " + responseCode);
				try (BufferedReader in = new BufferedReader(new InputStreamReader(conex.getErrorStream(), "utf-8"))) {
					String inputLine;
					StringBuilder response = new StringBuilder();
					while ((inputLine = in.readLine()) != null) {
						response.append(inputLine);
					}
					System.out.println("Respuesta de error del servidor: " + response.toString());
				}
			}
		} catch (Exception e) {
			System.out.println("ERROR - PlanServicios - ListaPlanes " + e);
			e.printStackTrace();
		}

		return lista;
	}
}
