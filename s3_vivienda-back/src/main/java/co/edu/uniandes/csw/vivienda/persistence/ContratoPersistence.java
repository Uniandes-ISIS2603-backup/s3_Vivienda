/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.vivienda.persistence;

import co.edu.uniandes.csw.vivienda.entities.ContratoEntity;
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
public class ContratoPersistence {
    
    
    private static final Logger LOGGER = Logger.getLogger(ContratoPersistence.class.getName());

    @PersistenceContext(unitName = "UniviviendaPU")
    protected EntityManager em;

    /**
     * Método para persisitir la entidad en la base de datos.
     *
     * @param contratoEntity objeto contrato que se creará en la base de datos
     * @return devuelve la entidad creada con un id dado por la base de datos.
     */
    public ContratoEntity create(ContratoEntity contratoEntity) {
        LOGGER.log(Level.INFO, "Creando un contrato nuevo");
        /* Note que hacemos uso de un método propio de EntityManager para persistir el contrato en la base de datos.
        Es similar a "INSERT INTO table_name (column1, column2, column3, ...) VALUES (value1, value2, value3, ...);" en SQL.
         */
        em.persist(contratoEntity);
        LOGGER.log(Level.INFO, "Saliendo de crear un contrato nuevo");
        return contratoEntity;
    }

    /**
     *
     * Borra un contrato de la base de datos recibiendo como argumento el id
     * del contrato
     *
     * @param contratosId: id correspondiente a la editorial a borrar.
     */
    public void delete(Long contratosId) {
        LOGGER.log(Level.INFO, "Borrando editorial con id = {0}", contratosId);
        // Se hace uso de mismo método que esta explicado en public ContratoEntity find(Long id) para obtener el contrato a borrar.
        ContratoEntity entity = em.find(ContratoEntity.class, contratosId);
        /* Note que una vez obtenido el objeto desde la base de datos llamado "entity", volvemos hacer uso de un método propio del
         EntityManager para eliminar de la base de datos el objeto que encontramos y queremos borrar.
         Es similar a "delete from ContratoEntity where id=id;" - "DELETE FROM table_name WHERE condition;" en SQL.*/
        em.remove(entity);
        LOGGER.log(Level.INFO, "Saliendo de borrar el contrato con id = {0}", contratosId);
    }

    /**
     * Busca si hay algun contrato con el ID que se envía de argumento
     *
     * @param id: Identificador del contrato que se está buscando
     * @return null si no existe ninguna contrato con el id del argumento.
     * Si existe alguno devuelve el primero.
     */
    public ContratoEntity findById(Long id) {
        LOGGER.log(Level.INFO, "Consultando contrato por id ", id);
        // Se crea un query para buscar contratos con el id que recibe el método como argumento. ":id" es un placeholder que debe ser remplazado
        TypedQuery query = em.createQuery("Select e From ContratoEntity e where e.id = :id", ContratoEntity.class);
        // Se remplaza el placeholder ":name" con el valor del argumento 
        query = query.setParameter("id", id);
        // Se invoca el query se obtiene la lista resultado
        List<ContratoEntity> sameId = query.getResultList();
        ContratoEntity result;
        if (sameId == null) {
            result = null;
        } else if (sameId.isEmpty()) {
            result = null;
        } else {
            result = sameId.get(0);
        }
        LOGGER.log(Level.INFO, "Saliendo de consultar contrato por id ", id);
        return result;
    }
}
