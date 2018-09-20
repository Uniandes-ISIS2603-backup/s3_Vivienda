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
import javax.persistence.Query;
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
        em.persist(contratoEntity);
        LOGGER.log(Level.INFO, "Contrato creado");
        return contratoEntity;
    }

    /**
     * Devuelve todos los contratos de la base de datos.
     *
     * @return una lista con todos los contratos que encuentre en la base de datos,
     * "select u from ContratoEntity u" es como un "select * from ContratoEntity;" -
     * "SELECT * FROM table_name" en SQL.
     */
    public List<ContratoEntity> findAll() {
        LOGGER.log(Level.INFO, "Consultando todos los contratos");
        Query q = em.createQuery("select u from ContratoEntity u");
        return q.getResultList();
    }

    /**
     * Busca si hay algun contrato con el id que se envía de argumento
     *
     * @param contratoId: id correspondiente al contrato buscado.
     * @return un contrato.
     */
    public ContratoEntity find(Long contratoId) {
        LOGGER.log(Level.INFO, "Consultando el contrato con id={0}", contratoId);
        return em.find(ContratoEntity.class, contratoId);
    }

    /**
     * Actualiza un contrato.
     *
     * @param contratoEntity: el contrato que viene con los nuevos cambios. Por ejemplo
     * el nombre pudo cambiar. En ese caso, se haria uso del método update.
     * @return un contrato con los cambios aplicados.
     */
    public ContratoEntity update(ContratoEntity contratoEntity) {
        LOGGER.log(Level.INFO, "Actualizando el contrato con id={0}", contratoEntity.getId());
        return em.merge(contratoEntity);
    }

    /**
     *
     * Borra un contrato de la base de datos recibiendo como argumento el id del contrato
     * @param contratoId: id correspondiente al contrato a borrar.
     */
    public void delete(Long contratoId) {
        LOGGER.log(Level.INFO, "Borrando el contrato con id={0}", contratoId);
        ContratoEntity bookEntity = em.find(ContratoEntity.class, contratoId);
        em.remove(bookEntity);
    }

    /**
     * Busca si hay algun contrato con el metodoPago que se envía de argumento
     *
     * @param metodoPago: Metodo de pago del contrato que se está buscando
     * @return null si no existe ningun contrato con el metodoPago del argumento. Si
     * existe alguno devuelve el primero.
     */
    public ContratoEntity findByMetodoPago(String metodoPago) {
        LOGGER.log(Level.INFO, "Consultando contratos por metodo de pago ", metodoPago);
        // Se crea un query para buscar contratos con el metodoPago que recibe el método como argumento. ":metodoPago" es un placeholder que debe ser reemplazado
        TypedQuery query = em.createQuery("Select e From ContratoEntity e where e.metodoPago = :metodoPago", ContratoEntity.class);
        // Se remplaza el placeholder ":metodoPago" con el valor del argumento 
        query = query.setParameter("metodoPago", metodoPago);
        // Se invoca el query que obtiene la lista resultado
        List<ContratoEntity> sameMetodoPago = query.getResultList();
        ContratoEntity result;
        if (sameMetodoPago == null) {
            result = null;
        } else if (sameMetodoPago.isEmpty()) {
            result = null;
        } else {
            result = sameMetodoPago.get(0);
        }
        LOGGER.log(Level.INFO, "Saliendo de consultar contratos por metodo de pago ", metodoPago);
        return result;
    }
    
    public ContratoEntity findById(Long id)
    {
                LOGGER.log(Level.INFO, "Consultando contratos por id", id);
        // Se crea un query para buscar contratos con el metodoPago que recibe el método como argumento. ":id" es un placeholder que debe ser reemplazado
        TypedQuery query = em.createQuery("Select e From ContratoEntity e where e.id = :id", ContratoEntity.class);
        // Se remplaza el placeholder ":id" con el valor del argumento 
        query = query.setParameter("id", id);
        // Se invoca el query que obtiene la lista resultado
        List<ContratoEntity> sameId = query.getResultList();
        ContratoEntity result;
        if (sameId == null) {
            result = null;
        } else if (sameId.isEmpty()) {
            result = null;
        } else {
            result = sameId.get(0);
        }
        LOGGER.log(Level.INFO, "Saliendo de consultar contratos por id", id);
        return result;
    }

    public ContratoEntity findByEstudiante(Long estudianteId) {
        LOGGER.log(Level.INFO, "Consultando contratos por id de estudiante", estudianteId);
        // Se crea un query para buscar contratos con el metodoPago que recibe el método como argumento. ":id" es un placeholder que debe ser reemplazado
        TypedQuery query = em.createQuery("Select e From ContratoEntity e where estudiante_id = :id", ContratoEntity.class);
        // Se remplaza el placeholder ":id" con el valor del argumento 
        query = query.setParameter("id", estudianteId);
        // Se invoca el query que obtiene la lista resultado
        List<ContratoEntity> sameId = query.getResultList();
        ContratoEntity result;
        if (sameId == null) {
            result = null;
        } else if (sameId.isEmpty()) {
            result = null;
        } else {
            result = sameId.get(0);
        }
        LOGGER.log(Level.INFO, "Saliendo de consultar contratos por id de estudiante", estudianteId);
        return result;
    }
}
