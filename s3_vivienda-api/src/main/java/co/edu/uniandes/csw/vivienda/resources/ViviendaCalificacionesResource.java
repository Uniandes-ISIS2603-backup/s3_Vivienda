/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.vivienda.resources;
import co.edu.uniandes.csw.vivienda.entities.CalificacionEntity;
import co.edu.uniandes.csw.vivienda.dtos.CalificacionDTO;
import co.edu.uniandes.csw.vivienda.ejb.CalificacionLogic;
import co.edu.uniandes.csw.vivienda.exceptions.BusinessLogicException;
import java.util.ArrayList;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import java.util.List;
import java.util.logging.Level;
import javax.inject.Inject;
import javax.ws.rs.PUT;
import javax.ws.rs.WebApplicationException;

/**
 *
 * @authors Juan Manuel Castillo y Daniel Giraldo
 */
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class ViviendaCalificacionesResource{
    
    private static final Logger LOGGER = Logger.getLogger(ViviendaCalificacionesResource.class.getName());
    
    @Inject
    private CalificacionLogic calificacionLogic; // Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.

    /**
     * Crea una nueva calificacion con la informacion que se recibe en el cuerpo de
     * la petición y se regresa un objeto identico con un id auto-generado por
     * la base de datos.
     *
     * @param viviendaId La vivienda asociadad a la calificación
     * @param calificacion {@link CalificacionDTO} - La calificacion que se desea
     * guardar.
     * @return JSON {@link CalificacionDTO} - La calificacion guardada con el atributo
     * id autogenerado.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando no se puede crear la calificación.
     */
    @POST
    public CalificacionDTO createCalificacion(@PathParam("viviendaId") Long viviendaId, CalificacionDTO calificacion) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "ViviendaCalificacionResource createCalificacion: input: {0}", calificacion.toString());
        // Convierte el DTO (json) en un objeto Entity para ser manejado por la lógica.
        CalificacionEntity calificacionEntity = calificacion.toEntity();
        // Invoca la lógica para crear la calificacion nueva
        CalificacionEntity nuevoCalificacionEntity = calificacionLogic.createCalificacion(viviendaId, calificacionEntity);
        // Como debe retornar un DTO (json) se invoca el constructor del DTO con argumento el entity nuevo
        CalificacionDTO nuevoCalificacionDTO = new CalificacionDTO(nuevoCalificacionEntity);
        LOGGER.log(Level.INFO, "ViviendaCalificacionResource createCalificacion: output: {0}", nuevoCalificacionDTO.toString());
        return nuevoCalificacionDTO;
    }
    
    /**
     * Busca y devuelve todos las calificacioness que existen en la vivienda.
     *
     * @param viviendaId Identificador de la vivienda que se esta buscando.
     * Este debe ser una cadena de dígitos.
     * @return JSONArray {@link CalificacionDTO} - Las calificaciones encontrados en el
     * vivienda. Si no hay ninguno retorna una lista vacía.
     */
    @GET
    public List<CalificacionDTO> getCalificaciones(@PathParam("viviendaId") Long viviendaId) {
        LOGGER.log(Level.INFO, "ViviendaCalificacionesResource getCalificaciones: input: {0}", viviendaId);
        List<CalificacionDTO> listaDTOs = calificacionListEntity2DTO(calificacionLogic.getCalificacionesVivienda(viviendaId));
        LOGGER.log(Level.INFO, "ViviendaCalificacionesResource getCalificaciones: output: {0}", listaDTOs.toString());
        return listaDTOs;
    }
    
    /**
     * Busca la calificacion con el id asociado dentro de la vivienda con id asociado.
     *
     * @param viviendaId Identificador de la vivienda que se esta buscando.
     * Este debe ser una cadena de dígitos.
     * @param calificacionId Identificador de la calificación que se esta buscando. Este debe
     * ser una cadena de dígitos.
     * @return JSON {@link CalificacionDTO} - La calificación buscado
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la calificacion.
     */
    @GET
    @Path("{calificacionId: \\d+}")
    public CalificacionDTO getCalificacion(@PathParam("viviendaId") Long viviendaId, @PathParam("calificacionId") Long calificacionId){
        LOGGER.log(Level.INFO, "ViviendaCalificacionesResource getCalificacion: input: viviendaID: {0} , calificacionId: {1}", new Object[]{viviendaId, calificacionId});
        try{
            CalificacionEntity calificacion = calificacionLogic.getCalificacionVivienda(viviendaId, calificacionId);
            CalificacionDTO calificacionDTO = new CalificacionDTO(calificacion);
            LOGGER.log(Level.INFO, "ViviendaCalificacionesResource getCalificacion: output: {0}", calificacionDTO.toString());
            return calificacionDTO;
        }
        catch(BusinessLogicException e){
            throw new WebApplicationException("El recurso /viviendas/" + viviendaId + "/calificaciones/" + calificacionId + " no existe.", 404);
        }
    }
    
    /**
     * Actualiza la calificacion con el id recibido en la URL con la informacion
     * que se recibe en el cuerpo de la petición.
     *
     * @param viviendaId Id de la vivienda
     * @param calificacionId Identificador de la calificacion que se desea
     * actualizar. Este debe ser una cadena de dígitos.
     * @param calificacion {@link CalificacionDetailDTO} La calificacion que se desea
     * guardar.
     * @return JSON {@link CalificacionDetailDTO} - La calificacion guardada.
     * @throws co.edu.uniandes.csw.vivienda.exceptions.BusinessLogicException - 
     * Error de lógica que se genera cuando no se encuentra la calificacion a actualizar.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la calificacion a actualizar.
     */
    @PUT
    @Path("{calificacionId:\\d+}")
    public CalificacionDTO updateCalificacion(@PathParam("viviendaId") Long viviendaId, @PathParam("calificacionId") Long calificacionId, CalificacionDTO calificacion) throws BusinessLogicException{
        LOGGER.log(Level.INFO, "ViviendaCalificacionesResource updateCalificacion: input: id:{0} , calificacion: {1}", new Object[]{calificacionId, calificacion.toString()});
        calificacion.setId(calificacionId);
        try{
            calificacionLogic.getCalificacionVivienda(viviendaId, calificacionId);
        }
        catch(BusinessLogicException e){
            throw new WebApplicationException("El recurso /viviendas/" + viviendaId + "/calificaciones/" + calificacionId + " no existe.", 404);
        }
        
        CalificacionEntity calificacionEnt = calificacionLogic.updateCalificacionVivienda(calificacionId, calificacion.toEntity());
        CalificacionDTO calificacionDTO = new CalificacionDTO(calificacionEnt);
        LOGGER.log(Level.INFO, "ViviendaCalificacionesResource updateCalificacion: output: {0}", calificacionDTO.toString());
        return calificacionDTO;
    }
    
    /**
     * Borra la calificacion con el id asociado recibido en la URL.
     *
     * @param viviendaId
     * @param calificacionId Identificador de la calificacion que se desea borrar.
     * Este debe ser una cadena de dígitos.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la calificacion.
     */
    @Path("{calificacionId:\\d+}")
    @DELETE
    public void deleteCalificacion(@PathParam("viviendaId") Long viviendaId, @PathParam("calificacionId") Long calificacionId){
        LOGGER.log(Level.INFO, "ViviendaCalificacionResource deleteCalificacion: input: {0}", calificacionId);
        try{
            calificacionLogic.deleteCalificacionVivienda(viviendaId, calificacionId);
            LOGGER.info("ViviendaCalificacionResource deleteCalificacion: output: void");
        }
        catch (BusinessLogicException e){
            throw new WebApplicationException("El recurso /viviendas/" + viviendaId + "/calificaciones/" + calificacionId + " no existe.", 404);
        }
    }
    
    /**
     * Convierte una lista de CalificacionEntity a una lista de CalificacionDTO.
     *
     * @param entityList Lista de CalificacionEntity a convertir.
     * @return Lista de CalificacionDTO convertida.
     */
    private List<CalificacionDTO> calificacionListEntity2DTO(List<CalificacionEntity> entityList) {
        List<CalificacionDTO> list = new ArrayList();
        for (CalificacionEntity entity : entityList) {
            list.add(new CalificacionDTO(entity));
        }
        return list;
    }

    /**
     * Convierte una lista de CalificacionDTO a una lista de CalificacionEntity.
     *
     * @param dtos Lista de CalificacionDTO a convertir.
     * @return Lista de CalificacionEntity convertida.
     */
    private List<CalificacionEntity> calificacionListDTO2Entity(List<CalificacionDTO> dtos) {
        List<CalificacionEntity> list = new ArrayList<>();
        for (CalificacionDTO dto : dtos) {
            list.add(dto.toEntity());
        }
        return list;
    }
}
