/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.vivienda.resources;

import co.edu.uniandes.csw.vivienda.dtos.UniversidadDTO;
import co.edu.uniandes.csw.vivienda.entities.UniversidadEntity;
import co.edu.uniandes.csw.vivienda.exceptions.BusinessLogicException;
import java.util.logging.*;
import javax.ws.rs.*;


@Path("estudiantes/(estudianteId:")
@Produces("application/json")
@Consumes("application/json")

/**
 * Clase que implementa el recurso "Universidad".
 *
 * @author Paula Molina Ruiz
 */
public class UniversidadResource {
    
    private static final Logger LOGGER = Logger.getLogger(UniversidadResource.class.getName());
    
    /**
     * Crea una nueva universidad con la informacion que se recibe en el cuerpo de la
     * petición y se regresa un objeto identico con un id auto-generado por la
     * base de datos.
     *
     * @param estudiantesId El ID del estudiante al cual se le crea la universidad
     * @param universidad {@link UniversidadDTO} - La universidad que se desea guardar.
     * @return JSON {@link UniversidadDTO} - La universidad guardada con el atributo id
     * autogenerado.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando ya existe la universidad.
     */
    @POST
    public UniversidadDTO createUniversidad(UniversidadDTO universidad) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "UniversidadResource createUniversidad: input: {0}", universidad.toString());
        // Convierte el DTO (json) en un objeto Entity para ser manejado por la lógica.
        UniversidadEntity universidadEntity = universidad.toEntity();
 
        UniversidadDTO nuevaUniversidad = new UniversidadDTO(universidadEntity);
        return nuevaUniversidad;
    }
    
    @GET
    @Path("estudiantes/{estudianteId:\\d+}/{universiadadId: \\d+}")
    public UniversidadDTO getUniversidad(@PathParam("universidadId")Long sitioInteresId) throws WebApplicationException
    {
        return null;
    }
    
    @PUT
    @Path("estudiantes/{estudianteId:\\d+}/{universiadadId: \\d+}")
    public UniversidadDTO updateUniversidad(@PathParam("universidadId")Long universidadId, UniversidadDTO universidad)throws WebApplicationException
    {
        return null;
    }
        
}