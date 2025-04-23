/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BackendDB.ReportesAdmin;

import BackendDB.ConexionDB;
import Modelos.Admin.ReporteClientes;
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
public class ReporteClienteMasCitasDB {
    
    public List<ReporteClientes> obtenerClientesConMasCitasAtendidas(Date inicio, Date fin, Integer idEmpleado) {
    Connection connection = null;
    PreparedStatement statement = null;
    ResultSet resultSet = null;
    List<ReporteClientes> reportes = new ArrayList<>();

    String query = "SELECT u.ID_Usuario AS ID_Cliente, "
            + "u.Nombre AS Nombre_Cliente, "
            + "COUNT(c.ID_Cita) AS Total_Citas, "
            + "GROUP_CONCAT(CONCAT('Servicio: ', s.Nombre_Servicio, ', Fecha: ', c.Fecha_Cita, ', Hora: ', c.Hora_Cita, ', Estado: ', c.Estado) SEPARATOR '; ') AS Detalle_Citas "
            + "FROM Usuarios u "
            + "JOIN Citas c ON u.ID_Usuario = c.ID_Cliente "
            + "JOIN Servicios s ON c.ID_Servicio = s.ID_Servicio "
            + "WHERE c.Estado = 'Atendida' "
            + "AND (c.Fecha_Cita BETWEEN ? AND ? OR (? IS NULL AND ? IS NULL)) "
            + "AND (c.ID_Empleado = ? OR ? IS NULL) "
            + "GROUP BY u.ID_Usuario "
            + "ORDER BY Total_Citas DESC "
            + "LIMIT 5";

    try {
        connection = ConexionDB.getConnection();
        statement = connection.prepareStatement(query);

        // Configuración dinámica de parámetros de fechas
        if (inicio != null && fin != null) {
            statement.setDate(1, new java.sql.Date(inicio.getTime()));
            statement.setDate(2, new java.sql.Date(fin.getTime()));
        } else {
            statement.setDate(1, null);
            statement.setDate(2, null);
        }

        statement.setDate(3, inicio != null ? new java.sql.Date(inicio.getTime()) : null);
        statement.setDate(4, fin != null ? new java.sql.Date(fin.getTime()) : null);

        // Configuración del filtro por empleado (opcional)
        if (idEmpleado != null) {
            statement.setInt(5, idEmpleado);
            statement.setInt(6, idEmpleado);
        } else {
            statement.setNull(5, java.sql.Types.INTEGER);
            statement.setNull(6, java.sql.Types.INTEGER);
        }

        resultSet = statement.executeQuery();

        while (resultSet.next()) {
            ReporteClientes reporte = new ReporteClientes(
            resultSet.getInt("ID_Cliente"),
            resultSet.getString("Nombre_Cliente"),
            resultSet.getInt("Total_Citas"),
            resultSet.getString("Detalle_Citas"));
            reportes.add(reporte);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    } finally {
        try {
            if (resultSet != null) resultSet.close();
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    return reportes;
}

}
