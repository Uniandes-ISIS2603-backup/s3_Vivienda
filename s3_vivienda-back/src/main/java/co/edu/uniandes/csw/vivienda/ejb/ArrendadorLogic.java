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
import java.util.List;
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
    
        /**
     *
     * Obtener una arrendador por medio de su id.
     *
     * @param arrendadorId: id del arrendador para ser buscada.
     * @return el arrendador solicitada por medio de su id.
     */
    public ArrendadorEntity getArrendador(Long arrendadorId) 
    {
        ArrendadorEntity arrendadorEntity = arrendadorPersistence.find(arrendadorId);
        if (arrendadorEntity == null) {
            LOGGER.log(Level.SEVERE, "El arrendador con el id = {0} no existe", arrendadorId);
        }
        return arrendadorEntity;
    }
    
    /**
     * Guardar un nuevo Arrendador 
     *
     * @param ArrendadorEntity La entidad de tipo arrendador del nuevo arrendador a persistir.
     * @return La entidad luego de persistirla
     * @throws BusinessLogicException Si el login del usuario ya esta en uso por otro usuario
     */
    public ArrendadorEntity createArrendador(ArrendadorEntity arrendadorEntity) throws BusinessLogicException
    {
        String login = arrendadorEntity.getLogin();

        ArrendadorEntity arrendadorExiste = arrendadorPersistence.findByLogin(login);
 
        if (arrendadorExiste == null){
            arrendadorEntity = arrendadorPersistence.create(arrendadorEntity);
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
        ArrendadorEntity newArrendador = null;
        if(getArrendador(arrendadorId)!=null)
        {
            newArrendador = arrendadorPersistence.update(arrendadorEntity);
        }
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
        List<ViviendaEntity> viviendas = getArrendador(arrendadorId).getViviendas();
        if (viviendas != null && !viviendas.isEmpty()) {
            throw new BusinessLogicException("No se puede borrar el arrendador con id = " + arrendadorId + " porque tiene viviendas asociadas");
        }
        else if(getArrendador(arrendadorId)==null)
        {
            throw new BusinessLogicException("El recurso /arrendadores/" + arrendadorId +" no existe");
        }
        arrendadorPersistence.delete(arrendadorId);

    }
    
        /**
     *
     * Obtener todos los arrendadores existentes en la base de datos.
     *
     * @return una lista de arrendadores.
     */
    public List<ArrendadorEntity> getArrendadores()
    {
        List<ArrendadorEntity> arrendadores = arrendadorPersistence.findAll();
        return arrendadores;
    }
}