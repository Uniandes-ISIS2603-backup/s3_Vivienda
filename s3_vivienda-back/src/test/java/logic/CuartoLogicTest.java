package logic;

import co.edu.uniandes.csw.vivienda.ejb.CuartoLogic;
import co.edu.uniandes.csw.vivienda.ejb.ViviendaLogic;
import co.edu.uniandes.csw.vivienda.entities.CuartoEntity;
import co.edu.uniandes.csw.vivienda.entities.ViviendaEntity;
import co.edu.uniandes.csw.vivienda.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.vivienda.persistence.CuartoPersistence;
import co.edu.uniandes.csw.vivienda.persistence.ViviendaPersistence;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.sun.tools.xjc.model.CEnumConstant;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import sun.util.resources.en.CurrencyNames_en_AU;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import java.util.ArrayList;
import java.util.List;

@RunWith(Arquillian.class)
public class CuartoLogicTest {

    @Inject
    ViviendaLogic viviendaLogic;

    @Inject
    CuartoLogic cuartoLogic;

    private PodamFactory factory = new PodamFactoryImpl();

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private List<ViviendaEntity> viviendasData = new ArrayList<>();
    private List<CuartoEntity> cuartosData = new ArrayList<>();

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
        for (int i = 0; i < 20; i++) {
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
                } catch (BusinessLogicException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void clearData() {
        em.createQuery("delete from ViviendaEntity").executeUpdate();
    }

    @Test
    public void createCuartoTest(){
        for(ViviendaEntity vivienda : viviendasData){
            CuartoEntity cuarto = factory.manufacturePojo(CuartoEntity.class);
            try {
                CuartoEntity cuartoNuevo = cuartoLogic.addCuarto(vivienda.getId(), cuarto);
                List<CuartoEntity> cuartos = viviendaLogic.getVivienda(vivienda.getId()).getCuartos();
                Assert.assertEquals(cuartos.get(0).getId(), cuartoNuevo.getId());
            } catch (BusinessLogicException e) {
                e.printStackTrace();
                Assert.fail("Debería añadirse el cuarto");
            }
        }
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
            Assert.assertEquals(listaCuartos.size(), 3);
        }

        //get cuartos de una vivienda que no existe
        List<CuartoEntity> listaVacia = cuartoLogic.getCuartos((long)234234233);
        Assert.assertNull(listaVacia);

        //get cuartos de un vivienda sin cuartos
        ViviendaEntity viviendaEntity = factory.manufacturePojo(ViviendaEntity.class);
        try {
            viviendaLogic.createVivienda(viviendaEntity);
            List<CuartoEntity> cuartos = cuartoLogic.getCuartos(viviendaEntity.getId());
            Assert.assertEquals(cuartos.size(), 0);
        } catch (BusinessLogicException e) {
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

        //Pruebas con cuartos que existen y viviendas que existen pero que no estan asociados
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

        //Pruebas con cuartos o viviendas que no existen
        try {
            CuartoEntity cuartoNull = cuartoLogic.getCuarto((long)2342344, idCuartoExistente);
            Assert.assertNull(cuartoNull);
            cuartoNull = cuartoLogic.getCuarto(idViviendaExistente, (long)2342343);
            Assert.assertNull(cuartoNull);
        } catch (BusinessLogicException e) {
            e.printStackTrace();
            Assert.fail("debería devolver null en vez de fallar");
        }
    }

}