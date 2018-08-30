/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.vivienda.persistence;

import co.edu.uniandes.csw.vivienda.entities.ArrendadorEntity;
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
}
