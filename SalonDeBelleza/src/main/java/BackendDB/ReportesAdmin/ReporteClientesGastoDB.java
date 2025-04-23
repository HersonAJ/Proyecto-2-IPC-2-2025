/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BackendDB.ReportesAdmin;

import BackendDB.ConexionDB;
import Modelos.Admin.ReporteClientesGastos;
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
public class ReporteClientesGastoDB {

    public List<ReporteClientesGastos> obtenerClientesMasGastadores(Date inicio, Date fin) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<ReporteClientesGastos> reportes = new ArrayList<>();

        String baseQuery = "SELECT u.ID_Usuario AS ID_Cliente, "
                + "u.Nombre AS Nombre_Cliente, "
                + "SUM(f.Total) AS Total_Gastado, "
                + "GROUP_CONCAT(CONCAT('Servicio: ', s.Nombre_Servicio, ', Fecha: ', c.Fecha_Cita, ', Hora: ', c.Hora_Cita, ', Total: ', f.Total) SEPARATOR '; ') AS Detalle_Citas "
                + "FROM Usuarios u "
                + "JOIN Citas c ON u.ID_Usuario = c.ID_Cliente "
                + "JOIN Facturas f ON c.ID_Cita = f.ID_Cita "
                + "JOIN Servicios s ON c.ID_Servicio = s.ID_Servicio "
                + "WHERE u.Rol = 'Cliente' "
                + "AND (f.Fecha_Factura BETWEEN ? AND ? OR (? IS NULL AND ? IS NULL)) "
                + "GROUP BY u.ID_Usuario "
                + "ORDER BY Total_Gastado DESC "
                + "LIMIT 5";

        try {
            connection = ConexionDB.getConnection();
            statement = connection.prepareStatement(baseQuery);

            // Configuración dinámica de los parámetros para el rango de fechas
            if (inicio != null && fin != null) {
                statement.setDate(1, new java.sql.Date(inicio.getTime()));
                statement.setDate(2, new java.sql.Date(fin.getTime()));
            } else {
                statement.setDate(1, null);
                statement.setDate(2, null);
            }

            statement.setDate(3, inicio != null ? new java.sql.Date(inicio.getTime()) : null);
            statement.setDate(4, fin != null ? new java.sql.Date(fin.getTime()) : null);

            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                ReporteClientesGastos reporte = new ReporteClientesGastos(
                        resultSet.getInt("ID_Cliente"),
                        resultSet.getString("Nombre_Cliente"),
                        resultSet.getDouble("Total_Gastado"),
                        resultSet.getString("Detalle_Citas")
                );
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

    public List<ReporteClientesGastos> obtenerClientesConMenosGasto(Date inicio, Date fin) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<ReporteClientesGastos> reportes = new ArrayList<>();

        String baseQuery = "SELECT u.ID_Usuario AS ID_Cliente, "
                + "u.Nombre AS Nombre_Cliente, "
                + "SUM(f.Total) AS Total_Gastado, "
                + "GROUP_CONCAT(CONCAT('Servicio: ', s.Nombre_Servicio, ', Fecha: ', c.Fecha_Cita, ', Hora: ', c.Hora_Cita, ', Total: ', f.Total) SEPARATOR '; ') AS Detalle_Citas "
                + "FROM Usuarios u "
                + "JOIN Citas c ON u.ID_Usuario = c.ID_Cliente "
                + "JOIN Facturas f ON c.ID_Cita = f.ID_Cita "
                + "JOIN Servicios s ON c.ID_Servicio = s.ID_Servicio "
                + "WHERE u.Rol = 'Cliente' "
                + "AND (f.Fecha_Factura BETWEEN ? AND ? OR (? IS NULL AND ? IS NULL)) "
                + "GROUP BY u.ID_Usuario "
                + "ORDER BY Total_Gastado ASC "
                + "LIMIT 5";

        try {
            connection = ConexionDB.getConnection();
            statement = connection.prepareStatement(baseQuery);

            // Configuración dinámica de los parámetros de rango de fechas
            if (inicio != null && fin != null) {
                statement.setDate(1, new java.sql.Date(inicio.getTime()));
                statement.setDate(2, new java.sql.Date(fin.getTime()));
            } else {
                statement.setDate(1, null);
                statement.setDate(2, null);
            }

            statement.setDate(3, inicio != null ? new java.sql.Date(inicio.getTime()) : null);
            statement.setDate(4, fin != null ? new java.sql.Date(fin.getTime()) : null);

            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                ReporteClientesGastos reporte = new ReporteClientesGastos(
                        resultSet.getInt("ID_Cliente"),
                        resultSet.getString("Nombre_Cliente"),
                        resultSet.getDouble("Total_Gastado"),
                        resultSet.getString("Detalle_Citas")
                );
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

}
