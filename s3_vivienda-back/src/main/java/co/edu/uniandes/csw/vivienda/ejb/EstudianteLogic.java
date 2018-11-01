/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.vivienda.ejb;

import co.edu.uniandes.csw.vivienda.entities.EstudianteEntity;
import co.edu.uniandes.csw.vivienda.entities.UniversidadEntity;
import co.edu.uniandes.csw.vivienda.entities.CalificacionEntity;
import co.edu.uniandes.csw.vivienda.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.vivienda.persistence.EstudiantePersistence;
import co.edu.uniandes.csw.vivienda.persistence.UniversidadPersistence;
import co.edu.uniandes.csw.vivienda.persistence.CalificacionPersistence;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import java.util.Random;
import javax.inject.Inject;

/**
 *
 * @author Juan Manuel Castillo
 */
@Stateless
public class EstudianteLogic {
    private static final Logger LOGGER = Logger.getLogger(EstudianteLogic.class.getName());

    @Inject
    private EstudiantePersistence persistence; // Variable para acceder a la persistencia de la aplicación. Es una inyección de dependencias.

    @Inject
    private UniversidadPersistence persistenceUniversidad; // Variable para acceder a la persistencia de la aplicación. Es una inyección de dependencias.
    
    @Inject
    private CalificacionPersistence persistenceCalificacion;
    
    public void generarDatos(){
        persistenceCalificacion.deleteAll();

        List<EstudianteEntity> estudiantesViejos = persistence.findAll();
        for (EstudianteEntity estudiante: estudiantesViejos){
            persistence.delete(estudiante.getId());
        }
        estudiantesViejos = persistence.findAll();
        List<UniversidadEntity> universidadesViejas = persistenceUniversidad.findAll();
        for (UniversidadEntity universidad: universidadesViejas){
            persistenceUniversidad.delete(universidad.getId());
        }
        universidadesViejas =  persistenceUniversidad.findAll();
        
        String [] nombres = new String[]{"Juan David", "Carlos Andrés", "Julián Felipe", "Oscar", "Carolina", "Daniela", "Jimena Sofía"};
        String [] apellidos = new String[]{"Cardoso", "Meneses", "Rojas", "García", "Gómez", "Vargas", "Quintero", "González"};
        String [] universidadesString = new String[]{"Universidad de Los Andes", "Universidad Javeriana", "Universidad de La Sabana"};
        
        Random rand = new Random();
        
        for (int i = 0; i < universidadesString.length; i ++){
            UniversidadEntity universidad = new UniversidadEntity();
            universidad.setLatitud(rand.nextFloat()*40);
            universidad.setLongitud(rand.nextFloat()*40);
            universidad.setNombre(universidadesString[i]);
            persistenceUniversidad.create(universidad);
        }
        
        List<UniversidadEntity> universidades = persistenceUniversidad.findAll();
        for (int i = 0; i < 10; i ++){
            EstudianteEntity estudiante = new EstudianteEntity();
            String nombre = nombres[rand.nextInt(nombres.length)];
            String apellido = apellidos[rand.nextInt(apellidos.length)];
            estudiante.setNombre(nombre + " " + apellido);
            estudiante.setLogin(nombre.substring(0, 3) + apellido.substring(0, 3) + (i+1));
            estudiante.setPassword("password" + (i+1));
            estudiante.setUniversidad(universidades.get(rand.nextInt(universidades.size())));
            persistence.create(estudiante);
        }
    }
    
    
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
        
        if (estudianteEntity.getLogin() == null){
            throw new BusinessLogicException("El estudiante debe tener login");
        }
        if (estudianteEntity.getNombre() == null){
            throw new BusinessLogicException("El estudiante debe tener nombre");
        }
        if (estudianteEntity.getPassword() == null){
            throw new BusinessLogicException("El estudiante debe tener password");
        }
        if (persistence.findByLogin(estudianteEntity.getLogin()) != null) {
            throw new BusinessLogicException("Ya existe un estudiante con el login \"" + estudianteEntity.getLogin() + "\"");
        }
        if (estudianteEntity.getUniversidad() == null) {
            throw new BusinessLogicException("El estudiante debe tener universidad");
        }
        else if (estudianteEntity.getUniversidad().getId() == null){
            throw new BusinessLogicException("La universidad debe tener id");
        }
        else if (persistenceUniversidad.find(estudianteEntity.getUniversidad().getId()) == null){
            throw new BusinessLogicException("No existe la universidad con el id \"" +estudianteEntity.getUniversidad().getId() + "\"");
        }
        
        persistence.create(estudianteEntity);
        LOGGER.log(Level.INFO, "Termina proceso de creación del estudiante");
        return estudianteEntity;
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
        
        EstudianteEntity prueba = persistence.findByLogin(entity.getLogin());
        if (prueba != null && !Objects.equals(prueba.getId(), id)) {
            throw new BusinessLogicException("Ya existe un estudiante con el login \"" + entity.getLogin() + "\"");
        }
        if (entity.getUniversidad() != null &&(entity.getUniversidad().getId()== null || persistenceUniversidad.find(entity.getUniversidad().getId()) == null)){
            throw new BusinessLogicException("Universidad invalida");
        }
        EstudianteEntity old = persistence.find(id);
        if (entity.getLogin() != null)
            old.setLogin(entity.getLogin());
        if (entity.getNombre()!= null)
            old.setNombre(entity.getNombre());
        if (entity.getPassword()!= null)
            old.setPassword(entity.getPassword());
        if (entity.getUniversidad()!= null)
            old.setUniversidad(entity.getUniversidad());
        
        persistence.update(old);
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
     * @throws co.edu.uniandes.csw.vivienda.exceptions.BusinessLogicException
     */
    public EstudianteEntity replaceUniversidad(Long estudianteId, Long universidadId) throws BusinessLogicException{
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar estudinate con id = {0}", estudianteId);
        UniversidadEntity universidadEntity = persistenceUniversidad.find(universidadId);
        if (universidadEntity == null){
            throw new BusinessLogicException("Universidad invalida");
        }
        EstudianteEntity estudianteEntity = persistence.find(estudianteId);
        estudianteEntity.setUniversidad(universidadEntity);
        persistence.update(estudianteEntity);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar estudiante con id = {0}", estudianteEntity.getId());
        return persistence.find(estudianteId);
    }

}
