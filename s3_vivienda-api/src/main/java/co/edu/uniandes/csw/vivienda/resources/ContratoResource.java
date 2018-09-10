/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.vivienda.resources;

import co.edu.uniandes.csw.vivienda.dtos.ContratoDTO;
import co.edu.uniandes.csw.vivienda.dtos.ContratoDetailDTO;
import co.edu.uniandes.csw.vivienda.ejb.ContratoLogic;
import co.edu.uniandes.csw.vivienda.ejb.ContratoViviendaLogic;
//import co.edu.uniandes.csw.vivienda.ejb.ContratoLogic;
import co.edu.uniandes.csw.vivienda.ejb.ViviendaLogic;
import co.edu.uniandes.csw.vivienda.entities.ContratoEntity;
import co.edu.uniandes.csw.vivienda.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.vivienda.mappers.BusinessLogicExceptionMapper;
import co.edu.uniandes.csw.vivienda.mappers.WebApplicationExceptionMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
//import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;

@Path("contratos")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class ContratoResource {

    private static final Logger LOGGER = Logger.getLogger(ContratoResource.class.getName());

    @Inject
    private ContratoLogic contratoLogic; // Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.

    @Inject
    private ViviendaLogic viviendaLogic; // Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.

    @Inject
    private ContratoViviendaLogic contratoViviendaLogic; // Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.

    /**
     * Crea un nuevo contrato con la informacion que se recibe en el cuerpo de la
     * petición y se regresa un objeto identico con un id auto-generado por la
     * base de datos.
     *
     * @param contrato {@link ContratoDTO} - El contrato que se desea guardar.
     * @return JSON {@link ContratoDTO} - El contrato guardado con el atributo id
     * autogenerado.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando ya existe el contrato o el metodoPago es
     * inválido o si la vivienda ingresada es invalida.
     */
    @POST
    public ContratoDTO createBook(ContratoDTO contrato) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "ContratoResource createContrato: input: {0}", contrato.toString());
        ContratoDTO nuevoContratoDTO = new ContratoDTO(contratoLogic.createContrato(contrato.toEntity()));
        LOGGER.log(Level.INFO, "ContratoResource createContrato: output: {0}", nuevoContratoDTO.toString());
        return nuevoContratoDTO;
    }

    /**
     * Busca y devuelve todos los contratos que existen en la aplicacion.
     *
     * @return JSONArray {@link ContratoDetailDTO} - Los contratos encontrados en la
     * aplicación. Si no hay ninguno retorna una lista vacía.
     */
    @GET
    public List<ContratoDetailDTO> getBooks() {
        LOGGER.info("ContratoResource getContratos: input: void");
        List<ContratoDetailDTO> listaContratos = listEntity2DetailDTO(contratoLogic.getContratos());
        LOGGER.log(Level.INFO, "ContratoResource getContratos: output: {0}", listaContratos.toString());
        return listaContratos;
    }

    /**
     * Busca el contrato con el id asociado recibido en la URL y lo devuelve.
     *
     * @param contratoId Identificador del contrato que se esta buscando. Este debe
     * ser una cadena de dígitos.
     * @return JSON {@link ContratoDetailDTO} - El contrato buscado
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el contrato.
     */
    @GET
    @Path("{contratoId: \\d+}")
    public ContratoDetailDTO getBook(@PathParam("contratoId") Long contratoId) {
        LOGGER.log(Level.INFO, "ContratoResource getContrato: input: {0}", contratoId);
        ContratoEntity contratoEntity = contratoLogic.getContrato(contratoId);
        if (contratoEntity == null) {
            throw new WebApplicationException("El recurso /contratos/" + contratoId + " no existe.", 404);
        }
        ContratoDetailDTO bookDetailDTO = new ContratoDetailDTO(contratoEntity);
        LOGGER.log(Level.INFO, "ContratoResource getContrato: output: {0}", bookDetailDTO.toString());
        return bookDetailDTO;
    }

    /**
     * Actualiza el contrato con el id recibido en la URL con la información que se
     * recibe en el cuerpo de la petición.
     *
     * @param contratoId Identificador del contrato que se desea actualizar. Este debe
     * ser una cadena de dígitos.
     * @param contrato {@link BookDTO} El contrato que se desea guardar.
     * @return JSON {@link BookDetailDTO} - El contrato guardado.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el contrato a
     * actualizar.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando no se puede actualizar el contrato.
     */
    @PUT
    @Path("{contratoId: \\d+}")
    public ContratoDetailDTO updateContrato(@PathParam("contratoId") Long contratoId, ContratoDTO contrato) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "ContratoResource updateContrato: input: id: {0} , contrato: {1}", new Object[]{contratoId, contrato.toString()});
        contrato.setId(contratoId);
        if (contratoLogic.getContrato(contratoId) == null) {
            throw new WebApplicationException("El recurso /contratos/" + contratoId + " no existe.", 404);
        }
        ContratoDetailDTO detailDTO = new ContratoDetailDTO(contratoLogic.updateContrato(contratoId, contrato.toEntity()));
        LOGGER.log(Level.INFO, "ContratoResource updateContrato: output: {0}", detailDTO.toString());
        return detailDTO;
    }

    /**
     * Borra el contrato con el id asociado recibido en la URL.
     *
     * @param contratoId Identificador del contrato que se desea borrar. Este debe ser
     * una cadena de dígitos.
     * @throws co.edu.uniandes.csw.bookstore.exceptions.BusinessLogicException
     * cuando el contrato tiene autores asociados.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el contrato.
     */
    @DELETE
    @Path("{contratoId: \\d+}")
    public void deleteContrato(@PathParam("contratoId") Long contratoId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "ContratoResource deleteContrato: input: {0}", contratoId);
        ContratoEntity entity = contratoLogic.getContrato(contratoId);
        if (entity == null) {
            throw new WebApplicationException("El recurso /contratos/" + contratoId + " no existe.", 404);
        }
        contratoViviendaLogic.removeVivienda(contratoId);
        contratoLogic.deleteContrato(contratoId);
        LOGGER.info("ContratoResource deleteContrato: output: void");
    }

    /**
     * Conexión con el servicio de serviciosAdicionales para un contrato. {@link ServicioAdicionalResource}
     *
     * Este método conecta la ruta de /contratos con las rutas de /serviciosAdicionalesAgregados que
     * dependen del contrato, es una redirección al servicio que maneja el segmento
     * de la URL que se encarga de los serviciosAgregados.
     *
     * @param contratoId El ID del contrato con respecto al cual se accede al
     * servicio.
     * @return El servicio de ServiciosAdicionalesAgregados para ese contrato en paricular.\
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el contrato.
     */
    @Path("{contratoId: \\d+}/serviciosAdicionalesAgregados")
    public Class<ServicioAdicionalResource> getServicioAdicionalResource(@PathParam("contratoId") Long contratoId) {
        if (contratoLogic.getContrato(contratoId) == null) {
            throw new WebApplicationException("El recurso /contratos/" + contratoId + "/serviciosAdicionalesAgregados no existe.", 404);
        }
        return ServicioAdicionalResource.class;
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
