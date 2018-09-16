/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.vivienda.resources;

import co.edu.uniandes.csw.vivienda.dtos.SitioInteresDTO;
import co.edu.uniandes.csw.vivienda.dtos.SitioInteresDetailDTO;
import co.edu.uniandes.csw.vivienda.ejb.SitioInteresLogic;
import co.edu.uniandes.csw.vivienda.ejb.ViviendaSitioInteresLogic;
import co.edu.uniandes.csw.vivienda.entities.SitioInteresEntity;
import co.edu.uniandes.csw.vivienda.exceptions.BusinessLogicException;
import static com.sun.xml.internal.ws.spi.db.BindingContextFactory.LOGGER;
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
import javax.ws.rs.core.MediaType;

/**
 *
 * @author estudiante
 */
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ViviendaSitioInteresResource 
{
    
    @Inject
    private ViviendaSitioInteresLogic viviendaSitioInteresLogic; // Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.

    @Inject
    private SitioInteresLogic sitioInteresLogic;
    
    /**
     * Guarda un sitioInteres dentro de una vivienda con la informacion que recibe el
     * la URL. Se devuelve el sitioInteres que se guarda en la vivienda.
     *
     * @param viviendaId Identificador de la vivienda que se esta
     * actualizando. Este debe ser una cadena de dígitos.
     * @param sitioInteresId Identificador del sitioInteres que se desea guardar. Este debe
     * ser una cadena de dígitos.
     * @return JSON {@link SitioInteresDTO} - El sitioInteres guardado en la vivienda.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el sitioInteres.
     */
    @POST
    @Path("{sitioInteresId: \\d+}")
    public SitioInteresDTO addSitioInteres(@PathParam("viviendaId") Long viviendaId, @PathParam("sitioInteresId") Long sitioInteresId) {
        LOGGER.log(Level.INFO, "ViviendaSitioInteresResource addSitioIntere: input: viviendaID: {0} , sitioInteresId: {1}", new Object[]{viviendaId, sitioInteresId});
        if (sitioInteresLogic.getSitioInteres(viviendaId,sitioInteresId) == null) {
            throw new WebApplicationException("El recurso /sitioInteres/" + sitioInteresId + " no existe.", 404);
        }
        SitioInteresDTO sitioInteresDTO = new SitioInteresDTO(viviendaSitioInteresLogic.addSitioInteres(sitioInteresId, viviendaId));
        LOGGER.log(Level.INFO, "ViviendaSitioInteresResource addSitioInteres: output: {0}", sitioInteresDTO.toString());
        return sitioInteresDTO;
    }
    
    /**
     * Busca y devuelve todos los sitiosInteres que existen en la vivienda.
     *
     * @param viviendaId Identificador de la vivienda que se esta buscando.
     * Este debe ser una cadena de dígitos.
     * @return JSONArray {@link SitioInteresDetailDTO} - Los sitioInteres encontrados en la
     * vivienda. Si no hay ninguno retorna una lista vacía.
     */
    @GET
    public List<SitioInteresDetailDTO> getSitiosInteres(@PathParam("viviendaId") Long viviendaId) {
        LOGGER.log(Level.INFO, "ViviendaSitioInteresResource getSitioInteres: input: {0}", viviendaId);
        List<SitioInteresDetailDTO> listaDetailDTOs = sitioInteresListEntity2DTO(viviendaSitioInteresLogic.getSitioInteres(viviendaId));
        LOGGER.log(Level.INFO, "ViviendaSitioInteresResource getSitioInteres: output: {0}", listaDetailDTOs.toString());
        return listaDetailDTOs;
    }

    /**
     * Busca el sitioInteres con el id asociado dentro de la vivienda con id asociado.
     *
     * @param viviendaId Identificador de la vivienda que se esta buscando.
     * Este debe ser una cadena de dígitos.
     * @param sitioInteresId Identificador del sitioInteres que se esta buscando. Este debe
     * ser una cadena de dígitos.
     * @return JSON {@link SitioInteresDetailDTO} - El sitioIntere buscado
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el sitioInteres.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el sitioInteres en la
     * vivienda.
     */
    @GET
    @Path("{sitioInteresId: \\d+}")
    public SitioInteresDetailDTO getSitioInteres(@PathParam("viviendaId") Long viviendaId, @PathParam("sitioInteresId") Long sitioInteresId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "EditorialBooksResource getBook: input: viviendaID: {0} , sitioInteresId: {1}", new Object[]{viviendaId, sitioInteresId});
        if (sitioInteresLogic.getSitioInteres(viviendaId, sitioInteresId) == null) {
            throw new WebApplicationException("El recurso /vivienda/" + viviendaId + "/sitioInteres/" + sitioInteresId + " no existe.", 404);
        }
        SitioInteresDetailDTO sitioInteresDetailDTO = new SitioInteresDetailDTO(viviendaSitioInteresLogic.getSitioInteres(viviendaId, sitioInteresId));
        LOGGER.log(Level.INFO, "ViviendaSitioInteresResource getSitioInteres: output: {0}", sitioInteresDetailDTO.toString());
        return sitioInteresDetailDTO;
    }

    /**
     * Remplaza las instancias de SitioInteres asociadas a una instancia de Vivienda
     *
     * @param viviendaId Identificador de la vivienda que se esta
     * remplazando. Este debe ser una cadena de dígitos.
     * @param sitiosInteres JSONArray {@link SitioInteresDTO} El arreglo de sitiosInteres nuevo para la
     * vivienda.
     * @return JSON {@link SitioInteresDTO} - El arreglo de sitiosInteres guardado en la
     * vivienda.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el sitioInteres.
     */
    @PUT
    public List<SitioInteresDetailDTO> replaceSitiosInteres(@PathParam("viviendaId") Long viviendaId, List<SitioInteresDetailDTO> sitiosInteres) {
        LOGGER.log(Level.INFO, "EditorialBooksResource replaceBooks: input: viviendaId: {0} , sitiosInteres: {1}", new Object[]{viviendaId, sitiosInteres.toString()});
        for (SitioInteresDetailDTO sitio : sitiosInteres) {
            if (sitioInteresLogic.getSitioInteres(viviendaId, sitio.getId()) == null) {
                throw new WebApplicationException("El recurso /sitioInteres/" + sitio.getId() + " no existe.", 404);
            }
        }
        List<SitioInteresDetailDTO> listaDetailDTOs = sitioInteresListEntity2DTO(viviendaSitioInteresLogic.replaceSitiosInteres(viviendaId, sitioInteresListDTO2Entity(sitiosInteres)));
        LOGGER.log(Level.INFO, "ViviendaSitioInteresResource replaceSitiosInteres: output: {0}", listaDetailDTOs.toString());
        return listaDetailDTOs;
    }

    /**
     * Convierte una lista de SitioInteresEntity a una lista de SitioInteresDetailDTO.
     *
     * @param entityList Lista de SitioInteresEntity a convertir.
     * @return Lista de SitioInteresDTO convertida.
     */
    private List<SitioInteresDetailDTO> sitioInteresListEntity2DTO(List<SitioInteresEntity> entityList) {
        List<SitioInteresDetailDTO> list = new ArrayList();
        for (SitioInteresEntity entity : entityList) {
            list.add(new SitioInteresDetailDTO(entity));
        }
        return list;
    }

    /**
     * Convierte una lista de SitioInteresDetailDTO a una lista de SitioInteresEntity.
     *
     * @param dtos Lista de SitioInteresDetailDTO a convertir.
     * @return Lista de SitioInteresEntity convertida.
     */
    private List<SitioInteresEntity> sitioInteresListDTO2Entity(List<SitioInteresDetailDTO> dtos) {
        List<SitioInteresEntity> list = new ArrayList<>();
        for (SitioInteresDetailDTO dto : dtos) {
            list.add(dto.toEntity());
        }
        return list;
    }
}
