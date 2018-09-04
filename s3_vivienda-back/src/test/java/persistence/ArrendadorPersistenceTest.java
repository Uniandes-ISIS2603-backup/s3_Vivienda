/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistence;

import co.edu.uniandes.csw.vivienda.entities.ArrendadorEntity;
import co.edu.uniandes.csw.vivienda.persistence.ArrendadorPersistence;
import java.util.ArrayList;
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
 * @author msalcedo
 */
@RunWith(Arquillian.class)
public class ArrendadorPersistenceTest {
    
    @Inject 
    private ArrendadorPersistence arrendadorPersistence;
    
    @PersistenceContext
    private EntityManager em;
    
    @Inject
    UserTransaction utx;
    
    private List<ArrendadorEntity> data = new ArrayList<>();
    
        /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(ArrendadorEntity.class.getPackage())
                .addPackage(ArrendadorPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }
    
        /**
     * Configuración inicial de la prueba.
     */
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
    
    /**
     * Limpia las tablas que están implicadas en la prueba.
     */
    private void clearData() {
        em.createQuery("delete from ArrendadorEntity").executeUpdate();
    }
    
       /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        PodamFactory factory = new PodamFactoryImpl();
        for (int i = 0; i < 3; i++) {
            ArrendadorEntity entity = factory.manufacturePojo(ArrendadorEntity.class);

            em.persist(entity);
            data.add(entity);
        }
    }
    
        /**
     * Prueba para crear un Arrendador.
     */
    @Test
    public void createArrendadorTest() {
        PodamFactory factory = new PodamFactoryImpl();
        ArrendadorEntity newEntity = factory.manufacturePojo(ArrendadorEntity.class);
        ArrendadorEntity result = arrendadorPersistence.create(newEntity);

        Assert.assertNotNull(result);

        ArrendadorEntity entity = em.find(ArrendadorEntity.class, result.getId());

        Assert.assertEquals(newEntity.getNombre(), entity.getNombre());
    }
    
     /**
     * Prueba para consultar la lista de Arrendadores.
     */
    @Test
    public void getArrendadoresTest() {
        List<ArrendadorEntity> list = arrendadorPersistence.findAll();
        Assert.assertEquals(data.size(), list.size());
        for (ArrendadorEntity ent : list) {
            boolean found = false;
            for (ArrendadorEntity entity : data) {
                if (ent.getId().equals(entity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }
    
    /**
     * Prueba para consultar un Arrendador.
     */
    @Test
    public void getArrendadorTest() {
        ArrendadorEntity entity = data.get(0);
        ArrendadorEntity newEntity = arrendadorPersistence.find(entity.getId());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(entity.getNombre(), newEntity.getNombre());
        Assert.assertEquals(entity.getLogin(), newEntity.getLogin());
    }

    /**
     * Prueba para actualizar un Arrendador.
     */
    @Test
    public void updateAuthorTest() {
        ArrendadorEntity entity = data.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        ArrendadorEntity newEntity = factory.manufacturePojo(ArrendadorEntity.class);

        newEntity.setId(entity.getId());

        arrendadorPersistence.update(newEntity);

        ArrendadorEntity resp = em.find(ArrendadorEntity.class, entity.getId());

        Assert.assertEquals(newEntity.getNombre(), resp.getNombre());
    }

    /**
     * Prueba para eliminar un Arrendador.
     */
    @Test
    public void deleteAuthorTest() {
        ArrendadorEntity entity = data.get(0);
        arrendadorPersistence.delete(entity.getId());
        ArrendadorEntity deleted = em.find(ArrendadorEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }
}
