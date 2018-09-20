/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.vivienda.ejb;

import co.edu.uniandes.csw.vivienda.entities.ArrendadorEntity;
import co.edu.uniandes.csw.vivienda.entities.ViviendaEntity;
import co.edu.uniandes.csw.vivienda.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.vivienda.persistence.ArrendadorPersistence;
import co.edu.uniandes.csw.vivienda.persistence.ViviendaPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;

/**
 *
 * @author msalcedo
 */
@Stateless
public class ArrendadorViviendasLogic {
    
     private static final Logger LOGGER = Logger.getLogger(ArrendadorViviendasLogic.class.getName());
        
    private ViviendaPersistence viviendasPersistence;
    
    private ArrendadorPersistence arrendadorPersistence;
    
     /**
     * Agregar una vivienda al arrendador
     *
     * @param viviendaId El id de la vivienda a guardar
     * @param arrendadorId El id del arrendador en la cual se va a guardar el
     * libro.
     * @return El libro creado.
     */
    public ViviendaEntity addVivienda(Long viviendaId, Long arrendadorId) {
        LOGGER.log(Level.INFO, "Inicia proceso de agregarle una vivienda al arrendador con id = {0}", arrendadorId);
        ArrendadorEntity arrendadorEntity = arrendadorPersistence.find(arrendadorId);
        ViviendaEntity viviendaEntity = viviendasPersistence.find(viviendaId);
        viviendaEntity.setArrendador(arrendadorEntity);
        LOGGER.log(Level.INFO, "Termina proceso de agregarle una vivienda al arrendador con id = {0}", arrendadorId);
        return viviendaEntity;
    }
    
        /**
     * Retorna todas las viviendas asociados a un arrendador
     *
     * @param arrendadorId El ID del arrendador buscado
     * @return La lista de viviendas  del arrendador
     */
    public List<ViviendaEntity> getViviendas(Long arrendadorId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar las viviendas asociadas a un arrendador con id = {0}", arrendadorId);
        return arrendadorPersistence.find(arrendadorId).getViviendas();
    }
    
        /**
     * Retorna una vivienda asociada a un arrendador
     *
     * @param arrendadorId El id del arrendador a buscar.
     * @param viviendaId El id de la vivienda a buscar
     * @return La vivienda encontrada dentro del arrendador.
     * @throws BusinessLogicException Si la vivienda no se encuentra en el arrendador
     */
    public ViviendaEntity getVivienda(Long arrendadorId, Long viviendaId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar la vivienda con id = {0} del arrendador con id = " + arrendadorId, viviendaId);
        List<ViviendaEntity> viviendas = arrendadorPersistence.find(arrendadorId).getViviendas();
        ViviendaEntity viviendaEntity = viviendasPersistence.find(viviendaId);
        int index = viviendas.indexOf(viviendaEntity);
        LOGGER.log(Level.INFO, "Termina proceso de consultar la vivienda con id = {0} del arrendador con id = " + arrendadorId, viviendaId);
        if (index >= 0) {
            return viviendas.get(index);
        }
        throw new BusinessLogicException("La vivienda no está asociado al arrendador");
    }
    
        /**
     * Remplazar viviendas de un arrendador
     *
     * @param viviendas Lista de viviendas que serán los del arrendador.
     * @param arrendadorId El id del arrendador que se quiere actualizar.
     * @return La lista de viviendas actualizada.
     */
    public List<ViviendaEntity> replaceViviendas(Long arrendadorId, List<ViviendaEntity> viviendas) {
         LOGGER.log(Level.INFO, "Inicia proceso de actualizar el arrendador con id = {0}", arrendadorId);
        ArrendadorEntity arrendadorEntity = arrendadorPersistence.find(arrendadorId);
        List<ViviendaEntity> viviendasList = viviendasPersistence.findAll();
        for (ViviendaEntity vivienda : viviendasList) {
            if (viviendas.contains(vivienda)) {
                vivienda.setArrendador(arrendadorEntity);
            } else if (vivienda.getArrendador() != null && vivienda.getArrendador().equals(arrendadorEntity)) {
                vivienda.setArrendador(null);
            }
        }
        LOGGER.log(Level.INFO, "Termina proceso de actualizar el arrendador con id = {0}", arrendadorId);
        return viviendas;
    }
}
