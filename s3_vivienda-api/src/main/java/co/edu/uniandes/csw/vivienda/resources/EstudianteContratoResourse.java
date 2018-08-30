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
    
    @POST
    public ContratoDTO createContrato(@PathParam("estudianteId") Long estudianteId, ContratoDTO contratoDTO){
        return contratoDTO;
    }
    @GET
    public ContratoDTO getContrato(@PathParam("estudianteId") Long estudianteId){
        return null;
    }
    @DELETE
    public void deleteContrato(@PathParam("estudianteId") Long estudianteId){

    }
    
}
