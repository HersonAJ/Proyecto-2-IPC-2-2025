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
public class GestionCitaEmpleadoDB {

    public List<Cita> obtenerCitasAsignadas(int idEmpleado) {
        List<Cita> citasAsignadas = new ArrayList<>();

        String sql = "SELECT c.ID_Cita, c.Fecha_Cita, c.Hora_Cita, c.Estado, c.Factura_Generada, "
                + "s.ID_Servicio, s.Nombre_Servicio, s.Descripción AS Descripcion_Servicio, s.Duración, s.Precio, "
                + "u_cliente.ID_Usuario AS ID_Cliente, u_cliente.Nombre AS Nombre_Cliente "
                + "FROM Citas c "
                + "INNER JOIN Servicios s ON c.ID_Servicio = s.ID_Servicio "
                + "INNER JOIN Usuarios u_cliente ON c.ID_Cliente = u_cliente.ID_Usuario "
                + "WHERE c.ID_Empleado = ?";

        try (Connection connection = ConexionDB.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, idEmpleado);
            ResultSet resultSet = preparedStatement.executeQuery();

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

                // Detalles del cliente
                cita.setIdCliente(resultSet.getInt("ID_Cliente"));
                cita.setNombreCliente(resultSet.getString("Nombre_Cliente"));

                citasAsignadas.add(cita);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return citasAsignadas;
    }

    public boolean cambiarEstadoCita(int idCita, String nuevoEstado) {
        String sqlObtenerEstado = "SELECT Estado FROM Citas WHERE ID_Cita = ?";
        String sqlActualizarEstado = "UPDATE Citas SET Estado = ? WHERE ID_Cita = ?";

        try (Connection connection = ConexionDB.getConnection(); PreparedStatement stmtObtenerEstado = connection.prepareStatement(sqlObtenerEstado); PreparedStatement stmtActualizarEstado = connection.prepareStatement(sqlActualizarEstado)) {

            // Obtener el estado actual de la cita
            stmtObtenerEstado.setInt(1, idCita);
            ResultSet resultSet = stmtObtenerEstado.executeQuery();

            if (resultSet.next()) {
                String estadoActual = resultSet.getString("Estado");

                // Validar que el estado no haya sido modificado previamente
                if (!estadoActual.equals("Pendiente")) {
                    return false; // No se permite cambiar el estado una vez que ya no es "Pendiente"
                }
            } else {
                return false; // La cita no existe
            }

            // Actualizar el estado de la cita
            stmtActualizarEstado.setString(1, nuevoEstado);
            stmtActualizarEstado.setInt(2, idCita);

            int filasActualizadas = stmtActualizarEstado.executeUpdate();
            return filasActualizadas > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    //metodo para obtener las citas para la factura
    public Cita obtenerCitaPorId(int idCita) {
        String sql = "SELECT c.ID_Cita, c.Fecha_Cita, c.Hora_Cita, c.Estado, c.Factura_Generada, "
                + "s.ID_Servicio, s.Nombre_Servicio, s.Descripción AS Descripcion_Servicio, s.Duración, s.Precio, "
                + "u_cliente.ID_Usuario AS ID_Cliente, u_cliente.Nombre AS Nombre_Cliente "
                + "FROM Citas c "
                + "INNER JOIN Servicios s ON c.ID_Servicio = s.ID_Servicio "
                + "INNER JOIN Usuarios u_cliente ON c.ID_Cliente = u_cliente.ID_Usuario "
                + "WHERE c.ID_Cita = ?";

        try (Connection connection = ConexionDB.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, idCita);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
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

                // Detalles del cliente
                cita.setIdCliente(resultSet.getInt("ID_Cliente"));
                cita.setNombreCliente(resultSet.getString("Nombre_Cliente"));

                return cita; // Retorna la cita encontrada
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null; 
    }

}
