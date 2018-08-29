/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.vivienda.entities;

<<<<<<< HEAD
import java.io.Serializable;
import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlRootElement;
=======
import javax.persistence.Entity;
>>>>>>> 19d70cd8c42e7ce0d880c6d2f08e782807f92fe9

/**
 *
 * @author estudiante
 */
@Entity
<<<<<<< HEAD
@XmlRootElement
public class UniversidadEntity extends BaseEntity implements Serializable
{
    private String nombre;
    private Float latitud;
    private Float longitud;
    
     public String getNombre() {
        return nombre;
    }
     
      public float getLatitud() {
        return latitud;
    }

    public float getLongitud() {
        return longitud;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
     public void setLatitud(Float latitud) {
        this.latitud = latitud;
    }

    public void setLongitud(Float longitud) {
        this.longitud = longitud;
    }
=======
public class UniversidadEntity {
>>>>>>> 19d70cd8c42e7ce0d880c6d2f08e782807f92fe9
    
}
