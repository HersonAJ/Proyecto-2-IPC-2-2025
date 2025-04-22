/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelos.Admin;

/**
 *
 * @author herson
 */
public class ReporteClientes {

    private Integer idCliente;           
    private String nombreCliente;        
    private Integer totalCitas;          
    private String detalleCitas;         

    public ReporteClientes(Integer idCliente, String nombreCliente, Integer totalCitas, String detalleCitas) {
        this.idCliente = idCliente;
        this.nombreCliente = nombreCliente;
        this.totalCitas = totalCitas;
        this.detalleCitas = detalleCitas;
    }

    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public Integer getTotalCitas() {
        return totalCitas;
    }

    public void setTotalCitas(Integer totalCitas) {
        this.totalCitas = totalCitas;
    }

    public String getDetalleCitas() {
        return detalleCitas;
    }

    public void setDetalleCitas(String detalleCitas) {
        this.detalleCitas = detalleCitas;
    }
}

