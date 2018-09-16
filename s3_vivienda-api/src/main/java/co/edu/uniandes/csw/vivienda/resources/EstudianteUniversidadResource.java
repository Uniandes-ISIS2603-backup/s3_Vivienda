/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.vivienda.resources;

import co.edu.uniandes.csw.vivienda.dtos.EstudianteDetailDTO;
import co.edu.uniandes.csw.vivienda.dtos.UniversidadDTO;
import co.edu.uniandes.csw.vivienda.ejb.EstudianteLogic;
import co.edu.uniandes.csw.vivienda.ejb.UniversidadLogic;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * Implementa el recurso /estudiantes/{id}/universidad
 * @author estudiante
 */
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class EstudianteUniversidadResource {
    private static final Logger LOGGER = Logger.getLogger(EstudianteUniversidadResource.class.getName());
    
    @Inject
    EstudianteLogic estudianteLogic;  // Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.
    
    @Inject
    UniversidadLogic universidadLogic;  // Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.
    
    /**
     * Remplaza la instancia de Universidad asociada a un Estudiante.
     *
     * @param estudianteId Identificador del estudinate que se está actualizando.
     * @param universidadId La universidad que estará asociada al estudiante.
     * @return JSON {@link EstudianteDetailDTO} - El arreglo de libros guardado en la
     * universidad.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la universidad o el
     * libro.
     */
    @PUT
    @Path("{universidadId:\\d+}")
    public EstudianteDetailDTO replaceUniversidad(@PathParam("estudianteId") Long estudianteId, @PathParam("universidadId") Long universidadId){
        LOGGER.log(Level.INFO, "EstudianteUniversidadResource replaceUniversidad: input: estudianteId{0} , Universidad:{1}", new Object[]{estudianteId, universidadId});
        if (universidadLogic.getUniversidad(universidadId) == null) {
            throw new WebApplicationException("El recurso /universidades/" + universidadId + " no existe.", 404);
        }
        EstudianteDetailDTO estudianteDetailDTO = new EstudianteDetailDTO(estudianteLogic.replaceUniversidad(estudianteId, universidadId));
        LOGGER.log(Level.INFO, "EstudianteUniversidadResource replaceUniversidad: output: {0}", estudianteDetailDTO.toString());
        return estudianteDetailDTO;
    }
    
    /**
     * Busca la universidad con el id asociado dentro de la estudiante con id asociado.
     *
     * @param estudianteId Identificador del estudinate que se esta buscando.
     * @return JSON {@link UniversidadDTO} - La universidad buscada
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el estudiante.
     */
    @GET
    public UniversidadDTO getUniversidad(@PathParam("estudianteId") Long estudianteId){
        LOGGER.log(Level.INFO, "EstudianteUniversidadResource getUniversidad: input: estudianteID: {0}", new Object[]{estudianteId});
        if (estudianteLogic.getEstudiante(estudianteId) == null) {
            throw new WebApplicationException("El recurso /estudiantes/" + estudianteId + " no existe.", 404);
        }
        UniversidadDTO universidadDTO = new UniversidadDTO(estudianteLogic.getEstudiante(estudianteId).getUniversidad());
        LOGGER.log(Level.INFO, "EstudianteUniversidadResource getUniversidad: output: {0}", universidadDTO.toString());
        return universidadDTO;
    }
}
