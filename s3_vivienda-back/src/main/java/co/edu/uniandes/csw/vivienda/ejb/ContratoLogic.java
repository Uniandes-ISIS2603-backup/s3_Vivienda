/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.vivienda.ejb;

import co.edu.uniandes.csw.vivienda.entities.ContratoEntity;
import co.edu.uniandes.csw.vivienda.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.vivienda.persistence.ContratoPersistence;
import co.edu.uniandes.csw.vivienda.persistence.ViviendaPersistence;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
//import co.edu.uniandes.csw.vivienda.persistence.ContratoPersistence;
//import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author estudiante
 */
@Stateless
public class ContratoLogic {

    private static final Logger LOGGER = Logger.getLogger(ContratoLogic.class.getName());

    @Inject
    private ContratoPersistence persistence;

    @Inject
    private ViviendaPersistence viviendaPersistence;

    /**
     * Guardar un nuevo contrato
     *
     * @param contratoEntity La entidad de tipo contrato del nuevo contrato a
     * persistir.
     * @return La entidad luego de persistirla
     * @throws BusinessLogicException Si el metodoPago es inválido o ya existe
     * en la persistencia.
     */
    public ContratoEntity createContrato(ContratoEntity contratoEntity) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de creación del contrato");
        if (contratoEntity.getVivienda() == null || viviendaPersistence.find(contratoEntity.getVivienda().getId()) == null) {
            throw new BusinessLogicException("La vivienda es inválida");
        }
        if (!validateMetodoPago(contratoEntity.getMetodoPago())) {
            throw new BusinessLogicException("El metodoPago es inválido");
        }
        persistence.create(contratoEntity);
        LOGGER.log(Level.INFO, "Termina proceso de creación del contrato");
        return contratoEntity;
    }

    /**
     * Devuelve todos los contratos que hay en la base de datos.
     *
     * @return Lista de entidades de tipo contrato.
     */
    public List<ContratoEntity> getContratos() {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todos los contratos");
        List<ContratoEntity> contratos = persistence.findAll();
        LOGGER.log(Level.INFO, "Termina proceso de consultar todos los contratos");
        return contratos;
    }

    /**
     * Busca un contrato por ID
     *
     * @param contratoId El id del contrato a buscar
     * @return El contrato encontrado, null si no lo encuentra.
     */
    public ContratoEntity getContrato(Long contratoId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar el contrato con id = {0}", contratoId);
        ContratoEntity contratoEntity = persistence.find(contratoId);
        if (contratoEntity == null) {
            LOGGER.log(Level.SEVERE, "El contrato con el id = {0} no existe", contratoId);
        }
        LOGGER.log(Level.INFO, "Termina proceso de consultar el contrato con id = {0}", contratoId);
        return contratoEntity;
    }


    /**
     * Actualizar un contrato por ID
     *
     * @param contratoId El ID del contrato a actualizar
     * @param contratoEntity La entidad del contrato con los cambios deseados
     * @return La entidad del contrato luego de actualizarla
     * @throws BusinessLogicException Si el metodoPago de la actualizacion es
     * invalido
     */
    public ContratoEntity updateContrato(Long contratoId, ContratoEntity contratoEntity) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar el contrato con id = {0}", contratoId);
        if (!validateMetodoPago(contratoEntity.getMetodoPago())) {
            throw new BusinessLogicException("El metodoPago es inválido");
        }
        ContratoEntity newEntity = persistence.update(contratoEntity);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar el libro con id = {0}", contratoEntity.getId());
        return newEntity;
    }

    /**
     * Eliminar un contrato por ID
     *
     * @param contratoId El ID del contrato a eliminar
     */
    public void deleteContrato(Long contratoId) {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar el contrato con id = {0}", contratoId);
        persistence.delete(contratoId);
        LOGGER.log(Level.INFO, "Termina proceso de borrar el contrato con id = {0}", contratoId);
    }

    /**
     * Verifica que el metodoPago no sea invalido.
     *
     * @param metodoPago a verificar
     * @return true si el metodoPago es valido.
     */
    private boolean validateMetodoPago(int metodoPago) {
        return metodoPago < 0;
    }

    /**
     * Verifica que el id no sea invalido.
     *
     * @param id a verificar
     * @return true si el metodoPago es valido.
     */
    private boolean validateId(Long id) {
        return !(id == null || id < 0);
    }

    public void generarDatos() 
    {
        List<ContratoEntity> contratosViejos = getContratos();
        
        for (ContratoEntity co : contratosViejos) 
        {
            try 
            {
                deleteContrato(co.getId());
            } 
            catch (Exception e) 
            {
                e.printStackTrace();
            }
        }

        Random rand = new Random();
        for (int i = 0; i < 10; i++) 
        {
            ContratoEntity c = new ContratoEntity();
            c.setMetodoPago(rand.nextInt(100));
            c.setFechaInicio(new Date(rand.nextInt(31), rand.nextInt(12), rand.nextInt(2018)));
            c.setFechaFin(new Date(rand.nextInt(31), rand.nextInt(12), rand.nextInt(2018)));
            try 
            {
                createContrato(c);
            } 
            catch (BusinessLogicException e)
            {
                e.printStackTrace();
            }
        }

    }
}
