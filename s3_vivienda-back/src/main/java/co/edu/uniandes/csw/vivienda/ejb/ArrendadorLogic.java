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
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author msalcedo
 */
@Stateless
public class ArrendadorLogic {
    
    @Inject
    private ArrendadorPersistence arrendadorPersistence;
    
    public ArrendadorEntity getArrendador(Long arrendadorId) throws BusinessLogicException
    {
        ArrendadorEntity arrendadorEntity = arrendadorPersistence.find(arrendadorId);
        if (arrendadorEntity == null) {
            throw new BusinessLogicException("El arrendador con id: " + arrendadorId + " no existe");
        }
        return arrendadorEntity;
    }
    
    public ArrendadorEntity createArrendador(ArrendadorEntity arrendadorEntity) throws BusinessLogicException
    {
        String login = arrendadorEntity.getLogin();

        ArrendadorEntity arrendadorExiste = arrendadorPersistence.findByLogin(login);
 
        if (arrendadorExiste == null){
            arrendadorEntity = arrendadorPersistence.create(arrendadorEntity);
            return(arrendadorEntity);
        }
        else {
            throw new BusinessLogicException("Ya existe un arrendador con ese login");
        }
    }
    
    public ArrendadorEntity updateArrendador(Long arrendadorId, ArrendadorEntity arrendadorEntity) throws BusinessLogicException
    {
        ArrendadorEntity newArrendador = null;
        if(getArrendador(arrendadorId)!=null)
        {
            newArrendador = arrendadorPersistence.update(arrendadorEntity);
        }
        return newArrendador;
    }
    
    
    public void deleteArrendador(Long arrendadorId) throws BusinessLogicException 
    {
        List<ViviendaEntity> viviendas = getArrendador(arrendadorId).getViviendas();
        if (viviendas != null && !viviendas.isEmpty()) {
            throw new BusinessLogicException("No se puede borrar el arrendador con id = " + arrendadorId + " porque tiene viviendas asociadas");
        }
        else if(getArrendador(arrendadorId)!=null)
        {
            throw new BusinessLogicException("El recurso /arrendadores/" + arrendadorId +" no existe");
        }
        arrendadorPersistence.delete(arrendadorId);

    }
    
    public List<ArrendadorEntity> getArrendadores()
    {
        List<ArrendadorEntity> arrendadores = arrendadorPersistence.findAll();
        return arrendadores;
    }
}
