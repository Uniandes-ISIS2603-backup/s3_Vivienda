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
public class SitioInteresDTO {
    private long id;
    private String nombre;
    private String descripcion;
    private float latitud;
    private float longitud;
    
    public SitioInteresDTO()
    {
        
    }
    
        /**
     * Devuelve el ID del sitioInteres.
     *
     * @return the id
     */
    public Long getId() {
        return id;
    }
    
        /**
     * Modifica el ID del sitioInteres.
     *
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Devuelve el nombre del sitioInteres.
     *
     * @return the name
     */
    public String getNombre() {
        return nombre;
    }
    
       /**
     * Modifica el nombre del sitioInteres.
     *
     * @param pNombre the name to set
     */
    public void setName(String pNombre) {
        this.nombre = pNombre;
    }
    
    public String getDescripcion()
    {
        return descripcion;
    }
    
    public void setDescripcion(String pDescripcion)
    {
        this.descripcion=pDescripcion;
    }
    
    public float getLatitud()
    {
        return latitud;
    }
    
    public float getLongitud()
    {
        return longitud;
    }
    
    public void setLatitud(float pLatitud)
    {
        this.latitud=pLatitud;
        
    }
 
    public void setLongitud(float pLongitud)
    {
        this.longitud=pLongitud;
    }
}
