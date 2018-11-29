/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.vivienda.test.logic;

import co.edu.uniandes.csw.vivienda.ejb.ArrendadorLogic;
import co.edu.uniandes.csw.vivienda.ejb.ContratoEstudianteLogic;
import co.edu.uniandes.csw.vivienda.ejb.ContratoLogic;
import co.edu.uniandes.csw.vivienda.ejb.ContratoViviendaLogic;
import co.edu.uniandes.csw.vivienda.ejb.EstudianteLogic;
import co.edu.uniandes.csw.vivienda.ejb.ViviendaContratosLogic;
import co.edu.uniandes.csw.vivienda.ejb.ViviendaLogic;
import co.edu.uniandes.csw.vivienda.entities.ContratoEntity;
import co.edu.uniandes.csw.vivienda.entities.CuartoEntity;
import co.edu.uniandes.csw.vivienda.entities.EstudianteEntity;
import co.edu.uniandes.csw.vivienda.entities.ViviendaEntity;
import co.edu.uniandes.csw.vivienda.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.vivienda.persistence.ContratoPersistence;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
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
 * @author estudiante
 */
@RunWith(Arquillian.class)
public class ContratoLogicTest {

    private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private ContratoLogic contratoLogic;
    
    @Inject
    private ContratoViviendaLogic contratoViviendaLogic;
    
    @Inject
    private ContratoEstudianteLogic contratoEstudianteLogic;

    @Inject
    private ViviendaContratosLogic viviendaContratoLogic;
    
    @Inject
    private EstudianteLogic estudianteLogic;

    @Inject
    private ViviendaLogic viviendaLogic;

    @Inject
    private ArrendadorLogic arrendadorLogic;
    
    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private final List<ContratoEntity> data = new ArrayList<>();

    private final List<ViviendaEntity> viviendaData = new ArrayList();
    
    private final List<EstudianteEntity> estudianteData = new ArrayList();
    

    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(ContratoEntity.class.getPackage())
                .addPackage(ContratoLogic.class.getPackage())
                .addPackage(ContratoPersistence.class.getPackage())
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
        } catch (IllegalStateException | SecurityException | HeuristicMixedException | HeuristicRollbackException | NotSupportedException | RollbackException | SystemException e) {
            e.getMessage();
            try {
                utx.rollback();
            } catch (IllegalStateException | SecurityException | SystemException e1) {
                e1.getMessage();
            }
        }
    }

    /**
     * Limpia las tablas que están implicadas en la prueba.
     */
    private void clearData() {
        em.createQuery("delete from " + "ContratoEntity").executeUpdate();
        em.createQuery("delete from " + "ViviendaEntity").executeUpdate();
        em.createQuery("delete from " + "EstudianteEntity").executeUpdate();
        em.createQuery("delete from " + "CuartoEntity").executeUpdate();
        em.createQuery("delete from " + "ArrendadorEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        for (int i = 0; i < 3; i++) {
            ViviendaEntity vivienda = factory.manufacturePojo(ViviendaEntity.class);
            EstudianteEntity estudiante = factory.manufacturePojo(EstudianteEntity.class);
        List<CuartoEntity> cuartosData = new ArrayList<>();
        for(int j=0;j<3;j++)
        {
            CuartoEntity cuarto = factory.manufacturePojo(CuartoEntity.class);
            cuarto.setOcupado(false);
            em.persist(cuarto);
            cuartosData.add(cuarto);
        }           
            vivienda.setCuartos(cuartosData);
            em.persist(vivienda);
            em.persist(estudiante);
            estudianteData.add(estudiante);
            viviendaData.add(vivienda);
            
            ContratoEntity entity = factory.manufacturePojo(ContratoEntity.class);
            entity.setVivienda(vivienda);
            em.persist(entity);
            data.add(entity);
        }
    }

    /**
     * Prueba para crear un Contrato
     *
     * @throws co.edu.uniandes.csw.vivienda.exceptions.BusinessLogicException
     */
    @Test
    public void createContratoTest() throws BusinessLogicException {
        ContratoEntity newEntity = factory.manufacturePojo(ContratoEntity.class);
        newEntity.setMetodoPago(1);
        ViviendaEntity vivienda = viviendaData.get(0);
        newEntity.setVivienda(vivienda);
        newEntity.setEstudiante(estudianteData.get(0));
        newEntity.setCuarto(vivienda.getCuartos().get(0));
        
        ContratoEntity result = contratoLogic.createContrato(newEntity);
//        result.setMetodoPago(result.getMetodoPago());
        Assert.assertNotNull(result);
        ContratoEntity entity = em.find(ContratoEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entity.getId());
        Assert.assertEquals(newEntity.getMetodoPago(), entity.getMetodoPago());


        Assert.assertEquals(newEntity.getFechaInicio(), entity.getFechaInicio());
        Assert.assertEquals(newEntity.getFechaFin(), entity.getFechaFin());
        Assert.assertEquals(newEntity.getEstudiante(), entity.getEstudiante());
        Assert.assertEquals(newEntity.getCuarto(), entity.getCuarto());
        Assert.assertEquals(newEntity.getVivienda(), entity.getVivienda());
    }
    
     /**
     * Prueba para crear un Contrato
     *
     * @throws co.edu.uniandes.csw.vivienda.exceptions.BusinessLogicException
     */
    @Test
    public void createViviendaContratoTest() throws BusinessLogicException {
        ContratoEntity newEntity = factory.manufacturePojo(ContratoEntity.class);
        newEntity.setMetodoPago(1);
        ViviendaEntity vivienda = viviendaData.get(1);
        EstudianteEntity estudiante = estudianteData.get(1);
        CuartoEntity cuarto = vivienda.getCuartos().get(0);
        ContratoEntity result = viviendaContratoLogic.addContrato(vivienda.getId(), cuarto.getId(), estudiante.getId(), newEntity);
        Assert.assertNotNull(result);
        ContratoEntity entity = em.find(ContratoEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entity.getId());

        Assert.assertEquals(newEntity.getEstudiante(), entity.getEstudiante());
        Assert.assertEquals(newEntity.getCuarto(), entity.getCuarto());
        Assert.assertEquals(newEntity.getVivienda(), entity.getVivienda());
        Assert.assertEquals(em.find(CuartoEntity.class, cuarto.getId()).isOcupado(), entity.getCuarto().isOcupado());
    }
    
         /**
     * Prueba para crear un Contrato
     *
     * @throws co.edu.uniandes.csw.vivienda.exceptions.BusinessLogicException
     */
    @Test
    public void generarDatos() throws BusinessLogicException {
        estudianteLogic.generarDatos();
        viviendaLogic.generarDatos();
        arrendadorLogic.generarDatos();
        contratoLogic.generarDatos();
        Assert.assertEquals(10, estudianteLogic.getEstudiantes().size());
        Assert.assertEquals(10, arrendadorLogic.getArrendadores().size());
        Assert.assertEquals(10, viviendaLogic.getViviendas().size());
        
    }

    /**
     * Prueba para crear un Contrato con MetodoPago inválido
     *
     * @throws co.edu.uniandes.csw.vivienda.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void createContratoTestConMetodoPagoInvalido() throws BusinessLogicException {
        ContratoEntity newEntity = factory.manufacturePojo(ContratoEntity.class);
        newEntity.setVivienda(viviendaData.get(0));
        newEntity.setMetodoPago(-1);
        contratoLogic.createContrato(newEntity);
    }

    /**
     * Prueba para crear un Contrato con MetodoPago inválido
     *
     * @throws co.edu.uniandes.csw.vivienda.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void createContratoTestConMetodoPagoInvalido2() throws BusinessLogicException {
        ContratoEntity newEntity = factory.manufacturePojo(ContratoEntity.class);
        newEntity.setVivienda(viviendaData.get(0));
        newEntity.setMetodoPago(-2);
        contratoLogic.createContrato(newEntity);
    }

    /**
     * Prueba para crear un Contrato con una vivienda que no existe.
     *
     * @throws co.edu.uniandes.csw.vivienda.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void createContratoTestConViviendaInexistente() throws BusinessLogicException {
        ContratoEntity newEntity = factory.manufacturePojo(ContratoEntity.class);
        ViviendaEntity viviendaEntity = new ViviendaEntity();
        viviendaEntity.setId(Long.MIN_VALUE);
        newEntity.setVivienda(viviendaEntity);
        contratoLogic.createContrato(newEntity);
    }

    /**
     * Prueba para crear un Contrato con vivienda en null.
     *
     * @throws co.edu.uniandes.csw.vivienda.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void createViviendaTestConNullVivienda() throws BusinessLogicException {
        ContratoEntity newEntity = factory.manufacturePojo(ContratoEntity.class);
        newEntity.setVivienda(null);
        contratoLogic.createContrato(newEntity);
    }

    /**
     * Prueba para consultar la lista de Contratos.
     */
    @Test
    public void getContratosTest() {
        List<ContratoEntity> list = contratoLogic.getContratos();
        Assert.assertEquals(data.size(), list.size());
        for (ContratoEntity entity : list) {
            boolean found = false;
            for (ContratoEntity storedEntity : data) {
                if (entity.getId().equals(storedEntity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }

    /**
     * Prueba para consultar un Contrato.
     */
    @Test
    public void getContratoTest() {
        ContratoEntity entity = data.get(0);
        ContratoEntity resultEntity = contratoLogic.getContrato(entity.getId());
        Assert.assertNotNull(resultEntity);
        Assert.assertEquals(entity.getId(), resultEntity.getId());
        Assert.assertEquals(entity.getFechaInicio(), resultEntity.getFechaInicio());
        Assert.assertEquals(entity.getFechaFin(), resultEntity.getFechaFin());
        Assert.assertEquals(entity.getMetodoPago(), resultEntity.getMetodoPago());
        
        contratoLogic.getContrato(Long.MAX_VALUE);
    }

    /**
     * Prueba para actualizar un Contrato.
     *
     * @throws co.edu.uniandes.csw.vivienda.exceptions.BusinessLogicException
     */
    @Test
    public void updateContratoTest() throws BusinessLogicException {
        ContratoEntity entity = data.get(0);
        ContratoEntity pojoEntity = factory.manufacturePojo(ContratoEntity.class);
        pojoEntity.setMetodoPago(Math.abs(pojoEntity.getMetodoPago()));
        pojoEntity.setId(entity.getId());
        contratoLogic.updateContrato(pojoEntity.getId(), pojoEntity);
        ContratoEntity resp = em.find(ContratoEntity.class, entity.getId());
        Assert.assertEquals(pojoEntity.getId(), resp.getId());
        Assert.assertEquals(pojoEntity.getFechaInicio(), resp.getFechaInicio());
        Assert.assertEquals(pojoEntity.getFechaFin(), resp.getFechaFin());
        Assert.assertEquals(pojoEntity.getMetodoPago(), resp.getMetodoPago());
        
        try
        {
            entity = data.get(1);
            pojoEntity = factory.manufacturePojo(ContratoEntity.class);
            pojoEntity.setMetodoPago(-1);
            contratoLogic.updateContrato(pojoEntity.getId(), pojoEntity);
            Assert.fail("Deberia haber lanzado una excepción");
        }catch(BusinessLogicException e)
        {
            Assert.assertNotEquals(pojoEntity.getId(), em.find(ContratoEntity.class, entity.getId()).getId());
        }
    }
    
        /**
     * Prueba para actualizar un Contrato.
     *
     * @throws co.edu.uniandes.csw.vivienda.exceptions.BusinessLogicException
     */
    @Test
    public void actualizarContratoTest() throws BusinessLogicException {
        ContratoEntity entity = data.get(0);
        ContratoEntity pojoEntity = factory.manufacturePojo(ContratoEntity.class);
        pojoEntity.setMetodoPago(Math.abs(pojoEntity.getMetodoPago()));
        contratoLogic.actualizarContrato(entity.getVivienda().getId(), entity.getId(), pojoEntity);
        ContratoEntity resp = em.find(ContratoEntity.class, entity.getId());
        Assert.assertEquals(pojoEntity.getId(), resp.getId());
        Assert.assertEquals(pojoEntity.getFechaInicio(), resp.getFechaInicio());
        Assert.assertEquals(pojoEntity.getFechaFin(), resp.getFechaFin());
        Assert.assertEquals(pojoEntity.getMetodoPago(), resp.getMetodoPago());
        
        try{    
            entity = data.get(1);
            pojoEntity = factory.manufacturePojo(ContratoEntity.class);
            pojoEntity.setMetodoPago(-1);
            contratoLogic.actualizarContrato(entity.getVivienda().getId(), entity.getId(), pojoEntity);
            Assert.fail("Debio haber lanzado una excepción");
        }catch(BusinessLogicException e)
        {
            Assert.assertNotEquals(pojoEntity.getId(), em.find(ContratoEntity.class, entity.getId()).getId());
        }
    }

    /**
     * Prueba para eliminar un Contrato.
     *
     * @throws co.edu.uniandes.csw.vivienda.exceptions.BusinessLogicException
     */
    @Test
    public void deleteContratoTest() throws BusinessLogicException {
        ContratoEntity entity = data.get(0);
        contratoLogic.deleteContrato(entity.getId());
        ContratoEntity deleted = em.find(ContratoEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }
    
     /**
     * Prueba para eliminar un Contrato.
     *
     * @throws co.edu.uniandes.csw.vivienda.exceptions.BusinessLogicException
     */
    @Test
    public void contratoViviendaTest() throws BusinessLogicException {
        ContratoEntity newEntity = factory.manufacturePojo(ContratoEntity.class);
        newEntity.setMetodoPago(1);
        ViviendaEntity vivienda = viviendaData.get(1);
        EstudianteEntity estudiante = estudianteData.get(1);
        CuartoEntity cuarto = vivienda.getCuartos().get(0);
        
        ContratoEntity contrato = viviendaContratoLogic.addContrato(vivienda.getId(), cuarto.getId(), estudiante.getId(), newEntity);
        
        ViviendaEntity viviendaAux = contratoViviendaLogic.getVivienda(contrato.getId(), vivienda.getId());
        Assert.assertNotNull(viviendaAux);
        
        vivienda = viviendaData.get(2);
        contrato = contratoViviendaLogic.replaceVivienda(contrato.getId(), vivienda.getId());
        Assert.assertEquals(vivienda.getNombre(), contrato.getVivienda().getNombre());
        
        contratoViviendaLogic.removeVivienda(contrato.getId());
    }

     /**
     * Prueba para eliminar un Contrato.
     *
     */
    @Test
    public void contratoEstudianteTest() {
        ContratoEntity entity = data.get(0);
        EstudianteEntity estudiante = estudianteData.get(0);
        
        ContratoEntity contrato = contratoEstudianteLogic.replaceEstudiante(entity.getId(), estudiante.getId());
        Assert.assertEquals(estudiante.getLogin(), contratoLogic.getContrato(contrato.getId()).getEstudiante().getLogin());
        
        contratoEstudianteLogic.removeEstudiante(entity.getId());
        Assert.assertNull(contratoLogic.getContrato(contrato.getId()).getEstudiante());
    }
}
