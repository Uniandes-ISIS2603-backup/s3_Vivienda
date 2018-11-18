/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.vivienda.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import javax.persistence.*;

import uk.co.jemos.podam.common.PodamExclude;


/**
 * Clase que representa una vivienda en la persistencia y permite su serialización
 * @author DANIEL
 */

@Entity
public class ViviendaEntity extends BaseEntity implements Serializable{

    private static final Logger LOGGER = Logger.getLogger(ViviendaEntity.class.getName());

    private String direccion;
    private String ciudad;
    private String nombre;

    @Lob
    @Column(name="descripcion", length=5000)
    private String descripcion;
    private String tipo;
    private Float latitud;
    private Float longitud;
    private String imgUrl;
    
    @ElementCollection
    private List<String> serviciosIncluidos = new ArrayList<>();
    
    @PodamExclude
    @OneToMany(
        cascade = CascadeType.ALL,
        mappedBy="vivienda",
        fetch=javax.persistence.FetchType.LAZY)
    private List<CuartoEntity> cuartos = new ArrayList<>();
    
    @PodamExclude
    @OneToMany(
        cascade = CascadeType.ALL,
        mappedBy="vivienda",
        fetch=javax.persistence.FetchType.LAZY)
    private List<ContratoEntity> contratos = new ArrayList<>();
    
    @PodamExclude
    @OneToMany(
        cascade = CascadeType.ALL,
        mappedBy="vivienda",
        fetch=javax.persistence.FetchType.LAZY)
    private List<ServicioAdicionalEntity> serviciosAdicionales = new ArrayList<>();
    
    @PodamExclude
    @OneToMany(
        cascade = CascadeType.ALL,
        mappedBy="vivienda",
        fetch=javax.persistence.FetchType.LAZY)
    private List<SitioInteresEntity> sitiosDeInteres = new ArrayList<>();
    
    @PodamExclude
    @OneToMany(
        cascade = CascadeType.ALL,
        mappedBy="vivienda",
        fetch=javax.persistence.FetchType.LAZY)
    private List<CalificacionEntity> calificaciones = new ArrayList<>();
    
    @PodamExclude
    @ManyToOne
    private ArrendadorEntity arrendador;

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

    public List<String> getServiciosIncluidos() {
        return serviciosIncluidos;
    }

    public void setServiciosIncluidos(List<String> serviciosIncluidos) {
        this.serviciosIncluidos = serviciosIncluidos;
    }

    public List<CuartoEntity> getCuartos() {
        return cuartos;
    }

    public void setCuartos(List<CuartoEntity> cuartos) {
        this.cuartos = cuartos;
    }

    public List<ContratoEntity> getContratos() {
        return contratos;
    }

    public void setContratos(List<ContratoEntity> contratos) {
        this.contratos = contratos;
    }

    public List<ServicioAdicionalEntity> getServiciosAdicionales() {
        return serviciosAdicionales;
    }

    public void setServiciosAdicionales(List<ServicioAdicionalEntity> serviciosAdicionales) {
        this.serviciosAdicionales = serviciosAdicionales;
    }

    public List<SitioInteresEntity> getSitiosDeInteres() {
        return sitiosDeInteres;
    }

    public void setSitiosDeInteres(List<SitioInteresEntity> sitiosDeInteres) {
        this.sitiosDeInteres = sitiosDeInteres;
    }

    public List<CalificacionEntity> getCalificaciones() {
        return calificaciones;
    }

    public void setCalificaciones(List<CalificacionEntity> calificaciones) {
        this.calificaciones = calificaciones;
    }

    public ArrendadorEntity getArrendador() {
        return arrendador;
    }

    public void setArrendador(ArrendadorEntity arrendador) {
        this.arrendador = arrendador;
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

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
