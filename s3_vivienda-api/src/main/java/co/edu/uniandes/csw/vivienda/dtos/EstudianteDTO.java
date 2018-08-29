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
 * @author estudiante
 */
public class EstudianteDTO implements Serializable {
    private Long id;
    private String nombre;
    private String login;
    private String password;
    private UniversidadDTO universidad;
    private ContratoDTO contrato;
    
    public EstudianteDTO (){}
    
    public EstudianteDTO(EstudianteEntity estudianteEntity){
        if(estudianteEntity!=null){
            this.id = estudianteEntity.getId();
            this.nombre = estudianteEntity.getNombre();
            this.login = estudianteEntity.getLogin();
            this.password = estudianteEntity.getPassword();
            if (estudianteEntity.getUniversidad() != null)
                this.universidad = new UniversidadDTO(estudianteEntity.getUniversidad());
            if (estudianteEntity.getContrato() != null)
                this.contrato = new ContratoDTO(estudianteEntity.getContrato());
        }
    }
    public EstudianteEntity toEntity(){
        EstudianteEntity entity = new EstudianteEntity();
        entity.setId(id);
        entity.setNombre(nombre);
        entity.setLogin(login);
        entity.setPassword(password);
        if (universidad != null)
            entity.setUniversidad(universidad.toEntity());
        if (contrato != null)
            entity.setContrato(contrato.toEntity());
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
    public UniversidadDTO getUniversidad(){
        return universidad;
    }
    public ContratoDTO getContrato(){
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
    public void setUniversidad(UniversidadDTO uni){
        this.universidad=uni;
    }
    public void setContrato(ContratoDTO cont){
        this.contrato=cont;
    }
    
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
