/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.vivienda.ejb;

import co.edu.uniandes.csw.vivienda.entities.ContratoEntity;
import co.edu.uniandes.csw.vivienda.entities.ViviendaEntity;
import co.edu.uniandes.csw.vivienda.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.vivienda.persistence.ContratoPersistence;
import co.edu.uniandes.csw.vivienda.persistence.ViviendaPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author estudiante
 */
@Stateless
public class ViviendaContratosLogic {
    
    private static final Logger LOGGER = Logger.getLogger(ViviendaContratosLogic.class.getName());

    @Inject
    private ContratoPersistence contratoPersistence;

    @Inject
    private ViviendaPersistence viviendaPersistence;

    /**
     * Agregar un contrato a la vivienda
     *
     * @param contratoId El id contrato a guardar
     * @param viviendaId El id de la vivienda en la cual se va a guardar el
     * contrato.
     * @return El contrato creado.
     */
    public ContratoEntity addContrato(Long contratoId, Long viviendaId) {
        LOGGER.log(Level.INFO, "Inicia proceso de agregarle un contrato a la vivienda con id = {0}", viviendaId);
        ViviendaEntity viviendaEntity = viviendaPersistence.find(viviendaId);
        ContratoEntity contratoEntity = contratoPersistence.find(contratoId);
        contratoEntity.setVivienda(viviendaEntity);
        LOGGER.log(Level.INFO, "Termina proceso de agregarle un contrato a la vivienda con id = {0}", viviendaId);
        return contratoEntity;
    }

    /**
     * Retorna todos los contratos asociados a una vivienda
     *
     * @param viviendaId El ID de la vivienda buscada
     * @return La lista de contratos de la vivienda
     */
    public List<ContratoEntity> getContratos(Long viviendaId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar los contratos asociados a la vivienda con id = {0}", viviendaId);
        return viviendaPersistence.find(viviendaId).getContratos();
    }

    /**
     * Retorna un contrato asociado a una vivienda
     *
     * @param viendaId El id de la vivienda a buscar.
     * @param contratoId El id del contrato a buscar
     * @return El contrato encontrado dentro de la vivienda.
     * @throws BusinessLogicException Si el contrato no se encuentra en la
     * vivienda
     */
    public ContratoEntity getContrato(Long viendaId, Long contratoId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar el contrato con id = {1} de la vivienda con id = {0} " , new Object[]{ viendaId, contratoId});
        List<ContratoEntity> contratos = viviendaPersistence.find(viendaId).getContratos();
        ContratoEntity contratoEntity = contratoPersistence.find(contratoId);
        int index = contratos.indexOf(contratoEntity);
        LOGGER.log(Level.INFO, "Termina proceso de consultar el contrato con id = {1} de la vivienda con id = {0} " , new Object[]{ viendaId, contratoId});
        if (index >= 0) {
            return contratos.get(index);
        }
        throw new BusinessLogicException("El contrato no está asociado a la vivienda");
    }

    /**
     * Remplazar books de una editorial
     *
     * @param contratos Lista de contratos que serán los de la vivienda.
     * @param viviendaId El id de la vivienda que se quiere actualizar.
     * @return La lista de contratos actualizada.
     */
    public List<ContratoEntity> replaceContratos(Long viviendaId, List<ContratoEntity> contratos) {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar la vivienda con id = {0}", viviendaId);
        ViviendaEntity viviendaEntity = viviendaPersistence.find(viviendaId);
        List<ContratoEntity> contratoList = contratoPersistence.findAll();
        for (ContratoEntity contrato : contratoList) {
            if (contratos.contains(contrato)) {
                contrato.setVivienda(viviendaEntity);
            } else if (contrato.getVivienda() != null && contrato.getVivienda().equals(viviendaEntity)) {
                contrato.setVivienda(null);
            }
        }
        LOGGER.log(Level.INFO, "Termina proceso de actualizar la vivienda con id = {0}", viviendaId);
        return contratos;
    }
}
