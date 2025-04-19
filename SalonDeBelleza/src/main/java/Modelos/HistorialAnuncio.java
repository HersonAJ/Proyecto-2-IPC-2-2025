/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelos;

import java.time.LocalDateTime;

/**
 *
 * @author herson
 */
public class HistorialAnuncio {
    
    private int idVisualizacion;
    private int idAnuncio;
    private LocalDateTime fechaVisualizacion;
    private String urlMostrada;

    public int getIdVisualizacion() {
        return idVisualizacion;
    }

    public int getIdAnuncio() {
        return idAnuncio;
    }

    public LocalDateTime getFechaVisualizacion() {
        return fechaVisualizacion;
    }

    public String getUrlMostrada() {
        return urlMostrada;
    }

    public void setIdVisualizacion(int idVisualizacion) {
        this.idVisualizacion = idVisualizacion;
    }

    public void setIdAnuncio(int idAnuncio) {
        this.idAnuncio = idAnuncio;
    }

    public void setFechaVisualizacion(LocalDateTime fechaVisualizacion) {
        this.fechaVisualizacion = fechaVisualizacion;
    }

    public void setUrlMostrada(String urlMostrada) {
        this.urlMostrada = urlMostrada;
    }
    
}
