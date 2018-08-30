/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.vivienda.resources;

import co.edu.uniandes.csw.vivienda.dtos.ServicioAdicionalDTO;
import co.edu.uniandes.csw.vivienda.entities.ServicioAdicionalEntity;
import co.edu.uniandes.csw.vivienda.exceptions.BusinessLogicException;
import java.util.logging.*;
import javax.ws.rs.*;


@Path("serviciosAdicionales")
@Produces("application/json")
@Consumes("application/json")

public class ServicioAdicionalResource {
    
    private static final Logger LOGGER = Logger.getLogger(ServicioAdicionalResource.class.getName());
    
    @POST
    public ServicioAdicionalDTO createServicioAdicional(ServicioAdicionalDTO servicioAdicional) throws BusinessLogicException{
        LOGGER.log(Level.INFO, "ServicioAdicionalResource.createServicioAdicional: input:{0}", servicioAdicional.toString());
        ServicioAdicionalEntity servicioAdicionalEntity = servicioAdicional.toEntity();
        
        ServicioAdicionalDTO nuevoservicioAdicionalDTO = new ServicioAdicionalDTO(servicioAdicionalEntity);
        return nuevoservicioAdicionalDTO;
    }
}
