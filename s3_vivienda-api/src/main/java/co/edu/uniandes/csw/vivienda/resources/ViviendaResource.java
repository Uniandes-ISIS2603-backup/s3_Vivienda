/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.vivienda.resources;

import co.edu.uniandes.csw.vivienda.dtos.ViviendaDTO;
import co.edu.uniandes.csw.vivienda.dtos.ViviendaDetailDTO;
import co.edu.uniandes.csw.vivienda.ejb.CuartoLogic;
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

    @Inject
    CuartoLogic cuartoLogic;
    
    private static final Logger LOGGER = Logger.getLogger(ViviendaResource.class.getName());
        
    @POST
    public ViviendaDTO createVivienda(ViviendaDTO vivienda) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "ViviendaResource.createVivienda: input:{0}", vivienda);
        ViviendaEntity viviendaEntity = vivienda.toEntity();


        ViviendaEntity newViviendaEntity = null;
        newViviendaEntity = logic.createVivienda(viviendaEntity);
        return new ViviendaDTO(newViviendaEntity);
    }
    
    @DELETE
    @Path("{viviendaId: \\d+}")
    public void deleteVivienda(@PathParam("viviendaId") Long viviendaId) throws BusinessLogicException{
        LOGGER.log(Level.INFO, "ViviendaResource.deleteVivienda: input:{0}", viviendaId);
        logic.deleteVivienda(viviendaId);
    }
    
    @GET
    @Path("{viviendaId: \\d+}")
    public ViviendaDetailDTO getVivienda(@PathParam("viviendaId") Long viviendaId){
        ViviendaEntity vivienda = logic.getVivienda(viviendaId);
        return new ViviendaDetailDTO(vivienda);
    }
    
    @GET
    public List<ViviendaDetailDTO> getViviendas(){
        List<ViviendaEntity> viviendas = logic.getViviendas();
        ArrayList<ViviendaDetailDTO> respuestas = new ArrayList<>();
        
        for (ViviendaEntity ent: viviendas){
            ViviendaDetailDTO viviendaDto = new ViviendaDetailDTO(ent);
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
            return new ViviendaDTO(newVivienda);
        } catch (BusinessLogicException e) {
            e.getMessage();
            return null;
        }
    }

    @POST
    @Path("generardatos")
    public List<ViviendaDTO> generarDatos() {
        logic.generarDatos();
        List<ViviendaEntity> viviendas = logic.getViviendas();
        ArrayList<ViviendaDTO> respuestas = new ArrayList<>();

        for (ViviendaEntity ent: viviendas){
            cuartoLogic.generarCuartos(ent.getId());
            ViviendaDTO viviendaDto = new ViviendaDTO(ent);
            respuestas.add(viviendaDto);
        }
        return respuestas;
    }

    @Path("{viviendaId: \\d+}/cuartos")
    public Class<ViviendaCuartoResource> getViviendaCuartoResource(@PathParam("viviendaId") Long viviendaId){
        if( logic.getVivienda(viviendaId) != null)
            return ViviendaCuartoResource.class;
        else
            throw new WebApplicationException("El recurso viviendas/"+viviendaId+" no existe", 404);
    }
    
    @Path("{viviendaId: \\d+}/arrendadores")
    public Class<ViviendaArrendadorResource> getViviendaArrendadoresResource(@PathParam("viviendaId") Long viviendaId){
        if( logic.getVivienda(viviendaId) != null)
            return ViviendaArrendadorResource.class;
        else
            throw new WebApplicationException("El recurso viviendas/"+viviendaId+" no existe", 404);
    }
    
    @Path("{viviendaId: \\d+}/calificaciones")
    public Class<ViviendaCalificacionesResource> getViviendaCalificacionesResource(@PathParam("viviendaId") Long viviendaId){
        if( logic.getVivienda(viviendaId) != null)
            return ViviendaCalificacionesResource.class;
        else
            throw new WebApplicationException("El recurso viviendas/"+viviendaId+" no existe", 404);
    }
    
    @Path("{viviendaId: \\d+}/contratos")
    public Class<ViviendaContratosResource> getViviendaContratoResource(@PathParam("viviendaId") Long viviendaId){
        if( logic.getVivienda(viviendaId) != null)
            return ViviendaContratosResource.class;
        else
            throw new WebApplicationException("El recurso viviendas/"+viviendaId+" no existe", 404);
    }
    
    @Path("{viviendaId: \\d+}/sitioInteres")
    public Class<SitioInteresResource> getViviendaSitioInteresResource(@PathParam("viviendaId") Long viviendaId){
        if( logic.getVivienda(viviendaId) != null)
            return SitioInteresResource.class;
        else
            throw new WebApplicationException("El recurso viviendas/"+viviendaId+" no existe", 404);
    }
    
    @Path("{viviendaId: \\d+}/servicioAdicional")
    public Class<ServicioAdicionalResource> getServicioAdicionalResource(@PathParam("viviendaId") Long viviendaId) {
        return ServicioAdicionalResource.class;
    }
            
}
