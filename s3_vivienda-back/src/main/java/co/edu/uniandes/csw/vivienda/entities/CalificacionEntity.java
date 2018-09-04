/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.vivienda.entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import uk.co.jemos.podam.common.PodamExclude;
/**
 *
 * @author estudiante
 */
@Entity
public class CalificacionEntity extends BaseEntity implements Serializable{
    private Float puntaje;
    private String descripcion;
    
    @PodamExclude
    @ManyToOne
    private EstudianteEntity estudiante;
    
    @PodamExclude
    @ManyToOne
    private ViviendaEntity vivienda;

    public Float getPuntaje(){
        return puntaje;
    }
    public String getDescripcion(){
        return descripcion;
    }
    public EstudianteEntity getEstudiante(){
        return estudiante;
    }
    public ViviendaEntity getVivienda(){
        return vivienda;
    }
    
    public void setPuntaje(Float p){
         this.puntaje = p;
    }
    public void setDescripcion(String d){
        this.descripcion = d;
    }
    public void setEstudiante(EstudianteEntity est){
        this.estudiante = est;
    }
    public void setVivienda(ViviendaEntity viv){
        this.vivienda = viv;
    }
}
