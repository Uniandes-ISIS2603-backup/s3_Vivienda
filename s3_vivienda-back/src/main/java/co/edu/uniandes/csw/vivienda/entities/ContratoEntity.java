/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.vivienda.entities;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.*;

import uk.co.jemos.podam.common.PodamExclude;

/**
 *
 * @author estudiante
 */
@Entity
public class ContratoEntity extends BaseEntity implements Serializable {

    @Temporal(TemporalType.DATE)
    private Date fechaInicio;

    @Temporal(TemporalType.DATE)
    private Date fechaFin;
    
    private String metodoPago;

    @PodamExclude
    @OneToOne(optional = false, fetch = javax.persistence.FetchType.LAZY)
    private EstudianteEntity estudiante;

    @PodamExclude
    @ManyToOne(optional = false, fetch = javax.persistence.FetchType.LAZY)
    private ViviendaEntity vivienda;

    @PodamExclude
    @OneToMany(
            mappedBy = "contrato",
            fetch = javax.persistence.FetchType.LAZY)
    private List<ServicioAdicionalEntity> serviciosAdicionalesAgregados;

    /**
     * @return the fechaInicio
     */
    public Date getFechaInicio() 
    {
        Date date = null;
        try 
        {
            date= getDateWithoutTimeUsingFormat(fechaInicio);
        } 
        catch (ParseException ex) 
        {
            Logger.getLogger(ContratoEntity.class.getName()).log(Level.SEVERE, null, ex);
        }
        return date;
    }

    /**
     * @param fechaInicio the fechaInicio to set
     */
    public void setFechaInicio(Date fechaInicio)
    {
        this.fechaInicio = fechaInicio;
    }

    /**
     * @return the fechaFin
     */
    public Date getFechaFin() 
    {
                Date date = null;
        try 
        {
            date= getDateWithoutTimeUsingFormat(fechaFin);
        } 
        catch (ParseException ex) 
        {
            Logger.getLogger(ContratoEntity.class.getName()).log(Level.SEVERE, null, ex);
        }
        return date;
    }

    /**
     * @param fechaFin the fechaFin to set
     */
    public void setFechaFin(Date fechaFin)
    {
        this.fechaFin = fechaFin;
    }

    /**
     * @return the metodoPago
     */
    public String getMetodoPago()
    {
        return metodoPago;
    }

    /**
     * @param metodoPago the metodoPago to set
     */
    public void setMetodoPago(String metodoPago)
    {
        this.metodoPago = metodoPago;
    }

    /**
     * Devuelve el estudiante al que pertenece el contrato.
     *
     * @return Una entidad de estudiante.
     */
    public EstudianteEntity getEstudiante() 
    {
        return estudiante;
    }

    /**
     * Modifica el estudiante al que pertenece el contrato.
     *
     * @param estudianteEntity El nuevo estudiante.
     */
    public void setEstudiante(EstudianteEntity estudianteEntity) 
    {
        this.estudiante = estudianteEntity;
    }

    /**
     * Devuelve la vivienda a la que pertenece el contrato.
     *
     * @return Una entidad de vivienda.
     */
    public ViviendaEntity getVivienda() {
        return vivienda;
    }

    /**
     * Modifica la vivienda a la que pertenece el contrato.
     *
     * @param viviendaEntity La nueva vivienda.
     */
    public void setVivienda(ViviendaEntity viviendaEntity)
    {
        this.vivienda = viviendaEntity;
    }

    public List<ServicioAdicionalEntity> getServiciosAdicionalesAgregados()
    {
        return serviciosAdicionalesAgregados;
    }

    public void setServiciosAdicionalesAgregados(List<ServicioAdicionalEntity> serviciosAdicionalesAgregados)
    {
        this.serviciosAdicionalesAgregados = serviciosAdicionalesAgregados;
    }

        public static Date getDateWithoutTimeUsingFormat(Date date) throws ParseException
    {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        return formatter.parse(formatter.format(date));
    }
}
