/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.vivienda.resources;

import co.edu.uniandes.csw.vivienda.dtos.EstudianteDetailDTO;
import co.edu.uniandes.csw.vivienda.dtos.UniversidadDTO;
import co.edu.uniandes.csw.vivienda.dtos.UniversidadDetailDTO;
import co.edu.uniandes.csw.vivienda.ejb.EstudianteLogic;
import co.edu.uniandes.csw.vivienda.ejb.EstudianteUniversidadLogic;
import co.edu.uniandes.csw.vivienda.ejb.UniversidadLogic;
import co.edu.uniandes.csw.vivienda.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.vivienda.mappers.*;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * Implementa el recurso /estudiantes/{id}/universidad
 *
 * @author Juan Manuel Castillo y Paula Molina
 */
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class EstudianteUniversidadResource {
    private static final Logger LOGGER = Logger.getLogger(EstudianteUniversidadResource.class.getName());

    @Inject
    EstudianteLogic estudianteLogic;  // Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.

    @Inject
    UniversidadLogic universidadLogic;  // Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.

    @Inject
    private EstudianteUniversidadLogic estudianteUniversidadLogic; // Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.

    /**
     * Remplaza la instancia de Universidad asociada a un Estudiante.
     *
     * @param estudianteId  Identificador del estudinate que se está actualizando.
     * @param universidadId La universidad que estará asociada al estudiante.
     * @return JSON {@link EstudianteDetailDTO} - El estudiante actualizado
     * @throws co.edu.uniandes.csw.vivienda.exceptions.BusinessLogicException -
     *                                                                        Error de lógica que se genera cuando no se encuentra la universidad.
     * @throws WebApplicationException                                        {@link WebApplicationExceptionMapper} -
     *                                                                        Error de lógica que se genera cuando no se encuentra la universidad.
     */
    @PUT
    @Path("{universidadId:\\d+}")
    public EstudianteDetailDTO replaceUniversidad(@PathParam("estudianteId") Long estudianteId, @PathParam("universidadId") Long universidadId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "EstudianteUniversidadResource replaceUniversidad: input: estudianteId{0} , Universidad:{1}", new Object[]{estudianteId, universidadId});
        if (universidadLogic.getUniversidad(universidadId) == null) {
            throw new WebApplicationException("El recurso /universidades/" + universidadId + " no existe.", 404);
        }
        EstudianteDetailDTO estudianteDetailDTO = new EstudianteDetailDTO(estudianteUniversidadLogic.replaceUniversidad(estudianteId, universidadId));
        LOGGER.log(Level.INFO, "EstudianteUniversidadResource replaceUniversidad: output: {0}", estudianteDetailDTO);
        return estudianteDetailDTO;
    }

    /**
     * Busca la universidad dentro del estudiante con id asociado.
     *
     * @param estudianteId Identificador del estudinate con la universidad que se está buscando.
     * @return JSON {@link UniversidadDTO} - La universidad buscada
     */
    @GET
    public UniversidadDetailDTO getUniversidad(@PathParam("estudianteId") Long estudianteId) {
        LOGGER.log(Level.INFO, "EstudianteUniversidadResource getUniversidad: input: estudianteID: {0}", new Object[]{estudianteId});
        UniversidadDetailDTO universidadDTO = new UniversidadDetailDTO(estudianteLogic.getEstudiante(estudianteId).getUniversidad());
        LOGGER.log(Level.INFO, "EstudianteUniversidadResource getUniversidad: output: {0}", universidadDTO);
        return universidadDTO;
    }
}
