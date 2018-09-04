/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.vivienda.resources;

import co.edu.uniandes.csw.vivienda.dtos.ArrendadorDTO;
import co.edu.uniandes.csw.vivienda.entities.ArrendadorEntity;
import co.edu.uniandes.csw.vivienda.exceptions.BusinessLogicException;
import java.util.List;
import java.util.logging.Level;
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
import javax.ws.rs.WebApplicationException;

/**
 *
 * @author estudiante
 */
@Path("arrendadores")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class ArrendadorResource {
    
    private static final Logger LOGGER = Logger.getLogger(ArrendadorResource.class.getName());
    
     @POST
    public ArrendadorDTO createSitioInteres(ArrendadorDTO arrendador) throws BusinessLogicException {
         
        LOGGER.log(Level.INFO, "EditorialResource createEditorial: input: {0}", arrendador.toString());
        // Convierte el DTO (json) en un objeto Entity para ser manejado por la lógica.
        ArrendadorEntity arrendadorEntity = arrendador.toEntity();
 
        ArrendadorDTO nuevoArrendador = new ArrendadorDTO(arrendadorEntity);
        return nuevoArrendador;
    }
    
    @GET
    @Path("{arrendadorId:\\d+}")
    public List<ArrendadorDTO> getArrendador(@PathParam("arrendadorId")Long arrendadorId)throws WebApplicationException
    {
        return null;
    }
    
    @PUT
    @Path("{arrendadorId:\\d+}")
    public ArrendadorDTO updateArrendador(@PathParam("arrendadorId")Long arrendadorId, ArrendadorDTO arrendador)throws WebApplicationException
    {
        return null;
    }
    
    @DELETE
    @Path("{arrendadorId:\\d+}")
    public void deleteArrendador(@PathParam("arrendadorId")Long arrendadorId)throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "ArrendadorResource.deleteArrendador: input:{0}", arrendadorId);
    }
}
