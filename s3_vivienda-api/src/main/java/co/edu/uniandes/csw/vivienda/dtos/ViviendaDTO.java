/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.vivienda.dtos;

import co.edu.uniandes.csw.vivienda.entities.ViviendaEntity;
import java.io.Serializable;


public class ViviendaDTO implements Serializable{
    private Long id;
    private String nombre;
    private String descripcion;
    private String tipo;
    private String[] serviciosIncluidos;
    private float latitud;
    private float longitud;
    
    public ViviendaDTO(){
        
    }
    
    public ViviendaDTO(ViviendaEntity viviendaEntity){
        if(viviendaEntity!=null){
            this.nombre = viviendaEntity.getNombre();
            this.descripcion = viviendaEntity.getDescripcion();
            this.tipo = viviendaEntity.getTipo();
            this.serviciosIncluidos = viviendaEntity.getServiciosIncluidos();
            this.latitud = viviendaEntity.getLatitud();
            this.longitud = viviendaEntity.getLongitud();
        }
    }
    
    public ViviendaEntity toEntity(){
        ViviendaEntity viviendaEntity = new ViviendaEntity();
        
        viviendaEntity.setId(id);
        viviendaEntity.setNombre(nombre);
        viviendaEntity.setDescripcion(descripcion);
        viviendaEntity.setTipo(tipo);
        viviendaEntity.setServiciosIncluidos(serviciosIncluidos);
        viviendaEntity.setLatitud(latitud);
        viviendaEntity.setLongitud(longitud);
        return viviendaEntity;
    }

    public Long getId() {
        return id;
    }

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

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String[] getServiciosIncluidos() {
        return serviciosIncluidos;
    }

    public void setServiciosIncluidos(String[] serviciosIncluidos) {
        this.serviciosIncluidos = serviciosIncluidos;
    }

    public float getLatitud() {
        return latitud;
    }

    public void setLatitud(float latitud) {
        this.latitud = latitud;
    }

    public float getLongitud() {
        return longitud;
    }

    public void setLongitud(float longitud) {
        this.longitud = longitud;
    }

   
    
}
