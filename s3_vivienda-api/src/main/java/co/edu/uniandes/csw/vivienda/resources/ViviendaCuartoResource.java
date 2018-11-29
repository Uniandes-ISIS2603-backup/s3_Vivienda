/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.vivienda.resources;

import co.edu.uniandes.csw.vivienda.dtos.CuartoDTO;
import co.edu.uniandes.csw.vivienda.ejb.CuartoLogic;
import co.edu.uniandes.csw.vivienda.entities.CuartoEntity;
import co.edu.uniandes.csw.vivienda.exceptions.BusinessLogicException;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.*;

/**
 * Implementa el recurso /viviendas/{id}/cuartos
 *
 * @author estudiante
 */
@Consumes("application/json")
@Produces("application/json")
public class ViviendaCuartoResource {
    @Inject
    CuartoLogic logic;

    /**
     * Metodo para obtener todos los cuartos de una vivienda
     *
     * @param viviendaId Identificador de la vivienda de la que se quieren los cuartos
     * @return Lista con todos los cuartos que tiene una vivienda.
     */
    @GET
    public List<CuartoDTO> getCuartos(@PathParam("viviendaId") Long viviendaId) {
        List<CuartoEntity> cuartos = logic.getCuartos(viviendaId);
        ArrayList<CuartoDTO> cuartoDTOS = new ArrayList<>();
        for (CuartoEntity entity : cuartos) {
            cuartoDTOS.add(new CuartoDTO(entity));
        }
        return cuartoDTOS;
    }

    /**
     * Metodo para obtener un cuarto dentro de una vivienda
     *
     * @param viviendaId Identificador de la vivienda.
     * @param cuartoId   Identificador del cuarto que se quiere.
     * @return DTO del cuarto pedido en la vivienda
     * @throws BusinessLogicException Excepcion en caso de no poder traer el cuarto.
     */
    @GET
    @Path("{cuartoId:\\d+}")
    public CuartoDTO getCuarto(@PathParam("viviendaId") Long viviendaId, @PathParam("cuartoId") Long cuartoId) throws BusinessLogicException {
        CuartoEntity cuartoEntity = logic.getCuarto(viviendaId, cuartoId);
        return new CuartoDTO(cuartoEntity);
    }

    /**
     * Metodo para crear un cuarto dentro de una vivienda
     *
     * @param viviendaId Identificador de la vivienda
     * @param cuartoDTO  DTO del cuarto a crear
     * @return DTO del cuarto que se va a crear
     * @throws BusinessLogicException Excepcion en caso de que no se pueda crear correctamente el cuarto
     */
    @POST
    public CuartoDTO createCuarto(@PathParam("viviendaId") Long viviendaId, CuartoDTO cuartoDTO) throws BusinessLogicException {
        CuartoEntity cuartoEntity = logic.addCuarto(viviendaId, cuartoDTO.toEntity());
        return new CuartoDTO(cuartoEntity);
    }

    /**
     * Actualiza un contrato con la informacion que se mande por medio del entity
     *
     * @param viviendaId Identificador de la vivienda
     * @param cuartoId   Identificador del cuarto
     * @param cuartoDTO  DTO del cuarto a modificar
     * @return DTO del cuarto que se modifica en una vivienda
     * @throws BusinessLogicException Excepcion en caso de que no se pueda actualizar el cuarto
     */
    @PUT
    @Path("{cuartoId:\\d+}")
    public CuartoDTO updateCuarto(@PathParam("viviendaId") Long viviendaId, @PathParam("cuartoId") Long cuartoId, CuartoDTO cuartoDTO) throws BusinessLogicException {
        CuartoEntity cuartoEntity = null;
        if (logic != null)
            cuartoEntity = logic.actualizarCuarto(viviendaId, cuartoId, cuartoDTO.toEntity());
        return new CuartoDTO(cuartoEntity);
    }

    /**
     * Metodo para eliminar un cuarto dentro de una vivienda
     *
     * @param viviendaId Identificador de la vivienda
     * @param cuartoId   Identificador del cuarto que se quiere eliminar
     * @throws BusinessLogicException Excepcion en caso de que no se pueda eliminar el cuarto.
     */
    @DELETE
    @Path("{cuartoId:\\d+}")
    public void deleteCuarto(@PathParam("viviendaId") Long viviendaId, @PathParam("cuartoId") Long cuartoId) throws BusinessLogicException {
        if (logic != null)
            logic.deleteCuarto(cuartoId);
    }
}
