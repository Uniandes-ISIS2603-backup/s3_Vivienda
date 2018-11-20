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
import java.util.Random;
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
        Float latitud = sitioInteresEntity.getLatitud();
        Float longitud = sitioInteresEntity.getLongitud();
        String nombre = sitioInteresEntity.getNombre();
        if(latitud==null)
        {
            throw new BusinessLogicException("latitud invalida");
        }
        else if(longitud==null)
        {
            throw new BusinessLogicException("longitud invalida");
        }
        else if(nombre==null || nombre.equals(" "))
        {
            throw new BusinessLogicException("nombre invalido");
        }
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
        LOGGER.log(Level.INFO, "Inicia proceso de consultar el sitioIntere con id = {1} de la vivienda con id = {0}" , new Object[]{viviendaId, sitioInteresId});
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
     * @param viviendaId id de la vivienda que sera el padre del sitioInteres a actualizar.
     * @return Instancia de SitioInteresEntity con los datos actualizados.
     *
     */
    public SitioInteresEntity updateSitioInteres(Long viviendaId, SitioInteresEntity sitioInteresEntity)
    {
        
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar el sitioInteres con id = {1} de la vivienda con id ={0} " , new Object[]{viviendaId, sitioInteresEntity.getId()});
        ViviendaEntity vivienda = viviendaPersistence.find(viviendaId);
        sitioInteresEntity.setVivienda(vivienda);
        sitioInteresPersistence.update(sitioInteresEntity);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar el sitioInteres con id = {1} de la vivienda con id = {0} " , new Object[]{viviendaId, sitioInteresEntity.getId()});
        return sitioInteresEntity;
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
    
    public void generarDatos(Long viviendaId)
    {
        List<SitioInteresEntity> sitios = getSitiosInteres(viviendaId);
        for (SitioInteresEntity sitio : sitios) {
            try {
                deleteSitioInteres(viviendaId,sitio.getId());
            } catch (BusinessLogicException e) {
                LOGGER.log(Level.INFO, "Inicia proceso de borrar el sitio de interes de la vivienda con id = {0}", viviendaId);
            }
        }
        Random rand = new Random();
        for (int i = 0; i < 3; i++) {
            SitioInteresEntity sitio = new SitioInteresEntity();
            sitio.setNombre("SitioInteres"+ (i+1));
            sitio.setDescripcion("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.");
            sitio.setLatitud(rand.nextFloat());
            sitio.setLongitud(rand.nextFloat());
            sitio.setImg("assets/img/sitioInteres" + (i+1) + ".jpg");
            
            try {
                createSitioInteres(viviendaId,sitio);
            } catch (BusinessLogicException e) {
                LOGGER.log(Level.INFO, "Inicia proceso de agregar el sitio de interes de la vivienda con id = {0}", viviendaId);
            }
        }
    }
}
