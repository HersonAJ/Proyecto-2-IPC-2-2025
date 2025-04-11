/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelos;

/**
 *
 * @author herson
 */
import java.util.List;

public class GestionServicio {

    private Servicio servicio; // Datos básicos del servicio
    private byte[] nuevaImagen; // Nueva imagen del servicio (opcional)
    private String nuevoEstado; // Nuevo estado del servicio (Visible/Oculto) (opcional)
    private List<Integer> empleadosIds; // IDs de empleados asignados (opcional)

    // Getters y Setters
    public Servicio getServicio() {
        return servicio;
    }

    public void setServicio(Servicio servicio) {
        this.servicio = servicio;
    }

    public byte[] getNuevaImagen() {
        return nuevaImagen;
    }

    public void setNuevaImagen(byte[] nuevaImagen) {
        this.nuevaImagen = nuevaImagen;
    }

    public String getNuevoEstado() {
        return nuevoEstado;
    }

    public void setNuevoEstado(String nuevoEstado) {
        this.nuevoEstado = nuevoEstado;
    }

    public List<Integer> getEmpleadosIds() {
        return empleadosIds;
    }

    public void setEmpleadosIds(List<Integer> empleadosIds) {
        this.empleadosIds = empleadosIds;
    }
}
