/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.vivienda.dtos;

import co.edu.uniandes.csw.vivienda.entities.SitioInteresEntity;
import java.io.Serializable;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *  SitioInteresDTO Objeto de transferencia de datos de SitioInteres. Los DTO contienen las
 * representaciones de los JSON que se transfieren entre el cliente y el
 * servidor.
 * @author msalcedo
 */
public class SitioInteresDTO implements Serializable{
    
    private Long id;
    private String nombre;
    private String descripccion;
    private Float latitud;
    private Float longitud;
    private String img;
    

    
     /**
     * Constructor por defecto
     */
    public SitioInteresDTO()
    {   
    }
    
        /**
     * Constructor a partir de una entidad
     *
     * @param sitioInteresEntity La entidad de la cual se construye el DTO
     */
    public SitioInteresDTO(SitioInteresEntity sitioInteresEntity) {
        if (sitioInteresEntity != null) {
            this.id = sitioInteresEntity.getId();
            this.nombre = sitioInteresEntity.getNombre();
            this.descripccion = sitioInteresEntity.getDescripcion();
            this.latitud = sitioInteresEntity.getLatitud();
            this.longitud = sitioInteresEntity.getLongitud();
            this.img = sitioInteresEntity.getImg();
        }
    }
    
        /**
     * Devuelve el ID del sitioInteres.
     *
     * @return the id
     */
    public Long getId() {
        return id;
    }
    
        /**
     * Modifica el ID del sitioInteres.
     *
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }
    
       /**
     * Convertir DTO a Entity
     *
     * @return Un Entity con los valores del DTO
     */
    public SitioInteresEntity toEntity() {
        SitioInteresEntity sitioInteresEntity = new SitioInteresEntity();
        
        sitioInteresEntity.setId(this.id);
        sitioInteresEntity.setNombre(this.getNombre());
        sitioInteresEntity.setDescripcion(this.getDescripccion());
        sitioInteresEntity.setLatitud(this.getLatitud());
        sitioInteresEntity.setLongitud(this.getLongitud());
        sitioInteresEntity.setImg(this.getImg());
        return sitioInteresEntity;
    }
    
        @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

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
    public String getDescripccion() {
        return descripccion;
    }

    /**
     * @param descripcion the descripcion to set
     */
    public void setDescripccion(String descripcion) {
        this.descripccion = descripcion;
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
    
}
