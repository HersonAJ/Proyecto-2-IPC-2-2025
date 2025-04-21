/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BackendDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author herson
 */
public class ValidacionListaNegraDB {

    public boolean esClientePermitidoParaCita(int idCliente) {
        String query = "SELECT Estado FROM Lista_Negra WHERE ID_Cliente = ?";
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = ConexionDB.getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt(1, idCliente);
            resultSet = statement.executeQuery();

            // Si el cliente está en la lista negra, validar su estado
            if (resultSet.next()) {
                String estado = resultSet.getString("Estado");
                return "Permitido".equalsIgnoreCase(estado); 
            }

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;

        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}

