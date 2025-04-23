/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BackendDB;

import Modelos.ReporteAnuncios;
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

    public List<ReporteAnuncios> obtenerAnunciosMasMostrados(Date inicio, Date fin) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<ReporteAnuncios> reportes = new ArrayList<>();

        String query = "SELECT a.ID_Anuncio, "
                + "a.Tipo_Anuncio, "
                + "a.Nombre_Anunciante, "
                + "COUNT(h.ID_Visualizacion) AS Total_Visualizaciones, "
                + "GROUP_CONCAT(h.URL_Mostrada SEPARATOR '; ') AS URLs_Mostradas "
                + "FROM Anuncios a "
                + "JOIN Historial_Anuncios h ON a.ID_Anuncio = h.ID_Anuncio "
                + "WHERE (h.Fecha_Visualizacion BETWEEN ? AND ? OR (? IS NULL AND ? IS NULL)) "
                + "GROUP BY a.ID_Anuncio, a.Tipo_Anuncio, a.Nombre_Anunciante "
                + "ORDER BY Total_Visualizaciones DESC "
                + "LIMIT 5";

        try {
            connection = ConexionDB.getConnection();
            statement = connection.prepareStatement(query);

            // Configurar los parámetros de la consulta
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
                ReporteAnuncios reporte = new ReporteAnuncios();
                reporte.setIdAnuncio(resultSet.getInt("ID_Anuncio"));
                reporte.setTipoAnuncio(resultSet.getString("Tipo_Anuncio"));
                reporte.setNombreAnunciante(resultSet.getString("Nombre_Anunciante"));
                reporte.setTotalVisualizaciones(resultSet.getInt("Total_Visualizaciones"));
                reporte.setUrlsMostradas(resultSet.getString("URLs_Mostradas"));
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

    public List<ReporteAnuncios> obtenerAnunciosMenosMostrados(Date inicio, Date fin) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<ReporteAnuncios> reportes = new ArrayList<>();

        String query = "SELECT a.ID_Anuncio, "
                + "a.Tipo_Anuncio, "
                + "a.Nombre_Anunciante, "
                + "COUNT(h.ID_Visualizacion) AS Total_Visualizaciones, "
                + "GROUP_CONCAT(h.URL_Mostrada SEPARATOR '; ') AS URLs_Mostradas "
                + "FROM Anuncios a "
                + "JOIN Historial_Anuncios h ON a.ID_Anuncio = h.ID_Anuncio "
                + "WHERE (h.Fecha_Visualizacion BETWEEN ? AND ? OR (? IS NULL AND ? IS NULL)) "
                + "GROUP BY a.ID_Anuncio, a.Tipo_Anuncio, a.Nombre_Anunciante "
                + "ORDER BY Total_Visualizaciones ASC "
                + "LIMIT 5";

        try {
            connection = ConexionDB.getConnection();
            statement = connection.prepareStatement(query);

            // Configuración de parámetros
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
                ReporteAnuncios reporte = new ReporteAnuncios();
                reporte.setIdAnuncio(resultSet.getInt("ID_Anuncio"));
                reporte.setTipoAnuncio(resultSet.getString("Tipo_Anuncio"));
                reporte.setNombreAnunciante(resultSet.getString("Nombre_Anunciante"));
                reporte.setTotalVisualizaciones(resultSet.getInt("Total_Visualizaciones"));
                reporte.setUrlsMostradas(resultSet.getString("URLs_Mostradas"));
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

    public List<ReporteAnuncios> obtenerHistorialAnunciosMasComprados(Date inicio, Date fin) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<ReporteAnuncios> reportes = new ArrayList<>();

        String query = "SELECT a.ID_Anuncio, "
                + "a.Tipo_Anuncio, "
                + "a.Nombre_Anunciante, "
                + "COUNT(p.ID_Pago) AS Total_Usos, "
                + "GROUP_CONCAT(CONCAT('Comprador: ', p.Comprador, ', Fecha de Pago: ', p.Fecha_Pago, ', Monto: ', p.Monto) SEPARATOR '; ') AS Detalles_Usos "
                + "FROM Anuncios a "
                + "JOIN Pagos_Anuncios p ON a.ID_Anuncio = p.ID_Anuncio "
                + "WHERE (p.Fecha_Pago BETWEEN ? AND ? OR (? IS NULL AND ? IS NULL)) "
                + "GROUP BY a.ID_Anuncio, a.Tipo_Anuncio, a.Nombre_Anunciante "
                + "ORDER BY Total_Usos DESC";

        try {
            connection = ConexionDB.getConnection();
            statement = connection.prepareStatement(query);

            // Configuración de los parámetros de fechas
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
                ReporteAnuncios reporte = new ReporteAnuncios();
                reporte.setIdAnuncio(resultSet.getInt("ID_Anuncio"));
                reporte.setTipoAnuncio(resultSet.getString("Tipo_Anuncio"));
                reporte.setNombreAnunciante(resultSet.getString("Nombre_Anunciante"));
                reporte.setTotalVisualizaciones(resultSet.getInt("Total_Usos"));
                reporte.setUrlsMostradas(resultSet.getString("Detalles_Usos"));
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
