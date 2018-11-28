/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.vivienda.test.logic;

import co.edu.uniandes.csw.vivienda.ejb.ContratoServiciosAdicionalesLogic;
import co.edu.uniandes.csw.vivienda.ejb.ServicioAdicionalLogic;
import co.edu.uniandes.csw.vivienda.entities.ContratoEntity;
import co.edu.uniandes.csw.vivienda.entities.ServicioAdicionalEntity;
import co.edu.uniandes.csw.vivienda.entities.ViviendaEntity;
import co.edu.uniandes.csw.vivienda.exceptions.BusinessLogicException;
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
 * Pruebas de logica de ServicioAdicionalLogic
 *
 * @author Paula Molina Ruiz
 */
@RunWith(Arquillian.class)
public class ServicioAdicionalLogicTest {
    
    private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private ServicioAdicionalLogic reviewLogic;
    
    @Inject
    private ContratoServiciosAdicionalesLogic contratoServiciosLogic;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private final List<ServicioAdicionalEntity> data = new ArrayList<>();

    private final List<ViviendaEntity> dataVivienda = new ArrayList<>();
    
    private final List<ContratoEntity> dataContrato = new ArrayList<>();


    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(ServicioAdicionalEntity.class.getPackage())
                .addPackage(ServicioAdicionalLogic.class.getPackage())
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
         em.createQuery("delete from ContratoEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        
        for (int i = 0; i < 3; i++) {
            ViviendaEntity vivienda = factory.manufacturePojo(ViviendaEntity.class);
            ContratoEntity contrato = factory.manufacturePojo(ContratoEntity.class);
            em.persist(vivienda);
            em.persist(contrato);
            dataContrato.add(contrato);
            dataVivienda.add(vivienda);
        }

        for (int i = 0; i < 3; i++) {
            ServicioAdicionalEntity servicio = factory.manufacturePojo(ServicioAdicionalEntity.class);
            servicio.setVivienda(dataVivienda.get(1));
            em.persist(servicio);
            data.add(servicio);
        }
    }

    /**
     * Prueba para crear un ServicioAdicional.
     *
     * @throws co.edu.uniandes.csw.vivienda.exceptions.BusinessLogicException
     */
    @Test
    public void createServicioAdicionalTest() throws BusinessLogicException {
        ServicioAdicionalEntity newEntity = factory.manufacturePojo(ServicioAdicionalEntity.class);
        newEntity.setVivienda(dataVivienda.get(1));
        newEntity.setContrato(dataContrato.get(1));
        ServicioAdicionalEntity result = reviewLogic.createServicioAdicional(dataVivienda.get(1).getId(), newEntity);
        Assert.assertNotNull(result);
        ServicioAdicionalEntity entity = em.find(ServicioAdicionalEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entity.getId());
        Assert.assertEquals(newEntity.getNombre(), entity.getNombre());
        Assert.assertEquals(newEntity.getCosto(), entity.getCosto());
        Assert.assertEquals(newEntity.getDescripcion(), entity.getDescripcion());
        Assert.assertEquals(newEntity.getVivienda(), entity.getVivienda());
        Assert.assertEquals(newEntity.getContrato(), entity.getContrato());
    }

    /**
     * Prueba para consultar la lista de Servicios Adicionales.
     *
     * @throws co.edu.uniandes.csw.vivienda.exceptions.BusinessLogicException
     */
    @Test
    public void ServiciosAdicionalesTest() throws BusinessLogicException {
        List<ServicioAdicionalEntity> list = reviewLogic.getServiciosAdicionales(dataVivienda.get(1).getId());
        Assert.assertEquals(data.size(), list.size());
        for (ServicioAdicionalEntity entity : list) {
            boolean found = false;
            for (ServicioAdicionalEntity storedEntity : data) {
                if (entity.getId().equals(storedEntity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }

    /**
     * Prueba para consultar un ServicioAdicional.
     */
    @Test
    public void getServicioAdicionalTest() {
        ServicioAdicionalEntity entity = data.get(0);
        ServicioAdicionalEntity resultEntity = reviewLogic.getServicioAdicional(dataVivienda.get(1).getId(), entity.getId());
        Assert.assertNotNull(resultEntity);
        Assert.assertEquals(entity.getId(), resultEntity.getId());
        Assert.assertEquals(entity.getNombre(), resultEntity.getNombre());
        Assert.assertEquals(entity.getCosto(), resultEntity.getCosto());
        Assert.assertEquals(entity.getDescripcion(), resultEntity.getDescripcion());
    }

    /**
     * Prueba para actualizar un ServicioAdicional.
     */
    @Test
    public void updateServicioAdicionalTest() {
        ServicioAdicionalEntity entity = data.get(0);
        ServicioAdicionalEntity pojoEntity = factory.manufacturePojo(ServicioAdicionalEntity.class);

        pojoEntity.setId(entity.getId());

        reviewLogic.updateServicioAdicional(dataVivienda.get(1).getId(), pojoEntity);

        ServicioAdicionalEntity resp = em.find(ServicioAdicionalEntity.class, entity.getId());

        Assert.assertEquals(pojoEntity.getId(), resp.getId());
        Assert.assertEquals(pojoEntity.getNombre(), resp.getNombre());
        Assert.assertEquals(pojoEntity.getCosto(), resp.getCosto());
        Assert.assertEquals(pojoEntity.getDescripcion(), resp.getDescripcion());
    }

    /**
     * Prueba para eliminar un ServicioAdicional.
     *
     * @throws co.edu.uniandes.csw.vivienda.exceptions.BusinessLogicException
     */
    @Test
    public void deleteServicioAdicionalTest() throws BusinessLogicException {
        ServicioAdicionalEntity entity = data.get(0);
        reviewLogic.deleteServicioAdicional(dataVivienda.get(1).getId(), entity.getId());
        ServicioAdicionalEntity deleted = em.find(ServicioAdicionalEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }

    /**
     * Prueba para eliminarle un servicio adicional a una vivienda del cual no pertenece.
     *
     * @throws co.edu.uniandes.csw.vivienda.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void deleteReviewConBookNoAsociadoTest() throws BusinessLogicException {
        ServicioAdicionalEntity entity = data.get(0);
        reviewLogic.deleteServicioAdicional(dataVivienda.get(0).getId(), entity.getId());
    }
    
        /**
     * Prueba para eliminar un Contrato.
     *
     * @throws co.edu.uniandes.csw.vivienda.exceptions.BusinessLogicException
     */
    @Test
    public void addServicioAdicionalTest() throws BusinessLogicException {
        ContratoEntity entity = dataContrato.get(0);
        ViviendaEntity vivienda = dataVivienda.get(0);
        ServicioAdicionalEntity servicio = data.get(0);
        
        contratoServiciosLogic.addServicioAdicional(entity.getId(), servicio.getId(), vivienda.getId());
        Assert.assertEquals(0,contratoServiciosLogic.getServiciosAdicionales(entity.getId()).size());
        
        Assert.assertNull(contratoServiciosLogic.getServicioAdicional(entity.getId(), servicio.getId(), vivienda.getId()));
        contratoServiciosLogic.replaceServiciosAdicionales(entity.getId(), data);
        contratoServiciosLogic.removeServicioAdicional(entity.getId(), servicio.getId(), vivienda.getId());
        
        Assert.assertNull(contratoServiciosLogic.getServicioAdicional(entity.getId(), servicio.getId(), vivienda.getId()));
    }
    
}
