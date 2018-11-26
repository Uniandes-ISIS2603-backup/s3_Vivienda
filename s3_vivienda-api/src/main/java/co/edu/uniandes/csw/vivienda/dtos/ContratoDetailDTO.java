/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.vivienda.dtos;

import co.edu.uniandes.csw.vivienda.entities.ContratoEntity;
import co.edu.uniandes.csw.vivienda.entities.ServicioAdicionalEntity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Carlos Infante
 */
public class ContratoDetailDTO extends ContratoDTO implements Serializable {
    
    // relación  cero o muchos serviciosAdicionalesAgregados 
    private List<ServicioAdicionalDTO> serviciosAdicionalesAgregados;

    public ContratoDetailDTO() {
        super();
    }

    /**
     * Constructor para transformar un Entity a un DTO
     *
     * @param contratoEntity La entidad de la cual se construye el DTO
     */
    public ContratoDetailDTO(ContratoEntity contratoEntity) {
        super(contratoEntity);
        if (contratoEntity.getServiciosAdicionalesAgregados() != null) {
            serviciosAdicionalesAgregados = new ArrayList<>();
            for (ServicioAdicionalEntity entityReview : contratoEntity.getServiciosAdicionalesAgregados())
            {
                serviciosAdicionalesAgregados.add(new ServicioAdicionalDTO(entityReview));
            }
        }
    }

    /**
     * Transformar el DTO a una entidad
     *
     * @return La entidad que representa el contrato.
     */
    @Override
    public ContratoEntity toEntity() {
        ContratoEntity contratoEntity = super.toEntity();
        if (serviciosAdicionalesAgregados != null) {
            List<ServicioAdicionalEntity> servicioAdicionalEntity = new ArrayList<>();
            for (ServicioAdicionalDTO dtoReview : getServiciosAdicionalesAgregados()) {
                servicioAdicionalEntity.add(dtoReview.toEntity());
            }
            contratoEntity.setServiciosAdicionalesAgregados(servicioAdicionalEntity);
        }
        return contratoEntity;
    }

    /**
     * Devuelve los servicios adicionales agregados que estan asociados a este contrato
     *
     * @return Lista de DTOs de serviciosAdicionalesAgregados
     */
    public List<ServicioAdicionalDTO> getServiciosAdicionalesAgregados() {
        return serviciosAdicionalesAgregados;
    }

    /**
     * Modifica las reseñas de este contrato.
     *
     * @param serviciosAdicionalesAgregados Los nuevos serviciosAdicionalesAgregados
     */
    public void setReviews(List<ServicioAdicionalDTO> serviciosAdicionalesAgregados) {
        this.serviciosAdicionalesAgregados = serviciosAdicionalesAgregados;
    }

}
