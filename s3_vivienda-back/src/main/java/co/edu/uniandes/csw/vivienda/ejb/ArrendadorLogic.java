/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.vivienda.ejb;

import co.edu.uniandes.csw.vivienda.entities.ArrendadorEntity;
import co.edu.uniandes.csw.vivienda.entities.ViviendaEntity;
import co.edu.uniandes.csw.vivienda.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.vivienda.persistence.ArrendadorPersistence;
import co.edu.uniandes.csw.vivienda.persistence.ViviendaPersistence;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author msalcedo
 */
@Stateless
public class ArrendadorLogic {
    
    private static final Logger LOGGER = Logger.getLogger(ArrendadorLogic.class.getName());
    @Inject
    private ArrendadorPersistence arrendadorPersistence;
    
    @Inject
    private ViviendaPersistence viviendaPersistence;
    
    @Inject
    private ViviendaLogic viviendaLogic;
    
        /**
     *
     * Obtener una arrendador por medio de su id.
     *
     * @param arrendadorId: id del arrendador para ser buscada.
     * @return el arrendador solicitada por medio de su id.
     */
    public ArrendadorEntity getArrendador(Long arrendadorId) 
    {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar el arrendador con id = {0}", arrendadorId);
        ArrendadorEntity arrendadorEntity = arrendadorPersistence.find(arrendadorId);
        if (arrendadorEntity == null) {
            LOGGER.log(Level.SEVERE, "El arrendador con el id = {0} no existe", arrendadorId);
        }
        LOGGER.log(Level.INFO, "Termina proceso de consultar el arrendador con id = {0}", arrendadorId);
        return arrendadorEntity;
    }
    
    /**
     * Guardar un nuevo Arrendador 
     *
     * @param arrendadorEntity La entidad de tipo arrendador del nuevo arrendador a persistir.
     * @return La entidad luego de persistirla
     * @throws BusinessLogicException Si el login del usuario ya esta en uso por otro usuario
     */
    public ArrendadorEntity createArrendador(ArrendadorEntity arrendadorEntity) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "Inicia proceso de creación del arrendador");
        String login = arrendadorEntity.getLogin();
        String password = arrendadorEntity.getPassword();
        
        if(password==null || password.equals(" "))
        {
            throw new BusinessLogicException("La contraseña es invalida");
        }
        if(login==null || login.equals(" "))
        {
            throw new BusinessLogicException("El login es invalido");
        }

        ArrendadorEntity arrendadorExiste = arrendadorPersistence.findByLogin(login);
 
        if (arrendadorExiste == null){
            arrendadorEntity = arrendadorPersistence.create(arrendadorEntity);
            LOGGER.log(Level.INFO, "Termina proceso de creación del arrendador");
            return(arrendadorEntity);
        }
        else {
            throw new BusinessLogicException("Ya existe un arrendador con ese login");
        }
    }
    
        /**
     *
     * Actualizar un arrendador.
     *
     * @param arrendadorId: id del arrendador para buscarla en la base de
     * datos.
     * @param arrendadorEntity: arrendador con los cambios para ser actualizada,
     * por ejemplo el nombre.
     * @return el arrendador con los cambios actualizados en la base de datos.
     */
    public ArrendadorEntity updateArrendador(Long arrendadorId, ArrendadorEntity arrendadorEntity) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar el arrendador con id = {0}", arrendadorId);
        ArrendadorEntity newArrendador = null;
        if(getArrendador(arrendadorId)!=null)
        {
            newArrendador = arrendadorPersistence.update(arrendadorEntity);
        }
        LOGGER.log(Level.INFO, "Termina proceso de actualizar el arrendador con id = {0}", arrendadorEntity.getId());
        return newArrendador;
    }
    
        /**
     * Borrar un arrendador
     *
     * @param arrendadorId: id del arrendador a borrar
     * @throws BusinessLogicException Si el arrendador a eliminar tiene viviendas.
     */
    public void deleteArrendador(Long arrendadorId) throws BusinessLogicException 
    {
         LOGGER.log(Level.INFO, "Inicia proceso de borrar el arrendador con id = {0}", arrendadorId);
        List<ViviendaEntity> viviendas = getArrendador(arrendadorId).getViviendas();
        if (viviendas != null && !viviendas.isEmpty()) {
            throw new BusinessLogicException("No se puede borrar el arrendador con id = " + arrendadorId + " porque tiene viviendas asociadas");
        }
        else if(getArrendador(arrendadorId)==null)
        {
            throw new BusinessLogicException("El recurso /arrendadores/" + arrendadorId +" no existe");
        }
        arrendadorPersistence.delete(arrendadorId);
        LOGGER.log(Level.INFO, "Termina proceso de borrar el arrendador con id = {0}", arrendadorId);

    }
    
        /**
     *
     * Obtener todos los arrendadores existentes en la base de datos.
     *
     * @return una lista de arrendadores.
     */
    public List<ArrendadorEntity> getArrendadores()
    {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todos los arrendadores");
        List<ArrendadorEntity> arrendadores = arrendadorPersistence.findAll();
        LOGGER.log(Level.INFO, "Termina proceso de consultar todos los arrendadores" + arrendadores);
        return arrendadores;
    }
    
    public List<ArrendadorEntity> generarDatos()
    {
         List<ViviendaEntity> viviendasViejas = viviendaLogic.getViviendas();
        for (ViviendaEntity vi : viviendasViejas) {
            try {
                viviendaLogic.deleteVivienda(vi.getId());
            } catch (BusinessLogicException e) {
                e.printStackTrace();
            }
        }
        viviendaLogic.generarDatos();
        
        List<ArrendadorEntity> arrendadores = getArrendadores();
        for(ArrendadorEntity arrendador: arrendadores)
        {
            arrendadorPersistence.delete(arrendador.getId());
        }
        
        List<ViviendaEntity> viviendas = viviendaPersistence.findAll();
        
        Random rand = new Random();
        String[] nombres = new String[]{"Mateo", "Juan", "Pedro","Camilo", "Jesus"};
        for (int i = 0; i < 10; i++) {
            ArrendadorEntity arrendador = new ArrendadorEntity();
            String nombre = nombres[rand.nextInt(nombres.length)];
            arrendador.setNombre(nombre);
            arrendador.setLogin(nombre + (i+1));
            arrendador.setPassword("asdf" + i+1);
            arrendador.setViviendas(viviendas);
            try {
                ArrendadorEntity arrendador2 = createArrendador(arrendador);
                arrendadores.add(arrendador2);
            } catch (BusinessLogicException ex) {
                Logger.getLogger(ArrendadorLogic.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return arrendadores;
    }
}