/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.vivienda.entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author estudiante
 */
@Entity
@XmlRootElement
public class ServicioAdicionalEntity extends BaseEntity implements Serializable{

  private String nombre;
  private String descripcion;
  private Float costo; 
  
  private ViviendaEntity vivienda;
  
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
     
}
