/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.vivienda.ejb;

import co.edu.uniandes.csw.vivienda.entities.CalificacionEntity;
import co.edu.uniandes.csw.vivienda.entities.EstudianteEntity;
import co.edu.uniandes.csw.vivienda.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.vivienda.persistence.EstudiantePersistence;
import co.edu.uniandes.csw.vivienda.persistence.UniversidadPersistence;
import co.edu.uniandes.csw.vivienda.persistence.ContratoPersistence;
import java.util.List;
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
    
    @Inject
    private ContratoPersistence persistenceContrato;
    /**
     * Crea un estudiante en la persistencia.
     *
     * @param estudianteEntity La entidad que representa el estudiante a
     * persistir.
     * @return La entidad del estudiante luego de persistirlo.
     * @throws BusinessLogicException Si el login del estudiante a persistir ya existe.
     * Si el estudinate no tiene universidad.
     * Si la universidad del estudiante a persistir no existe.
     */
    public EstudianteEntity createEstudiante(EstudianteEntity estudianteEntity) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de creación del estudiante");
        
        if (persistence.findByLogin(estudianteEntity.getLogin()) != null) {
            throw new BusinessLogicException("Ya existe un estudiante con el login \"" + estudianteEntity.getLogin() + "\"");
        }
        if (estudianteEntity.getUniversidad() == null) {
            throw new BusinessLogicException("El estudinate debe tener universidad");
        }
        else if (persistenceUniversidad.find(estudianteEntity.getUniversidad().getId()) == null){
            throw new BusinessLogicException("No existe la universidad con el id \"" +estudianteEntity.getUniversidad().getId() + "\"");
        }
        // Invoca la persistencia para crear el estudiante
        persistence.create(estudianteEntity);
        LOGGER.log(Level.INFO, "Termina proceso de creación del estudiante");
        return estudianteEntity;
    }
    
     public List<EstudianteEntity> getEstudiantes(){
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todos los estudinates");
        List<EstudianteEntity> estudiantes = persistence.findAll();
        LOGGER.log(Level.INFO, "Termina proceso de consultar todos los estudiantes");
        return estudiantes;
    }
    
    public EstudianteEntity getEstudiante(Long id){
        LOGGER.log(Level.INFO, "Inicia proceso de consultar el estudiante");
        EstudianteEntity estudiante = persistence.find(id);
        if (estudiante == null) {
            LOGGER.log(Level.SEVERE, "El estudiantes con el id = {0} no existe", id);
        }
        LOGGER.log(Level.INFO, "Termina proceso de consultar el estudiante");
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
        if (entity.getContrato() != null && (persistenceContrato.find(entity.getContrato().getId()) == null)){
            throw new BusinessLogicException("No existe el contrato con el id \"" +entity.getContrato().getId() + "\"");
        }
        if (entity.getContrato() != null && !Objects.equals(entity.getContrato().getEstudiante().getId(), id)){
            throw new BusinessLogicException("El contrato debe tener el mismo estudiante");
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
