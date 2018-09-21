/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.vivienda.resources;

import co.edu.uniandes.csw.vivienda.dtos.ContratoDTO;
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
 * @author estudiante
 */

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class EstudianteContratoResource2 {
    private static final Logger LOGGER = Logger.getLogger(EstudianteContratoResource.class.getName());
    
    @Inject
    ContratoLogic contratoLogic;
    
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
