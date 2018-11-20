package co.edu.uniandes.csw.vivienda.ejb;

import co.edu.uniandes.csw.vivienda.entities.CuartoEntity;
import co.edu.uniandes.csw.vivienda.entities.ViviendaEntity;
import co.edu.uniandes.csw.vivienda.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.vivienda.persistence.CuartoPersistence;
import co.edu.uniandes.csw.vivienda.persistence.ViviendaPersistence;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author: Daniel Giraldo
 */
@Stateless
public class CuartoLogic {

    private static final Logger LOGGER = Logger.getLogger(CuartoLogic.class.getName());

    @Inject
    CuartoPersistence cuartoPersistence;

    @Inject
    ViviendaPersistence viviendaPersistence;

    public CuartoEntity addCuarto(Long viviendaId, CuartoEntity cuartoEntity) throws BusinessLogicException {
        if (cuartoEntity.getCostoArriendo() == null || cuartoEntity.getCostoArriendo() == 0) {
            throw new BusinessLogicException("El cuarto debe tener un costo de arriendo");
        }
        ViviendaEntity vivienda = viviendaPersistence.find(viviendaId);
        if (vivienda == null) {
            throw new BusinessLogicException("No existe la vivienda");
        }
        cuartoEntity.setVivienda(vivienda);
        cuartoPersistence.create(cuartoEntity);
        return cuartoEntity;
    }

    public List<CuartoEntity> getCuartos(Long viviendaId) {
        ViviendaEntity viviendaEntity = viviendaPersistence.find(viviendaId);
        List<CuartoEntity> cuartos = null;
        if (viviendaEntity != null) {
            cuartos = viviendaEntity.getCuartos();
        }
        return cuartos;
    }

    public CuartoEntity getCuarto(Long viviendaId, Long cuartoId) throws BusinessLogicException {
        ViviendaEntity vivienda = viviendaPersistence.find(viviendaId);
        if (vivienda == null) {
            return null;
        }
        CuartoEntity cuarto = cuartoPersistence.find(cuartoId);
        if (cuarto == null) {
            return null;
        }

        List<CuartoEntity> cuartosVivienda = vivienda.getCuartos();
        int index = cuartosVivienda.indexOf(cuarto);
        if (index >= 0) {
            return cuartosVivienda.get(index);
        }
        throw new BusinessLogicException("El cuarto no esta asociado a la vivienda");
    }

    public CuartoEntity actualizarCuarto(Long viviendaId, Long cuartoId, CuartoEntity cuartoEntity) throws BusinessLogicException {
        if (cuartoEntity.getCostoArriendo() != null && cuartoEntity.getCostoArriendo() <= 0) {
            throw new BusinessLogicException("El cuarto debe tener un costo de arriendo");
        }
        ViviendaEntity vivienda = viviendaPersistence.find(viviendaId);
        cuartoEntity.setVivienda(vivienda);
        cuartoEntity.setId(cuartoId);
        cuartoPersistence.update(cuartoEntity);
        return cuartoEntity;
    }

    public void deleteCuarto(Long cuartoId) throws BusinessLogicException {
        if (cuartoPersistence.find(cuartoId) == null) {
            throw new BusinessLogicException("El cuarto no existe");
        }
        cuartoPersistence.delete(cuartoId);
    }

    public void generarCuartos(Long viviendaId) {

        List<CuartoEntity> cuartosViejos = getCuartos(viviendaId);
        for (CuartoEntity c : cuartosViejos) {
            try {
                deleteCuarto(c.getId());
            } catch (BusinessLogicException e) {
                LOGGER.log(Level.INFO, "Error en el proceso de borrar el cuarto de la vivienda con id = {0}", viviendaId);
            }
        }

        int numeroCuartos = new Random().nextInt(3) + 1;
        for (int i = 0; i < numeroCuartos; i++) {
            CuartoEntity cuarto = new CuartoEntity();
            int costoArriendo = (800 + new Random().nextInt(1000)) * 1000;

            cuarto.setNombre("Cuarto " + i);
            cuarto.setCostoArriendo(costoArriendo);
            cuarto.setDescripcion("Un cuarto común");

            try {
                addCuarto(viviendaId, cuarto);
            } catch (BusinessLogicException e) {
                LOGGER.log(Level.INFO, "Error en proceso de agregar el cuarto de la vivienda con id = {0}", viviendaId);
            }
        }
    }
}
