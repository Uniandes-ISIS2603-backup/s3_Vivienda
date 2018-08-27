/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.vivienda.dtos;

/**
 *
 * @author estudiante
 */
public class ArrendadorDTO {
    private long id;
    private String nombre;
    private String login;
    private String password;
    
    public ArrendadorDTO()
    { 
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
}
