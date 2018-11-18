/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.vivienda.dtos;

import co.edu.uniandes.csw.vivienda.entities.ArrendadorEntity;
import co.edu.uniandes.csw.vivienda.entities.ViviendaEntity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Clase que extiende de {@link ArrendadorDTO} para manejar las relaciones entre los
 * ArrendadorDTO y otros DTOs. Para conocer el contenido de un Arrendador vaya a la
 * documentacion de {@link ArrendadorDTO}
 * @author msalcedo
 */
public class ArrendadorDetailDTO extends ArrendadorDTO implements Serializable{
    
    //Relación 1 a muchos arrendadores
    private List<ViviendaDTO> viviendas;
    
    
    /**
     * Constructor vacio
     */
    public ArrendadorDetailDTO()
    {
        super();
    }
    
    /**
     * Crea un objeto ArredadorDetailDTO a partir de un objeto ArrendadorEntity
     * incluyendo los atributos de ArrendadorDTO.
     *
     * @param arrendadorEntity Entidad ArrendadorEntity desde la cual se va a crear el
     * nuevo objeto.
     *
     */
    public ArrendadorDetailDTO(ArrendadorEntity arrendadorEntity)
    {
        super(arrendadorEntity);
        if(arrendadorEntity !=null && arrendadorEntity.getViviendas()!=null)
        {
            viviendas = new ArrayList<>();
            for (ViviendaEntity entityViviendas : arrendadorEntity.getViviendas()) {
                ViviendaDTO asd= new ViviendaDTO(entityViviendas);
                viviendas.add(asd);             
            }  
            
        }
    }
    
    @Override
    public ArrendadorEntity toEntity() {
        ArrendadorEntity arrendadorEntity = super.toEntity();
        if (viviendas != null) {
            List<ViviendaEntity> viviendasEntity = new ArrayList<>();
            for (ViviendaDTO dtoVivienda : viviendas) {
                viviendasEntity.add(dtoVivienda.toEntity());
            }
            arrendadorEntity.setViviendas(viviendasEntity);
        }
        return arrendadorEntity;
    }

    /**
     * @return the viviendas
     */
    public List<ViviendaDTO> getViviendas() {
        return viviendas;
    }

    /**
     * @param viviendas the viviendas to set
     */
    public void setViviendas(List<ViviendaDTO> viviendas) {
        this.viviendas = viviendas;
    }
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
