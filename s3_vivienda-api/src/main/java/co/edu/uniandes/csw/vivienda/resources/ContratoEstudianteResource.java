/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.vivienda.resources;

import co.edu.uniandes.csw.vivienda.dtos.ContratoDTO;
import co.edu.uniandes.csw.vivienda.dtos.ViviendaDTO;
import co.edu.uniandes.csw.vivienda.ejb.ContratoEstudianteLogic;
import co.edu.uniandes.csw.vivienda.ejb.ContratoLogic;
import co.edu.uniandes.csw.vivienda.ejb.EstudianteLogic;
import co.edu.uniandes.csw.vivienda.mappers.WebApplicationExceptionMapper;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Carlos Infante
 */
@Path("contratos/{contratoId: \\d+}/estudiante")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ContratoEstudianteResource {
    
    private static final Logger LOGGER = Logger.getLogger(ContratoViviendaResource.class.getName());

    @Inject
    private ContratoLogic contratoLogic; // Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.

    @Inject
    private ContratoEstudianteLogic contratoEstudianteLogic; // Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.

    @Inject
    private EstudianteLogic estudianteLogic; // Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.

    /**
     * Remplaza la instancia de Estudiante asociada a un Contrato.
     *
     * @param estudianteId Identificador del contrato que se esta actualizando. Este
     * debe ser una cadena de dígitos.
     * @param contrato El contrato  que se será del contrato.
     * @return JSON {@link ContratoDTO} - El arreglo de contratos guardado en la
     * vivienda.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el estudiante o el
     * contrato.
     */
    @PUT
    public ContratoDTO replaceEstudiante(@PathParam("estudianteId") Long estudianteId, ViviendaDTO contrato) {
        LOGGER.log(Level.INFO, "ContratoEstudianteResource replaceEstudiante: input: contratoId{0} , estudiante:{1}", new Object[]{estudianteId, contrato});
        if (contratoLogic.getContrato(estudianteId) == null)
        {
            throw new WebApplicationException("El recurso /contratos/" + estudianteId + " no existe.", 404);
        }
        if (estudianteLogic.getEstudiante(contrato.getId()) == null) {
            throw new WebApplicationException("El recurso /estudiante/" + contrato.getId() + " no existe.", 404);
        }
        ContratoDTO contratoDTO = new ContratoDTO(contratoEstudianteLogic.replaceEstudiante(estudianteId, contrato.getId()));
        LOGGER.log(Level.INFO, "ContratoEstudianteResource replaceEstudiante: output: {0}", contratoDTO);
        return contratoDTO;
    }
}
