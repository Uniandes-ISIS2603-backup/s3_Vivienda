/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.vivienda.resources;

import co.edu.uniandes.csw.vivienda.dtos.ContratoDTO;
import java.util.logging.Logger;
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
public class EstudianteContratoResourse {
    private static final Logger LOGGER = Logger.getLogger(EstudianteContratoResourse.class.getName());
    
    @Path("{contratoId:\\d+}")
    @POST
    public ContratoDTO addContrato(@PathParam("estudianteId") Long estudianteId, @PathParam("contratoId") Long contratoId){
        return null;
    }
    @Path("{contratoId:\\d+}")
    @PUT
    public ContratoDTO replaceContrato(@PathParam("estudianteId") Long estudianteId, @PathParam("contratoId") Long contratoId){
        return null;
    }
    @GET
    public ContratoDTO getContrato(@PathParam("estudianteId") Long estudianteId){
        return null;
    }
    @DELETE
    public void deleteContrato(@PathParam("estudianteId") Long estudianteId){

    }
    
}
