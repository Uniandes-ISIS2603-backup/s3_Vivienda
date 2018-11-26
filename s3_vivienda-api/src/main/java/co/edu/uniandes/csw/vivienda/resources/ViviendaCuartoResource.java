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
 * @author estudiante
 */
@Consumes("application/json")
@Produces("application/json")
public class ViviendaCuartoResource {
    @Inject
    CuartoLogic logic;

    @GET
    public List<CuartoDTO> getCuartos(@PathParam("viviendaId") Long viviendaId){
        List<CuartoEntity> cuartos = logic.getCuartos(viviendaId);
        ArrayList<CuartoDTO> cuartoDTOS = new ArrayList<>();
        for(CuartoEntity entity : cuartos){
            cuartoDTOS.add(new CuartoDTO(entity));
        }
        return cuartoDTOS;
    }

    @GET
    @Path("{cuartoId:\\d+}")
    public CuartoDTO getCuarto(@PathParam("viviendaId") Long viviendaId, @PathParam("cuartoId") Long cuartoId) throws BusinessLogicException{
        CuartoEntity cuartoEntity = logic.getCuarto(viviendaId, cuartoId);
        return new CuartoDTO(cuartoEntity);
    }

    @POST
    public CuartoDTO createCuarto(@PathParam("viviendaId") Long viviendaId, CuartoDTO cuartoDTO) throws BusinessLogicException{
            CuartoEntity cuartoEntity = logic.addCuarto(viviendaId, cuartoDTO.toEntity());
            return new CuartoDTO(cuartoEntity);
    }

    @PUT
    @Path("{cuartoId:\\d+}")
    public CuartoDTO updateCuarto(@PathParam("viviendaId") Long viviendaId, @PathParam("cuartoId") Long cuartoId, CuartoDTO cuartoDTO) throws BusinessLogicException{
        CuartoEntity cuartoEntity = logic.actualizarCuarto(viviendaId, cuartoId, cuartoDTO.toEntity());
        return new CuartoDTO(cuartoEntity);
    }

    @DELETE
    @Path("{cuartoId:\\d+}")
    public void deleteCuarto(@PathParam("viviendaId") Long viviendaId, @PathParam("cuartoId") Long cuartoId) throws BusinessLogicException{
        logic.deleteCuarto(cuartoId);
    }
}
