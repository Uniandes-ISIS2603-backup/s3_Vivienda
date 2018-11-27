package co.edu.uniandes.csw.vivienda.entities;

import uk.co.jemos.podam.common.PodamExclude;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 *
 * @author Daniel Giraldo
 */
@Entity
public class CuartoEntity extends BaseEntity implements Serializable{

      @PodamExclude
      @ManyToOne
      private ViviendaEntity vivienda;
      
      private String nombre;
      private String descripcion;
      private Integer costoArriendo;
      private boolean ocupado;

      public ViviendaEntity getVivienda() {
            return vivienda;
      }

      public void setVivienda(ViviendaEntity vivienda) {
            this.vivienda = vivienda;
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
      
      
}
