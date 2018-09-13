/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.vivienda.persistence;


import co.edu.uniandes.csw.vivienda.entities.UniversidadEntity;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author Paula Molina
 */
@Stateless
public class UniversidadPersistence 
{
    private final static Logger LOGGER = Logger.getLogger(UniversidadPersistence.class.getName());
    
    @PersistenceContext(unitName = "UniviviendaPU")
    protected EntityManager em;
    
    public UniversidadEntity create(UniversidadEntity universidadEntity) {
        LOGGER.log(Level.INFO, "Creando una universidad nueva");
        em.persist(universidadEntity);
        LOGGER.log(Level.INFO, "Saliendo de crear una universdiad nueva");
        return universidadEntity;
    }
    
    public UniversidadEntity update(UniversidadEntity universidadEntity) {
        LOGGER.log(Level.INFO, "Actualizando universidad con id = {0}", universidadEntity.getId());
        return em.merge(universidadEntity);
    }
    
    public List<UniversidadEntity> findAll() {
        LOGGER.log(Level.INFO, "Consultando todas las universidades");
        // Se crea un query para buscar todas las universidades en la base de datos.
        TypedQuery query = em.createQuery("select u from UniversidadEntity u", UniversidadEntity.class);
        return query.getResultList();
    }
    
    public UniversidadEntity find(Long universidadId){
        return em.find(UniversidadEntity.class, universidadId);
    }
    
    public void delete(Long universidadId) {
        LOGGER.log(Level.INFO, "Borrando editorial con id = {0}", universidadId);
        UniversidadEntity entity = em.find(UniversidadEntity.class, universidadId);
        em.remove(entity);
        LOGGER.log(Level.INFO, "Saliendo de borrar la editorial con id = {0}", universidadId);
    }   
    
    public UniversidadEntity findByName(String nombre) {
        LOGGER.log(Level.INFO, "Consultando universidad por nombre ", nombre);
        // Se crea un query para buscar universidades con el nombre que recibe el método como argumento. ":name" es un placeholder que debe ser remplazado
        TypedQuery query = em.createQuery("Select u From UniversidadEntity u where u.nombre = :nombre", UniversidadEntity.class);
        // Se remplaza el placeholder ":name" con el valor del argumento 
        query = query.setParameter("nombre", nombre);
        // Se invoca el query se obtiene la lista resultado
        List<UniversidadEntity> sameName = query.getResultList();
        UniversidadEntity result;
        if (sameName == null) {
            result = null;
        } else if (sameName.isEmpty()) {
            result = null;
        } else {
            result = sameName.get(0);
        }
        LOGGER.log(Level.INFO, "Saliendo de consultar universidad por nombre ", nombre);
        return result;
    }
}
