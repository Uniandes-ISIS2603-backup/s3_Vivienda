/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.vivienda.resources;

import co.edu.uniandes.csw.vivienda.dtos.ViviendaDTO;
import co.edu.uniandes.csw.vivienda.ejb.ViviendaLogic;
import co.edu.uniandes.csw.vivienda.entities.ViviendaEntity;
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

@Path("viviendas")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class ViviendaResource {
    
    @Inject 
    private ViviendaLogic logic;
    
    private static final Logger LOGGER = Logger.getLogger(ViviendaResource.class.getName());
        
    @POST
    public ViviendaDTO createVivienda(ViviendaDTO vivienda) throws BusinessLogicException{
        LOGGER.log(Level.INFO, "ViviendaResource.createVivienda: input:{0}", vivienda.toString());
        ViviendaEntity viviendaEntity = vivienda.toEntity();
     
        ViviendaEntity newViviendaEntity = logic.createVivienda(viviendaEntity);
        
        ViviendaDTO nuevoViviendaDTO = new ViviendaDTO(newViviendaEntity);
        return nuevoViviendaDTO;
    }
    
    @DELETE
    @Path("{viviendaId: \\d+}")
    public void deleteVivienda(@PathParam("viviendaId") Long viviendaId) throws BusinessLogicException{
        LOGGER.log(Level.INFO, "ViviendaResource.deleteVivienda: input:{0}", viviendaId);
        logic.deleteVivienda(viviendaId);
    }
    
    @GET
    @Path("{viviendaId: \\d+}")
    public ViviendaDTO getVivienda(@PathParam("viviendaId") Long viviendaId) throws WebApplicationException{
        ViviendaEntity vivienda = logic.getVivienda(viviendaId);
        ViviendaDTO viviendaDTO = new ViviendaDTO(vivienda);
        return viviendaDTO;
    }
    
    @GET
    public List<ViviendaDTO> getViviendas(){
        List<ViviendaEntity> viviendas = logic.getViviendas();
        ArrayList<ViviendaDTO> respuestas = new ArrayList<ViviendaDTO>();
        
        for (ViviendaEntity ent: viviendas){
            ViviendaDTO viviendaDto = new ViviendaDTO(ent);
            respuestas.add(viviendaDto);
        }
        return respuestas;
    }
    
    @PUT
    @Path("{viviendaId: \\d+}")
    public ViviendaDTO updateVivienda(@PathParam("viviendaId") Long viviendaId, ViviendaDTO vivienda){
        ViviendaEntity old = vivienda.toEntity();
        try {
            ViviendaEntity newVivienda = logic.updateVivienda(viviendaId, old);
            ViviendaDTO newDTO = new ViviendaDTO(newVivienda);
            return newDTO;
        } catch (BusinessLogicException e) {
            e.getMessage();
            return null;
        }
    }
  
    @Path("{viviendaId: \\d+}/cuartos")
    public Class<ViviendaCuartoResource> getViviendaCuartoResource(@PathParam("viviendaId") Long viviendaId){
        return ViviendaCuartoResource.class;
    }
    
    @Path("{viviendaId: \\d+}/arrendador")
    public Class<ViviendaArrendadorResource> getViviendaArrendadorResource(@PathParam("viviendaId") Long viviendaId){
        return ViviendaArrendadorResource.class;
    }
    
    @Path("{viviendaId: \\d+}/calificaciones")
    public Class<ViviendaCalificacionesResource> getViviendaCalificacionesResource(@PathParam("viviendaId") Long viviendaId){
        return ViviendaCalificacionesResource.class;
    }
    
    @Path("{viviendaId: \\d+}/contrato")
    public Class<ViviendaContratosResource> getViviendaContratoResource(@PathParam("viviendaId") Long viviendaId){
        return ViviendaContratosResource.class;
    }
    
    @Path("{viviendaId: \\d+}/sitioInteres")
    public Class<ViviendaSitioInteresResource> getViviendaSitioInteresResource(@PathParam("viviendaId") Long viviendaId){
        return ViviendaSitioInteresResource.class;
    }
    
    
            
}
