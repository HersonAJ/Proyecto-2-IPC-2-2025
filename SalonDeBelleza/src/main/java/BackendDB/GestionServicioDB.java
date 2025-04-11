/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BackendDB;

import Modelos.Servicio;
import Modelos.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author herson
 */
public class GestionServicioDB {

    // Método para gestionar un servicio (dinámico: edición, actualización de estado, asignaciones)
    public boolean gestionarServicio(int idServicio, Servicio servicio, byte[] nuevaImagen, String nuevoEstado, List<Integer> empleadosIds) {
        Connection connection = null;

        try {
            connection = ConexionDB.getConnection();
            connection.setAutoCommit(false); // Iniciar transacción

            // Actualizar datos básicos del servicio
            if (servicio != null) {
                String queryActualizarServicio = "UPDATE Servicios SET Nombre_Servicio = ?, Descripción = ?, Duración = ?, Precio = ? WHERE ID_Servicio = ?";
                try (PreparedStatement statement = connection.prepareStatement(queryActualizarServicio)) {
                    statement.setString(1, servicio.getNombreServicio());
                    statement.setString(2, servicio.getDescripcion());
                    statement.setInt(3, servicio.getDuracion());
                    statement.setDouble(4, servicio.getPrecio());
                    statement.setInt(5, idServicio);
                    statement.executeUpdate();
                }
            }

            // Actualizar la imagen del servicio
            if (nuevaImagen != null) {
                String queryActualizarImagen = "UPDATE Servicios SET Imagen = ? WHERE ID_Servicio = ?";
                try (PreparedStatement statement = connection.prepareStatement(queryActualizarImagen)) {
                    statement.setBytes(1, nuevaImagen);
                    statement.setInt(2, idServicio);
                    statement.executeUpdate();
                }
            }

            // Actualizar el estado del servicio
            if (nuevoEstado != null) {
                String queryActualizarEstado = "UPDATE Servicios SET Estado = ? WHERE ID_Servicio = ?";
                try (PreparedStatement statement = connection.prepareStatement(queryActualizarEstado)) {
                    statement.setString(1, nuevoEstado);
                    statement.setInt(2, idServicio);
                    statement.executeUpdate();
                }
            }

            // Actualizar empleados asignados (eliminar todos y volver a asignar si se proporcionan IDs)
            if (empleadosIds != null) {
                String queryEliminarEmpleados = "DELETE FROM Trabajadores_Servicios WHERE ID_Servicio = ?";
                try (PreparedStatement statement = connection.prepareStatement(queryEliminarEmpleados)) {
                    statement.setInt(1, idServicio);
                    statement.executeUpdate();
                }

                String queryInsertarEmpleados = "INSERT INTO Trabajadores_Servicios (ID_Empleado, ID_Servicio) VALUES (?, ?)";
                try (PreparedStatement statement = connection.prepareStatement(queryInsertarEmpleados)) {
                    for (Integer idEmpleado : empleadosIds) {
                        statement.setInt(1, idEmpleado);
                        statement.setInt(2, idServicio);
                        statement.addBatch(); // Batch para múltiples inserciones
                    }
                    statement.executeBatch();
                }
            }

            connection.commit(); // Confirmar transacción
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            if (connection != null) {
                try {
                    connection.rollback(); // Revertir cambios en caso de error
                } catch (Exception rollbackEx) {
                    rollbackEx.printStackTrace();
                }
            }
            return false;

        } finally {
            if (connection != null) {
                try {
                    connection.close(); // Cerrar conexión
                } catch (Exception closeEx) {
                    closeEx.printStackTrace();
                }
            }
        }
    }

    // Método para obtener los empleados asignados a un servicio
    public List<Usuario> obtenerEmpleadosPorServicio(int idServicio) {
        List<Usuario> empleados = new ArrayList<>();
        String query = "SELECT u.ID_Usuario, u.Nombre, u.Descripción "
                + "FROM Usuarios u "
                + "JOIN Trabajadores_Servicios ts ON u.ID_Usuario = ts.ID_Empleado "
                + "WHERE ts.ID_Servicio = ?";

        try (Connection connection = ConexionDB.getConnection(); PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, idServicio);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Usuario empleado = new Usuario();
                    empleado.setIdUsuario(resultSet.getInt("ID_Usuario"));
                    empleado.setNombre(resultSet.getString("Nombre"));
                    empleado.setDescripcion(resultSet.getString("Descripción"));
                    empleados.add(empleado);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return empleados;
    }
    // Método para obtener todos los servicios

    public List<Servicio> obtenerTodosLosServicios() {
        List<Servicio> servicios = new ArrayList<>();
        String query = "SELECT ID_Servicio, Nombre_Servicio, Descripción, Duración, Precio, Estado, Imagen, ID_Encargado FROM Servicios";

        try (Connection connection = ConexionDB.getConnection(); PreparedStatement statement = connection.prepareStatement(query); ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Servicio servicio = new Servicio();
                servicio.setIdServicio(resultSet.getInt("ID_Servicio"));
                servicio.setNombreServicio(resultSet.getString("Nombre_Servicio"));
                servicio.setDescripcion(resultSet.getString("Descripción"));
                servicio.setDuracion(resultSet.getInt("Duración"));
                servicio.setPrecio(resultSet.getDouble("Precio"));
                servicio.setEstado(resultSet.getString("Estado"));
                servicio.setImagen(resultSet.getBytes("Imagen"));
                servicio.setIdEncargado(resultSet.getInt("ID_Encargado"));

                servicios.add(servicio);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return servicios;
    }

    // Método para obtener empleados no asignados a un servicio específico
    public List<Usuario> obtenerEmpleadosNoAsignados(int idServicio) {
        List<Usuario> empleadosNoAsignados = new ArrayList<>();
        String query = "SELECT u.ID_Usuario, u.Nombre, u.Descripción "
                + "FROM Usuarios u "
                + "LEFT JOIN Trabajadores_Servicios ts "
                + "ON u.ID_Usuario = ts.ID_Empleado AND ts.ID_Servicio = ? "
                + "WHERE ts.ID_Servicio IS NULL AND u.Rol = 'Empleado'";

        try (Connection connection = ConexionDB.getConnection(); PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, idServicio);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Usuario empleado = new Usuario();
                    empleado.setIdUsuario(resultSet.getInt("ID_Usuario"));
                    empleado.setNombre(resultSet.getString("Nombre"));
                    empleado.setDescripcion(resultSet.getString("Descripción"));
                    empleadosNoAsignados.add(empleado);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return empleadosNoAsignados;
    }

}
