/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.vivienda.dtos;
import co.edu.uniandes.csw.vivienda.entities.EstudianteEntity;
import java.io.Serializable;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
/**
 *
 * @author Juan Manuel Castillo
 */
public class EstudianteDTO implements Serializable {
    private Long id;
    private String nombre;
    private String login;
    private String password;
    
    /*
    * Relación a una universidad  
    * dado que esta tiene cardinalidad 1.
    */
    private UniversidadDTO universidad;
    
    
    /**
     * Constructor por defecto
     */
    public EstudianteDTO (){}
    
    /**
     * Constructor a partir de la entidad
     *
     * @param estudianteEntity La entidad del estudiante
     */
    public EstudianteDTO(EstudianteEntity estudianteEntity){
        if(estudianteEntity!=null){
            this.id = estudianteEntity.getId();
            this.nombre = estudianteEntity.getNombre();
            this.login = estudianteEntity.getLogin();
            this.password = estudianteEntity.getPassword();
            if (estudianteEntity.getUniversidad() != null)
                this.universidad = new UniversidadDTO(estudianteEntity.getUniversidad());
        }
    }
    
     /**
     * Método para transformar el DTO a una entidad.
     *
     * @return La entidad del estudiante asociado.
     */
    public EstudianteEntity toEntity(){
        EstudianteEntity entity = new EstudianteEntity();
        entity.setId(id);
        entity.setNombre(nombre);
        entity.setLogin(login);
        entity.setPassword(password);
        if (universidad != null)
            entity.setUniversidad(universidad.toEntity());
        return entity;
    }
    
    /**
     * Devuelve el id del estudiante
     *
     * @return the id
     */
    public Long getId(){
        return id;
    }
    
    /**
     * Devuelve el nombre del estudiante
     *
     * @return the nombre
     */
    public String getNombre(){
        return nombre;
    }
    
    /**
     * Devuelve el login del estudiante
     *
     * @return the login
     */
    public String getLogin(){
        return login;
    }
    
    /**
     * Devuelve el password del estudiante
     *
     * @return the password
     */
    public String getPassword(){
        return password;
    }
    
    /**
     * Devuelve el universidad del estudiante
     *
     * @return the universidad
     */
    public UniversidadDTO getUniversidad(){
        return universidad;
    }

    
    /**
     * Modifica el id del estudiante.
     *
     * @param id the id to set
     */
    public void setId(Long id){
        this.id=id;
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
     * Modifica el universidad del estudiante.
     *
     * @param uni the universidad to set
     */
    public void setUniversidad(UniversidadDTO uni){
        this.universidad=uni;
    }
    

    
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
