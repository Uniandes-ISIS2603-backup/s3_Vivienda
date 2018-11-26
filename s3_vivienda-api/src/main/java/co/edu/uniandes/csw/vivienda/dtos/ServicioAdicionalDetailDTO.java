/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.vivienda.dtos;

import co.edu.uniandes.csw.vivienda.entities.ServicioAdicionalEntity;
import java.io.Serializable;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @author Paula Molina Ruiz
 */
public class ServicioAdicionalDetailDTO extends ServicioAdicionalDTO implements Serializable
{
    private ContratoDTO contrato;
    
        /*
    * Relación a una vivienda
    * dado que esta tiene cardinalidad 1.
    */
    private ViviendaDTO vivienda;
    
    /**
     * Constructor por defecto
     */
    public ServicioAdicionalDetailDTO() {
        super();
    }

    /**
     * Crea un objeto ServicioAdicionalDetailDTO a partir de un objeto ServicioAdicionalEntity
     * incluyendo los atributos de ServicioAdicionalDTO.
     *
     * @param servicioAdicionalEntity Entidad ServicioAdicionalEntity desde la cual se va a crear el
     * nuevo objeto.
     *
     */
    public ServicioAdicionalDetailDTO(ServicioAdicionalEntity servicioAdicionalEntity) {
        super(servicioAdicionalEntity);
        if (servicioAdicionalEntity.getContrato() != null) {
            this.contrato = new ContratoDTO(servicioAdicionalEntity.getContrato());
        }else if (servicioAdicionalEntity.getVivienda()!=null)
            this.vivienda = new ViviendaDTO(servicioAdicionalEntity.getVivienda());
    }

    /**
     * Convierte un objeto ServicioAdicionalDetailDTO a ServicioAdicionalEntity incluyendo los atributos
     * de ServicioAdicionalDTO.
     *
     * @return Nueva objeto ServicioAdicionalEntity.
     *
     */
    @Override
    public ServicioAdicionalEntity toEntity() {
        ServicioAdicionalEntity servicioAdicionalEntity = super.toEntity();
        if (contrato != null) {
            servicioAdicionalEntity.setContrato(contrato.toEntity());
        }
        return servicioAdicionalEntity;
    }

    /**
     * Modifica el contrato asociada a este premio.
     *
     * @param contrato the author to set
     */
    public void setContrato(ContratoDTO contrato) {
        this.contrato = contrato;
    }

    /**
     * Devuelve el contrato asociada a este premio
     *
     * @return DTO de Contrato
     */
    public ContratoDTO getContrato() {
        return contrato;
    }
    
    
    /**
     * Devuelve la vivienda asociada a este servicio.
     *
     * @return the vivienda
     */
    public ViviendaDTO getVivienda() {
        return vivienda;
    }

    /**
     * Modifica la vivienda asociada a este servicio.
     *
     * @param vivienda the book to set
     */
    public void setVivienda(ViviendaDTO vivienda) {
        this.vivienda = vivienda;
    }   

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
