/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.vivienda.resources;

import co.edu.uniandes.csw.vivienda.dtos.SitioInteresDTO;
import co.edu.uniandes.csw.vivienda.dtos.SitioInteresDetailDTO;
import co.edu.uniandes.csw.vivienda.ejb.SitioInteresLogic;
import co.edu.uniandes.csw.vivienda.ejb.ViviendaLogic;
import co.edu.uniandes.csw.vivienda.entities.SitioInteresEntity;
import co.edu.uniandes.csw.vivienda.entities.ViviendaEntity;
import co.edu.uniandes.csw.vivienda.exceptions.BusinessLogicException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
 * Clase que implementa el recurso "sitioInteres"
 * @author estudiante
 */
@Produces("application/json")
@Consumes("application/json")
@Path("sitiosInteres")
public class SitioInteresResource {
    private static final Logger LOGGER = Logger.getLogger(SitioInteresResource.class.getName());
    private static final String SITIO_INTERES = "/sitioInteres/";
    private static final String NO_EXISTE = " no existe.";
    
    @Inject 
    SitioInteresLogic sitioInteresLogic;
    
    @Inject
    ViviendaLogic viviendaLogic;
    
       /**
     * Crea un nuevo sitioInteres con la informacion que se recibe en el cuerpo de la
     * petición y se regresa un objeto identico con un id auto-generado por la
     * base de datos.
     *
     * @param viviendaId El ID de la vivienda del cual se le agrega el sitioInteres
     * @param sitioInteres {@link SitioInteresDTO} - El sitioInteres que se desea guardar.
     * @return JSON {@link SitioInteresDTO} - El sitioInteres guardada con el atributo id
     * autogenerado.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando ya existe un sitioInteres con esa longitud y latitud.
     */
    @POST
    public SitioInteresDTO createSitioInteres(@PathParam("viviendaId") Long viviendaId, SitioInteresDTO sitioInteres) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "SitioInteresResource createSitioInteres: input: {0}", sitioInteres);
        SitioInteresEntity entity = sitioInteres.toEntity();
        SitioInteresDTO sitioInteresDTO = new SitioInteresDTO(sitioInteresLogic.createSitioInteres(viviendaId, entity));
        LOGGER.log(Level.INFO, "SitioInteresResource createSitioInteres: output: {0}", sitioInteresDTO);
        return sitioInteresDTO;
    }
    
    @POST
    @Path("generardatos")
    public List<SitioInteresDTO> generarDatos() {
        
        List<ViviendaEntity> viviendas = viviendaLogic.getViviendas();
        List<SitioInteresDTO> sitios = new ArrayList<>();
        for(ViviendaEntity vivienda: viviendas)
        {
        sitioInteresLogic.generarDatos(vivienda.getId());
        List<SitioInteresEntity> sitiosInteres = sitioInteresLogic.getSitiosInteres(vivienda.getId());
        for(SitioInteresEntity sitio: sitiosInteres)
        {
            SitioInteresDTO sitioDTO = new SitioInteresDTO(sitio);
            sitios.add(sitioDTO);
        }

        }
        return sitios;
    }
    /**
     * Remplaza las instancias de SitioInteres asociadas a una instancia de Vivienda
     *
     * @param viviendaId Identificador de la vivienda que se esta
     * remplazando. Este debe ser una cadena de dígitos.
     * @return JSON {@link SitioInteresDTO} - El arreglo de sitiosInteres guardado en la
     * vivienda.
     */
    @GET
    public List<SitioInteresDetailDTO> getSitiosInteres(@PathParam("viviendaId") Long viviendaId) {
        LOGGER.log(Level.INFO, "SitioInteresResource getSitiosInteres: input: {0}", viviendaId);
        List<SitioInteresDetailDTO> listaDetailDTOs = sitioInteresListEntity2DTO(sitioInteresLogic.getSitiosInteres(viviendaId));
        LOGGER.log(Level.INFO, "SitioInteresResource getSitiosInteres: output: {0}", listaDetailDTOs);
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
        LOGGER.log(Level.INFO, "SitioInteresResource getSitioInteres: input: viviendaID: {0} , sitioInteresId: {1}", new Object[]{viviendaId, sitioInteresId});
        if (sitioInteresLogic.getSitioInteres(viviendaId, sitioInteresId) == null) {
            throw new WebApplicationException("El recurso /vivienda/" + viviendaId + SITIO_INTERES + sitioInteresId + NO_EXISTE, 404);
        }
        SitioInteresDetailDTO sitioInteresDTO = new SitioInteresDetailDTO(sitioInteresLogic.getSitioInteres(viviendaId, sitioInteresId));
        LOGGER.log(Level.INFO, "SitioInteresResource getSitioInteres: output: {0}", sitioInteresDTO);
        return sitioInteresDTO;
    }
    
        /**
     * Actualiza un sitioInteres con la informacion que se recibe en el cuerpo de la
     * petición y se regresa el objeto actualizado.
     *
     * @param viviendaId El ID del libro del cual se guarda la vivienda
     * @param sitioInteresId El ID del sitioInteres que se va a actualizar
     * @param sitioInteres {@link ReviewDTO} - El sitioInteres que se desea guardar.
     * @return JSON {@link SitioInteresDTO} - El sitioInteres actualizada.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando ya existe el sitioInteres.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el sitioInteres.
     */
    @PUT
    @Path("{sitioInteresId: \\d+}")
    public SitioInteresDetailDTO updateSitioInteres(@PathParam("viviendaId") Long viviendaId, @PathParam("sitioInteresId")Long sitioInteresId, SitioInteresDTO sitioInteres)throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "SitioInteresResource updateSitioInteres: input: viviendaId: {0} , sitioInteresId: {1} , sitioInteres:{2}", new Object[]{viviendaId, sitioInteresId, sitioInteres});
        SitioInteresEntity entity = sitioInteresLogic.getSitioInteres(viviendaId, sitioInteresId);
        if (entity == null) {
            throw new WebApplicationException("El recurso /viviendas/" + viviendaId + SITIO_INTERES + sitioInteresId + NO_EXISTE, 404);
        }
        sitioInteres.setId(sitioInteresId);
        SitioInteresDetailDTO sitioInteresDTO = new SitioInteresDetailDTO(sitioInteresLogic.updateSitioInteres(viviendaId, sitioInteres.toEntity()));
        LOGGER.log(Level.INFO, "SitioInteresResource updateSitioInteres: output:{0}", sitioInteresDTO);
        return sitioInteresDTO;
    }
    
     /**
     * Borra el sitioInteres con el id asociado recibido en la URL.
     *
     * @param viviendaId El ID de la viviena del cual se va a eliminar el sitioInteres.
     * @param sitioInteresId El ID del sitioInteres que se va a eliminar.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando no se puede eliminar el sitioInteres.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el sitioInteres.
     */
    @DELETE
    @Path("{sitioInteresId: \\d+}")
    public void deleteSitioInteres(@PathParam("viviendaId") Long viviendaId, @PathParam("sitioInteresId")Long sitioInteresId)throws BusinessLogicException
    {
        SitioInteresEntity entity = sitioInteresLogic.getSitioInteres(viviendaId, sitioInteresId);
        if (entity == null) {
            throw new WebApplicationException("El recurso /viviendas/" + viviendaId + SITIO_INTERES + sitioInteresId + NO_EXISTE, 404);
        }
        sitioInteresLogic.deleteSitioInteres(viviendaId, sitioInteresId);
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
    
}
