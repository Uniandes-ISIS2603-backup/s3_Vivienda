/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.vivienda.ejb;

import co.edu.uniandes.csw.vivienda.entities.ContratoEntity;
import co.edu.uniandes.csw.vivienda.entities.ViviendaEntity;
import co.edu.uniandes.csw.vivienda.persistence.ContratoPersistence;
import co.edu.uniandes.csw.vivienda.persistence.ViviendaPersistence;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * Clase que implementa la conexion con la persistencia para la relación entre
 * la entidad de Contrato y Vivienda.
 *
 * @author estudiante
 */
@Stateless
public class ContratoViviendaLogic {

    private static final Logger LOGGER = Logger.getLogger(ContratoViviendaLogic.class.getName());

    @Inject
    private ContratoPersistence contratoPersistence;

    @Inject
    private ViviendaPersistence viviendaPersistence;

    /**
     * Obtiene una instancia de viviendaEntity asociada a una instancia de Book
     *
     * @param contratoId Identificador de la instancia de Book
     * @param viviendaId Identificador de la instancia de Author
     * @return La entidad del Autor asociada al libro
     */
    public ViviendaEntity getVivienda(Long contratoId, Long viviendaId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar una vivienda del contrato con id = {0}", contratoId);
        ViviendaEntity vivienda = contratoPersistence.find(contratoId).getVivienda();
        LOGGER.log(Level.INFO, "Termina proceso de consultar una vivienda del contrato con id = {0}", contratoId);
        if (vivienda != null) {
            return vivienda;
        }
        return null;
    }

    /**
     * Remplazar la vivienda de un contrato.
     *
     * @param contratoId id del contrato que se quiere actualizar.
     * @param viviendaId El id de la vivienda que se será del contrato.
     * @return el nuevo contrato.
     */
    public ContratoEntity replaceVivienda(Long contratoId, Long viviendaId) {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar contrato con id = {0}", contratoId);
        ViviendaEntity editorialEntity = viviendaPersistence.find(viviendaId);
        ContratoEntity bookEntity = contratoPersistence.find(contratoId);
        bookEntity.setVivienda(editorialEntity);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar contrato con id = {0}", bookEntity.getId());
        return bookEntity;
    }

    /**
     * Borrar un contrato de una vivienda. Este metodo se utiliza para borrar la
     * relacion de un contrato.
     *
     * @param contratoId El contrato que se desea borrar de la vivienda.
     */
    public void removeVivienda(Long contratoId) 
    {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar la Vivienda del contrato con id = {0}", contratoId);
        ContratoEntity contratoEntity = contratoPersistence.find(contratoId);
        ViviendaEntity viviendaEntity = viviendaPersistence.find(contratoEntity.getVivienda().getId());
        contratoEntity.setVivienda(null);
        viviendaEntity.getContratos().remove(contratoEntity);
        LOGGER.log(Level.INFO, "Termina proceso de borrar la Vivienda del contrato con id = {0}", contratoEntity.getId());
    }
}
