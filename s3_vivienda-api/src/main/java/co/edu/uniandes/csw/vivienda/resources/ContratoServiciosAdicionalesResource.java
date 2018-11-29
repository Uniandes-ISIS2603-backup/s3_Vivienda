/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.vivienda.resources;


import co.edu.uniandes.csw.vivienda.dtos.ServicioAdicionalDetailDTO;
import co.edu.uniandes.csw.vivienda.ejb.ContratoServiciosAdicionalesLogic;
import co.edu.uniandes.csw.vivienda.ejb.ServicioAdicionalLogic;
import co.edu.uniandes.csw.vivienda.entities.ServicioAdicionalEntity;
import co.edu.uniandes.csw.vivienda.mappers.*;
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
 *
 * @author Paula Molina Ruiz
 */
@Path("contratoServiciosAdicionales")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ContratoServiciosAdicionalesResource {
    
private static final Logger LOGGER = Logger.getLogger(ContratoServiciosAdicionalesResource.class.getName());
private static final String NO_EXISTE = " no existe.";

    @Inject
    private ContratoServiciosAdicionalesLogic contratoServiciosAdicionalesLogic;

    @Inject
    private ServicioAdicionalLogic servicioAdicionalLogic;

    /**
     * Asocia un servicio adiconal existente con un contrato existente
     *
     * @param servicioAdicionalId El ID del servicio adicional que se va a asociar
     * @param viviendaId El ID de la vivienda que ofrece el servicio adicional 
     * @param contratoId El ID del contrato al cual se le va a asociar el servicio adicional
     * @return JSON {@link ServicioAdicionalDetailDTO} - El autor asociado.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el servicio adicional.
     */
    @POST
    @Path("viviendas/{viviendaId: \\d+}/contratos/{contratoId: \\d+}/serviciosAdicionales/{servicioAdicionalId: \\d+}")
    public ServicioAdicionalDetailDTO addServicioAdicional(@PathParam("contratoId") Long contratoId, @PathParam("viviendaId") Long viviendaId, @PathParam("servicioAdicionalId") Long servicioAdicionalId) {
        LOGGER.log(Level.INFO, "ContratoServiciosAdicionalesResource addServicioAdicional: input: contratoId {0} , viviendaId: {1} , servicioAdicionalId {2}", new Object[]{contratoId, viviendaId, servicioAdicionalId});
        if (servicioAdicionalLogic.getServicioAdicional(viviendaId, servicioAdicionalId) == null) {
            throw new WebApplicationException("El recurso /serviciosAdicionales/" + servicioAdicionalId + NO_EXISTE, 404);
        }
        ServicioAdicionalDetailDTO detailDTO = new ServicioAdicionalDetailDTO(contratoServiciosAdicionalesLogic.addServicioAdicional(contratoId, servicioAdicionalId, viviendaId));
        LOGGER.log(Level.INFO, "ContratoServiciosAdicionalesResource addServicioAdicional: output: {0}", detailDTO);
        return detailDTO;
    }

    /**
     * Busca y devuelve todos los servicios adicionales que existen en un contrato.
     *
     * @param contratoId El ID del libro del cual se buscan los autores
     * @return JSONArray {@link ServicioAdicionalDetailDTO} - Los servicios adicoinales encontrados en el
     * contrato. Si no hay ninguno retorna una lista vacía.
     */
    @GET
    @Path("contratos/{contratoId: \\d+}")
    public List<ServicioAdicionalDetailDTO> getServiciosAdicionales(@PathParam("contratoId") Long contratoId) {
        LOGGER.log(Level.INFO, "ContratoServiciosAdicionalesResource getServiciosAdicionales: input: {0}", contratoId);
        List<ServicioAdicionalDetailDTO> lista = serviciosAdicionalesListEntity2DTO(contratoServiciosAdicionalesLogic.getServiciosAdicionales(contratoId));
        LOGGER.log(Level.INFO, "ContratoServiciosAdicionalesResource getServiciosAdicionales: output: {0}", lista);
        return lista;
    }

    /**
     * Busca y devuelve el servicio adicional con el ID recibido en la URL, relativo a un
     * contrato.
     *
     * @param servicioAdicionalId El ID del servicio adicional que se busca
     * @param viviendaId El ID de la vivienda que ofrece el servicio adicional 
     * @param contratoId El ID del libro del cual se busca el autor
     * @return {@link ServicioAdicionalDetailDTO} - El autor encontrado en el libro.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper}
     * Error de lógica que se genera cuando no se encuentra el autor.
     */
    @GET
    @Path("viviendas/{viviendaId: \\d+}/contratos/{contratoId: \\d+}/serviciosAdicionales/{servicioAdicionalId: \\d+}")
    public ServicioAdicionalDetailDTO getServicioAdicional(@PathParam("contratoId") Long contratoId, @PathParam("servicioAdicionalId") Long servicioAdicionalId, @PathParam("viviendaId") Long viviendaId ) {
        LOGGER.log(Level.INFO, "ContratoServiciosAdicionalesResource getServicioAdicional: input: booksId {0} , viviendaId: {0} , authorsId {1}", new Object[]{contratoId, servicioAdicionalId});
        if (servicioAdicionalLogic.getServicioAdicional( viviendaId ,servicioAdicionalId) == null) {
            throw new WebApplicationException("El recurso /authors/" + servicioAdicionalId + NO_EXISTE, 404);
        }
        ServicioAdicionalDetailDTO detailDTO = new ServicioAdicionalDetailDTO(contratoServiciosAdicionalesLogic.getServicioAdicional(contratoId, servicioAdicionalId, viviendaId));
        LOGGER.log(Level.INFO, "ContratoServiciosAdicionalesResource getServicioAdicional: output: {0}", detailDTO);
        return detailDTO;
    }

    /**
     * Actualiza la lista de servicios adicionales de un libro con la lista que se recibe en
     * el cuerpo.
     *
     * @param contratoId El ID del contrato al cual se le va a asociar la lista de servicios adicionales
     * @param viviendaId El ID de la vivienda que ofrece el servicio adicional 
     * @param serviciosAdicionales JSONArray {@link ServicioAdicionalDetailDTO} - La lista de servicios adicionales
     * que se desea guardar.
     * @return JSONArray {@link ServicioAdicionalDetailDTO} - La lista actualizada.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper}
     * Error de lógica que se genera cuando no se encuentra el servicio adicional.
     */ 
    @PUT
    @Path("viviendas/{viviendaId: \\d+}/contratos/{contratoId: \\d+}")
    public List<ServicioAdicionalDetailDTO> replaceServiciosAdicionales(@PathParam("contratoId") Long contratoId, @PathParam("viviendaId") Long viviendaId, List<ServicioAdicionalDetailDTO> serviciosAdicionales) {
        LOGGER.log(Level.INFO, "ContratoServiciosAdicionalesResource replaceServiciosAdicionales: input: contratoId {0} , viviendaId {1} , servicios {2}", new Object[]{contratoId, viviendaId, serviciosAdicionales});
        for (ServicioAdicionalDetailDTO servicioAdicional : serviciosAdicionales) {
            if (servicioAdicionalLogic.getServicioAdicional( servicioAdicional.getVivienda().getId(), servicioAdicional.getId()) == null) {
                throw new WebApplicationException("El recurso /serviciosAdicionales/" + servicioAdicional.getId() + NO_EXISTE, 404);
            }
        }
        List<ServicioAdicionalDetailDTO> lista = serviciosAdicionalesListEntity2DTO(contratoServiciosAdicionalesLogic.replaceServiciosAdicionales(contratoId, serviciosAdicionalesListDTO2Entity(serviciosAdicionales)));
        LOGGER.log(Level.INFO, "ContratoServiciosAdicionalesResource replaceServiciosAdicionales: output:{0}", lista);
        return lista;
    }

    /**
     * Elimina la conexión entre el servicio adicional y el contrato recibidos en la URL.
     *
     * @param contratoId El ID del contrato al cual se le va a desasociar el servicio adicional
     * @param viviendaId El ID de la vivienda que ofrece el servicio adicional 
     * @param servicioAdicionalId El ID del servicio adicional que se desasocia
     * @throws WebApplicationException {@link WebApplicationExceptionMapper}
     * Error de lógica que se genera cuando no se encuentra el servicio adicional.
     */
    @DELETE
    @Path("viviendas/{viviendaId: \\d+}/contratos/{contratoId: \\d+}/serviciosAdicionales/{servicioAdicionalId: \\d+}")
    public void removeServicioAdicional(@PathParam("contratoId") Long contratoId, @PathParam("servicioAdicionalId") Long servicioAdicionalId, @PathParam("viviendaId") Long viviendaId) {
        LOGGER.log(Level.INFO, "ContratoServiciosAdicionalesResource removeServicioAdicional: input: contratoId {0} , viviendaId {1} , servicioAdicionalId {2}", new Object[]{contratoId, viviendaId, servicioAdicionalId});
        if (servicioAdicionalLogic.getServicioAdicional(viviendaId ,servicioAdicionalId) == null) {
            throw new WebApplicationException("El recurso /authors/" + servicioAdicionalId + NO_EXISTE, 404);
        }
        contratoServiciosAdicionalesLogic.removeServicioAdicional(contratoId, servicioAdicionalId, viviendaId);
        LOGGER.info("ContratoServiciosAdicionalesResource removeServicioAdicional: output: void");
    }

    /**
     * Convierte una lista de ServicioAdicionalEntity a una lista de ServicioAdicionalDetailDTO.
     *
     * @param entityList Lista de ServicioAdicionalEntity a convertir.
     * @return Lista de ServicioAdicionalDetailDTO convertida.
     */
    private List<ServicioAdicionalDetailDTO> serviciosAdicionalesListEntity2DTO(List<ServicioAdicionalEntity> entityList) {
        List<ServicioAdicionalDetailDTO> list = new ArrayList<>();
        for (ServicioAdicionalEntity entity : entityList) {
            list.add(new ServicioAdicionalDetailDTO(entity));
        }
        return list;
    }

    /**
     * Convierte una lista de ServicioAdicionalDetailDTO a una lista de ServicioAdicionalEntity.
     *
     * @param dtos Lista de ServicioAdicionalDetailDTO a convertir.
     * @return Lista de ServicioAdicionalEntity convertida.
     */
    private List<ServicioAdicionalEntity> serviciosAdicionalesListDTO2Entity(List<ServicioAdicionalDetailDTO> dtos) {
        List<ServicioAdicionalEntity> list = new ArrayList<>();
        for (ServicioAdicionalDetailDTO dto : dtos) {
            list.add(dto.toEntity());
        }
        return list;
    }
}
