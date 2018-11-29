/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.vivienda.resources;

import co.edu.uniandes.csw.vivienda.dtos.ServicioAdicionalDTO;
import co.edu.uniandes.csw.vivienda.dtos.ServicioAdicionalDetailDTO;
import co.edu.uniandes.csw.vivienda.ejb.ServicioAdicionalLogic;
import co.edu.uniandes.csw.vivienda.ejb.ViviendaLogic;
import co.edu.uniandes.csw.vivienda.entities.ServicioAdicionalEntity;
import co.edu.uniandes.csw.vivienda.entities.ViviendaEntity;
import co.edu.uniandes.csw.vivienda.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.vivienda.mappers.*;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.*;
import javax.inject.Inject;
import javax.ws.rs.*;

@Produces("application/json")
@Consumes("application/json")

public class ServicioAdicionalResource {

    private static final Logger LOGGER = Logger.getLogger(ServicioAdicionalResource.class.getName());

    private static final String RECURSO_VIVIENDAS = "El recurso /viviendas/";
    private static final String SERVICIO_ADICIONAL = "/servicio-adicional/";
    private static final String NO_EXISTE = " no existe.";

    @Inject
    private ServicioAdicionalLogic servicioAdicionalLogic;
    
    @Inject
    ViviendaLogic viviendaLogic;

    /**
     * Crea un nuevo servicio adicional con la informacion que se recibe en el cuerpo de la
     * petición y se regresa un objeto identico con un id auto-generado por la
     * base de datos.
     *
     * @param viviendaId El ID de la vivienda de la cual se le agrega el servicio adicional
     * @param servicio   {@link ServicioAdicionalDTO} - La reseña que se desea guardar.
     * @return JSON {@link ServicioAdicionalDTO} - La reseña guardada con el atributo id
     * autogenerado.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     *                                Error de lógica que se genera cuando ya existe la reseña.
     */
    @POST
    public ServicioAdicionalDTO createServicioAdicional(@PathParam("viviendaId") Long viviendaId, ServicioAdicionalDTO servicio) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "ServicioAdicionalResource createServicioAdicional: input: {0}", servicio);
        ServicioAdicionalDTO nuevoReviewDTO = new ServicioAdicionalDTO(servicioAdicionalLogic.createServicioAdicional(viviendaId, servicio.toEntity()));
        LOGGER.log(Level.INFO, "ServicioAdicionalResource createServicioAdicional: output: {0}", nuevoReviewDTO);
        return nuevoReviewDTO;
    }
    
     /**
     * Metodo para generar los datos de SitioInteres
     * @return Lista con los elementos creados de SitioInteres
     * @throws WebApplicationException Excepcion en caso de no poder generar los datos
     */
    @POST
    @Path("generardatos")
    public List<ServicioAdicionalDTO> generarDatos(){

        List<ViviendaEntity> viviendas = viviendaLogic.getViviendas();
        List<ServicioAdicionalDTO> servicios = new ArrayList<>();
        if (viviendas != null) {
            for (ViviendaEntity vivienda : viviendas) {
                servicioAdicionalLogic.generarServiciosAdicionales(vivienda.getId());
                List<ServicioAdicionalEntity> serviciosAdicionales = servicioAdicionalLogic.getServiciosAdicionales(vivienda.getId());
                for (ServicioAdicionalEntity servicio : serviciosAdicionales) {
                    ServicioAdicionalDTO servicioDTO = new ServicioAdicionalDTO(servicio);
                    servicios.add(servicioDTO);
                }
            }
        }
        else
        {
            throw new WebApplicationException(SERVICIO_ADICIONAL + viviendas + NO_EXISTE, 404);
        }
        return servicios;
    }

    /**
     * Busca y devuelve todos los servicios adicionales que existen en una vivienda.
     *
     * @param viviendaId El ID del libro del cual se buscan las reseñas
     * @return JSONArray {@link ServicioAdicionalDTO} - Las reseñas encontradas en el
     * libro. Si no hay ninguna retorna una lista vacía.
     */
    @GET
    public List<ServicioAdicionalDetailDTO> getServiciosAdicionales(@PathParam("viviendaId") Long viviendaId) {
        LOGGER.log(Level.INFO, "ServicioAdicionalResource getSericiosAdicionales: input: {0}", viviendaId);
        List<ServicioAdicionalDetailDTO> listaDTOs = listEntity2DTO(servicioAdicionalLogic.getServiciosAdicionales(viviendaId));
        LOGGER.log(Level.INFO, "ViviendaResource getViviendas: output: {0}", listaDTOs);
        return listaDTOs;
    }

    /**
     * Busca y devuelve el servicio adicional con el ID recibido en la URL, relativa a una
     * vivienda.
     *
     * @param viviendaId          El ID de la vivienda de la cual se buscan los servicios adicionales.
     * @param servicioAdicionalId El ID del servicio adicional que se busca
     * @return {@link ServicioAdicionalDTO} - El servicio adicional encontrado en la vivienda.
     * @throws BusinessLogicException  {@link BusinessLogicExceptionMapper} -
     *                                 Error de lógica que se genera cuando no se encuentra la vivienda.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     *                                 Error de lógica que se genera cuando no se encuentra el servicio adicional.
     */
    @GET
    @Path("{servicioAdicionalId: \\d+}")
    public ServicioAdicionalDetailDTO getServicioAdicional(@PathParam("viviendaId") Long viviendaId, @PathParam("servicioAdicionalId") Long servicioAdicionalId){
        LOGGER.log(Level.INFO, "ServicioAdicionalResource getServicioAdicional: input: {0}", servicioAdicionalId);
        ServicioAdicionalEntity entity = servicioAdicionalLogic.getServicioAdicional(viviendaId, servicioAdicionalId);
        if (entity == null) {
            throw new WebApplicationException(RECURSO_VIVIENDAS + viviendaId + "/servicio/" + servicioAdicionalId + NO_EXISTE, 404);
        }
        ServicioAdicionalDetailDTO servicioAdicionalDTO = new ServicioAdicionalDetailDTO(entity);
        LOGGER.log(Level.INFO, "ServicioAdicionalResource getServicioAdicional: output: {0}", servicioAdicionalDTO);
        return servicioAdicionalDTO;
    }

    /**
     * Actualiza una reseña con la informacion que se recibe en el cuerpo de la
     * petición y se regresa el objeto actualizado.
     *
     * @param viviendaId          El ID de la vivienda de la cual se guarda el servicio adicional
     * @param servicioAdicionalId El ID del servicio adicional que se va a actualizar
     * @param servicio {@link ServicioAdicionalDTO} - El servicio adicional que se desea guardar.
     * @return JSON {@link ServicioAdicionalDTO} - El servicio adicional actualizado.
     * @throws BusinessLogicException  {@link BusinessLogicExceptionMapper} -
     *                                 Error de lógica que se genera cuando ya existe el servicio adicional.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     *                                 Error de lógica que se genera cuando no se encuentra el servicio adicional.
     */
    @PUT
    @Path("{servicioAdicionalId: \\d+}")
    public ServicioAdicionalDTO updateServicioAdicional(@PathParam("viviendaId") Long viviendaId, @PathParam("servicioAdicionalId") Long servicioAdicionalId, ServicioAdicionalDTO servicio) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "ServicioAdicionalResource updateServicioAdicional: input: viviendaId: {0} , servicioAdicionalId: {1} , servicio:{2}", new Object[]{viviendaId, servicioAdicionalId, servicio});
        if (servicioAdicionalId.equals(servicio.getId())) {
            throw new BusinessLogicException("Los ids del ServicioAdicional no coinciden.");
        }
        ServicioAdicionalEntity entity = servicioAdicionalLogic.getServicioAdicional(viviendaId, servicioAdicionalId);
        if (entity == null) {
            throw new WebApplicationException(RECURSO_VIVIENDAS + viviendaId + "/serviciosadicionales/" + servicioAdicionalId + NO_EXISTE, 404);

        }
        servicio.setId(servicioAdicionalId);
        ServicioAdicionalDTO reviewDTO = new ServicioAdicionalDTO(servicioAdicionalLogic.updateServicioAdicional(viviendaId, servicio.toEntity()));
        LOGGER.log(Level.INFO, "servicioAdicionalResource updateReview: output:{0}", reviewDTO);
        return reviewDTO;

    }

    /**
     * Borra el servicio adicional con el id asociado recibido en la URL.
     *
     * @param viviendaId          El ID de la vivienda del cual se va a eliminar el servicio adicional.
     * @param servicioAdicionalId El ID del servicio adicional que se va a eliminar.
     * @throws BusinessLogicException  {@link BusinessLogicExceptionMapper} -
     *                                 Error de lógica que se genera cuando no se puede eliminar el servicio adicional.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     *                                 Error de lógica que se genera cuando no se encuentra el servicio adicional.
     */
    @DELETE
    @Path("{servicioAdicionalId: \\d+}")
    public void deleteServicioAdicional(@PathParam("viviendaId") Long viviendaId, @PathParam("servicioAdicionalId") Long servicioAdicionalId) throws BusinessLogicException {
        ServicioAdicionalEntity entity = servicioAdicionalLogic.getServicioAdicional(viviendaId, servicioAdicionalId);
        if (entity == null) {
            throw new WebApplicationException(RECURSO_VIVIENDAS + viviendaId + "/serviciosadicionales/" + servicioAdicionalId + NO_EXISTE, 404);
        }
        servicioAdicionalLogic.deleteServicioAdicional(viviendaId, servicioAdicionalId);
    }

    /**
     * Lista de entidades a DTO.
     * Este método convierte una lista de objetos ServicioAdicionalEntity a una lista de
     * objetos ServicioAdicionalDTO (json)
     *
     * @param entityList corresponde a la lista de reseñas de tipo Entity que
     *                   vamos a convertir a DTO.
     * @return la lista de servicios adicinales en forma DTO (json)
     */
    private List<ServicioAdicionalDetailDTO> listEntity2DTO(List<ServicioAdicionalEntity> entityList) {
        List<ServicioAdicionalDetailDTO> list = new ArrayList<>();
        for (ServicioAdicionalEntity entity : entityList) {
            list.add(new ServicioAdicionalDetailDTO(entity));
        }
        return list;
    }
}
