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
 * Clase que representa un sitioInteres en la persistencia y permite su serializacion
 * @author msalcedo
 */
@Entity
public class SitioInteresEntity extends BaseEntity implements Serializable{

    private String nombre;
    private String descripcion;
    private Float latitud;
    private Float longitud;
    private String img;

    @PodamExclude
    @ManyToOne
    private ViviendaEntity vivienda;
    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @param descripcion the descripcion to set
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * @return the latitud
     */
    public Float getLatitud() {
        return latitud;
    }

    /**
     * @param latitud the latitud to set
     */
    public void setLatitud(Float latitud) {
        this.latitud = latitud;
    }

    /**
     * @return the longitud
     */
    public Float getLongitud() {
        return longitud;
    }

    /**
     * @param longitud the longitud to set
     */
    public void setLongitud(Float longitud) {
        this.longitud = longitud;
    }

    /**
     * @return the vivienda
     */
    public ViviendaEntity getVivienda() {
        return vivienda;
    }

    /**
     * @param vivienda the vivienda to set
     */
    public void setVivienda(ViviendaEntity vivienda) {
        this.vivienda = vivienda;
    }

    /**
     * @return the img
     */
    public String getImg() {
        return img;
    }

    /**
     * @param img the img to set
     */
    public void setImg(String img) {
        this.img = img;
    }
    
//    public boolean equals(SitioInteresEntity o)
//    {
//        return (this.latitud==o.latitud) && (this.longitud==o.longitud);
//    }
    
}
