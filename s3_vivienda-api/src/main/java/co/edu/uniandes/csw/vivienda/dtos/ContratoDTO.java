/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.vivienda.dtos;

import co.edu.uniandes.csw.vivienda.entities.ContratoEntity;
import java.io.Serializable;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @author Carlos Infante
 */
public class ContratoDTO implements Serializable {

    private Long id;
    private String fechaInicio;
    private String fechaFin;
    private String metodoPago;
    
    public ContratoDTO()
    {
        
    }
    
    public ContratoDTO(ContratoEntity contratoEntity){
        if(contratoEntity!=null){
            this.id = contratoEntity.getId();
            this.fechaInicio = contratoEntity.getFechaInicio();
            this.fechaFin = contratoEntity.getFechaFin();
            this.metodoPago = contratoEntity.getMetodoPago();

        }
    }
    
        public ContratoEntity toEntity(){
        ContratoEntity contratoEntity = new ContratoEntity();
        
        contratoEntity.setId(this.id);
        contratoEntity.setFechaInicio(this.fechaInicio);
        contratoEntity.setFechaFin(this.fechaFin);
        contratoEntity.setMetodoPago(this.metodoPago);

        return contratoEntity;
    }

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the FechaInicio
     */
    public String getFechaInicio() {
        return fechaInicio;
    }

    /**
     * @param FechaInicio the FechaInicio to set
     */
    public void setFechaInicio(String FechaInicio) {
        this.fechaInicio = FechaInicio;
    }

    /**
     * @return the FechaFin
     */
    public String getFechaFin() {
        return fechaFin;
    }

    /**
     * @param FechaFin the FechaFin to set
     */
    public void setFechaFin(String FechaFin) {
        this.fechaFin = FechaFin;
    }

    /**
     * @return the MetodoPago
     */
    public String getMetodoPago() {
        return metodoPago;
    }

    /**
     * @param MetodoPago the MetodoPago to set
     */
    public void setMetodoPago(String MetodoPago) {
        this.metodoPago = MetodoPago;
    }
    
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
