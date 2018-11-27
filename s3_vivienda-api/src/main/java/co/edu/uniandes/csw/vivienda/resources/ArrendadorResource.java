/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.vivienda.resources;

import co.edu.uniandes.csw.vivienda.dtos.ArrendadorDTO;
import co.edu.uniandes.csw.vivienda.dtos.ArrendadorDetailDTO;
import co.edu.uniandes.csw.vivienda.ejb.ArrendadorLogic;
import co.edu.uniandes.csw.vivienda.entities.ArrendadorEntity;
import co.edu.uniandes.csw.vivienda.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.vivienda.mappers.*;

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

/**
 * Clase que implementa el recurso "arrendadores"
 *
 * @author estudiante
 */
@Path("arrendadores")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class ArrendadorResource {

    private static final Logger LOGGER = Logger.getLogger(ArrendadorResource.class.getName());

    private static final String RECURSO_ARRENDADORES = "El recurso /arrendadores/";
    private static final String NO_EXISTE = " no existe.";

    @Inject
    private ArrendadorLogic arrendadorLogic;

    /**
     * Crea un nuevo arrendador con la informacion que se recibe en el cuerpo de la
     * petición y se regresa un objeto identico con un id auto-generado por la
     * base de datos.
     *
     * @param arrendador {@link ArrendadorDTO} - EL arrendador que se desea guardar.
     * @return JSON {@link ArrendadorDTO} - El arrendador guardado con el atributo id
     * autogenerado.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     *                                Error de lógica que se genera cuando ya existe el arrendador con un mismo login
     */
    @POST
    public ArrendadorDTO createArrendador(ArrendadorDTO arrendador) throws BusinessLogicException {

        LOGGER.log(Level.INFO, "ArrendadorResource createArrendador: input: {0}", arrendador);
        // Convierte el DTO (json) en un objeto Entity para ser manejado por la lógica.
        ArrendadorEntity arrendadorEntity = arrendador.toEntity();

        ArrendadorEntity nuevoArrendadorEntity = arrendadorLogic.createArrendador(arrendadorEntity);

        ArrendadorDTO nuevoArrendadorDTO = new ArrendadorDTO(nuevoArrendadorEntity);
        LOGGER.log(Level.INFO, "ArrendadorResource createArrendador: output: {0}", nuevoArrendadorDTO);
        return nuevoArrendadorDTO;
    }

    /**
     * Metodo para generar los datos de arrendador
     *
     * @return Lista con los arrendadores creados
     */
    @POST
    @Path("generardatos")
    public List<ArrendadorDetailDTO> generarDatos() throws BusinessLogicException, WebApplicationException {
        List<ArrendadorEntity> arrendadores = arrendadorLogic.generarDatos();

        ArrayList<ArrendadorDetailDTO> respuestas = new ArrayList<>();

        if (arrendadores != null) {
            for (ArrendadorEntity ent : arrendadores) {
                ArrendadorDetailDTO arrendador = new ArrendadorDetailDTO(ent);
                respuestas.add(arrendador);
            }
        }
        return respuestas;
    }

    /**
     * Busca y devuelve todas los arrendadores que existen en la aplicacion.
     *
     * @return JSONArray {@link ArrendadorDetailDTO} - Los arrendadores
     * encontradas en la aplicación. Si no hay ninguna retorna una lista vacía.
     */
    @GET
    public List<ArrendadorDetailDTO> getArrendadores() {
        LOGGER.info("ArrendadorResource getArrendadores: input: void");
        List<ArrendadorDetailDTO> listaArrendadores = listEntity2DetailDTO(arrendadorLogic.getArrendadores());
        LOGGER.log(Level.INFO, "ArrendadorResource getArrendadores: output: {0}", listaArrendadores);
        return listaArrendadores;
    }

    /**
     * Convierte una lista de entidades a DTO.
     * <p>
     * Este método convierte una lista de objetos ArrendadorEntity a una lista de
     * objetos ArrendadorDetailDTO (json)
     *
     * @param entityList corresponde a la lista de arrendadores de tipo Entity
     *                   que vamos a convertir a DTO.
     * @return la lista de arrendadores en forma DTO (json)
     */
    private List<ArrendadorDetailDTO> listEntity2DetailDTO(List<ArrendadorEntity> entityList) {
        List<ArrendadorDetailDTO> list = new ArrayList<>();
        for (ArrendadorEntity entity : entityList) {
            list.add(new ArrendadorDetailDTO(entity));
        }
        return list;
    }

    /**
     * Busca el arrendador con el id asociado recibido en la URL y la devuelve.
     *
     * @param arrendadorId Identificador de el arrendador que se esta buscando.
     *                     Este debe ser una cadena de dígitos.
     * @return JSON {@link ArrendadorDetailDTO} - El arrendador buscado
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     *                                 Error de lógica que se genera cuando no se encuentra el arrendador.
     */
    @GET
    @Path("{arrendadorId:\\d+}")
    public ArrendadorDetailDTO getArrendador(@PathParam("arrendadorId") Long arrendadorId) {
        LOGGER.log(Level.INFO, "ArrendadorResource getArrendador: input: {0}", arrendadorId);
        ArrendadorEntity arrendadorEntity = arrendadorLogic.getArrendador(arrendadorId);
        if (arrendadorEntity == null) {
            throw new WebApplicationException(RECURSO_ARRENDADORES + arrendadorId + NO_EXISTE, 404);
        }
        ArrendadorDetailDTO detailDTO = new ArrendadorDetailDTO(arrendadorEntity);
        LOGGER.log(Level.INFO, "ArrendadorResource getArrendador: output: {0}", detailDTO);
        return detailDTO;
    }

    /**
     * Actualiza el arrendador con el id recibido en la URL con la informacion
     * que se recibe en el cuerpo de la petición.
     *
     * @param arrendadorId Identificador del arrendador que se desea
     *                     actualizar. Este debe ser una cadena de dígitos.
     * @param arrendador   {@link ArrendadorDetailDTO} El arrendador que se desea
     *                     guardar.
     * @return JSON {@link ArrendadorDetailDTO} - El arrendador guardado.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     *                                 Error de lógica que se genera cuando no se encuentra el arrendador a
     *                                 actualizar.
     */
    @PUT
    @Path("{arrendadorId:\\d+}")
    public ArrendadorDetailDTO updateArrendador(@PathParam("arrendadorId") Long arrendadorId, ArrendadorDTO arrendador) {
        LOGGER.log(Level.INFO, "ArrendadorResource updateArrendador: input: id:{0} , arrendador: {1}", new Object[]{arrendadorId, arrendador});
        arrendador.setId(arrendadorId);
        if (arrendadorLogic.getArrendador(arrendadorId) == null) {
            throw new WebApplicationException(RECURSO_ARRENDADORES + arrendadorId + NO_EXISTE, 404);
        }
        arrendador.setId(arrendadorId);
        ArrendadorDetailDTO arrendadorDetailDTO = new ArrendadorDetailDTO(arrendadorLogic.updateArrendador(arrendadorId, arrendador.toEntity()));
        LOGGER.log(Level.INFO, "ArrendadorResource updateArrendador: output: {0}", arrendadorDetailDTO);
        return arrendadorDetailDTO;

    }

    /**
     * Borra el arrendador con el id asociado recibido en la URL.
     *
     * @param arrendadorId Identificador dl arrendador que se desea borrar.
     *                     Este debe ser una cadena de dígitos.
     * @throws BusinessLogicException  {@link BusinessLogicExceptionMapper} -
     *                                 Error de lógica que se genera cuando no se puede eliminar el arrendador.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     *                                 Error de lógica que se genera cuando no se encuentra el arrendador.
     */
    @DELETE
    @Path("{arrendadorId:\\d+}")
    public void deleteArrendador(@PathParam("arrendadorId") Long arrendadorId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "ArrendadorResource.deleteArrendador: input:{0}", arrendadorId);
        if (arrendadorLogic.getArrendador(arrendadorId) == null) {
            throw new WebApplicationException(RECURSO_ARRENDADORES + arrendadorId + NO_EXISTE, 404);
        }
        arrendadorLogic.deleteArrendador(arrendadorId);
        LOGGER.info("ArrendadorResource deleteArrendador: output: void");
    }

    /**
     * Conexión con el servicio de viviendas para un arrendador.
     * {@link ArrendadorViviendasResource}
     * <p>
     * Este método conecta la ruta de /arrendadores con las rutas de /viviendas que
     * dependen del arrendador, es una redirección al servicio que maneja el
     * segmento de la URL que se encarga de las viviendas de un arrendador.
     *
     * @param arrendadorId El ID del arrendador con respecto a la cual se
     *                     accede al servicio.
     * @return El servicio de viviendas para este arrendador en paricular.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     *                                 Error de lógica que se genera cuando no se encuentra el arrendador.
     */
    @Path("{arrendadorId: \\d+}/viviendas")
    public Class<ArrendadorViviendasResource> getArrendadorViviendasResource(@PathParam("arrendadorId") Long arrendadorId) {
        if (arrendadorLogic.getArrendador(arrendadorId) == null) {
            throw new WebApplicationException(RECURSO_ARRENDADORES + arrendadorId + NO_EXISTE, 404);
        }
        return ArrendadorViviendasResource.class;
    }

}
