/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.vivienda.resources;

import co.edu.uniandes.csw.vivienda.dtos.UniversidadDTO;
import co.edu.uniandes.csw.vivienda.dtos.UniversidadDetailDTO;
import co.edu.uniandes.csw.vivienda.ejb.UniversidadLogic;
import co.edu.uniandes.csw.vivienda.entities.UniversidadEntity;
import co.edu.uniandes.csw.vivienda.exceptions.*;
import co.edu.uniandes.csw.vivienda.mappers.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.*;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;


@Path("universidades")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped

/**
 * Clase que implementa el recurso "Universidad".
 *
 * @author Paula Molina Ruiz
 */
public class UniversidadResource {
    
    private static final Logger LOGGER = Logger.getLogger(UniversidadResource.class.getName());
    
   @Inject
   UniversidadLogic universidadLogic; // Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.

    /**
     * Crea una nueva universidad con la informacion que se recibe en el cuerpo de
     * la petición y se regresa un objeto identico con un id auto-generado por
     * la base de datos.
     *
     * @param universidad {@link UniversidadDTO} - La universidad que se desea guardar.
     * @return JSON {@link UniversidadDTO} - La universidad guardada con el atributo id autogenerado.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando ya existe la universidad.
     */
    @POST
    public UniversidadDTO createUniversidad(UniversidadDTO universidad) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "UniversidadResource createUniversidad: input: {0}", universidad);
        // Convierte el DTO (json) en un objeto Entity para ser manejado por la lógica.
        UniversidadEntity universidadEntity = universidad.toEntity();
        UniversidadEntity nuevaUniversidadEntity = universidadLogic.createUniversidad(universidadEntity);
        UniversidadDTO nuevaUniversidadDTO = new UniversidadDTO(nuevaUniversidadEntity);
        LOGGER.log(Level.INFO, "UniversidadResource createUniversidad: output: {0}", nuevaUniversidadDTO);
        return nuevaUniversidadDTO;
    }
    
    @POST
    @Path("generardatos")
    public List<UniversidadDTO> generarDatos() {
        universidadLogic.generarDatos();
        List<UniversidadEntity> universidades = universidadLogic.getUniversidades();
        ArrayList<UniversidadDTO> respuestas = new ArrayList<>();

        for (UniversidadEntity ent : universidades) {
            UniversidadDTO universidad = new UniversidadDTO(ent);
            respuestas.add(universidad);
        }
        return respuestas;
    }
    
    /**
     * Busca y devuelve todas las universidades que existen en la aplicacion.
     *
     * @return JSONArray {@link UniversidadDetailDTO} - Las universidades
     * encontradas en la aplicación. Si no hay ninguna retorna una lista vacía.
     */

    @GET
    public List<UniversidadDetailDTO> getUniversidades() {
         
        LOGGER.info("UniversdiadResource getUniversidades: input: void");
        List<UniversidadDetailDTO> listaUniversidades = listEntity2DetailDTO(universidadLogic.getUniversidades());
        LOGGER.log(Level.INFO, "UniversdiadResource getUniversidades: output: {0}", listaUniversidades);
        return listaUniversidades;
    }

    /**
     * Busca la universidad con el id asociado recibido en la URL y la devuelve.
     *
     * @param universidadId Identificador de la universidad que se esta buscando.
     * Este debe ser una cadena de dígitos.
     * @return JSON {@link EditorialDetailDTO} - La universidad buscada
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la universidad.
     */
    @GET
    @Path("{universidadId: \\d+}")
    public UniversidadDetailDTO getUniversidad(@PathParam("universidadId") Long universidadId){
        
       LOGGER.log(Level.INFO, "UniversidadResource getUniversidadl: input: {0}", universidadId);
        UniversidadEntity universidadEntity = universidadLogic.getUniversidad(universidadId);
        if (universidadEntity == null) {
            throw new WebApplicationException("El recurso /universidades/" + universidadId + " no existe.", 404);
        }
        UniversidadDetailDTO detailDTO = new UniversidadDetailDTO(universidadEntity);
        LOGGER.log(Level.INFO, "UniversidadResource getUniversidad: output: {0}", detailDTO);
        return detailDTO;
    }

    /**
     * Actualiza la universidad con el id recibido en la URL con la informacion
     * que se recibe en el cuerpo de la petición.
     *
     * @param universidadId Identificador de la universidad que se desea
     * actualizar. Este debe ser una cadena de dígitos.
     * @param universidad {@link UniversidadDetailDTO} La editorial que se desea guardar.
     * @return JSON {@link UniversidadDetailDTO} - La universidad guardada.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la universidad a actualizar.
     */
    @PUT
    @Path("{universidadId: \\d+}")
    public UniversidadDetailDTO updateuniversidad(@PathParam("universidadId") Long universidadId, UniversidadDetailDTO universidad){
        
        LOGGER.log(Level.INFO, "UniversidadResource updateUniversidad: input: id:{0} , universidad: {1}", new Object[]{universidadId, universidad});
        universidad.setId(universidadId);
        if (universidadLogic.getUniversidad(universidadId) == null) {
            throw new WebApplicationException("El recurso /universidades/" + universidadId + " no existe.", 404);
        }
        UniversidadDetailDTO detailDTO = new UniversidadDetailDTO(universidadLogic.updateUniversidad(universidadId, universidad.toEntity()));
        LOGGER.log(Level.INFO, "UniversidadResource updateUniversidad: output: {0}", detailDTO);
        return detailDTO;

    }

    /**
     * Borra la universidad con el id asociado recibido en la URL.
     *
     * @param universidadId Identificador de la universidad que se desea borrar.
     * Este debe ser una cadena de dígitos.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando no se puede eliminar la universidad.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la universidad.
     */
    @DELETE
    @Path("{universidadId: \\d+}")
    public void deleteUniversidad(@PathParam("universidadId") Long universidadId) throws BusinessLogicException 
    {
        LOGGER.log(Level.INFO, "UniversidadResource deleteUniversidad: input: {0}", universidadId);
        if (universidadLogic.getUniversidad(universidadId) == null) {
            throw new WebApplicationException("El recurso /universidades/" + universidadId + " no existe.", 404);
        }
        universidadLogic.deleteUniversidad(universidadId);
        LOGGER.info("UniversidadResource deleteUniversidad: output: void");
    }   
    
    /**
     * Convierte una lista de entidades a DTO.
     *
     * Este método convierte una lista de objetos EditorialEntity a una lista de
     * objetos EditorialDetailDTO (json)
     *
     * @param entityList corresponde a la lista de editoriales de tipo Entity
     * que vamos a convertir a DTO.
     * @return la lista de editoriales en forma DTO (json)
     */
    private List<UniversidadDetailDTO> listEntity2DetailDTO(List<UniversidadEntity> entityList) {
        List<UniversidadDetailDTO> list = new ArrayList<>();
        for (UniversidadEntity entity : entityList) {
            list.add(new UniversidadDetailDTO(entity));
        }
        return list;
    }
}