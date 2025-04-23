/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelos;

/**
 *
 * @author herson
 */
public class ReporteAnuncios {
    
    private Integer idAnuncio;
    private String tipoAnuncio;
    private String nombreAnunciante;
    private Integer totalVisualizaciones;
    private String urlsMostradas;

    public Integer getIdAnuncio() {
        return idAnuncio;
    }

    public String getTipoAnuncio() {
        return tipoAnuncio;
    }

    public String getNombreAnunciante() {
        return nombreAnunciante;
    }

    public Integer getTotalVisualizaciones() {
        return totalVisualizaciones;
    }

    public String getUrlsMostradas() {
        return urlsMostradas;
    }

    public void setIdAnuncio(Integer idAnuncio) {
        this.idAnuncio = idAnuncio;
    }

    public void setTipoAnuncio(String tipoAnuncio) {
        this.tipoAnuncio = tipoAnuncio;
    }

    public void setNombreAnunciante(String nombreAnunciante) {
        this.nombreAnunciante = nombreAnunciante;
    }

    public void setTotalVisualizaciones(Integer totalVisualizaciones) {
        this.totalVisualizaciones = totalVisualizaciones;
    }

    public void setUrlsMostradas(String urlsMostradas) {
        this.urlsMostradas = urlsMostradas;
    }
    
}
