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
public class SitioInteresDetailDTO extends SitioInteresDTO implements Serializable{
    
     /*
    * Relación a una vivienda
    * dado que esta tiene cardinalidad 1.
     */
    private ViviendaDTO vivienda;
    
    public SitioInteresDetailDTO()
    {
        super();
    }
    
        /**
     * Constructor para transformar un Entity a un DTO
     *
     * @param sitioInteresEntity La entidad de la cual se construye el DTO
     */
    public SitioInteresDetailDTO(SitioInteresEntity sitioInteresEntity)
    {
        super(sitioInteresEntity);
        if (sitioInteresEntity.getVivienda() != null) {
            this.vivienda = new ViviendaDTO(sitioInteresEntity.getVivienda());
        }
    }
    
        /**
     * Transformar el DTO a una entidad
     *
     * @return La entidad que representa el sitioInteres.
     */
    @Override
    public SitioInteresEntity toEntity() {
        SitioInteresEntity sitioInteresEntity = super.toEntity();
        if (getVivienda() != null) {
            sitioInteresEntity.setVivienda(getVivienda().toEntity());
        }
        return sitioInteresEntity;
    }
    
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

    /**
     * @return the vivienda
     */
    public ViviendaDTO getVivienda() {
        return vivienda;
    }

    /**
     * @param vivienda the vivienda to set
     */
    public void setVivienda(ViviendaDTO vivienda) {
        this.vivienda = vivienda;
    }
}
