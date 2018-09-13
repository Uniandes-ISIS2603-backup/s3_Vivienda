/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.vivienda.ejb;

import co.edu.uniandes.csw.vivienda.entities.UniversidadEntity;
import co.edu.uniandes.csw.vivienda.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.vivienda.persistence.UniversidadPersistence;
import java.util.List;
import java.util.logging.*;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Paula Molina
 */
@Stateless
public class UniversidadLogic 
{
     private static final Logger LOGGER = Logger.getLogger(UniversidadLogic.class.getName());

    @Inject
    private UniversidadPersistence persistence; // Variable para acceder a la persistencia de la aplicación. Es una inyección de dependencias.

    public UniversidadEntity createUniversidad(UniversidadEntity universidadEntity) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de creación de la universdiad");
        // Verifica la regla de negocio que dice que no puede haber dos editoriales con el mismo nombre
        if (persistence.findByName(universidadEntity.getNombre()) != null) {
            throw new BusinessLogicException("Ya existe una Universdiad con el nombre \"" + universidadEntity.getNombre() + "\"");
        }
        // Invoca la persistencia para crear la editorial
        persistence.create(universidadEntity);
        LOGGER.log(Level.INFO, "Termina proceso de creación de la universidad");
        return universidadEntity;
    }

    public List<UniversidadEntity> getUniversidades() {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todas las universidades");
        List<UniversidadEntity> universidades = persistence.findAll();
        LOGGER.log(Level.INFO, "Termina proceso de consultar todas las universidades");
        return universidades;
    }

    /**
     * Obtener una editorial por medio de su id.
     *
     * @param universidadId: id de la universidad para ser buscada.
     * @return la universidad solicitada por medio de su id.
     */
    public UniversidadEntity getUniversidad(Long universidadId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar la universidad con id = {0}", universidadId);
        UniversidadEntity universidadEntity = persistence.find(universidadId);
        if (universidadEntity == null) {
            LOGGER.log(Level.SEVERE, "La universidad con el id = {0} no existe", universidadId);
        }
        LOGGER.log(Level.INFO, "Termina proceso de consultar la universidad con id = {0}", universidadId);
        return universidadEntity;
    }

    /**
     * Actualizar una universidad.
     *
     * @param universidadId: id de la universidad para buscarla en la base de
     * datos.
     * @param universidadEntity: universidad con los cambios para ser actualizada,
     * por ejemplo el nombre.
     * @return la universidad con los cambios actualizados en la base de datos.
     */
    public UniversidadEntity updateUniversidad(Long universidadId, UniversidadEntity universidadEntity) {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar la universidad con id = {0}", universidadId);
        UniversidadEntity newEntity = persistence.update(universidadEntity);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar la universidad con id = {0}", universidadEntity.getId());
        return newEntity;
    }

    /**
     * Borrar un universidad
     *
     * @param universidadId: id de la editorial a borrar
     */
    public void deleteUniversidad(Long universidadId) {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar la universidad con id = {0}", universidadId);
        persistence.delete(universidadId);
        LOGGER.log(Level.INFO, "Termina proceso de borrar la universidad con id = {0}", universidadId);
    }
}

