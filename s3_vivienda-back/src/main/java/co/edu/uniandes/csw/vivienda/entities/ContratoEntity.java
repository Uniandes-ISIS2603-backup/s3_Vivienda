package co.edu.uniandes.csw.vivienda.entities;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.persistence.*;

import uk.co.jemos.podam.common.PodamExclude;

/**
 * @author estudiante
 */
@Entity
public class ContratoEntity extends BaseEntity implements Serializable {

//    @Temporal(TemporalType.DATE)
    private String fechaInicio;

//    @Temporal(TemporalType.DATE)
    private String fechaFin;

    private Integer metodoPago;

    @PodamExclude
    @OneToOne(fetch = javax.persistence.FetchType.LAZY)
    private EstudianteEntity estudiante;
    
    @PodamExclude
    @OneToOne(fetch = javax.persistence.FetchType.LAZY)
    private ArrendadorEntity arrendador;
        
    @PodamExclude
    @OneToOne(fetch = javax.persistence.FetchType.LAZY)
    private CuartoEntity cuarto;

    @PodamExclude
    @ManyToOne(cascade=CascadeType.MERGE, fetch=javax.persistence.FetchType.LAZY)
    private ViviendaEntity vivienda;

    @PodamExclude
    @OneToMany(
            mappedBy = "contrato",
            cascade = CascadeType.PERSIST, orphanRemoval = true,
            fetch = javax.persistence.FetchType.LAZY)
    private List<ServicioAdicionalEntity> serviciosAdicionalesAgregados;

    /**
     * @return the fechaInicio
     */
    public String getFechaInicio() {
        return fechaInicio;
    }

    /**
     * @param fechaInicio the fechaInicio to set
     */
    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    /**
     * @return the fechaFin
     */
    public String getFechaFin() {
        return fechaFin;
    }

    /**
     * @param fechaFin the fechaFin to set
     */
    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    /**
     * @return the metodoPago
     */
    public Integer getMetodoPago() {
        return metodoPago;
    }

    /**
     * @param metodoPago the metodoPago to set
     */
    public void setMetodoPago(int metodoPago) {
        this.metodoPago = metodoPago;
    }

    /**
     * Devuelve el estudiante al que pertenece el contrato.
     *
     * @return Una entidad de estudiante.
     */
    public EstudianteEntity getEstudiante() {
        return estudiante;
    }

    /**
     * Modifica el estudiante al que pertenece el contrato.
     *
     * @param estudianteEntity El nuevo estudiante.
     */
    public void setEstudiante(EstudianteEntity estudianteEntity) {
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
    public void setVivienda(ViviendaEntity viviendaEntity) {
        this.vivienda = viviendaEntity;
    }

    public List<ServicioAdicionalEntity> getServiciosAdicionalesAgregados() {
        return serviciosAdicionalesAgregados;
    }

    public void setServiciosAdicionalesAgregados(List<ServicioAdicionalEntity> serviciosAdicionalesAgregados) {
        this.serviciosAdicionalesAgregados = serviciosAdicionalesAgregados;
    }

    
    public static Date getDateWithoutTimeUsingFormat(Date date) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        return formatter.parse(formatter.format(date));
    }

    /**
     * @return the arrendador
     */
    public ArrendadorEntity getArrendador() {
        return arrendador;
    }

    /**
     * @param arrendador the arrendador to set
     */
    public void setArrendador(ArrendadorEntity arrendador) {
        this.arrendador = arrendador;
    }

    /**
     * @return the cuarto
     */
    public CuartoEntity getCuarto() {
        return cuarto;
    }

    /**
     * @param cuarto the cuarto to set
     */
    public void setCuarto(CuartoEntity cuarto) {
        this.cuarto = cuarto;
    }
}
