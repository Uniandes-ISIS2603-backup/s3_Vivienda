/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.vivienda.resources;

import co.edu.uniandes.csw.vivienda.dtos.SitioInteresDTO;
import co.edu.uniandes.csw.vivienda.dtos.SitioInteresDetailDTO;
import co.edu.uniandes.csw.vivienda.ejb.SitioInteresLogic;
//import co.edu.uniandes.csw.vivienda.ejb.SitioInteresLogic;
import co.edu.uniandes.csw.vivienda.entities.SitioInteresEntity;
import co.edu.uniandes.csw.vivienda.exceptions.BusinessLogicException;
import java.util.ArrayList;
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
    
    @Inject 
    SitioInteresLogic sitioInteresLogic;
    
    @POST
    public SitioInteresDetailDTO createSitioInteres( SitioInteresDTO sitioInteres) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "EditorialResource createEditorial: input: {0}", sitioInteres.toString());
        // Convierte el DTO (json) en un objeto Entity para ser manejado por la lógica.
        SitioInteresEntity sitioInteresEntity = sitioInteres.toEntity();
        
        SitioInteresEntity nuevoSitioInteresEntity = sitioInteresLogic.createSitioInteres(sitioInteresEntity);
 
        SitioInteresDetailDTO nuevoSitioInteres = new SitioInteresDetailDTO(nuevoSitioInteresEntity);
        return nuevoSitioInteres;
    }
    
    @GET
    public List<SitioInteresDetailDTO> getSitiosInteres(@PathParam("sitioInteresId")Long sitioInteresId) throws WebApplicationException
    {
        List<SitioInteresEntity> lista = sitioInteresLogic.getSitiosInteres();
        List<SitioInteresDetailDTO> resp = new ArrayList<SitioInteresDetailDTO>();
        
        for (SitioInteresEntity ent: lista){
            SitioInteresDetailDTO sitioInteresDto = new SitioInteresDetailDTO(ent);
            resp.add(sitioInteresDto);
        }
        return resp;
    }
    
    @GET
    @Path("{sitioInteresId: \\d+}")
    public SitioInteresDetailDTO getSitioInteres(@PathParam("viviendaId") Long viviendaId, @PathParam("sitiointeresId") Long sitioInteresId) throws BusinessLogicException {
       // LOGGER.log(Level.INFO, "ReviewResource getReview: input: {0}", reviewsId);
        SitioInteresEntity entity = sitioInteresLogic.getSitioInteres(viviendaId, sitioInteresId);
        if (entity == null) {
            throw new WebApplicationException("El recurso /viviendas/" + viviendaId + "/sitioInteres/" + sitioInteresId + " no existe.", 404);
        }
        SitioInteresDetailDTO sitioInteresDTO = new SitioInteresDetailDTO(entity);
        LOGGER.log(Level.INFO, "SitioInteresResource getSitioInteres: output: {0}", sitioInteresDTO.toString());
        return sitioInteresDTO;
    }
    
    @PUT
    @Path("{sitioInteresId: \\d+}")
    public SitioInteresDetailDTO updateSitioInteres(@PathParam("viviendaId") Long viviendaId, @PathParam("sitioInteresId")Long sitioInteresId, SitioInteresDTO sitioInteres)throws WebApplicationException, BusinessLogicException
    {
        LOGGER.log(Level.INFO, "SitioInteresResource updateSitioInteres: input: viviendaId: {0} , sitioInteresId: {1} , review:{2}", new Object[]{viviendaId, sitioInteresId, sitioInteres.toString()});
        if (sitioInteresId.equals(sitioInteres.getId())) {
            throw new BusinessLogicException("Los ids del sitio no coinciden.");
        }
        SitioInteresEntity entity = sitioInteresLogic.getSitioInteres(viviendaId, sitioInteresId);
        if (entity == null) {
            throw new WebApplicationException("El recurso /viviendas/" + viviendaId + "/sitioInteres/" + sitioInteresId + " no existe.", 404);

        }
        SitioInteresDetailDTO sitioInteresDTO = new SitioInteresDetailDTO(sitioInteresLogic.updateSitioInteres(viviendaId, sitioInteres.toEntity()));
        LOGGER.log(Level.INFO, "SitioInteresResource updateSitioInteres: output:{0}", sitioInteresDTO.toString());
        return sitioInteresDTO;
    }
    
    @DELETE
    @Path("{sitioInteresId: \\d+}")
    public void deleteSitioInteres(@PathParam("viviendaId") Long viviendaId, @PathParam("sitioInteresId")Long sitioInteresId)throws BusinessLogicException
    {
        SitioInteresEntity entity = sitioInteresLogic.getSitioInteres(viviendaId, sitioInteresId);
        if (entity == null) {
            throw new WebApplicationException("El recurso /viviendas/" + viviendaId + "/sitioInteres/" + sitioInteresId + " no existe.", 404);
        }
        sitioInteresLogic.deleteSitioInteres(viviendaId, sitioInteresId);
    }
    
 
    
}
