/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.vivienda.resources;

import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import co.edu.uniandes.csw.vivienda.dtos.EstudianteDTO;
import co.edu.uniandes.csw.vivienda.dtos.ContratoDTO;
import co.edu.uniandes.csw.vivienda.entities.EstudianteEntity;
import co.edu.uniandes.csw.vivienda.ejb.EstudianteLogic;

/**
 *
 * @author estudiante
 */
@Path("estudinates")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class EstudianteResource {
    private static final Logger LOGGER = Logger.getLogger(EstudianteResource.class.getName());
    //@Inject
    //EstudianteLogic estudianteLogic;
    
    @POST
    public EstudianteDTO createEstudinate(EstudianteDTO estudiante){
        return estudiante;
        /**
        LOGGER.info("");
        EstudianteEntity newEntity = estudiante.toEntity();
        
        EstudianteEntity newEntity = estudianteLogic.createEditorial(entity);
        
        EstudianteDTO dto = new EstudianteDTO(newEntity);
        LOGGER.info("");
        return dto;
        */
    }
    
    @GET
    @Path("{estId:\\d+}")
    public EstudianteDTO getEstudiante(@PathParam("estId") Long estId) {
        return null;
    }
    
    @GET
    @Path("{estId:\\d+}/universidad")
    public EstudianteDTO getUniversidad(@PathParam("estId") Long estId) {
        return null;
    }
    
    @GET
    @Path("{estId:\\d+}/contrato/{contratoId:\\d+}")
    public ContratoDTO getContrato(@PathParam("estId") Long estId, @PathParam("contratoId") Long contratoId) {
        return null;
    }
    
    @POST
    @Path("{estId:\\d+}/contrato")
    public ContratoDTO createContrato(@PathParam("estId") Long estId, ContratoDTO contrato) {
        return contrato;
    }
    
    @GET
    public EstudianteDTO getEstudiantes() {
        return null;
    }
    
    @DELETE
    @Path("{estId:\\d+}")
    public void deleteEstudiantes(@PathParam("estId") Long estId) {
        
    }

}
