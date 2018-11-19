/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.vivienda.dtos;

import co.edu.uniandes.csw.vivienda.entities.CuartoEntity;
import java.io.Serializable;

/**
 *
 * @author Daniel Giraldo
 */
public class CuartoDTO implements Serializable {
    private Long id;
    private String nombre;
    private String descripcion;
    private Integer costoArriendo;

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
        }
    }

    public CuartoEntity toEntity(){
        CuartoEntity cuartoEntity = new CuartoEntity();
        cuartoEntity.setNombre(nombre);
        cuartoEntity.setDescripcion(descripcion);
        cuartoEntity.setCostoArriendo(costoArriendo);
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
}
