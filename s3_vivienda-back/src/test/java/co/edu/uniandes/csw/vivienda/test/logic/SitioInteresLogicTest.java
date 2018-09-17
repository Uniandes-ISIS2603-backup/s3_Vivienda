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
 *
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
         em.createQuery("delete from ViviendaEntity").executeUpdate();
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
            em.persist(entity);
            data.add(entity);
            if (i == 2) {
                viviendaData.get(0).setSitiosDeInteres(data);
            }
        }
        
    }
    
    /**
     * Prueba para crear un SitioInteres
     */
    @Test
    public void createSitioInteresTest() throws BusinessLogicException {
//        SitioInteresEntity newEntity = factory.manufacturePojo(SitioInteresEntity.class);
//        SitioInteresEntity result = sitioInteresLogic.createSitioInteres(newEntity);
//        Assert.assertNotNull(result);
//        SitioInteresEntity entity = em.find(SitioInteresEntity.class, result.getId());
//        Assert.assertEquals(newEntity.getId(), entity.getId());
//        Assert.assertEquals(newEntity.getLatitud(), entity.getLatitud());
//        Assert.assertEquals(newEntity.getLongitud(), entity.getLongitud());
//        Assert.assertEquals(newEntity.getNombre(), entity.getNombre());
    }
}
