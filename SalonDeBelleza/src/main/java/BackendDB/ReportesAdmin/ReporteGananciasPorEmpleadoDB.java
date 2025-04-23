/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BackendDB.ReportesAdmin;

import BackendDB.ConexionDB;
import Modelos.Admin.ReporteGananciaEmpleado;
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
public class ReporteGananciasPorEmpleadoDB {

    public List<ReporteGananciaEmpleado> obtenerEmpleadosActivos() {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<ReporteGananciaEmpleado> empleados = new ArrayList<>();

        String query = "SELECT ID_Usuario AS ID_Empleado, Nombre FROM Usuarios WHERE Rol = 'Empleado' AND Estado = 'Activo'";

        try {
            connection = ConexionDB.getConnection();
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                ReporteGananciaEmpleado empleado = new ReporteGananciaEmpleado();
                empleado.setIdEmpleado(resultSet.getInt("ID_Empleado"));
                empleado.setNombreEmpleado(resultSet.getString("Nombre"));
                empleado.setTotalGanancia(null);
                empleado.setDetalleCitas(null);
                empleados.add(empleado);
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

        return empleados;
    }


    public List<ReporteGananciaEmpleado> obtenerGananciasPorEmpleado(Date inicio, Date fin, Integer idEmpleado) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<ReporteGananciaEmpleado> reportes = new ArrayList<>();

        String query = "SELECT u.ID_Usuario AS ID_Empleado, "
                + "u.Nombre AS Nombre_Empleado, "
                + "SUM(f.Total) AS Total_Ganancia, "
                + "GROUP_CONCAT(CONCAT('Servicio: ', s.Nombre_Servicio, ', Fecha: ', c.Fecha_Cita, ', Hora: ', c.Hora_Cita, ', Total: ', f.Total) SEPARATOR '; ') AS Detalle_Citas "
                + "FROM Usuarios u "
                + "JOIN Facturas f ON u.ID_Usuario = f.ID_Empleado "
                + "JOIN Citas c ON f.ID_Cita = c.ID_Cita "
                + "JOIN Servicios s ON c.ID_Servicio = s.ID_Servicio "
                + "WHERE u.Rol = 'Empleado' "
                + "AND (f.Fecha_Factura BETWEEN ? AND ? OR (? IS NULL AND ? IS NULL)) "
                + "AND (u.ID_Usuario = ? OR ? IS NULL) "
                + "GROUP BY u.ID_Usuario "
                + "ORDER BY Total_Ganancia DESC";

        try {
            connection = ConexionDB.getConnection();
            statement = connection.prepareStatement(query);

            // Configuración dinámica de los parámetros de fechas
            if (inicio != null && fin != null) {
                statement.setDate(1, new java.sql.Date(inicio.getTime()));
                statement.setDate(2, new java.sql.Date(fin.getTime()));
            } else {
                statement.setDate(1, null);
                statement.setDate(2, null);
            }

            statement.setDate(3, inicio != null ? new java.sql.Date(inicio.getTime()) : null);
            statement.setDate(4, fin != null ? new java.sql.Date(fin.getTime()) : null);

            // Configuración del filtro por empleado 
            if (idEmpleado != null) {
                statement.setInt(5, idEmpleado);
                statement.setInt(6, idEmpleado);
            } else {
                statement.setNull(5, java.sql.Types.INTEGER);
                statement.setNull(6, java.sql.Types.INTEGER);
            }

            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                ReporteGananciaEmpleado reporte = new ReporteGananciaEmpleado();
                reporte.setIdEmpleado(resultSet.getInt("ID_Empleado"));
                reporte.setNombreEmpleado(resultSet.getString("Nombre_Empleado"));
                reporte.setTotalGanancia(resultSet.getDouble("Total_Ganancia"));
                reporte.setDetalleCitas(resultSet.getString("Detalle_Citas"));
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
