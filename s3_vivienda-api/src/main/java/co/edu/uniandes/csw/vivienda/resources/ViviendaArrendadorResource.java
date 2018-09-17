/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.vivienda.resources;

import co.edu.uniandes.csw.vivienda.dtos.ArrendadorDTO;
import co.edu.uniandes.csw.vivienda.dtos.ArrendadorDetailDTO;
import co.edu.uniandes.csw.vivienda.dtos.ViviendaDetailDTO;
import co.edu.uniandes.csw.vivienda.ejb.ArrendadorLogic;
import co.edu.uniandes.csw.vivienda.ejb.ViviendaArrendadorLogic;
import co.edu.uniandes.csw.vivienda.ejb.ViviendaLogic;
import co.edu.uniandes.csw.vivienda.exceptions.BusinessLogicException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;

/**
 * Implementa el recurso /viviendas/{id}/arrendadores
 * @author msalcedo
 */
@Consumes("application/json")
@Produces("application/json")
public class ViviendaArrendadorResource {
    
    private static final Logger LOGGER = Logger.getLogger(ViviendaArrendadorResource.class.getName());

    @Inject
    private ViviendaLogic viviendasLogic; // Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.
    
    @Inject
    private ViviendaArrendadorLogic viviendaArrendadorLogic; // Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.

    @Inject
    private ArrendadorLogic arrendadorLogic; // Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.
    
    @GET
    @Path("{arrendadorId: \\d+}")
    public ArrendadorDetailDTO getArrendador(@PathParam("viviendaId") Long viviendaId, @PathParam("arrendadorId") Long arrendadorId)  {
        
        if (arrendadorLogic.getArrendador(arrendadorId) == null) {
            throw new WebApplicationException("El recurso /viviendas/" + viviendaId + "/arrendadores/" + arrendadorId + " no existe.", 404);
        }
        ArrendadorDetailDTO arrendadorDetailDTO = new ArrendadorDetailDTO(viviendaArrendadorLogic.getArrendador(viviendaId, arrendadorId));
        
        return arrendadorDetailDTO;
    }
    
    @PUT
    @Path("{viviendaId: \\d+}")
    public ViviendaDetailDTO replaceArrendador(@PathParam("viviendaId") Long viviendaId, ArrendadorDTO arrendador) {
        
        if (viviendasLogic.getVivienda(viviendaId) == null) {
            throw new WebApplicationException("El recurso /viviendas/" + viviendaId + " no existe.", 404);
        }
        if (arrendadorLogic.getArrendador(arrendador.getId()) == null) {
            throw new WebApplicationException("El recurso /arrendadores/" + arrendador.getId() + " no existe.", 404);
        }
        ViviendaDetailDTO viviendaDetailDTO = new ViviendaDetailDTO(viviendaArrendadorLogic.replaceArrendador(viviendaId, arrendador.getId()));
        
        return viviendaDetailDTO;
    }
}
