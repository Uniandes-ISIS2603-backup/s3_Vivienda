/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.vivienda.ejb;

import co.edu.uniandes.csw.vivienda.entities.EstudianteEntity;
import co.edu.uniandes.csw.vivienda.entities.UniversidadEntity;
import co.edu.uniandes.csw.vivienda.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.vivienda.persistence.EstudiantePersistence;
import co.edu.uniandes.csw.vivienda.persistence.UniversidadPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Paula Molina
 */
@Stateless
public class UniversidadEstudiantesLogic {
    
    private static final Logger LOGGER = Logger.getLogger(UniversidadEstudiantesLogic.class.getName());

    @Inject
    private EstudiantePersistence estudiantePersistence;

    @Inject
    private UniversidadPersistence universidadPersistence;

    /**
     * Agregar un book a la editorial
     *
     * @param estudianteId El id del estudiante a guardar
     * @param universidadId El id de la universidad en la cual se va a guardar el
     * estudiante.
     * @return El estudiante creado.
     */
    public EstudianteEntity addEstudiante(Long estudianteId, Long universidadId) {
        LOGGER.log(Level.INFO, "Inicia proceso de agregarle un estudiante a la universidad con id = {0}", universidadId);
        UniversidadEntity universidadEntity = universidadPersistence.find(universidadId);
        EstudianteEntity estudianteEntity = estudiantePersistence.find(estudianteId);
        estudianteEntity.setUniversidad(universidadEntity);
        LOGGER.log(Level.INFO, "Termina proceso de agregarle un libro a la editorial con id = {0}", universidadId);
        return estudianteEntity;
    }

    /**
     * Retorna todos los estudiantes asociados a una universidad
     *
     * @param universidadId El ID de la editorial buscada
     * @return La lista de libros de la editorial
     */
    public List<EstudianteEntity> getEstudiantes(Long universidadId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar los estudiantes asociados a la universidad con id = {0}", universidadId);
        return universidadPersistence.find(universidadId).getEstudiantes();
    }

    /**
     * Retorna un estudiante asociado a una Universidad
     *
     * @param universidadId El id de la editorial a buscar.
     * @param estudianteId El id del libro a buscar
     * @return El libro encontrado dentro de la editorial.
     * @throws BusinessLogicException Si el libro no se encuentra en la
     * editorial
     */
    public EstudianteEntity getEstudiante(Long universidadId, Long estudianteId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar el estudiante con id = {1} de la universidad con id = {0}", new Object[]{ universidadId, estudianteId});
        List<EstudianteEntity> estudiantes = universidadPersistence.find(universidadId).getEstudiantes();
        EstudianteEntity estudianteEntity = estudiantePersistence.find(estudianteId);
        int index = estudiantes.indexOf(estudianteEntity);
        LOGGER.log(Level.INFO, "Termina proceso de consultar el estudiante con id = {1} de la universidad con id = {0}" , new Object[]{ universidadId, estudianteId});
        if (index >= 0) {
            return estudiantes.get(index);
        }
        throw new BusinessLogicException("El libro no está asociado a la editorial");
    }

    /**
     * Remplazar estudiantes de una universidad
     *
     * @param estudiantes Lista de estudiantes que serán los de la universidad.
     * @param universidadId El id de la universidad que se quiere actualizar.
     * @return La lista de estudiantes actualizada.
     */
    public List<EstudianteEntity> replaceEstudiantes(Long universidadId, List<EstudianteEntity> estudiantes) {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar la universidad con id = {0}", universidadId);
        UniversidadEntity universidadEntity = universidadPersistence.find(universidadId);
        List<EstudianteEntity> estudiantesList = estudiantePersistence.findAll();
        for (EstudianteEntity estudiante : estudiantesList) {
            if (estudiantes.contains(estudiante)) {
                estudiante.setUniversidad(universidadEntity);
            } else if (estudiante.getUniversidad() != null && estudiante.getUniversidad().equals(universidadEntity)) {
                estudiante.setUniversidad(null);
            }
        }
        LOGGER.log(Level.INFO, "Termina proceso de actualizar la universidad con id = {0}", universidadId);
        return estudiantes;
    }
}
