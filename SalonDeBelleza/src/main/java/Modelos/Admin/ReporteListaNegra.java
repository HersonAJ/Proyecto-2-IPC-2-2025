/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelos.Admin;

import java.sql.Date;
import java.sql.Time;

/**
 *
 * @author herson
 */
public class ReporteListaNegra {
    private Integer idCliente;
    private String nombreCliente;
    private Integer idCita;
    private Date fechaCita;
    private Time horaCita;
    private String estadoCita;
    private String nombreServicio;
    private String motivoListaNegra;
    private String estadoListaNegra;

    public ReporteListaNegra(Integer idCliente, String nombreCliente, Integer idCita, Date fechaCita, Time horaCita,
                             String estadoCita, String nombreServicio, String motivoListaNegra, String estadoListaNegra) {
        this.idCliente = idCliente;
        this.nombreCliente = nombreCliente;
        this.idCita = idCita;
        this.fechaCita = fechaCita;
        this.horaCita = horaCita;
        this.estadoCita = estadoCita;
        this.nombreServicio = nombreServicio;
        this.motivoListaNegra = motivoListaNegra;
        this.estadoListaNegra = estadoListaNegra;
    }

    public Integer getIdCliente() {
        return idCliente;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public Integer getIdCita() {
        return idCita;
    }

    public Date getFechaCita() {
        return fechaCita;
    }

    public Time getHoraCita() {
        return horaCita;
    }

    public String getEstadoCita() {
        return estadoCita;
    }

    public String getNombreServicio() {
        return nombreServicio;
    }

    public String getMotivoListaNegra() {
        return motivoListaNegra;
    }

    public String getEstadoListaNegra() {
        return estadoListaNegra;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public void setIdCita(Integer idCita) {
        this.idCita = idCita;
    }

    public void setFechaCita(Date fechaCita) {
        this.fechaCita = fechaCita;
    }

    public void setHoraCita(Time horaCita) {
        this.horaCita = horaCita;
    }

    public void setEstadoCita(String estadoCita) {
        this.estadoCita = estadoCita;
    }

    public void setNombreServicio(String nombreServicio) {
        this.nombreServicio = nombreServicio;
    }

    public void setMotivoListaNegra(String motivoListaNegra) {
        this.motivoListaNegra = motivoListaNegra;
    }

    public void setEstadoListaNegra(String estadoListaNegra) {
        this.estadoListaNegra = estadoListaNegra;
    }

    

}
