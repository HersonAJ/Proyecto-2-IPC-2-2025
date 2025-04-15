/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelos;

import java.time.LocalDate;

/**
 *
 * @author herson
 */
public class Factura {

    private int idFactura;
    private int idCita;
    private int idCliente;
    private int idEmpleado;
    private int idServicio;
    private double total;
    private LocalDate fechaFactura;
    private String detalles;

    public Factura(int idFactura, int idCita, int idCliente, int idEmpleado, int idServicio,
            double total, LocalDate fechaFactura, String detalles) {
        this.idFactura = idFactura;
        this.idCita = idCita;
        this.idCliente = idCliente;
        this.idEmpleado = idEmpleado;
        this.idServicio = idServicio;
        this.total = total;
        this.fechaFactura = fechaFactura;
        this.detalles = detalles;
    }

    public Factura() {
    }

    public int getIdFactura() {
        return idFactura;
    }

    public void setIdFactura(int idFactura) {
        this.idFactura = idFactura;
    }

    public int getIdCita() {
        return idCita;
    }

    public void setIdCita(int idCita) {
        this.idCita = idCita;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public int getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public int getIdServicio() {
        return idServicio;
    }

    public void setIdServicio(int idServicio) {
        this.idServicio = idServicio;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public LocalDate getFechaFactura() {
        return fechaFactura;
    }

    public void setFechaFactura(LocalDate fechaFactura) {
        this.fechaFactura = fechaFactura;
    }

    public String getDetalles() {
        return detalles;
    }

    public void setDetalles(String detalles) {
        this.detalles = detalles;
    }
}
