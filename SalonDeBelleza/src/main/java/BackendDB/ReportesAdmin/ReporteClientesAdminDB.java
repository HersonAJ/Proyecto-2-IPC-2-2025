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
public class ReporteClientesAdminDB {

    public List<ReporteClientes> obtenerClientesConMasCitas(Date inicio, Date fin) {
        Connection connection = null;
        PreparedStatement setStatement = null;
        PreparedStatement selectStatement = null;
        ResultSet resultSet = null;
        List<ReporteClientes> reportes = new ArrayList<>();
        String setGroupConcatLimit = "SET SESSION group_concat_max_len = 1000000";

        // Construcción dinámica de la consulta SQL
        String baseQuery = "SELECT u.ID_Usuario AS ID_Cliente, "
                + "u.Nombre AS Nombre_Cliente, "
                + "COUNT(c.ID_Cita) AS Total_Citas, "
                + "GROUP_CONCAT(CONCAT('Servicio: ', s.Nombre_Servicio, ', Fecha: ', c.Fecha_Cita, ', Hora: ', c.Hora_Cita, ', Estado: ', c.Estado) SEPARATOR '; ') AS Detalle_Citas "
                + "FROM Usuarios u "
                + "JOIN Citas c ON u.ID_Usuario = c.ID_Cliente "
                + "JOIN Servicios s ON c.ID_Servicio = s.ID_Servicio "
                + "WHERE u.Rol = 'Cliente' ";
        String fechaFilter = "AND c.Fecha_Cita BETWEEN ? AND ? ";
        String groupOrderBy = "GROUP BY u.ID_Usuario "
                + "ORDER BY Total_Citas DESC "
                + "LIMIT 5";

        // Agregar filtro de fechas dinámicamente
        if (inicio != null && fin != null) {
            baseQuery += fechaFilter;
        }
        baseQuery += groupOrderBy;

        try {
            connection = ConexionDB.getConnection();

            setStatement = connection.prepareStatement(setGroupConcatLimit);
            setStatement.execute();

            selectStatement = connection.prepareStatement(baseQuery);

            if (inicio != null && fin != null) {
                selectStatement.setDate(1, new java.sql.Date(inicio.getTime()));
                selectStatement.setDate(2, new java.sql.Date(fin.getTime()));
            }
            resultSet = selectStatement.executeQuery();

            // Procesar resultados
            while (resultSet.next()) {
                ReporteClientes reporte = new ReporteClientes(
                        resultSet.getInt("ID_Cliente"),
                        resultSet.getString("Nombre_Cliente"),
                        resultSet.getInt("Total_Citas"),
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
                if (selectStatement != null) {
                    selectStatement.close();
                }
                if (setStatement != null) {
                    setStatement.close();
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
