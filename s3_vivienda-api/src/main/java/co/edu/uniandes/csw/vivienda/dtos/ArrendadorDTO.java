/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.vivienda.dtos;

import co.edu.uniandes.csw.vivienda.entities.ArrendadorEntity;
import java.io.Serializable;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @author estudiante
 */
public class ArrendadorDTO implements Serializable{
    private long id;
    private String nombre;
    private String login;
    private String password;
    
    public ArrendadorDTO()
    { 
    }
    
    public ArrendadorDTO(ArrendadorEntity arrendadorEntity) {
        if (arrendadorEntity != null) {
            this.id = arrendadorEntity.getId();
            this.nombre = arrendadorEntity.getNombre();
            this.login = arrendadorEntity.getLogin();
            this.password = arrendadorEntity.getPassword();
        }
    }
        /**
     * Devuelve el ID del arrendador.
     *
     * @return the id
     */
    public Long getId() {
        return id;
    }
    
        /**
     * Modifica el ID del arrendador.
     *
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Devuelve el nombre del arrendador.
     *
     * @return the name
     */
    public String getNombre() {
        return nombre;
    }
    
       /**
     * Modifica el nombre del arrendador.
     *
     * @param pNombre the name to set
     */
    public void setName(String pNombre) {
        this.nombre = pNombre;
    }
    
        /**
     * Devuelve el login del arrendador.
     *
     * @return the login
     */
    public String getLogin() {
        return login;
    }
    
       /**
     * Modifica el nombre del arrendador.
     *
     * @param pLogin the name to set
     */
    public void setLogin(String pLogin) {
        this.login = pLogin;
    }
    
        /**
     * Devuelve el nombre del arrendador.
     *
     * @return the password
     */
    public String getPassword() {
        return password;
    }
    
       /**
     * Modifica el nombre del arrendador.
     *
     * @param pPassword the name to set
     */
    public void setPassword(String pPassword) {
        this.password = pPassword;
    }
    
        public ArrendadorEntity toEntity() {
        ArrendadorEntity arrendadorEntity = new ArrendadorEntity();
        arrendadorEntity.setId(this.id);
        arrendadorEntity.setNombre(this.nombre);
        arrendadorEntity.setLogin(this.login);
        arrendadorEntity.setPassword(this.password);
        return arrendadorEntity;
    }
    
        @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
