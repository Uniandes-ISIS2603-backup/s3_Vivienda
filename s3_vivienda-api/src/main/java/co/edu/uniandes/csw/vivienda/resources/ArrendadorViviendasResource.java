/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.vivienda.resources;

//import co.edu.uniandes.csw.vivienda.ejb.ViviendaLogic;

import co.edu.uniandes.csw.vivienda.dtos.ArrendadorDTO;
import co.edu.uniandes.csw.vivienda.dtos.ArrendadorDetailDTO;
import co.edu.uniandes.csw.vivienda.dtos.ViviendaDTO;
import co.edu.uniandes.csw.vivienda.dtos.ViviendaDetailDTO;
import co.edu.uniandes.csw.vivienda.ejb.ArrendadorLogic;
import co.edu.uniandes.csw.vivienda.ejb.ArrendadorViviendasLogic;
import co.edu.uniandes.csw.vivienda.ejb.ViviendaLogic;
import co.edu.uniandes.csw.vivienda.entities.ArrendadorEntity;
import co.edu.uniandes.csw.vivienda.entities.ViviendaEntity;
import co.edu.uniandes.csw.vivienda.exceptions.BusinessLogicException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;

//import co.edu.uniandes.csw.vivienda.entities.ViviendaEntity;
//import static com.sun.xml.internal.ws.spi.db.BindingContextFactory.LOGGER;
//import java.util.logging.Level;
//import javax.inject.Inject;
//import javax.ws.rs.GET;
//import javax.ws.rs.Path;
//import javax.ws.rs.PathParam;
//import javax.ws.rs.WebApplicationException;

/**
 * Implementa el recurso "arrendadores/{id}/viviendas"
 * @author msalcedo
 */
@Consumes("application/json")
@Produces("application/json")
public class ArrendadorViviendasResource {
    
    @Inject
    private ArrendadorViviendasLogic arrendadorViviendasLogic; // Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.

    @Inject
    private ViviendaLogic viviendasLogic; // Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.
   
    
    @GET
    public List<ViviendaDetailDTO> getViviendas(@PathParam("arrendadorId")Long arrendadorId)
    {
        List<ViviendaDetailDTO> listaDetailDTOs = viviendasListEntity2DTO(arrendadorViviendasLogic.getViviendas(arrendadorId));
        return listaDetailDTOs;
    }
    
    @POST
    @Path("{viviendaId: \\d+}")
    public ViviendaDTO addVivienda(@PathParam("arrendadorId") Long arrendadorId, @PathParam("viviendaId") Long viviendaId) {
        
        if (viviendasLogic.getVivienda(viviendaId) == null) {
            throw new WebApplicationException("El recurso /viviendas/" + viviendaId + " no existe.", 404);
        }
        ViviendaDTO viviendaDTO = new ViviendaDTO(arrendadorViviendasLogic.addVivienda(viviendaId, arrendadorId));
        
        return viviendaDTO;
    }
    
    @GET
    @Path("{viviendaId: \\d+}")
    public ViviendaDetailDTO getVivienda(@PathParam("arrendadorId") Long arrendadorId, @PathParam("viviendaId") Long viviendaId) throws BusinessLogicException {
        
        if (viviendasLogic.getVivienda(viviendaId) == null) {
            throw new WebApplicationException("El recurso /arrendadores/" + arrendadorId + "/viviendas/" + viviendaId + " no existe.", 404);
        }
        ViviendaDetailDTO bookDetailDTO = new ViviendaDetailDTO(arrendadorViviendasLogic.getVivienda(arrendadorId, viviendaId));
        
        return bookDetailDTO;
    }
    
    @PUT
    public List<ViviendaDetailDTO> replaceViviendas(@PathParam("arrendadorId") Long arrendadorId, List<ViviendaDetailDTO> viviendas) {
        
        for (ViviendaDetailDTO vivienda : viviendas) {
            if (viviendasLogic.getVivienda(vivienda.getId()) == null) {
                throw new WebApplicationException("El recurso /viviendas/" + vivienda.getId() + " no existe.", 404);
            }
        }
        List<ViviendaDetailDTO> listaDetailDTOs = viviendasListEntity2DTO(arrendadorViviendasLogic.replaceViviendas(arrendadorId, viviendaListDTO2Entity(viviendas)));
        
        return listaDetailDTOs;
    }
    
     private List<ViviendaDetailDTO> viviendasListEntity2DTO(List<ViviendaEntity> entityList) {
        List<ViviendaDetailDTO> list = new ArrayList();
        for (ViviendaEntity entity : entityList) {
            list.add(new ViviendaDetailDTO(entity));
        }
        return list;
    }
     
    private List<ViviendaEntity> viviendaListDTO2Entity(List<ViviendaDetailDTO> dtos) {
        List<ViviendaEntity> list = new ArrayList<>();
        for (ViviendaDetailDTO dto : dtos) {
            list.add(dto.toEntity());
        }
        return list;
    }
    
    
    
 
}
