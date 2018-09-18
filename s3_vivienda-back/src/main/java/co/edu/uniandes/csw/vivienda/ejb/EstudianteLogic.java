/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.vivienda.ejb;

import co.edu.uniandes.csw.vivienda.entities.ContratoEntity;
import co.edu.uniandes.csw.vivienda.entities.EstudianteEntity;
import co.edu.uniandes.csw.vivienda.entities.UniversidadEntity;
import co.edu.uniandes.csw.vivienda.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.vivienda.persistence.EstudiantePersistence;
import co.edu.uniandes.csw.vivienda.persistence.UniversidadPersistence;
import co.edu.uniandes.csw.vivienda.persistence.ContratoPersistence;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class EstudianteLogic {
    private static final Logger LOGGER = Logger.getLogger(EstudianteLogic.class.getName());

    @Inject
    private EstudiantePersistence persistence; // Variable para acceder a la persistencia de la aplicación. Es una inyección de dependencias.

    @Inject
    private UniversidadPersistence persistenceUniversidad; // Variable para acceder a la persistencia de la aplicación. Es una inyección de dependencias.
    
    @Inject
    private ContratoPersistence persistenceContrato; // Variable para acceder a la persistencia de la aplicación. Es una inyección de dependencias.
    
    /**
     * Crea un estudiante en la persistencia.
     *
     * @param estudianteEntity La entidad que representa el estudiante apersistir.
     * @return La entidad del estudiante luego de persistirlo.
     * @throws BusinessLogicException Si el login del estudiante a persistir ya existe.
     * Si el estudinate no tiene universidad.
     * Si la universidad del estudiante a persistir no existe.
     */
    public EstudianteEntity createEstudiante(EstudianteEntity estudianteEntity) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de creación del estudiante");
        
        if (persistence.findByLogin(estudianteEntity.getLogin()) != null) {
            throw new BusinessLogicException("Ya existe un estudiante con el login \"" + estudianteEntity.getLogin() + "\"");
        }
        if (estudianteEntity.getUniversidad() == null) {
            throw new BusinessLogicException("El estudinate debe tener universidad");
        }
        else if (persistenceUniversidad.find(estudianteEntity.getUniversidad().getId()) == null){
            throw new BusinessLogicException("No existe la universidad con el id \"" +estudianteEntity.getUniversidad().getId() + "\"");
        }
        // Invoca la persistencia para crear el estudiante
        estudianteEntity = persistence.create(estudianteEntity);
        LOGGER.log(Level.INFO, "Termina proceso de creación del estudiante");
        return persistence.find(estudianteEntity.getId());
    }
    
    /**
     * Obtiene la lista de los registros de Estudiante.
     *
     * @return Colección de objetos de EstudianteEntity.
     */
    public List<EstudianteEntity> getEstudiantes(){
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todos los estudinates");
        List<EstudianteEntity> estudiantes = persistence.findAll();
        LOGGER.log(Level.INFO, "Termina proceso de consultar todos los estudiantes");
        return estudiantes;
    }
    
    /**
     * Obtiene los datos de una instancia de Estudiante a partir de su ID.
     *
     * @param id Identificador de la instancia a consultar
     * @return Instancia de EstudianteEntity con los datos del Estudiante consultado.
     */
    public EstudianteEntity getEstudiante(Long id){
        LOGGER.log(Level.INFO, "Inicia proceso de consultar el estudiante");
        EstudianteEntity estudiante = persistence.find(id);
        if (estudiante == null) {
            LOGGER.log(Level.SEVERE, "El estudiantes con el id = {0} no existe", id);
        }
        LOGGER.log(Level.INFO, "Termina proceso de consultar el estudiante");
        return estudiante;
    }
    
    /**
     * Actualiza la información de una instancia de Estudiante.
     *
     * @param id Identificador de la instancia a actualizar
     * @param entity Instancia de EstudianteEntity con los nuevos datos.
     * @return Instancia de EstudianteEntity con los datos actualizados.
     * @throws co.edu.uniandes.csw.vivienda.exceptions.BusinessLogicException si 
     * no se puede actualizar el estudiante.
     */
    public EstudianteEntity updateEstudiante(Long id, EstudianteEntity entity) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar el estudiante con id = {0}", id);
        if (entity.getLogin() == null)
            System.out.println("Es null");
        else
            System.out.println("No es null");
        
        EstudianteEntity prueba = persistence.findByLogin(entity.getLogin());
        if (prueba != null && !Objects.equals(prueba.getId(), id)) {
            throw new BusinessLogicException("Ya existe un estudiante con el login \"" + entity.getLogin() + "\"");
        }
        if (entity.getUniversidad() != null && persistenceUniversidad.find(entity.getUniversidad().getId()) == null) {
            throw new BusinessLogicException("No existe la universidad con el id \"" +entity.getUniversidad().getId() + "\"");
        }
        if (entity.getContrato() != null && (persistenceContrato.find(entity.getContrato().getId()) == null)){
            throw new BusinessLogicException("No existe el contrato con el id \"" +entity.getContrato().getId() + "\"");
        }
        if (entity.getContrato() != null &&(entity.getContrato().getEstudiante() == null || !Objects.equals(entity.getContrato().getEstudiante().getId(), id))){
            throw new BusinessLogicException("El contrato debe tener el mismo estudiante");
        }
        entity.setId(id);
        EstudianteEntity newEntity = persistence.update(entity);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar el estudiante con id = {0}", entity.getId());
        return persistence.find(id);
    }

    /**
     * Borrar un estudiante
     *
     * @param estudianteId: id del estudiante a borrar
     */
    public void deleteEstudiante(Long estudianteId) {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar el estudiante con id = {0}", estudianteId);
        persistence.delete(estudianteId);
        LOGGER.log(Level.INFO, "Termina proceso de borrar el estudiante con id = {0}", estudianteId);
    }
    
    /**
     * Reemplazar la universidad de un estudiante.
     *
     * @param estudianteId id del estudiante que se quiere actualizar.
     * @param universidadId El id de la universidad que se será del estudiante.
     * @return el nuevo estudianteId.
     */
    public EstudianteEntity replaceUniversidad(Long estudianteId, Long universidadId){
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar estudinate con id = {0}", estudianteId);
        UniversidadEntity universidadEntity = persistenceUniversidad.find(universidadId);
        EstudianteEntity estudianteEntity = persistence.find(estudianteId);
        estudianteEntity.setUniversidad(universidadEntity);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar estudiante con id = {0}", estudianteEntity.getId());
        return persistence.find(estudianteId);
    }

    /**
     * Borrar el contrato de un premio
     *
     * @param estudianteId El premio que se desea borrar del contrato.
     * @throws BusinessLogicException si el premio no tiene contrato
     */
    public void deleteContrato(Long estudianteId) throws BusinessLogicException {
       LOGGER.log(Level.INFO, "Inicia proceso de borrar el contrato del estudinate con id = {0}", estudianteId);
        EstudianteEntity estudianteEntity = persistence.find(estudianteId);
        if (estudianteEntity.getContrato() == null) {
            throw new BusinessLogicException("El estudiante no tiene contrato");
        }
        ContratoEntity contratoEntity = persistenceContrato.find(estudianteEntity.getContrato().getId());
        persistenceContrato.delete(contratoEntity.getId());
        estudianteEntity.setContrato(null);
        
        LOGGER.log(Level.INFO, "Termina proceso de borrar el contrato con id = {0} del estudiante con id = " + estudianteId, contratoEntity.getId());
    }

    /**
     * Agregar un contrato a un estudiante
     *
     * @param estudianteId El id estudiante a guardar
     * @param contratoId El id del contrato el cual se le va a guardar al estudiante.
     * @return El contrato que fue agregado al estudiante.
     * @throws co.edu.uniandes.csw.vivienda.exceptions.BusinessLogicException
     */
    public ContratoEntity addContrato(Long contratoId, Long estudianteId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de asociar el contrato con id = {0} al estudiante con id = " + estudianteId, contratoId);
        ContratoEntity contratoEntity = persistenceContrato.find(contratoId);
        EstudianteEntity estudianteEntity = persistence.find(estudianteId);
        estudianteEntity.setContrato(contratoEntity);
        updateEstudiante(estudianteId, estudianteEntity);
        LOGGER.log(Level.INFO, "Termina proceso de asociar el contrato con id = {0} al estudiante con id = " + estudianteId, contratoId);
        return persistenceContrato.find(contratoId);
    }
}
