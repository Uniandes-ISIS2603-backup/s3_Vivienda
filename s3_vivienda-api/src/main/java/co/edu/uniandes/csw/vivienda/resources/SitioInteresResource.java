/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.vivienda.resources;

import co.edu.uniandes.csw.vivienda.dtos.SitioInteresDTO;
import co.edu.uniandes.csw.vivienda.entities.SitioInteresEntity;
import co.edu.uniandes.csw.vivienda.exceptions.BusinessLogicException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

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
    
     @POST
    public SitioInteresDTO createSitioInteres(SitioInteresDTO sitioInteres) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "EditorialResource createEditorial: input: {0}", sitioInteres.toString());
        // Convierte el DTO (json) en un objeto Entity para ser manejado por la lógica.
        SitioInteresEntity sitioInteresEntity = sitioInteres.toEntity();
 
        SitioInteresDTO nuevoSitioInteres = new SitioInteresDTO(sitioInteresEntity);
        return nuevoSitioInteres;
    }
    
}
