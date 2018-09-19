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
     * Crea una nueva reseña con la informacion que se recibe en el cuerpo de la
     * petición y se regresa un objeto identico con un id auto-generado por la
     * base de datos.
     *
     * @param viviendaId El ID del libro del cual se le agrega la reseña
     * @param servicio {@link ReviewDTO} - La reseña que se desea guardar.
     * @return JSON {@link ReviewDTO} - La reseña guardada con el atributo id
     * autogenerado.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando ya existe la reseña.
     */
    @POST
    public ServicioAdicionalDTO createServicioAdicional(@PathParam("viviendaId") Long viviendaId, ServicioAdicionalDTO servicio) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "ServicioAdicionalResource createServicioAdicional: input: {0}", servicio.toString());
        ServicioAdicionalDTO nuevoReviewDTO = new ServicioAdicionalDTO(servicioAdicionalLogic.createReview(viviendaId, servicio.toEntity()));
        LOGGER.log(Level.INFO, "ServicioAdicionalResource createServicioAdicional: output: {0}", nuevoReviewDTO.toString());
        return nuevoReviewDTO;
    }

    /**
     * Busca y devuelve todas las reseñas que existen en un libro.
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
     * Busca y devuelve la reseña con el ID recibido en la URL, relativa a un
     * libro.
     *
     * @param viviendaId El ID del libro del cual se buscan las reseñas
     * @param servicioAdicionalId El ID de la reseña que se busca
     * @return {@link ReviewDTO} - La reseña encontradas en el libro.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el libro.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la reseña.
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
     * @param viviendaId El ID del libro del cual se guarda la reseña
     * @param servicioAdicionalId El ID de la reseña que se va a actualizar
     * @param servicio {@link ReviewDTO} - La reseña que se desea guardar.
     * @return JSON {@link ReviewDTO} - La reseña actualizada.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando ya existe la reseña.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la reseña.
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
     * Borra la reseña con el id asociado recibido en la URL.
     *
     * @param viviendaId El ID del libro del cual se va a eliminar la reseña.
     * @param servicioAdicionalId El ID de la reseña que se va a eliminar.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando no se puede eliminar la reseña.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la reseña.
     */
    @DELETE
    @Path("{servicioAdicionalId: \\d+}")
    public void deleteReview(@PathParam("viviendaId") Long viviendaId, @PathParam("servicioAdicionalId") Long servicioAdicionalId) throws BusinessLogicException {
        ServicioAdicionalEntity entity = servicioAdicionalLogic.getServicioAdicional(viviendaId, servicioAdicionalId);
        if (entity == null) {
            throw new WebApplicationException("El recurso /viviendas/" + viviendaId + "/serviciosadicionales/" + servicioAdicionalId + " no existe.", 404);
        }
        servicioAdicionalLogic.deleteReview(viviendaId, servicioAdicionalId);
    }

    /**
     * Lista de entidades a DTO.
     *
     * Este método convierte una lista de objetos PrizeEntity a una lista de
     * objetos ReviewDTO (json)
     *
     * @param entityList corresponde a la lista de reseñas de tipo Entity que
     * vamos a convertir a DTO.
     * @return la lista de reseñas en forma DTO (json)
     */
    private List<ServicioAdicionalDTO> listEntity2DTO(List<ServicioAdicionalEntity> entityList) {
        List<ServicioAdicionalDTO> list = new ArrayList<>();
        for (ServicioAdicionalEntity entity : entityList) {
            list.add(new ServicioAdicionalDTO(entity));
        }
        return list;
    }
}
