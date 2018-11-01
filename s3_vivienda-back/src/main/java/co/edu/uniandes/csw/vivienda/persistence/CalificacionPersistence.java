/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.vivienda.persistence;

import co.edu.uniandes.csw.vivienda.entities.CalificacionEntity;
import java.util.List;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author Juan Manuel Castillo
 */
@Stateless
public class CalificacionPersistence {
    
    private final static Logger LOGGER = Logger.getLogger(CalificacionPersistence.class.getName());
    
    @PersistenceContext(unitName = "UniviviendaPU")
    protected EntityManager em;
    
    /**
     * Método para persisitir la entidad en la base de datos.
     *
     * @param entity objeto calificacion que se creará en la base de datos
     * @return devuelve la entidad creada con un id dado por la base de datos.
     */
    public CalificacionEntity create(CalificacionEntity entity){
        em.persist(entity);
        return entity;
    }
    
    /**
     * Devuelve todas las calificaciones de la base de datos.
     *
     * @return una lista con todas las calificaciones que encuentren en la base de
     * datos, "select u from CalificacionEntity u" es como un "select * from
     * CalificacionEntity;" - "SELECT * FROM table_name" en SQL.
     */
    public List<CalificacionEntity> findAll(){
        TypedQuery query = em.createQuery("select u from CalificacionEntity u", CalificacionEntity.class);
        return query.getResultList();
    }
    
    /**
     * Devuelve todas las calificaciones de una vivienda de la base de datos.
     *
     * @param viviendaId
     * @return una lista con todas las calificaciones de una vivienda que encuentren en la base de
     * datos, "select u from CalificacionEntity u" es como un "select * from
     * CalificacionEntity;" - "SELECT * FROM table_name" en SQL.
     */
    public List<CalificacionEntity> findAllByVivienda(Long viviendaId){
        TypedQuery query = em.createQuery("select u from CalificacionEntity u where (u.vivienda.id = :viviendaId)", CalificacionEntity.class);
        query.setParameter("viviendaId", viviendaId);
        return query.getResultList();
    }
    
    /**
     * Devuelve todas las calificaciones de un estudiante de la base de datos.
     *
     * @param estudianteId
     * @return una lista con todas las calificaciones de un estudiante que encuentren en la base de
     * datos, "select u from CalificacionEntity u" es como un "select * from
     * CalificacionEntity;" - "SELECT * FROM table_name" en SQL.
     */
    public List<CalificacionEntity> findAllByEstudiante(Long estudianteId){
        TypedQuery query = em.createQuery("select u from CalificacionEntity u where (u.estudiante.id = :estudianteId)", CalificacionEntity.class);
        query.setParameter("estudianteId", estudianteId);
        return query.getResultList();
    }
    
    /**
     * Busca si hay alguna calificacion con el id que se envía de argumento
     *
     * @param calificacionId: id correspondiente a la calificacion buscada.
     * @return una calificacion.
     */
    public CalificacionEntity find(Long calificacionId){
        return em.find(CalificacionEntity.class, calificacionId);
    }
    
    /**
     * Busca si hay alguna calificacion, de una vivienda, con el id que se envía de argumento
     *
     * @param viviendaId: id de la vivienda
     * @param calificacionId: id correspondiente a la calificacion buscada.
     * @return una calificacion.
     */
    public CalificacionEntity findByVivienda(Long viviendaId, Long calificacionId){
        TypedQuery query = em.createQuery("select u from CalificacionEntity u where (u.vivienda.id = :viviendaId) and (u.id = :calificacionId)", CalificacionEntity.class);
        query.setParameter("viviendaId", viviendaId);
        query.setParameter("calificacionId", calificacionId);
        
        List <CalificacionEntity> resultList = query.getResultList();
        CalificacionEntity result;
        if (resultList == null || resultList.isEmpty())
            result = null;
        else
            result = resultList.get(0);
        return result;
    }
    
    /**
     * Busca si hay alguna calificacion, de un estudiante, con el id que se envía de argumento
     *
     * @param estudianteId: id del estudinate
     * @param calificacionId: id correspondiente a la calificacion buscada.
     * @return una calificacion.
     */
    public CalificacionEntity findByEstudiante(Long estudianteId, Long calificacionId){
        TypedQuery query = em.createQuery("select u from CalificacionEntity u where (u.estudiante.id = :estudianteId) and (u.id = :calificacionId)", CalificacionEntity.class);
        query.setParameter("estudianteId", estudianteId);
        query.setParameter("calificacionId", calificacionId);
        
        List <CalificacionEntity> resultList = query.getResultList();
        CalificacionEntity result;
        if (resultList == null || resultList.isEmpty())
            result = null;
        else
            result = resultList.get(0);
        return result;
    }
    
    /**
     * Busca si hay alguna calificacion de un estudiante y una vivienda
     *
     * @param estudianteId: id del estudinate
     * @param viviendaId: id de la vivienda.
     * @return una calificacion.
     */
    public CalificacionEntity findByViviendaEstudiante(Long viviendaId, Long estudianteId) {
        TypedQuery query = em.createQuery("select u from CalificacionEntity u where (u.estudiante.id = :estudianteId) and (u.vivienda.id = :viviendaId)", CalificacionEntity.class);
        query.setParameter("estudianteId", estudianteId);
        query.setParameter("viviendaId", viviendaId);
        
        List <CalificacionEntity> resultList = query.getResultList();
        CalificacionEntity result;
        if (resultList == null || resultList.isEmpty())
            result = null;
        else
            result = resultList.get(0);
        return result;
    }
    
    /**
     * Actualiza una calificacion.
     *
     * @param entity: la calificacion que viene con los nuevos cambios.
     * Por ejemplo la descripción pudo cambiar. En ese caso, se haria uso del método
     * update.
     * @return una calificacion con los cambios aplicados.
     */
     public CalificacionEntity update(CalificacionEntity entity){
        return em.merge(entity);
    }
     
     /**
     *
     * Borra una calificacion de la base de datos recibiendo como argumento el id
     * de la calificacion
     *
     * @param calificacionId: id correspondiente a la calificacion a borrar.
     */
    public void delete(Long calificacionId){
        CalificacionEntity entity = em.find(CalificacionEntity.class, calificacionId);
        em.remove(entity);
        entity = em.find(CalificacionEntity.class, calificacionId);
    }
    
    public void deleteAll(){
        em.createQuery("Delete from CalificacionEntity").executeUpdate();
    }
}
