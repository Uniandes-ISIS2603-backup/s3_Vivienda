/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.vivienda.test.logic;

import co.edu.uniandes.csw.vivienda.ejb.CalificacionLogic;
import co.edu.uniandes.csw.vivienda.entities.EstudianteEntity;
import co.edu.uniandes.csw.vivienda.entities.CalificacionEntity;
import co.edu.uniandes.csw.vivienda.entities.UniversidadEntity;
import co.edu.uniandes.csw.vivienda.entities.ViviendaEntity;
import co.edu.uniandes.csw.vivienda.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.vivienda.persistence.CalificacionPersistence;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
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
 * @author calificacion
 */
@RunWith(Arquillian.class)
public class CalificacionLogicTest {
    private final static float MAX_PUNTAJE = 5;
    
    private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private CalificacionLogic calificacionLogic;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private List<CalificacionEntity> data = new ArrayList<>();
    
    private List<EstudianteEntity> dataEstudiante = new ArrayList<>();
    
    private List<ViviendaEntity> dataVivienda = new ArrayList<>();
    
    private Random r = new Random();

    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(CalificacionEntity.class.getPackage())
                .addPackage(CalificacionLogic.class.getPackage())
                .addPackage(CalificacionPersistence.class.getPackage())
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
        em.createQuery("delete from EstudianteEntity").executeUpdate();
        em.createQuery("delete from ViviendaEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        for (int i = 0; i < 3; i++) {
            EstudianteEntity estudiante = factory.manufacturePojo(EstudianteEntity.class);
            ViviendaEntity vivienda = factory.manufacturePojo(ViviendaEntity.class);
            CalificacionEntity entity = factory.manufacturePojo(CalificacionEntity.class);
            entity.setPuntaje(r.nextFloat()*MAX_PUNTAJE);
            dataEstudiante.add(estudiante);
            dataVivienda.add(vivienda);
            data.add(entity);
            
            entity.setEstudiante(estudiante);
            estudiante.getCalificaciones().add(entity);
            
            entity.setVivienda(vivienda);
            vivienda.setCalificaciones(new ArrayList<>());
            //dataVivienda.get(0).getCalificaciones().add(entity);
            
            em.persist(estudiante); 
            em.persist(entity);
            em.persist(vivienda);
        }
    }

    /**
    private void printList (List<CalificacionEntity> lista){
        for (CalificacionEntity e : lista){
            String s = "";
            s += (e.getUniversidad() != null) ? e.getUniversidad().getId(): "NULL";
            s += "\nCONTRATO: ";
            s += (e.getEstudiante()!= null) ? e.getEstudiante().getId(): "NULL";
                 
            System.out.println("ID: " + e.getId() + "\nLOGIN: "+ e.getLogin()+"\nUNIVERSIDAD: "+ s);
        }
    }*/
    
    private void esIgual(CalificacionEntity entity, CalificacionEntity newEntity){
        Assert.assertEquals(newEntity.getId(), entity.getId());
        Assert.assertEquals(newEntity.getDescripcion(), entity.getDescripcion());
        Assert.assertEquals(newEntity.getPuntaje(), entity.getPuntaje());
        
        // Estudiante
        if (entity.getEstudiante() != null && newEntity.getEstudiante() != null){
            EstudianteEntity est1 = entity.getEstudiante();
            EstudianteEntity est2 = newEntity.getEstudiante();
            Assert.assertEquals(est1.getId(), est2.getId());
            Assert.assertEquals(est1.getNombre(), est2.getNombre());
            Assert.assertEquals(est1.getLogin(), est2.getLogin());
            Assert.assertEquals(est1.getPassword(), est2.getPassword());
        }
        
        // Vivienda
        if (entity.getVivienda()!= null && newEntity.getVivienda() != null){
            ViviendaEntity viv1 = entity.getVivienda();
            ViviendaEntity viv2 = newEntity.getVivienda();
            Assert.assertEquals(viv1.getId(), viv2.getId());
            Assert.assertEquals(viv1.getNombre(), viv2.getNombre());
            Assert.assertEquals(viv1.getCiudad(), viv2.getCiudad());
            Assert.assertEquals(viv1.getDescripcion(), viv2.getDescripcion());
            Assert.assertEquals(viv1.getDireccion(), viv2.getDireccion());
            Assert.assertEquals(viv1.getLatitud(), viv2.getLatitud());
            Assert.assertEquals(viv1.getLongitud(), viv2.getLongitud());
            Assert.assertEquals(viv1.getTipo(), viv2.getTipo());
        }
    }
    
    /**
     * Prueba para crear un Calificacion.
     * @throws co.edu.uniandes.csw.vivienda.exceptions.BusinessLogicException
     */
    @Test
    public void createCalificacionTest() throws BusinessLogicException{
        CalificacionEntity newEntity = factory.manufacturePojo(CalificacionEntity.class);
        newEntity.setPuntaje(r.nextFloat()*MAX_PUNTAJE);
        newEntity.setEstudiante(dataEstudiante.get(0));
        CalificacionEntity result = calificacionLogic.createCalificacion(dataVivienda.get(1).getId(), newEntity);
        Assert.assertNotNull(result);
        CalificacionEntity entity = em.find(CalificacionEntity.class, result.getId());
        
        esIgual(entity, newEntity);
    }

    @Test(expected = BusinessLogicException.class)
    public void createCalificacionTestSinEstudiante() throws BusinessLogicException{
        CalificacionEntity newEntity = factory.manufacturePojo(CalificacionEntity.class);
        newEntity.setPuntaje(r.nextFloat()*MAX_PUNTAJE);
        calificacionLogic.createCalificacion(dataVivienda.get(2).getId(), newEntity);
    }
    
    @Test(expected = BusinessLogicException.class)
    public void createCalificacionTestConEstudianteInexistente() throws BusinessLogicException{
        CalificacionEntity newEntity = factory.manufacturePojo(CalificacionEntity.class);
        newEntity.setPuntaje(r.nextFloat()*MAX_PUNTAJE);
        EstudianteEntity estudiante = factory.manufacturePojo(EstudianteEntity.class);
        newEntity.setEstudiante(estudiante);
        calificacionLogic.createCalificacion(dataVivienda.get(2).getId(), newEntity);
    }
    
    
    @Test(expected = BusinessLogicException.class)
    public void createCalificacionTestConViviendaInexistente() throws BusinessLogicException{
        CalificacionEntity newEntity = factory.manufacturePojo(CalificacionEntity.class);
        newEntity.setPuntaje(r.nextFloat()*MAX_PUNTAJE);
        ViviendaEntity vivienda = factory.manufacturePojo(ViviendaEntity.class);
        newEntity.setEstudiante(dataEstudiante.get(1));
        calificacionLogic.createCalificacion(vivienda.getId(), newEntity);
    }
    
    @Test(expected = BusinessLogicException.class)
    public void createCalificacionTestConPuntajeInvalido() throws BusinessLogicException{
        CalificacionEntity newEntity = factory.manufacturePojo(CalificacionEntity.class);
        newEntity.setEstudiante(dataEstudiante.get(2));
        newEntity.setPuntaje(r.nextFloat()*MAX_PUNTAJE+6);
        calificacionLogic.createCalificacion(dataVivienda.get(2).getId(), newEntity);
    }
    
    /**
     * Prueba para consultar la lista de Calificacions.
     */
    @Test
    public void getCalificacionesTest() {
        List<CalificacionEntity> list =  calificacionLogic.getCalificaciones();
        Assert.assertEquals(data.size(), list.size());
        for (CalificacionEntity entity : list) {
            boolean found = false;
            for (CalificacionEntity storedEntity : data) {
                if (entity.getId().equals(storedEntity.getId()))
                    found = true;
            }
            Assert.assertTrue(found);
        }
    }
    
    /**
     * Prueba para consultar la lista de Calificaciones de estudiante.
     */
    @Test
    public void getCalificacionesEstudianteTest() {
        List<CalificacionEntity> list =  calificacionLogic.getCalificacionesEstudiante(dataEstudiante.get(0).getId());
        Assert.assertEquals(1, list.size());
        esIgual(list.get(0), data.get(0));
    }
    
    /**
     * Prueba para consultar la lista de Calificaciones de vivienda.
     */
    @Test
    public void getCalificacionesViviendaTest() {
        List<CalificacionEntity> list =  calificacionLogic.getCalificacionesVivienda(dataVivienda.get(0).getId());
        Assert.assertEquals(1, list.size());
        esIgual(list.get(0), data.get(0));
    }

    /**
     * Prueba para consultar un Calificacion.
     */
    @Test
    public void getCalificacionTest() {
        CalificacionEntity entity = data.get(0);
        CalificacionEntity resultEntity = calificacionLogic.getCalificacion(entity.getId());
        Assert.assertNotNull(resultEntity);
        esIgual(entity, resultEntity);
    }
    
    /**
     * Prueba para consultar un Calificacion de estudiante.
     */
    @Test
    public void getCalificacionEstudianteTest() throws BusinessLogicException {
        CalificacionEntity entity = data.get(0);
        CalificacionEntity resultEntity = calificacionLogic.getCalificacionEstudiante(dataEstudiante.get(0).getId(), entity.getId());
        Assert.assertNotNull(resultEntity);
        esIgual(entity, resultEntity);
    }
    
    /**
     * Prueba para consultar un Calificacion de estudiante.
     */
    @Test(expected = BusinessLogicException.class)
    public void getCalificacionEstudianteTestConEstudianteInvalido() throws BusinessLogicException {
        CalificacionEntity entity = data.get(0);
        CalificacionEntity resultEntity = calificacionLogic.getCalificacionEstudiante(dataEstudiante.get(1).getId(), entity.getId());
    }
    
    /**
     * Prueba para consultar un Calificacion de estudiante.
     */
    @Test
    public void getCalificacionViviendaTest() throws BusinessLogicException {
        CalificacionEntity entity = data.get(0);
        CalificacionEntity resultEntity = calificacionLogic.getCalificacionVivienda(dataVivienda.get(0).getId(), entity.getId());
        Assert.assertNotNull(resultEntity);
        esIgual(entity, resultEntity);
    }
    
    /**
     * Prueba para consultar un Calificacion de estudiante.
     */
    @Test(expected = BusinessLogicException.class)
    public void getCalificacionViviendaTestConViviendaInvalida() throws BusinessLogicException {
        CalificacionEntity entity = data.get(0);
        CalificacionEntity resultEntity = calificacionLogic.getCalificacionVivienda(dataVivienda.get(1).getId(), entity.getId());
    }
    
    /**
     * Prueba para consultar un Calificacion de estudiante.
     */
    @Test
    public void getCalificacionViviendaEstudiante() throws BusinessLogicException {
        CalificacionEntity entity = data.get(0);
        CalificacionEntity resultEntity = calificacionLogic.getCalificacionViviendaEstudiante(dataVivienda.get(0).getId(), dataEstudiante.get(0).getId());
        Assert.assertNotNull(resultEntity);
        esIgual(entity, resultEntity);
        
        resultEntity = calificacionLogic.getCalificacionViviendaEstudiante(dataVivienda.get(1).getId(), dataEstudiante.get(0).getId());
        Assert.assertNull(resultEntity);
    }

    /**
     * Prueba para actualizar una Calificacion.
     * @throws co.edu.uniandes.csw.vivienda.exceptions.BusinessLogicException
     */
    @Test
    public void updateCalificacionTest() throws BusinessLogicException {
        CalificacionEntity entity = data.get(0);
        CalificacionEntity pojoEntity = factory.manufacturePojo(CalificacionEntity.class);
        pojoEntity.setPuntaje(r.nextFloat()*MAX_PUNTAJE);
        pojoEntity.setId(entity.getId());

        calificacionLogic.updateCalificacion(pojoEntity.getId(), pojoEntity);
        CalificacionEntity resp = em.find(CalificacionEntity.class, entity.getId());

        esIgual(pojoEntity, resp);
    }
    
    @Test(expected = BusinessLogicException.class)
    public void updateCalificacionTestConPuntajeInvalido() throws BusinessLogicException {
        CalificacionEntity pojoEntity = factory.manufacturePojo(CalificacionEntity.class);
        pojoEntity.setPuntaje(r.nextFloat()*MAX_PUNTAJE+6);
        pojoEntity.setId(data.get(0).getId());

        calificacionLogic.updateCalificacion(pojoEntity.getId(), pojoEntity);
    }
    

    @Test
    public void deleteCalificacionTest() throws BusinessLogicException {
        CalificacionEntity entity = data.get(0);
        calificacionLogic.deleteCalificacion(entity.getId());
        CalificacionEntity deleted = em.find(CalificacionEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }
    
    @Test
    public void deleteCalificacionEstudianteTest() throws BusinessLogicException {
        CalificacionEntity entity = data.get(0);
        calificacionLogic.deleteCalificacionEstudiante(dataEstudiante.get(0).getId(), entity.getId());
        CalificacionEntity deleted = em.find(CalificacionEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }
    
    @Test(expected = BusinessLogicException.class)
    public void deleteCalificacionEstudianteTestConEstudianteInvalido() throws BusinessLogicException {
        CalificacionEntity entity = data.get(0);
        calificacionLogic.deleteCalificacionEstudiante(dataEstudiante.get(1).getId(), entity.getId());
    }
    
    @Test
    public void deleteCalificacionViviendaTest() throws BusinessLogicException {
        CalificacionEntity entity = data.get(0);
        calificacionLogic.deleteCalificacionVivienda(dataVivienda.get(0).getId(), entity.getId());
        CalificacionEntity deleted = em.find(CalificacionEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }
    
    @Test(expected = BusinessLogicException.class)
    public void deleteCalificacionViviendaTestConViviendaInvalida() throws BusinessLogicException {
        CalificacionEntity entity = data.get(0);
        calificacionLogic.deleteCalificacionVivienda(dataVivienda.get(1).getId(), entity.getId());
    }
}
    