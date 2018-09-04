/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.vivienda.dtos;

import java.io.Serializable;
import co.edu.uniandes.csw.vivienda.entities.CalificacionEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
/**
 *
 * @author estudiante
 */
public class CalificacionDTO implements Serializable{
    private Long id;
    private Float puntaje;
    private String descripcion;
    private EstudianteDTO estudiante;
    private ViviendaDTO vivienda;
    
    public CalificacionDTO (){}

    public CalificacionDTO (CalificacionEntity entity){
        if (entity != null){
           this.id = entity.getId();
           this.puntaje = entity.getPuntaje();
           this.descripcion = entity.getDescripcion();
           if (entity.getEstudiante() != null)
               this.estudiante = new EstudianteDTO(entity.getEstudiante());
           if (entity.getVivienda()!= null)
               this.vivienda = new ViviendaDTO(entity.getVivienda());
        }
    }
    public CalificacionEntity toEntity(){
        CalificacionEntity entity = new CalificacionEntity ();
        entity.setId(id);
        entity.setDescripcion(descripcion);
        entity.setPuntaje(puntaje);
        if (estudiante != null)
            entity.setEstudiante(estudiante.toEntity());
        if (vivienda != null)
            entity.setVivienda(vivienda.toEntity());
        return entity;
    }
    public Float getPuntaje(){
        return puntaje;
    }
    public String getDescripcion(){
        return descripcion;
    }
    public EstudianteDTO getEstudinate(){
        return estudiante;
    }
    
    public void setPuntaje(Float p){
         this.puntaje = p;
    }
    public void setDescripcion(String d){
        this.descripcion = d;
    }
    public void setEstudinate(EstudianteDTO est){
        this.estudiante=est;
    }
    
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
