/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.vivienda.persistence;

import co.edu.uniandes.csw.vivienda.entities.EstudianteEntity;
import java.util.Collection;
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
public class EstudiantePersistence {
    private final static Logger LOGGER = Logger.getLogger(EstudiantePersistence.class.getName());
    
    @PersistenceContext(unitName = "UniviviendaPU")
    protected EntityManager em;
    
    public EstudianteEntity create(EstudianteEntity estudianteEntity){
        em.persist(estudianteEntity);
        return estudianteEntity;
    }
    public Collection<EstudianteEntity> findAll(){
        TypedQuery query = em.createQuery("select u from EstudianteEntity u", EstudianteEntity.class);
        return query.getResultList();
    }
    public EstudianteEntity find(Long estudianteId){
        return em.find(EstudianteEntity.class, estudianteId);
    }
    public EstudianteEntity update(EstudianteEntity estudianteEntity){
        return em.merge(estudianteEntity);
    }
    public void delete(Long estudianteId){
        EstudianteEntity estudianteEntity = em.find(EstudianteEntity.class, estudianteId);
        em.remove(estudianteEntity);
    }
    public Collection<EstudianteEntity> findByName(String nombre){
        TypedQuery query = em.createQuery("select u from EstudianteEntity u where u.nombre = :nombre", EstudianteEntity.class);
        query.setParameter("nombre", nombre);
        
        List <EstudianteEntity> resultList = query.getResultList();
        if (resultList == null || resultList.isEmpty())
            return null;
        return resultList;
    }
    public EstudianteEntity findByLogin(String login){
        TypedQuery query = em.createQuery("select u from EstudianteEntity u where u.login = :login", EstudianteEntity.class);
        query.setParameter("login", login);
        
        List <EstudianteEntity> resultList = query.getResultList();
        EstudianteEntity result;
        if (resultList == null || resultList.isEmpty())
            result = null;
        else
            result = resultList.get(0);
        return result;
    }
    
}
