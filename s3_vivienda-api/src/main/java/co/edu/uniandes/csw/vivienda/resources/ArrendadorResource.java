/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.vivienda.resources;

import co.edu.uniandes.csw.vivienda.dtos.ArrendadorDTO;
import co.edu.uniandes.csw.vivienda.dtos.ArrendadorDetailDTO;
import co.edu.uniandes.csw.vivienda.ejb.ArrendadorLogic;
import co.edu.uniandes.csw.vivienda.entities.ArrendadorEntity;
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
@Path("arrendadores")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class ArrendadorResource {
    
    private static final Logger LOGGER = Logger.getLogger(ArrendadorResource.class.getName());
    
    @Inject
    private ArrendadorLogic arrendadorLogic;
    
    @POST
    public ArrendadorDTO createArrendor(ArrendadorDTO arrendador) throws BusinessLogicException {
         
        LOGGER.log(Level.INFO, "ArrendadorResource createArrendador: input: {0}", arrendador.toString());
        // Convierte el DTO (json) en un objeto Entity para ser manejado por la lógica.
        ArrendadorEntity arrendadorEntity = arrendador.toEntity();
        
        ArrendadorEntity nuevoArrendadorEntity = arrendadorLogic.createArrendador(arrendadorEntity);
 
        ArrendadorDTO nuevoArrendador = new ArrendadorDTO(nuevoArrendadorEntity);
        return nuevoArrendador;
    }
    
    @GET
    public List<ArrendadorDetailDTO> getArrendadores() {
        LOGGER.info("ArrendadorResource getArrendadores: input: void");
        List<ArrendadorDetailDTO> listaArrendadores = listEntity2DetailDTO(arrendadorLogic.getArrendadores());
        LOGGER.log(Level.INFO, "ArrendadorResource getArrendadores: output: {0}", listaArrendadores.toString());
        return listaArrendadores;
    }
    
    private List<ArrendadorDetailDTO> listEntity2DetailDTO(List<ArrendadorEntity> entityList) {
        List<ArrendadorDetailDTO> list = new ArrayList<>();
        for (ArrendadorEntity entity : entityList) {
            list.add(new ArrendadorDetailDTO(entity));
        }
        return list;
    }
    
    @GET
    @Path("{arrendadorId:\\d+}")
    public ArrendadorDetailDTO getArrendador(@PathParam("arrendadorId")Long arrendadorId)throws WebApplicationException
    {
        ArrendadorEntity arrendadorEntity = arrendadorLogic.getArrendador(arrendadorId);
        if(arrendadorEntity == null)
        {
            throw new WebApplicationException("El recurso /arrendadores/"+ arrendadorId +"no existe.", 404);
        }
        ArrendadorDetailDTO detailDTO = new ArrendadorDetailDTO(arrendadorEntity);
        return detailDTO;
    }
    
    @PUT
    @Path("{arrendadorId:\\d+}")
    public ArrendadorDetailDTO updateArrendador(@PathParam("arrendadorId")Long arrendadorId, ArrendadorDTO arrendador)throws WebApplicationException
    {
        arrendador.setId(arrendadorId);
        if(arrendadorLogic.getArrendador(arrendadorId)==null)
        {
            throw new WebApplicationException("El recurso /arrendadores/"+ arrendadorId +" no existe.", 404);
        }

        ArrendadorDetailDTO arrendadorDetailDTO = new ArrendadorDetailDTO(arrendadorLogic.updateArrendador(arrendadorId, arrendador.toEntity()));
        return arrendadorDetailDTO;
        
    }
    
    @DELETE
    @Path("{arrendadorId:\\d+}")
    public void deleteArrendador(@PathParam("arrendadorId")Long arrendadorId)throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "ArrendadorResource.deleteArrendador: input:{0}", arrendadorId);
        if (arrendadorLogic.getArrendador(arrendadorId) == null) {
            throw new WebApplicationException("El recurso /arrendadores/" + arrendadorId + " no existe.", 404);
        }
        arrendadorLogic.deleteArrendador(arrendadorId);
    }
    
    @Path("{arrendadorId: \\d+}/viviendas")
    public Class<ArrendadorViviendasResource> getArrendadorViviendasResource(@PathParam("arrendadorId")Long arrendadorId)
    {
        if(arrendadorLogic.getArrendador(arrendadorId)==null)
        {
            throw new WebApplicationException("El recurso /arrendadores/ "+ arrendadorId + "no existe", 404);
        }
        return ArrendadorViviendasResource.class;
    }
}
