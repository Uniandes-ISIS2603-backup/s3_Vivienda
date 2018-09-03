/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.vivienda.persistence;


import co.edu.uniandes.csw.vivienda.entities.UniversidadEntity;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author Paula Molina
 */
@Stateless
public class UniversidadPersistence 
{
    private final static Logger LOGGER = Logger.getLogger(UniversidadPersistence.class.getName());
    
    @PersistenceContext(unitName = "UniviviendaPU")
    protected EntityManager em;
    
    /**
     * Método para persisitir la entidad en la base de datos.
     *
     * @param universidadEntity objeto universidad que se creará en la base de datos
     * @return devuelve la entidad creada con un id dado por la base de datos.
     */
    public UniversidadEntity create(UniversidadEntity universidadEntity) {
        LOGGER.log(Level.INFO, "Creando una universidad nueva");

        em.persist(universidadEntity);
        LOGGER.log(Level.INFO, "Saliendo de crear una universidad nueva");
        return universidadEntity;
    }
    
    /**
     * Actualizar una universidad
     *
     * Actualiza la entidad que recibe en la base de datos
     *
     * @param universidadEntity La entidad actualizada que se desea guardar
     * @return La entidad resultante luego de la acutalización
     */
    public UniversidadEntity update(UniversidadEntity universidadEntity) {
        LOGGER.log(Level.INFO, "Actualizando universidad con id = {0}", universidadEntity.getId());
        return em.merge(universidadEntity);
    }
    
    /**
     * Buscar una reseña
     *
     * Busca si hay alguna reseña asociada a un libro y con un ID específico
     *
     * @param estudianteId El ID del estudiante con respecto al cual se busca
     * @param universidadeId El ID de la universidad buscada
     * @return La universidad encontrada o null. 
     */
    public UniversidadEntity findByEstudiante(Long estudianteId, Long universidadId){
        LOGGER.log(Level.INFO, "Consultando la universidad con id = {0} del estudiante con id = " + estudianteId, universidadId);
        TypedQuery<UniversidadEntity> q = em.createQuery("select p from UniversidadEntity p where (p.estudiante.id = :estudianteId) and (p.id = :universidadId)", UniversidadEntity.class);
        q.setParameter("estudianteId", estudianteId);
        q.setParameter("universidadId", universidadId);
        List<UniversidadEntity> results = q.getResultList();
        UniversidadEntity universidad = null;
        if (results == null) {
            universidad = null;
        } else if (results.isEmpty()) {
            universidad = null;
        } else if (results.size() >= 1) {
            universidad = results.get(0);
        }
        LOGGER.log(Level.INFO, "Saliendo de consultar la universidad con id = {0} del estudiante con id =" + estudianteId, universidadId);
        return universidad;
    }
          
}
