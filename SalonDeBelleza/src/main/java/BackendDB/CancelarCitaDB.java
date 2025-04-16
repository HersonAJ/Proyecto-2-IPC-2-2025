/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BackendDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 *
 * @author herson
 */
public class CancelarCitaDB {

    public boolean cancelarCita(int idCita) {
        // Cambiar el estado de la cita a 'Cancelada'
        String sqlActualizarEstado = "UPDATE Citas SET Estado = 'Cancelada' WHERE ID_Cita = ?";

        try (Connection connection = ConexionDB.getConnection(); 
                PreparedStatement stmtActualizarEstado = connection.prepareStatement(sqlActualizarEstado)) {
            stmtActualizarEstado.setInt(1, idCita);

            int filasAfectadas = stmtActualizarEstado.executeUpdate();

            if (filasAfectadas > 0) {
                // Liberar el horario ocupado por la cita
                liberarHorarioEmpleado(idCita);
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Error al cancelar la cita: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    private void liberarHorarioEmpleado(int idCita) {
        // Obtener la información de la cita (empleado, fecha, hora, duración)
        String sqlObtenerCita = "SELECT ID_Empleado, Fecha_Cita, Hora_Cita, Duración "
                + "FROM Citas c "
                + "JOIN Servicios s ON c.ID_Servicio = s.ID_Servicio "
                + "WHERE c.ID_Cita = ?";

        try (Connection connection = ConexionDB.getConnection(); 
                PreparedStatement stmtObtenerCita = connection.prepareStatement(sqlObtenerCita)) {
            stmtObtenerCita.setInt(1, idCita);

            ResultSet rs = stmtObtenerCita.executeQuery();

            if (rs.next()) {
                int idEmpleado = rs.getInt("ID_Empleado");
                LocalDate fechaCita = rs.getDate("Fecha_Cita").toLocalDate();
                LocalTime horaCita = rs.getTime("Hora_Cita").toLocalTime();
                int duracion = rs.getInt("Duración");

                LocalTime horaFin = horaCita.plusMinutes(duracion);

                
                System.out.println("Horario liberado: " + horaCita + " - " + horaFin + " para el empleado ID: " + idEmpleado);
             
            }
        } catch (SQLException e) {
            System.out.println("Error al liberar el horario ocupado: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
