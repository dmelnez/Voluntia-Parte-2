package ApiVoluntia.ApiVoluntia.Controladores;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ApiVoluntia.ApiVoluntia.Dtos.ContratoDtos;
import ApiVoluntia.ApiVoluntia.Dtos.UsuarioDtos;
import ApiVoluntia.ApiVoluntia.Repositorios.ServicioInterfaz;
import ApiVoluntia.ApiVoluntia.Repositorios.UsuarioInterfaz;
import ApiVoluntia.ApiVoluntia.Servicios.UsuarioFuncionalidades;
import ApiVoluntia.ApiVoluntia.Utilidades.Utilidades;

/**
 * Controlador REST para la gestión de usuarios y contratos.
 * <p>
 * Este controlador expone endpoints para obtener, guardar, actualizar y
 * eliminar usuarios, así como para gestionar el login, la validación de correos
 * duplicados y la creación de contratos asociados a planes.
 * </p>
 * 
 * @author DMN
 */
@RestController
@RequestMapping("/api/usuario")
public class UsuarioControlador {

	@Autowired
	UsuarioFuncionalidades usuarioServicio;

	@Autowired
	UsuarioInterfaz usuarioInterfaz;

	@Autowired
	ServicioInterfaz servicioInterfaz;

	@Autowired
	Utilidades utilidades;

	/**
	 * Endpoint para obtener la lista de todos los usuarios.
	 * 
	 * @return ArrayList de UsuarioDtos con todos los usuarios registrados. En caso
	 *         de error, retorna una lista vacía.
	 */
	@GetMapping("/usuarios")
	public ArrayList<UsuarioDtos> listausuarios() {
		utilidades.ficheroLog(1, "UsuarioControlador - listausuarios() - Entrada");
		try {
			return usuarioServicio.getUsuario();
		} catch (Exception e) {
			utilidades.ficheroLog(3, "UsuarioControlador - listausuarios() - " + e.getMessage());
			return new ArrayList<>();
		}
	}

	/**
	 * Endpoint para guardar un nuevo usuario.
	 * 
	 * @param usuario Objeto UsuarioDtos con los datos del usuario a guardar.
	 * @return El usuario guardado o null en caso de error.
	 */
	@PostMapping("/guardarusuario")
	public UsuarioDtos guardarUsuario(@RequestBody UsuarioDtos usuario) {
		utilidades.ficheroLog(1, "UsuarioControlador - guardarUsuario() - Entrada");
		try {
			return usuarioServicio.guardarUsuario(usuario);
		} catch (Exception e) {
			utilidades.ficheroLog(3, "UsuarioControlador - guardarUsuario() - " + e.getMessage());
			throw e;
		}
	}

	@GetMapping("/organizacion/{id_org}")
	public List<UsuarioDtos> listarPorOrganizacion(@PathVariable("id_org") Long idOrg) {
		return usuarioServicio.listarPorOrganizacion(idOrg);
	}

	/**
	 * Endpoint para realizar el login de un usuario.
	 * <p>
	 * Se verifica el correo del usuario y se devuelven las credenciales si son
	 * correctas.
	 * </p>
	 * 
	 * @param usuario Objeto UsuarioDtos que contiene el correo (y otros datos, si
	 *                aplica).
	 * @return ResponseEntity con el usuario completo en caso de éxito o 401
	 *         Unauthorized si falla.
	 */
	@PostMapping("/login")
	public ResponseEntity<UsuarioDtos> verificarUsuario(@RequestBody UsuarioDtos usuarioReq) {
		UsuarioDtos u = usuarioServicio.verificarCredenciales(usuarioReq.getCorreoUsuario(),
				usuarioReq.getPasswordUsuario());
		if (u == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		u.setPasswordUsuario(null);
		return ResponseEntity.ok(u);
	}

	/**
	 * Endpoint para eliminar un usuario por su ID.
	 * 
	 * @param id_usuario ID del usuario a eliminar.
	 * @return true si el usuario se eliminó correctamente, false en caso de error.
	 */
	/*
	@DeleteMapping("/eliminarusuario/{id_usuario}")
	public boolean deleteUsuario(@PathVariable("id_usuario") String idUsuario) {
		utilidades.ficheroLog(1, "UsuarioControlador - deleteUsuario() - Entrada");
		try {
			return usuarioServicio.eliminarUsuario(idUsuario);
		} catch (Exception e) {
			utilidades.ficheroLog(3, "UsuarioControlador - deleteUsuario() - " + e.getMessage());
			return false;
		}
	}*/
	@DeleteMapping("/eliminarusuario/{id_usuario}")
	public ResponseEntity<Void> deleteUsuario(@PathVariable("id_usuario") String idUsuario) {
	    utilidades.ficheroLog(1, "UsuarioControlador - deleteUsuario() - Entrada");
	    try {
	        boolean ok = usuarioServicio.eliminarUsuario(idUsuario);
	        if (!ok) {
	            return ResponseEntity.status(HttpStatus.CONFLICT).build();
	        }
	        return ResponseEntity.ok().build();
	    } catch (Exception e) {
	        utilidades.ficheroLog(3, "UsuarioControlador - deleteUsuario() - " + e.getMessage());
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	    }
	}

	/**
	 * Endpoint para modificar los datos de un usuario.
	 * 
	 * @param idUsuario ID del usuario a modificar.
	 * @param usuario   Objeto UsuarioDtos con los nuevos datos.
	 * @return true si el usuario se modificó correctamente, false en caso de error.
	 */
	@PutMapping("/modificarusuario/{id_usuario}")
	public boolean putUsuario(@PathVariable("id_usuario") String idUsuario, @RequestBody UsuarioDtos usuario) {
		utilidades.ficheroLog(1, "UsuarioControlador - putUsuario() - Entrada");
		try {
			return usuarioServicio.modificarUsuario(idUsuario, usuario);
		} catch (Exception e) {
			utilidades.ficheroLog(3, "UsuarioControlador - putUsuario() - " + e.getMessage());
			return false;
		}
	}

	/**
	 * Devuelve todos los correos de los usuarios que pertenezcan a la organización
	 * indicada.
	 * 
	 * @param idOrganizacion El identificador de la organización.
	 */
	@GetMapping("/correos/{idOrganizacion}")
	public ResponseEntity<List<String>> obtenerCorreosPorOrganizacion(@PathVariable Long idOrganizacion) {
		utilidades.ficheroLog(1,
				"UsuarioControlador - obtenerCorreosPorOrganizacion() - Entrada para org=" + idOrganizacion);
		try {
			List<String> correos = usuarioServicio.getCorreosPorOrganizacion(idOrganizacion);
			return ResponseEntity.ok(correos);
		} catch (Exception e) {
			utilidades.ficheroLog(3, "UsuarioControlador - obtenerCorreosPorOrganizacion() - Error: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	/**
	 * Endpoint para guardar un contrato asociado a un plan.
	 * <p>
	 * Asocia el plan identificado por id_plan al contrato y lo guarda.
	 * </p>
	 * 
	 * @param idPlan   ID del plan a asociar.
	 * @param contrato Objeto ContratoDtos a guardar.
	 * @return ResponseEntity con el contrato creado y el código HTTP
	 *         correspondiente.
	 */
	@PostMapping("/guardarcontrato/plan/{id_plan}")
	public ResponseEntity<ContratoDtos> guardarContratoConPlan(@PathVariable("id_plan") Long idPlan,
			@RequestBody ContratoDtos contrato) {
		utilidades.ficheroLog(1, "UsuarioControlador - guardarContratoConPlan() - Entrada");
		
	
		try {
			
			UsuarioDtos usuario = new UsuarioDtos();
			
			 for(UsuarioDtos usu:contrato.getOrganizacion().getUsuarios()) {
				 
				 usuario = usu;
				 break;
				 				 
			 }
				
			 usuarioServicio.guardarUsuario(usuario);
			ContratoDtos nuevoContrato = usuarioServicio.guardarContratoConPlan(idPlan, contrato);
			return new ResponseEntity<>(nuevoContrato, HttpStatus.CREATED);
		} catch (RuntimeException e) {
			utilidades.ficheroLog(3, "UsuarioControlador - guardarContratoConPlan() - " + e.getMessage());
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			utilidades.ficheroLog(3, "UsuarioControlador - guardarContratoConPlan() - " + e.getMessage());
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Endpoint para validar si un correo aparece más de una vez en la base de
	 * datos.
	 * 
	 * @param correo El correo a verificar.
	 * @return ResponseEntity con true si el correo está duplicado, false en caso
	 *         contrario.
	 */
	@GetMapping("/validarcorreo")
	public ResponseEntity<Boolean> validarCorreo(@RequestParam String correo) {
		utilidades.ficheroLog(1, "UsuarioControlador - validarCorreo() - Entrada");
		try {
			boolean duplicado = usuarioServicio.correoDuplicado(correo);
			return ResponseEntity.ok(duplicado);
		} catch (Exception e) {
			utilidades.ficheroLog(3, "UsuarioControlador - validarCorreo() - " + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	/**
	 * Endpoint para obtener el número total de usuarios registrados.
	 * 
	 * @return ResponseEntity con el total de usuarios y código HTTP 200 OK, o error
	 *         en caso de fallo.
	 */
	@GetMapping("/total")
	public ResponseEntity<Long> contarUsuarios() {
		utilidades.ficheroLog(1, "UsuarioControlador - contarUsuarios() - Entrada");
		try {
			long totalUsuarios = usuarioServicio.contarUsuarios();
			return new ResponseEntity<>(totalUsuarios, HttpStatus.OK);
		} catch (Exception e) {
			utilidades.ficheroLog(3, "UsuarioControlador - contarUsuarios() - " + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@GetMapping("/total/{orgId}")
	public ResponseEntity<Long> contarUsuariosPorOrganizacion(@PathVariable Long orgId) {
		utilidades.ficheroLog(1, "UsuarioControlador - contarUsuariosPorOrganizacion() - Entrada orgId=" + orgId);
		try {
			long totalUsuarios = usuarioServicio.contarUsuariosPorOrganizacion(orgId);
			return new ResponseEntity<>(totalUsuarios, HttpStatus.OK);
		} catch (Exception e) {
			utilidades.ficheroLog(3, "UsuarioControlador - contarUsuariosPorOrganizacion() - " + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	/**
	 * Endpoint para actualizar el token y la fecha generada del token de un
	 * usuario, basado en su correo.
	 * 
	 * @param usuarioDto Objeto UsuarioDtos que contiene el correo, token y fecha
	 *                   generada del token.
	 * @return ResponseEntity con el usuario actualizado o un mensaje de error en
	 *         caso de que no se encuentre.
	 */
	@PutMapping("/actualizartokencorreo")
	public ResponseEntity<?> actualizarTokenCorreo(@RequestBody UsuarioDtos usuarioDto) {
		utilidades.ficheroLog(1, "UsuarioControlador - actualizarTokenCorreo() - Entrada");
		try {
			UsuarioDtos usuarioExistente = usuarioServicio.buscarPorCorreo(usuarioDto.getCorreoUsuario());
			if (usuarioExistente == null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND)
						.body("Usuario con correo " + usuarioDto.getCorreoUsuario() + " no encontrado.");
			}
			usuarioExistente.setTokenUsuario(usuarioDto.getTokenUsuario());
			usuarioExistente.setFechaGeneradaTokenUsuario(usuarioDto.getFechaGeneradaTokenUsuario());
			usuarioServicio.guardarUsuario(usuarioExistente);
			return ResponseEntity.ok(usuarioExistente);
		} catch (Exception e) {
			utilidades.ficheroLog(3, "UsuarioControlador - actualizarTokenCorreo() - " + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al actualizar token.");
		}
	}

	/**
	 * Endpoint para crear un contrato asociado a un plan y, de manera implícita, al
	 * usuario.
	 * 
	 * @param idPlan   ID del plan a asociar.
	 * @param contrato Objeto ContratoDtos a guardar.
	 * @return ResponseEntity con el contrato creado y el código HTTP
	 *         correspondiente.
	 */
	@PostMapping("/contrato/plan/{id_plan}")
	public ResponseEntity<ContratoDtos> crearContratoConPlan(@PathVariable("id_plan") Long idPlan,
			@RequestBody ContratoDtos contrato) {
		utilidades.ficheroLog(1, "UsuarioControlador - crearContratoConPlan() - Entrada");
		try {
			ContratoDtos nuevo = usuarioServicio.guardarContratoConPlan(idPlan, contrato);
			return new ResponseEntity<>(nuevo, HttpStatus.CREATED);
		} catch (RuntimeException e) {
			utilidades.ficheroLog(3, "Error: " + e.getMessage());
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			utilidades.ficheroLog(3, "Error: " + e.getMessage());
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Endpoint para actualizar la contraseña a través del token.
	 * 
	 * @param token           Token de verificación enviado desde la vista.
	 * @param nuevaContrasena La nueva contraseña en texto plano.
	 * @return ResponseEntity con mensaje de éxito o error.
	 */
	@PostMapping("/actualizar-password")
	public ResponseEntity<?> actualizarContraseniaPorToken(@RequestParam("token") String token,
			@RequestParam("nuevaContrasena") String nuevaContrasena) {
		utilidades.ficheroLog(1, "UsuarioControlador - actualizarContraseniaPorToken() - Entrada");
		try {
			boolean actualizado = usuarioServicio.actualizarContraseniaPorToken(token, nuevaContrasena);
			if (actualizado) {
				return ResponseEntity.ok("Contraseña actualizada correctamente.");
			} else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body("El token es inválido o ha expirado. No se pudo actualizar la contraseña.");
			}
		} catch (Exception e) {
			utilidades.ficheroLog(3, "UsuarioControlador - actualizarContraseniaPorToken() - " + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al actualizar la contraseña.");
		}
	}

	/**
	 * Endpoint para actualizar la contraseña de un usuario mediante su ID. Se
	 * espera recibir el parámetro "id" y "nuevaContrasena" en la petición.
	 * 
	 * @param id              ID del usuario.
	 * @param nuevaContrasena La nueva contraseña en texto plano.
	 * @return ResponseEntity con mensaje de éxito o error.
	 */
	@PutMapping("/actualizar-password-por-id")
	public ResponseEntity<?> actualizarPasswordPorId(@RequestParam("id") Long id,
			@RequestParam("nuevaContrasena") String nuevaContrasena) {
		utilidades.ficheroLog(1, "UsuarioControlador - actualizarPasswordPorId() - Entrada");
		try {
			boolean actualizado = usuarioServicio.actualizarContraseniaPorId(id, nuevaContrasena);
			if (actualizado) {
				return ResponseEntity.ok("Contraseña actualizada exitosamente.");
			} else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body("Error al actualizar la contraseña. Verifica que el ID sea correcto.");
			}
		} catch (Exception e) {
			utilidades.ficheroLog(3, "UsuarioControlador - actualizarPasswordPorId() - " + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al actualizar la contraseña.");
		}
	}
}
