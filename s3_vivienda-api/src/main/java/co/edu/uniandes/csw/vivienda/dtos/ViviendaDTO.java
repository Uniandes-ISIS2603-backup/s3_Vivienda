/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.vivienda.dtos;

import co.edu.uniandes.csw.vivienda.entities.ViviendaEntity;
import java.io.Serializable;
import java.util.List;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;


public class ViviendaDTO implements Serializable{
    private String direccion;
    private String ciudad;
    private Long id;
    private String nombre;
    private String descripcion;
    private String tipo;
    private List<String> serviciosIncluidos;
    private Float latitud;
    private Float longitud;
    private String imgUrl;

    /*
    * Relación a un arrendador  
    * dado que esta tiene cardinalidad 1.
    */
    private ArrendadorDTO arrendador;
    
    public ViviendaDTO(){
    }
    
    public ViviendaDTO(ViviendaEntity viviendaEntity){
        if(viviendaEntity!=null){
            this.direccion = viviendaEntity.getDireccion();
            this.ciudad = viviendaEntity.getCiudad();
            this.id = viviendaEntity.getId();
            this.nombre = viviendaEntity.getNombre();
            this.descripcion = viviendaEntity.getDescripcion();
            this.tipo = viviendaEntity.getTipo();
            this.serviciosIncluidos = viviendaEntity.getServiciosIncluidos();
            this.latitud = viviendaEntity.getLatitud();
            this.longitud = viviendaEntity.getLongitud();
            this.imgUrl = viviendaEntity.getImgUrl();
            if (viviendaEntity.getArrendador() != null)
                this.arrendador = new ArrendadorDTO(viviendaEntity.getArrendador());
        }
    }
    
    public ViviendaEntity toEntity(){
        ViviendaEntity viviendaEntity = new ViviendaEntity();
        viviendaEntity.setDireccion(direccion);
        viviendaEntity.setCiudad(ciudad);
        viviendaEntity.setId(id);
        viviendaEntity.setNombre(nombre);
        viviendaEntity.setDescripcion(descripcion);
        viviendaEntity.setTipo(tipo);
        viviendaEntity.setServiciosIncluidos(serviciosIncluidos);
        viviendaEntity.setLatitud(latitud);
        viviendaEntity.setLongitud(longitud);
        viviendaEntity.setImgUrl(imgUrl);
        if (arrendador != null)
            viviendaEntity.setArrendador(arrendador.toEntity());
        return viviendaEntity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public List<String> getServiciosIncluidos() {
        return serviciosIncluidos;
    }

    public void setServiciosIncluidos(List<String> serviciosIncluidos) {
        this.serviciosIncluidos = serviciosIncluidos;
    }

    public Float getLatitud() {
        return latitud;
    }

    public void setLatitud(Float latitud) {
        this.latitud = latitud;
    }

    public Float getLongitud() {
        return longitud;
    }

    public void setLongitud(Float longitud) {
        this.longitud = longitud;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }
    
    public ArrendadorDTO getArrendador(){
        return arrendador;
    }
    
    public void setArrendador(ArrendadorDTO arrendador){
        this.arrendador = arrendador;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
    
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}