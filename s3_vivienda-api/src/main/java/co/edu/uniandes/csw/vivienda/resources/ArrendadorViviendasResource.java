/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.vivienda.resources;

//import co.edu.uniandes.csw.vivienda.ejb.ViviendaLogic;

import co.edu.uniandes.csw.vivienda.dtos.ViviendaDTO;
import co.edu.uniandes.csw.vivienda.dtos.ViviendaDetailDTO;
import co.edu.uniandes.csw.vivienda.ejb.ArrendadorViviendasLogic;
import co.edu.uniandes.csw.vivienda.ejb.ViviendaLogic;
import co.edu.uniandes.csw.vivienda.entities.ViviendaEntity;
import co.edu.uniandes.csw.vivienda.exceptions.BusinessLogicException;
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


/**
 * Implementa el recurso "arrendadores/{id}/viviendas"
 * @author msalcedo
 */
@Consumes("application/json")
@Produces("application/json")
public class ArrendadorViviendasResource {
    
    private static final Logger LOGGER = Logger.getLogger(ArrendadorViviendasResource.class.getName());
    @Inject
    private ArrendadorViviendasLogic arrendadorViviendasLogic; // Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.

    @Inject
    private ViviendaLogic viviendasLogic; // Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.
   
     /**
     * Busca y devuelve todas las viviendas que existen en el arrendador.
     *
     * @param arrendadorId Identificador del arrendador que se esta buscando.
     * Este debe ser una cadena de dígitos.
     * @return JSONArray {@link ViviendaDetailDTO} - Las viviendas encontrados en la
     * arrendador. Si no hay ninguno retorna una lista vacía.
     */
    @GET
    public List<ViviendaDetailDTO> getViviendas(@PathParam("arrendadorId")Long arrendadorId)
    {
        LOGGER.log(Level.INFO, "ArrendadorViviendasResource getViviendas: input: {0}", arrendadorId);
        List<ViviendaDetailDTO> listaDetailDTOs = viviendasListEntity2DTO(arrendadorViviendasLogic.getViviendas(arrendadorId));
        LOGGER.log(Level.INFO, "ArrendadorViviendasResource getViviendas: output: {0}", listaDetailDTOs.toString());
        return listaDetailDTOs;
    }
    
        /**
     * Guarda una vivienda dentro de un arrendador con la informacion que recibe el
     * la URL. Se devuelve la vivienda que se guarda en el arrendador.
     *
     * @param arrendadorId Identificador del arrendador que se esta
     * actualizando. Este debe ser una cadena de dígitos.
     * @param viviendaId Identificador de la vivienda que se desea guardar. Este debe
     * ser una cadena de dígitos.
     * @return JSON {@link ViviendaDTO} - La vivienda guardada en el arrendador.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la vivienda.
     */
    @POST
    @Path("{viviendaId: \\d+}")
    public ViviendaDTO addVivienda(@PathParam("arrendadorId") Long arrendadorId, @PathParam("viviendaId") Long viviendaId) {
        LOGGER.log(Level.INFO, "ArrendadorViviendasResource addVivienda: input: arrendadorID: {0} , viviendaId: {1}", new Object[]{arrendadorId, viviendaId});
        if (viviendasLogic.getVivienda(viviendaId) == null) {
            throw new WebApplicationException("El recurso /viviendas/" + viviendaId + " no existe.", 404);
        }
        ViviendaDTO viviendaDTO = new ViviendaDTO(arrendadorViviendasLogic.addVivienda(viviendaId, arrendadorId));
        LOGGER.log(Level.INFO, "ArrendadorViviendasResource addVivienda: output: {0}", viviendaDTO.toString());
        return viviendaDTO;
    }
    
     /**
     * Busca la vivienda con el id asociado dentro del arrendador con id asociado.
     *
     * @param arrendadorId Identificador del arrendador que se esta buscando.
     * Este debe ser una cadena de dígitos.
     * @param viviendaId Identificador de la vivienda que se esta buscando. Este debe
     * ser una cadena de dígitos.
     * @return JSON {@link ViviendaDetailDTO} - la vivienda buscada
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la vivienda.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la vivienda en el arrendador.
     */
    @GET
    @Path("{viviendaId: \\d+}")
    public ViviendaDetailDTO getVivienda(@PathParam("arrendadorId") Long arrendadorId, @PathParam("viviendaId") Long viviendaId) throws BusinessLogicException {
        
        LOGGER.log(Level.INFO, "ArrendadorViviendasResource getVIvienda: input: arrendadorID: {0} , viviendaId: {1}", new Object[]{arrendadorId, viviendaId});
        if (viviendasLogic.getVivienda(viviendaId) == null) {
            throw new WebApplicationException("El recurso /arrendadores/" + arrendadorId + "/viviendas/" + viviendaId + " no existe.", 404);
        }
        ViviendaDetailDTO viviendaDetailDTO = new ViviendaDetailDTO(arrendadorViviendasLogic.getVivienda(arrendadorId, viviendaId));
        LOGGER.log(Level.INFO, "ArrendadorViviendasResource getVivienda: output: {0}", viviendaDetailDTO.toString());        
        return viviendaDetailDTO;
    }
    
        /**
     * Remplaza las instancias de Vivienda asociadas a una instancia de Arrendador
     *
     * @param arrendadorId Identificador del arrendador que se esta
     * remplazando. Este debe ser una cadena de dígitos.
     * @param viviendas JSONArray {@link ViviendaDTO} El arreglo de viviendas nuevo para el arrendador.
     * @return JSON {@link ViviendaDTO} - El arreglo de viviendas guardado en el arrendador.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la vivienda.
     */
    @PUT
    public List<ViviendaDetailDTO> replaceViviendas(@PathParam("arrendadorId") Long arrendadorId, List<ViviendaDetailDTO> viviendas) {
        
        LOGGER.log(Level.INFO, "ArrendadorViviendasResource replaceViviendas: input: arrendadorId: {0} , viviendas: {1}", new Object[]{arrendadorId, viviendas.toString()});
        for (ViviendaDetailDTO vivienda : viviendas) {
            if (viviendasLogic.getVivienda(vivienda.getId()) == null) {
                throw new WebApplicationException("El recurso /viviendas/" + vivienda.getId() + " no existe.", 404);
            }
        }
        List<ViviendaDetailDTO> listaDetailDTOs = viviendasListEntity2DTO(arrendadorViviendasLogic.replaceViviendas(arrendadorId, viviendaListDTO2Entity(viviendas)));
           LOGGER.log(Level.INFO, "ArrendadorViviendasResource replaceViviendas: output: {0}", listaDetailDTOs.toString());
        return listaDetailDTOs;
    }
    
     private List<ViviendaDetailDTO> viviendasListEntity2DTO(List<ViviendaEntity> entityList) {
        List<ViviendaDetailDTO> list = new ArrayList();
        for (ViviendaEntity entity : entityList) {
            list.add(new ViviendaDetailDTO(entity));
        }
        return list;
    }
     
    private List<ViviendaEntity> viviendaListDTO2Entity(List<ViviendaDetailDTO> dtos) {
        List<ViviendaEntity> list = new ArrayList<>();
        for (ViviendaDetailDTO dto : dtos) {
            list.add(dto.toEntity());
        }
        return list;
    }
}