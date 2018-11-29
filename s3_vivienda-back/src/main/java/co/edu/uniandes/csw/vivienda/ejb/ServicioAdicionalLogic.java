/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.vivienda.ejb;


import co.edu.uniandes.csw.vivienda.entities.ServicioAdicionalEntity;
import co.edu.uniandes.csw.vivienda.entities.ViviendaEntity;
import co.edu.uniandes.csw.vivienda.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.vivienda.persistence.ServicioAdicionalPersistence;
import co.edu.uniandes.csw.vivienda.persistence.ViviendaPersistence;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Paula Molina
 */
@Stateless
public class ServicioAdicionalLogic {
    
    private static final Logger LOGGER = Logger.getLogger(ServicioAdicionalLogic.class.getName());

    @Inject
    private ServicioAdicionalPersistence persistence;

    @Inject
    private ViviendaPersistence viviendaPersistence;

    /**
     * Se encarga de crear un ServicioAdicional en la base de datos.
     *
     * @param servicioAdicionalEntity Objeto de ServicioAdicionalEntity con los datos nuevos
     * @param viviendaId id de la Vivienda el cual sera padre del nuevo ServicioAdicional.
     * @return Objeto de ServicioAdicionalEntity con los datos nuevos y su ID.
     * @throws BusinessLogicException si viviendaId no es el mismo que tiene el entity.
     *
     */
    public ServicioAdicionalEntity createServicioAdicional(Long viviendaId, ServicioAdicionalEntity servicioAdicionalEntity) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de crear servicio adicional");
        ViviendaEntity vivienda = viviendaPersistence.find(viviendaId);
        servicioAdicionalEntity.setVivienda(vivienda);
        LOGGER.log(Level.INFO, "Termina proceso de creación del servicio adicional");
        return persistence.create(servicioAdicionalEntity);
    }

    /**
     * Obtiene la lista de los registros de ServicioAdicional que pertenecen a una Vivienda.
     *
     * @param viviendaId id de la vivienda la cual es padre de los ServicioAdicional.
     * @return Colección de objetos de ServicioAdicionalEntity.
     */
    public List<ServicioAdicionalEntity> getServiciosAdicionales(Long viviendaId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar los servicios adicionales asociados al book con id = {0}", viviendaId);
        ViviendaEntity viviendaEntity = viviendaPersistence.find(viviendaId);
        LOGGER.log(Level.INFO, "Termina proceso de consultar los reviews asociados al book con id = {0}", viviendaId);
        return viviendaEntity.getServiciosAdicionales();
    }

    /**
     * Obtiene los datos de una instancia de ServicioAdicional a partir de su ID. La
     * existencia del elemento padre Vivienda se debe garantizar.
     *
     * @param viviendaId El id de la vivienda buscado
     * @param servicioAdicionalId Identificador del servicio adicional a consultar
     * @return Instancia de ServicioAdicionalEntity con los datos del ServicioAdicional consultado.
     *
     */
    public ServicioAdicionalEntity getServicioAdicional(Long viviendaId, Long servicioAdicionalId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar el servicio adicional con id = {1} de la vivienda con id = {0}" , new Object[]{ viviendaId, servicioAdicionalId});
        return persistence.find(viviendaId, servicioAdicionalId);
    }

    /**
     * Actualiza la información de una instancia de Servicio Adicional.
     *
     * @param servicioAdicionalEntity Instancia de ServicioAdicionalEntity con los nuevos datos.
     * @param viviendaId id de la Vivienda el cual sera padre del ServicioAdicoinal actualizado.
     * @return Instancia de ServicioAdicionalEntity con los datos actualizados.
     *
     */
    public ServicioAdicionalEntity updateServicioAdicional(Long viviendaId, ServicioAdicionalEntity servicioAdicionalEntity) {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar el servicio adicional con id = {1} de la vivienda con id = {0}" , new Object[]{ viviendaId, servicioAdicionalEntity.getId()});
        ViviendaEntity viviendaEntity = viviendaPersistence.find(viviendaId);
        servicioAdicionalEntity.setVivienda(viviendaEntity);
        persistence.update(servicioAdicionalEntity);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar el servicio adicional con id = {1} de la vivienda con id = {0} " , new Object[]{ viviendaId, servicioAdicionalEntity.getId()});
        return servicioAdicionalEntity;
    }

    /**
     * Elimina una instancia de Servicio Adicional de la base de datos.
     *
     * @param servicioAdicionalId Identificador de la instancia a eliminar.
     * @param viviendaId id del Book el cual es padre del ServicioAdicional.
     * @throws BusinessLogicException Si el servicio no esta asociada a la vivienda.
     *
     */
    public void deleteServicioAdicional(Long viviendaId, Long servicioAdicionalId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar el servicio adicional con id = {1} de la vivienda con id = {0} " , new Object[]{ viviendaId, servicioAdicionalId});
        ServicioAdicionalEntity old = getServicioAdicional(viviendaId, servicioAdicionalId);
        if (old == null) {
            throw new BusinessLogicException("El servicio adicional con id = " + servicioAdicionalId + " no esta asociado a la vivienda con id = " + viviendaId);
        }
        persistence.delete(old.getId());
        LOGGER.log(Level.INFO, "Termina proceso de borrar el servicio adicional con id = {1} de la vivienda con id = {0} " , new Object[]{ viviendaId, servicioAdicionalId});
    }
    
    public void generarServiciosAdicionales(Long viviendaId) {

        List<ServicioAdicionalEntity> serviciosViejos = getServiciosAdicionales(viviendaId);
        for (ServicioAdicionalEntity c : serviciosViejos) {
            try {
                deleteServicioAdicional(viviendaId, c.getId());
            } catch (BusinessLogicException e) {
                LOGGER.log(Level.INFO, "Error en el proceso de borrar el servicio de la vivienda con id = {0}", viviendaId);
            }
        }

        String[] nombresServiciosAdiconales = new String[]{"Lavandería", "Parqueadero", "Limpieza"};
        String[] descripcionServiciosAdiconales = new String[]{"Servicio de lavandería un día a la semana.", "Servicio de parquéo para un vehiculo.", "Servicio de limpieza dos dias a la semana. "};
        
        for (int i = 0; i < nombresServiciosAdiconales.length; i++) {
            ServicioAdicionalEntity servicioAdicional = new ServicioAdicionalEntity();
            float costo = (800 + new Random().nextFloat());
            costo *= 1000;

            servicioAdicional.setNombre(nombresServiciosAdiconales[i]);
            servicioAdicional.setCosto(costo);
            servicioAdicional.setDescripcion(descripcionServiciosAdiconales[i]);

            try {
                createServicioAdicional(viviendaId, servicioAdicional);
            } catch (BusinessLogicException e) {
                LOGGER.log(Level.INFO, "Error en proceso de agregar el servicio adicional de la vivienda con id = {0}", viviendaId);
            }
        }
    }
}
