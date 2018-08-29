/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.vivienda.dtos;

import co.edu.uniandes.csw.vivienda.entities.ServicioAdicionalEntity;

/**
 *
 * @author estudiante
 */
public class ServicioAdicionalDTO {
    
    public String nombre;
    public String descripcion;
    public Float costo;
    
    public ServicioAdicionalDTO(){
        
    }
    
    public ServicioAdicionalDTO( ServicioAdicionalEntity servicioAdicionalEntity){
        
        if(servicioAdicionalEntity!=null){
            this.nombre = servicioAdicionalEntity.getNombre();
            this.descripcion = servicioAdicionalEntity.getDescripcion();
            this.costo = servicioAdicionalEntity.getCosto();
            
        }
    } 
        public ServicioAdicionalEntity toEntity(){
        ServicioAdicionalEntity servicioAdicionalEntity = new ServicioAdicionalEntity();
        
        servicioAdicionalEntity.setNombre(nombre);
        servicioAdicionalEntity.setDescripcion(descripcion);
        servicioAdicionalEntity.setDescripcion(descripcion);

        return servicioAdicionalEntity;
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
