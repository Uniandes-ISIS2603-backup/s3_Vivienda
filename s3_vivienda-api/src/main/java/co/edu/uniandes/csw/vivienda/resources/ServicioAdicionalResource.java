/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.vivienda.resources;

import co.edu.uniandes.csw.vivienda.dtos.ServicioAdicionalDTO;
import co.edu.uniandes.csw.vivienda.entities.ServicioAdicionalEntity;
import co.edu.uniandes.csw.vivienda.exceptions.BusinessLogicException;
import java.util.Collection;
import java.util.logging.*;
import javax.ws.rs.*;

@Produces("application/json")
@Consumes("application/json")

public class ServicioAdicionalResource {
    
    private static final Logger LOGGER = Logger.getLogger(ServicioAdicionalResource.class.getName());
    
   // @Inject
   // private ReviewLogic reviewLogic;
    
    @POST
    public ServicioAdicionalDTO createServicioAdicional(@PathParam("viviendaId") Long viviendaId, ServicioAdicionalDTO servicio) throws BusinessLogicException{
       return null;
    }
    
    @GET
    public Collection <ServicioAdicionalDTO> getServiciosAdicionales(){
        return null;
    }
    
    @GET
    @Path("{servicioAdicionalId: \\d+}")
    public ServicioAdicionalDTO getServicioAdicional(@PathParam("servicioAdicionalId")Long servicioAdicionalId) throws WebApplicationException
    {
        return null;
    }
    
    @PUT
    @Path("{servicioAdicionalId: \\d+}")
    public ServicioAdicionalDTO updateServicioAdicional(@PathParam("servicioAdicionalId")Long servicioAdicionalId, ServicioAdicionalDTO servicioAdicional)throws WebApplicationException
    {
        return null;
    }
    
    @DELETE
    @Path("{servicioAdicionalId: \\d+}")
    public void deleteSitioInteres(@PathParam("sitioInteresId")Long sitioInteresId)throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "SitioInteresResource.deleteSitioInteres: input:{0}", sitioInteresId);
    }
}
