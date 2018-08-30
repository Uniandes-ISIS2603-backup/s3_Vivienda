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
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import co.edu.uniandes.csw.vivienda.dtos.EstudianteDTO;
import co.edu.uniandes.csw.vivienda.dtos.EstudianteDetailDTO;
import co.edu.uniandes.csw.vivienda.dtos.UniversidadDTO;
import co.edu.uniandes.csw.vivienda.dtos.ContratoDTO;
import co.edu.uniandes.csw.vivienda.entities.EstudianteEntity;
import co.edu.uniandes.csw.vivienda.ejb.EstudianteLogic;
import java.util.Collection;

/**
 *
 * @author estudiante
 */
@Path("estudiantes")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class EstudianteResource {
    private static final Logger LOGGER = Logger.getLogger(EstudianteResource.class.getName());
    //@Inject
    //EstudianteLogic estudianteLogic;
    
    @POST
    public EstudianteDTO createEstudiante(EstudianteDTO estudianteDTO){
        return estudianteDTO;
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
    public Collection <EstudianteDetailDTO> getEstudiantes() {
        return null;
    }
    
    @GET
    @Path("{estudianteId:\\d+}")
    public EstudianteDetailDTO getEstudiante(@PathParam("estudianteId") Long estudianteId) {
        return null;
    }
    
    @PUT
    @Path("{estudianteId:\\d+}")
    public EstudianteDetailDTO updateEstudiante(@PathParam("estudianteId") Long estudianteId, EstudianteDTO estudianteDTO) {
        return null;
    }
    
    @DELETE
    @Path("{estudianteId:\\d+}")
    public void deleteEstudiantes(@PathParam("estudianteId") Long estudianteId) {
        
    }
}
