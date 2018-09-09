/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.vivienda.resources;

import co.edu.uniandes.csw.vivienda.dtos.ContratoDTO;
import co.edu.uniandes.csw.vivienda.dtos.ViviendaDTO;
import co.edu.uniandes.csw.vivienda.ejb.ContratoLogic;
import co.edu.uniandes.csw.vivienda.ejb.ContratoViviendaLogic;
import co.edu.uniandes.csw.vivienda.ejb.ViviendaLogic;
import co.edu.uniandes.csw.vivienda.mappers.WebApplicationExceptionMapper;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author estudiante
 */
@Path("contratos/{contratoId: \\d+}/vivienda")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ContratoViviendaResource {
    
    private static final Logger LOGGER = Logger.getLogger(ContratoViviendaResource.class.getName());

    @Inject
    private ContratoLogic contratoLogic; // Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.

    @Inject
    private ContratoViviendaLogic contratoViviendaLogic; // Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.

    @Inject
    private ViviendaLogic viviendaLogic; // Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.

    /**
     * Remplaza la instancia de Vivienda asociada a un Contrato.
     *
     * @param contratoId Identificador del contrato que se esta actualizando. Este
     * debe ser una cadena de dígitos.
     * @param vivienda La vivienda que se será del contrato.
     * @return JSON {@link BookDTO} - El arreglo de contratos guardado en la
     * viviendz.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la vivienda o el
     * contrato.
     */
    @PUT
    public ContratoDTO replaceVivienda(@PathParam("contratoId") Long contratoId, ViviendaDTO vivienda) {
        LOGGER.log(Level.INFO, "ContratoViviendaResource replaceVivienda: input: contratoId{0} , vivienda:{1}", new Object[]{contratoId, vivienda.toString()});
        if (contratoLogic.getContrato(contratoId) == null) {
            throw new WebApplicationException("El recurso /contratos/" + contratoId + " no existe.", 404);
        }
        if (viviendaLogic.getVivienda(vivienda.getId()) == null) {
            throw new WebApplicationException("El recurso /viviendas/" + vivienda.getId() + " no existe.", 404);
        }
        ContratoDTO contratoDTO = new ContratoDTO(contratoViviendaLogic.replaceVivienda(contratoId, vivienda.getId()));
        LOGGER.log(Level.INFO, "ContratoViviendaResource replaceVivienda: output: {0}", contratoDTO.toString());
        return contratoDTO;
    }
}
