/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.vivienda.resources;

import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import co.edu.uniandes.csw.vivienda.dtos.EstudianteDTO;
import co.edu.uniandes.csw.vivienda.dtos.EstudianteDetailDTO;
import co.edu.uniandes.csw.vivienda.ejb.EstudianteLogic;
import co.edu.uniandes.csw.vivienda.entities.EstudianteEntity;
import co.edu.uniandes.csw.vivienda.exceptions.BusinessLogicException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import javax.inject.Inject;
import javax.ws.rs.WebApplicationException;

/**
 *
 * @author estudiante
 */
@Path("estudiantes")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class EstudianteResource {
    
    private static final Logger LOGGER = Logger.getLogger(EstudianteResource.class.getName());
    
    @Inject
    EstudianteLogic estudianteLogic;  // Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.
    
    /**
     * Crea un nuevo estudiante con la informacion que se recibe en el cuerpo de la
     * petición y se regresa un objeto identico con un id auto-generado por la
     * base de datos.
     *
     * @param estudiante {@link EstudianteDTO} - EL estudiante que se desea guardar.
     * @return JSON {@link EstudianteDTO} - El estudiante guardado con el atributo id
     * autogenerado.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando no se puede crear el estudiante.
     */
    @POST
    public EstudianteDTO createEstudiante(EstudianteDTO estudiante) throws BusinessLogicException{
        LOGGER.log(Level.INFO, "EstudianteResource createEstudiante: input: {0}", estudiante.toString());
        EstudianteDTO nuevoEstudianteDTO = new EstudianteDTO(estudianteLogic.createEstudiante(estudiante.toEntity()));
        LOGGER.log(Level.INFO, "EstudianteResource createEstudiante: output: {0}", nuevoEstudianteDTO.toString());
        return nuevoEstudianteDTO;
    }
    
    /**
     * Busca y devuelve todos los estudiantes que existen en la aplicacion.
     *
     * @return JSONArray {@link EstudianteDetailDTO} - Los estudiantes encontrados en la
     * aplicación. Si no hay ninguno retorna una lista vacía.
     */
    @GET
    public Collection <EstudianteDetailDTO> getEstudiantes() {
        LOGGER.info("EstudianteResource getEstudiantes: input: void");
        List<EstudianteDetailDTO> listaEstudiantes = listEntity2DetailDTO(estudianteLogic.getEstudiantes());
        LOGGER.log(Level.INFO, "EstudianteResource getEstudiantes: output: {0}", listaEstudiantes.toString());
        return listaEstudiantes;
    }
    
    /**
     * Busca el estudiante con el id asociado recibido en la URL y lo devuelve.
     *
     * @param estudiantesId Identificador del estudiante que se esta buscando. Este debe
     * ser una cadena de dígitos.
     * @return JSON {@link EstudianteDetailDTO} - El estudiante buscado
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el estudiante.
     */
    @GET
    @Path("{estudianteId:\\d+}")
    public EstudianteDetailDTO getEstudiante(@PathParam("estudianteId") Long estudiantesId) {
        LOGGER.log(Level.INFO, "EstudianteResource getEstudiante: input: {0}", estudiantesId);
        EstudianteEntity estudianteEntity = estudianteLogic.getEstudiante(estudiantesId);
        if (estudianteEntity == null) {
            throw new WebApplicationException("El recurso /estudiantes/" + estudiantesId + " no existe.", 404);
        }
        EstudianteDetailDTO estudianteDetailDTO = new EstudianteDetailDTO(estudianteEntity);
        LOGGER.log(Level.INFO, "EstudianteResource getEstudiante: output: {0}", estudianteDetailDTO.toString());
        return estudianteDetailDTO;
    }
    
    /**
     * Actualiza el estudiante con el id recibido en la URL con la información que se
     * recibe en el cuerpo de la petición.
     *
     * @param estudiantesId Identificador del estudiante que se desea actualizar. Este debe
     * ser una cadena de dígitos.
     * @param estudiante {@link EstudianteDTO} El estudiante que se desea guardar.
     * @return JSON {@link EstudianteDetailDTO} - El estudiante guardada.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el estudiante a
     * actualizar.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando no se puede actualizar el estudiante.
     */
    @PUT
    @Path("{estudianteId:\\d+}")
    public EstudianteDetailDTO updateEstudiante(@PathParam("estudianteId") Long estudiantesId, EstudianteDTO estudiante) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "EstudianteResource updateEstudiante: input: id: {0} , estudiante: {1}", new Object[]{estudiantesId, estudiante.toString()});
        estudiante.setId(estudiantesId);
        if (estudianteLogic.getEstudiante(estudiantesId) == null) {
            throw new WebApplicationException("El recurso /estudiantes/" + estudiantesId + " no existe.", 404);
        }
        EstudianteDetailDTO detailDTO = new EstudianteDetailDTO(estudianteLogic.updateEstudiante(estudiantesId, estudiante.toEntity()));
        LOGGER.log(Level.INFO, "EstudianteResource updateEstudiante: output: {0}", detailDTO.toString());
        return detailDTO;
    }
    
    /**
     * Borra el estudiante con el id asociado recibido en la URL.
     *
     * @param estudiantesId Identificador del estudiante que se desea borrar. Este debe ser
     * una cadena de dígitos.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el estudiante.
     */
    @DELETE
    @Path("{estudianteId:\\d+}")
    public void deleteEstudiante(@PathParam("estudianteId") Long estudiantesId) {
        LOGGER.log(Level.INFO, "EstudianteResource deleteEstudiante: input: {0}", estudiantesId);
        EstudianteEntity entity = estudianteLogic.getEstudiante(estudiantesId);
        if (entity == null) {
            throw new WebApplicationException("El recurso /estudiantes/" + estudiantesId + " no existe.", 404);
        }
        estudianteLogic.deleteEstudiante(estudiantesId);
        LOGGER.info("EstudianteResource deleteEstudiante: output: void");
    }
    
    /**
     * Conexión con el servicio de autores para un estudiante.
     * {@link EstudianteAuthorsResource}
     *
     * Este método conecta la ruta de /estudiantes con las rutas de /authors que
     * dependen del estudiante, es una redirección al servicio que maneja el segmento
     * de la URL que se encarga de las reseñas.
     *
     * @param estudianteId El ID del estudiante con respecto al cual se accede al
     * servicio.
     * @return El servicio de autores para ese estudiante en paricular.\
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el estudiante.
     */
    @Path("{estudianteId:\\d+}/contrato")
    public Class<EstudianteContratoResource> getEstudianteContratoResource(@PathParam("estudianteId") Long estudianteId){
        if (estudianteLogic.getEstudiante(estudianteId) == null) {
            throw new WebApplicationException("El recurso /estudiantes/" + estudianteId + "  no existe.", 404);
        }
        return EstudianteContratoResource.class;
    }
    
    /**
     * Conexión con el servicio de autores para un estudiante.
     * {@link EstudianteAuthorsResource}
     *
     * Este método conecta la ruta de /estudiantes con las rutas de /authors que
     * dependen del estudiante, es una redirección al servicio que maneja el segmento
     * de la URL que se encarga de las reseñas.
     *
     * @param estudianteId El ID del estudiante con respecto al cual se accede al
     * servicio.
     * @return El servicio de autores para ese estudiante en paricular.\
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el estudiante.
     */
    @Path("{estudianteId:\\d+}/universidad")
    public Class<EstudianteUniversidadResource> getEstudianteUniversidadResource(@PathParam("estudianteId") Long estudianteId){
        if (estudianteLogic.getEstudiante(estudianteId) == null) {
            throw new WebApplicationException("El recurso /estudiantes/" + estudianteId + "  no existe.", 404);
        }
        return EstudianteUniversidadResource.class;
    }
    
    /**
     * Conexión con el servicio de autores para un estudiante.
     * {@link EstudianteAuthorsResource}
     *
     * Este método conecta la ruta de /estudiantes con las rutas de /authors que
     * dependen del estudiante, es una redirección al servicio que maneja el segmento
     * de la URL que se encarga de las reseñas.
     *
     * @param estudianteId El ID del estudiante con respecto al cual se accede al
     * servicio.
     * @return El servicio de autores para ese estudiante en paricular.\
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el estudiante.
     */
    @Path("{estudianteId:\\d+}/calificaciones")
    public Class<EstudianteCalificacionesResource> getEstudianteCalificacionesResource(@PathParam("estudianteId") Long estudianteId){
        if (estudianteLogic.getEstudiante(estudianteId) == null) {
            throw new WebApplicationException("El recurso /estudiantes/" + estudianteId + " no existe.", 404);
        }
        return EstudianteCalificacionesResource.class;
    }
    
    /**
     * Convierte una lista de entidades a DTO.
     *
     * Este método convierte una lista de objetos EstudianteEntity a una lista de
     * objetos EstudianteDetailDTO (json)
     *
     * @param entityList corresponde a la lista de estudiantes de tipo Entity que
     * vamos a convertir a DTO.
     * @return la lista de estudiantes en forma DTO (json)
     */
    private List<EstudianteDetailDTO> listEntity2DetailDTO(List<EstudianteEntity> entityList) {
        List<EstudianteDetailDTO> list = new ArrayList<>();
        for (EstudianteEntity entity : entityList) {
            list.add(new EstudianteDetailDTO(entity));
        }
        return list;
    }
}
