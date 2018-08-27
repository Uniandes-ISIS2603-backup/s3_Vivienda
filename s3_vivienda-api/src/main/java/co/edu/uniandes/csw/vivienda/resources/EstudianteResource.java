/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.vivienda.resources;

import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import co.edu.uniandes.csw.vivienda.dtos.EstudianteDTO;
import co.edu.uniandes.csw.vivienda.dtos.EstudianteEntity;

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
    @Inject
    EstudinateLogic estudianteLogic;
    
    @POST
    public EstudinateDTO createEstudinate(EstudianteDTO estudiante){
        
        EstudianteEntity entity = estudiante.toEntity();
        
        EstudianteEntity newEntity = estudianteLogic.createEditorial(entity);
        EstudinateDTO dto = new EstudianteDTO(newEntity)
        return dto;
    }
    
    
}
