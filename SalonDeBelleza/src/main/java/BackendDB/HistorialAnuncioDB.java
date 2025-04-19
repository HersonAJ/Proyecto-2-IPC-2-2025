/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BackendDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;

/**
 *
 * @author herson
 */
public class HistorialAnuncioDB {


    public void insertarHistorial(int idAnuncio, LocalDateTime fechaVisualizacion, String urlMostrada) {
        String sql = "INSERT INTO Historial_Anuncios (ID_Anuncio, Fecha_Visualizacion, URL_Mostrada) "
                   + "VALUES (?, ?, ?)";

        try (Connection connection = ConexionDB.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, idAnuncio);
            statement.setObject(2, fechaVisualizacion != null ? fechaVisualizacion : LocalDateTime.now());
            statement.setString(3, urlMostrada);

            statement.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Error al registrar la visualización en la base de datos: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Se produjo un error inesperado: " + e.getMessage());
            e.printStackTrace();
        }
    }
}


