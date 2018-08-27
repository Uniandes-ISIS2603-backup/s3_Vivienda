/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.vivienda.resources;

import co.edu.uniandes.csw.vivienda.dtos.ContratoDTO;
import co.edu.uniandes.csw.vivienda.entities.ContratoEntity;
import co.edu.uniandes.csw.vivienda.exceptions.BusinessLogicException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
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
    
    private static final Logger LOGGER = Logger.getLogger(ViviendaResource.class.getName());
        
    @POST
    public ContratoDTO createContrato(ContratoDTO contrato) throws BusinessLogicException{
        LOGGER.log(Level.INFO, "ContratoResource.createContrato: input:{0}", contrato.toString());
        ContratoEntity contratoEntity = contrato.toEntity();
        
        ContratoDTO nuevoContratoDTO = new ContratoDTO(contratoEntity);
        return nuevoContratoDTO;
    }
    
    @DELETE
    public void deleteContrato(@PathParam("contratoId") Long contratoId){
        LOGGER.log(Level.INFO, "ContratoResource.deleteContrato: input:{0}", contratoId);
        
    }
}
