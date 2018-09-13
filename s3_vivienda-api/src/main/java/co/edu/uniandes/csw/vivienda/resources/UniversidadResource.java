/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.vivienda.resources;

import co.edu.uniandes.csw.vivienda.dtos.UniversidadDTO;
import co.edu.uniandes.csw.vivienda.ejb.UniversidadLogic;
import co.edu.uniandes.csw.vivienda.entities.UniversidadEntity;
import co.edu.uniandes.csw.vivienda.exceptions.BusinessLogicException;
import java.util.List;
import java.util.logging.*;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;


@Path("universidades")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped

/**
 * Clase que implementa el recurso "Universidad".
 *
 * @author Paula Molina Ruiz
 */
public class UniversidadResource {
    
    private static final Logger LOGGER = Logger.getLogger(UniversidadResource.class.getName());
    
   @Inject
   UniversidadLogic universidadLogic; // Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.

    
    @POST
    public UniversidadDTO createUniversidad(UniversidadDTO universidad) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "UniversidadResource createUniversidad: input: {0}", universidad.toString());
        // Convierte el DTO (json) en un objeto Entity para ser manejado por la lógica.
        UniversidadEntity universidadEntity = universidad.toEntity();
        UniversidadDTO nuevaUniversidadDTO = new UniversidadDTO(universidadEntity);
        LOGGER.log(Level.INFO, "UniversidadResource createUniversidad: output: {0}", nuevaUniversidadDTO.toString());
        return nuevaUniversidadDTO;
    }

    @GET
    public List<UniversidadDTO> getUniversidades() {
         return null;
    }

    @GET
    @Path("{universidadId: \\d+}")
    public UniversidadDTO getUniversidad(@PathParam("universidadId") Long universidadId) throws WebApplicationException {
        
        return null;
    }

    @PUT
    @Path("{universidadId: \\d+}")
    public UniversidadDTO updateuniversidad(@PathParam("universidadId") Long universidadId, UniversidadDTO uiversidad) throws WebApplicationException {
        return null;
    }

    @DELETE
    @Path("{universidadId: \\d+}")
    public void deleteUniversidad(@PathParam("universidadId") Long universidadId) throws BusinessLogicException 
    {
        LOGGER.log(Level.INFO, "UniversidadResource.deleteUniversidad: input:{0}", universidadId);
    }   
}