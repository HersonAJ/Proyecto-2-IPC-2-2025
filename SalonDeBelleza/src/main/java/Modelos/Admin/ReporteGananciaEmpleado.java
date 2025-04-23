/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelos.Admin;

/**
 *
 * @author herson
 */
public class ReporteGananciaEmpleado {
    private Integer idEmpleado; 
    private String nombreEmpleado; 
    private Double totalGanancia; 
    private String detalleCitas; 

    public Integer getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(Integer idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public String getNombreEmpleado() {
        return nombreEmpleado;
    }

    public void setNombreEmpleado(String nombreEmpleado) {
        this.nombreEmpleado = nombreEmpleado;
    }

    public Double getTotalGanancia() {
        return totalGanancia;
    }

    public void setTotalGanancia(Double totalGanancia) {
        this.totalGanancia = totalGanancia;
    }

    public String getDetalleCitas() {
        return detalleCitas;
    }

    public void setDetalleCitas(String detalleCitas) {
        this.detalleCitas = detalleCitas;
    }
}

