/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.vivienda.resources;

import co.edu.uniandes.csw.vivienda.dtos.UniversidadDTO;
import co.edu.uniandes.csw.vivienda.entities.UniversidadEntity;
import co.edu.uniandes.csw.vivienda.exceptions.BusinessLogicException;
import java.util.logging.*;
import javax.ws.rs.*;


@Path("universidades")
@Produces("application/json")
@Consumes("application/json")

/**
 *
 * @author estudiante
 */
public class UniversidadResource {
    
    private static final Logger LOGGER = Logger.getLogger(UniversidadResource.class.getName());
    
    @POST
    public UniversidadDTO createUniversidad(UniversidadDTO universidad) throws BusinessLogicException{
        LOGGER.log(Level.INFO, "UniversidadResource.createUniversidad: input:{0}", universidad.toString());
        UniversidadEntity universidadEntity = universidad.toEntity();
        
        UniversidadDTO nuevaUniversidadDTO = new UniversidadDTO(universidadEntity);
        return nuevaUniversidadDTO;
    }
    
}
