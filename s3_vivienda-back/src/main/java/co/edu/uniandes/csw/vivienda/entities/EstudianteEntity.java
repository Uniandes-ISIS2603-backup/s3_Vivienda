/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.vivienda.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import static javax.persistence.CascadeType.ALL;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import uk.co.jemos.podam.common.PodamExclude;
/**
 *
 * @author estudiante
 */
@Entity
public class EstudianteEntity extends BaseEntity implements Serializable{
    private String nombre;
    private String login;
    private String password;
    
    @PodamExclude
    @ManyToOne
    private UniversidadEntity universidad;
    
    @PodamExclude
    @OneToOne(mappedBy="estudiante",cascade=ALL, orphanRemoval=true)
    private ContratoEntity contrato;
    
    @PodamExclude
    @OneToMany(mappedBy="estudiante", cascade=ALL, orphanRemoval=true)
    private List<CalificacionEntity> calificaciones = new ArrayList<CalificacionEntity>();
    
      
    public String getNombre(){
        return nombre;
    }
    public String getLogin(){
        return login;
    }
    public String getPassword(){
        return password;
    }
    public List <CalificacionEntity> getCalificaciones(){
        return calificaciones;
    }
    public UniversidadEntity getUniversidad(){
        return universidad;
    }
    public ContratoEntity getContrato(){
        return contrato;
    }

    
    public void setNombre(String n){
        this.nombre=n;
    }
    public void setLogin(String l){
        this.login=l;
    }
    public void setPassword(String p){
        this.password = p;
    }
    public void setCalificaciones(List<CalificacionEntity> cal){
        this.calificaciones=cal;
    }
    public void setUniversidad(UniversidadEntity uni){
        this.universidad=uni;
    }
    public void setContrato(ContratoEntity cont){
        this.contrato=cont;
    }
}
