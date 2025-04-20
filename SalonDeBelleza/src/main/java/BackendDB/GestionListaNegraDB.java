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
        String query = "INSERT INTO Lista_Negra (ID_Cliente, ID_Cita, Motivo, Estado) VALUES (?, ?, ?, 'En Lista')";
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = ConexionDB.getConnection();
            statement = connection.prepareStatement(query);

            statement.setInt(1, listaNegra.getIdCliente());
            statement.setInt(2, listaNegra.getIdCita());
            statement.setString(3, listaNegra.getMotivo());
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
