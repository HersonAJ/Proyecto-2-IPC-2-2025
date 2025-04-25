/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelos.Admin;

/**
 *
 * @author herson
 */
public class ReporteClientesGastos {
    private Integer idCliente;
    private String nombreCliente;
    private Double totalGastado;
    private String detalleCitas;

    public ReporteClientesGastos(Integer idCliente, String nombreCliente, Double totalGastado, String detalleCitas) {
        this.idCliente = idCliente;
        this.nombreCliente = nombreCliente;
        this.totalGastado = totalGastado;
        this.detalleCitas = detalleCitas;
    }

    public Integer getIdCliente() {
        return idCliente;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public Double getTotalGastado() {
        return totalGastado;
    }

    public String getDetalleCitas() {
        return detalleCitas;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public void setTotalGastado(Double totalGastado) {
        this.totalGastado = totalGastado;
    }

    public void setDetalleCitas(String detalleCitas) {
        this.detalleCitas = detalleCitas;
    }
    
}

