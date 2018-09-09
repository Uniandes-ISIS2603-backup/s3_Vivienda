/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.vivienda.resources;

import co.edu.uniandes.csw.vivienda.dtos.ContratoDTO;
import co.edu.uniandes.csw.vivienda.ejb.ContratoLogic;
import co.edu.uniandes.csw.vivienda.ejb.ViviendaContratosLogic;
import co.edu.uniandes.csw.vivienda.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.vivienda.mappers.BusinessLogicExceptionMapper;
import co.edu.uniandes.csw.vivienda.mappers.WebApplicationExceptionMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;

/**
 * Implementa el recurso viviendas/{id}/contratos
 * @author estudiante
 */
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ViviendaContratosResource {
    
//    private static final Logger LOGGER = Logger.getLogger(ViviendaContratosResource.class.getName());
//
//    @Inject
//    private ViviendaContratosLogic viviendaContratosLogic; // Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.
//
//    @Inject
//    private ContratoLogic contratoLogic; // Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.
//
//    /**
//     * Guarda un contrato dentro de una vivienda con la informacion que recibe el
//     * la URL. Se devuelve el contrato que se guarda en la vivienda.
//     *
//     * @param viviendaId Identificador de la vivienda que se esta
//     * actualizando. Este debe ser una cadena de dígitos.
//     * @param contratoId Identificador del contrato que se desea guardar. Este debe
//     * ser una cadena de dígitos.
//     * @return JSON {@link BookDTO} - El contrato guardado en la vivienda.
//     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
//     * Error de lógica que se genera cuando no se encuentra el contrato.
//     */
//    @POST
//    @Path("{contratoId: \\d+}")
//    public ContratoDTO addContrato(@PathParam("viviendaId") Long viviendaId, @PathParam("contratoId") Long contratoId) {
//        LOGGER.log(Level.INFO, "VivivendaContratosResource addContrato: input: contratoID: {0} , viviendaId: {1}", new Object[]{viviendaId, contratoId});
//        if (contratoLogic.getContrato(contratoId) == null) {
//            throw new WebApplicationException("El recurso /contratos/" + contratoId + " no existe.", 404);
//        }
//        ContratoDTO contratoDTO = new ContratoDTO(viviendaContratosLogic.addContrato(contratoId, viviendaId));
//        LOGGER.log(Level.INFO, "EditorialBooksResource addBook: output: {0}", contratoDTO.toString());
//        return contratoDTO;
//    }
//
//    /**
//     * Busca y devuelve todos los contratos que existen en la vivienda.
//     *
//     * @param viviendaId Identificador de la vivienda que se esta buscando.
//     * Este debe ser una cadena de dígitos.
//     * @return JSONArray {@link ContratoDTO} - Los libros encontrados en la
//     * vivienda. Si no hay ninguno retorna una lista vacía.
//     */
//    @GET
//    public List<ContratoDTO> getBooks(@PathParam("editorialsId") Long viviendaId) {
//        LOGGER.log(Level.INFO, "ViviendaContratosResource getContratos: input: {0}", viviendaId);
//        List<ContratoDTO> listaDetailDTOs = contratosListEntity2DTO(viviendaContratosLogic.getContratos(viviendaId));
//        LOGGER.log(Level.INFO, "ViviendaContratosResource getContratos: output: {0}", listaDetailDTOs.toString());
//        return listaDetailDTOs;
//    }
//
//    /**
//     * Busca el contrato con el id asociado dentro de la vivienda con id asociado.
//     *
//     * @param viviendaId Identificador de la vivienda que se esta buscando.
//     * Este debe ser una cadena de dígitos.
//     * @param contratoId Identificador del contrato que se esta buscando. Este debe
//     * ser una cadena de dígitos.
//     * @return JSON {@link ContratoDTO} - El contrato buscado
//     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
//     * Error de lógica que se genera cuando no se encuentra el contrato en la
//     * vivienda.
//     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
//     * Error de lógica que se genera cuando no se encuentra el contrato.
//     */
//    @GET
//    @Path("{contratoId: \\d+}")
//    public ContratoDTO getBook(@PathParam("viviendaId") Long viviendaId, @PathParam("contratoId") Long contratoId) throws BusinessLogicException {
//        LOGGER.log(Level.INFO, "ViviendaContratosResource getContrato: input: viviendaID: {0} , contratoId: {1}", new Object[]{viviendaId, contratoId});
//        if (contratoLogic.getContrato(contratoId) == null) {
//            throw new WebApplicationException("El recurso /viviendas/" + viviendaId + "/contratos/" + contratoId + " no existe.", 404);
//        }
//        ContratoDTO contratoDTO = new ContratoDTO(viviendaContratosLogic.getContrato(viviendaId, contratoId));
//        LOGGER.log(Level.INFO, "ViviendaContratosResource getContrato: output: {0}", contratoDTO.toString());
//        return contratoDTO;
//    }
//
//    /**
//     * Remplaza las instancias de Contrato asociadas a una instancia de Vivienda
//     *
//     * @param editorialsId Identificador de la editorial que se esta
//     * remplazando. Este debe ser una cadena de dígitos.
//     * @param books JSONArray {@link BookDTO} El arreglo de libros nuevo para la
//     * editorial.
//     * @return JSON {@link BookDTO} - El arreglo de libros guardado en la
//     * editorial.
//     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
//     * Error de lógica que se genera cuando no se encuentra el libro.
//     */
//    @PUT
//    public List<BookDTO> replaceBooks(@PathParam("editorialsId") Long editorialsId, List<BookDTO> books) {
//        LOGGER.log(Level.INFO, "EditorialBooksResource replaceBooks: input: editorialsId: {0} , books: {1}", new Object[]{editorialsId, books.toString()});
//        for (BookDTO book : books) {
//            if (contratoLogic.getBook(book.getId()) == null) {
//                throw new WebApplicationException("El recurso /books/" + book.getId() + " no existe.", 404);
//            }
//        }
//        List<BookDTO> listaDetailDTOs = contratosListEntity2DTO(viviendaContratosLogic.replaceBooks(editorialsId, booksListDTO2Entity(books)));
//        LOGGER.log(Level.INFO, "EditorialBooksResource replaceBooks: output: {0}", listaDetailDTOs.toString());
//        return listaDetailDTOs;
//    }
//
//    /**
//     * Convierte una lista de BookEntity a una lista de BookDTO.
//     *
//     * @param entityList Lista de BookEntity a convertir.
//     * @return Lista de BookDTO convertida.
//     */
//    private List<BookDTO> contratosListEntity2DTO(List<BookEntity> entityList) {
//        List<BookDTO> list = new ArrayList();
//        for (BookEntity entity : entityList) {
//            list.add(new BookDTO(entity));
//        }
//        return list;
//    }
//
//    /**
//     * Convierte una lista de BookDTO a una lista de BookEntity.
//     * 
//     * @param dtos Lista de BookDTO a convertir.
//     * @return Lista de BookEntity convertida.
//     */
//    private List<BookEntity> booksListDTO2Entity(List<BookDTO> dtos) {
//        List<BookEntity> list = new ArrayList<>();
//        for (BookDTO dto : dtos) {
//            list.add(dto.toEntity());
//        }
//        return list;
//    }
}
