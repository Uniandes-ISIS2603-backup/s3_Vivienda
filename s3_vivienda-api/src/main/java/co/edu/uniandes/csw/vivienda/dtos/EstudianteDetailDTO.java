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
import java.util.Collection;

/**
 *
 * @author estudiante
 */
public class EstudianteDetailDTO extends EstudianteDTO implements Serializable{
    Collection <CalificacionDTO> calificaciones;
    
    public EstudianteDetailDTO(EstudianteEntity entity){
        super(entity);
        if (entity.getCalificaciones() != null){
            calificaciones = new ArrayList<>();
            for (CalificacionEntity e: entity.getCalificaciones())
                calificaciones.add(new CalificacionDTO(e));
        }   
    }
    
    @Override
    public EstudianteEntity toEntity(){
        EstudianteEntity entity = super.toEntity();
        if (calificaciones != null){
            Collection <CalificacionEntity> cal = new ArrayList<>();
            for (CalificacionDTO e: calificaciones)
                cal.add(e.toEntity());
            entity.setCalificaciones(cal);
        }
        return entity;
    }
    
    public Collection<CalificacionDTO> getCalificaciones(){
        return calificaciones;
    }
    public void setCalificaciones(Collection<CalificacionDTO> cal){
        this.calificaciones=cal;
    }
    
    
}
