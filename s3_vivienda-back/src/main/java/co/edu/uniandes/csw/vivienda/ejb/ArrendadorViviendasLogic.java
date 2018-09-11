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
import javax.ejb.Stateless;

/**
 *
 * @author msalcedo
 */
@Stateless
public class ArrendadorViviendasLogic {
    
    private ViviendaPersistence viviendasPersistence;
    
    private ArrendadorPersistence arrendadorPersistence;
    
    public ViviendaEntity addVivienda(Long viviendaId, Long arrendadorId) {
 
        ArrendadorEntity arrendadorEntity = arrendadorPersistence.find(arrendadorId);
        ViviendaEntity viviendaEntity = viviendasPersistence.find(viviendaId);
        viviendaEntity.setArrendador(arrendadorEntity);
        return viviendaEntity;
    }
    
    public List<ViviendaEntity> getViviendas(Long arrendadorId) {
        
        return arrendadorPersistence.find(arrendadorId).getViviendas();
    }
    
    public ViviendaEntity getVivienda(Long arrendadorId, Long viviendaId) throws BusinessLogicException {
        
        List<ViviendaEntity> viviendas = arrendadorPersistence.find(arrendadorId).getViviendas();
        ViviendaEntity viviendaEntity = viviendasPersistence.find(viviendaId);
        int index = viviendas.indexOf(viviendaEntity);
        
        if (index >= 0) {
            return viviendas.get(index);
        }
        throw new BusinessLogicException("La vivienda no está asociado al arrendador");
    }
    
    public List<ViviendaEntity> replaceViviendas(Long arrendadorId, List<ViviendaEntity> viviendas) {
        
        ArrendadorEntity arrendadorEntity = arrendadorPersistence.find(arrendadorId);
        List<ViviendaEntity> viviendasList = viviendasPersistence.findAll();
        for (ViviendaEntity vivienda : viviendasList) {
            if (viviendas.contains(vivienda)) {
                vivienda.setArrendador(arrendadorEntity);
            } else if (vivienda.getArrendador() != null && vivienda.getArrendador().equals(arrendadorEntity)) {
                vivienda.setArrendador(null);
            }
        }
        
        return viviendas;
    }
}
