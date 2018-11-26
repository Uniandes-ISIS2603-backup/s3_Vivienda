/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.vivienda.ejb;

import co.edu.uniandes.csw.vivienda.entities.ViviendaEntity;
import co.edu.uniandes.csw.vivienda.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.vivienda.persistence.ViviendaPersistence;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.WebApplicationException;

/**
 * @author DANIEL
 */
@Stateless
public class ViviendaLogic {

    private static final Logger LOGGER = Logger.getLogger(ViviendaLogic.class.getName());
    
    @Inject
    private ViviendaPersistence persistence;

    public ViviendaEntity createVivienda(ViviendaEntity viviendaEntity) throws BusinessLogicException {

        if (viviendaEntity.getNombre() == null || viviendaEntity.getNombre().isEmpty()) {
            throw new WebApplicationException("La vivienda debe tener un nombre");
        }
        if (viviendaEntity.getCiudad() == null || viviendaEntity.getCiudad().isEmpty()) {
            throw new WebApplicationException("La vivienda debe tener una ciudad");
        }
        if (viviendaEntity.getDireccion() == null || viviendaEntity.getDireccion().isEmpty()) {
            throw new WebApplicationException("La vivienda debe tener una dirección");
        }

        String ciudad = viviendaEntity.getCiudad();
        String direccion = viviendaEntity.getDireccion();

        ViviendaEntity direccionRepetida = persistence.buscarPorDireccion(ciudad, direccion);

        if (direccionRepetida == null) {
            viviendaEntity = persistence.create(viviendaEntity);
            return (viviendaEntity);
        } else {
            throw new BusinessLogicException("Ya existe una vivienda en la misma dirección");
        }
    }

    public ViviendaEntity getVivienda(Long id) {
        return persistence.find(id);
    }

    public List<ViviendaEntity> getViviendas() {
        return persistence.findAll();
    }

    public ViviendaEntity updateVivienda(Long id, ViviendaEntity viviendaEntity) throws BusinessLogicException {
        if (persistence.find(id) == null) {
            throw new BusinessLogicException("La vivienda con el id dado no existe");
        }
        return persistence.update(viviendaEntity);
    }

    public void deleteVivienda(Long viviendaId) throws BusinessLogicException {
        if (persistence.find(viviendaId) == null) {
            throw new BusinessLogicException("La vivienda no existe");
        }
        persistence.delete(viviendaId);
    }

    public void generarDatos() {
        List<ViviendaEntity> viviendasViejas = getViviendas();
        for (ViviendaEntity vi : viviendasViejas) {
            try {
                deleteVivienda(vi.getId());
            } catch (BusinessLogicException e) {
                LOGGER.log(Level.INFO, "Error en el proceso de borrar la vivienda con id = {0}", vi.getId());
            }
        }

        String[] ciudades = new String[]{
                "Bogotá", "Cali", "Medellín", "Barranquilla"
        };
        Random rand = new Random();
        for (int i = 0; i < 10; i++) {
            ViviendaEntity v = new ViviendaEntity();
            v.setNombre("Vivienda " + (i + 1));
            v.setCiudad(ciudades[rand.nextInt(ciudades.length)]);
            v.setDireccion(String.format("Calle %d #%d-%d apto %d0%d", rand.nextInt(100), rand.nextInt(100), rand.nextInt(80), rand.nextInt(10), rand.nextInt(5)));
            v.setImgUrl("assets/img/vivienda" + (i + 1) + ".jpg");
            v.setDescripcion("Una casa (del latín casa, choza) es una edificación destinada para ser habitada.");
            v.setTipo("B");

            List<String> serviciosIncluidos = new ArrayList<>(5);
            serviciosIncluidos.add("Internet");
            serviciosIncluidos.add("Agua");
            serviciosIncluidos.add("Gas");
            serviciosIncluidos.add("Electricidad");
            v.setServiciosIncluidos(serviciosIncluidos);
            try {
                createVivienda(v);
            } catch (Exception e) {
                LOGGER.log(Level.INFO, "Error en el proceso de crear la vivienda");
            }
        }

    }

}
