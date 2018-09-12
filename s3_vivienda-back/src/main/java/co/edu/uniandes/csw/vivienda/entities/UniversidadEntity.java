/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.vivienda.entities;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import uk.co.jemos.podam.common.PodamExclude;


/**
 *
 * @author Paula Molina
 */
@Entity
public class UniversidadEntity extends BaseEntity implements Serializable
{
    private String nombre;
    private Float latitud;
    private Float longitud;
    
    @PodamExclude
    @OneToMany(mappedBy = "universidad")
    private List<EstudianteEntity> estudiantes = new ArrayList<EstudianteEntity>();
    
     public String getNombre() {
        return nombre;
    }
     
      public Float getLatitud() {
        return latitud;
    }
     public List<EstudianteEntity> getEstudiantes() {
        return estudiantes;
    }

    public Float getLongitud() {
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
    
    public void setEstudiantes(List<EstudianteEntity> est) {
        this.estudiantes = est;
    }

}
