/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.vivienda.test.persistence;

import co.edu.uniandes.csw.vivienda.entities.ServicioAdicionalEntity;
import co.edu.uniandes.csw.vivienda.entities.ViviendaEntity;
import co.edu.uniandes.csw.vivienda.persistence.ServicioAdicionalPersistence;
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
 * Pruebas de persistencia de ServicioAdicional
 *
 * @author Paula Molina 
 */
@RunWith(Arquillian.class)
public class ServicioAdicionalPersistenceTest {
    
    @Inject
    private ServicioAdicionalPersistence servicioAdicionalPersistence;

    @PersistenceContext
    private EntityManager em;

    @Inject
    UserTransaction utx;

    private List<ServicioAdicionalEntity> data = new ArrayList<ServicioAdicionalEntity>();
	
    private List<ViviendaEntity> dataServicioAdicional = new ArrayList<ViviendaEntity>();

    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(ServicioAdicionalEntity.class.getPackage())
                .addPackage(ServicioAdicionalPersistence.class.getPackage())
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
        em.createQuery("delete from ServicioAdicionalEntity").executeUpdate();
        em.createQuery("delete from ViviendaEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        PodamFactory factory = new PodamFactoryImpl();
        for (int i = 0; i < 3; i++) {
            ViviendaEntity entity = factory.manufacturePojo(ViviendaEntity.class);
            em.persist(entity);
            dataServicioAdicional.add(entity);
        }
        for (int i = 0; i < 3; i++) {
            ServicioAdicionalEntity entity = factory.manufacturePojo(ServicioAdicionalEntity.class);
            if (i == 0) {
                entity.setVivienda(dataServicioAdicional.get(0));
            }
            em.persist(entity);
            data.add(entity);
        }
    }

    /**
     * Prueba para crear un ServicioAdicional.
     */
    @Test
    public void createServicioAdicionalTest() {

        PodamFactory factory = new PodamFactoryImpl();
        ServicioAdicionalEntity newEntity = factory.manufacturePojo(ServicioAdicionalEntity.class);
        ServicioAdicionalEntity result = servicioAdicionalPersistence.create(newEntity);

        Assert.assertNotNull(result);

        ServicioAdicionalEntity entity = em.find(ServicioAdicionalEntity.class, result.getId());

        Assert.assertEquals(newEntity.getNombre(), entity.getNombre());
        Assert.assertEquals(newEntity.getCosto(), entity.getCosto());
        Assert.assertEquals(newEntity.getDescripcion(), entity.getDescripcion());
    }

    /**
     * Prueba para consultar un ServicioAdicional.
     */
    @Test
    public void getServicioAdicionalTest() {
        ServicioAdicionalEntity entity = data.get(0);
        ServicioAdicionalEntity newEntity = servicioAdicionalPersistence.find(dataServicioAdicional.get(0).getId(), entity.getId());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(entity.getNombre(), newEntity.getNombre());
        Assert.assertEquals(entity.getCosto(), newEntity.getCosto());
        Assert.assertEquals(entity.getDescripcion(), newEntity.getDescripcion());
    }

    /**
     * Prueba para eliminar un ServicioAdicional.
     */
    @Test
    public void deleteServicioAdicionalTest() {
        ServicioAdicionalEntity entity = data.get(0);
        servicioAdicionalPersistence.delete(entity.getId());
        ServicioAdicionalEntity deleted = em.find(ServicioAdicionalEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }

    /**
     * Prueba para actualizar un ServicioAdicional.
     */
    @Test
    public void updateServicioAdicionalTest() {
        ServicioAdicionalEntity entity = data.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        ServicioAdicionalEntity newEntity = factory.manufacturePojo(ServicioAdicionalEntity.class);

        newEntity.setId(entity.getId());

        servicioAdicionalPersistence.update(newEntity);

        ServicioAdicionalEntity resp = em.find(ServicioAdicionalEntity.class, entity.getId());

        Assert.assertEquals(newEntity.getNombre(), resp.getNombre());
        Assert.assertEquals(newEntity.getCosto(), resp.getCosto());
        Assert.assertEquals(newEntity.getDescripcion(), resp.getDescripcion());
    }
}
