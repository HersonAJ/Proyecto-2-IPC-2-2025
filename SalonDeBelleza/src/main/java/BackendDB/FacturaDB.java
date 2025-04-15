/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BackendDB;

import Modelos.Factura;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author herson
 */
public class FacturaDB {

    //metodo para crear una factura en la base de datos 
    public boolean crearFactura(Factura factura) {
        String sqlFactura = "INSERT INTO Facturas (ID_Cita, ID_Cliente, ID_Empleado, ID_Servicio, Total, Fecha_Factura, Detalles) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";
        String sqlActualizarCita = "UPDATE Citas SET Factura_Generada = ? WHERE ID_Cita = ?";

        try (Connection connection = ConexionDB.getConnection(); PreparedStatement stmtFactura = connection.prepareStatement(sqlFactura); PreparedStatement stmtActualizarCita = connection.prepareStatement(sqlActualizarCita)) {

            // Insertar la factura
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
                stmtActualizarCita.setBoolean(1, true); // Cambiar Factura_Generada a true
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

}
