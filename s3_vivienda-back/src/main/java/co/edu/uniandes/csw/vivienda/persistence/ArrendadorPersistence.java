/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.vivienda.persistence;

import co.edu.uniandes.csw.vivienda.entities.ArrendadorEntity;
import java.util.List;
import java.util.logging.Level;
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
public class ArrendadorPersistence {
    private static final Logger LOGGER = Logger.getLogger(ArrendadorPersistence.class.getName());
    
    @PersistenceContext(unitName = "UniviviendaPU")
    protected EntityManager em;

    /**
     * Método para persisitir la entidad en la base de datos.
     *
     * @param arrendadorEntity objeto arrendador que se creará en la base de datos
     * @return devuelve la entidad creada con un id dado por la base de datos.
     */
    public ArrendadorEntity create(ArrendadorEntity arrendadorEntity) {
        LOGGER.log(Level.INFO, "Creando un arrendador nuevo");

        em.persist(arrendadorEntity);
        LOGGER.log(Level.INFO, "Saliendo de crear un arrendador nuevo");
        return arrendadorEntity;
    }
    
        public List<ArrendadorEntity> findAll() {
        LOGGER.log(Level.INFO, "Consultando todos los arrendadores");
        // Se crea un query para buscar todas las authores en la base de datos.
        TypedQuery query = em.createQuery("select u from ArrendadorEntity u", ArrendadorEntity.class);
        // Note que en el query se hace uso del método getResultList() que obtiene una lista de authores.
        return query.getResultList();
    }
        
            /**
     * Busca si hay alguna arrendador con el id que se envía de argumento
     *
     * @param arrendadorId: id correspondiente a la author buscada.
     * @return un arrendador.
     */
    public ArrendadorEntity find(Long arrendadorId) {
        LOGGER.log(Level.INFO, "Consultando el arrendador con id={0}", arrendadorId);
        return em.find(ArrendadorEntity.class, arrendadorId);
    }
    
    public ArrendadorEntity findByLogin(String arrendadorLogin) {
        
        TypedQuery query = em.createQuery("Select v from ArrendadorEntity v where v.login = :login", ArrendadorEntity.class);
        query = query.setParameter("login", arrendadorLogin);

        List<ArrendadorEntity> busc = query.getResultList();
        ArrendadorEntity result = null;
        if(busc != null && !busc.isEmpty()){
            result = busc.get(0);
        }
        return result;
    }
    
      /**
     * Actualiza un arrendador.
     *
     * @param arrendadorEntity
     * @return un arrendador con los cambios aplicados.
     */
    public ArrendadorEntity update(ArrendadorEntity arrendadorEntity) {
        LOGGER.log(Level.INFO, "Actualizando el arrendador con id={0}", arrendadorEntity.getId());
        return em.merge(arrendadorEntity);
    }
    
        /**
     * Borra un arrendador de la base de datos recibiendo como argumento el id del arrendador
     *
     * @param arrendadorId: id correspondiente a la arrendador a borrar.
     */
    public void delete(Long arrendadorId) {

        LOGGER.log(Level.INFO, "Borrando el arrendador con id={0}", arrendadorId);
        ArrendadorEntity arrendadorEntity = em.find(ArrendadorEntity.class, arrendadorId);
        em.remove(arrendadorEntity);
    }
}
