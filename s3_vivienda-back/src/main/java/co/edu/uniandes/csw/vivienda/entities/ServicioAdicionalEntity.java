/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.vivienda.entities;

import java.io.Serializable;
import javax.persistence.Entity;

/**
 *
 * @author Paula Molina
 */
@Entity

public class ServicioAdicionalEntity extends BaseEntity implements Serializable{

  private String nombre;
  private String descripcion;
  private Float costo; 
  
  private ViviendaEntity vivienda;
  private EstudianteEntity estudiante;
  
  public String getNombre() {
        return nombre;
  }
  
  public String getDescripcion() {
        return descripcion;
  }
  
  public Float getCosto() {
        return costo;
  }

  public void setNombre(String nombre) {
        this.nombre = nombre;
  }
  
  public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
  }

 public void setCosto(Float costo) {
        this.costo = costo;
  }
 
 public ViviendaEntity getVivienda() {
        return vivienda;
    }

    public void setVivienda(ViviendaEntity viviendaEntity) {
        this.vivienda = viviendaEntity;
    }
     
}
