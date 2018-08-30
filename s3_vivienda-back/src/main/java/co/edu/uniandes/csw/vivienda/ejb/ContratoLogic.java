/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.vivienda.ejb;

import co.edu.uniandes.csw.vivienda.entities.ContratoEntity;
import co.edu.uniandes.csw.vivienda.exceptions.BusinessLogicException;
//import co.edu.uniandes.csw.vivienda.persistence.ContratoPersistence;
//import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
//import javax.inject.Inject;

/**
 *
 * @author estudiante
 */
@Stateless
public class ContratoLogic {
    
    private static final Logger LOGGER = Logger.getLogger(ContratoLogic.class.getName());

//    @Inject
//    private ContratoPersistence persistence; // Variable para acceder a la persistencia de la aplicación. Es una inyección de dependencias.

    /**
     * Crea un contrato en la persistencia.
     *
     * @param contratoEntity La entidad que representa el contrato a
     * persistir.
     * @return La entiddad del contrato luego de persistirlo.
     * @throws BusinessLogicException Si el contrato a persistir ya existe.
     */
    public ContratoEntity createContrato(ContratoEntity contratoEntity) throws BusinessLogicException {
//        LOGGER.log(Level.INFO, "Inicia proceso de creación de la editorial");
//        // Verifica la regla de negocio que dice que no puede haber dos contratos con el mismo ID
//        if (persistence.findById(contratoEntity.getId()) != null) {
//            throw new BusinessLogicException("Ya existe una Contrato con el id \"" + contratoEntity.getId() + "\"");
//        }
//        // Invoca la persistencia para crear el contrato
//        persistence.create(contratoEntity);
//        LOGGER.log(Level.INFO, "Termina proceso de creación del contrato");
//        return contratoEntity;
        return null;
    }

    /**
     * Borrar un contrato
     *
     * @param contratosId: id del contrato a borrar
     */
    public void deleteEditorial(Long contratosId) {
//        LOGGER.log(Level.INFO, "Inicia proceso de borrar el contrato con id = {0}", contratosId);
//        // Note que, por medio de la inyección de dependencias se llama al método "delete(id)" que se encuentra en la persistencia.
//        persistence.delete(contratosId);
//        LOGGER.log(Level.INFO, "Termina proceso de borrar el contrato con id = {0}", contratosId);
    }
}
