package ApiVoluntia.ApiVoluntia.Servicios;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ApiVoluntia.ApiVoluntia.Dtos.EmergenciaDtos;
import ApiVoluntia.ApiVoluntia.Dtos.OrganizacionDtos;
import ApiVoluntia.ApiVoluntia.Repositorios.EmergenciaInterfaz;
import ApiVoluntia.ApiVoluntia.Repositorios.OrganizacionInterfaz;
import ApiVoluntia.ApiVoluntia.Repositorios.UsuarioInterfaz;
import jakarta.transaction.Transactional;

@Service
public class EmergenciaFuncionalidades {

    @Autowired
    EmergenciaInterfaz emergenciaInterfaz;
    
    @Autowired
    UsuarioInterfaz usuarioInterfaz;

    @Autowired
    OrganizacionInterfaz organizacionInterfaz;
    
    /**
     * Método encargado de buscar todas las emergencias existentes en la base de datos.
     * 
     * @return ArrayList de EmergenciaDtos con todas las emergencias.
     * @author DMN - 14/02/2025
     */
    public ArrayList<EmergenciaDtos> getEmergencia() {
        return (ArrayList<EmergenciaDtos>) emergenciaInterfaz.findAll();
    }
    
    /**
     * Método encargado de guardar una nueva emergencia en la base de datos.
     * 
     * @param emergencia Objeto EmergenciaDtos a guardar.
     * @return La emergencia guardada.
     * @author DMN - 14/02/2025
     */
    @Transactional
    public EmergenciaDtos guardarEmergencia(EmergenciaDtos emergencia) {
        Long idOrg = emergencia.getOrganizacion() != null
                ? emergencia.getOrganizacion().getIdOrganizacion()
                : null;

        if (idOrg == null) {
            throw new IllegalArgumentException("Debe indicar idOrganizacion en el JSON");
        }

        OrganizacionDtos org = organizacionInterfaz.findById(idOrg)
            .orElseThrow(() -> new IllegalArgumentException(
                "No existe organización con ID " + idOrg));

        emergencia.setOrganizacion(org);
        return emergenciaInterfaz.save(emergencia);
    }

    @Transactional
    public List<EmergenciaDtos> listarPorOrganizacion(Long idOrganizacion) {
        return emergenciaInterfaz.findByOrganizacion_IdOrganizacion(idOrganizacion);
    }
    
    /**
     * Método encargado de eliminar una emergencia en base a su ID.
     * <p>
     * Convierte el ID a tipo long, verifica si la emergencia existe y, en caso afirmativo,
     * elimina la emergencia.
     * </p>
     * 
     * @param id ID de la emergencia a eliminar (en formato String).
     * @return true si la emergencia fue eliminada correctamente, false en caso contrario.
     * @author DMN - 14/02/2025
     */
    @Transactional
    public boolean eliminarEmergencia(String idEmergenciaStr) {
        long idEmergencia;
        try {
            idEmergencia = Long.parseLong(idEmergenciaStr);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("ID de emergencia no válido: " + idEmergenciaStr);
        }

        EmergenciaDtos emergencia = emergenciaInterfaz.findById(idEmergencia)
            .orElseThrow(() -> new IllegalArgumentException(
                "No existe emergencia con ID " + idEmergencia));

        emergenciaInterfaz.delete(emergencia);
        return true;
    }

    
    /**
     * Método encargado de eliminar todas las emergencias de la base de datos.
     * 
     * @return true si se eliminaron correctamente, false en caso de error.
     * @author DMN - 14/02/2025
     */
    public boolean eliminarTodasEmergencias() {
        try {
            emergenciaInterfaz.deleteAll();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Método encargado de modificar los datos de una emergencia en base a su ID.
     * 
     * @param id         ID de la emergencia a modificar (en formato String).
     * @param emergencia Objeto EmergenciaDtos con los nuevos datos.
     * @return true si la emergencia fue modificada con éxito, false en caso contrario.
     * @author DMN - 14/02/2025
     */
    public boolean modificarEmergencia(String id, EmergenciaDtos emergencia) {
        boolean esModificado = false;
        Long idEmergencia = Long.parseLong(id);

        EmergenciaDtos emergenciaDtos = emergenciaInterfaz.findByIdEmergencia(idEmergencia);

        if (emergenciaDtos == null) {
            System.out.println("La emergencia no existe");
        } else {
            emergenciaDtos.setTituloEmergencia(emergencia.getTituloEmergencia());
            emergenciaDtos.setCategoriaEmergencia(emergencia.getCategoriaEmergencia());
            emergenciaDtos.setDescripcionEmergencia(emergencia.getDescripcionEmergencia());
            emergenciaInterfaz.save(emergenciaDtos);
            esModificado = true;
            System.out.println("La emergencia ha sido modificada con éxito");
        }

        return esModificado;
    }
    
    /**
     * Método encargado de obtener la cantidad total de emergencias registradas en la base de datos.
     * 
     * @return long - Total de emergencias.
     * @author DMN - 01/02/2025
     */
    
    public long contarEmergencias() {
        return emergenciaInterfaz.count();
    }
    public long contarEmergencias(Long orgId) {
        return emergenciaInterfaz.countByOrganizacion_IdOrganizacion(orgId);
    }
}
