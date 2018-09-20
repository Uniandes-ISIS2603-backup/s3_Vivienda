/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.vivienda.ejb;

import co.edu.uniandes.csw.vivienda.entities.ContratoEntity;
import co.edu.uniandes.csw.vivienda.entities.ServicioAdicionalEntity;
import co.edu.uniandes.csw.vivienda.persistence.ContratoPersistence;
import co.edu.uniandes.csw.vivienda.persistence.ServicioAdicionalPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * Clase que implementa la conexion con la persistencia para la relación entre
 * la entidad de Editorial y Book.
 *
 * @author Paula Molina Ruiz
 */
@Stateless
public class ContratoServiciosAdicionalesLogic {
    
       private static final Logger LOGGER = Logger.getLogger(ContratoServiciosAdicionalesLogic.class.getName());

    @Inject
    private ServicioAdicionalPersistence servicioAdicionalPersistence;

    @Inject
    private ContratoPersistence contratoPersistence;

    /**
     * Asocia un Author existente a un Book
     *
     * @param contratoId Identificador de la instancia de Book
     * @param viviendaId Identificador de la instancia de Book
     * @param servicioAdicionalId Identificador de la instancia de Author
     * @return Instancia de AuthorEntity que fue asociada a Book
     */
    public ServicioAdicionalEntity addServicioAdicional(Long contratoId, Long servicioAdicionalId, Long viviendaId) {
        LOGGER.log(Level.INFO, "Inicia proceso de asociarle un autor al libro con id = {0}", contratoId);
        ServicioAdicionalEntity authorEntity = servicioAdicionalPersistence.find(viviendaId, servicioAdicionalId);
        ContratoEntity bookEntity = contratoPersistence.find(contratoId);
        bookEntity.getServiciosAdicionalesAgregados().add(authorEntity);
        LOGGER.log(Level.INFO, "Termina proceso de asociarle un autor al libro con id = {0}", contratoId);
        return servicioAdicionalPersistence.find(viviendaId, servicioAdicionalId);
    }

    /**
     * Obtiene una colección de instancias de AuthorEntity asociadas a una
     * instancia de Book
     *
     * @param contratoId Identificador de la instancia de Book
     * @return Colección de instancias de AuthorEntity asociadas a la instancia
     * de Book
     */
    public List<ServicioAdicionalEntity> getServiciosAdicionales(Long contratoId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todos los autores del libro con id = {0}", contratoId);
        return contratoPersistence.find(contratoId).getServiciosAdicionalesAgregados();
    }

    /**
     * Obtiene una instancia de AuthorEntity asociada a una instancia de Book
     *
     * @param contratoId Identificador de la instancia de Book
     * @param viviendaId Identificador de la instancia de Book
     * @param servicioAdicionalId Identificador de la instancia de Author
     * @return La entidad del Autor asociada al libro
     */
    public ServicioAdicionalEntity getServicioAdicional(Long contratoId, Long servicioAdicionalId, Long viviendaId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar un autor del libro con id = {0}", contratoId);
        List<ServicioAdicionalEntity> authors = contratoPersistence.find(contratoId).getServiciosAdicionalesAgregados();
        ServicioAdicionalEntity authorEntity = servicioAdicionalPersistence.find(viviendaId, servicioAdicionalId);
        int index = authors.indexOf(authorEntity);
        LOGGER.log(Level.INFO, "Termina proceso de consultar un autor del libro con id = {0}", contratoId);
        if (index >= 0) {
            return authors.get(index);
        }
        return null;
    }

    /**
     * Remplaza las instancias de Author asociadas a una instancia de Book
     *
     * @param contratoId Identificador de la instancia de Book
     * @param list Colección de instancias de AuthorEntity a asociar a instancia
     * de Book
     * @return Nueva colección de AuthorEntity asociada a la instancia de Book
     */
    public List<ServicioAdicionalEntity> replaceServiciosAdicionales(Long contratoId, List<ServicioAdicionalEntity> list) {
        LOGGER.log(Level.INFO, "Inicia proceso de reemplazar los autores del libro con id = {0}", contratoId);
        ContratoEntity bookEntity = contratoPersistence.find(contratoId);
        bookEntity.setServiciosAdicionalesAgregados(list);
        LOGGER.log(Level.INFO, "Termina proceso de reemplazar los autores del libro con id = {0}", contratoId);
        return contratoPersistence.find(contratoId).getServiciosAdicionalesAgregados();
    }

    /**
     * Desasocia un Author existente de un Book existente
     *
     * @param contratoId Identificador de la instancia de Book
     * @param viviendaId Identificador de la instancia de Book
     * @param servicioAdicionalId Identificador de la instancia de Author
     */
    public void removeServicioAdicional(Long contratoId, Long servicioAdicionalId, Long viviendaId) {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar un autor del libro con id = {0}", contratoId);
        ServicioAdicionalEntity authorEntity = servicioAdicionalPersistence.find(viviendaId, servicioAdicionalId);
        ContratoEntity bookEntity = contratoPersistence.find(contratoId);
        bookEntity.getServiciosAdicionalesAgregados().remove(authorEntity);
        LOGGER.log(Level.INFO, "Termina proceso de borrar un autor del libro con id = {0}", contratoId);
    }
    
}
