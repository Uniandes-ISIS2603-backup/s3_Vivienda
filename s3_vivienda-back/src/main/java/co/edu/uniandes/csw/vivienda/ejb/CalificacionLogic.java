/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.vivienda.ejb;

import co.edu.uniandes.csw.vivienda.entities.CalificacionEntity;
import co.edu.uniandes.csw.vivienda.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.vivienda.persistence.CalificacionPersistence;
import co.edu.uniandes.csw.vivienda.persistence.EstudiantePersistence;
import co.edu.uniandes.csw.vivienda.persistence.ViviendaPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author calificacion
 */
@Stateless
public class CalificacionLogic {
    private static final Logger LOGGER = Logger.getLogger(CalificacionLogic.class.getName());

    @Inject
    private CalificacionPersistence persistence; // Variable para acceder a la persistencia de la aplicación. Es una inyección de dependencias.

    @Inject
    private EstudiantePersistence persistenceEstudiante;
    
    @Inject
    private ViviendaPersistence persistenceVivienda;
    
    /**
     * Crea un calificacion en la persistencia.
     *
     * @param calificacionEntity La entidad que representa el calificacion a
     * persistir.
     * @return La entidad del calificacion luego de persistirlo.
     * @throws BusinessLogicException Si el estudiante es invalido.
     * BusinessLogicException Si la vivienda es invalida.
     * BusinessLogicException Si el puntaje es menor a 0 o mayor a 5
     */
    public CalificacionEntity createCalificacion(CalificacionEntity calificacionEntity) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de creación del calificacion");
        // Verifica la regla de negocio que dice que no puede haber dos contratos con el mismo ID
        if (calificacionEntity.getEstudiante() == null || persistenceEstudiante.find(calificacionEntity.getEstudiante().getId()) == null) {
            throw new BusinessLogicException("El estudiante es invalido");
        }
        if (calificacionEntity.getVivienda() == null || persistenceVivienda.find(calificacionEntity.getVivienda().getId()) == null ){
            throw new BusinessLogicException("La vivienda es invalida");
        }
        if (calificacionEntity.getPuntaje() < 0 || calificacionEntity.getPuntaje() > 5) {
            throw new BusinessLogicException("El puntaje debe ser mayor o igual a 0, y menor o igual a 5. Puntaje= " + calificacionEntity.getPuntaje());
        }
        // Invoca la persistencia para crear el contrato
        persistence.create(calificacionEntity);
        LOGGER.log(Level.INFO, "Termina proceso de creación del calificacion");
        return calificacionEntity;
    }
    
    /**
     *
     * Obtener todas las calificaciones existentes en la base de datos.
     *
     * @return una lista de calificaciones.
     */
    public List<CalificacionEntity> getCalificaciones(){
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todas las calificaciones");
        List<CalificacionEntity> calificaciones = persistence.findAll();
        LOGGER.log(Level.INFO, "Termina proceso de consultar todas las calificaciones");
        return calificaciones;
    }
    
    /**
     *
     * Obtener todas las calificaciones de un estudiante en la base de datos.
     *
     * @param estudianteId: id del estudiante
     * @return una lista de calificaciones.
     */
    public List<CalificacionEntity> getCalificacionesEstudiante(Long estudianteId){
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todas las calificaciones");
        List<CalificacionEntity> calificaciones = persistence.findAllByEstudiante(estudianteId);
        LOGGER.log(Level.INFO, "Termina proceso de consultar todas las calificaciones");
        return calificaciones;
    }
    
    /**
     *
     * Obtener todas las calificaciones de una vivienda en la base de datos.
     *
     * @param viviendaId: id de la calificacion para ser buscada.
     * @return una lista de calificaciones.
     */
    public List<CalificacionEntity> getCalificacionesVivienda(Long viviendaId){
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todas las calificaciones");
        List<CalificacionEntity> calificaciones = persistence.findAllByVivienda(viviendaId);
        LOGGER.log(Level.INFO, "Termina proceso de consultar todas las calificaciones");
        return calificaciones;
    }
    
    /**
     *
     * Obtener una calificacion por medio de su id.
     *
     * @param id: id de la calificacion para ser buscada.
     * @return la calificacion solicitada por medio de su id.
     */
    public CalificacionEntity getCalificacion(Long id){
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todos los calificacions");
        CalificacionEntity calificacion = persistence.find(id);
        if (calificacion == null) {
            LOGGER.log(Level.SEVERE, "El calificacions con el id = {0} no existe", id);
        }
        LOGGER.log(Level.INFO, "Termina proceso de consultar todos los calificacions");
        return calificacion;
    }
    
    /**
     *
     * Obtener una calificacion, de un estudiante,  por medio de su id.
     *
     * @param estudianteId: id del estudiante
     * @param calificacionId: id de la calificacion para ser buscada.
     * @return la calificacion solicitada por medio de su id.
     * @throws co.edu.uniandes.csw.vivienda.exceptions.BusinessLogicException
     */
    public CalificacionEntity getCalificacionEstudiante(Long estudianteId, Long calificacionId) throws BusinessLogicException{
        LOGGER.log(Level.INFO, "Inicia proceso de consultar las calificaciones del estudiante");
        CalificacionEntity calificacion = persistence.findByEstudiante(estudianteId, calificacionId);
        if (calificacion == null) {
            throw new BusinessLogicException("La calificación no está asociada con el estudiante");
        }
        LOGGER.log(Level.INFO, "Termina proceso de consultar las calificaciones del estudinate");
        return calificacion;
    }
    
    /**
     *
     * Obtener una calificacion, de una vivienda, por medio de su id.
     *
     * @param viviendaId
     * @param calificacionId: id de la calificacion para ser buscada.
     * @return la calificacion solicitada por medio de su id.
     * @throws co.edu.uniandes.csw.vivienda.exceptions.BusinessLogicException 
     * si a calificación no está asociada con la vivienda.
     */
    public CalificacionEntity getCalificacionVivienda(Long viviendaId, Long calificacionId) throws BusinessLogicException{
        LOGGER.log(Level.INFO, "Inicia proceso de consultar las calificaciones del estudiante");
        CalificacionEntity calificacion = persistence.findByVivienda(viviendaId, calificacionId);
        if (calificacion == null) {
            throw new BusinessLogicException("La calificación no está asociada con la vivienda");
        }
        LOGGER.log(Level.INFO, "Termina proceso de consultar las calificaciones de la vivienda");
        return calificacion;
    }
    
    /**
     *
     * Actualizar una calificacion.
     *
     * @param calificacionId: id de la calificacion para buscarla en la base de datos.
     * @param calificacionEntity: calificacion con los cambios para ser actualizada,
     * por ejemplo el nombre.
     * @return la calificacion con los cambios actualizados en la base de datos.
     * @throws co.edu.uniandes.csw.vivienda.exceptions.BusinessLogicException si 
     * no se puede actualizar la calificacion
     */
    public CalificacionEntity updateCalificacion(Long calificacionId, CalificacionEntity calificacionEntity) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar el calificacion con id = {0}", calificacionId);
        if (calificacionEntity.getEstudiante() != null && persistenceEstudiante.find(calificacionEntity.getEstudiante().getId()) == null) {
            throw new BusinessLogicException("El estudiante es invalido");
        }
        if (calificacionEntity.getVivienda() != null && persistenceVivienda.find(calificacionEntity.getVivienda().getId()) == null ){
            throw new BusinessLogicException("La vivienda es invalida");
        }
        if (calificacionEntity.getPuntaje() < 0 || calificacionEntity.getPuntaje() > 5) {
            throw new BusinessLogicException("El puntaje debe ser mayor o igual a 0 y menor o igual a 5. Puntaje= " + calificacionEntity.getPuntaje());
        }
        calificacionEntity.setId(calificacionId);
        CalificacionEntity newEntity = persistence.update(calificacionEntity);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar el calificacion con id = {0}", calificacionEntity.getId());
        return newEntity;
    }
    
    /**
     *
     * Actualizar una calificacion de un estudiante .
     *
     * @param estudianteId: id del estudiante
     * @param calificacionId: id de la calificacion para buscarla en la base de
     * datos.
     * @param calificacionEntity: calificacion con los cambios para ser actualizada,
     * por ejemplo el nombre.
     * @return la calificacion con los cambios actualizados en la base de datos.
     * @throws co.edu.uniandes.csw.vivienda.exceptions.BusinessLogicException
     */
    public CalificacionEntity updateCalificacionEstudiante(Long estudianteId, Long calificacionId, CalificacionEntity calificacionEntity)throws BusinessLogicException{
        return updateCalificacion(calificacionId, calificacionEntity);
    }
    
    /**
     *
     * Actualizar una calificacion de una vivienda.
     *
     * @param viviendaId: id de la vivienda
     * @param calificacionId: id de la calificacion para buscarla en la base de
     * datos.
     * @param calificacionEntity: calificacion con los cambios para ser actualizada,
     * por ejemplo el nombre.
     * @return la calificacion con los cambios actualizados en la base de datos.
     * @throws co.edu.uniandes.csw.vivienda.exceptions.BusinessLogicException
     */
    public CalificacionEntity updateCalificacionVivienda(Long viviendaId, Long calificacionId, CalificacionEntity calificacionEntity)throws BusinessLogicException{
        return updateCalificacion(calificacionId, calificacionEntity);
    }

    /**
     * Borrar un calificacion
     *
     * @param calificacionId: id del calificacion a borrar
     */
    public void deleteCalificacion(Long calificacionId) {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar el calificacion con id = {0}", calificacionId);
        // Note que, por medio de la inyección de dependencias se llama al método "delete(id)" que se encuentra en la persistencia.
        persistence.delete(calificacionId);
        LOGGER.log(Level.INFO, "Termina proceso de borrar el calificacion con id = {0}", calificacionId);
    }
    
    /**
     * Borrar un calificacion de un estudiante
     *
     * @param estudianteId: id del estudiante
     * @param calificacionId: id del calificacion a borrar
     * @throws co.edu.uniandes.csw.vivienda.exceptions.BusinessLogicException
     */
    public void deleteCalificacionEstudiante(Long estudianteId, Long calificacionId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar el calificacion con id = {0}", calificacionId);
        getCalificacionEstudiante(estudianteId, calificacionId);
        persistence.delete(calificacionId);
        LOGGER.log(Level.INFO, "Termina proceso de borrar el calificacion con id = {0}", calificacionId);
    }
    
    /**
     * Borrar una calificacion de una vivienda
     *
     * @param viviendaId: id de la vivienda
     * @param calificacionId: id del calificacion a borrar
     * @throws co.edu.uniandes.csw.vivienda.exceptions.BusinessLogicException
     */
    public void deleteCalificacionVivienda(Long viviendaId, Long calificacionId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar el calificacion con id = {0}", calificacionId);
        getCalificacionVivienda(viviendaId, calificacionId);
        persistence.delete(calificacionId);
        LOGGER.log(Level.INFO, "Termina proceso de borrar el calificacion con id = {0}", calificacionId);
    }
}
