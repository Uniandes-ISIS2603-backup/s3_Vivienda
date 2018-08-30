/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.vivienda.resources;

import co.edu.uniandes.csw.vivienda.dtos.UniversidadDTO;
import java.util.logging.Logger;
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
    
    @POST
    @Path("{universidadId:\\d+}")
    public UniversidadDTO addUniversidad(@PathParam("estudianteId") Long estudianteId, @PathParam("universidadId") Long universidadId){
        return null;
    }
    @PUT
    @Path("{universidadId:\\d+}")
    public UniversidadDTO replaceUniversidad(@PathParam("estudianteId") Long estudianteId, @PathParam("universidadId") Long universidadId){
        return null;
    }
    @GET
    public UniversidadDTO getUniversidad(@PathParam("estudianteId") Long estudianteId){
        return null;
    }
    @DELETE
    public void deleteUniversidad(@PathParam("estudianteId") Long estudianteId){

    }
}
