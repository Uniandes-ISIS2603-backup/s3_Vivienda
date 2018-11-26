/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.vivienda.dtos;

import co.edu.uniandes.csw.vivienda.entities.ServicioAdicionalEntity;
import java.io.Serializable;

/**
 *
 * @author Paula Molina
 */
public class ServicioAdicionalDTO implements Serializable {
    
    private Long id;
    private String nombre;
    private String descripcion;
    private Float costo;
    
    public ServicioAdicionalDTO(){
        
    }
    
    public ServicioAdicionalDTO( ServicioAdicionalEntity servicioAdicionalEntity){
        
        if(servicioAdicionalEntity!=null){
            this.id = servicioAdicionalEntity.getId();
            this.nombre = servicioAdicionalEntity.getNombre();
            this.descripcion = servicioAdicionalEntity.getDescripcion();
            this.costo = servicioAdicionalEntity.getCosto();           
        }
    } 
        public ServicioAdicionalEntity toEntity(){
        ServicioAdicionalEntity servicioAdicionalEntity = new ServicioAdicionalEntity();
        
        servicioAdicionalEntity.setId(this.id);
        servicioAdicionalEntity.setNombre(this.nombre);
        servicioAdicionalEntity.setDescripcion(this.descripcion);
        servicioAdicionalEntity.setCosto(this.costo);

        return servicioAdicionalEntity;
    }
        
    /**
     * Devuelve el ID del servicio.
     *
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * Modifica el ID de la reseña.
     *
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
     public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
     public Float getCosto() {
        return costo;
    }

    public void setCosto(Float costo) {
        this.costo = costo;
    }      
 
}
