/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.vivienda.resources;

import co.edu.uniandes.csw.vivienda.dtos.ContratoDTO;
import co.edu.uniandes.csw.vivienda.ejb.ContratoLogic;
import co.edu.uniandes.csw.vivienda.ejb.ViviendaContratosLogic;
import co.edu.uniandes.csw.vivienda.entities.ContratoEntity;
import co.edu.uniandes.csw.vivienda.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.vivienda.mappers.BusinessLogicExceptionMapper;
import co.edu.uniandes.csw.vivienda.mappers.WebApplicationExceptionMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
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
    
    private static final Logger LOGGER = Logger.getLogger(ViviendaContratosResource.class.getName());

    @Inject
    private ViviendaContratosLogic viviendaContratosLogic; // Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.

    @Inject
    private ContratoLogic contratoLogic; // Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.

    
    
    
    /**
     * Guarda un contrato dentro de una vivienda con la informacion que recibe el
     * la URL. Se devuelve el contrato que se guarda en la vivienda.
     *
     * @param viviendaId Identificador de la vivienda que se esta
     * actualizando. Este debe ser una cadena de dígitos.
     * @param contratoId Identificador del contrato que se desea guardar. Este debe
     * ser una cadena de dígitos.
     * @return JSON {@link BookDTO} - El contrato guardado en la vivienda.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el contrato.
     */
    @POST
    @Path("{contratoId: \\d+}")
    public ContratoDTO addContrato(@PathParam("viviendaId") Long viviendaId, @PathParam("contratoId") Long contratoId) {
        LOGGER.log(Level.INFO, "VivivendaContratosResource addContrato: input: contratoID: {0} , viviendaId: {1}", new Object[]{viviendaId, contratoId});
        if (contratoLogic.getContrato(contratoId) == null) {
            throw new WebApplicationException("El recurso /contratos/" + contratoId + " no existe.", 404);
        }
        ContratoDTO contratoDTO = new ContratoDTO(viviendaContratosLogic.addContrato(contratoId, viviendaId));
        LOGGER.log(Level.INFO, "VivivendaContratosResource addContrato: output: {0}", contratoDTO.toString());
        return contratoDTO;
    }
    
    /**
     * Guarda un contrato dentro de una vivienda con la informacion que recibe el
     * la URL. Se devuelve el contrato que se guarda en la vivienda.
     *
     * @param viviendaId Identificador de la vivienda que se esta
     * actualizando. Este debe ser una cadena de dígitos.
     * @param contrato
     * @return JSON {@link BookDTO} - El contrato guardado en la vivienda.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el contrato.
     */
    @POST
    public ContratoDTO createContrato(@PathParam("viviendaId") Long viviendaId, ContratoDTO contrato) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "EstudianteResource createEstudiante: input: {0}", contrato.toString());
        ContratoDTO nuevoContrato = new ContratoDTO(contratoLogic.createContrato(contrato.toEntity()));
        LOGGER.log(Level.INFO, "EstudianteResource createEstudiante: output: {0}", nuevoContrato.toString());
        return nuevoContrato;
    }

    /**
     * Busca y devuelve todos los contratos que existen en la vivienda.
     *
     * @param viviendaId Identificador de la vivienda que se esta buscando.
     * Este debe ser una cadena de dígitos.
     * @return JSONArray {@link ContratoDTO} - Los contratos encontrados en la
     * vivienda. Si no hay ninguno retorna una lista vacía.
     */
    @GET
    public List<ContratoDTO> getContratos(@PathParam("viviendaId") Long viviendaId) {
        LOGGER.log(Level.INFO, "ViviendaContratosResource getContratos: input: {0}", viviendaId);
        List<ContratoDTO> listaDetailDTOs = contratosListEntity2DTO(viviendaContratosLogic.getContratos(viviendaId));
        LOGGER.log(Level.INFO, "ViviendaContratosResource getContratos: output: {0}", listaDetailDTOs.toString());
        return listaDetailDTOs;
    }

    /**
     * Busca el contrato con el id asociado dentro de la vivienda con id asociado.
     *
     * @param viviendaId Identificador de la vivienda que se esta buscando.
     * Este debe ser una cadena de dígitos.
     * @param contratoId Identificador del contrato que se esta buscando. Este debe
     * ser una cadena de dígitos.
     * @return JSON {@link ContratoDTO} - El contrato buscado
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el contrato en la
     * vivienda.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el contrato.
     */
    @GET
    @Path("{contratoId: \\d+}")
    public ContratoDTO getContrato(@PathParam("viviendaId") Long viviendaId, @PathParam("contratoId") Long contratoId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "ViviendaContratosResource getContrato: input: viviendaID: {0} , contratoId: {1}", new Object[]{viviendaId, contratoId});
        if (contratoLogic.getContrato(contratoId) == null) {
            throw new WebApplicationException("El recurso /viviendas/" + viviendaId + "/contratos/" + contratoId + " no existe.", 404);
        }
        ContratoDTO contratoDTO = new ContratoDTO(viviendaContratosLogic.getContrato(viviendaId, contratoId));
        LOGGER.log(Level.INFO, "ViviendaContratosResource getContrato: output: {0}", contratoDTO.toString());
        return contratoDTO;
    }

    /**
     * Remplaza las instancias de Contrato asociadas a una instancia de Vivienda
     *
     * @param viviendaId Identificador de la vivienda que se esta
     * remplazando. Este debe ser una cadena de dígitos.
     * @param contratos JSONArray {@link ContratoDTO} El arreglo de contratos nuevo para la
     * vivienda.
     * @return JSON {@link ContratoDTO} - El arreglo de contratos guardado en la
     * vivienda.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el contrato.
     */
    @PUT
    public List<ContratoDTO> replaceContratos(@PathParam("viviendaId") Long viviendaId, List<ContratoDTO> contratos) {
        LOGGER.log(Level.INFO, "ViviendaContratosResource replaceContratos: input: viviendaId: {0} , contratos: {1}", new Object[]{viviendaId, contratos.toString()});
        for (ContratoDTO contrato : contratos) {
            if (contratoLogic.getContrato(contrato.getId()) == null) {
                throw new WebApplicationException("El recurso /contratos/" + contrato.getId() + " no existe.", 404);
            }
        }
        List<ContratoDTO> listaDetailDTOs = contratosListEntity2DTO(viviendaContratosLogic.replaceContratos(viviendaId, contratosListDTO2Entity(contratos)));
        LOGGER.log(Level.INFO, "ViviendaContratosResource replaceContratos: output: {0}", listaDetailDTOs.toString());
        return listaDetailDTOs;
    }

    /**
     * Convierte una lista de ContratoEntity a una lista de ContratoDTO.
     *
     * @param entityList Lista de ContratoEntity a convertir.
     * @return Lista de ContratoDTO convertida.
     */
    private List<ContratoDTO> contratosListEntity2DTO(List<ContratoEntity> entityList) 
    {
        List<ContratoDTO> list = new ArrayList();
        for (ContratoEntity entity : entityList)
        {
            list.add(new ContratoDTO(entity));
        }
        return list;
    }

    /**
     * Convierte una lista de ContratoDTO a una lista de ContratoEntity.
     * 
     * @param dtos Lista de ContratoDTO a convertir.
     * @return Lista de ContratoEntity convertida.
     */
    private List<ContratoEntity> contratosListDTO2Entity(List<ContratoDTO> dtos) {
        List<ContratoEntity> list = new ArrayList<>();
        for (ContratoDTO dto : dtos) {
            list.add(dto.toEntity());
        }
        return list;
    }
    
    /**
     * Actualiza un contrato dentro de una vivienda
     * @param viviendaId - Identificador de la vivienda
     * @param contratoId - Identificador del contrato de la vivienda
     * @param contratoDTO - DTO del contrato a actualizar
     * @return Contrato actualizado
     * @throws BusinessLogicException En el caso que no pueda actualizar
     */
    @PUT
    @Path("{contratoId:\\d+}")
    public ContratoDTO updateContrato(@PathParam("viviendaId") Long viviendaId, @PathParam("contratoId") Long contratoId, ContratoDTO contratoDTO) throws BusinessLogicException{
        ContratoEntity contratoEntity = contratoLogic.actualizarContrato(viviendaId, contratoId, contratoDTO.toEntity());
        return new ContratoDTO(contratoEntity);
    }

    @DELETE
    @Path("{contratoId:\\d+}")
    public void deleteContrato(@PathParam("viviendaId") Long viviendaId, @PathParam("contratoId") Long contratoId) throws BusinessLogicException{
        contratoLogic.deleteContrato(contratoId);
    }
}
