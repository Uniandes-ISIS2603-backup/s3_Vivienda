/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.vivienda.dtos;

import co.edu.uniandes.csw.vivienda.entities.ContratoEntity;
import co.edu.uniandes.csw.vivienda.entities.ViviendaEntity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @author estudiante
 */
public class ViviendaDetailDTO extends ViviendaDTO implements Serializable{
    
    /*
    * Esta lista de tipo ContratoDTO contiene los contratos que estan asociados a una vivienda
     */
    private List<ContratoDTO> contratos;

    /**
     * Constructor por defecto
     */
    public ViviendaDetailDTO() {
    }

    /**
     * Constructor para transformar un Entity a un DTO
     *
     * @param viviendaEntity La entidad de la vivienda para transformar a DTO.
     */
    public ViviendaDetailDTO(ViviendaEntity viviendaEntity) {
        super(viviendaEntity);
        if (viviendaEntity != null) {
            if (viviendaEntity.getContratos() != null) {
                contratos = new ArrayList<>();
                for (ContratoEntity entityContrato : viviendaEntity.getContratos()) {
                    contratos.add(new ContratoDTO(entityContrato));
                }
            }
        }
    }

    /**
     * Transformar un DTO a un Entity
     *
     * @return El DTO de la vivienda para transformar a Entity
     */
    @Override
    public ViviendaEntity toEntity() {
        ViviendaEntity viviendaEntity = super.toEntity();
        if (contratos != null) {
            List<ContratoEntity> contratosEntity = new ArrayList<>();
            for (ContratoDTO dtoContrato : contratos) {
                contratosEntity.add(dtoContrato.toEntity());
            }
            viviendaEntity.setContratos(contratosEntity);
        }
        return viviendaEntity;
    }

    /**
     * Devuelve la lista de contratos de la vivienda.
     *
     * @return the contratos
     */
    public List<ContratoDTO> getContratos() {
        return contratos;
    }

    /**
     * Modifica la lista de contratos de la vivienda.
     *
     * @param contratos the contratos to set
     */
    public void setContratos(List<ContratoDTO> contratos) {
        this.contratos = contratos;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
