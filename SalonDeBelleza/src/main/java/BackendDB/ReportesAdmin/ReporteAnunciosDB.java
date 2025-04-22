/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BackendDB.ReportesAdmin;

import BackendDB.ConexionDB;
import Modelos.ReporteAdmin;
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
public class ReporteAnunciosDB {

    public List<ReporteAdmin> obtenerAnunciosMasMostrados(Date inicio, Date fin) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<ReporteAdmin> reportes = new ArrayList<>();

        String baseQuery = "SELECT a.ID_Anuncio, a.Nombre_Anunciante, a.Tipo_Anuncio, "
                + "COUNT(DISTINCT h.ID_Visualizacion) AS Total_Mostrado, "
                + "GROUP_CONCAT(DISTINCT h.URL_Mostrada SEPARATOR ', ') AS URLs_Mostrados "
                + "FROM Anuncios a "
                + "LEFT JOIN Historial_Anuncios h ON a.ID_Anuncio = h.ID_Anuncio "
                + "WHERE 1 = 1 ";

        String fechaFilter = "AND h.Fecha_Visualizacion BETWEEN ? AND ? ";
        String groupOrderBy = "GROUP BY a.ID_Anuncio ORDER BY Total_Mostrado DESC LIMIT 5";

        StringBuilder queryBuilder = new StringBuilder(baseQuery);
        if (inicio != null && fin != null) {
            queryBuilder.append(fechaFilter);
        }
        queryBuilder.append(groupOrderBy);

        try {
            connection = ConexionDB.getConnection();
            statement = connection.prepareStatement(queryBuilder.toString());
            int paramIndex = 1;

            if (inicio != null && fin != null) {
                statement.setDate(paramIndex++, new java.sql.Date(inicio.getTime()));
                statement.setDate(paramIndex++, new java.sql.Date(fin.getTime()));
            }

            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                ReporteAdmin reporte = new ReporteAdmin(
                        resultSet.getInt("ID_Anuncio"),
                        resultSet.getString("URLs_Mostrados"),
                        resultSet.getInt("Total_Mostrado"),
                        resultSet.getString("Tipo_Anuncio"),
                        resultSet.getString("Nombre_Anunciante")
                );

                reporte.setTitulo("Reporte de Anuncios Más Mostrados");
                reporte.setDescripcion("Los anuncios más mostrados en el intervalo de tiempo especificado.");
                reporte.setFechaInicio(inicio);
                reporte.setFechaFin(fin);

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
