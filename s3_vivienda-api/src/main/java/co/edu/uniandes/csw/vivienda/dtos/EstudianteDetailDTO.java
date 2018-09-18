/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.vivienda.dtos;

import co.edu.uniandes.csw.vivienda.entities.CalificacionEntity;
import co.edu.uniandes.csw.vivienda.entities.EstudianteEntity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @author Juan Manuel Castillo
 */
public class EstudianteDetailDTO extends EstudianteDTO implements Serializable{
    
    /*
    * Esta lista de tipo CalificacionDTO contiene las calificaciones que estan asociados a un estudiante
    */
    private List <CalificacionDTO> calificaciones;
    
    /**
     * Constructor por defecto
     */
    public EstudianteDetailDTO(){}
    
    /**
     * Constructor para transformar un Entity a un DTO
     *
     * @param entity La entidad del estudiante para transformar a DTO.
     */
    public EstudianteDetailDTO(EstudianteEntity entity){
        super(entity);
        if (entity != null){
            if (entity.getCalificaciones() != null){
                calificaciones = new ArrayList<>();
                for (CalificacionEntity e: entity.getCalificaciones())
                    calificaciones.add(new CalificacionDTO(e));
            }
        }
    }
    
    /**
     * Transformar un DTO a un Entity
     *
     * @return El DTO del estudiante para transformar a Entity
     */
    @Override
    public EstudianteEntity toEntity(){
        EstudianteEntity entity = super.toEntity();
        if (calificaciones != null){
            List <CalificacionEntity> cal = new ArrayList<>();
            for (CalificacionDTO e: calificaciones)
                cal.add(e.toEntity());
            entity.setCalificaciones(cal);
        }
        return entity;
    }
    
    /**
     * Devuelve la lista de calificaciones del estudiante
     *
     * @return the calificaciones
     */
    public List<CalificacionDTO> getCalificaciones(){
        return calificaciones;
    }
    
    /**
     * Modifica la lista de calificaciones del estudiante.
     *
     * @param cal the calificaciones to set
     */
    public void setCalificaciones(List<CalificacionDTO> cal){
        this.calificaciones=cal;
    }
    
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
