/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelos;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 *
 * @author herson
 */
public class Cita {
    
    private int idCita;
    private int idServicio;
    private int idCliente;
    private int idEmpleado;
    private LocalDate fechaCita;
    private LocalTime horaCita;
    private String estado;
    private boolean facturaGenerada;

    public int getIdCita() {
        return idCita;
    }

    public int getIdServicio() {
        return idServicio;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public int getIdEmpleado() {
        return idEmpleado;
    }

    public LocalDate getFechaCita() {
        return fechaCita;
    }

    public LocalTime getHoraCita() {
        return horaCita;
    }

    public String getEstado() {
        return estado;
    }

    public boolean isFacturaGenerada() {
        return facturaGenerada;
    }

    public void setIdCita(int idCita) {
        this.idCita = idCita;
    }

    public void setIdServicio(int idServicio) {
        this.idServicio = idServicio;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public void setIdEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public void setFechaCita(LocalDate fechaCita) {
        this.fechaCita = fechaCita;
    }

    public void setHoraCita(LocalTime horaCita) {
        this.horaCita = horaCita;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void setFacturaGenerada(boolean facturaGenerada) {
        this.facturaGenerada = facturaGenerada;
    }
    
    @Override
    public String toString() {
        return "Cita{" +
                "idCita=" + idCita +
                ", idServicio=" + idServicio +
                ", idCliente=" + idCliente +
                ", idEmpleado=" + idEmpleado +
                ", fechaCita='" + fechaCita + '\'' +
                ", horaCita='" + horaCita + '\'' +
                ", estado='" + estado + '\'' +
                ", facturaGenerada=" + facturaGenerada +
                '}';
    }
}
