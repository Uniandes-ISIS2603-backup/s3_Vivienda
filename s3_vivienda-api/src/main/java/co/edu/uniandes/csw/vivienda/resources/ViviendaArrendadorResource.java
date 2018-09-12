/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.vivienda.resources;

import java.util.logging.Logger;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 * Implementa el recurso /viviendas/{id}/arrendadores
 * @author msalcedo
 */
@Path("arrendadores/{arrendadorId: \\d+}/vivienda")
@Consumes("application/json")
@Produces("application/json")
public class ViviendaArrendadorResource {
    
    private static final Logger LOGGER = Logger.getLogger(ViviendaArrendadorResource.class.getName());

//    @Inject
//    private ViviendaLogic viviendasLogic; // Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.
//    
//    @Inject
//    private ViviendaArrendadorLogic viviendaArrendadorLogic; // Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.
//
//    @Inject
//    private ArrendadorLogic arrendadorLogic; // Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.
    
//    @PUT
//    public ViviendaDetailDTO replaceArrendador(@PathParam("arrendadorId") Long arrendadorId, ArrendadorDTO arrendador) {
//        LOGGER.log(Level.INFO, "ViviendaArrendadorResource replaceArrendador: input: arrendadorId{0} , Arrendador:{1}", new Object[]{arrendadorId, arrendador.toString()});
//        if (viviendasLogic.getVivienda(arrendadorId) == null) {
//            throw new WebApplicationException("El recurso /arrendadores/" + arrendadorId + " no existe.", 404);
//        }
//        if (arrendadorLogic.getArrendador(arrendador.getId()) == null) {
//            throw new WebApplicationException("El recurso /editorials/" + arrendador.getId() + " no existe.", 404);
//        }
//        ViviendaDetailDTO viviendaDetailDTO = new ViviendaDetailDTO(viviendaArrendadorLogic.replaceArrendador(arrendadorId, arrendador.getId()));
//        LOGGER.log(Level.INFO, "ViviendaArrendadorResource replaceEditorial: output: {0}", viviendaDetailDTO.toString());
//        return viviendaDetailDTO;
//    }  
    
}
