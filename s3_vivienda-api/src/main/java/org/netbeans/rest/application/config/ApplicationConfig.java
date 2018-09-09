/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.netbeans.rest.application.config;

import java.util.Set;
import javax.ws.rs.core.Application;

/**
 *
 * @author estudiante
 */
@javax.ws.rs.ApplicationPath("webresources")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method.
     * It is automatically populated with
     * all resources defined in the project.
     * If required, comment out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(co.edu.uniandes.csw.vivienda.mappers.BusinessLogicExceptionMapper.class);
        resources.add(co.edu.uniandes.csw.vivienda.mappers.WebApplicationExceptionMapper.class);
        resources.add(co.edu.uniandes.csw.vivienda.resources.ArrendadorResource.class);
        resources.add(co.edu.uniandes.csw.vivienda.resources.ContratoResource.class);
        resources.add(co.edu.uniandes.csw.vivienda.resources.EstudianteResource.class);
        resources.add(co.edu.uniandes.csw.vivienda.resources.EstudianteUniversidadResource.class);
        resources.add(co.edu.uniandes.csw.vivienda.resources.ServicioAdicionalResource.class);
        resources.add(co.edu.uniandes.csw.vivienda.resources.SitioInteresResource.class);
        resources.add(co.edu.uniandes.csw.vivienda.resources.UniversidadResource.class);
        resources.add(co.edu.uniandes.csw.vivienda.resources.ViviendaArrendadorResource.class);
        resources.add(co.edu.uniandes.csw.vivienda.resources.ViviendaCalificacionesResource.class);
        resources.add(co.edu.uniandes.csw.vivienda.resources.ViviendaResource.class);
    }
    
}
