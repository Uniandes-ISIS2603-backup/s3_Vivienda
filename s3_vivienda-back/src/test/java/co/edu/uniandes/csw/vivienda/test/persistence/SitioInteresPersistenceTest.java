/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.vivienda.test.persistence;

import co.edu.uniandes.csw.vivienda.entities.SitioInteresEntity;
import co.edu.uniandes.csw.vivienda.persistence.SitioInteresPersistence;
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
public class SitioInteresPersistenceTest {
    
    @Inject 
    private SitioInteresPersistence sitioInteresPersistence;
    
    @PersistenceContext
    private EntityManager em;
    
    @Inject
    UserTransaction utx;
    
    private List<SitioInteresEntity> data = new ArrayList<>();
    
        /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(SitioInteresEntity.class.getPackage())
                .addPackage(SitioInteresPersistence.class.getPackage())
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
        em.createQuery("delete from SitioInteresEntity").executeUpdate();
    }
    
       /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        PodamFactory factory = new PodamFactoryImpl();
        for (int i = 0; i < 3; i++) {
            SitioInteresEntity entity = factory.manufacturePojo(SitioInteresEntity.class);

            em.persist(entity);
            data.add(entity);
        }
    }
    
        /**
     * Prueba para crear un sitioInteres.
     */
    @Test
    public void createSitioInteresTest() {
        PodamFactory factory = new PodamFactoryImpl();
        SitioInteresEntity newEntity = factory.manufacturePojo(SitioInteresEntity.class);
        SitioInteresEntity result = sitioInteresPersistence.create(newEntity);

        Assert.assertNotNull(result);

        SitioInteresEntity entity = em.find(SitioInteresEntity.class, result.getId());

        Assert.assertEquals(newEntity.getNombre(), entity.getNombre());
    }
    
     /**
     * Prueba para consultar la lista de SitioInteres.
     */
    @Test
    public void getSitiosInteresTest() {
        List<SitioInteresEntity> list = sitioInteresPersistence.findAll();
        Assert.assertEquals(data.size(), list.size());
        for (SitioInteresEntity ent : list) {
            boolean found = false;
            for (SitioInteresEntity entity : data) {
                if (ent.getId().equals(entity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }
    
    /**
     * Prueba para consultar un SitioInteres.
     */
    @Test
    public void getSitioInteresTest() {
        SitioInteresEntity entity = data.get(0);
        SitioInteresEntity newEntity = sitioInteresPersistence.find(entity.getId());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(entity.getNombre(), newEntity.getNombre());
        Assert.assertEquals(entity.getLatitud(), newEntity.getLatitud());
    }
    
        /**
     * Prueba para consultar un SitioInteres.
     */
    @Test
    public void getSitioInteresByLatLongTest() {
        SitioInteresEntity entity = data.get(0);
        SitioInteresEntity newEntity = sitioInteresPersistence.findByLatLong(entity.getLatitud(),entity.getLongitud());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(entity.getNombre(), newEntity.getNombre());
        Assert.assertEquals(entity.getLatitud(), newEntity.getLatitud());
    }

    /**
     * Prueba para actualizar un SitioInteres.
     */
    @Test
    public void updateSitioInteresTest() {
        SitioInteresEntity entity = data.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        SitioInteresEntity newEntity = factory.manufacturePojo(SitioInteresEntity.class);

        newEntity.setId(entity.getId());

        sitioInteresPersistence.update(newEntity);

        SitioInteresEntity resp = em.find(SitioInteresEntity.class, entity.getId());

        Assert.assertEquals(newEntity.getNombre(), resp.getNombre());
    }

    /**
     * Prueba para eliminar un SitioInteres.
     */
    @Test
    public void deleteSitioInteresTest() {
        SitioInteresEntity entity = data.get(0);
        sitioInteresPersistence.delete(entity.getId());
        SitioInteresEntity deleted = em.find(SitioInteresEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }
}

