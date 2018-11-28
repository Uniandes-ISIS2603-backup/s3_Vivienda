package co.edu.uniandes.csw.vivienda.test.logic;

import co.edu.uniandes.csw.vivienda.ejb.CuartoLogic;
import co.edu.uniandes.csw.vivienda.ejb.ViviendaLogic;
import co.edu.uniandes.csw.vivienda.entities.CuartoEntity;
import co.edu.uniandes.csw.vivienda.entities.ViviendaEntity;
import co.edu.uniandes.csw.vivienda.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.vivienda.persistence.CuartoPersistence;
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
public class CuartoLogicTest {

    @Inject
    ViviendaLogic viviendaLogic;
    
    @Inject
    CuartoPersistence cuartoPersistence;

    @Inject
    CuartoLogic cuartoLogic;

    private PodamFactory factory = new PodamFactoryImpl();

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private final List<ViviendaEntity> viviendasData = new ArrayList();
    private final List<CuartoEntity> cuartosData = new ArrayList<>();

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(ViviendaEntity.class.getPackage())
                .addPackage(ViviendaLogic.class.getPackage())
                .addPackage(CuartoLogic.class.getPackage())
                .addPackage(CuartoEntity.class.getPackage())
                .addPackage(ViviendaPersistence.class.getPackage())
                .addPackage(CuartoPersistence.class.getPackage())
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

    private void insertData() {
        for (int i = 0; i < 10; i++) {
            ViviendaEntity entity = factory.manufacturePojo(ViviendaEntity.class);
            em.persist(entity);
            viviendasData.add(entity);
        }
    }

    private void insertarCuartos(){
        for (ViviendaEntity vivienda: viviendasData) {
            for (int i = 0; i < 3; i++) {
                CuartoEntity cuarto = factory.manufacturePojo(CuartoEntity.class);
                try {
                    cuartoLogic.addCuarto(vivienda.getId(), cuarto);
                    cuartosData.add(cuarto);
                } catch (BusinessLogicException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void clearData() {
        em.createQuery("delete from ContratoEntity").executeUpdate();
        em.createQuery("delete from ViviendaEntity").executeUpdate();
        em.createQuery("delete from CuartoEntity").executeUpdate();
    }

    @Test
    public void createCuartoTest(){
        for(ViviendaEntity vivienda : viviendasData){
            CuartoEntity cuarto = factory.manufacturePojo(CuartoEntity.class);
            try {
                CuartoEntity cuartoNuevo = cuartoLogic.addCuarto(vivienda.getId(), cuarto);
                List<CuartoEntity> cuartos = viviendaLogic.getVivienda(vivienda.getId()).getCuartos();
                Assert.assertEquals(cuartos.get(0).getDescripcion(), cuartoNuevo.getDescripcion());
                Assert.assertEquals(cuartos.get(0).getId(), cuartoNuevo.getId());
                Assert.assertEquals(cuartos.get(0).getVivienda(), cuartoNuevo.getVivienda());
            } catch (BusinessLogicException e) {
                e.printStackTrace();
                Assert.fail("Debería añadirse el cuarto");
            }
        }
        
        CuartoEntity cuarto = factory.manufacturePojo(CuartoEntity.class);
        try
        {
            ViviendaEntity vivienda = viviendaLogic.getViviendas().get(0);
            cuarto.setVivienda(vivienda);
            cuarto.setCostoArriendo(0);
            cuartoLogic.addCuarto(vivienda.getId(), cuarto);
            Assert.fail("Deberia haber lanzado Excepcion");
        }catch(BusinessLogicException e)
        {
            Assert.assertNull(cuartoPersistence.find(cuarto.getId()));
        }
    }

    @Test
    public void crearCuartoViviendaInexistenteTest(){
        CuartoEntity cuarto = factory.manufacturePojo(CuartoEntity.class);
        try {
            cuartoLogic.addCuarto((long) 239234023, cuarto);
            Assert.fail("La vivienda no existe");
        } catch (BusinessLogicException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getCuartosTest(){
        //get cuartos existentes
        insertarCuartos();
        for(ViviendaEntity vivienda : viviendasData){
            List<CuartoEntity> listaCuartos = cuartoLogic.getCuartos(vivienda.getId());
            Assert.assertEquals(3, listaCuartos.size());
        }
    }

    @Test
    public void getCuartosNullTest(){
        List<CuartoEntity> listaVacia = cuartoLogic.getCuartos((long)234234233);
        Assert.assertNull(listaVacia);
    }

    @Test
    public void getCuartosViviendaSinCuartosTest(){
        ViviendaEntity viviendaEntity = factory.manufacturePojo(ViviendaEntity.class);
        try {
            viviendaLogic.createVivienda(viviendaEntity);
            List<CuartoEntity> cuartos = cuartoLogic.getCuartos(viviendaEntity.getId());
            Assert.assertEquals(0, cuartos.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getCuartoTest(){
        Long idCuartoExistente = new Long(-2);
        Long idViviendaExistente = new Long(-2);

        //Pruebas para encontrar cuartos que existen
        for(ViviendaEntity vivienda : viviendasData){
            CuartoEntity cuarto = factory.manufacturePojo(CuartoEntity.class);
            try {
                cuartoLogic.addCuarto(vivienda.getId(), cuarto);
                CuartoEntity busq = cuartoLogic.getCuarto(vivienda.getId(), cuarto.getId());
                Assert.assertEquals(busq.getNombre(), cuarto.getNombre());
                idCuartoExistente = busq.getId();
                idViviendaExistente = vivienda.getId();
            } catch (BusinessLogicException e) {
                e.printStackTrace();
                Assert.fail("No debería fallar");
            }
        }
        
        CuartoEntity cuarto = factory.manufacturePojo(CuartoEntity.class);
        try
        {
            ViviendaEntity vivienda = viviendaLogic.getViviendas().get(0);
            cuarto.setVivienda(vivienda);
            cuartoLogic.addCuarto(vivienda.getId(), cuarto);
            cuartoLogic.getCuarto(viviendaLogic.getViviendas().get(1).getId(), cuarto.getId());
            Assert.fail("Deberia haber lanzado Excepcion");
        }catch(BusinessLogicException e)
        {
            Assert.assertNotNull(cuartoPersistence.find(cuarto.getId()));
        }
    }

    @Test
    public void getCuartoSinAsociacion(){
        for(ViviendaEntity vivienda : viviendasData){
            CuartoEntity cuarto = factory.manufacturePojo(CuartoEntity.class);
            try {
                cuartoLogic.addCuarto((long)23423, cuarto);
                cuartoLogic.getCuarto(vivienda.getId(), cuarto.getId());
                Assert.fail("El cuarto no pertenece a la vivienda");
            } catch (BusinessLogicException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void getCuartoNull(){
        try {
            insertarCuartos();
            ViviendaEntity vivienda = viviendaLogic.getViviendas().get(0);
            List<CuartoEntity> cuartos = cuartoLogic.getCuartos(vivienda.getId());
            CuartoEntity cuartoNull = cuartoLogic.getCuarto((long)2342344, cuartos.get(0).getId());
            Assert.assertNull(cuartoNull);
            cuartoNull = cuartoLogic.getCuarto(vivienda.getId(), (long)2342343);
            Assert.assertNull(cuartoNull);
        } catch (BusinessLogicException e) {
            e.printStackTrace();
            Assert.fail("debería devolver null en vez de fallar");
        }
    }
    
    @Test
    public void actualizarCuarto(){
        ViviendaEntity vivienda = viviendaLogic.getViviendas().get(0);
        CuartoEntity cuarto = factory.manufacturePojo(CuartoEntity.class);      
        cuarto.setCostoArriendo(1000);
        try {
            CuartoEntity cuartoAux = cuartoLogic.addCuarto(vivienda.getId(), cuarto);
            
            cuarto = cuartoLogic.getCuarto(vivienda.getId(), cuartoAux.getId());
            cuarto.setCostoArriendo(0);
            cuartoLogic.actualizarCuarto(vivienda.getId(), cuarto.getId(), cuarto);
            Assert.fail("debería  lanzar una excepccion");
        } catch (BusinessLogicException e) {
            Assert.assertEquals("El cuarto debe tener un costo de arriendo", e.getMessage());
        }
        
        cuarto = factory.manufacturePojo(CuartoEntity.class);       
        cuarto.setCostoArriendo(1000);
        try {
            CuartoEntity cuartoAux = cuartoLogic.addCuarto(vivienda.getId(), cuarto);
           
            cuarto = cuartoLogic.getCuarto(vivienda.getId(), cuartoAux.getId());
            cuartoLogic.actualizarCuarto(vivienda.getId(), cuarto.getId(), cuarto);
            Assert.assertEquals(cuartoAux.getNombre(), cuarto.getNombre());
        } catch (BusinessLogicException e) {
            Assert.fail("debería  lanzar una excepccion");
        }
    }
    
    @Test
    public void deleteCuarto(){
        ViviendaEntity vivienda = viviendaLogic.getViviendas().get(0);

        try {        
            cuartoLogic.deleteCuarto(Long.MIN_VALUE);
            Assert.fail("debería  lanzar una excepccion");
        } catch (BusinessLogicException e) {
            Assert.assertEquals("El cuarto no existe", e.getMessage());
        }
        
        CuartoEntity cuarto = factory.manufacturePojo(CuartoEntity.class);    
        try {
            CuartoEntity cuartoAux = cuartoLogic.addCuarto(vivienda.getId(), cuarto);
           
            cuartoLogic.deleteCuarto(cuarto.getId());
            cuarto = cuartoLogic.getCuarto(vivienda.getId(), cuartoAux.getId());
            Assert.assertNull(cuarto);
        } catch (BusinessLogicException e) {
            Assert.fail("debería  lanzar una excepccion");
        }
        
        cuartoLogic.generarCuartos(vivienda.getId());
    }
    
}