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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *Clase que implementa la conexion con la persistencia para la entidad de SitioInteres.
 * @author msalcedo
 */
@Stateless
public class SitioInteresLogic 
{
    private static final Logger LOGGER = Logger.getLogger(SitioInteresLogic.class.getName());
    @Inject
    private SitioInteresPersistence sitioInteresPersistence;
    
    @Inject
    private ViviendaPersistence viviendaPersistence;
    
    /**
     * Se encarga de crear un SitioInteres en la base de datos.
     *
     * @param sitioInteresEntity Objeto de SitioInteresEntity con los datos nuevos
     * @param viviendaId id de la vivienda el cual sera padre del nuevo SitioInteres.
     * @return Objeto de SitioInteresEntity con los datos nuevos y su ID.
     * @throws BusinessLogicException si ya existe un sitioInteres con esa latitud y longitud
     *
     */
    public SitioInteresEntity createSitioInteres(Long viviendaId, SitioInteresEntity sitioInteresEntity) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "Inicia proceso de creación del sitioInteres");
        if(sitioInteresPersistence.findByLatLong(sitioInteresEntity.getLatitud(), sitioInteresEntity.getLongitud())!=null)
        {
            throw new BusinessLogicException("Ya existe un sitioInteres con esa latitud y longitud ");
        }
        ViviendaEntity viviendaEntity = viviendaPersistence.find(viviendaId);
        sitioInteresEntity.setVivienda(viviendaEntity);
        LOGGER.log(Level.INFO, "Termina proceso de creación del sitioInteres");
        return sitioInteresPersistence.create(sitioInteresEntity);
        
    }
    
        /**
     * Obtiene los datos de una instancia de SitioInteres a partir de su ID. La
     * existencia del elemento padre vivienda se debe garantizar.
     *
     * @param viviendaId El id de la vivienda buscada
     * @param sitioInteresId Identificador del sitioInteres a consultar
     * @return Instancia de SitioInteresEntity con los datos del sitioInteres consultado.
     *
     */
    public SitioInteresEntity getSitioInteres(Long viviendaId, Long sitioInteresId)
    {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar el sitioIntere con id = {0} de la vivienda con id = " + viviendaId, sitioInteresId);
        SitioInteresEntity sitioInteresEntity = sitioInteresPersistence.find(viviendaId, sitioInteresId);
        if (sitioInteresEntity == null) {
            LOGGER.log(Level.SEVERE, "El sitioInteres con el id = {0} no existe", sitioInteresId);
        }
        LOGGER.log(Level.INFO, "Termina proceso de consultar el sitioInteres con id = {0}", sitioInteresId);
       return sitioInteresEntity;
    }
    
    
    /**
     * Actualiza la información de una instancia de SitioInteres.
     *
     * @param sitioInteresEntity Instancia de SitioInteresEntity con los nuevos datos.
     * @param viviendaId id de la vivienda el cual sera padre del sitioInteres actualizado.
     * @return Instancia de SitioInteresEntity con los datos actualizados.
     *
     */
    public SitioInteresEntity updateSitioInteres(Long sitioInteresId, SitioInteresEntity sitioInteresEntity)
    {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar el sitioInteres con id = {0}", sitioInteresId);
        SitioInteresEntity newSitioInteres = sitioInteresPersistence.update(sitioInteresEntity);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar el sitioInteres con id = {0}", sitioInteresEntity.getId());
        return newSitioInteres;
    }
    
     /**
     * Elimina una instancia de SitioInteres de la base de datos.
     *
     * @param sitioInteresId Identificador de la instancia a eliminar.
     * @param viviendaId id del Book el cual es padre del SitioInteres.
     * @throws BusinessLogicException Si el sitioInteres no esta asociada a la vivienda.
     *
     */
    public void deleteSitioInteres(Long viviendaId, Long sitioInteresId) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar el sitioInteres con id = {0}", sitioInteresId);
        SitioInteresEntity old = getSitioInteres(viviendaId, sitioInteresId);
        if(old==null)
        {
            throw new BusinessLogicException("El sitioInteres con id = " + sitioInteresId + " no esta asociado a la vivienda con id = " + viviendaId);
        }
        sitioInteresPersistence.delete(sitioInteresId);
        LOGGER.log(Level.INFO, "Termina proceso de borrar el sitioInteres con id = {0}", sitioInteresId);
    }
    
      /**
     * Retorna todos los sitiosInteres asociados a una Vivienda
     *
     * @param viviendaId El ID de la vivienda buscada
     * @return La lista de sitioInteres de la vivienda
     */
    public List<SitioInteresEntity> getSitiosInteres(Long viviendaId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar los sitiosInteres asociados a la vivienda con id = {0}", viviendaId);
        ViviendaEntity viviendaEntity = viviendaPersistence.find(viviendaId);
        LOGGER.log(Level.INFO, "Termina proceso de consultar los sitioInteres asociados a la vivienda con id = {0}", viviendaId);
        return viviendaEntity.getSitiosDeInteres();
    }
}
