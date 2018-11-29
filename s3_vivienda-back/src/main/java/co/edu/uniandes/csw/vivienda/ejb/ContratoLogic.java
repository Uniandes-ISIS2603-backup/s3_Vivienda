/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.vivienda.ejb;

import co.edu.uniandes.csw.vivienda.entities.ContratoEntity;
import co.edu.uniandes.csw.vivienda.entities.CuartoEntity;
import co.edu.uniandes.csw.vivienda.entities.EstudianteEntity;
import co.edu.uniandes.csw.vivienda.entities.ViviendaEntity;
import co.edu.uniandes.csw.vivienda.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.vivienda.persistence.ContratoPersistence;
import co.edu.uniandes.csw.vivienda.persistence.CuartoPersistence;
import co.edu.uniandes.csw.vivienda.persistence.EstudiantePersistence;
import co.edu.uniandes.csw.vivienda.persistence.ViviendaPersistence;

import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * @author estudiante
 */
@Stateless
public class ContratoLogic {

    private static final Logger LOGGER = Logger.getLogger(ContratoLogic.class.getName());

    @Inject
    private ContratoPersistence persistence;

    @Inject
    private ViviendaPersistence viviendaPersistence;

    @Inject
    private EstudiantePersistence estudiantePersistence;

    @Inject
    private CuartoPersistence cuartoPersistence;
    
    /**
     * Guardar un nuevo contrato
     *
     * @param contratoEntity La entidad de tipo contrato del nuevo contrato a
     * persistir.
     * @return La entidad luego de persistirla
     * @throws BusinessLogicException Si el metodoPago es inválido o ya existe
     * en la persistencia.
     */
    public ContratoEntity createContrato(ContratoEntity contratoEntity) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de creación del contrato");
        if (contratoEntity.getVivienda() == null || viviendaPersistence.find(contratoEntity.getVivienda().getId()) == null) {
            throw new BusinessLogicException("La vivienda es inválida");
        }
        if (!validateMetodoPago(contratoEntity.getMetodoPago())) {
            throw new BusinessLogicException("El metodoPago es inválido");
        }
        persistence.create(contratoEntity);
        LOGGER.log(Level.INFO, "Termina proceso de creación del contrato");
        return contratoEntity;
    }

    /**
     * Devuelve todos los contratos que hay en la base de datos.
     *
     * @return Lista de entidades de tipo contrato.
     */
    public List<ContratoEntity> getContratos() {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todos los contratos");
        List<ContratoEntity> contratos = persistence.findAll();
        LOGGER.log(Level.INFO, "Termina proceso de consultar todos los contratos");
        return contratos;
    }

    /**
     * Busca un contrato por ID
     *
     * @param contratoId El id del contrato a buscar
     * @return El contrato encontrado, null si no lo encuentra.
     */
    public ContratoEntity getContrato(Long contratoId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar el contrato con id = {0}", contratoId);
        ContratoEntity contratoEntity = persistence.find(contratoId);
        if (contratoEntity == null) {
            LOGGER.log(Level.SEVERE, "El contrato con el id = {0} no existe", contratoId);
        }
        LOGGER.log(Level.INFO, "Termina proceso de consultar el contrato con id = {0}", contratoId);
        return contratoEntity;
    }

    /**
     * Actualizar un contrato por ID
     *
     * @param contratoId El ID del contrato a actualizar
     * @param contratoEntity La entidad del contrato con los cambios deseados
     * @return La entidad del contrato luego de actualizarla
     * @throws BusinessLogicException Si el metodoPago de la actualizacion es
     * invalido
     */
    public ContratoEntity updateContrato(Long contratoId, ContratoEntity contratoEntity) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar el contrato con id = {0}", contratoId);
        if (!validateMetodoPago(contratoEntity.getMetodoPago())) {
            throw new BusinessLogicException("El metodoPago es inválido");
        }
        ContratoEntity newContrato = null;
        if (getContrato(contratoId) != null) {
            newContrato = persistence.update(contratoEntity);
        }
        LOGGER.log(Level.INFO, "Termina proceso de actualizar el contrato con id = {0}", contratoEntity.getId());
        return newContrato;
    }
    
        public ContratoEntity actualizarContrato(Long viviendaId, Long contratoId, ContratoEntity contratoEntity) throws BusinessLogicException {
        if (contratoEntity.getMetodoPago() != null && contratoEntity.getMetodoPago() <= 0) {
            throw new BusinessLogicException("El metodoPago debe ser mayor a 0");
        }
        ViviendaEntity vivienda = viviendaPersistence.find(viviendaId);
        contratoEntity.setVivienda(vivienda);
        contratoEntity.setId(contratoId);
        persistence.update(contratoEntity);
        return contratoEntity;
    }

    /**
     * Eliminar un contrato por ID
     *
     * @param contratoId El ID del contrato a eliminar
     * @throws co.edu.uniandes.csw.vivienda.exceptions.BusinessLogicException
     */
    public void deleteContrato(Long contratoId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar el contrato con id = {0}", contratoId);
        if (persistence.find(contratoId) == null) {
            throw new BusinessLogicException("El contrato no existe");
        }
        persistence.delete(contratoId);
        LOGGER.log(Level.INFO, "Termina proceso de borrar el contrato con id = {0}", contratoId);
    }

    /**
     * Verifica que el metodoPago no sea invalido.
     *
     * @param metodoPago a verificar
     * @return true si el metodoPago es valido.
     */
    private boolean validateMetodoPago(Integer metodoPago) {
        return metodoPago > 0;
    }

    public void generarDatos() {
        List<ContratoEntity> contratosViejos = getContratos();

        for (ContratoEntity co : contratosViejos) {
            try {
                deleteContrato(co.getId());
            } catch (BusinessLogicException e) {
                LOGGER.log(Level.INFO, "Error en el proceso de borrar el contrato de la vivienda con id = {0}", co.getId());
            }
        }

        List<ViviendaEntity> viviendas = viviendaPersistence.findAll();
        List<EstudianteEntity> estudiantes = estudiantePersistence.findAll();
        Random rand = new Random();
        for (int i = 0; i < estudiantes.size(); i++) {
            ContratoEntity c = new ContratoEntity();
            c.setMetodoPago(rand.nextInt(100));
            int anio = rand.nextInt(5) + 2011;
            int mes = rand.nextInt(12);
            int dia = rand.nextInt(28);
            c.setFechaInicio(anio + "-" + mes + "-" + dia + "");
            c.setFechaFin((anio + 2) + "-" + mes + "-" + dia + "");
            
            ViviendaEntity vivienda = viviendas.get(rand.nextInt(viviendas.size()));
            c.setVivienda(vivienda);
            c.setArrendador(vivienda.getArrendador());
            
            List<CuartoEntity> cuartos = vivienda.getCuartos();
            Iterator iter = cuartos.iterator();
            boolean agrego = false;
            while(iter.hasNext() && !agrego)
            {
                CuartoEntity cuarto = (CuartoEntity) iter.next();
                if(!cuarto.isOcupado())
                {
                    c.setCuarto(cuarto);
                    cuarto.setOcupado(true);
                    cuartoPersistence.update(cuarto);   
                    agrego = true;
                }
            }
            
            EstudianteEntity estudiante = estudiantes.get(i);
            c.setEstudiante(estudiante);
            
            try {

                    ContratoEntity contrato = createContrato(c);
                    estudiante.setContrato(contrato);
                    estudiantePersistence.update(estudiante);
                
            } catch (BusinessLogicException e) {
                LOGGER.log(Level.INFO, "Hubo un error creando el contrato");
            }
        }

    }

}
