/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelos;

/**
 *
 * @author herson
 */
public class ReporteServicios {
    
    private String nombreServicio;
    private int totalReservas;
    private double totalIngresos;

    public double getTotalIngresos() {
        return totalIngresos;
    }

    public void setTotalIngresos(double totalIngresos) {
        this.totalIngresos = totalIngresos;
    }

    public String getNombreServicio() {
        return nombreServicio;
    }

    public int getTotalReservas() {
        return totalReservas;
    }

    public void setNombreServicio(String nombreServicio) {
        this.nombreServicio = nombreServicio;
    }

    public void setTotalReservas(int totalReservas) {
        this.totalReservas = totalReservas;
    }
}
