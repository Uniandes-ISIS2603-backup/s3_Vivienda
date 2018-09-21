/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.vivienda.resources;

import co.edu.uniandes.csw.vivienda.dtos.ContratoDTO;
import co.edu.uniandes.csw.vivienda.dtos.ContratoDetailDTO;
import co.edu.uniandes.csw.vivienda.ejb.ContratoLogic;
import co.edu.uniandes.csw.vivienda.entities.ContratoEntity;
import co.edu.uniandes.csw.vivienda.exceptions.BusinessLogicException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;

/**
 *
 * @Author estudiante
 */

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class EstudianteContratoResource {
    private static final Logger LOGGER = Logger.getLogger(EstudianteContratoResource.class.getName());
    
    @Inject
    private ContratoLogic contratoLogic; // Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.

    /* Guarda un contrato dentro de un premio con la informacion que recibe el la
     * URL.
    /**
     *
     * @param estudianteId Identificador de el premio que se esta actualizando. Este
     * debe ser una cadena de dígitos.
     * @param contratoId Identificador del autor que se desea guardar. Este debe
     * ser una cadena de dígitos.
     * @return JSON {@link ContratoDTO} - El autor guardado en el premio.
     * @throws co.edu.uniandes.csw.vivienda.exceptions.BusinessLogicException
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el autor.
     
    @POST
    @Path("{contratoId: \\d+}")
    public EstudianteDetailDTO addContrato(@PathParam("estudianteId") Long estudianteId, @PathParam("contratoId") Long contratoId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "EstudianteContratoResource addContrato: input: estudiantesID: {0} , contratoId: {1}", new Object[]{estudianteId, contratoId});
        if (contratoLogic.getContrato(contratoId) == null) {
            throw new WebApplicationException("El recurso estudiantes/"+ estudianteId+"/contratos/" + contratoId + " no existe.", 404);
        }
        EstudianteDetailDTO estudianteDTO = new EstudianteDetailDTO(estudianteLogic.addContrato(contratoId, estudianteId));
        LOGGER.log(Level.INFO, "EstudianteContratoResource addContrato: output: {0}", estudianteDTO.toString());
        return estudianteDTO;
    }
    */
    
    /**
     * Busca el autor dentro de el premio con id asociado.
     *
     * @param estudianteId Identificador de el premio que se esta buscando. Este
     * debe ser una cadena de dígitos.
     * @return JSON {@link ContratoDetailDTO} - El contrato buscado
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando el premio no tiene autor.
     */
    @GET
    public ContratoDTO getContrato(@PathParam("estudianteId") Long estudianteId) {
        LOGGER.log(Level.INFO, "EstudianteContratoResource getContrato: input: {0}", estudianteId);
        LOGGER.log(Level.INFO, "Inicia proceso de consultar del contrato del estudiante");
        try {
            ContratoEntity contratoEntity = contratoLogic.getContratoByEstudiante(estudianteId);
            ContratoDTO contratoDTO = new ContratoDTO(contratoEntity);
            LOGGER.log(Level.INFO, "EstudianteContratoResource getContrato: output: {0}", contratoDTO.toString());
            return contratoDTO;
        }
        catch (BusinessLogicException e){
            throw new WebApplicationException("El recurso /estudiantes/" + estudianteId + "/contrato no existe.", 404);
        }    
    }

    /**
     * Remplaza la instancia de Contrato asociada a una instancia de Estudiante
     *
     * @param estudianteId Identificador de el premio que se esta actualizando. Este
     * debe ser una cadena de dígitos.
     * @param contratoId Identificador de el contrato que se esta remplazando. Este
     * debe ser una cadena de dígitos.
     * @return JSON {@link ContratoDetailDTO} - El autor actualizado
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el autor.
     
    @PUT
    @Path("{contratoId: \\d+}")
    public EstudianteDetailDTO replaceContrato(@PathParam("estudianteId") Long estudianteId, @PathParam("contratoId") Long contratoId) {
        LOGGER.log(Level.INFO, "EstudianteContratoResource replaceContrato: input: estudianteId: {0} , contratoId: {1}", new Object[]{estudianteId, contratoId});
        if (contratoLogic.getContrato(contratoId) == null) {
            throw new WebApplicationException("El recurso /contratos/" + contratoId + " no existe.", 404);
        }
        EstudianteDetailDTO estudianteDetailDTO = new EstudianteDetailDTO(estudianteLogic.replaceContrato(estudianteId, contratoId));
        LOGGER.log(Level.INFO, "EstudianteContratoResource replaceContrato: output: {0}", estudianteDetailDTO.toString());
        return estudianteDetailDTO;
    }
    */

    /**
     * Elimina la conexión entre el autor y el premio recibido en la URL.
     *
     * @param estudianteId El ID del premio al cual se le va a desasociar el autor
     * @throws co.edu.uniandes.csw.vivienda.exceptions.BusinessLogicException 
     * cuando el estudiante no tiene contrato.
     */
    @DELETE
    public void deleteContrato(@PathParam("estudianteId") Long estudianteId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "EstudianteContratoResource deleteContrato: input: {0}", estudianteId);
        try{
            ContratoEntity contrato = contratoLogic.getContratoByEstudiante(estudianteId);
            contratoLogic.deleteContrato(contrato.getId());
            LOGGER.info("EstudianteContratoResource deleteContrato: output: void");
        }
        catch (BusinessLogicException e){
            throw new WebApplicationException("El recurso estudiantes/"+estudianteId+"/contrato no existe.", 404);
        }
        
    }
    
}
