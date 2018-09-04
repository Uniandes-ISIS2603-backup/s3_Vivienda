/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.vivienda.resources;

import co.edu.uniandes.csw.vivienda.dtos.SitioInteresDTO;
//import co.edu.uniandes.csw.vivienda.ejb.SitioInteresLogic;
import co.edu.uniandes.csw.vivienda.entities.SitioInteresEntity;
import co.edu.uniandes.csw.vivienda.exceptions.BusinessLogicException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
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
@Path("sitioInteres")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class SitioInteresResource {
    private static final Logger LOGGER = Logger.getLogger(SitioInteresResource.class.getName());
    
//    @Inject 
//    SitioInteresLogic sitioInteresLogic;
    
    @POST
    public SitioInteresDTO createSitioInteres(SitioInteresDTO sitioInteres) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "EditorialResource createEditorial: input: {0}", sitioInteres.toString());
        // Convierte el DTO (json) en un objeto Entity para ser manejado por la lógica.
        SitioInteresEntity sitioInteresEntity = sitioInteres.toEntity();
 
        SitioInteresDTO nuevoSitioInteres = new SitioInteresDTO(sitioInteresEntity);
        return nuevoSitioInteres;
    }
    
    @GET
    @Path("{sitioInteresId: \\d+}")
    public List<SitioInteresDTO> getSitioInteres(@PathParam("sitioInteresId")Long sitioInteresId) throws WebApplicationException
    {
        return null;
    }
    
    @PUT
    @Path("{sitioInteresId: \\d+}")
    public SitioInteresDTO updateSitioInteres(@PathParam("sitioInteresId")Long sitioInteresId, SitioInteresDTO sitioInteres)throws WebApplicationException
    {
        return null;
    }
    
    @DELETE
    @Path("{sitioInteresId: \\d+}")
    public void deleteSitioInteres(@PathParam("sitioInteresId")Long sitioInteresId)throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "SitioInteresResource.deleteSitioInteres: input:{0}", sitioInteresId);
    }
    
 
    
}
