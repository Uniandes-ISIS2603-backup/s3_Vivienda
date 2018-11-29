/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.vivienda.dtos;

import co.edu.uniandes.csw.vivienda.entities.CuartoEntity;
import java.io.Serializable;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @author Daniel Giraldo
 */
public class CuartoDTO implements Serializable {
    private Long id;
    private String nombre;
    private String descripcion;
    private Integer costoArriendo;
    private boolean ocupado;

    private ViviendaDTO vivienda;

    public CuartoDTO(){
    }

    public CuartoDTO(CuartoEntity cuartoEntity){
        if(cuartoEntity != null){
            this.id = cuartoEntity.getId();
            this.nombre = cuartoEntity.getNombre();
            this.descripcion = cuartoEntity.getDescripcion();
            this.costoArriendo = cuartoEntity.getCostoArriendo();
            this.vivienda = new ViviendaDTO(cuartoEntity.getVivienda());
            this.ocupado = cuartoEntity.isOcupado();
        }
    }

    public CuartoEntity toEntity()
    {
        CuartoEntity cuartoEntity = new CuartoEntity();
        cuartoEntity.setId(id);
        cuartoEntity.setNombre(nombre);
        cuartoEntity.setDescripcion(descripcion);
        cuartoEntity.setCostoArriendo(costoArriendo);
        if (this.vivienda != null)
            cuartoEntity.setVivienda(this.vivienda.toEntity());
        cuartoEntity.setOcupado(ocupado);
        return cuartoEntity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getCostoArriendo() {
        return costoArriendo;
    }

    public void setCostoArriendo(Integer costoArriendo) {
        this.costoArriendo = costoArriendo;
    }

    public ViviendaDTO getVivienda() {
        return vivienda;
    }

    public void setVivienda(ViviendaDTO vivienda) {
        this.vivienda = vivienda;
    }
    
    /**
     * @return the ocupado
     */
    public boolean isOcupado() {
        return ocupado;
    }

    /**
     * @param ocupado the ocupado to set
     */
    public void setOcupado(boolean ocupado) {
        this.ocupado = ocupado;
    }
    
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
    
}
