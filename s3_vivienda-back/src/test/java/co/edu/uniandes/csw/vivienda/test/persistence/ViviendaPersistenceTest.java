/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.vivienda.test.persistence;

import co.edu.uniandes.csw.vivienda.entities.ContratoEntity;
import co.edu.uniandes.csw.vivienda.entities.CuartoEntity;
import co.edu.uniandes.csw.vivienda.entities.ViviendaEntity;
import co.edu.uniandes.csw.vivienda.persistence.ViviendaPersistence;
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
 * @author Daniel Giraldo
 */
@RunWith(Arquillian.class)
public class ViviendaPersistenceTest {
    @Inject
    private ViviendaPersistence persistence;
    
    @PersistenceContext
    private EntityManager em;
    
    @Inject
    UserTransaction utx;
    
    private List<ViviendaEntity> data = new ArrayList<ViviendaEntity>();
    
    private List<CuartoEntity> dataCuarto = new ArrayList<>();
    
    private List<ContratoEntity> dataContrato = new ArrayList<>();
    
        @Deployment
        public static JavaArchive createDeployement(){
            return ShrinkWrap.create(JavaArchive.class)
                    .addPackage(ViviendaEntity.class.getPackage())
                    .addPackage(ViviendaPersistence.class.getPackage())
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
    
    private void clearData()     {
        em.createQuery("delete from ViviendaEntity").executeUpdate();
    }
    
    private void insertData(){
        PodamFactory factory = new PodamFactoryImpl();
        for (int i = 0; i < 5; i++) {
            ViviendaEntity entity = factory.manufacturePojo(ViviendaEntity.class);
            em.persist(entity);
            data.add(entity);
        }
        
        for(int i = 0; i<2;i++)
        {
            CuartoEntity cuarto = factory.manufacturePojo(CuartoEntity.class);
            em.persist(cuarto);
            dataCuarto.add(cuarto);
        }
        for(int i = 0; i<2;i++)
        {
            ContratoEntity contrato = factory.manufacturePojo(ContratoEntity.class);
            em.persist(contrato);
            dataContrato.add(contrato);
        }
    }

    @Test
    public void createViviendaTest(){
        PodamFactory factory = new PodamFactoryImpl();
        ViviendaEntity ent = factory.manufacturePojo(ViviendaEntity.class);
        ent.setCuartos(dataCuarto);
        ent.setContratos(dataContrato);
        ViviendaEntity result = persistence.create(ent);
        Assert.assertNotNull(result);
        ViviendaEntity entity = em.find(ViviendaEntity.class, result.getId());
        Assert.assertEquals(entity.getNombre(), result.getNombre());
        Assert.assertEquals(entity.getServiciosIncluidos(), result.getServiciosIncluidos());
        Assert.assertEquals(entity.getImgUrl(), result.getImgUrl());
        Assert.assertEquals(entity.getArrendador(), result.getArrendador());
        Assert.assertEquals(ent.getContratos().size(), result.getContratos().size());
    }

    @Test
    public void getViviendasTest(){
        List<ViviendaEntity> listResult = persistence.findAll();
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
            ViviendaEntity found = persistence.find(entity.getId());
            Assert.assertNotNull(found);
            Assert.assertEquals(found.getId(), entity.getId());
            Assert.assertEquals(found.getNombre(), entity.getNombre());
        }
    }

    @Test
    public void deleteViviendaTest(){
        for(ViviendaEntity entity: data){
            persistence.delete(entity.getId());
            ViviendaEntity deleted = em.find(ViviendaEntity.class, entity.getId());
            Assert.assertNull(deleted);
        }
        Assert.assertEquals( 0, em.createQuery("select u from ViviendaEntity u", ViviendaEntity.class)
                .getResultList().size());
    }

    @Test
    public void updateViviendaTest(){
        List<ViviendaEntity> viviendas = persistence.findAll();
        for(ViviendaEntity entity: viviendas){
            Long id = entity.getId();
            ViviendaEntity entityUpdate = new ViviendaEntity();
            entityUpdate.setId(id);
            entityUpdate.setNombre(Long.toString(id));
            persistence.update(entityUpdate);
        }

        List<ViviendaEntity> viviendas2 = persistence.findAll();
        for (ViviendaEntity entity: viviendas2){
            Assert.assertEquals(entity.getNombre(), Long.toString(entity.getId()));
        }
    }

    @Test
    public void buscarDireccionTest(){
        for(ViviendaEntity entity : data){
            String ciudad = entity.getCiudad();
            String direccion = entity.getDireccion();
            ViviendaEntity resultado = persistence.buscarPorDireccion(ciudad, direccion);
            Assert.assertNotNull(resultado);
            Assert.assertEquals(resultado.getId(), entity.getId());
        }
        String ciudadNoExistente = "293jfaenj89jawr90j2330";
        String direccionNoExistente = "2930jai0 j09j23 ewj02  2390j23 2340j23 23oij4p2'1 mepom 23";
        Assert.assertNull(persistence.buscarPorDireccion(ciudadNoExistente, direccionNoExistente));
    }
    
    @Test
    public void deleteAllTest(){
        PodamFactory factory = new PodamFactoryImpl();
        for(int i=0;i<3;i++)
        {
            ViviendaEntity ent = factory.manufacturePojo(ViviendaEntity.class);
            ViviendaEntity result = persistence.create(ent);
            Assert.assertNotNull(result);
        }
        
        Assert.assertEquals(8,persistence.findAll().size());
        persistence.deleteAll();
        Assert.assertEquals(0,persistence.findAll().size());
    }
}
