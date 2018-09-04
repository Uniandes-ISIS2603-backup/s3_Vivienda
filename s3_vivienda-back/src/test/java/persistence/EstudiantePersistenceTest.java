/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistence;

import co.edu.uniandes.csw.vivienda.entities.EstudianteEntity;
import co.edu.uniandes.csw.vivienda.persistence.EstudiantePersistence;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

/**
 *
 * @author estudiante
 */
@RunWith(Arquillian.class)
public class EstudiantePersistenceTest {
    
    @Inject
    private EstudiantePersistence estudiantePersistence;

    @PersistenceContext
    private EntityManager em;

    @Inject
    UserTransaction utx;
    
    List <EstudianteEntity> data = new ArrayList();
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(EstudianteEntity.class.getPackage())
                .addPackage(EstudiantePersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }
    
    @Before
    public void configTest() {
        try {
            utx.begin();
            em.joinTransaction();
            clearData();
            insertData();
            utx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                utx.rollback();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }
    
    private void clearData() {
        em.createQuery("delete from EstudianteEntity").executeUpdate();
    }
    
    private void insertData() {
        PodamFactory factory = new PodamFactoryImpl();
        for (int i = 0; i < 3; i++) {
            EstudianteEntity entity = factory.manufacturePojo(EstudianteEntity.class);

            em.persist(entity);
            data.add(entity);
        }
    }
    public void esIgual(EstudianteEntity entity, EstudianteEntity newEntity){
        Assert.assertEquals(newEntity.getLogin(), entity.getLogin());
        Assert.assertEquals(newEntity.getNombre(), entity.getNombre());
        Assert.assertEquals(newEntity.getPassword(), entity.getPassword());
        Assert.assertEquals(newEntity.getUniversidad(), entity.getUniversidad());
        Assert.assertEquals(newEntity.getCalificaciones(), entity.getCalificaciones());
        Assert.assertEquals(newEntity.getContrato(), entity.getContrato());
    }
    @Test
    public void createEstudianteTest() {
        PodamFactory factory = new PodamFactoryImpl();
        EstudianteEntity newEntity = factory.manufacturePojo(EstudianteEntity.class);
        EstudianteEntity result = estudiantePersistence.create(newEntity);

        Assert.assertNotNull(result);
        EstudianteEntity entity = em.find(EstudianteEntity.class, result.getId());
        esIgual(entity, newEntity);
    }
    
    @Test
    public void getEstudiantesTest() {
        Collection <EstudianteEntity> list =  estudiantePersistence.findAll();
        Assert.assertEquals(data.size(), list.size());
        for (EstudianteEntity ent : list) {
            boolean found = false;
            for (EstudianteEntity entity : data) {
                if (ent.getId().equals(entity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }
    
    @Test
    public void getEstudianteTest(){
        EstudianteEntity entity = data.get(0);
        EstudianteEntity newEntity = estudiantePersistence.find(entity.getId());
        Assert.assertNotNull(newEntity); 
        esIgual(entity, newEntity);
    }
    
    @Test
    public void deleteEstudianteTest(){
        EstudianteEntity entity = data.get(0);
        estudiantePersistence.delete(entity.getId());
        EstudianteEntity deleted = em.find(EstudianteEntity.class, entity.getId());
        Assert.assertNull(deleted); 
    }
    
    @Test
    public void findEstudianteByLoginTest(){
        EstudianteEntity entity = data.get(0);
        EstudianteEntity newEntity = estudiantePersistence.findByLogin(entity.getLogin());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(entity.getLogin(), newEntity.getLogin());
        
        newEntity = estudiantePersistence.findByLogin(null);   
        Assert.assertNull(newEntity);
    }
    
    @Test
    public void findEstudianteByNombreTest(){
        EstudianteEntity entity = data.get(0);
        Collection <EstudianteEntity> listEntity = estudiantePersistence.findByNombre(entity.getNombre());
        Assert.assertNotNull(listEntity);
        for (EstudianteEntity ent : listEntity)
            Assert.assertEquals(entity.getNombre(), ent.getNombre());
        
        listEntity = estudiantePersistence.findByNombre(null);   
        Assert.assertNull(listEntity);
    }
}
