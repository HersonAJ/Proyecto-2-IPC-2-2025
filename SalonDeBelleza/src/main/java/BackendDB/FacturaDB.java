/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BackendDB;

import Modelos.Factura;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author herson
 */
public class FacturaDB {

    // metodo para crear una factura en la base de datos
    public boolean crearFactura(Factura factura) {
        String sqlFactura = "INSERT INTO Facturas (ID_Cita, ID_Cliente, ID_Empleado, ID_Servicio, Total, Fecha_Factura, Detalles) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";
        String sqlActualizarCita = "UPDATE Citas SET Factura_Generada = ? WHERE ID_Cita = ?";

        try (Connection connection = ConexionDB.getConnection(); PreparedStatement stmtFactura = connection.prepareStatement(sqlFactura); PreparedStatement stmtActualizarCita = connection.prepareStatement(sqlActualizarCita)) {

            stmtFactura.setInt(1, factura.getIdCita());
            stmtFactura.setInt(2, factura.getIdCliente());
            stmtFactura.setInt(3, factura.getIdEmpleado());
            stmtFactura.setInt(4, factura.getIdServicio());
            stmtFactura.setDouble(5, factura.getTotal());
            stmtFactura.setDate(6, java.sql.Date.valueOf(factura.getFechaFactura()));
            stmtFactura.setString(7, factura.getDetalles());

            int filasInsertadas = stmtFactura.executeUpdate();

            // Si la factura fue insertada correctamente, actualizar la cita
            if (filasInsertadas > 0) {
                stmtActualizarCita.setBoolean(1, true); 
                stmtActualizarCita.setInt(2, factura.getIdCita());
                int filasActualizadas = stmtActualizarCita.executeUpdate();

                // Log para depuración
                System.out.println("Factura generada y cita actualizada correctamente.");
                return filasActualizadas > 0;
            } else {
                System.err.println("No se pudo insertar la factura.");
                return false;
            }

        } catch (SQLException e) {
            System.err.println("Error al generar la factura y actualizar la cita: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public List<Factura> obtenerFacturasPorCliente(int idCliente) {
        String sql = "SELECT F.ID_Factura, F.ID_Cita, F.ID_Cliente, UCliente.Nombre AS NombreCliente, "
                + "UCliente.Direccion AS DireccionCliente, UCliente.Telefono AS NumeroTelefonoCliente, "
                + "UEmpleado.Nombre AS NombreEmpleado, F.ID_Empleado, F.ID_Servicio, S.Nombre_Servicio AS Servicio, "
                + "F.Total, DATE_FORMAT(F.Fecha_Factura, '%Y-%m-%d') AS FechaFactura, F.Detalles "
                + "FROM Facturas F "
                + "INNER JOIN Citas C ON F.ID_Cita = C.ID_Cita "
                + "INNER JOIN Usuarios UCliente ON F.ID_Cliente = UCliente.ID_Usuario "
                + "INNER JOIN Usuarios UEmpleado ON F.ID_Empleado = UEmpleado.ID_Usuario "
                + "INNER JOIN Servicios S ON F.ID_Servicio = S.ID_Servicio "
                + "WHERE F.ID_Cliente = ? AND C.Estado = 'Atendida'";

        List<Factura> facturas = new ArrayList<>();

        try (Connection connection = ConexionDB.getConnection(); PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idCliente);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Factura factura = new Factura(
                        rs.getInt("ID_Factura"),
                        rs.getInt("ID_Cita"),
                        rs.getInt("ID_Cliente"),
                        rs.getString("NombreCliente"),
                        rs.getString("DireccionCliente"),
                        rs.getString("NumeroTelefonoCliente"),
                        rs.getString("NombreEmpleado"),
                        rs.getInt("ID_Empleado"),
                        rs.getInt("ID_Servicio"),
                        rs.getDouble("Total"),
                        rs.getDate("FechaFactura").toLocalDate(),
                        rs.getString("Detalles")
                );
                facturas.add(factura);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return facturas;
    }
}
