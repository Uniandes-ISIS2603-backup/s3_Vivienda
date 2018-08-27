/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.vivienda.dtos;
import co.edu.uniandes.csw.vivienda.entities.EstudianteEntity;
import java.io.Serializable;
/**
 *
 * @author estudiante
 */
public class EstudianteDTO implements Serializable {
    private Long id;
    private String nombre;
    private String login;
    private String password;
    
    public EstudianteDTO (){}
    
    public EstudianteDTO(EstudianteEntity estudianteEntity){
        if(estudianteEntity!=null){
            this.nombre = estudianteEntity.getNombre();
            this.login = estudianteEntity.getLogin();
            this.password = estudianteEntity.getPassword();
        }
    }
    public EstudianteEntity toEntity(){
        EstudianteEntity entity = new EstudianteEntity();
        
        entity.setNombre(nombre);
        entity.setLogin(login);
        entity.setPassword(password);
        return entity;
    }
    
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
