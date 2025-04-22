/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BackendDB.ReportesAdmin;

import BackendDB.ConexionDB;
import Modelos.Admin.ReporteListaNegra;
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
public class ReporteListaNegraDB {

    public List<ReporteListaNegra> obtenerClientesListaNegra(Date inicio, Date fin) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<ReporteListaNegra> reportes = new ArrayList<>();

        String baseQuery = "SELECT u.ID_Usuario AS ID_Cliente, "
                + "u.Nombre AS Nombre_Cliente, "
                + "c.ID_Cita AS ID_Cita, "
                + "c.Fecha_Cita, "
                + "c.Hora_Cita, "
                + "c.Estado, "
                + "s.Nombre_Servicio, "
                + "ln.Motivo, "
                + "ln.Estado AS Estado_Lista_Negra "
                + "FROM Lista_Negra ln "
                + "JOIN Usuarios u ON ln.ID_Cliente = u.ID_Usuario "
                + "JOIN Citas c ON ln.ID_Cita = c.ID_Cita "
                + "JOIN Servicios s ON c.ID_Servicio = s.ID_Servicio "
                + "WHERE c.Estado = 'No Presentado' "
                + "AND ln.Estado = 'En Lista' ";

        // Filtro dinámico para las fechas
        if (inicio != null && fin != null) {
            baseQuery += "AND c.Fecha_Cita BETWEEN ? AND ? ";
        }

        try {
            connection = ConexionDB.getConnection();
            statement = connection.prepareStatement(baseQuery);

            if (inicio != null && fin != null) {
                statement.setDate(1, new java.sql.Date(inicio.getTime()));
                statement.setDate(2, new java.sql.Date(fin.getTime()));
            }

            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                ReporteListaNegra reporte = new ReporteListaNegra(
                        resultSet.getInt("ID_Cliente"),
                        resultSet.getString("Nombre_Cliente"),
                        resultSet.getInt("ID_Cita"),
                        resultSet.getDate("Fecha_Cita"),
                        resultSet.getTime("Hora_Cita"),
                        resultSet.getString("Estado"),
                        resultSet.getString("Nombre_Servicio"),
                        resultSet.getString("Motivo"),
                        resultSet.getString("Estado_Lista_Negra")
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
