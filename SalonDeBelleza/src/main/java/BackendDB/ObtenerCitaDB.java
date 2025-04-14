/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BackendDB;

import Modelos.Cita;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author herson
 */
public class ObtenerCitaDB {

    public List<Cita> obtenerCitasPendientesPorCliente(int idCliente) {

        List<Cita> citasPendientes = new ArrayList<>();

        String sql = "SELECT c.ID_Cita, c.Fecha_Cita, c.Hora_Cita, c.Estado, c.Factura_Generada, "
                + "s.ID_Servicio, s.Nombre_Servicio, s.Descripción AS Descripcion_Servicio, s.Duración, s.Precio, "
                + "u_empleado.ID_Usuario AS ID_Empleado, u_empleado.Nombre AS Nombre_Empleado "
                + "FROM Citas c "
                + "INNER JOIN Servicios s ON c.ID_Servicio = s.ID_Servicio "
                + "INNER JOIN Usuarios u_empleado ON c.ID_Empleado = u_empleado.ID_Usuario "
                + "WHERE c.ID_Cliente = ? AND c.Estado = 'Pendiente'";

        try (Connection connection = ConexionDB.getConnection(); PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, idCliente);
            ResultSet resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                Cita cita = new Cita();
                cita.setIdCita(resultSet.getInt("ID_Cita"));
                cita.setFechaCita(resultSet.getDate("Fecha_Cita").toLocalDate());
                cita.setHoraCita(resultSet.getTime("Hora_Cita").toLocalTime());
                cita.setEstado(resultSet.getString("Estado"));
                cita.setFacturaGenerada(resultSet.getBoolean("Factura_Generada"));

                // Detalles del servicio
                cita.setIdServicio(resultSet.getInt("ID_Servicio"));
                cita.setNombreServicio(resultSet.getString("Nombre_Servicio"));
                cita.setDescripcionServicio(resultSet.getString("Descripcion_Servicio"));
                cita.setDuracionServicio(resultSet.getInt("Duración"));
                cita.setPrecioServicio(resultSet.getDouble("Precio"));

                // Detalles del empleado
                cita.setIdEmpleado(resultSet.getInt("ID_Empleado"));
                cita.setNombreEmpleado(resultSet.getString("Nombre_Empleado"));

                citasPendientes.add(cita);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return citasPendientes;
    }

}
