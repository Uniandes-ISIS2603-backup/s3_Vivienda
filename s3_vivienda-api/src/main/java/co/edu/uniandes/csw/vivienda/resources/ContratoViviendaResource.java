/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.vivienda.resources;

import co.edu.uniandes.csw.vivienda.dtos.ContratoDTO;
import co.edu.uniandes.csw.vivienda.dtos.ViviendaDTO;
import co.edu.uniandes.csw.vivienda.dtos.ViviendaDetailDTO;
import co.edu.uniandes.csw.vivienda.ejb.ContratoLogic;
import co.edu.uniandes.csw.vivienda.ejb.ContratoViviendaLogic;
import co.edu.uniandes.csw.vivienda.ejb.ViviendaLogic;
import co.edu.uniandes.csw.vivienda.mappers.WebApplicationExceptionMapper;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.ws.rs.*;
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
    private static final String NO_EXISTE = " no existe.";

    @Inject
    private ContratoLogic contratoLogic; // Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.

    @Inject
    private ContratoViviendaLogic contratoViviendaLogic; // Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.

    @Inject
    private ViviendaLogic viviendaLogic; // Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.

    /**
     * Da la vivienda que esta asociada con el contrato
     * @param contratoId - Identificador del contrato
     * @param viviendaId - Identificador de la vivienda asociada con un contrato
     * @return
     */
    @GET
    @Path("{viviendaId: \\d+}")
    public ViviendaDetailDTO getVivienda(@PathParam("contratoId") Long contratoId, @PathParam("viviendaId") Long viviendaId) {
        LOGGER.log(Level.INFO, "ContratoViviendaResource getVivienda: input: contratoId {0} , viviendaId {1}", new Object[]{contratoId, viviendaId});
        if (viviendaLogic.getVivienda(viviendaId) == null) {
            throw new WebApplicationException("El recurso /viviendas/" + viviendaId + NO_EXISTE, 404);
        }
        ViviendaDetailDTO detailDTO = new ViviendaDetailDTO(contratoViviendaLogic.getVivienda(contratoId, viviendaId));
        LOGGER.log(Level.INFO, "ContratoViviendaResource getVivienda: output: {0}", detailDTO);
        return detailDTO;
    }

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
        LOGGER.log(Level.INFO, "ContratoViviendaResource replaceVivienda: input: contratoId{0} , vivienda:{1}", new Object[]{contratoId, vivienda});
        if (contratoLogic.getContrato(contratoId) == null) {
            throw new WebApplicationException("El recurso /contratos/" + contratoId + NO_EXISTE, 404);
        }
        if (viviendaLogic.getVivienda(vivienda.getId()) == null) {
            throw new WebApplicationException("El recurso /viviendas/" + vivienda.getId() + NO_EXISTE, 404);
        }
        ContratoDTO contratoDTO = new ContratoDTO(contratoViviendaLogic.replaceVivienda(contratoId, vivienda.getId()));
        LOGGER.log(Level.INFO, "ContratoViviendaResource replaceVivienda: output: {0}", contratoDTO);
        return contratoDTO;
    }
}
