package co.edu.uniandes.csw.vivienda.test.logic;

import co.edu.uniandes.csw.vivienda.ejb.ArrendadorViviendasLogic;
import co.edu.uniandes.csw.vivienda.ejb.ContratoLogic;
import co.edu.uniandes.csw.vivienda.ejb.ContratoViviendaLogic;
import co.edu.uniandes.csw.vivienda.ejb.ViviendaArrendadorLogic;
import co.edu.uniandes.csw.vivienda.ejb.ViviendaLogic;
import co.edu.uniandes.csw.vivienda.entities.ArrendadorEntity;
import co.edu.uniandes.csw.vivienda.entities.ContratoEntity;
import co.edu.uniandes.csw.vivienda.entities.ViviendaEntity;
import co.edu.uniandes.csw.vivienda.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.vivienda.persistence.ViviendaPersistence;
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

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: Daniel Giraldo
 */
@RunWith(Arquillian.class)
public class ViviendaLogicTest {

    @Inject
    ViviendaLogic logic;
    
    @Inject
    ViviendaArrendadorLogic viviendaArrendadorLogic;
    
    @Inject
    ContratoViviendaLogic contratoViviendaLogic;
    
    @Inject
    ArrendadorViviendasLogic arrendadorViviendasLogic;
    
    @Inject
    ContratoLogic contratoLogic;

    private PodamFactory factory = new PodamFactoryImpl();

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private final List<ViviendaEntity> data = new ArrayList<>();
    
    private final List<ArrendadorEntity> arrendadorData = new ArrayList<>();
    
    private final List<ContratoEntity> contratoData = new ArrayList<>();

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(ViviendaEntity.class.getPackage())
                .addPackage(ViviendaLogic.class.getPackage())
                .addPackage(ViviendaPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }


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

    private void insertData(){
        for (int i = 0; i < 20; i++) {
            ViviendaEntity entity = factory.manufacturePojo(ViviendaEntity.class);
            ArrendadorEntity arrendador = factory.manufacturePojo(ArrendadorEntity.class);
            ContratoEntity contrato = factory.manufacturePojo(ContratoEntity.class);
            em.persist(entity);
            em.persist(arrendador);
            em.persist(contrato);
            data.add(entity);
            arrendadorData.add(arrendador);
            contratoData.add(contrato);
        }
    }

    private void clearData(){
        em.createQuery("delete from ViviendaEntity").executeUpdate();
        em.createQuery("delete from ArrendadorEntity").executeUpdate();
    }

    private ViviendaEntity crearVivienda(){
        return factory.manufacturePojo(ViviendaEntity.class);
    }

    @Test
    public void createViviendaTest(){
        ViviendaEntity entity = crearVivienda();
        try{
            logic.createVivienda(entity);
            ViviendaEntity busq = em.find(ViviendaEntity.class, entity.getId());
            Assert.assertEquals(busq.getNombre(), entity.getNombre());
        } catch (Exception e){
            Assert.fail("Se debería crear");
        }

        ViviendaEntity direccionRepetida = data.get(0);
        direccionRepetida.setId((long) 234234234);
        try {
            logic.createVivienda(direccionRepetida);
            Assert.fail("La dirección ya existía");
        } catch (Exception e){
        }
        
        try
        {
            entity = factory.manufacturePojo(ViviendaEntity.class);
            entity.setNombre(null);
            logic.createVivienda(entity);
            Assert.fail("Deberia haber lanzada excepción");
        }catch(BusinessLogicException e)
        {
            Assert.assertNull("No deberia encontrar la vivienda", logic.getVivienda(entity.getId()));
        }
        
        try
        {
            entity = factory.manufacturePojo(ViviendaEntity.class);
            entity.setCiudad(null);
            logic.createVivienda(entity);
            Assert.fail("Deberia haber lanzada excepción");
        }catch(BusinessLogicException e)
        {
            Assert.assertNull("No deberia encontrar la vivienda", logic.getVivienda(entity.getId()));
        }
                
        try
        {
            entity = factory.manufacturePojo(ViviendaEntity.class);
            entity.setDireccion(null);
            logic.createVivienda(entity);
            Assert.fail("Deberia haber lanzada excepción");
        }catch(BusinessLogicException e)
        {
            Assert.assertNull("No deberia encontrar la vivienda", logic.getVivienda(entity.getId()));
        }
    }

    @Test
    public void getViviendasTest(){
        List<ViviendaEntity> listResult = logic.getViviendas();
        Assert.assertEquals(listResult.size(), data.size());
        for(ViviendaEntity entity : listResult){
            boolean found = false;
            for(ViviendaEntity entity2: data){
                if(entity.getId().equals(entity2.getId())){
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }

    @Test
    public void getViviendaTest(){
        for(ViviendaEntity entity: data){
            ViviendaEntity found = logic.getVivienda(entity.getId());
            Assert.assertNotNull(found);
            Assert.assertEquals(found.getId(), entity.getId());
            Assert.assertEquals(found.getNombre(), entity.getNombre());
        }
    }

    @Test
    public void deleteViviendaTest(){
        for(ViviendaEntity entity: data){
            try {
                logic.deleteVivienda(entity.getId());
                ViviendaEntity deleted = em.find(ViviendaEntity.class, entity.getId());
                Assert.assertNull(deleted);
            } catch (BusinessLogicException e) {
                e.printStackTrace();
                Assert.fail("Vivienda existe");
            }
        }
        Assert.assertEquals( 0,em.createQuery("select u from ViviendaEntity u", ViviendaEntity.class)
                .getResultList().size());
   
        ViviendaEntity entity = data.get(0);
        try
        {
            entity.setId(Long.MIN_VALUE);
            logic.deleteVivienda(entity.getId());
            Assert.fail("Deberia haber lanzada excepción");
        }catch(BusinessLogicException e)
        {
            Assert.assertNull("No deberia encontrar la vivienda", logic.getVivienda(entity.getId()));
        }
    }

    @Test
    public void updateViviendaTest(){
        List<ViviendaEntity> viviendas = logic.getViviendas();
        for(ViviendaEntity entity: viviendas){
            Long id = entity.getId();
            ViviendaEntity entityUpdate = new ViviendaEntity();
            entityUpdate.setNombre(Long.toString(id));
            try {
                logic.updateVivienda(id, entityUpdate);
            } catch (BusinessLogicException e) {
                e.printStackTrace();
                Assert.fail("Vivienda existe");
            }
        }

        List<ViviendaEntity> viviendas2 = logic.getViviendas();
        for (ViviendaEntity entity: viviendas2){
            Assert.assertNotEquals(entity.getNombre(), Long.toString(entity.getId()));
        }
        ViviendaEntity entity = data.get(0);
        try
        {
            entity.setId(Long.MIN_VALUE);
            logic.updateVivienda(entity.getId(), entity);
            Assert.fail("Deberia haber lanzada excepción");
        }catch(BusinessLogicException e)
        {
            if(entity!=null)
                Assert.assertNull("No deberia encontrar la vivienda", logic.getVivienda(entity.getId()));
        }
        
        logic.generarDatos();
        Assert.assertEquals(10, logic.getViviendas().size());
    }
    
    @Test
    public void viviendaArrendadorTest(){
       ArrendadorEntity arrendador = arrendadorData.get(0);
       ViviendaEntity vivienda = data.get(0);
       arrendador = arrendadorData.get(1);
       viviendaArrendadorLogic.replaceArrendador(vivienda.getId(), arrendador.getId());
       Assert.assertNull(viviendaArrendadorLogic.getArrendador(Long.MIN_VALUE));
    }

    @Test
    public void contratoViviendaTest() {
        ContratoEntity entity = contratoData.get(0);
        ViviendaEntity vivienda = data.get(0);
        
        ContratoEntity contrato = contratoViviendaLogic.replaceVivienda(entity.getId(), vivienda.getId());
        Assert.assertEquals(vivienda.getNombre(), contratoLogic.getContrato(contrato.getId()).getVivienda().getNombre());
        
        contratoViviendaLogic.removeVivienda(entity.getId());
        Assert.assertNull(contratoLogic.getContrato(contrato.getId()).getVivienda());
    }
}