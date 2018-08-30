/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.vivienda.persistence;

import co.edu.uniandes.csw.vivienda.entities.SitioInteresEntity;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author estudiante
 */
@Stateless
public class SitioInteresPersistence {
        private static final Logger LOGGER = Logger.getLogger(SitioInteresPersistence.class.getName());
    
       @PersistenceContext(unitName = "UniviviendaPU")
    protected EntityManager em;

    /**
     * Método para persisitir la entidad en la base de datos.
     *
     * @param sitioInteresEntity objeto sitioInteres que se creará en la base de datos
     * @return devuelve la entidad creada con un id dado por la base de datos.
     */
    public SitioInteresEntity create(SitioInteresEntity sitioInteresEntity) {
        LOGGER.log(Level.INFO, "Creando un sitioInteres nuevo");

        em.persist(sitioInteresEntity);
        LOGGER.log(Level.INFO, "Saliendo de crear un sitioInteres nuevo");
        return sitioInteresEntity;
    }
}
