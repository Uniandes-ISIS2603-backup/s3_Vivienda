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

@Path("{viviendaId:\\d+}/calificaciones")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class CalificacionResource{
    private static final Logger LOGGER = Logger.getLogger(EstudianteResource.class.getName());
    
    @POST
    public CalificacionDTO createCalificacion(CalificacionDTO calificacion){
        return calificacion;
    }
    
    @GET
    public CalificacionDTO getCalificacion(){
        return null;
    }
    
    @GET
    @Path("{calificacionId:\\d+}")
    public CalificacionDTO getCalificacion(@PathParam("calificacionId") Long calificacionId){
        return null;
    }
    
    @DELETE
    public void deleteCalificacion(){
        
    }
}
