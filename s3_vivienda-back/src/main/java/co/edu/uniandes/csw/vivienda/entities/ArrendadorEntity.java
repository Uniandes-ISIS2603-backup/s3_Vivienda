/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.vivienda.entities;

import uk.co.jemos.podam.common.PodamExclude;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.FetchType;
/**
 *
 * @author msalcedo
 */
@Entity
public class ArrendadorEntity extends BaseEntity implements Serializable{
    
    private String nombre;
    private String login;
    private String password;

    @PodamExclude
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "arrendador", fetch=FetchType.LAZY)
    private List<ViviendaEntity> viviendas = new ArrayList<>();
    
    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the login
     */
    public String getLogin() {
        return login;
    }

    /**
     * @param login the login to set
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }
    
        /**
     * Obtiene la colección de viviendas del arrendador.
     *
     * @return colección viviendas.
     */
    public List<ViviendaEntity> getViviendas() {
        return viviendas;
    }
    
    public void setViviendas(List<ViviendaEntity> viviendas) {
        this.viviendas = viviendas;
    }
    
}
