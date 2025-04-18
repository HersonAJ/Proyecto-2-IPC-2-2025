/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelos;

import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author herson
 */
public class Anuncio {
    
    private int idAnuncio;
    private String nombreAnunciante;
    private String contactoAnunciante;
    private String tipo;
    private String contenidoTexto;
    private String urlImagen;
    private String urlVideo;
    private List<String> hobbiesRelacionados; 
    private double precioPorDia;
    private int duracionDias;
    private int vecesMonstrado;
    private String ubicacionURL;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private String estado;

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    public int getIdAnuncio() {
        return idAnuncio;
    }

    public String getNombreAnunciante() {
        return nombreAnunciante;
    }

    public String getContactoAnunciante() {
        return contactoAnunciante;
    }

    public String getTipo() {
        return tipo;
    }

    public String getContenidoTexto() {
        return contenidoTexto;
    }

    public String getUrlImagen() {
        return urlImagen;
    }

    public String getUrlVideo() {
        return urlVideo;
    }

    public List<String> getHobbiesRelacionados() {
        return hobbiesRelacionados;
    }

    public double getPrecioPorDia() {
        return precioPorDia;
    }

    public int getDuracionDias() {
        return duracionDias;
    }


    public int getVecesMonstrado() {
        return vecesMonstrado;
    }

    public String getUbicacionURL() {
        return ubicacionURL;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setIdAnuncio(int idAnuncio) {
        this.idAnuncio = idAnuncio;
    }

    public void setNombreAnunciante(String nombreAnunciante) {
        this.nombreAnunciante = nombreAnunciante;
    }

    public void setContactoAnunciante(String contactoAnunciante) {
        this.contactoAnunciante = contactoAnunciante;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setContenidoTexto(String contenidoTexto) {
        this.contenidoTexto = contenidoTexto;
    }

    public void setUrlImagen(String urlImagen) {
        this.urlImagen = urlImagen;
    }

    public void setUrlVideo(String urlVideo) {
        this.urlVideo = urlVideo;
    }

    public void setHobbiesRelacionados(List<String> hobbiesRelacionados) {
        this.hobbiesRelacionados = hobbiesRelacionados;
    }

    public void setPrecioPorDia(double precioPorDia) {
        this.precioPorDia = precioPorDia;
    }

    public void setDuracionDias(int duracionDias) {
        this.duracionDias = duracionDias;
    }

    public void setVecesMonstrado(int vecesMonstrado) {
        this.vecesMonstrado = vecesMonstrado;
    }

    public void setUbicacionURL(String ubicacionURL) {
        this.ubicacionURL = ubicacionURL;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }
    
    
}
