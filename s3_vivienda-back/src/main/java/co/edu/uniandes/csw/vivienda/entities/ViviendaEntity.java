/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.vivienda.entities;

import java.io.Serializable;
import java.util.logging.Logger;

/**
 * Clase que representa una vivienda en la persistencia y permite su serialización
 * @author DANIEL
 */
public class ViviendaEntity extends BaseEntity implements Serializable{
    private static final Logger LOGGER = Logger.getLogger(ViviendaEntity.class.getName());
    private String nombre;
    private String descripcion;
    private String tipo;
    private String[] serviciosIncluidos;
    private float latitud;
    private float longitud;

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getTipo() {
        return tipo;
    }

    public String[] getServiciosIncluidos() {
        return serviciosIncluidos;
    }

    public float getLatitud() {
        return latitud;
    }

    public float getLongitud() {
        return longitud;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setServiciosIncluidos(String[] serviciosIncluidos) {
        this.serviciosIncluidos = serviciosIncluidos;
    }

    public void setLatitud(float latitud) {
        this.latitud = latitud;
    }

    public void setLongitud(float longitud) {
        this.longitud = longitud;
    }
    
    
}
