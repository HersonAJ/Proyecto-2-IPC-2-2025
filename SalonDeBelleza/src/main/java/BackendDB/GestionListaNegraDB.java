/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BackendDB;

import Modelos.ListaNegra;
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
public class GestionListaNegraDB {

    public boolean agregarAListaNegra(ListaNegra listaNegra) {
        String verificarQuery = "SELECT ID_Lista FROM Lista_Negra WHERE ID_Cliente = ?";
        String actualizarQuery = "UPDATE Lista_Negra SET ID_Cita = ?, Motivo = ?, Estado = 'En Lista' WHERE ID_Cliente = ?";
        String insertarQuery = "INSERT INTO Lista_Negra (ID_Cliente, ID_Cita, Motivo, Estado) VALUES (?, ?, ?, 'En Lista')";
        Connection connection = null;
        PreparedStatement verificarStmt = null;
        PreparedStatement actualizarStmt = null;
        PreparedStatement insertarStmt = null;
        ResultSet resultSet = null;

        try {
            connection = ConexionDB.getConnection();

            // Verificar si ya existe un registro para el cliente
            verificarStmt = connection.prepareStatement(verificarQuery);
            verificarStmt.setInt(1, listaNegra.getIdCliente());
            resultSet = verificarStmt.executeQuery();

            if (resultSet.next()) {
                // Actualizar el registro existente
                actualizarStmt = connection.prepareStatement(actualizarQuery);
                actualizarStmt.setInt(1, listaNegra.getIdCita());
                actualizarStmt.setString(2, listaNegra.getMotivo());
                actualizarStmt.setInt(3, listaNegra.getIdCliente());
                int rowsUpdated = actualizarStmt.executeUpdate();
                return rowsUpdated > 0; 

            } else {
                // Insertar un nuevo registro
                insertarStmt = connection.prepareStatement(insertarQuery);
                insertarStmt.setInt(1, listaNegra.getIdCliente());
                insertarStmt.setInt(2, listaNegra.getIdCita());
                insertarStmt.setString(3, listaNegra.getMotivo());
                int rowsInserted = insertarStmt.executeUpdate();
                return rowsInserted > 0; 
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;

        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (verificarStmt != null) {
                    verificarStmt.close();
                }
                if (actualizarStmt != null) {
                    actualizarStmt.close();
                }
                if (insertarStmt != null) {
                    insertarStmt.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public boolean cambiarEstadoListaNegra(int idLista, String nuevoEstado) {
        String query = "UPDATE Lista_Negra SET Estado = ? WHERE ID_Lista = ?";
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = ConexionDB.getConnection();
            statement = connection.prepareStatement(query);

            statement.setString(1, nuevoEstado);
            statement.setInt(2, idLista);
            statement.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;

        } finally {
            try {
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
    }

    public List<ListaNegra> obtenerClientesEnListaNegra() {
        String query = "SELECT ln.ID_Lista, ln.ID_Cliente, ln.ID_Cita, ln.Motivo, ln.Estado, "
                + "u.Nombre AS NombreCliente, u.Correo AS CorreoCliente "
                + "FROM Lista_Negra ln "
                + "JOIN Usuarios u ON ln.ID_Cliente = u.ID_Usuario "
                + "WHERE ln.Estado = 'En Lista'";
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<ListaNegra> listaNegra = new ArrayList<>();

        try {
            connection = ConexionDB.getConnection();
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                ListaNegra cliente = new ListaNegra();
                cliente.setIdLista(resultSet.getInt("ID_Lista"));
                cliente.setIdCliente(resultSet.getInt("ID_Cliente"));
                cliente.setIdCita(resultSet.getInt("ID_Cita"));
                cliente.setMotivo(resultSet.getString("Motivo"));
                cliente.setEstado(resultSet.getString("Estado"));
                cliente.setNombreCliente(resultSet.getString("NombreCliente"));
                listaNegra.add(cliente);
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

        return listaNegra;
    }

}
