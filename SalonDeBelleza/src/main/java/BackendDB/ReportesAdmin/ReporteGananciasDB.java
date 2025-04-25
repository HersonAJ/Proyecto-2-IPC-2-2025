/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BackendDB.ReportesAdmin;

import BackendDB.ConexionDB;
import Controllers.ReportesAdmin.ServicioParaReportes;
import Modelos.ReporteAdmin;
import java.math.BigDecimal;
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
public class ReporteGananciasDB {

    public List<ReporteAdmin> obtenerGananciasPorServicio(Date inicio, Date fin, Integer idServicio) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<ReporteAdmin> reportes = new ArrayList<>();
        BigDecimal totalGeneral = BigDecimal.ZERO; 

        String baseQuery = "SELECT s.ID_Servicio, s.Nombre_Servicio, COUNT(c.ID_Cita) AS Total_Citas, SUM(f.Total) AS Total_Ingresos, f.Total AS Precio_Factura "
                + "FROM Servicios s "
                + "JOIN Citas c ON s.ID_Servicio = c.ID_Servicio "
                + "JOIN Facturas f ON c.ID_Cita = f.ID_Cita "
                + "WHERE c.Estado = 'Atendida' ";
        String fechaFilter = "AND f.Fecha_Factura BETWEEN ? AND ? ";
        String servicioFilter = "AND s.ID_Servicio = ? ";
        String groupOrderBy = "GROUP BY s.ID_Servicio, f.Total ORDER BY Total_Ingresos DESC";

        StringBuilder queryBuilder = new StringBuilder(baseQuery);

        if (inicio != null && fin != null) {
            queryBuilder.append(fechaFilter);
        }

        // Agregar filtro por servicio si se proporciona
        if (idServicio != null) {
            queryBuilder.append(servicioFilter);
        }
        queryBuilder.append(groupOrderBy);

        try {
            connection = ConexionDB.getConnection();
            statement = connection.prepareStatement(queryBuilder.toString());
            int paramIndex = 1;

            // Configurar parámetros de fecha
            if (inicio != null && fin != null) {
                statement.setDate(paramIndex++, new java.sql.Date(inicio.getTime()));
                statement.setDate(paramIndex++, new java.sql.Date(fin.getTime()));
            }

            if (idServicio != null) {
                statement.setInt(paramIndex++, idServicio);
            }


            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                ReporteAdmin reporte = new ReporteAdmin(
                        resultSet.getInt("ID_Servicio"),
                        resultSet.getString("Nombre_Servicio"),
                        resultSet.getInt("Total_Citas"),
                        resultSet.getBigDecimal("Total_Ingresos"),
                        resultSet.getBigDecimal("Precio_Factura") 
                );

                // Sumar ganancias totales al total general
                totalGeneral = totalGeneral.add(reporte.getTotalIngresos());

                reportes.add(reporte);
            }

            if (!reportes.isEmpty()) {
                ReporteAdmin totalReporte = new ReporteAdmin();
                totalReporte.setTitulo("Total General de Ganancias");
                totalReporte.setDescripcion("Suma de todas las ganancias totales por servicio");
                totalReporte.setTotalIngresos(totalGeneral);
                reportes.add(totalReporte); 
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

    public List<ServicioParaReportes> obtenerListadoServicios() {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<ServicioParaReportes> servicios = new ArrayList<>();

        String query = "SELECT ID_Servicio, Nombre_Servicio FROM Servicios WHERE Estado = 'Visible'";

        try {
            connection = ConexionDB.getConnection(); 
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                ServicioParaReportes servicio = new ServicioParaReportes(
                        resultSet.getInt("ID_Servicio"),
                        resultSet.getString("Nombre_Servicio")
                );
                servicios.add(servicio);
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
        return servicios;
    }

}
