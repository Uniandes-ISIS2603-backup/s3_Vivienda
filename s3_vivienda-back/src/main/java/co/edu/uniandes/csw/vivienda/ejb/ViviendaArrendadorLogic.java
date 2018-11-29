/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.vivienda.ejb;

import co.edu.uniandes.csw.vivienda.entities.ArrendadorEntity;
import co.edu.uniandes.csw.vivienda.entities.ViviendaEntity;
import co.edu.uniandes.csw.vivienda.persistence.ArrendadorPersistence;
import co.edu.uniandes.csw.vivienda.persistence.ViviendaPersistence;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *Clase que implementa la conexion con la persistencia para la relación entre
 * la entidad de vivienda e Arrendador.
 * @author estudiante
 */
@Stateless
public class ViviendaArrendadorLogic {
    private static final Logger LOGGER = Logger.getLogger(ViviendaArrendadorLogic.class.getName());

    @Inject
    private ViviendaPersistence viviendaPersistence;

    @Inject
    private ArrendadorPersistence arrendadorPersistence;
    
     /**
     * Remplazar el arrendador de una vivienda.
     *
     * @param viviendaId id de la vivienda que se quiere actualizar.
     * @param arrendadorId El id del arrendador que se será de la vivienda.
     * @return la nueva vivienda.
     */
    public ViviendaEntity replaceArrendador(Long viviendaId, Long arrendadorId) {   
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar con id = {0}", arrendadorId);
        ArrendadorEntity arrendadorEntity = arrendadorPersistence.find(arrendadorId);
        ViviendaEntity viviendaEntity = viviendaPersistence.find(viviendaId);
        viviendaEntity.setArrendador(arrendadorEntity);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar  con id = {0}", viviendaEntity.getId());
        return viviendaEntity;
    }
    
        /**
     *
     * Obtener un arrendador por medio de su id y de la vivienda sociada al arrendador.
     *
     * @param viviendaId id del premio a ser buscado.
     * @return el arrendador solicitado por medio de su id.
     */
    public ArrendadorEntity getArrendador(Long viviendaId)
    {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar un arrendador de la vivienda con id = {0}", viviendaId);
        if(viviendaPersistence.find(viviendaId)!=null)
        {
            ArrendadorEntity arrendador = viviendaPersistence.find(viviendaId).getArrendador();
            if(arrendador!=null)
            {
                return arrendador;
            }
        }
        LOGGER.log(Level.INFO, "Termina proceso de consultar un arrendador de la vivienda id = {0}", viviendaId);
        
        return null;
    }
    
    
}
