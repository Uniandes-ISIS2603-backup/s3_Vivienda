/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.vivienda.test.logic;

import co.edu.uniandes.csw.vivienda.ejb.ContratoLogic;
import co.edu.uniandes.csw.vivienda.ejb.EstudianteLogic;
import co.edu.uniandes.csw.vivienda.entities.ContratoEntity;
import co.edu.uniandes.csw.vivienda.entities.EstudianteEntity;
import co.edu.uniandes.csw.vivienda.entities.CalificacionEntity;
import co.edu.uniandes.csw.vivienda.entities.UniversidadEntity;
import co.edu.uniandes.csw.vivienda.entities.ViviendaEntity;
import co.edu.uniandes.csw.vivienda.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.vivienda.persistence.EstudiantePersistence;
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
 * @estudiante estudiante
 */
@RunWith(Arquillian.class)
public class EstudianteLogicTest {

    private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private EstudianteLogic estudianteLogic;
    
    @Inject
    private ContratoLogic contratoLogic;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private List<EstudianteEntity> data = new ArrayList<>();
    
    private List<UniversidadEntity> dataUniversidad = new ArrayList<>();
    
    private List<ContratoEntity> dataContrato = new ArrayList<>();
    
    private List<ViviendaEntity> dataVivienda = new ArrayList<>();

    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(EstudianteEntity.class.getPackage())
                .addPackage(EstudianteLogic.class.getPackage())
                .addPackage(EstudiantePersistence.class.getPackage())
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
        em.createQuery("delete from CalificacionEntity").executeUpdate();
        em.createQuery("delete from ContratoEntity").executeUpdate();
        em.createQuery("delete from ViviendaEntity").executeUpdate();
        
        em.createQuery("delete from EstudianteEntity").executeUpdate();
        em.createQuery("delete from UniversidadEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        for (int i = 0; i < 3; i++) {
            ContratoEntity contrato = factory.manufacturePojo(ContratoEntity.class);
            UniversidadEntity universidad = factory.manufacturePojo(UniversidadEntity.class);
            EstudianteEntity entity = factory.manufacturePojo(EstudianteEntity.class);
            ViviendaEntity vivienda = factory.manufacturePojo(ViviendaEntity.class);
            
            //entity.setContrato(contrato);
            contrato.setEstudiante(entity);
            contrato.setVivienda(vivienda);
            
            em.persist(contrato); 
            em.persist(universidad);
            em.persist(entity);
            em.persist(vivienda);
            
            dataUniversidad.add(universidad); 
            dataContrato.add(contrato);
            dataVivienda.add(vivienda);
            data.add(entity);
            
            
            entity.setUniversidad(dataUniversidad.get(0));
            dataUniversidad.get(0).getEstudiantes().add(entity);
        }

        ContratoEntity contrato = factory.manufacturePojo(ContratoEntity.class);
        ViviendaEntity vivienda = factory.manufacturePojo(ViviendaEntity.class);
        contrato.setVivienda(vivienda);
        em.persist(contrato); 
        em.persist(vivienda);
        dataContrato.add(contrato);
        dataVivienda.add(vivienda);
        
        EstudianteEntity estudiante = data.get(2);
        CalificacionEntity entity = factory.manufacturePojo(CalificacionEntity.class);
        entity.setEstudiante(estudiante);
        em.persist(entity);
        estudiante.getCalificaciones().add(entity);
    }
    
    private void esIgual(EstudianteEntity entity, EstudianteEntity newEntity){
        Assert.assertEquals(newEntity.getId(), entity.getId());
        Assert.assertEquals(newEntity.getNombre(), entity.getNombre());
        Assert.assertEquals(newEntity.getLogin(), entity.getLogin());
        Assert.assertEquals(newEntity.getPassword(), entity.getPassword());
        
        // Universidad
        if (entity.getUniversidad()!= null && newEntity.getUniversidad() != null){
            Assert.assertEquals(newEntity.getUniversidad().getId(), entity.getUniversidad().getId());
            Assert.assertEquals(newEntity.getUniversidad().getLatitud(), entity.getUniversidad().getLatitud());
            Assert.assertEquals(newEntity.getUniversidad().getLongitud(), entity.getUniversidad().getLongitud());
            Assert.assertEquals(newEntity.getUniversidad().getNombre(), entity.getUniversidad().getNombre());
        }
        
        // Calificaciones
        if (entity.getCalificaciones()!= null && newEntity.getCalificaciones() != null){
            Assert.assertEquals(newEntity.getCalificaciones().size(), entity.getCalificaciones().size());
            for (CalificacionEntity ent: entity.getCalificaciones()){
                boolean encontrado = false;
                for (CalificacionEntity ent2: newEntity.getCalificaciones()){
                    if (ent.getId().equals(ent2.getId()))
                        encontrado = true;
                }
                 Assert.assertTrue(encontrado);
            }
        }
    }
    
    /**
     * Prueba para crear un Estudiante.
     * @throws co.edu.uniandes.csw.vivienda.exceptions.BusinessLogicException
     */
    @Test
    public void createEstudianteTest() throws BusinessLogicException{
        EstudianteEntity newEntity = factory.manufacturePojo(EstudianteEntity.class);
        newEntity.setUniversidad(dataUniversidad.get(0));
        EstudianteEntity result = estudianteLogic.createEstudiante(newEntity);
        Assert.assertNotNull(result);
        EstudianteEntity entity = em.find(EstudianteEntity.class, result.getId());
        
        esIgual(entity, newEntity);
    }

    @Test(expected = BusinessLogicException.class)
    public void createEstudianteTestConLoginExistente() throws BusinessLogicException{
        EstudianteEntity newEntity = factory.manufacturePojo(EstudianteEntity.class);
        newEntity.setUniversidad(dataUniversidad.get(1));
        newEntity.setLogin(data.get(1).getLogin());
        estudianteLogic.createEstudiante(newEntity);
    }
    
    @Test(expected = BusinessLogicException.class)
    public void createEstudianteTestSinUniversidad() throws BusinessLogicException{
        EstudianteEntity newEntity = factory.manufacturePojo(EstudianteEntity.class);
        estudianteLogic.createEstudiante(newEntity);
    }
    
    @Test(expected = BusinessLogicException.class)
    public void createEstudianteTestConUniversidadInexistente() throws BusinessLogicException{
        EstudianteEntity newEntity = factory.manufacturePojo(EstudianteEntity.class);
        UniversidadEntity universidad = factory.manufacturePojo(UniversidadEntity.class);
        newEntity.setUniversidad(universidad);
        estudianteLogic.createEstudiante(newEntity);
    }
    
    @Test(expected = BusinessLogicException.class)
    public void createEstudianteTestSinLogin() throws BusinessLogicException{
        EstudianteEntity newEntity = factory.manufacturePojo(EstudianteEntity.class);
        newEntity.setUniversidad(dataUniversidad.get(0));
        newEntity.setLogin(null);
        estudianteLogic.createEstudiante(newEntity);
    }
    
    /**
     * Prueba para consultar la lista de Estudiantes.
     */
    @Test
    public void getEstudiantesTest() {
        List<EstudianteEntity> list =  estudianteLogic.getEstudiantes();

        Assert.assertEquals(data.size(), list.size());
        for (EstudianteEntity entity : list) {
            boolean found = false;
            for (EstudianteEntity storedEntity : data) {
                if (entity.getId().equals(storedEntity.getId()))
                    found = true;
            }
            Assert.assertTrue(found);
        }
    }

    /**
     * Prueba para consultar un Estudiante.
     */
    @Test
    public void getEstudianteTest() {
        EstudianteEntity entity = data.get(0);
        EstudianteEntity resultEntity = estudianteLogic.getEstudiante(entity.getId());
        Assert.assertNotNull(resultEntity);
        esIgual(entity, resultEntity);
    }

    /**
     * Prueba para actualizar un Estudiante.
     * @throws co.edu.uniandes.csw.vivienda.exceptions.BusinessLogicException
     */
    @Test
    public void updateEstudianteTest() throws BusinessLogicException {
        EstudianteEntity entity = data.get(0);
        EstudianteEntity pojoEntity = factory.manufacturePojo(EstudianteEntity.class);
        pojoEntity.setUniversidad(dataUniversidad.get(1));
        dataUniversidad.get(1).getEstudiantes().add(pojoEntity);
        pojoEntity.setId(entity.getId());

        estudianteLogic.updateEstudiante(pojoEntity.getId(), pojoEntity);
        EstudianteEntity resp = em.find(EstudianteEntity.class, entity.getId());
        
        Assert.assertTrue(resp.getUniversidad()!= null && pojoEntity.getUniversidad() != null);
        esIgual(pojoEntity, resp);
    }
    
    @Test(expected = BusinessLogicException.class)
    public void updateEstudianteTestConLoginExistente() throws BusinessLogicException {
        EstudianteEntity pojoEntity = factory.manufacturePojo(EstudianteEntity.class);
        pojoEntity.setLogin(data.get(1).getLogin());
        pojoEntity.setId(data.get(0).getId());

        estudianteLogic.updateEstudiante(pojoEntity.getId(), pojoEntity);
        
    }
    
    @Test(expected = BusinessLogicException.class)
    public void updateEstudianteTestConUniversidadInexistente() throws BusinessLogicException {
        EstudianteEntity pojoEntity = factory.manufacturePojo(EstudianteEntity.class);
        UniversidadEntity universidad = factory.manufacturePojo(UniversidadEntity.class);
        pojoEntity.setUniversidad(universidad);
        pojoEntity.setId(data.get(0).getId());

        estudianteLogic.updateEstudiante(pojoEntity.getId(), pojoEntity);
    }
    

    @Test
    public void deleteEstudianteTest() throws BusinessLogicException {
        EstudianteEntity entity = data.get(0);
        estudianteLogic.deleteEstudiante(entity.getId());
        EstudianteEntity deleted = em.find(EstudianteEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }
    
    @Test
    public void replaceUniversidadTest() throws BusinessLogicException {
        EstudianteEntity entity = data.get(0);
        UniversidadEntity universidad = dataUniversidad.get(2);
        Assert.assertNotEquals(entity.getUniversidad().getId(), universidad.getId());
        estudianteLogic.replaceUniversidad(entity.getId(), universidad.getId());
        
        EstudianteEntity replaced = em.find(EstudianteEntity.class, entity.getId());
        Assert.assertNotNull(replaced);
        Assert.assertEquals(replaced.getUniversidad().getId(), universidad.getId());
    }
    
    @Test(expected = BusinessLogicException.class)
    public void replaceUniversidadTestConUniversidadInvalida() throws BusinessLogicException {
        EstudianteEntity entity = data.get(0);
        UniversidadEntity universidad = factory.manufacturePojo(UniversidadEntity.class);
        estudianteLogic.replaceUniversidad(entity.getId(), universidad.getId());
    }
}
