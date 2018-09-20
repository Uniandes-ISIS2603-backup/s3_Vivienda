/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.vivienda.test.logic;

import co.edu.uniandes.csw.vivienda.ejb.SitioInteresLogic;
import co.edu.uniandes.csw.vivienda.ejb.ViviendaLogic;
import co.edu.uniandes.csw.vivienda.entities.SitioInteresEntity;
import co.edu.uniandes.csw.vivienda.entities.ViviendaEntity;
import co.edu.uniandes.csw.vivienda.exceptions.BusinessLogicException;
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
 *Pruebas de logica de sitioInteres
 * @author msalcedo
 */
@RunWith(Arquillian.class)
public class SitioInteresLogicTest {
    
    private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private SitioInteresLogic sitioInteresLogic;
    
    @Inject
    private ViviendaLogic viviendaLogic;
    
    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;
    
    private List<SitioInteresEntity> data = new ArrayList<SitioInteresEntity>();
    
    private List<ViviendaEntity> viviendaData = new ArrayList<>();

    
    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(SitioInteresEntity.class.getPackage())  
                .addPackage(ViviendaEntity.class.getPackage())
                .addPackage(SitioInteresLogic.class.getPackage())
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
//         em.createQuery("delete from ViviendaEntity").executeUpdate();
        em.createQuery("delete from SitioInteresEntity").executeUpdate();
        
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        
        for (int i = 0; i < 3; i++) {
            ViviendaEntity viviendas = factory.manufacturePojo(ViviendaEntity.class);
            em.persist(viviendas);
            viviendaData.add(viviendas);
 
        }
        for (int i = 0; i < 3; i++) {
            
            SitioInteresEntity entity = factory.manufacturePojo(SitioInteresEntity.class);
            entity.setVivienda(viviendaData.get(1));
            em.persist(entity);
            data.add(entity);
        }

        
    }
    
    /**
     * Prueba para crear un SitioInteres
     */
    @Test
    public void createSitioInteresTest() throws BusinessLogicException {
        SitioInteresEntity newEntity = factory.manufacturePojo(SitioInteresEntity.class);
        newEntity.setVivienda(viviendaData.get(1));
        SitioInteresEntity result = sitioInteresLogic.createSitioInteres(viviendaData.get(1).getId(),newEntity);
        Assert.assertNotNull(result);
        SitioInteresEntity entity = em.find(SitioInteresEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entity.getId());
        Assert.assertEquals(newEntity.getLatitud(), entity.getLatitud());
        Assert.assertEquals(newEntity.getLongitud(), entity.getLongitud());
        Assert.assertEquals(newEntity.getNombre(), entity.getNombre());
    }
    
    /**
     * Prueba para consultar la lista de sitiosInteres.
     */
    @Test
    public void getSitiosInteresTest() {
        ViviendaEntity newVivienda = viviendaData.get(1);
        List<SitioInteresEntity> list = sitioInteresLogic.getSitiosInteres(newVivienda.getId());
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
        ViviendaEntity newVivienda = viviendaData.get(1);
        SitioInteresEntity entity = data.get(0);
        SitioInteresEntity resultEntity = sitioInteresLogic.getSitioInteres(newVivienda.getId(),entity.getId());
        Assert.assertNotNull(resultEntity);
        Assert.assertEquals(entity.getId(), resultEntity.getId());
        Assert.assertEquals(entity.getNombre(), resultEntity.getNombre());
        Assert.assertEquals(entity.getDescripcion(), resultEntity.getDescripcion());
        Assert.assertEquals(entity.getLatitud(), resultEntity.getLatitud());
        Assert.assertEquals(entity.getLongitud(), resultEntity.getLongitud());
    }
    
    /**
    * Prueba para actualizar un SitioInteres.
     */
    @Test
    public void updateSitioInteresTest() {
        SitioInteresEntity entity = data.get(0);
        SitioInteresEntity pojoEntity = factory.manufacturePojo(SitioInteresEntity.class);

        pojoEntity.setId(entity.getId());

        sitioInteresLogic.updateSitioInteres(pojoEntity.getId(), pojoEntity);

        SitioInteresEntity resp = em.find(SitioInteresEntity.class, entity.getId());

        Assert.assertEquals(pojoEntity.getId(), resp.getId());
        Assert.assertEquals(pojoEntity.getNombre(), resp.getNombre());
        Assert.assertEquals(pojoEntity.getLatitud(), resp.getLatitud());
        Assert.assertEquals(pojoEntity.getLongitud(), resp.getLongitud());
    }

    /**
     * Prueba para eliminar un SitioInteres de una vivienda a la cual no esta asociada.
     *
     * @throws co.edu.uniandes.csw.univiviendaDB.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void deleteSitioInteresConViviendaNoAsociadaTest() throws BusinessLogicException {
        SitioInteresEntity entity = data.get(0);
        sitioInteresLogic.deleteSitioInteres(viviendaData.get(0).getId(),entity.getId());
    }
    
    /**
     * Prueba para eliminar un SitioInteres.
     *
     * @throws co.edu.uniandes.csw.univiviendaDB.exceptions.BusinessLogicException
     */
    @Test
    public void deleteSitioInteresTest() throws BusinessLogicException {
        SitioInteresEntity entity = data.get(0);
        sitioInteresLogic.deleteSitioInteres(viviendaData.get(1).getId(), entity.getId());
        SitioInteresEntity deleted = em.find(SitioInteresEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }
}
