package persistence;

import co.edu.uniandes.csw.vivienda.entities.CuartoEntity;
import co.edu.uniandes.csw.vivienda.entities.ViviendaEntity;
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

@RunWith(Arquillian.class)
public class CuartoPersistenceTest {

    @Inject
    private CuartoPersistence persistence;

    @PersistenceContext
    private EntityManager em;

    @Inject
    UserTransaction utx;

    private List<CuartoEntity> data = new ArrayList<>();

    @Deployment
    public static JavaArchive createDeployement(){
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(CuartoEntity.class.getPackage())
                .addPackage(CuartoPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }

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

    private void insertData(){
        PodamFactory factory = new PodamFactoryImpl();
        for (int i = 0; i < 10; i++) {
            CuartoEntity cuartoEntity = factory.manufacturePojo(CuartoEntity.class);
            em.persist(cuartoEntity);
            data.add(cuartoEntity);
        }
    }

    private void clearData(){
        em.createQuery("delete from CuartoEntity").executeUpdate();
    }

    @Test
    public void createCuartoTest(){
        PodamFactory factory = new PodamFactoryImpl();
        CuartoEntity cuarto = factory.manufacturePojo(CuartoEntity.class);

        CuartoEntity result = persistence.create(cuarto);
        Assert.assertNotNull(result);
        Assert.assertEquals(result, cuarto);
    }

    @Test
    public void updateCuartoTest(){
        for (CuartoEntity cuarto : data){
            cuarto.setNombre(Long.toString(cuarto.getId()));
            persistence.update(cuarto);

            CuartoEntity updated = em.find(CuartoEntity.class, cuarto.getId());
            Assert.assertEquals(updated.getNombre(), Long.toString(updated.getId()));
        }
    }

    @Test
    public void deleteCuartoTest(){
        for (CuartoEntity entity : data){
            boolean deleted = persistence.delete(entity.getId());
            Assert.assertTrue(deleted);
            CuartoEntity cuartoDeleted = em.find(CuartoEntity.class, entity.getId());
            Assert.assertNull(cuartoDeleted);
        }
        Assert.assertEquals(em.createQuery("select u from CuartoEntity u", CuartoEntity.class).getResultList().size(), 0);
    }
}
