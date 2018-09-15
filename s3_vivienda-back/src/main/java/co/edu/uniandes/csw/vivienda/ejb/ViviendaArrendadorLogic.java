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
//import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author estudiante
 */
@Stateless
public class ViviendaArrendadorLogic {
    //private static final Logger LOGGER = Logger.getLogger(ViviendaArrendadorLogic.class.getName());

    @Inject
    private ViviendaPersistence viviendaPersistence;

    @Inject
    private ArrendadorPersistence arrendadorPersistence;
    
    public ViviendaEntity replaceArrendador(Long viviendaId, Long arrendadorId) {   
        //LOGGER.log(Level.INFO, "Inicia proceso de actualizar con id = {0}", Id);
        ArrendadorEntity arrendadorEntity = arrendadorPersistence.find(arrendadorId);
        ViviendaEntity viviendaEntity = viviendaPersistence.find(viviendaId);
        viviendaEntity.setArrendador(arrendadorEntity);
        //LOGGER.log(Level.INFO, "Termina proceso de actualizar  con id = {0}", viviendaEntity.getId());
        return viviendaEntity;
    }
    
    public ArrendadorEntity getArrendador(Long viviendaId, Long arrendadorId)
    {
        ArrendadorEntity arrendador = viviendaPersistence.find(viviendaId).getArrendador();
        
        if(arrendador!=null)
        {
            return arrendador;
        }
        
        return null;
    }
    
    
}
