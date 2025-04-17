/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BackendDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author herson
 */
public class CancelarCitaDB {

    public boolean cancelarCita(int idCita) {
        // Cambiar el estado de la cita a 'Cancelada'
        String sqlActualizarEstado = "UPDATE Citas SET Estado = 'Cancelada' WHERE ID_Cita = ?";

        try (Connection connection = ConexionDB.getConnection(); PreparedStatement stmtActualizarEstado = connection.prepareStatement(sqlActualizarEstado)) {
            stmtActualizarEstado.setInt(1, idCita);

            int filasAfectadas = stmtActualizarEstado.executeUpdate();

            return filasAfectadas > 0;

        } catch (SQLException e) {
            System.out.println("Error al cancelar la cita: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }
}