/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.vivienda.dtos;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("viviendas")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class ViviendaDTO {
 
    private String nombre;
    private String descripcion;
    private String tipo;
    private String[] serviciosIncluidos;
    private float latitud;
    private float longitud;
    
}
