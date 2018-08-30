/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.vivienda.resources;
import co.edu.uniandes.csw.vivienda.entities.CalificacionEntity;
import co.edu.uniandes.csw.vivienda.dtos.CalificacionDTO;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import java.util.Collection;
import javax.ws.rs.PUT;

@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class ViviendaCalificacionesResource{
    private static final Logger LOGGER = Logger.getLogger(ViviendaCalificacionesResource.class.getName());
    
    @POST
    public CalificacionDTO createCalificacion(@PathParam("viviendaId") Long viviendaId, CalificacionDTO calificacion){
        return calificacion;
    }
    
    @GET
    public Collection <CalificacionDTO> getCalificaciones(@PathParam("viviendaId") Long viviendaId){
        return null;
    }
    
    @GET
    @Path("{calificacionId:\\d+}")
    public CalificacionDTO getCalificacion(@PathParam("viviendaId") Long viviendaId, @PathParam("calificacionId") Long calificacionId){
        return null;
    }
    @PUT
    @Path("{calificacionId:\\d+}")
    public CalificacionDTO updateCalificacion(@PathParam("viviendaId") Long viviendaId, @PathParam("calificacionId") Long calificacionId, CalificacionDTO calificacionDTO){
        return null;
    }
    
    @Path("{calificacionId:\\d+}")
    @DELETE
    public void deleteCalificacion(@PathParam("viviendaId") Long viviendaId, @PathParam("calificacionId") Long calificacionId){
        
    }
}
