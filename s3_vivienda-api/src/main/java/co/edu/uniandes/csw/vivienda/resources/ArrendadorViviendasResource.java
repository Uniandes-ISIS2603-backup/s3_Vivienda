/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.vivienda.resources;

//import co.edu.uniandes.csw.vivienda.ejb.ViviendaLogic;

import co.edu.uniandes.csw.vivienda.dtos.ArrendadorDTO;
import co.edu.uniandes.csw.vivienda.dtos.ArrendadorDetailDTO;
import co.edu.uniandes.csw.vivienda.ejb.ArrendadorLogic;
import co.edu.uniandes.csw.vivienda.entities.ArrendadorEntity;
import co.edu.uniandes.csw.vivienda.exceptions.BusinessLogicException;
import java.util.logging.Level;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
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
 * Implementa el recurso /arrendadores/{id}/vivienda
 * @author msalcedo
 */
@Path("vivienda/{viviendaId: \\d+}/arrendadores")
@Consumes("application/json")
@Produces("application/json")
public class ArrendadorViviendasResource {
    
    //@Inject
    //private ArrendadorViviendasLogic ArrendadorViviendasLogic; // Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.

    //@Inject
    //private ViviendaLogic viviendasLogic; // Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.
    
    @Inject
    private ArrendadorLogic arrendadorLogic; // Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.
    
    @GET
    @Path("{arrendadorId: \\d}")
    public ArrendadorDetailDTO getArrendador(@PathParam("arrendadorId") Long arrendadorId) throws WebApplicationException {
        //LOGGER.log(Level.INFO, "ArrendadorResource getArrendador: input: {0}", arrendadorId);
        ArrendadorEntity arrendadorEntity = arrendadorLogic.getArrendador(arrendadorId);
        if (arrendadorEntity == null) {
            throw new WebApplicationException("El recurso /arrendador/" + arrendadorId + " no existe.", 404);
        }
        ArrendadorDetailDTO detailDTO = new ArrendadorDetailDTO(arrendadorEntity);
        //LOGGER.log(Level.INFO, "ArrendadorResource getArrendador: output: {0}", detailDTO.toString());
        return detailDTO;
    }
    
    @PUT
    @Path("{arrendadorId:\\d+}")
    public ArrendadorDTO updateArrendador(@PathParam("arrendadorId")Long arrendadorId, ArrendadorDTO arrendador)throws WebApplicationException, BusinessLogicException
    {
        ArrendadorEntity arrendadorEntity = arrendadorLogic.getArrendador(arrendadorId);
        if (arrendadorEntity == null) {
            throw new WebApplicationException("El recurso /arrendador/" + arrendadorId + " no existe.", 404);
        }
        arrendadorEntity = arrendadorLogic.updateArrendador(arrendadorId,arrendadorEntity);
        ArrendadorDetailDTO detailDTO = new ArrendadorDetailDTO(arrendadorEntity);
        //LOGGER.log(Level.INFO, "ArrendadorResource getArrendador: output: {0}", detailDTO.toString());
        return detailDTO;
    }
    
 
}
