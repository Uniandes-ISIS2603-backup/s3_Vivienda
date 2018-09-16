/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.vivienda.test.logic;

import co.edu.uniandes.csw.vivienda.ejb.ArrendadorLogic;
import co.edu.uniandes.csw.vivienda.entities.ArrendadorEntity;
import co.edu.uniandes.csw.vivienda.entities.ViviendaEntity;
import co.edu.uniandes.csw.vivienda.exceptions.BusinessLogicException;
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
 * @author estudiante
 */
@RunWith(Arquillian.class)
public class ArrendadorLogicTest {
    
    private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private ArrendadorLogic arrendadorLogic;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private List<ArrendadorEntity> data = new ArrayList<ArrendadorEntity>();

    private List<ViviendaEntity> viviendasData = new ArrayList();
    
    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(ArrendadorEntity.class.getPackage())
                .addPackage(ArrendadorLogic.class.getPackage())
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
        em.createQuery("delete from ViviendaEntity").executeUpdate();
        em.createQuery("delete from ArrendadorEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        for (int i = 0; i < 3; i++) {
            ViviendaEntity viviendas = factory.manufacturePojo(ViviendaEntity.class);
            em.persist(viviendas);
            viviendasData.add(viviendas);
        }
        for (int i = 0; i < 3; i++) {
            ArrendadorEntity entity = factory.manufacturePojo(ArrendadorEntity.class);
            em.persist(entity);
            data.add(entity);
            if (i == 0) {
                viviendasData.get(i).setArrendador(entity);
            }
        }
    }
    
    /**
     * Prueba para crear un Arrendador
     */
    @Test
    public void createArrendadorTest() throws BusinessLogicException {
        ArrendadorEntity newEntity = factory.manufacturePojo(ArrendadorEntity.class);
        ArrendadorEntity result = arrendadorLogic.createArrendador(newEntity);
        Assert.assertNotNull(result);
        ArrendadorEntity entity = em.find(ArrendadorEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entity.getId());
        Assert.assertEquals(newEntity.getNombre(), entity.getNombre());
    }
    
    /**
     * Prueba para crear un Arrendador con el mismo nombre de un Arrendador que ya
     * existe.
     *
     * @throws co.edu.uniandes.csw.univiviendaDB.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void createArrendadorConMismoLoginTest() throws BusinessLogicException {
       ArrendadorEntity newEntity = factory.manufacturePojo(ArrendadorEntity.class);
        newEntity.setLogin(data.get(0).getLogin());
        arrendadorLogic.createArrendador(newEntity);
    }
    
     /**
     * Prueba para consultar la lista de Arrendadores.
     */
    @Test
    public void getArrendadoresTest() {
        List<ArrendadorEntity> list = arrendadorLogic.getArrendadores();
        Assert.assertEquals(data.size(), list.size());
        for (ArrendadorEntity entity : list) {
            boolean found = false;
            for (ArrendadorEntity storedEntity : data) {
                if (entity.getId().equals(storedEntity.getId())) {
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
        ArrendadorEntity resultEntity = arrendadorLogic.getArrendador(entity.getId());
        Assert.assertNotNull(resultEntity);
        Assert.assertEquals(entity.getId(), resultEntity.getId());
        Assert.assertEquals(entity.getNombre(), resultEntity.getNombre());
    }

    /**
     * Prueba para actualizar un Arrendador.
     */
    @Test
    public void updateArrendadorTest() {
        ArrendadorEntity entity = data.get(0);
        ArrendadorEntity pojoEntity = factory.manufacturePojo(ArrendadorEntity.class);
        pojoEntity.setId(entity.getId());
        arrendadorLogic.updateArrendador(pojoEntity.getId(), pojoEntity);
        ArrendadorEntity resp = em.find(ArrendadorEntity.class, entity.getId());
        Assert.assertEquals(pojoEntity.getId(), resp.getId());
        Assert.assertEquals(pojoEntity.getNombre(), resp.getNombre());
        Assert.assertEquals(pojoEntity.getLogin(), resp.getLogin());
    }

    /**
     * Prueba para eliminar un Editorial.
     *
     * @throws co.edu.uniandes.csw.univiviendaDB.exceptions.BusinessLogicException
     */
    @Test
    public void deleteArrendadorTest() throws BusinessLogicException {
        ArrendadorEntity entity = data.get(1);
        Assert.assertNotNull(entity);
        arrendadorLogic.deleteArrendador(entity.getId());
        ArrendadorEntity deleted = em.find(ArrendadorEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }

    /**
     * Prueba para eliminar un Editorial con books asociados.
     *
     * @throws co.edu.uniandes.csw.univiviendaDB.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void deleteArrendadorConViviendasAsociadosTest() throws BusinessLogicException {
        ArrendadorEntity entity = data.get(0);
        arrendadorLogic.deleteArrendador(entity.getId());
    }
}
