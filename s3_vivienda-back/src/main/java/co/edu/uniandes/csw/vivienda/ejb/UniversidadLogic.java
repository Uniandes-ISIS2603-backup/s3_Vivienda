/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.vivienda.ejb;

import co.edu.uniandes.csw.vivienda.entities.EstudianteEntity;
import co.edu.uniandes.csw.vivienda.entities.UniversidadEntity;
import co.edu.uniandes.csw.vivienda.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.vivienda.persistence.UniversidadPersistence;
import co.edu.uniandes.csw.vivienda.persistence.EstudiantePersistence;
import java.util.List;
import java.util.Random;
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
    private UniversidadPersistence universidadPersistence; // Variable para acceder a la persistencia de la aplicación. Es una inyección de dependencias.

    @Inject
    private EstudiantePersistence estudiantePersistence;
    
    @Inject
    private EstudianteLogic estudianteLogic;
    
    public UniversidadEntity createUniversidad(UniversidadEntity universidadEntity) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de creación de la universdiad");
        // Verifica la regla de negocio que dice que no puede haber dos editoriales con el mismo nombre
        if (universidadPersistence.findByName(universidadEntity.getNombre()) != null) {
            throw new BusinessLogicException("Ya existe una Universdiad con el nombre \"" + universidadEntity.getNombre() + "\"");
        }
        // Invoca la persistencia para crear la editorial
        universidadPersistence.create(universidadEntity);
        LOGGER.log(Level.INFO, "Termina proceso de creación de la universidad");
        return universidadEntity;
    }

    public List<UniversidadEntity> getUniversidades() {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todas las universidades");
        List<UniversidadEntity> universidades = universidadPersistence.findAll();
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
        UniversidadEntity universidadEntity = universidadPersistence.find(universidadId);
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
        UniversidadEntity newEntity = universidadPersistence.update(universidadEntity);
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
        universidadPersistence.delete(universidadId);
        LOGGER.log(Level.INFO, "Termina proceso de borrar la universidad con id = {0}", universidadId);
    }
    
     public List<UniversidadEntity> generarDatos()
    {       
        List<UniversidadEntity> universidades = getUniversidades();
        for(UniversidadEntity universidad: universidades)
        {
            universidadPersistence.delete(universidad.getId());
        }
        
        List<EstudianteEntity> estudiantes = estudiantePersistence.findAll();
        
        Random rand = new Random();
        String[] nombresUniversidades = new String[]{"Universidad de Los Andes", "Universidad Javeriana", "Universidad Nacional", "Universidad del Rosario", "Universidad Externado", "Universidad del Bosque", "Universidad de La Sabana", "CESA"};
        for (int i = 0; i < 8; i++) {
            UniversidadEntity universidad = new UniversidadEntity();
            String nombreUniversidad = nombresUniversidades[i];
            universidad.setNombre(nombreUniversidad);
            universidad.setLongitud(rand.nextFloat());
            universidad.setLatitud(rand.nextFloat());
            universidad.setImgUrl("assets/img/universidad" + (i + 1) + ".png");
            universidad.setEstudiantes(estudiantes);
            
            try {
                UniversidadEntity universidad2 = createUniversidad(universidad);
                if(i<estudiantes.size()){
                EstudianteEntity estudiante = estudiantes.get(i);
                estudiante.setUniversidad(universidad2);                
                estudianteLogic.updateEstudiante(estudiante.getId(), estudiante);
                }
                universidades.add(universidad);
            } catch (BusinessLogicException ex) {
                Logger.getLogger(ArrendadorLogic.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return universidades;
    }
}

