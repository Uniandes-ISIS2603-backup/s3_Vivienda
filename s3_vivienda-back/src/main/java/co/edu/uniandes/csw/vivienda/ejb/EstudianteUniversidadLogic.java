/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.vivienda.ejb;

import co.edu.uniandes.csw.vivienda.entities.EstudianteEntity;
import co.edu.uniandes.csw.vivienda.entities.UniversidadEntity;
import co.edu.uniandes.csw.vivienda.persistence.EstudiantePersistence;
import co.edu.uniandes.csw.vivienda.persistence.UniversidadPersistence;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * Clase que implementa la conexion con la persistencia para la relación entre
 * la entidad de Estudiante y Universidad.
 *
 * @author ISIS2603
 */
@Stateless
public class EstudianteUniversidadLogic {
    
    private static final Logger LOGGER = Logger.getLogger(EstudianteUniversidadLogic.class.getName());

    @Inject
    private EstudiantePersistence estudiantePersistence;

    @Inject
    private UniversidadPersistence universidadPersistence;

    /**
     * Remplazar la universidad de un estudiante.
     *
     * @param estudianteId id del estudiante que se quiere actualizar.
     * @param universidadId El id de la universidad que se será del estudiante.
     * @return el nuevo libro.
     */
    public EstudianteEntity replaceUniversidad(Long estudianteId, Long universidadId) {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar estudiante con id = {0}", estudianteId);
        UniversidadEntity universidadlEntity = universidadPersistence.find(universidadId);
        EstudianteEntity estudianteEntity = estudiantePersistence.find(estudianteId);
        estudianteEntity.setUniversidad(universidadlEntity);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar estudiante con id = {0}", estudianteEntity.getId());
        return estudianteEntity;
    }

    /**
     * Borrar un estudiante de una universidad. Este metodo se utiliza para borrar la
     * relacion de un estudiante.
     *
     * @param estudianteId El estudiante que se desea borrar de la universidad.
     */
    public void removeUniversidad(Long estudianteId) {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar la Editorial del estudiante con id = {0}", estudianteId);
        EstudianteEntity estudainteEntity = estudiantePersistence.find(estudianteId);
        UniversidadEntity universidadEntity = universidadPersistence.find(estudainteEntity.getUniversidad().getId());
        estudainteEntity.setUniversidad(null);
        universidadEntity.getEstudiantes().remove(estudainteEntity);
        LOGGER.log(Level.INFO, "Termina proceso de borrar la Editorial del estudiante con id = {0}", estudainteEntity.getId());
    }
}
