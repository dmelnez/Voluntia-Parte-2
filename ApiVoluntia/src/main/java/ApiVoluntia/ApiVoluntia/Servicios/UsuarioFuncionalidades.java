package ApiVoluntia.ApiVoluntia.Servicios;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ApiVoluntia.ApiVoluntia.Dtos.ContratoDtos;
import ApiVoluntia.ApiVoluntia.Dtos.OrganizacionDtos;
import ApiVoluntia.ApiVoluntia.Dtos.PlanDtos;
import ApiVoluntia.ApiVoluntia.Dtos.UsuarioDtos;
import ApiVoluntia.ApiVoluntia.Repositorios.*;
import jakarta.transaction.Transactional;

@Service
public class UsuarioFuncionalidades {

	@Autowired
	UsuarioInterfaz usuarioInferfaz;

	@Autowired
	ContratoInterfaz contratoInterfaz;

	@Autowired
	UsuarioServicioInterfaz usuarioServicioInterfaz;

	@Autowired
	PlanInterfaz planInterfaz;

	@Autowired
	OrganizacionInterfaz organizacionInterfaz;

	/**
	 * Método encargado de buscar todos los usuarios existentes en la base de datos.
	 * 
	 * @return ArrayList de UsuarioDtos con todos los usuarios.
	 * @author DMN - 14/02/2025
	 */
	public ArrayList<UsuarioDtos> getUsuario() {
		return (ArrayList<UsuarioDtos>) usuarioInferfaz.findAll();
	}

	public List<String> getCorreosPorOrganizacion(Long idOrganizacion) {
		return usuarioInferfaz.findByOrganizacion_IdOrganizacion(idOrganizacion) // Repositorio ajustado
				.stream().map(UsuarioDtos::getCorreoUsuario).collect(Collectors.toList());
	}

	/**
	 * Método encargado de guardar un contrato asociado a un plan.
	 * <p>
	 * Busca el plan por su ID y lo asigna al contrato. Si el contrato tiene un
	 * usuario, encripta la contraseña (que llega en texto plano) antes de guardar.
	 * </p>
	 * 
	 * @param idPlan   ID del plan a asociar.
	 * @param contrato Objeto ContratoDtos a guardar.
	 * @return El contrato guardado.
	 * @throws RuntimeException si el plan no es encontrado.
	 * @author DMN - 14/02/2025
	 */
	public ContratoDtos guardarContratoConPlan(Long idPlan, ContratoDtos contrato) {
		PlanDtos plan = planInterfaz.findById(idPlan).orElseThrow(() -> new RuntimeException("Plan no encontrado"));
		contrato.setPlan(plan);
		System.out.println("PRUEBA");		
		// Guardar la organización junto con sus usuarios
		OrganizacionDtos org = organizacionInterfaz.save(contrato.getOrganizacion());

		/*
		for (UsuarioDtos u : org.getUsuarios()) {
			u.setOrganizacion(org);
			u.setPasswordUsuario(encriptarContrasenya(u.getPasswordUsuario()));
			usuarioInferfaz.save(u);
		}*/

		contrato.setOrganizacion(org);

		org.setContrato(contrato);

		return contratoInterfaz.save(contrato);
	}

	/**
	 * Método encargado de obtener una lista de correos electrónicos de todos los
	 * usuarios.
	 * 
	 * @return ArrayList de String con los correos de los usuarios.
	 * @author DMN - 14/02/2025
	 */
	public ArrayList<String> getCorreos() {
		ArrayList<String> correos = new ArrayList<>();

		ArrayList<UsuarioDtos> usuarios = (ArrayList<UsuarioDtos>) usuarioInferfaz.findAll();

		for (UsuarioDtos usuario : usuarios) {
			correos.add(usuario.getCorreoUsuario());
		}

		return correos;
	}

	/**
	 * Método encargado de guardar un nuevo usuario en la base de datos.
	 * <p>
	 * Encripta la contraseña del usuario antes de guardarlo.
	 * </p>
	 * 
	 * @param usuario Objeto UsuarioDtos a guardar.
	 * @return El usuario guardado.
	 * @author DMN - 14/02/2025
	 */
	@Transactional
	public UsuarioDtos guardarUsuario(UsuarioDtos usuario) {
		Long idOrg = usuario.getOrganizacion() != null ? usuario.getOrganizacion().getIdOrganizacion() : null;

		if (idOrg == null) {
			throw new IllegalArgumentException("Debe indicar idOrganizacion en el JSON");
		}

		OrganizacionDtos org = organizacionInterfaz.findById(idOrg)
				.orElseThrow(() -> new IllegalArgumentException("No existe organización con ID " + idOrg));

		usuario.setOrganizacion(org);
		usuario.setPasswordUsuario(encriptarContrasenya(usuario.getPasswordUsuario()));
		return usuarioInferfaz.save(usuario);
	}

	@Transactional
	public List<UsuarioDtos> listarPorOrganizacion(Long idOrganizacion) {
		return usuarioInferfaz.findByOrganizacion_IdOrganizacion(idOrganizacion);
	}

	/**
	 * Método encargado de verificar las credenciales de un usuario por su correo.
	 * <p>
	 * Busca el usuario por correo y verifica si existe.
	 * </p>
	 * 
	 * @param correo Correo del usuario a verificar.
	 * @return El objeto UsuarioDtos si se encuentra; de lo contrario, null.
	 * @author DMN - 14/02/2025
	 */
	public UsuarioDtos verificarCredenciales(String correo, String password) {
		System.out.println("Correo en servicio: " + correo);

		UsuarioDtos usuario = usuarioInferfaz.findByCorreoUsuario(correo);
		if (usuario == null) {
			System.out.println("Usuario no encontrado.");
			return null;
		}

		System.out.println("Usuario encontrado, comprobando contraseña…");

		String hashInput = encriptarContrasenya(password);

		if (!hashInput.equals(usuario.getPasswordUsuario())) {
			System.out.println("Contraseña incorrecta.");
			return null;
		}

		System.out.println("Contraseña válida.");
		return usuario;
	}

	/**
	 * Método encargado de encriptar la contraseña utilizando SHA-256.
	 * 
	 * @param contraseña La contraseña en texto plano.
	 * @return La contraseña encriptada en formato hexadecimal.
	 * @throws RuntimeException si el algoritmo SHA-256 no está disponible.
	 * @author DMN - 14/02/2025
	 */
	public String encriptarContrasenya(String contraseña) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] hash = digest.digest(contraseña.getBytes());
			StringBuilder hexString = new StringBuilder();

			for (byte b : hash) {
				String hex = String.format("%02x", b);
				hexString.append(hex);
			}

			return hexString.toString();
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Método encargado de eliminar un usuario de la base de datos por su ID.
	 * <p>
	 * Elimina también las relaciones en la tabla intermedia asociadas al usuario.
	 * </p>
	 * 
	 * @param id El ID del usuario a eliminar en formato String.
	 * @return true si el usuario fue eliminado correctamente, false en caso
	 *         contrario.
	 * @author DMN - 14/02/2025
	 */
	/*
	@Transactional
	public boolean eliminarUsuario(String idUsuarioStr) {
		long idUsuario;
		try {
			idUsuario = Long.parseLong(idUsuarioStr);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("ID de usuario no válido: " + idUsuarioStr);
		}

		UsuarioDtos usuario = usuarioInferfaz.findById(idUsuario)
				.orElseThrow(() -> new IllegalArgumentException("No existe usuario con ID " + idUsuario));

		usuarioServicioInterfaz.deleteByUsuario(usuario);

		usuario.setOrganizacion(null);
		usuarioInferfaz.save(usuario);

		usuarioInferfaz.delete(usuario);

		return true;
	}*/
	@Transactional
	public boolean eliminarUsuario(String idUsuarioStr) {
	    long idUsuario = Long.parseLong(idUsuarioStr);
	    UsuarioDtos usuario = usuarioInferfaz.findById(idUsuario)
	        .orElseThrow(() -> new IllegalArgumentException("No existe usuario con ID " + idUsuario));
	    Long idOrg = usuario.getOrganizacion().getIdOrganizacion();
	    long totalEnOrg = usuarioInferfaz.countByOrganizacion_IdOrganizacion(idOrg);
	    if (totalEnOrg <= 1) {
	        return false;
	    }
	    String rol = usuario.getRolUsuario();
	    if ("admin".equals(rol) || "adminEmpresa".equals(rol)) {
	        long admins    = usuarioInferfaz.countByOrganizacion_IdOrganizacionAndRolUsuario(idOrg, "admin");
	        long empAdmins = usuarioInferfaz.countByOrganizacion_IdOrganizacionAndRolUsuario(idOrg, "adminEmpresa");
	        if (admins + empAdmins <= 1) {
	            return false;
	        }
	    }
	    usuario.setOrganizacion(null);
	    usuarioInferfaz.save(usuario);
	    usuarioInferfaz.delete(usuario);
	    return true;
	}

	/**
	 * Método encargado de modificar los datos de un usuario en base a su ID.
	 * 
	 * @param id      El ID del usuario a modificar en formato String.
	 * @param usuario Objeto UsuarioDtos con los nuevos datos.
	 * @return true si el usuario fue modificado exitosamente, false en caso
	 *         contrario.
	 * @author DMN - 14/02/2025
	 */
	public boolean modificarUsuario(String id, UsuarioDtos usuario) {
		boolean esModificado = false;
		Long idUsu = Long.parseLong(id);
		UsuarioDtos usuarioDtos = usuarioInferfaz.findByIdUsuario(idUsu);
		if (usuarioDtos == null) {
			esModificado = false;
			System.out.println("El usuario no existe");
		} else {
			usuarioDtos.setNombreUsuario(usuario.getNombreUsuario());
			usuarioDtos.setApellidosUsuario(usuario.getApellidosUsuario());
			usuarioDtos.setTelefonoUsuario(usuario.getTelefonoUsuario());
			usuarioDtos.setDniUsuario(usuario.getDniUsuario());
			usuarioDtos.setGeneroUsuario(usuario.getGeneroUsuario());
			usuarioDtos.setProvinciaUsuario(usuario.getProvinciaUsuario());
			usuarioDtos.setLocalidadUsuario(usuario.getLocalidadUsuario());
			usuarioDtos.setDireccionUsuario(usuario.getDireccionUsuario());
			usuarioDtos.setCodigoPostalUsuario(usuario.getCodigoPostalUsuario());
			usuarioDtos.setFechaIngresoUsuario(usuario.getFechaIngresoUsuario());
			usuarioDtos.setNumeroIdentificativoUsuario(usuario.getNumeroIdentificativoUsuario());
			usuarioDtos.setIndicativoUsuario(usuario.getIndicativoUsuario());
			usuarioDtos.setRolUsuario(usuario.getRolUsuario());
			usuarioDtos.setCorreoUsuario(usuario.getCorreoUsuario());
			usuarioDtos.setImagenPerfilUsuario(usuario.getImagenPerfilUsuario());
			usuarioInferfaz.save(usuarioDtos);
			esModificado = true;
			System.out.println("El usuario ha sido modificado con éxito");
		}

		return esModificado;
	}

	/**
	 * Método encargado de guardar un nuevo contrato en la base de datos.
	 * 
	 * @param contrato Objeto ContratoDtos a guardar.
	 * @return El contrato guardado.
	 * @author DMN - 14/02/2025
	 */
	public ContratoDtos guardarContrato(ContratoDtos contrato) {
		System.out.println("Se ha creado el contrato de manera exitosa");
		return contratoInterfaz.save(contrato);
	}

	/**
	 * Método encargado de buscar un usuario por su correo electrónico.
	 * 
	 * @param correo Correo del usuario a buscar.
	 * @return Objeto UsuarioDtos encontrado o null si no existe.
	 * @author DMN - 14/02/2025
	 */
	public UsuarioDtos buscarPorCorreo(String correo) {
		return usuarioInferfaz.findByCorreoUsuario(correo);
	}

	/**
	 * Método encargado de validar si el correo aparece más de una vez en la base de
	 * datos.
	 * 
	 * @param correo El correo a verificar.
	 * @return true si el correo está duplicado, false en caso contrario.
	 * @author DMN - 14/02/2025
	 */
	public boolean correoDuplicado(String correo) {
		ArrayList<String> correos = getCorreos();

		long ocurrencias = correos.stream().filter(c -> c.equalsIgnoreCase(correo)).count();
		return ocurrencias > 0;
	}

	/**
	 * Método encargado de obtener los números de teléfono de todos los usuarios.
	 * 
	 * @return ArrayList de String con los números de teléfono.
	 * @author DMN - 14/02/2025
	 */
	public ArrayList<String> getTelefonos() {
		ArrayList<String> telefonos = new ArrayList<>();

		ArrayList<UsuarioDtos> usuarios = (ArrayList<UsuarioDtos>) usuarioInferfaz.findAll();

		for (UsuarioDtos usuario : usuarios) {
			telefonos.add(usuario.getTelefonoUsuario());
		}

		return telefonos;
	}

	/**
	 * Método encargado de obtener la cantidad total de usuarios registrados en la
	 * base de datos.
	 * 
	 * @return Total de usuarios (long).
	 * @author DMN - 14/02/2025
	 */

	public long contarUsuarios() {
		return usuarioInferfaz.count();
	}

	public long contarUsuariosPorOrganizacion(Long orgId) {
		return usuarioInferfaz.countByOrganizacion_IdOrganizacion(orgId);
	}

	/**
	 * Método encargado de actualizar la contraseña de un usuario a través de un
	 * token.
	 * 
	 * Verifica que el token exista y que su fecha (que actúa como fecha de
	 * expiración) sea mayor que la fecha actual. Si es así, encripta la nueva
	 * contraseña, actualiza el registro y limpia el token para evitar
	 * reutilización.
	 *
	 * @param token           Token de verificación.
	 * @param nuevaContrasena La nueva contraseña en texto plano.
	 * @return true si la actualización fue exitosa, false en caso contrario.
	 */
	@Transactional
	public boolean actualizarContraseniaPorToken(String token, String nuevaContrasena) {
		UsuarioDtos usuario = usuarioInferfaz.findByTokenUsuario(token);
		if (usuario != null) {
			LocalDateTime fechaValidez = usuario.getFechaGeneradaTokenUsuario();

			if (fechaValidez != null && fechaValidez.isAfter(LocalDateTime.now())) {
				String encryptedPassword = encriptarContrasenya(nuevaContrasena);
				usuario.setPasswordUsuario(encryptedPassword);
				usuario.setTokenUsuario(null);
				usuario.setFechaGeneradaTokenUsuario(null);
				usuarioInferfaz.save(usuario);
				return true;
			}
		}
		return false;
	}

	/**
	 * Actualiza la contraseña de un usuario mediante su ID.
	 * 
	 * @param id              ID del usuario.
	 * @param nuevaContrasena La nueva contraseña en texto plano.
	 * @return true si la actualización fue exitosa, false en caso contrario.
	 */
	@Transactional
	public boolean actualizarContraseniaPorId(Long id, String nuevaContrasena) {
		Optional<UsuarioDtos> usuarioOpt = usuarioInferfaz.findById(id);
		if (usuarioOpt.isPresent()) {
			UsuarioDtos usuario = usuarioOpt.get();
			String encryptedPassword = encriptarContrasenya(nuevaContrasena);
			usuario.setPasswordUsuario(encryptedPassword);
			usuarioInferfaz.save(usuario);
			return true;
		}
		return false;
	}
}
