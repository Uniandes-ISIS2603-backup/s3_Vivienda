/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.vivienda.persistence;

import co.edu.uniandes.csw.vivienda.entities.ServicioAdicionalEntity;
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
public class ServicioAdicionalPersistence 
{
    private static final Logger LOGGER = Logger.getLogger(ServicioAdicionalPersistence.class.getName());
    
    @PersistenceContext(unitName = "UniviviendaPU")
    protected EntityManager em;
    
    /**
     * Crear un servicio adicional
     *
     * Crea un nuevo servicio adicional con la información recibida en la entidad.
     *
     * @param servicioAdicionalEntity La entidad que representa el nuevo servicio adicional
     * @return La entidad creada
     */
    public ServicioAdicionalEntity create(ServicioAdicionalEntity servicioAdicionalEntity) {
        LOGGER.log(Level.INFO, "Creando un ServicioAdicional nuevo");
        em.persist(servicioAdicionalEntity);
        LOGGER.log(Level.INFO, "ServicioAdicional creado");
        return servicioAdicionalEntity;
    }

    /**
     * Actualizar una servicio adicional
     *
     * Actualiza la entidad que recibe en la base de datos
     *
     * @param servicioAdicionalEntity La entidad actualizada que se desea guardar
     * @return La entidad resultante luego de la acutalización
     */
    public ServicioAdicionalEntity update(ServicioAdicionalEntity servicioAdicionalEntity) {
        LOGGER.log(Level.INFO, "Actualizando el servicio adicional con id = {0}", servicioAdicionalEntity.getId());
        return em.merge(servicioAdicionalEntity);
    }

    /**
     * Eliminar un servicio adicional
     *
     * Elimina el servico adicional asociado al ID que recibe
     *
     * @param servicioAdicionalId El ID del servicio adicional que se desea borrar
     */
    public void delete(Long servicioAdicionalId) {
        LOGGER.log(Level.INFO, "Borrando el servicio adicional con id = {0}", servicioAdicionalId);
        ServicioAdicionalEntity servicioAdicionalEntity = em.find(ServicioAdicionalEntity.class, servicioAdicionalId);
        em.remove(servicioAdicionalEntity);
        LOGGER.log(Level.INFO, "Saliendo de borrar el servicio adicional con id = {0}", servicioAdicionalId);
    }

    /**
     * Buscar un servicio adicional
     *
     * Busca si hay algun servicio adicional asociado a una vivienda y con un ID específico
     *
     * @param viviendaId El ID de la vivienda con respecto al cual se busca
     * @param servicioAdicionalId El ID del servicio adicional buscado
     * @return El servicio adicional encontrado o null. Nota: Si existe uno o mas servicios adicionales
     * devuelve siempre el primero que encuentra
     */
    public ServicioAdicionalEntity find(Long viviendaId, Long servicioAdicionalId) {
        LOGGER.log(Level.INFO, "Consultando el servicio adicional con id = {1} de la vivienda con id = {0} " , new Object[]{ viviendaId, servicioAdicionalId});
        TypedQuery<ServicioAdicionalEntity> q = em.createQuery("select p from ServicioAdicionalEntity p where (p.vivienda.id = :viviendaId) and (p.id = :servicioAdicionalId)", ServicioAdicionalEntity.class);
        q.setParameter("viviendaId", viviendaId);
        q.setParameter("servicioAdicionalId", servicioAdicionalId);
        List<ServicioAdicionalEntity> results = q.getResultList();
        ServicioAdicionalEntity servicioAdicional = null;
        if (results != null && !results.isEmpty()) {
            servicioAdicional = results.get(0);
        }
        LOGGER.log(Level.INFO, "Saliendo de consultar el servicio adiconal con id = {1} de la vivienda con id = {0}" , new Object[]{ viviendaId, servicioAdicionalId});
        return servicioAdicional;
    }
}
