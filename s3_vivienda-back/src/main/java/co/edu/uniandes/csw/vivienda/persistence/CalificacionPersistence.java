/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.vivienda.persistence;

import co.edu.uniandes.csw.vivienda.entities.CalificacionEntity;
import java.util.List;
import java.util.List;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author estudiante
 */
@Stateless
public class CalificacionPersistence {
    
    private final static Logger LOGGER = Logger.getLogger(CalificacionPersistence.class.getName());
    
    @PersistenceContext(unitName = "UniviviendaPU")
    protected EntityManager em;
    
    public CalificacionEntity create(CalificacionEntity entity){
        em.persist(entity);
        return entity;
    }
    public List<CalificacionEntity> findAll(){
        TypedQuery query = em.createQuery("select u from CalificacionEntity u", CalificacionEntity.class);
        return query.getResultList();
    }
    public CalificacionEntity find(Long calificacionId){
        return em.find(CalificacionEntity.class, calificacionId);
    }
    public CalificacionEntity update(CalificacionEntity entity){
        return em.merge(entity);
    }
    public void delete(Long calificacionId){
        CalificacionEntity entity = em.find(CalificacionEntity.class, calificacionId);
        em.remove(entity);
    }
    public CalificacionEntity findByVivienda(Long viviendaId, Long calificacionId){
        TypedQuery query = em.createQuery("select u from CalificacionEntity u where (u.vivienda.id = :viviendaId) and (u.id = :calificacionId", CalificacionEntity.class);
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
    public CalificacionEntity findByEstudiante(Long estudianteId, Long calificacionId){
        TypedQuery query = em.createQuery("select u from CalificacionEntity u where (u.estudiante.id = :estudianteId) and (u.id = :calificacionId", CalificacionEntity.class);
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
}
