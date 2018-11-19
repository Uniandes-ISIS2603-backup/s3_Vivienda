/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.vivienda.ejb;

import co.edu.uniandes.csw.vivienda.entities.CalificacionEntity;
import co.edu.uniandes.csw.vivienda.entities.ViviendaEntity;
import co.edu.uniandes.csw.vivienda.entities.EstudianteEntity;
import co.edu.uniandes.csw.vivienda.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.vivienda.persistence.CalificacionPersistence;
import co.edu.uniandes.csw.vivienda.persistence.EstudiantePersistence;
import co.edu.uniandes.csw.vivienda.persistence.ViviendaPersistence;
import java.util.List;
import java.util.Random;
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
    
    private static final String INICIA_PROCESO_CONSULTA = "Inicia proceso de consultar todas las calificaciones";
    private static final String TERMINA_PROCESO_CONSULTA = "Termina proceso de consultar todas las calificaciones";
    
    public void generarDatos(){
        Random rand = new Random();
        
        String [] descripciones = new String[]{"Muy buena experiencia", "Excelente!", "Recomendada", "Muy recomendada",
            "Me siento afortunado de haber vivido ahí", "El arrendador es muy amable"};
        Float [] puntajes = new Float[11];
        for (int i = 0; i < 11; i ++)
            puntajes[i] = new Float(0.5*i) ;
        
        List <EstudianteEntity>  estudinates = persistenceEstudiante.findAll();
        List <ViviendaEntity>  viviendas = persistenceVivienda.findAll();
        for (EstudianteEntity estudiante: estudinates){
            int calificacionesDeEstudiante = rand.nextInt((viviendas.size() >= 3)? viviendas.size()/3+1:viviendas.size()+1);
            int indiceVivienda = rand.nextInt(viviendas.size());
            
            for (int i = 0; i < calificacionesDeEstudiante; i ++){
                CalificacionEntity calificacion = new CalificacionEntity();
                calificacion.setDescripcion(descripciones[rand.nextInt(descripciones.length)]);
                calificacion.setPuntaje(puntajes[rand.nextInt(puntajes.length)]);
                calificacion.setEstudiante(estudiante);
                int ind = Math.floorMod(indiceVivienda, viviendas.size());
                calificacion.setVivienda(viviendas.get(ind));
                persistence.create(calificacion);
                indiceVivienda++;
            }
        }
    }
    
    /**
     * Crea un calificacion en la persistencia.
     *
     * @param viviendaId: id de la vivienda a la cual se va a agregar
     * @param calificacionEntity La entidad que representa el calificacion a
     * persistir.
     * @return La entidad del calificacion luego de persistirlo.
     * @throws BusinessLogicException Si el estudiante es invalido.
     * BusinessLogicException Si la vivienda es invalida.
     * BusinessLogicException Si el puntaje es menor a 0 o mayor a 5
     */
    public CalificacionEntity createCalificacion(Long viviendaId, CalificacionEntity calificacionEntity) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de creación del calificacion");
        // Verifica la regla de negocio que dice que no puede haber dos contratos con el mismo ID
        if (calificacionEntity.getEstudiante() == null || calificacionEntity.getEstudiante().getId() == null || persistenceEstudiante.find(calificacionEntity.getEstudiante().getId()) == null) {
            throw new BusinessLogicException("El estudiante es invalido");
        }
        ViviendaEntity vivienda = persistenceVivienda.find(viviendaId);
        if (vivienda == null){
            throw new BusinessLogicException("La vivienda es invalida");
        }
        if (calificacionEntity.getPuntaje() < 0 || calificacionEntity.getPuntaje() > 5) {
            throw new BusinessLogicException("El puntaje debe ser mayor o igual a 0, y menor o igual a 5. Puntaje= " + calificacionEntity.getPuntaje());
        }
        if (getCalificacionViviendaEstudiante(viviendaId, calificacionEntity.getEstudiante().getId()) != null)
            throw new BusinessLogicException("La vivienda y el estudiante ya tienen una calificacion");
        
        calificacionEntity.setVivienda(vivienda);
        calificacionEntity = persistence.create(calificacionEntity);
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
        LOGGER.log(Level.INFO, INICIA_PROCESO_CONSULTA);
        List<CalificacionEntity> calificaciones = persistence.findAll();
        LOGGER.log(Level.INFO, TERMINA_PROCESO_CONSULTA);
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
        LOGGER.log(Level.INFO, INICIA_PROCESO_CONSULTA);
        List<CalificacionEntity> calificaciones = persistence.findAllByEstudiante(estudianteId);
        LOGGER.log(Level.INFO, TERMINA_PROCESO_CONSULTA);
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
        LOGGER.log(Level.INFO, INICIA_PROCESO_CONSULTA);
        List<CalificacionEntity> calificaciones = persistence.findAllByVivienda(viviendaId);
        LOGGER.log(Level.INFO, TERMINA_PROCESO_CONSULTA);
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
        LOGGER.log(Level.INFO, INICIA_PROCESO_CONSULTA);
        CalificacionEntity calificacion = persistence.find(id);
        if (calificacion == null) {
            LOGGER.log(Level.SEVERE, "El calificacions con el id = {0} no existe", id);
        }
        LOGGER.log(Level.INFO, TERMINA_PROCESO_CONSULTA);
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
        LOGGER.log(Level.INFO, "Inicia proceso de consultar la calificacion de la vivienda");
        CalificacionEntity calificacion = persistence.findByVivienda(viviendaId, calificacionId);
        if (calificacion == null) {
            throw new BusinessLogicException("La calificación no está asociada con la vivienda");
        }
        LOGGER.log(Level.INFO, "Termina proceso de consultar las calificaciones de la vivienda");
        return calificacion;
    }
    
    /**
     *
     * Obtener una calificacion, de una vivienda y un estudiante
     *
     * @param viviendaId: id de la vivienda para ser buscada.
     * @param estudianteId: id del estudiante para ser buscada.
     * @return la calificacion solicitada por medio de su id.
     * @throws co.edu.uniandes.csw.vivienda.exceptions.BusinessLogicException 
     * si no hay calificación asociada con la vivienda y el estudiante.
     */
    public CalificacionEntity getCalificacionViviendaEstudiante(Long viviendaId, Long estudianteId) throws BusinessLogicException{
        LOGGER.log(Level.INFO, "Inicia proceso de consultar las calificacion de la vivienda y el estudiante");
        CalificacionEntity calificacion = persistence.findByViviendaEstudiante(viviendaId, estudianteId);
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
        if (calificacionEntity.getPuntaje() < 0 || calificacionEntity.getPuntaje() > 5) {
            throw new BusinessLogicException("El puntaje debe ser mayor o igual a 0 y menor o igual a 5. Puntaje= " + calificacionEntity.getPuntaje());
        }
        CalificacionEntity old = persistence.find(calificacionId);
        if (calificacionEntity.getDescripcion() != null)
            old.setDescripcion(calificacionEntity.getDescripcion());
        if (calificacionEntity.getPuntaje()!= null)
            old.setPuntaje(calificacionEntity.getPuntaje());
        
        CalificacionEntity newEntity = persistence.update(old);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar el calificacion con id = {0}", calificacionEntity.getId());
        return newEntity;
    }
    
    /**
     *
     * Actualizar una calificacion de un estudiante .
     *
     * @param calificacionId: id de la calificacion para buscarla en la base de
     * datos.
     * @param calificacionEntity: calificacion con los cambios para ser actualizada,
     * por ejemplo el nombre.
     * @return la calificacion con los cambios actualizados en la base de datos.
     * @throws co.edu.uniandes.csw.vivienda.exceptions.BusinessLogicException
     */
    public CalificacionEntity updateCalificacionEstudiante( Long calificacionId, CalificacionEntity calificacionEntity)throws BusinessLogicException{
        return updateCalificacion(calificacionId, calificacionEntity);
    }
    
    /**
     *
     * Actualizar una calificacion de una vivienda.
     *
     * @param calificacionId: id de la calificacion para buscarla en la base de
     * datos.
     * @param calificacionEntity: calificacion con los cambios para ser actualizada,
     * por ejemplo el nombre.
     * @return la calificacion con los cambios actualizados en la base de datos.
     * @throws co.edu.uniandes.csw.vivienda.exceptions.BusinessLogicException
     */
    public CalificacionEntity updateCalificacionVivienda(Long calificacionId, CalificacionEntity calificacionEntity)throws BusinessLogicException{
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
     * si no existe calificacion del estudiante
     */
    public void deleteCalificacionEstudiante(Long estudianteId, Long calificacionId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar el calificacion, de estudiante, con id = {0}", calificacionId);
        getCalificacionEstudiante(estudianteId, calificacionId);
        persistence.delete(calificacionId);
        LOGGER.log(Level.INFO, "Termina proceso de borrar el calificacion, de estudiante, con id = {0}", calificacionId);
    }
    
    /**
     * Borrar una calificacion de una vivienda
     *
     * @param viviendaId: id de la vivienda
     * @param calificacionId: id del calificacion a borrar
     * @throws co.edu.uniandes.csw.vivienda.exceptions.BusinessLogicException
     * si no existe calificacion de la vivienda
     */
    public void deleteCalificacionVivienda(Long viviendaId, Long calificacionId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar el calificacion con id = {0}", calificacionId);
        getCalificacionVivienda(viviendaId, calificacionId);
        persistence.delete(calificacionId);
        LOGGER.log(Level.INFO, "Termina proceso de borrar el calificacion con id = {0}", calificacionId);
    }
}
