/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.vivienda.dtos;

import co.edu.uniandes.csw.vivienda.entities.EstudianteEntity;
import co.edu.uniandes.csw.vivienda.entities.UniversidadEntity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * @author Paula Molina
 */
public class UniversidadDetailDTO extends UniversidadDTO implements Serializable {

    /*
     * Esta lista de tipo EstudianteDTO contiene los estudiantes que estan asociados a una universidad
     */
    private List<EstudianteDTO> estudiantes;

    /**
     * Constructor por defecto
     */
    public UniversidadDetailDTO() {
        super();
    }

    /**
     * Constructor para transformar un Entity a un DTO
     *
     * @param universidadEntity La entidad de la universidad para transformar a DTO.
     */
    public UniversidadDetailDTO(UniversidadEntity universidadEntity) {
        super(universidadEntity);
        if (universidadEntity != null && universidadEntity.getEstudiantes() != null) {
            estudiantes = new ArrayList<>();
            for (EstudianteEntity entityEstudiante : universidadEntity.getEstudiantes()) {
                estudiantes.add(new EstudianteDTO(entityEstudiante));
            }
        }
    }

    /**
     * Transformar un DTO a un Entity
     *
     * @return El DTO de la editorial para transformar a Entity
     */
    @Override
    public UniversidadEntity toEntity() {
        UniversidadEntity universidadEntity = super.toEntity();
        if (estudiantes != null) {
            List<EstudianteEntity> estudiantesEntity = new ArrayList<>();
            for (EstudianteDTO dtoEstudiante : estudiantes) {
                estudiantesEntity.add(dtoEstudiante.toEntity());
            }
            universidadEntity.setEstudiantes(estudiantesEntity);
        }
        return universidadEntity;
    }

    /**
     * Devuelve la lista de estudiantes de la universidad.
     *
     * @return estudiantes
     */
    public List<EstudianteDTO> getEstudiantes() {
        return estudiantes;
    }

    /**
     * Modifica la lista de estudiantes de la universidad.
     *
     * @param estudiantes to set
     */
    public void setEstudiantes(List<EstudianteDTO> estudiantes) {
        this.estudiantes = estudiantes;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
