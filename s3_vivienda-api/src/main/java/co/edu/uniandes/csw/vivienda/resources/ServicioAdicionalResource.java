/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.vivienda.resources;

import co.edu.uniandes.csw.vivienda.dtos.ServicioAdicionalDTO;
import co.edu.uniandes.csw.vivienda.ejb.ServicioAdicionalLogic;
import co.edu.uniandes.csw.vivienda.entities.ServicioAdicionalEntity;
import co.edu.uniandes.csw.vivienda.exceptions.BusinessLogicException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.*;
import javax.inject.Inject;
import javax.ws.rs.*;

@Produces("application/json")
@Consumes("application/json")

public class ServicioAdicionalResource {
    
    private static final Logger LOGGER = Logger.getLogger(ServicioAdicionalResource.class.getName());

    @Inject
    private ServicioAdicionalLogic servicioAdicionalLogic;

    /**
     * Crea un nuevo servicio adicional con la informacion que se recibe en el cuerpo de la
     * petición y se regresa un objeto identico con un id auto-generado por la
     * base de datos.
     *
     * @param viviendaId El ID de la vivienda de la cual se le agrega el servicio adicional
     * @param servicio {@link ReviewDTO} - La reseña que se desea guardar.
     * @return JSON {@link ReviewDTO} - La reseña guardada con el atributo id
     * autogenerado.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando ya existe la reseña.
     */
    @POST
    public ServicioAdicionalDTO createServicioAdicional(@PathParam("viviendaId") Long viviendaId, ServicioAdicionalDTO servicio) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "ServicioAdicionalResource createServicioAdicional: input: {0}", servicio.toString());
        ServicioAdicionalDTO nuevoReviewDTO = new ServicioAdicionalDTO(servicioAdicionalLogic.createServicioAdicional(viviendaId, servicio.toEntity()));
        LOGGER.log(Level.INFO, "ServicioAdicionalResource createServicioAdicional: output: {0}", nuevoReviewDTO.toString());
        return nuevoReviewDTO;
    }

    /**
     * Busca y devuelve todos los servicios adicionales que existen en una vivienda.
     *
     * @param viviendaId El ID del libro del cual se buscan las reseñas
     * @return JSONArray {@link ReviewDTO} - Las reseñas encontradas en el
     * libro. Si no hay ninguna retorna una lista vacía.
     */
    @GET
    public List<ServicioAdicionalDTO> getServiciosAdicionales(@PathParam("viviendaId") Long viviendaId) {
        LOGGER.log(Level.INFO, "ServicioAdicionalResource getSericiosAdicionales: input: {0}", viviendaId);
        List<ServicioAdicionalDTO> listaDTOs = listEntity2DTO(servicioAdicionalLogic.getServiciosAdicionales(viviendaId));
        LOGGER.log(Level.INFO, "ViviendaResource getViviendas: output: {0}", listaDTOs.toString());
        return listaDTOs;
    }

    /**
     * Busca y devuelve el servicio adicional con el ID recibido en la URL, relativa a una
     * vivienda.
     *
     * @param viviendaId El ID de la vivienda de la cual se buscan los servicios adicionales.
     * @param servicioAdicionalId El ID del servicio adicional que se busca
     * @return {@link ReviewDTO} - El servicio adicional encontrado en la vivienda.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la vivienda.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el servicio adicional.
     */
    @GET
    @Path("{servicioAdicionalId: \\d+}")
    public ServicioAdicionalDTO getServicioAdicional(@PathParam("viviendaId") Long viviendaId, @PathParam("servicioAdicionalId") Long servicioAdicionalId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "ServicioAdicionalResource getReview: input: {0}", servicioAdicionalId);
        ServicioAdicionalEntity entity = servicioAdicionalLogic.getServicioAdicional(viviendaId, servicioAdicionalId);
        if (entity == null) {
            throw new WebApplicationException("El recurso /viviendas/" + viviendaId + "/servicio/" + servicioAdicionalId + " no existe.", 404);
        }
        ServicioAdicionalDTO servicioAdicionalDTO = new ServicioAdicionalDTO(entity);
        LOGGER.log(Level.INFO, "SerivicoAdicionalResource getServicioAdicional: output: {0}", servicioAdicionalDTO.toString());
        return servicioAdicionalDTO;
    }

    /**
     * Actualiza una reseña con la informacion que se recibe en el cuerpo de la
     * petición y se regresa el objeto actualizado.
     *
     * @param viviendaId El ID de la vivienda de la cual se guarda el servicio adicional
     * @param servicioAdicionalId El ID del servicio adicional que se va a actualizar
     * @param servicio {@link ReviewDTO} - El servicio adicional que se desea guardar.
     * @return JSON {@link ReviewDTO} - El servicio adicional actualizado.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando ya existe el servicio adicional.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el servicio adicional.
     */
    @PUT
    @Path("{servicioAdicionalId: \\d+}")
    public ServicioAdicionalDTO updateServicioAdicional(@PathParam("viviendaId") Long viviendaId, @PathParam("servicioAdicionalId") Long servicioAdicionalId, ServicioAdicionalDTO servicio) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "ServicioAdicionalResource updateServicioAdicional: input: viviendaId: {0} , servicioAdicionalId: {1} , servicio:{2}", new Object[]{viviendaId, servicioAdicionalId, servicio.toString()});
        if (servicioAdicionalId.equals(servicio.getId())) {
            throw new BusinessLogicException("Los ids del ServicioAdicional no coinciden.");
        }
        ServicioAdicionalEntity entity = servicioAdicionalLogic.getServicioAdicional(viviendaId, servicioAdicionalId);
        if (entity == null) {
            throw new WebApplicationException("El recurso /viviendas/" + viviendaId + "/serviciosadicionales/" + servicioAdicionalId + " no existe.", 404);

        }
        ServicioAdicionalDTO reviewDTO = new ServicioAdicionalDTO(servicioAdicionalLogic.updateServicioAdicional(viviendaId, servicio.toEntity()));
        LOGGER.log(Level.INFO, "servicioAdicionalResource updateReview: output:{0}", reviewDTO.toString());
        return reviewDTO;

    }

    /**
     * Borra el servicio adicional con el id asociado recibido en la URL.
     *
     * @param viviendaId El ID de la vivienda del cual se va a eliminar el servicio adicional.
     * @param servicioAdicionalId El ID del servicio adicional que se va a eliminar.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando no se puede eliminar el servicio adicional.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el servicio adicional.
     */
    @DELETE
    @Path("{servicioAdicionalId: \\d+}")
    public void deleteReview(@PathParam("viviendaId") Long viviendaId, @PathParam("servicioAdicionalId") Long servicioAdicionalId) throws BusinessLogicException {
        ServicioAdicionalEntity entity = servicioAdicionalLogic.getServicioAdicional(viviendaId, servicioAdicionalId);
        if (entity == null) {
            throw new WebApplicationException("El recurso /viviendas/" + viviendaId + "/serviciosadicionales/" + servicioAdicionalId + " no existe.", 404);
        }
        servicioAdicionalLogic.deleteServicioAdicional(viviendaId, servicioAdicionalId);
    }

    /**
     * Lista de entidades a DTO.
     *
     * Este método convierte una lista de objetos ServicioAdicionalEntity a una lista de
     * objetos ServicioAdicionalDTO (json)
     *
     * @param entityList corresponde a la lista de reseñas de tipo Entity que
     * vamos a convertir a DTO.
     * @return la lista de servicios adicinales en forma DTO (json)
     */
    private List<ServicioAdicionalDTO> listEntity2DTO(List<ServicioAdicionalEntity> entityList) {
        List<ServicioAdicionalDTO> list = new ArrayList<>();
        for (ServicioAdicionalEntity entity : entityList) {
            list.add(new ServicioAdicionalDTO(entity));
        }
        return list;
    }
}
