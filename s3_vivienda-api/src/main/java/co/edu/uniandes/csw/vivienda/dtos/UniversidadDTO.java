/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.vivienda.dtos;

import java.io.Serializable;
import co.edu.uniandes.csw.vivienda.entities.UniversidadEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;


/**
 * @author Paula Molina
 */

public class UniversidadDTO implements Serializable {

    private Long id;
    private String nombre;
    private Float latitud;
    private Float longitud;
    private String imgUrl;

    public UniversidadDTO(){
        
    }
    
    public UniversidadDTO(UniversidadEntity universidadEntity){
        
        if (universidadEntity != null){
           this.id = universidadEntity.getId();
           this.nombre = universidadEntity.getNombre();
           this.latitud = universidadEntity.getLatitud();
           this.longitud = universidadEntity.getLongitud();
           this.imgUrl = universidadEntity.getImgUrl();
           
        }
    }
    
    public UniversidadEntity toEntity(){
        UniversidadEntity universidadEntity = new UniversidadEntity(); 
        universidadEntity.setId(this.id);
        universidadEntity.setNombre(this.nombre);
        universidadEntity.setLatitud(this.latitud);
        universidadEntity.setLongitud(this.longitud);
        universidadEntity.setImgUrl(this.imgUrl);
        return universidadEntity;
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

    public Float getLatitud() {
        return latitud;
    }

    public void setLatitud(Float latitud) {
        this.latitud = latitud;
    }

    public Float getLongitud() {
        return longitud;
    }

    public void setLongitud(Float longitud) {
        this.longitud = longitud;
    }
    
     public String getImgUrl() {
        return imgUrl;
    }
     public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
     
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}