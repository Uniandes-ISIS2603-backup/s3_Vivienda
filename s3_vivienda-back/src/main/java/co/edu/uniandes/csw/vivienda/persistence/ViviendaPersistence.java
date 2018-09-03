/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.vivienda.persistence;

import co.edu.uniandes.csw.vivienda.entities.ViviendaEntity;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

/**
 * Clase que maneja la persistencia de la entidad vivienda
 * @author DANIEL
 */
@Stateless
public class ViviendaPersistence {
    
    @PersistenceContext(unitName = "UniviviendaPU")
    protected EntityManager em;
    
    public ViviendaEntity create(ViviendaEntity viviendaEntity){
        em.persist(viviendaEntity );
        return viviendaEntity;
    }
    
    public List<ViviendaEntity> findAll(){
        TypedQuery query = em.createQuery("select u from ViviendaEntity u", ViviendaEntity.class);
        return query.getResultList();
    }
    
    public ViviendaEntity find(Long id){
        return em.find(ViviendaEntity.class, id);
    }
    
    public ViviendaEntity update(ViviendaEntity viviendaEntity){
        return em.merge(viviendaEntity);
    }
    
    public void delete(Long id){
        ViviendaEntity viviendaEntity = em.find(ViviendaEntity.class, id);
        em.remove(viviendaEntity);
    }
}
