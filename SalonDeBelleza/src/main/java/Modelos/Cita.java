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
    private int idCliente;
    private LocalDate fechaCita;
    private LocalTime horaCita;
    private String estado;
    private boolean facturaGenerada;
    
    //elementos para obtener la cita completa
    // Detalles del servicio
    private int idServicio;
    private String nombreServicio;
    private String descripcionServicio;
    private int duracionServicio;
    private double precioServicio;

    // Detalles del empleado
    private int idEmpleado;
    private String nombreEmpleado;
    
    //detalles del cliente
    private String nombreCliente;
    
    //factura asociada cuando la cita sea atendida
    private Integer idFactura;

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

    public String getNombreServicio() {
        return nombreServicio;
    }

    public String getDescripcionServicio() {
        return descripcionServicio;
    }

    public int getDuracionServicio() {
        return duracionServicio;
    }

    public double getPrecioServicio() {
        return precioServicio;
    }

    public String getNombreEmpleado() {
        return nombreEmpleado;
    }

    public String getNombreCliente() {
        return nombreCliente;
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

    public void setNombreServicio(String nombreServicio) {
        this.nombreServicio = nombreServicio;
    }

    public void setDescripcionServicio(String descripcionServicio) {
        this.descripcionServicio = descripcionServicio;
    }

    public void setDuracionServicio(int duracionServicio) {
        this.duracionServicio = duracionServicio;
    }

    public void setPrecioServicio(double precioServicio) {
        this.precioServicio = precioServicio;
    }

    public void setNombreEmpleado(String nombreEmpleado) {
        this.nombreEmpleado = nombreEmpleado;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public Integer getIdFactura() {
        return idFactura;
    }

    public void setIdFactura(Integer idFactura) {
        this.idFactura = idFactura;
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
