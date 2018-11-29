/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.vivienda.resources;

import co.edu.uniandes.csw.vivienda.dtos.EstudianteDTO;
import co.edu.uniandes.csw.vivienda.dtos.EstudianteDetailDTO;
import co.edu.uniandes.csw.vivienda.ejb.EstudianteLogic;
import co.edu.uniandes.csw.vivienda.ejb.UniversidadEstudiantesLogic;
import co.edu.uniandes.csw.vivienda.entities.EstudianteEntity;
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
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Paula Molina
 */
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UniversidadEstudiantesResource {
    
    private static final Logger LOGGER = Logger.getLogger(UniversidadEstudiantesResource.class.getName());
    
    private static final String NO_EXISTE = " no existe.";

    @Inject
    private UniversidadEstudiantesLogic universidadEstudiantesLogic; // Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.

    @Inject
    private EstudianteLogic estudianteLogic; // Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.

    /**
     * Guarda un estudiante dentro de una universidad con la informacion que recibe el
     * la URL. Se devuelve el estudiante que se guarda en la universidad.
     *
     * @param universidadId Identificador de la universidad que se esta
     * actualizando. Este debe ser una cadena de dígitos.
     * @param estudianteId Identificador del estudiante que se desea guardar. Este debe
     * ser una cadena de dígitos.
     * @return JSON {@link EstudianteDTO} - El estudiante guardado en la universidad.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el estudiante.
     */
    @POST
    @Path("{estudianteId: \\d+}")
    public EstudianteDTO addEstudiante(@PathParam("universidadId") Long universidadId, @PathParam("estudianteId") Long estudianteId) {
        LOGGER.log(Level.INFO, "UniversidadEstudiantesResource addEstudiante: input: universidadId: {0} , estudianteId: {1}", new Object[]{universidadId, estudianteId});
        if (estudianteLogic.getEstudiante(estudianteId) == null) {
            throw new WebApplicationException("El recurso /estudiantes/" + estudianteId + NO_EXISTE, 404);
        }
        EstudianteDTO estudianteDTO = new EstudianteDTO(universidadEstudiantesLogic.addEstudiante(estudianteId, universidadId));
        LOGGER.log(Level.INFO, "UniversidadEstudiantesResource addEstudiante: output: {0}", estudianteDTO);
        return estudianteDTO;
    }

    /**
     * Busca y devuelve todos los estudiantes que existen en la universidad.
     *
     * @param universidadId Identificador de la universidad que se esta buscando.
     * Este debe ser una cadena de dígitos.
     * @return JSONArray {@link EstudianteDetailDTO} - Los estudaintes encontrados en la
     * universidad. Si no hay ninguno retorna una lista vacía.
     */
    @GET
    public List<EstudianteDetailDTO> getEstudiantes(@PathParam("universidadId") Long universidadId) {
        LOGGER.log(Level.INFO, "UniversidadEstudiantesResource getEstudiantes: input: {0}", universidadId);
        List<EstudianteDetailDTO> listaDetailDTOs = estudiantesListEntity2DTO(universidadEstudiantesLogic.getEstudiantes(universidadId));
        LOGGER.log(Level.INFO, "UniversidadEstudiantesResource getEstudiantes: output: {0}", listaDetailDTOs);
        return listaDetailDTOs;
    }

    /**
     * Busca el estudiante con el id asociado dentro de la universidad con id asociado.
     *
     * @param universidadId Identificador de la universidad que se esta buscando.
     * Este debe ser una cadena de dígitos.
     * @param estudianteId Identificador del estudiante que se esta buscando. Este debe
     * ser una cadena de dígitos.
     * @return JSON {@link EstudianteDetailDTO} - El estudiante buscado
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el libro.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el estudiante en la
     * universidad.
     */
    @GET
    @Path("{estudianteId: \\d+}")
    public EstudianteDetailDTO getEstudiante(@PathParam("universidadId") Long universidadId, @PathParam("estudianteId") Long estudianteId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "UniversidadEstudiantesResource getEstudiante: input: universidadId: {0} , estudianteId: {1}", new Object[]{universidadId, estudianteId});
        if (estudianteLogic.getEstudiante(estudianteId) == null) {
            throw new WebApplicationException("El recurso /universidades/" + universidadId + "/estudiantes/" + estudianteId + NO_EXISTE, 404);
        }
        EstudianteDetailDTO estudianteDetailDTO = new EstudianteDetailDTO(universidadEstudiantesLogic.getEstudiante(universidadId, estudianteId));
        LOGGER.log(Level.INFO, "UniversidadEstudiantesResource getEstudiante: output: {0}", estudianteDetailDTO);
        return estudianteDetailDTO;
    }

    /**
     * Remplaza las instancias de Estudiante asociadas a una instancia de Universidad
     *
     * @param universidadId Identificador de la editorial que se esta
     * remplazando. Este debe ser una cadena de dígitos.
     * @param estudiantes JSONArray {@link EstudianteDetailDTO} El arreglo de estudiantes nuevo para la
     * universidad.
     * @return JSON {@link BookDTO} - El arreglo de estudiantes guardado en la
     * universidad.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el estudiante.
     */
    @PUT
    public List<EstudianteDetailDTO> replaceEstudiantes(@PathParam("universidadId") Long universidadId, List<EstudianteDetailDTO> estudiantes) {
        LOGGER.log(Level.INFO, "UniversidadEstudiantesResource replaceEstudiantes: input: universidadId: {0} , estudiantes: {1}", new Object[]{universidadId, estudiantes});
        for (EstudianteDetailDTO estudiante : estudiantes) {
            if (estudianteLogic.getEstudiante(estudiante.getId()) == null) {
                throw new WebApplicationException("El recurso /estudiantes/" + estudiante.getId() + NO_EXISTE, 404);
            }
        }
        List<EstudianteDetailDTO> listaDetailDTOs = estudiantesListEntity2DTO(universidadEstudiantesLogic.replaceEstudiantes(universidadId, estudiantesListDTO2Entity(estudiantes)));
        LOGGER.log(Level.INFO, "UniversidadEstudiantesResource replaceEstudiantes: output: {0}", listaDetailDTOs);
        return listaDetailDTOs;
    }

    /**
     * Convierte una lista de EstudianteEntity a una lista de EstudianteDetailDTO.
     *
     * @param entityList Lista de EstudianteEntity a convertir.
     * @return Lista de EstudianteDetailDTO convertida.
     */
    private List<EstudianteDetailDTO> estudiantesListEntity2DTO(List<EstudianteEntity> entityList) {
        List<EstudianteDetailDTO> list = new ArrayList();
        for (EstudianteEntity entity : entityList) {
            list.add(new EstudianteDetailDTO(entity));
        }
        return list;
    }

    /**
     * Convierte una lista de EstudianteDetailDTO a una lista de EstudianteEntity.
     *
     * @param dtos Lista de EstudianteDetailDTO a convertir.
     * @return Lista de EstudianteEntity convertida.
     */
    private List<EstudianteEntity> estudiantesListDTO2Entity(List<EstudianteDetailDTO> dtos) {
        List<EstudianteEntity> list = new ArrayList<>();
        for (EstudianteDetailDTO dto : dtos) {
            list.add(dto.toEntity());
        }
        return list;
    }
    
}
