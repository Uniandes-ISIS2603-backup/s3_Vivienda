/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.vivienda.dtos;

import co.edu.uniandes.csw.vivienda.entities.ContratoEntity;
import java.io.Serializable;
import org.apache.commons.lang3.builder.*;

/**
 *
 * @author Carlos Infante
 */
public class ContratoDTO implements Serializable {

    /**
     * Identificador del contrato
     */
    private Long id;

    /**
     * Fecha de inicio del contrato
     */
//    @XmlJavaTypeAdapter(DateAdapter.class)
    private String fechaInicio;

    /**
     * Fecha de fin para el contrato
     */
//    @XmlJavaTypeAdapter(DateAdapter.class)
    private String fechaFin;

    /**
     * Metodo de pago del contrato
     */
    private Integer metodoPago;

    /**
     * Relación a una vivienda, dado que esta tiene cardinalidad 1.
     */
    private ViviendaDTO vivienda;

    /**
     * Relación a un estudiante, dado que esta tiene cardinalidad 1.
     */
    private EstudianteDTO estudiante;

    /**
     * Constructor por defecto
     */
    public ContratoDTO() {

    }

    /**
     * Conviertir Entity a DTO (Crea un nuevo DTO con los valores que recibe en
     * la entidad que viene de argumento.
     *
     * @param contratoEntity: Es la entidad que se va a convertir a DTO
     */
    public ContratoDTO(ContratoEntity contratoEntity) {
        if (contratoEntity != null) {
            this.id = contratoEntity.getId();
            this.fechaInicio = contratoEntity.getFechaInicio();
            this.fechaFin = contratoEntity.getFechaFin();
            this.metodoPago = contratoEntity.getMetodoPago();
            if (contratoEntity.getVivienda() != null) {
                this.vivienda = new ViviendaDTO(contratoEntity.getVivienda());
            } else {
                this.vivienda = null;
            }
            if (contratoEntity.getEstudiante() != null) {
                this.estudiante = new EstudianteDTO(contratoEntity.getEstudiante());
            } else {
                this.estudiante = null;
            }
        }
    }

    /**
     * Convertir DTO a Entity
     *
     * @return Un Entity con los valores del DTO
     */
    public ContratoEntity toEntity() {
        ContratoEntity contratoEntity = new ContratoEntity();

        contratoEntity.setId(this.id);
        contratoEntity.setFechaInicio(this.fechaInicio);
        contratoEntity.setFechaFin(this.fechaFin);
        contratoEntity.setMetodoPago(this.metodoPago);
        if (this.vivienda != null) {
            contratoEntity.setVivienda(this.vivienda.toEntity());
        }
        if (this.estudiante != null) {
            contratoEntity.setEstudiante(this.estudiante.toEntity());
        }

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
     * @param fechaInicio the FechaInicio to set
     */
    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    /**
     * @return the FechaFin
     */
    public String getFechaFin() {
        return fechaFin;
    }

    /**
     * @param fechaFin the FechaFin to set
     */
    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    /**
     * @return the MetodoPago
     */
    public int getMetodoPago() {
        return metodoPago;
    }

    /**
     * @param metodoPago the MetodoPago to set
     */
    public void setMetodoPago(int metodoPago) {
        this.metodoPago = metodoPago;
    }

    /**
     * Devuelve la vivienda asociada a este contrato.
     *
     * @return the vivienda
     */
    public ViviendaDTO getVivienda() {
        return vivienda;
    }

    /**
     * Modifica la vivienda asociada a este contrato.
     *
     * @param vivienda the vivienda to set
     */
    public void setVivienda(ViviendaDTO vivienda) {
        this.vivienda = vivienda;
    }

    /**
     * Da el estudiante que esta en el contrato
     *
     * @return Estudiante que posee el contrato.
     */
    public EstudianteDTO getEstudiante() {
        return estudiante;
    }

    /**
     * Modifica el estudiante del contrato.
     *
     * @param estudiante Estudiante que ser quiere modificar.
     */
    public void setEstudiante(EstudianteDTO estudiante) {
        this.estudiante = estudiante;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
