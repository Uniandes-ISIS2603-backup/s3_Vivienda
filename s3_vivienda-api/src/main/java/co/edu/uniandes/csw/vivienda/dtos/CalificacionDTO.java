/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.vivienda.dtos;

import java.io.Serializable;
import co.edu.uniandes.csw.vivienda.entities.CalificacionEntity;
/**
 *
 * @author estudiante
 */
public class CalificacionDTO implements Serializable{
    private Long id;
    private Float puntaje;
    private String descripcion;
    
    public CalificacionDTO (){}

    public CalificacionDTO (CalificacionEntity entity){
        if (entity != null){
           this.puntaje = entity.getPuntaje();
           this.descripcion = entity.getDescripcion();
        }
    }
    public CalificacionEntity toEntity(){
        CalificacionEntity entity = new CalificacionEntity ();
        entity.setDescripcion(descripcion);
        entity.setPuntaje(puntaje);
        return entity;
    }
}
