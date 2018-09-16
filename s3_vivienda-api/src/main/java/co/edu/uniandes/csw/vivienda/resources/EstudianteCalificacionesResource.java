/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.vivienda.resources;

import co.edu.uniandes.csw.vivienda.dtos.CalificacionDTO;
import co.edu.uniandes.csw.vivienda.ejb.CalificacionLogic;
import co.edu.uniandes.csw.vivienda.ejb.EstudianteCalificacionesLogic;
import co.edu.uniandes.csw.vivienda.entities.CalificacionEntity;
import co.edu.uniandes.csw.vivienda.exceptions.BusinessLogicException;
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
 * @author estudiante
 */
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class EstudianteCalificacionesResource{
    private static final Logger LOGGER = Logger.getLogger(EstudianteCalificacionesResource.class.getName());
    
   
    @Inject
    private CalificacionLogic calificacionLogic; // Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.
    
    /**
     * Guarda una calificacion dentro de una estudiante con la informacion que recibe el
     * la URL. Se devuelve la calificacion que se guarda en el estudiante.
     *
     * @param estudianteId Identificador de el estudiante que se esta
     * actualizando. Este debe ser una cadena de dígitos.
     * @param calificacionId Identificador de la calificación que se desea guardar. Este debe
     * ser una cadena de dígitos.
     * @return JSON {@link CalificacionDTO} - La calificación guardado en el estudiante.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la calificacion.
     */
    @POST
    @Path("{calificacionId: \\d+}")
    public CalificacionDTO addCalificacion(@PathParam("estudianteId") Long estudianteId, @PathParam("calificacionId") Long calificacionId) {
        LOGGER.log(Level.INFO, "EstudianteCalificacionesResource addCalificacion: input: estudianteID: {0} , calificacionId: {1}", new Object[]{estudianteId, calificacionId});
        if (calificacionLogic.getCalificacion(calificacionId) == null) {
            throw new WebApplicationException("El recurso /calificacion/" + calificacionId + " no existe.", 404);
        }
        CalificacionDTO calificacionDTO = new CalificacionDTO(calificacionLogic.addCalificacion(calificacionId, estudianteId));
        LOGGER.log(Level.INFO, "EstudianteCalificacionesResource addCalificacion: output: {0}", calificacionDTO.toString());
        return calificacionDTO;
    }
    
    /**
     * Busca y devuelve todos las calificacioness que existen en el estudiante.
     *
     * @param estudianteId Identificador de el estudiante que se esta buscando.
     * Este debe ser una cadena de dígitos.
     * @return JSONArray {@link CalificacionDTO} - Las calificaciones encontrados en el
     * estudiante. Si no hay ninguno retorna una lista vacía.
     */
    @GET
    public List<CalificacionDTO> getCalificaciones(@PathParam("estudianteId") Long estudianteId) {
        LOGGER.log(Level.INFO, "EstudianteCalificacionesResource getCalificaciones: input: {0}", estudianteId);
        List<CalificacionDTO> listaDTOs = calificacionListEntity2DTO(calificacionLogic.getCalificacionesEstudiante(estudianteId));
        LOGGER.log(Level.INFO, "EstudianteCalificacionesResource getCalificaciones: output: {0}", listaDTOs.toString());
        return listaDTOs;
    }
    
    /**
     * Busca la calificacion con el id asociado dentro de el estudiante con id asociado.
     *
     * @param estudianteId Identificador de el estudiante que se esta buscando.
     * Este debe ser una cadena de dígitos.
     * @param calificacionId Identificador de la calificación que se esta buscando. Este debe
     * ser una cadena de dígitos.
     * @return JSON {@link CalificacionDTO} - La calificación buscado
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la calificacion.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la calificacion en la
     * estudiante.
     */
    @GET
    @Path("{calificacionId: \\d+}")
    public CalificacionDTO getCalificacion(@PathParam("estudianteId") Long estudianteId, @PathParam("calificacionId") Long calificacionId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "EstudianteCalificacionesResource getCalificacion: input: estudianteID: {0} , calificacionId: {1}", new Object[]{estudianteId, calificacionId});
        if (calificacionLogic.getCalificacion(calificacionId) == null) {
            throw new WebApplicationException("El recurso /estudiante/" + estudianteId + "/calificacion/" + calificacionId + " no existe.", 404);
        }
        CalificacionDTO calificacionDTO = new CalificacionDTO(calificacionLogic.getCalificacionEstudiante(estudianteId, calificacionId));
        LOGGER.log(Level.INFO, "EstudianteCalificacionesResource getCalificacion: output: {0}", calificacionDTO.toString());
        return calificacionDTO;
    }
    
    /**
     * Actualiza la calificacion con el id recibido en la URL con la informacion
     * que se recibe en el cuerpo de la petición.
     *
     * @param estudianteId Id del estudiante
     * @param calificacionId Identificador de la calificacion que se desea
     * actualizar. Este debe ser una cadena de dígitos.
     * @param calificacion {@link CalificacionDetailDTO} La calificacion que se desea
     * guardar.
     * @return JSON {@link CalificacionDetailDTO} - La calificacion guardada.
     * @throws co.edu.uniandes.csw.vivienda.exceptions.BusinessLogicException
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la calificacion a
     * actualizar.
     */
    @PUT
    @Path("{calificacionId:\\d+}")
    public CalificacionDTO updateCalificacion(@PathParam("estudianteId") Long estudianteId, @PathParam("calificacionId") Long calificacionId, CalificacionDTO calificacion) throws BusinessLogicException{
        LOGGER.log(Level.INFO, "EstudianteCalificacionesResource updateCalificacion: input: id:{0} , calificacion: {1}", new Object[]{calificacionId, calificacion.toString()});
        calificacion.setId(calificacionId);
        if (calificacionLogic.getCalificacion(calificacionId) == null) {
            throw new WebApplicationException("El recurso /estudiante/" + estudianteId + "/calificacion/" + calificacionId + " no existe.", 404);
        }
        CalificacionDTO calificacionDTO = new CalificacionDTO(calificacionLogic.updateCalificacion(calificacionId, calificacion.toEntity()));
        LOGGER.log(Level.INFO, "EstudianteCalificacionesResource updateCalificacion: output: {0}", calificacionDTO.toString());
        return calificacionDTO;
    }
    
    /**
     * Borra la calificacion con el id asociado recibido en la URL.
     *
     * @param estudianteId
     * @param calificacionId Identificador de la calificacion que se desea borrar.
     * Este debe ser una cadena de dígitos.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando no se puede eliminar la calificacion.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la calificacion.
     */
    @Path("{calificacionId:\\d+}")
    @DELETE
    public void deleteCalificacion(@PathParam("estudianteId") Long estudianteId, @PathParam("calificacionId") Long calificacionId) throws BusinessLogicException{
        LOGGER.log(Level.INFO, "EstudianteCalificacionResource deleteCalificacion: input: {0}", calificacionId);
        if (calificacionLogic.getCalificacion(calificacionId) == null) {
            throw new WebApplicationException("El recurso /calificacion/" + calificacionId + " no existe.", 404);
        }
        calificacionLogic.deleteCalificacionEstudiante(estudianteId, calificacionId);
        LOGGER.info("EstudianteCalificacionResource deleteCalificacion: output: void");
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
