/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.vivienda.entities;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import uk.co.jemos.podam.common.PodamExclude;
/**
 *
 * @author Juan Manuel Castillo
 */
@Entity
public class CalificacionEntity extends BaseEntity implements Serializable{
    private Float puntaje;
    private String descripcion;
    
    @PodamExclude
    @ManyToOne(optional=false, cascade =CascadeType.MERGE, fetch = javax.persistence.FetchType.LAZY)
    private EstudianteEntity estudiante;
    
    @PodamExclude
    @ManyToOne(optional=false)
    private ViviendaEntity vivienda;
    
    /**
     * Devuelve el puntaje de la calificación.
     *
     * @return the puntaje
     */
    public Float getPuntaje(){
        return puntaje;
    }
    
    /**
     * Devuelve la descripcion de la calificación.
     *
     * @return the descripcion
     */
    public String getDescripcion(){
        return descripcion;
    }
    
    /**
     * Devuelve el estudiante de la calificación.
     *
     * @return the estudiante
     */
    public EstudianteEntity getEstudiante(){
        return estudiante;
    }
    
    /**
     * Devuelve la vivienda de la calificación.
     *
     * @return the vivienda
     */
    public ViviendaEntity getVivienda(){
        return vivienda;
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
     * Modifica el puntaje de la calificación.
     *
     * @param d the puntaje to set
     */
    public void setDescripcion(String d){
        this.descripcion = d;
    }
    
    /**
     * Modifica el estudiante de la calificación.
     *
     * @param est the estudiante to set
     */
    public void setEstudiante(EstudianteEntity est){
        this.estudiante = est;
    }
    
    /**
     * Modifica el vivienda de la calificación.
     *
     * @param viv the vivienda to set
     */
    public void setVivienda(ViviendaEntity viv){
        this.vivienda = viv;
    }
}
