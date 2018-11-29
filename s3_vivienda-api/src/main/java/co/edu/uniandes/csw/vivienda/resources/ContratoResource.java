package co.edu.uniandes.csw.vivienda.resources;


import co.edu.uniandes.csw.vivienda.dtos.ContratoDTO;
import co.edu.uniandes.csw.vivienda.dtos.ContratoDetailDTO;
import co.edu.uniandes.csw.vivienda.dtos.EstudianteDetailDTO;
import co.edu.uniandes.csw.vivienda.ejb.ContratoLogic;
import co.edu.uniandes.csw.vivienda.entities.ContratoEntity;
import co.edu.uniandes.csw.vivienda.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.vivienda.mappers.BusinessLogicExceptionMapper;
import co.edu.uniandes.csw.vivienda.mappers.WebApplicationExceptionMapper;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase que implementa el recurso "contratos".
 */
@Path("contratos")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class ContratoResource {

    private static final Logger LOGGER = Logger.getLogger(ContratoResource.class.getName());
    
    private static final String NO_EXISTE = " no existe.";

    @Inject
    private ContratoLogic contratoLogic; // Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.

    /**
     * Crea un nuevo contrato con la informacion que se recibe en el cuerpo de la
     * petición y se regresa un objeto identico con un id auto-generado por la
     * base de datos.
     *
     * @param contrato {@link ContratoDTO} - EL contrato que se desea guardar.
     * @return JSON {@link ContratoDTO} - El contrato guardado con el atributo id
     * autogenerado.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando ya existe el contrato o el id es
     * inválido o si la vivienda ingresada es invalida.
     */
    @POST
    public ContratoDTO createContrato(ContratoDTO contrato) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "BookResource createBook: input: {0}", contrato);
        ContratoDTO nuevoBookDTO = new ContratoDTO(contratoLogic.createContrato(contrato.toEntity()));
        LOGGER.log(Level.INFO, "BookResource createBook: output: {0}", nuevoBookDTO);
        return nuevoBookDTO;
    }

    @POST
    @Path("generardatos")
    public List<ContratoDTO> generarDatos() {
        contratoLogic.generarDatos();
        List<ContratoEntity> contratos = contratoLogic.getContratos();
        if (contratos == null) {
            throw new WebApplicationException("El recurso " + contratos + NO_EXISTE, 404);
        }
        ArrayList<ContratoDTO> respuestas = new ArrayList<>();

        for (ContratoEntity ent: contratos){
            ContratoDTO contratodto = new ContratoDTO(ent);
            respuestas.add(contratodto);
        }
        return respuestas;
    }

    /**
     * Busca y devuelve todos los contratos que existen en la aplicacion.
     *
     * @return JSONArray {@link ContratoDetailDTO} - Los contratos encontrados en la
     * aplicación. Si no hay ninguno retorna una lista vacía.
     */
    @GET
    public List<ContratoDetailDTO> getContratos() {
        LOGGER.info("ContratoResource getContratos: input: void");
        List<ContratoDetailDTO> listaContratos = listEntity2DetailDTO(contratoLogic.getContratos());
        LOGGER.log(Level.INFO, "ContratoResource getContratos: output: {0}", listaContratos);
        return listaContratos;
    }
    
        /**
     * Actualiza el contrato con el id recibido en la URL con la información que se
     * recibe en el cuerpo de la petición.
     *
     * @param contratoId Identificador del contrato que se desea actualizar. Este debe
     * ser una cadena de dígitos.
     * @param contrato {@link ContratoDTO} El contrato que se desea guardar.
     * @return JSON {@link EstudianteDetailDTO} - El contrato guardado.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el contrato a
     * actualizar.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando no se puede actualizar el contrato.
     */
    @PUT
    @Path("{contratoId:\\d+}")
    public ContratoDetailDTO updateContrato(@PathParam("contratoId") Long contratoId, ContratoDTO contrato) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "ContratoResource updateContrato: input: id: {0} , estudiante: {1}", new Object[]{contratoId, contrato});
        contrato.setId(contratoId);
        if (contratoLogic.getContrato(contratoId) == null) {
            throw new WebApplicationException("El recurso /contratos/" + contratoId + NO_EXISTE, 404);
        }
        ContratoDetailDTO detailDTO = new ContratoDetailDTO(contratoLogic.updateContrato(contratoId, contrato.toEntity()));
        LOGGER.log(Level.INFO, "ContratoResource updateContrato: output: {0}", detailDTO);
        return detailDTO;
    }

    /**
     * Busca el contrato con el id asociado recibido en la URL y lo devuelve.
     *
     * @param contratoId Identificador del contrato que se esta buscando. Este debe
     * ser una cadena de dígitos.
     * @return JSON {@link ContratoDetailDTO} - El contrato buscado
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el libro.
     */
    @GET
    @Path("{contratoId: \\d+}")
    public ContratoDetailDTO getContrato(@PathParam("contratoId") Long contratoId) {
        LOGGER.log(Level.INFO, "ContratoResource getContrato: input: {0}", contratoId);
        ContratoEntity contratoEntity = contratoLogic.getContrato(contratoId);
        if (contratoEntity == null) {
            throw new WebApplicationException("El recurso /contratos/" + contratoId + NO_EXISTE, 404);
        }
        ContratoDetailDTO contratoDetailDTO = new ContratoDetailDTO(contratoEntity);
        LOGGER.log(Level.INFO, "ContratoResource getContrato: output: {0}", contratoDetailDTO);
        return contratoDetailDTO;
    }

    /**
     * Convierte una lista de entidades a DTO.
     *
     * Este método convierte una lista de objetos ContratoEntity a una lista de
     * objetos ContratoDetailDTO (json)
     *
     * @param entityList corresponde a la lista de contratos de tipo Entity que
     * vamos a convertir a DTO.
     * @return la lista de contratos en forma DTO (json)
     */
    private List<ContratoDetailDTO> listEntity2DetailDTO(List<ContratoEntity> entityList) {
        List<ContratoDetailDTO> list = new ArrayList<>();
        for (ContratoEntity entity : entityList) {
            list.add(new ContratoDetailDTO(entity));
        }
        return list;
    }
}
