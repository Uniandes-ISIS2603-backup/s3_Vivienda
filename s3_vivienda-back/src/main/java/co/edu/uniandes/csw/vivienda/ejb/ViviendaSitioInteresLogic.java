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
import static com.sun.xml.internal.ws.spi.db.BindingContextFactory.LOGGER;
import java.util.List;
import java.util.logging.Level;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author estudiante
 */
@Stateless
public class ViviendaSitioInteresLogic {
    
    @Inject
    private SitioInteresPersistence sitioInteresPersistence;

    @Inject
    private ViviendaPersistence viviendaPersistence;
    
    /**
     * Agregar un sitioInteres a la vivienda
     *
     * @param sitioInteresId El id sitioInteres a guardar
     * @param editorialsId El id de la vivienda en la cual se va a guardar el
     * sitioInteres.
     * @return El sitioInteres creado.
     */
    public SitioInteresEntity addSitioInteres(Long sitioInteresId, Long viviendaId) {
        LOGGER.log(Level.INFO, "Inicia proceso de agregarle un sitioInteres a la vivienda con id = {0}", viviendaId);
        ViviendaEntity viviendaEntity = viviendaPersistence.find(viviendaId);
        SitioInteresEntity sitioInteresEntity = sitioInteresPersistence.find(sitioInteresId);
        sitioInteresEntity.setVivienda(viviendaEntity);
        LOGGER.log(Level.INFO, "Termina proceso de agregarle un sitioInteres a la vivienda con id = {0}", viviendaId);
        return sitioInteresEntity;
    }
    
    /**
     * Retorna todos los sitiosInteres asociados a una Vivienda
     *
     * @param viviendaId El ID de la vivienda buscada
     * @return La lista de sitioInteres de la vivienda
     */
    public List<SitioInteresEntity> getSitioInteres(Long viviendaId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar los sitiosInteres asociados a la vivienda con id = {0}", viviendaId);
        return viviendaPersistence.find(viviendaId).getSitiosDeInteres();
    }
    
    /**
     * Retorna un sitioInteres asociado a una vivienda
     *
     * @param viviendaId El id de la vivienda a buscar.
     * @param sitioInteresId El id del sitioInteres a buscar
     * @return El sitioInteres encontrado dentro de la vivienda.
     * @throws BusinessLogicException Si el sitioInteres no se encuentra en la
     * vivienda
     */
    public SitioInteresEntity getSitioInteres(Long viviendaId, Long sitioInteresId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar el sitioInteres con id = {0} de la vivienda con id = " + viviendaId, sitioInteresId);
        List<SitioInteresEntity> sitios = viviendaPersistence.find(viviendaId).getSitiosDeInteres();
        SitioInteresEntity sitioInteresEntity = sitioInteresPersistence.find(sitioInteresId);
        int index = sitios.indexOf(sitioInteresEntity);
        LOGGER.log(Level.INFO, "Termina proceso de consultar el sitioInteres con id = {0} de la vivienda con id = " + viviendaId, sitioInteresId);
        if (index >= 0) {
            return sitios.get(index);
        }
        throw new BusinessLogicException("El sitioInteres no está asociado a la vivienda");
    }
    
    /**
     * Remplazar sitiosInteres de una vivienda
     *
     * @param sitios Lista de itiosInteres que serán los de la vivienda.
     * @param viviendaId El id de la vivienda que se quiere actualizar.
     * @return La lista de sitiosInteres actualizada.
     */
    public List<SitioInteresEntity> replaceSitiosInteres(Long viviendaId, List<SitioInteresEntity> sitios) {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar la vivienda con id = {0}", viviendaId);
        ViviendaEntity viviendaEntity = viviendaPersistence.find(viviendaId);
        List<SitioInteresEntity> sitioInteresList = sitioInteresPersistence.findAll();
        for (SitioInteresEntity sitio : sitioInteresList) {
            if (sitios.contains(sitio)) {
                sitio.setVivienda(viviendaEntity);
            } else if (sitio.getVivienda() != null && sitio.getVivienda().equals(viviendaEntity)) {
                sitio.setVivienda(null);
            }
        }
        LOGGER.log(Level.INFO, "Termina proceso de actualizar la vivienda con id = {0}", viviendaId);
        return sitios;
    }
}
