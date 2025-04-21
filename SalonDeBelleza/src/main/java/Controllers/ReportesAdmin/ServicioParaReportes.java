/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllers.ReportesAdmin;

/**
 *
 * @author herson
 */
public class ServicioParaReportes {

    private int idServicio;
    private String nombreServicio;

    // Constructor
    public ServicioParaReportes(int idServicio, String nombreServicio) {
        this.idServicio = idServicio;
        this.nombreServicio = nombreServicio;
    }

    // Getters y Setters
    public int getIdServicio() {
        return idServicio;
    }

    public void setIdServicio(int idServicio) {
        this.idServicio = idServicio;
    }

    public String getNombreServicio() {
        return nombreServicio;
    }

    public void setNombreServicio(String nombreServicio) {
        this.nombreServicio = nombreServicio;
    }
}
