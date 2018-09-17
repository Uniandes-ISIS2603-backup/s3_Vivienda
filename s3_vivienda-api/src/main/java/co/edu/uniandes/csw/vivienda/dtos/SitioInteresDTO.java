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
 *
 * @author estudiante
 */
public class SitioInteresDTO implements Serializable{
    private long id;
    private String nombre;
    private String descripcion;
    private Float latitud;
    private Float longitud;
    
    private ViviendaDTO viviendadto;
    
    public SitioInteresDTO()
    {   
    }
    
    public SitioInteresDTO(SitioInteresEntity sitioInteresEntity) {
        if (sitioInteresEntity != null) {
            this.id = sitioInteresEntity.getId();
            this.nombre = sitioInteresEntity.getNombre();
            this.descripcion = sitioInteresEntity.getDescripcion();
            this.latitud = sitioInteresEntity.getLatitud();
            this.longitud = sitioInteresEntity.getLongitud();
            if (sitioInteresEntity.getVivienda() != null)
                this.viviendadto = new ViviendaDTO(sitioInteresEntity.getVivienda());
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
     * Devuelve el nombre del sitioInteres.
     *
     * @return the name
     */
    public String getNombre() {
        return nombre;
    }
    
       /**
     * Modifica el nombre del sitioInteres.
     *
     * @param pNombre the name to set
     */
    public void setName(String pNombre) {
        this.nombre = pNombre;
    }
    
    public String getDescripcion()
    {
        return descripcion;
    }
    
    public void setDescripcion(String pDescripcion)
    {
        this.descripcion=pDescripcion;
    }
    
    public Float getLatitud()
    {
        return latitud;
    }
    
    public Float getLongitud()
    {
        return longitud;
    }
    
    public void setLatitud(float pLatitud)
    {
        this.latitud=pLatitud;
        
    }
 
    public void setLongitud(float pLongitud)
    {
        this.longitud=pLongitud;
    }
    
       /**
     * Convertir DTO a Entity
     *
     * @return Un Entity con los valores del DTO
     */
    public SitioInteresEntity toEntity() {
        SitioInteresEntity sitioInteresEntity = new SitioInteresEntity();
        sitioInteresEntity.setId(this.id);
        sitioInteresEntity.setNombre(this.nombre);
        sitioInteresEntity.setDescripcion(this.descripcion);
        sitioInteresEntity.setLatitud(this.latitud);
        sitioInteresEntity.setLongitud(this.longitud);
        if (viviendadto != null)
            sitioInteresEntity.setVivienda(viviendadto.toEntity());
        return sitioInteresEntity;
    }
    
        @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

    /**
     * @return the viviendadto
     */
    public ViviendaDTO getViviendadto() {
        return viviendadto;
    }

    /**
     * @param viviendadto the viviendadto to set
     */
    public void setViviendadto(ViviendaDTO viviendadto) {
        this.viviendadto = viviendadto;
    }
}
