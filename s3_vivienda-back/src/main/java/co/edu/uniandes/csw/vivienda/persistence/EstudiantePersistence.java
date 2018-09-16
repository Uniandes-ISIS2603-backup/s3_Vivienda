/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.vivienda.persistence;

import co.edu.uniandes.csw.vivienda.entities.EstudianteEntity;
import java.util.List;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @estudiante Juan Manuel Castillo
 */
@Stateless
public class EstudiantePersistence {
    private final static Logger LOGGER = Logger.getLogger(EstudiantePersistence.class.getName());
    
    @PersistenceContext(unitName = "UniviviendaPU")
    protected EntityManager em;
    
    /**
     * Crea un autor en la base de datos
     *
     * @param estudianteEntity objeto estudiante que se creará en la base de datos
     * @return devuelve la entidad creada con un id dado por la base de datos.
     */
    public EstudianteEntity create(EstudianteEntity estudianteEntity){
        em.persist(estudianteEntity);
        return estudianteEntity;
    }
    
    /**
     * Devuelve todos los estudiantes de la base de datos.
     *
     * @return una lista con todos los estudiantes que encuentre en la base de
     * datos, "select u from EstudianteEntity u" es como un "select * from
     * EstudianteEntity;" - "SELECT * FROM table_name" en SQL.
     */
    public List<EstudianteEntity> findAll(){
        TypedQuery query = em.createQuery("select u from EstudianteEntity u", EstudianteEntity.class);
        return query.getResultList();
    }
    
    /**
     * Busca si hay algún estudiante con el id que se envía de argumento
     *
     * @param estudianteId: id correspondiente a la estudiante buscada.
     * @return un estudiante.
     */
    public EstudianteEntity find(Long estudianteId){
        return em.find(EstudianteEntity.class, estudianteId);
    }
    
    /**
     * Busca si hay algún estudiante con el id que se envía de argumento
     *
     * @param nombre: nombre correspondiente al estudiante buscado.
     * @return lista de estudiantes con el nombre dado por parámetro.
     */
    public List<EstudianteEntity> findByNombre(String nombre){
        TypedQuery query = em.createQuery("select u from EstudianteEntity u where u.nombre = :nombre", EstudianteEntity.class);
        query.setParameter("nombre", nombre);
        
        List <EstudianteEntity> resultList = query.getResultList();
        if (resultList.isEmpty())
            return null;
        return resultList;
    }
    
    /**
     * Busca si hay algún estudiante con el login que se envía de argumento
     *
     * @param login: login correspondiente al estudiante buscado.
     * @return un estudiante.
     */
    public EstudianteEntity findByLogin(String login){
        TypedQuery query = em.createQuery("select u from EstudianteEntity u where u.login = :login", EstudianteEntity.class);
        query.setParameter("login", login);
        
        List <EstudianteEntity> resultList = query.getResultList();
        EstudianteEntity result;
        if (resultList == null || resultList.isEmpty())
            result = null;
        else
            result = resultList.get(0);
        return result;
    }
    
    /**
     * Actualiza un estudiante.
     *
     * @param estudianteEntity: la estudiante que viene con los nuevos cambios. Por
     * ejemplo el nombre pudo cambiar. En ese caso, se haria uso del método
     * update.
     * @return un estudiante con los cambios aplicados.
     */
    public EstudianteEntity update(EstudianteEntity estudianteEntity){
        return em.merge(estudianteEntity);
    }
    
    /**
     * Borra un estudiante de la base de datos recibiendo como argumento el id de
     * la estudiante
     *
     * @param estudianteId: id correspondiente a la estudiante a borrar.
     */
    public void delete(Long estudianteId){
        EstudianteEntity estudianteEntity = em.find(EstudianteEntity.class, estudianteId);
        em.remove(estudianteEntity);
    }
}
