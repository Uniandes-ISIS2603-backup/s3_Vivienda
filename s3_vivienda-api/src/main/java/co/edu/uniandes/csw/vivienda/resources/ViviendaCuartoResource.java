/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.vivienda.resources;

import co.edu.uniandes.csw.vivienda.dtos.CuartoDTO;
import co.edu.uniandes.csw.vivienda.dtos.ViviendaDTO;
import co.edu.uniandes.csw.vivienda.ejb.CuartoLogic;
import co.edu.uniandes.csw.vivienda.entities.CuartoEntity;
import co.edu.uniandes.csw.vivienda.entities.ViviendaEntity;
import co.edu.uniandes.csw.vivienda.exceptions.BusinessLogicException;
import sun.misc.CEFormatException;

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
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;

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
    public CuartoDTO getCuarto(@PathParam("viviendaId") Long viviendaId, @PathParam("cuartoId") Long cuartoId){
        try {
            CuartoEntity cuartoEntity = logic.getCuarto(viviendaId, cuartoId);
            CuartoDTO cuarto = new CuartoDTO(cuartoEntity);
            return cuarto;
        } catch (BusinessLogicException e) 
        {
            e.getMessage();
            return null;
        }
    }

    @POST
    public CuartoDTO createCuarto(@PathParam("viviendaId") Long viviendaId, CuartoDTO cuartoDTO){
        try {
            CuartoEntity cuartoEntity = logic.addCuarto(viviendaId, cuartoDTO.toEntity());
            return new CuartoDTO(cuartoEntity);
        } catch (BusinessLogicException e){
            e.getMessage();
            return null;
        }
    }
}
