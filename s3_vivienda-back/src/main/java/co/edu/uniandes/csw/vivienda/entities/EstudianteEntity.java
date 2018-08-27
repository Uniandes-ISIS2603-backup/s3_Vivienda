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
 * @author estudiante
 */
@Entity
public class EstudianteEntity extends BaseEntity implements Serializable{
    private String nombre;
    private String login;
    private String password;
    
    public String getNombre(){
        return nombre;
    }
    public String getLogin(){
        return login;
    }
    public String getPassword(){
        return password;
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
}
