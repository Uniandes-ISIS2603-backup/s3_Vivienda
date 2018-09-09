/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.vivienda.ejb;

import co.edu.uniandes.csw.vivienda.entities.EstudianteEntity;
import co.edu.uniandes.csw.vivienda.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.vivienda.persistence.EstudiantePersistence;
import co.edu.uniandes.csw.vivienda.persistence.UniversidadPersistence;
import java.util.Collection;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class EstudianteLogic {
    private static final Logger LOGGER = Logger.getLogger(EstudianteLogic.class.getName());

    @Inject
    private EstudiantePersistence persistence; // Variable para acceder a la persistencia de la aplicación. Es una inyección de dependencias.

    @Inject
    private UniversidadPersistence persistenceUniversidad;
    /**
     * Crea un estudiante en la persistencia.
     *
     * @param estudianteEntity La entidad que representa el estudiante a
     * persistir.
     * @return La entidad del estudiante luego de persistirlo.
     * @throws BusinessLogicException Si el estudiante a persistir ya existe.
     * @throws BusinessLogicException Si el login del estudiante a persistir ya existe.
     * @throws BusinessLogicException Si la universidad del estudiante a persistir no existe.
     */
    public EstudianteEntity createEstudiante(EstudianteEntity estudianteEntity) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de creación del estudiante");
        // Verifica la regla de negocio que dice que no puede haber dos contratos con el mismo ID
        if (persistence.find(estudianteEntity.getId()) != null) {
            throw new BusinessLogicException("Ya existe un estudiante con el id \"" + estudianteEntity.getId() + "\"");
        }
        if (persistence.findByLogin(estudianteEntity.getLogin()) != null) {
            throw new BusinessLogicException("Ya existe un estudiante con el login \"" + estudianteEntity.getLogin() + "\"");
        }
        if (estudianteEntity.getUniversidad() != null && persistenceUniversidad.find(estudianteEntity.getUniversidad().getId()) == null) {
            throw new BusinessLogicException("No existe la universidad con el id \"" +estudianteEntity.getUniversidad().getId() + "\"");
        }
        // Invoca la persistencia para crear el contrato
        persistence.create(estudianteEntity);
        LOGGER.log(Level.INFO, "Termina proceso de creación del estudiante");
        return estudianteEntity;
    }
    
    public Collection<EstudianteEntity> getEstudiantes(Long id){
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todos los libros");
        Collection<EstudianteEntity> estudiantes = persistence.findAll();
        LOGGER.log(Level.INFO, "Termina proceso de consultar todos los libros");
        return estudiantes;
    }
    
    public EstudianteEntity getEstudiante(Long id){
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todos los estudiantes");
        EstudianteEntity estudiante = persistence.find(id);
        if (estudiante == null) {
            LOGGER.log(Level.SEVERE, "El estudiantes con el id = {0} no existe", id);
        }
        LOGGER.log(Level.INFO, "Termina proceso de consultar todos los estudiantes");
        return estudiante;
    }
    
    public EstudianteEntity updateEstudiante(Long id, EstudianteEntity entity) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar el estudiante con id = {0}", id);
        EstudianteEntity prueba = persistence.findByLogin(entity.getLogin());
        if (prueba != null && !Objects.equals(prueba.getId(), id)) {
            throw new BusinessLogicException("Ya existe un estudiante con el login \"" + entity.getLogin() + "\"");
        }
        if (entity.getUniversidad() != null && persistenceUniversidad.find(entity.getUniversidad().getId()) == null) {
            throw new BusinessLogicException("No existe la universidad con el id \"" +entity.getUniversidad().getId() + "\"");
        }
        entity.setId(id);
        EstudianteEntity newEntity = persistence.update(entity);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar el estudiante con id = {0}", entity.getId());
        return newEntity;
    }

    /**
     * Borrar un estudiante
     *
     * @param estudiantesId: id del estudiante a borrar
     */
    public void deleteEstudiante(Long estudiantesId) {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar el estudiante con id = {0}", estudiantesId);
        // Note que, por medio de la inyección de dependencias se llama al método "delete(id)" que se encuentra en la persistencia.
        persistence.delete(estudiantesId);
        LOGGER.log(Level.INFO, "Termina proceso de borrar el estudiante con id = {0}", estudiantesId);
    }
}
