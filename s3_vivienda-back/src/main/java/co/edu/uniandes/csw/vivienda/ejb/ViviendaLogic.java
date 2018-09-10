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
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author DANIEL
 */
@Stateless
public class ViviendaLogic {
    
    @Inject
    private ViviendaPersistence persistence;
    
    public ViviendaEntity createVivienda(ViviendaEntity viviendaEntity) throws BusinessLogicException{
        String ciudad = viviendaEntity.getCiudad();
        String direccion = viviendaEntity.getDireccion();

        ViviendaEntity direccionRepetida = persistence.buscarPorDireccion(ciudad, direccion);

        if (direccionRepetida == null){
            viviendaEntity = persistence.create(viviendaEntity);
            return(viviendaEntity);
        }
        else {
            throw new BusinessLogicException("Ya existe una vivienda en la misma dirección");
        }
    }
    
    public ViviendaEntity getVivienda(Long id){
        ViviendaEntity viviendaEntity = persistence.find(id);
        return viviendaEntity;
    }
    
    public List<ViviendaEntity> getViviendas(){
        List<ViviendaEntity> viviendas = persistence.findAll();
        return viviendas;
    }
    
    public ViviendaEntity updateVivienda(Long id, ViviendaEntity viviendaEntity) throws BusinessLogicException{
        if(persistence.find(id) == null){
            throw new BusinessLogicException("La universidad con el id dado no existe");
        }
        viviendaEntity.setId(id);
        ViviendaEntity newVivienda = persistence.update(viviendaEntity);
        return newVivienda;
    }
    
    public void deleteVivienda(Long viviendaId) throws BusinessLogicException{
        if(persistence.find(viviendaId)==null){
            throw new BusinessLogicException("La vivienda no existe");
        }
        persistence.delete(viviendaId);
    }

}
