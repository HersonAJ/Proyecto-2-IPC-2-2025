/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BackendDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author herson
 */
public class GestionPrecioAnunciosDB {

    // Método para obtener el precio diario actual para un tipo de anuncio
    public List<Map<String, Object>> obtenerTodosLosPrecios() {
        String sql = "SELECT Tipo_Anuncio, Precio_Diario FROM Precio_Diario_Anuncios";
        List<Map<String, Object>> listaPrecios = new ArrayList<>();

        try (Connection connection = ConexionDB.getConnection(); 
                PreparedStatement statement = connection.prepareStatement(sql); 
                ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Map<String, Object> precio = new HashMap<>();
                precio.put("tipo", resultSet.getString("Tipo_Anuncio"));
                precio.put("precioPorDia", resultSet.getDouble("Precio_Diario"));
                listaPrecios.add(precio);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener los precios: " + e.getMessage());
            e.printStackTrace();
        }

        return listaPrecios;
    }

    public void actualizarPrecio(String tipoAnuncio, double nuevoPrecio) {
        String sql = "INSERT INTO Precio_Diario_Anuncios (Tipo_Anuncio, Precio_Diario) "
                + "VALUES (?, ?) ON DUPLICATE KEY UPDATE Precio_Diario = ?";

        try (Connection connection = ConexionDB.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, tipoAnuncio);
            statement.setDouble(2, nuevoPrecio);
            statement.setDouble(3, nuevoPrecio); 

            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al actualizar el precio diario: " + e.getMessage());
            e.printStackTrace();
        }
    }

}
