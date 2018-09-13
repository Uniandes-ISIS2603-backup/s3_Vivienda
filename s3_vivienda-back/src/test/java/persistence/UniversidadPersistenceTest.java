/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistence;

import co.edu.uniandes.csw.vivienda.entities.UniversidadEntity;
import co.edu.uniandes.csw.vivienda.persistence.UniversidadPersistence;
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
 * @author Paula Molina Ruiz
 */
@RunWith(Arquillian.class)
public class UniversidadPersistenceTest {
    
    /**
     * Inyección de la dependencia a la clase UniversidadPersistence cuyos métodos
     * se van a probar.
     */
    @Inject
    private UniversidadPersistence universidadPersistence;

    /**
     * Contexto de Persistencia que se va a utilizar para acceder a la Base de
     * datos por fuera de los métodos que se están probando.
     */
    @PersistenceContext
    private EntityManager em;

    /**
     * Variable para martcar las transacciones del em anterior cuando se
     * crean/borran datos para las pruebas.
     */
    @Inject
    UserTransaction utx;

    /**
     * lista que tiene los datos de prueba.
     */
    private List<UniversidadEntity> data = new ArrayList<UniversidadEntity>();

    /**
     * @return Devuelve el jar que Arquillian va a desplegar en el Glassfish
     * embebido. El jar contiene las clases de Editorial, el descriptor de la
     * base de datos y el archivo beans.xml para resolver la inyección de
     * dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(UniversidadEntity.class.getPackage())
                .addPackage(UniversidadPersistence.class.getPackage())
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
        em.createQuery("delete from UniversidadEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        PodamFactory factory = new PodamFactoryImpl();
        for (int i = 0; i < 3; i++) {

            UniversidadEntity entity = factory.manufacturePojo(UniversidadEntity.class);

            em.persist(entity);

            data.add(entity);
        }
    }

    /**
     * Prueba para crear una Universidad.
     */
    @Test
    public void createUniversidadTest() {
        PodamFactory factory = new PodamFactoryImpl();
        UniversidadEntity newEntity = factory.manufacturePojo(UniversidadEntity.class);
        UniversidadEntity result = universidadPersistence.create(newEntity);

        Assert.assertNotNull(result);

        UniversidadEntity entity = em.find(UniversidadEntity.class, result.getId());

        Assert.assertEquals(newEntity.getNombre(), entity.getNombre());
    }

    /**
     * Prueba para consultar la lista de Universidades.
     */
    @Test
    public void getUniversidadesTest() {
        List<UniversidadEntity> list = universidadPersistence.findAll();
        Assert.assertEquals(data.size(), list.size());
        for (UniversidadEntity ent : list) {
            boolean found = false;
            for (UniversidadEntity entity : data) {
                if (ent.getId().equals(entity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }

    /**
     * Prueba para consultar una Editorial.
     */
    @Test
    public void getUniversidadTest() {
        UniversidadEntity entity = data.get(0);
        UniversidadEntity newEntity = universidadPersistence.find(entity.getId());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(entity.getNombre(), newEntity.getNombre());
    }

    /**
     * Prueba para eliminar una Universidad.
     */
    @Test
    public void deleteUniversidadTest() {
        UniversidadEntity entity = data.get(0);
        universidadPersistence.delete(entity.getId());
        UniversidadEntity deleted = em.find(UniversidadEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }

    /**
     * Prueba para actualizar una Editorial.
     */
    @Test
    public void updateUniversidadTest() {
        UniversidadEntity entity = data.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        UniversidadEntity newEntity = factory.manufacturePojo(UniversidadEntity.class);

        newEntity.setId(entity.getId());

        universidadPersistence.update(newEntity);

        UniversidadEntity resp = em.find(UniversidadEntity.class, entity.getId());

        Assert.assertEquals(newEntity.getNombre(), resp.getNombre());
    }   
    
    /**
     * Prueba para consultar una Universidad por nombre.
     */
    @Test
    public void findEditorialByNameTest() {
        UniversidadEntity entity = data.get(0);
        UniversidadEntity newEntity = universidadPersistence.findByName(entity.getNombre());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(entity.getNombre(), newEntity.getNombre());

        newEntity = universidadPersistence.findByName(null);
        Assert.assertNull(newEntity);
    }
}

