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
 * @author Juan Manuel Castillo
 */
public class CalificacionDTO implements Serializable{
    private Long id;
    private Float puntaje;
    private String descripcion;
    
    /*
    * Relación a un estudiante
    * dado que esta tiene cardinalidad 1.
    */
    private EstudianteDTO estudiante;
    
    /*
    * Relación a una vivienda
    * dado que esta tiene cardinalidad 1.
    */
    private ViviendaDTO vivienda;
    
    /**
     * Constructor por defecto
     */
    public CalificacionDTO (){}

    /**
     * Constructor a partir de la entidad
     *
     * @param entity La entidad de la calificación
     */
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
    
    /**
     * Método para transformar el DTO a una entidad.
     *
     * @return La entidad de la calificación asociada.
     */
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
    
    /**
     * Devuelve el ID de la calificación
     *
     * @return the id
     */
    public Long getId(){
       return id;
    }
    
    /**
     * Devuelve el puntaje de la calificación
     *
     * @return el puntaje
     */
    public Float getPuntaje(){
        return puntaje;
    }
    
    /**
     * Devuelve la descripcion de la calificación
     *
     * @return la descripcion
     */
    public String getDescripcion(){
        return descripcion;
    }
    
    /**
     * Devuelve el estudiante de la calificación
     *
     * @return el estudiante
     */
    public EstudianteDTO getEstudinate(){
        return estudiante;
    }
    
    /**
     * Modifica el nombre de la calificación.
     *
     * @param id the id to set
     */
    public void setId(Long id){
        this.id = id;
    }
    
    /**
     * Modifica el puntaje de la calificación.
     *
     * @param p the puntaje to set
     */
    public void setPuntaje(Float p){
         this.puntaje = p;
    }
    
    /**
     * Modifica el descripcion de la calificación.
     *
     * @param d the descripcion to set
     */
    public void setDescripcion(String d){
        this.descripcion = d;
    }
    
    /**
     * Modifica el estudiante de la calificación.
     *
     * @param est the estudiante to set
     */
    public void setEstudinate(EstudianteDTO est){
        this.estudiante=est;
    }
    
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
