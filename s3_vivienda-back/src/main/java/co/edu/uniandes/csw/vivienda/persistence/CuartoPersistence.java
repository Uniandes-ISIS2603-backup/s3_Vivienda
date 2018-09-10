package co.edu.uniandes.csw.vivienda.persistence;

import co.edu.uniandes.csw.vivienda.entities.CuartoEntity;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class CuartoPersistence {

    @PersistenceContext(unitName = "UniviviendaPU")
    protected EntityManager em;

    public CuartoEntity create(CuartoEntity cuartoEntity){
        em.persist(cuartoEntity);
        return cuartoEntity;
    }

    public CuartoEntity find(Long cuartoId){
        return em.find(CuartoEntity.class, cuartoId);
    }

    public CuartoEntity update(CuartoEntity cuartoEntity){
        return em.merge(cuartoEntity);
    }

    public boolean delete(Long cuartoId){
        CuartoEntity cuartoEntity = em.find(CuartoEntity.class, cuartoId);
        if(cuartoEntity != null){
            em.remove(cuartoEntity);
            return true;
        }
        return false;
    }

}
