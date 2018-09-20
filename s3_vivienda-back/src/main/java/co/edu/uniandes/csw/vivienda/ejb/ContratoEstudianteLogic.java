/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.vivienda.ejb;

import co.edu.uniandes.csw.vivienda.entities.ContratoEntity;
import co.edu.uniandes.csw.vivienda.entities.EstudianteEntity;
import co.edu.uniandes.csw.vivienda.persistence.ContratoPersistence;
import co.edu.uniandes.csw.vivienda.persistence.EstudiantePersistence;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author carlosinfante
 */
@Stateless
public class ContratoEstudianteLogic {
    
    private static final Logger LOGGER = Logger.getLogger(ContratoViviendaLogic.class.getName());

    @Inject
    private ContratoPersistence contratoPersistence;

    @Inject
    private EstudiantePersistence estudiantePersistence;

    /**
     * Remplazar el estudiante de un contrato.
     *
     * @param contratoId id del contrato que se quiere actualizar.
     * @param estudianteId El id del estudiante que se será del contrato.
     * @return el nuevo contrato.
     */
    public ContratoEntity replaceEstudiante(Long contratoId, Long estudianteId) {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar contrato con id = {0}", contratoId);
        EstudianteEntity estudianteEntity = estudiantePersistence.find(estudianteId);
        ContratoEntity contratoEntity = contratoPersistence.find(contratoId);
        contratoEntity.setEstudiante(estudianteEntity);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar contrato con id = {0}", contratoEntity.getId());
        return contratoEntity;
    }

    /**
     * Borrar un contrato de un estudiante. Este metodo se utiliza para borrar la
     * relacion de un contrato.
     *
     * @param contratoId El contrato que se desea borrar del estudiante.
     */
    public void removeEstudiante(Long contratoId)
    {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar el Estudiante del contrato con id = {0}", contratoId);
        ContratoEntity contratoEntity = contratoPersistence.find(contratoId);
        contratoEntity.setEstudiante(null);
        LOGGER.log(Level.INFO, "Termina proceso de borrar el Estudiante del contrato con id = {0}", contratoEntity.getId());
    }
}
