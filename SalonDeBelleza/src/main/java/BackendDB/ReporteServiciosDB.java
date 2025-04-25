/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BackendDB;

import Modelos.ReporteServicios;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author herson
 */
public class ReporteServiciosDB {

    public List<ReporteServicios> obtenerServiciosMasReservados(Date inicio, Date fin) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<ReporteServicios> reportes = new ArrayList<>();

        String query = "SELECT s.Nombre_Servicio, "
                + "COUNT(c.ID_Cita) AS Total_Reservas "
                + "FROM Servicios s "
                + "JOIN Citas c ON s.ID_Servicio = c.ID_Servicio "
                + "WHERE (c.Fecha_Cita BETWEEN ? AND ? OR (? IS NULL AND ? IS NULL)) "
                + "GROUP BY s.ID_Servicio, s.Nombre_Servicio "
                + "ORDER BY Total_Reservas DESC "
                + "LIMIT 5";

        try {
            connection = ConexionDB.getConnection();
            statement = connection.prepareStatement(query);

            // Configurar parámetros para filtrar por fechas
            if (inicio != null && fin != null) {
                statement.setDate(1, new java.sql.Date(inicio.getTime()));
                statement.setDate(2, new java.sql.Date(fin.getTime()));
            } else {
                statement.setNull(1, java.sql.Types.DATE);
                statement.setNull(2, java.sql.Types.DATE);
            }

            statement.setDate(3, inicio != null ? new java.sql.Date(inicio.getTime()) : null);
            statement.setDate(4, fin != null ? new java.sql.Date(fin.getTime()) : null);

            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                ReporteServicios reporte = new ReporteServicios();
                reporte.setNombreServicio(resultSet.getString("Nombre_Servicio"));
                reporte.setTotalReservas(resultSet.getInt("Total_Reservas"));
                reportes.add(reporte);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        return reportes;
    }

    public List<ReporteServicios> obtenerServiciosMenosReservados(Date inicio, Date fin) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<ReporteServicios> reportes = new ArrayList<>();

        String query = "SELECT s.Nombre_Servicio, "
                + "COUNT(c.ID_Cita) AS Total_Reservas "
                + "FROM Servicios s "
                + "JOIN Citas c ON s.ID_Servicio = c.ID_Servicio "
                + "WHERE (c.Fecha_Cita BETWEEN ? AND ? OR (? IS NULL AND ? IS NULL)) "
                + "GROUP BY s.ID_Servicio, s.Nombre_Servicio "
                + "ORDER BY Total_Reservas ASC "
                + "LIMIT 5";

        try {
            connection = ConexionDB.getConnection();
            statement = connection.prepareStatement(query);

            // Configurar parámetros para filtrar por fechas
            if (inicio != null && fin != null) {
                statement.setDate(1, new java.sql.Date(inicio.getTime()));
                statement.setDate(2, new java.sql.Date(fin.getTime()));
            } else {
                statement.setNull(1, java.sql.Types.DATE);
                statement.setNull(2, java.sql.Types.DATE);
            }

            statement.setDate(3, inicio != null ? new java.sql.Date(inicio.getTime()) : null);
            statement.setDate(4, fin != null ? new java.sql.Date(fin.getTime()) : null);

            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                ReporteServicios reporte = new ReporteServicios();
                reporte.setNombreServicio(resultSet.getString("Nombre_Servicio"));
                reporte.setTotalReservas(resultSet.getInt("Total_Reservas"));
                reportes.add(reporte);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        return reportes;
    }

    public ReporteServicios obtenerServicioConMasIngresos(Date inicio, Date fin) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        ReporteServicios reporte = null;

        String query = "SELECT s.Nombre_Servicio, "
                + "COUNT(f.ID_Factura) AS Total_Facturas, "
                + "SUM(f.Total) AS Ingreso_Total "
                + "FROM Facturas f "
                + "JOIN Servicios s ON f.ID_Servicio = s.ID_Servicio "
                + "WHERE (f.Fecha_Factura BETWEEN ? AND ? OR (? IS NULL AND ? IS NULL)) "
                + "GROUP BY s.ID_Servicio, s.Nombre_Servicio "
                + "ORDER BY Ingreso_Total DESC "
                + "LIMIT 1";

        try {
            connection = ConexionDB.getConnection();
            statement = connection.prepareStatement(query);

            // Configurar parámetros para filtrar por fechas
            if (inicio != null && fin != null) {
                statement.setDate(1, new java.sql.Date(inicio.getTime()));
                statement.setDate(2, new java.sql.Date(fin.getTime()));
            } else {
                statement.setNull(1, java.sql.Types.DATE);
                statement.setNull(2, java.sql.Types.DATE);
            }

            statement.setDate(3, inicio != null ? new java.sql.Date(inicio.getTime()) : null);
            statement.setDate(4, fin != null ? new java.sql.Date(fin.getTime()) : null);

            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                reporte = new ReporteServicios();
                reporte.setNombreServicio(resultSet.getString("Nombre_Servicio"));
                reporte.setTotalReservas(resultSet.getInt("Total_Facturas"));
                reporte.setTotalIngresos(resultSet.getDouble("Ingreso_Total"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        return reporte;
    }

}
