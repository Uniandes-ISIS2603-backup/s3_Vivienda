/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.vivienda.dtos;

import co.edu.uniandes.csw.vivienda.entities.*;

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
    private List<CuartoDTO> cuartos;
    private List<SitioInteresDTO> sitiosDeInteres;
    private List<CalificacionDTO> calificaciones;
    private List<String> serviciosOfrecidos;
    private List<ServicioAdicionalDTO> serviciosAdicionales;

    /**
     * Constructor por defecto
     */
    public ViviendaDetailDTO() {
        super();
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
                for (ContratoEntity entityContrato : viviendaEntity.getContratos()){
                    contratos.add(new ContratoDTO(entityContrato));
                }
            }

            if (viviendaEntity.getCuartos() != null) {
                cuartos = new ArrayList<>();
                for(CuartoEntity entityCuarto : viviendaEntity.getCuartos()){
                    cuartos.add(new CuartoDTO(entityCuarto));
                }
            }

            if (viviendaEntity.getSitiosDeInteres() != null){
                sitiosDeInteres = new ArrayList<>();
                for(SitioInteresEntity sitioEntity : viviendaEntity.getSitiosDeInteres()){
                    sitiosDeInteres.add(new SitioInteresDTO(sitioEntity));
                }
            }

            if(viviendaEntity.getCalificaciones() != null ){
                calificaciones = new ArrayList<>();
                for(CalificacionEntity calificacionEntity : viviendaEntity.getCalificaciones()){
                    calificaciones.add(new CalificacionDTO(calificacionEntity));
                }
            }

            if(viviendaEntity.getServiciosAdicionales() != null){
                serviciosAdicionales = new ArrayList<>();
                for (ServicioAdicionalEntity servicioEntity : viviendaEntity.getServiciosAdicionales()){
                    serviciosAdicionales.add(new ServicioAdicionalDTO(servicioEntity));
                }
            }

            if(viviendaEntity.getServiciosIncluidos() != null){
                serviciosOfrecidos = new ArrayList<>();
                for (String servicio : viviendaEntity.getServiciosIncluidos()){
                    serviciosOfrecidos.add(servicio);
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

        if (cuartos != null) {
            List<CuartoEntity> cuartoEntities = new ArrayList<>();
            for (CuartoDTO dto : cuartos) {
                cuartoEntities.add(dto.toEntity());
            }
            viviendaEntity.setCuartos(cuartoEntities);
        }

        if (sitiosDeInteres != null) {
            List<SitioInteresEntity> entities = new ArrayList<>();
            for (SitioInteresDTO dto : sitiosDeInteres) {
                entities.add(dto.toEntity());
            }
            viviendaEntity.setSitiosDeInteres(entities);
        }

        if (calificaciones != null) {
            List<CalificacionEntity> entities = new ArrayList<>();
            for (CalificacionDTO dto : calificaciones) {
                entities.add(dto.toEntity());
            }
            viviendaEntity.setCalificaciones(entities);
        }

        if (serviciosOfrecidos != null) {
            viviendaEntity.setServiciosIncluidos(serviciosOfrecidos);
        }

        if (serviciosAdicionales != null) {
            List<ServicioAdicionalEntity> entities = new ArrayList<>();
            for (ServicioAdicionalDTO dto : serviciosAdicionales) {
                entities.add(dto.toEntity());
            }
            viviendaEntity.setServiciosAdicionales(entities);
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

    public List<CuartoDTO> getCuartos() {
        return cuartos;
    }

    public void setCuartos(List<CuartoDTO> cuartos) {
        this.cuartos = cuartos;
    }

    public List<SitioInteresDTO> getSitiosDeInteres() {
        return sitiosDeInteres;
    }

    public void setSitiosDeInteres(List<SitioInteresDTO> sitiosDeInteres) {
        this.sitiosDeInteres = sitiosDeInteres;
    }

    public List<CalificacionDTO> getCalificaciones() {
        return calificaciones;
    }

    public void setCalificaciones(List<CalificacionDTO> calificaciones) {
        this.calificaciones = calificaciones;
    }

    public List<String> getServiciosOfrecidos() {
        return serviciosOfrecidos;
    }

    public void setServiciosOfrecidos(List<String> serviciosOfrecidos) {
        this.serviciosOfrecidos = serviciosOfrecidos;
    }

    public List<ServicioAdicionalDTO> getServiciosAdicionales() {
        return serviciosAdicionales;
    }

    public void setServiciosAdicionales(List<ServicioAdicionalDTO> serviciosAdicionales) {
        this.serviciosAdicionales = serviciosAdicionales;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
