/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.vivienda.ejb;

import co.edu.uniandes.csw.vivienda.entities.SitioInteresEntity;
import co.edu.uniandes.csw.vivienda.entities.ViviendaEntity;
import co.edu.uniandes.csw.vivienda.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.vivienda.persistence.SitioInteresPersistence;
import co.edu.uniandes.csw.vivienda.persistence.ViviendaPersistence;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author msalcedo
 */
@Stateless
public class SitioInteresLogic 
{
    @Inject
    private SitioInteresPersistence sitioInteresPersistence;
    
    @Inject
    private ViviendaPersistence viviendaPersistence;
    
    public SitioInteresEntity getSitioInteres(Long viviendaId, Long sitioInteresId)
    {
       SitioInteresEntity sitioInteresEntity = sitioInteresPersistence.find(viviendaId, sitioInteresId);
       return sitioInteresEntity;
    }
    
    public SitioInteresEntity createSitioInteres(Long viviendaId, SitioInteresEntity sitioInteresEntity) throws BusinessLogicException
    {
        if(sitioInteresPersistence.findByLatLong(sitioInteresEntity.getLatitud(), sitioInteresEntity.getLongitud())!=null)
        {
            throw new BusinessLogicException("Ya existe un sitioInteres con esa latitud y longitud ");
        }
        ViviendaEntity viviendaEntity = viviendaPersistence.find(viviendaId);
        sitioInteresEntity.setVivienda(viviendaEntity);

        return sitioInteresPersistence.create(sitioInteresEntity);
    }
    
    public SitioInteresEntity updateSitioInteres(Long sitioInteresId, SitioInteresEntity sitioInteresEntity)
    {
        SitioInteresEntity newSitioInteres = sitioInteresPersistence.update(sitioInteresEntity);
        return newSitioInteres;
    }
    
    
    public void deleteSitioInteres(Long viviendaId, Long sitioInteresId) throws BusinessLogicException
    {
        SitioInteresEntity old = getSitioInteres(viviendaId, sitioInteresId);
        if(old==null)
        {
            throw new BusinessLogicException("El sitioInteres con id = " + sitioInteresId + " no esta asociado a la vivienda con id = " + viviendaId);
        }
        sitioInteresPersistence.delete(sitioInteresId);
    }
    
    public List<SitioInteresEntity> getSitiosInteres()
    {
         List<SitioInteresEntity> sitiosInteres = sitioInteresPersistence.findAll();
        return sitiosInteres;
    }
}
