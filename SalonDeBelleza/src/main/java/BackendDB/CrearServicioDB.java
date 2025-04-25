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
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author herson
 */
public class CrearServicioDB {

// Método para crear un servicio
    public boolean crearServicio(Servicio servicio, List<Integer> empleadosIds) {
        Connection connection = null;
        String queryServicio = "INSERT INTO Servicios (Nombre_Servicio, Descripción, Duración, Precio, Estado, Imagen, ID_Encargado, Catalogo_PDF) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        String queryAsociacion = "INSERT INTO Trabajadores_Servicios (ID_Empleado, ID_Servicio) VALUES (?, ?)";

        try {
            connection = ConexionDB.getConnection();
            connection.setAutoCommit(false); // Iniciar transacción

            // Insertar el servicio en la tabla Servicios
            try (PreparedStatement statementServicio = connection.prepareStatement(queryServicio, Statement.RETURN_GENERATED_KEYS)) {
                statementServicio.setString(1, servicio.getNombreServicio());
                statementServicio.setString(2, servicio.getDescripcion());
                statementServicio.setInt(3, servicio.getDuracion());
                statementServicio.setDouble(4, servicio.getPrecio());
                statementServicio.setString(5, servicio.getEstado());
                statementServicio.setBytes(6, servicio.getImagen());
                statementServicio.setInt(7, servicio.getIdEncargado());
                statementServicio.setBytes(8, servicio.getCatalogoPdf());

                int rowsAffected = statementServicio.executeUpdate();
                if (rowsAffected == 0) {
                    connection.rollback();
                    return false; 
                }

                // Obtener el ID del servicio recién insertado
                ResultSet generatedKeys = statementServicio.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int idServicio = generatedKeys.getInt(1);

                    // Asociar empleados al servicio en la tabla Trabajadores_Servicios
                    try (PreparedStatement statementAsociacion = connection.prepareStatement(queryAsociacion)) {
                        for (Integer idEmpleado : empleadosIds) {
                            statementAsociacion.setInt(1, idEmpleado);
                            statementAsociacion.setInt(2, idServicio);
                            statementAsociacion.addBatch(); 
                        }
                        statementAsociacion.executeBatch(); // Ejecutar batch de inserciones
                    }
                } else {
                    connection.rollback();
                    return false;
                }
            }

            connection.commit(); // Confirmar transacción
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            if (connection != null) {
                try {
                    connection.rollback(); 
                } catch (SQLException rollbackEx) {
                    rollbackEx.printStackTrace();
                }
            }
            return false;

        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException closeEx) {
                    closeEx.printStackTrace();
                }
            }
        }
    }

// Método para obtener todos los servicios
    public List<Servicio> obtenerServicios() {
        List<Servicio> servicios = new ArrayList<>();
        String query = "SELECT s.ID_Servicio, s.Nombre_Servicio, s.Descripción, s.Duración, "
                + "s.Precio, s.Estado, s.Imagen, s.Catalogo_PDF, s.ID_Encargado, "
                + "GROUP_CONCAT(u.Nombre) AS EmpleadosAsignados "
                + "FROM Servicios s "
                + "LEFT JOIN Trabajadores_Servicios ts ON s.ID_Servicio = ts.ID_Servicio "
                + "LEFT JOIN Usuarios u ON ts.ID_Empleado = u.ID_Usuario "
                + "GROUP BY s.ID_Servicio";

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
                servicio.setCatalogoPdf(resultSet.getBytes("Catalogo_PDF")); 
                servicio.setIdEncargado(resultSet.getInt("ID_Encargado"));

                // Agregar empleados asignados
                String empleadosAsignados = resultSet.getString("EmpleadosAsignados");
                servicio.setEmpleadosAsignados(empleadosAsignados != null ? empleadosAsignados.split(",") : new String[0]);

                servicios.add(servicio);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return servicios;
    }

    // Método para obtener usuarios con rol "Empleado" para poder asociarlos a un servicio
    public List<Usuario> obtenerEmpleados() {
        List<Usuario> empleados = new ArrayList<>();
        String query = "SELECT ID_Usuario, Nombre, DPI, Telefono, Direccion, Correo, Foto_Perfil, Descripción "
                + "FROM Usuarios WHERE Rol = 'Empleado' AND Estado = 'Activo'";

        try (Connection connection = ConexionDB.getConnection(); PreparedStatement statement = connection.prepareStatement(query); ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Usuario empleado = new Usuario();
                empleado.setIdUsuario(resultSet.getInt("ID_Usuario"));
                empleado.setNombre(resultSet.getString("Nombre"));
                empleado.setDpi(resultSet.getString("DPI"));
                empleado.setTelefono(resultSet.getString("Telefono"));
                empleado.setDireccion(resultSet.getString("Direccion"));
                empleado.setCorreo(resultSet.getString("Correo"));
                empleado.setFotoPerfil(resultSet.getBytes("Foto_Perfil"));
                empleado.setDescripcion(resultSet.getString("Descripción"));

                empleados.add(empleado);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return empleados;
    }

}
