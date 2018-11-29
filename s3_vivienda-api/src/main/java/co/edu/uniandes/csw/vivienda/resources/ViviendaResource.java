/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.vivienda.resources;

import co.edu.uniandes.csw.vivienda.dtos.ViviendaDTO;
import co.edu.uniandes.csw.vivienda.dtos.ViviendaDetailDTO;
import co.edu.uniandes.csw.vivienda.ejb.CuartoLogic;
import co.edu.uniandes.csw.vivienda.ejb.ServicioAdicionalLogic;
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

    private static final String RECURSO_VIVIENDAS = "El recurso /viviendas/";
    private static final String NO_EXISTE = " no existe.";

    @Inject
    private ViviendaLogic logic;

    @Inject
    CuartoLogic cuartoLogic;
    
    @Inject
    ServicioAdicionalLogic servicioAdicionalLogic;

    private static final Logger LOGGER = Logger.getLogger(ViviendaResource.class.getName());

    /**
     * Metodo para poder crear una vivienda
     *
     * @param vivienda DTO de la vivienda que se va a crear
     * @return DTO para la vivienda que se quiere crear
     * @throws BusinessLogicException Excepcion en caso de que no se pueda crear la vivienda
     */
    @POST
    public ViviendaDTO createVivienda(ViviendaDTO vivienda) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "ViviendaResource.createVivienda: input:{0}", vivienda);
        ViviendaEntity viviendaEntity = vivienda.toEntity();

        ViviendaEntity newViviendaEntity = null;
        if (logic != null) {
            newViviendaEntity = logic.createVivienda(viviendaEntity);
        }
        ViviendaDTO viviendaDTO = new ViviendaDTO(newViviendaEntity);
        LOGGER.log(Level.INFO, "Saliendo de crear ViviendaResource.createVivienda: input:{0}", viviendaDTO);
        return viviendaDTO;
    }

    /**
     * Metodo para eliminar una vivienda especifica
     *
     * @param viviendaId Identificador de la vivienda
     * @throws BusinessLogicException Excepcion en caso de no poder eliminar una vivienda
     */
    @DELETE
    @Path("{viviendaId: \\d+}")
    public void deleteVivienda(@PathParam("viviendaId") Long viviendaId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "ViviendaResource.deleteVivienda: input:{0}", viviendaId);
        if (logic.getVivienda(viviendaId) == null) {
            throw new WebApplicationException(RECURSO_VIVIENDAS + viviendaId + NO_EXISTE, 404);
        }
        logic.deleteVivienda(viviendaId);
    }

    /**
     * Metodo para obtener una vivienda especifica
     *
     * @param viviendaId Identificador de la vivienda
     * @return DTO de la vivienda que se quiere obtener
     */
    @GET
    @Path("{viviendaId: \\d+}")
    public ViviendaDetailDTO getVivienda(@PathParam("viviendaId") Long viviendaId) {
        if (logic.getVivienda(viviendaId) == null) {
            throw new WebApplicationException(RECURSO_VIVIENDAS + viviendaId + NO_EXISTE, 404);
        }
        ViviendaEntity vivienda = logic.getVivienda(viviendaId);
        return new ViviendaDetailDTO(vivienda);
    }

    /**
     * Metodo para obtener todas las viviendas de la aplicacion
     *
     * @return Lista con todas las viviendas
     */
    @GET
    public List<ViviendaDetailDTO> getViviendas() {
        List<ViviendaEntity> viviendas = logic.getViviendas();
        ArrayList<ViviendaDetailDTO> respuestas = new ArrayList<>();

        if (viviendas != null) {
            for (ViviendaEntity ent : viviendas) {
                ViviendaDetailDTO viviendaDto = new ViviendaDetailDTO(ent);
                respuestas.add(viviendaDto);
            }
        }
        return respuestas;
    }

    /**
     * Metodo para actualizar una vivienda
     *
     * @param viviendaId Identificador de la vivienda
     * @param vivienda   DTO de la vivienda que se va a actualizar
     * @return DTO de la vivienda que sera actualizada
     * @throws BusinessLogicException Excepcion en caso de no poder actualizar la vivienda
     */
    @PUT
    @Path("{viviendaId: \\d+}")
    public ViviendaDTO updateVivienda(@PathParam("viviendaId") Long viviendaId, ViviendaDTO vivienda) throws BusinessLogicException {
        if (logic.getVivienda(viviendaId) == null) {
            throw new WebApplicationException(RECURSO_VIVIENDAS + viviendaId + NO_EXISTE, 404);
        }
        vivienda.setId(viviendaId);
        ViviendaEntity old = vivienda.toEntity();
        ViviendaDetailDTO viviendaDetail = new ViviendaDetailDTO(logic.updateVivienda(viviendaId, old));
        return viviendaDetail;
    }

    /**
     * Metodo para generar los datos de las viviendas
     *
     * @return Lista con los elementos de vivienda
     */
    @POST
    @Path("generardatos")
    public List<ViviendaDTO> generarDatos() {
        ArrayList<ViviendaDTO> respuestas = new ArrayList<>();
        if (logic == null)
            throw new WebApplicationException(RECURSO_VIVIENDAS + logic + NO_EXISTE, 404);
        else {
            logic.generarDatos();
            List<ViviendaEntity> viviendas = logic.getViviendas();

            for (ViviendaEntity ent : viviendas) {
                cuartoLogic.generarCuartos(ent.getId());
                servicioAdicionalLogic.generarServiciosAdicionales(ent.getId());
                ViviendaDTO viviendaDto = new ViviendaDTO(ent);
                respuestas.add(viviendaDto);
            }
        }
        return respuestas;
    }

    /**
     * Metodo para obtener cuartos de una vivienda
     *
     * @param viviendaId Identificador de la vivienda
     * @return Recurso de Cuarto
     */
    @Path("{viviendaId: \\d+}/cuartos")
    public Class<ViviendaCuartoResource> getViviendaCuartoResource(@PathParam("viviendaId") Long viviendaId) {
        if (logic.getVivienda(viviendaId) == null)
            throw new WebApplicationException(RECURSO_VIVIENDAS + viviendaId + NO_EXISTE, 404);
        else
            return ViviendaCuartoResource.class;

    }

    /**
     * Metodo para obtener arrendadores de una vivienda
     *
     * @param viviendaId Identificador de la vivienda
     * @return Recurso de Arrendador
     */
    @Path("{viviendaId: \\d+}/arrendadores")
    public Class<ViviendaArrendadorResource> getViviendaArrendadoresResource(@PathParam("viviendaId") Long viviendaId) {
        if (logic.getVivienda(viviendaId) == null)
            throw new WebApplicationException(RECURSO_VIVIENDAS + viviendaId + NO_EXISTE, 404);
        else
            return ViviendaArrendadorResource.class;
    }

    /**
     * Metodo para obtener calificaciones de una vivienda
     *
     * @param viviendaId Identificador de la vivienda
     * @return Recurso de Calificacion
     */
    @Path("{viviendaId: \\d+}/calificaciones")
    public Class<ViviendaCalificacionesResource> getViviendaCalificacionesResource(@PathParam("viviendaId") Long viviendaId) {
        if (logic.getVivienda(viviendaId) == null)
            throw new WebApplicationException(RECURSO_VIVIENDAS + viviendaId + NO_EXISTE, 404);
        else
            return ViviendaCalificacionesResource.class;
    }

    /**
     * Metodo para obtener Contrato de una vivienda
     *
     * @param viviendaId Identificador de la vivienda
     * @return Recurso de Contrato
     */
    @Path("{viviendaId: \\d+}/contratos")
    public Class<ViviendaContratosResource> getViviendaContratoResource(@PathParam("viviendaId") Long viviendaId) {
        if (logic.getVivienda(viviendaId) == null)
            throw new WebApplicationException(RECURSO_VIVIENDAS + viviendaId + NO_EXISTE, 404);
        else
            return ViviendaContratosResource.class;
    }

    /**
     * Metodo para obtener SitioInteres de una vivienda
     *
     * @param viviendaId Identificador de la vivienda
     * @return Recurso de SitioInteres
     */
    @Path("{viviendaId: \\d+}/sitioInteres")
    public Class<SitioInteresResource> getViviendaSitioInteresResource(@PathParam("viviendaId") Long viviendaId) {
        if (logic.getVivienda(viviendaId) == null)
            throw new WebApplicationException(RECURSO_VIVIENDAS + viviendaId + NO_EXISTE, 404);
        else
            return SitioInteresResource.class;
    }

    /**
     * Metodo para obtener los servicios adicionales de una vivienda
     *
     * @param viviendaId Identificador de la vivienda
     * @return Recurso de servicio adicional
     */
    @Path("{viviendaId: \\d+}/servicioAdicional")
    public Class<ServicioAdicionalResource> getServicioAdicionalResource(@PathParam("viviendaId") Long viviendaId) {
        if (logic.getVivienda(viviendaId) == null)
            throw new WebApplicationException(RECURSO_VIVIENDAS + viviendaId + NO_EXISTE, 404);
        else
            return ServicioAdicionalResource.class;
    }

}
