/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.vivienda.resources;

import co.edu.uniandes.csw.vivienda.dtos.CalificacionDTO;
import java.util.Collection;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author estudiante
 */
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class EstudianteCalificacionesResource{
    private static final Logger LOGGER = Logger.getLogger(EstudianteCalificacionesResource.class.getName());
    
    /*
    @POST
    public CalificacionDTO createCalificacion(@PathParam("estudianteId") Long estudianteId, CalificacionDTO calificacion){
        return calificacion;
    }
    */
    @GET
    public Collection <CalificacionDTO> getCalificaciones(@PathParam("estudianteId") Long estudianteId){
        return null;
    }
    
    @GET
    @Path("{calificacionId:\\d+}")
    public CalificacionDTO getCalificacion(@PathParam("estudianteId") Long estudianteId, @PathParam("calificacionId") Long calificacionId){
        return null;
    }
    @PUT
    @Path("{calificacionId:\\d+}")
    public CalificacionDTO updateCalificacion(@PathParam("estudianteId") Long estudianteId, @PathParam("calificacionId") Long calificacionId, CalificacionDTO calificacionDTO){
        return null;
    }
    
    @Path("{calificacionId:\\d+}")
    @DELETE
    public void deleteCalificacion(@PathParam("estudianteId") Long estudianteId, @PathParam("calificacionId") Long calificacionId){
        
    }
}
