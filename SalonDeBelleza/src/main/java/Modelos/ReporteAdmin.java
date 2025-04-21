/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelos;

import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author herson
 */
public class ReporteAdmin {

    // Atributos comunes para reportes
    private String titulo;                 // Título del reporte
    private String descripcion;           // Descripción adicional del reporte
    private Date fechaInicio;             // Fecha de inicio del intervalo
    private Date fechaFin;                // Fecha de fin del intervalo
    private BigDecimal precioFactura;

    // Atributos específicos para reportes de tipo servicio
    private Integer idServicio;
    private String nombreServicio;
    private Integer totalCitas;
    private BigDecimal totalIngresos;

    // Atributos específicos para reportes de clientes
    private Integer idCliente;
    private String nombreCliente;
    private Integer totalReservas;
    private BigDecimal totalGastado;

    // Atributos específicos para reportes de anuncios
    private Integer idAnuncio;
    private String urlMostrada;
    private Integer vecesMostrado;

    public ReporteAdmin() {
    }

// Constructor para reportes de ganancias por servicio
    public ReporteAdmin(Integer idServicio, String nombreServicio, Integer totalCitas, BigDecimal totalIngresos, BigDecimal precioFactura) {
        this.idServicio = idServicio;
        this.nombreServicio = nombreServicio;
        this.totalCitas = totalCitas;
        this.totalIngresos = totalIngresos;
        this.precioFactura = precioFactura;
    }
// Constructor para reportes de clientes 

    public ReporteAdmin(Integer idCliente, String nombreCliente, Integer totalReservas, BigDecimal totalGastado, String clienteTipo) {
        this.idCliente = idCliente;
        this.nombreCliente = nombreCliente;
        this.totalReservas = totalReservas;
        this.totalGastado = totalGastado;
    }

    // Constructor para reportes de anuncios
    public ReporteAdmin(Integer idAnuncio, String urlMostrada, Integer vecesMostrado) {
        this.idAnuncio = idAnuncio;
        this.urlMostrada = urlMostrada;
        this.vecesMostrado = vecesMostrado;
    }

    public BigDecimal getPrecioFactura() {
        return precioFactura;
    }

    public void setPrecioFactura(BigDecimal precioFactura) {
        this.precioFactura = precioFactura;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Integer getIdServicio() {
        return idServicio;
    }

    public void setIdServicio(Integer idServicio) {
        this.idServicio = idServicio;
    }

    public String getNombreServicio() {
        return nombreServicio;
    }

    public void setNombreServicio(String nombreServicio) {
        this.nombreServicio = nombreServicio;
    }

    public Integer getTotalCitas() {
        return totalCitas;
    }

    public void setTotalCitas(Integer totalCitas) {
        this.totalCitas = totalCitas;
    }

    public BigDecimal getTotalIngresos() {
        return totalIngresos;
    }

    public void setTotalIngresos(BigDecimal totalIngresos) {
        this.totalIngresos = totalIngresos;
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

    public Integer getTotalReservas() {
        return totalReservas;
    }

    public void setTotalReservas(Integer totalReservas) {
        this.totalReservas = totalReservas;
    }

    public BigDecimal getTotalGastado() {
        return totalGastado;
    }

    public void setTotalGastado(BigDecimal totalGastado) {
        this.totalGastado = totalGastado;
    }

    public Integer getIdAnuncio() {
        return idAnuncio;
    }

    public void setIdAnuncio(Integer idAnuncio) {
        this.idAnuncio = idAnuncio;
    }

    public String getUrlMostrada() {
        return urlMostrada;
    }

    public void setUrlMostrada(String urlMostrada) {
        this.urlMostrada = urlMostrada;
    }

    public Integer getVecesMostrado() {
        return vecesMostrado;
    }

    public void setVecesMostrado(Integer vecesMostrado) {
        this.vecesMostrado = vecesMostrado;
    }
}
