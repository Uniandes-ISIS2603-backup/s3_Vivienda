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
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import uk.co.jemos.podam.common.PodamExclude;
/**
 *
 * @author Juan Manuel Castillo
 */
@Entity
public class EstudianteEntity extends BaseEntity implements Serializable{
    
    private String nombre;
    private String login;
    private String password;
    
    @PodamExclude
    @ManyToOne(cascade=CascadeType.MERGE, fetch=javax.persistence.FetchType.LAZY)
    private UniversidadEntity universidad;
    
    @PodamExclude
    @OneToOne(mappedBy="estudiante", cascade={CascadeType.MERGE, CascadeType.REMOVE}, orphanRemoval=true, fetch=javax.persistence.FetchType.LAZY)
    private ContratoEntity contrato;
    
    @PodamExclude
    @OneToMany(mappedBy="estudiante", cascade=CascadeType.ALL, orphanRemoval=true)
    private List<CalificacionEntity> calificaciones = new ArrayList<>();
    
    /**
     * Devuelve el nombre del estudiante.
     *
     * @return the nombre
     */  
    public String getNombre(){
        return nombre;
    }
    
    /**
     * Devuelve el login del estudiante.
     *
     * @return the login
     */
    public String getLogin(){
        return login;
    }
    
    /**
     * Devuelve el password del estudiante.
     *
     * @return the password
     */
    public String getPassword(){
        return password;
    }
    
    /**
     * Devuelve la lista de calificaciones del estudiante.
     *
     * @return the calificaciones
     */
    public List <CalificacionEntity> getCalificaciones(){
        return calificaciones;
    }
    
    /**
     * Devuelve la universidad del estudiante.
     *
     * @return the universidad
     */
    public UniversidadEntity getUniversidad(){
        return universidad;
    }
    
    /**
     * Devuelve el contrato del estudiante.
     *
     * @return the contrato
     */
    public ContratoEntity getContrato(){
        return contrato;
    }

    /**
     * Modifica el nombre del estudiante.
     *
     * @param n the nombre to set
     */
    public void setNombre(String n){
        this.nombre=n;
    }
    
    /**
     * Modifica el login del estudiante.
     *
     * @param l the login to set
     */
    public void setLogin(String l){
        this.login=l;
    }
    
    /**
     * Modifica el password del estudiante.
     *
     * @param p the password to set
     */
    public void setPassword(String p){
        this.password = p;
    }
    
    /**
     * Modifica las calificaciones del estudiante.
     *
     * @param cal the calificaciones to set
     */
    public void setCalificaciones(List<CalificacionEntity> cal){
        this.calificaciones=cal;
    }
    
    /**
     * Modifica la universidad del estudiante.
     *
     * @param uni the universidad to set
     */
    public void setUniversidad(UniversidadEntity uni){
        this.universidad=uni;
    }
    
    /**
     * Modifica el contrato del estudiante.
     *
     * @param contrato the contrato to set
     */
    public void setContrato(ContratoEntity contrato){
        this.contrato = contrato;
    }
}
