/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.vivienda.entities;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import uk.co.jemos.podam.common.PodamExclude;

/**
 *
 * @author Paula Molina
 */
@Entity

public class ServicioAdicionalEntity extends BaseEntity implements Serializable{

  private String nombre;
  private String descripcion;
  private Float costo; 
  
  @PodamExclude
  @ManyToOne(cascade = CascadeType.PERSIST)
  private ViviendaEntity vivienda;
  
  @PodamExclude
  @ManyToOne
  private ContratoEntity contrato;
  
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
    
    public ContratoEntity getContrato()
    {
        return contrato;
    }
    
    public void setContrato(ContratoEntity contrato)
    {
        this.contrato = contrato;
    }
     
}
