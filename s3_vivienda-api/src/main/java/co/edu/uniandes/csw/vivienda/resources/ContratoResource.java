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
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;


@Path("contratos")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class ContratoResource {
    
    @Inject
    ContratoLogic contratoLogic;
        
    private static final Logger LOGGER = Logger.getLogger(ContratoResource.class.getName());
        
    @POST
    public ContratoDTO createContrato(ContratoDTO contrato) throws BusinessLogicException{
        LOGGER.log(Level.INFO, "ContratoResource.createContrato: input:{0}", contrato.toString());
        
        // Convierte el DTO (json) en un objeto Entity para ser manejado por la lógica.
        ContratoEntity contratoEntity = contrato.toEntity();
        // Invoca la lógica para crear la editorial nueva
        ContratoEntity nuevoContratoEntity = contratoLogic.createContrato(contratoEntity);
        // Como debe retornar un DTO (json) se invoca el constructor del DTO con argumento el entity nuevo
        ContratoDTO nuevoContratoDTO = new ContratoDTO(nuevoContratoEntity);
        LOGGER.log(Level.INFO, "ContratoResource createContrato: output: {0}", nuevoContratoDTO.toString());
        return nuevoContratoDTO;
    }
    
    @DELETE
    @Path("{contratoId: \\d+}")
    public void deleteContrato(@PathParam("contratoId") Long contratoId){
        LOGGER.log(Level.INFO, "ContratoResource.deleteContrato: input:{0}", contratoId);
        
    }
}
