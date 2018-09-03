/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.vivienda.ejb;

import co.edu.uniandes.csw.vivienda.entities.ViviendaEntity;
import co.edu.uniandes.csw.vivienda.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.vivienda.persistence.ViviendaPersistence;
import java.util.List;
import javax.inject.Inject;

/**
 *
 * @author DANIEL
 */
public class ViviendaLogic {
    
    @Inject
    private ViviendaPersistence persistence;
    
    public ViviendaEntity createVivienda(ViviendaEntity viviendaEntity) throws BusinessLogicException{
        persistence.create(viviendaEntity);
        return(viviendaEntity);
    }
    
    public ViviendaEntity getVivienda(Long id){
        ViviendaEntity viviendaEntity = persistence.find(id);
        return viviendaEntity;
    }
    
    public List<ViviendaEntity> getViviendas(){
        List<ViviendaEntity> viviendas = persistence.findAll();
        return viviendas;
    }
    
    public ViviendaEntity updateVivienda(Long id, ViviendaEntity viviendaEntity){
        ViviendaEntity newVivienda = persistence.update(viviendaEntity);
        return newVivienda;
    }
    
    public void deleteVivienda(Long ViviedaId){
        persistence.delete(ViviedaId);
    }
}
