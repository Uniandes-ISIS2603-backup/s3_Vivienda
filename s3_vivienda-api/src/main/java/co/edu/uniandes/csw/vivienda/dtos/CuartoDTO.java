/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.vivienda.dtos;

import co.edu.uniandes.csw.vivienda.entities.CuartoEntity;

/**
 *
 * @author Daniel
 */
public class CuartoDTO {
    private String nombre;
    private String descripcion;

    public CuartoDTO(){
    }

    public CuartoDTO(CuartoEntity cuartoEntity){
        if(cuartoEntity != null){
            this.nombre = cuartoEntity.getNombre();
            this.descripcion = cuartoEntity.getDescripcion();
        }
    }

    public CuartoEntity toEntity(){
        CuartoEntity cuartoEntity = new CuartoEntity();
        cuartoEntity.setNombre(nombre);
        cuartoEntity.setDescripcion(descripcion);
        return cuartoEntity;
    }
}
