/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.vivienda.ejb;

import co.edu.uniandes.csw.vivienda.entities.ArrendadorEntity;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author msalcedo
 */
@Stateless
public class ArrendadorLogic {
    
    //@Inject
    //private ArrendadorPersistence arrendadorpersistence;
    
    public ArrendadorEntity getArrendador(Long arrendadorId)
    {
        return null;
    }
    
    public ArrendadorEntity createArrendador(ArrendadorEntity arrendadorEntity)
    {
        return null;
    }
    
    public ArrendadorEntity updateArrendador(Long arrendadorId, ArrendadorEntity arrendadorEntity)
    {
        return null;
    }
    
    
    public void deleteArrendador(Long arrendadorId)
    {
        
    }
    
    public List<ArrendadorEntity> getArrendadores()
    {
        return null;
    }
}
