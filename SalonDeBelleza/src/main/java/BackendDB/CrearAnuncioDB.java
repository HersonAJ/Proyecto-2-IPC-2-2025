/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BackendDB;

import Modelos.Anuncio;
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
public class CrearAnuncioDB {

    public boolean insertarAnuncio(Anuncio anuncio) {
        String sqlInsertAnuncio = "INSERT INTO Anuncios (Nombre_Anunciante, Contacto_Anunciante, Tipo_Anuncio, Contenido_Texto, URL_Imagen, "
                + "URL_Video, Precio_Diario, Tiempo_Duración, Fecha_Creacion, Estado, ID_Marketing) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, 'Activo', ?)";

        String sqlInsertAnuncioHobbies = "INSERT INTO Anuncio_Hobbies (ID_Anuncio, ID_Hobbie) VALUES (?, ?)";

        try (Connection connection = ConexionDB.getConnection(); 
                PreparedStatement stmtAnuncio = connection.prepareStatement(sqlInsertAnuncio, PreparedStatement.RETURN_GENERATED_KEYS); 
                PreparedStatement stmtAnuncioHobbies = connection.prepareStatement(sqlInsertAnuncioHobbies)) {

            // Asignar los valores del objeto Anuncio a los parámetros SQL
            stmtAnuncio.setString(1, anuncio.getNombreAnunciante());
            stmtAnuncio.setString(2, anuncio.getContactoAnunciante());
            stmtAnuncio.setString(3, anuncio.getTipo());
            stmtAnuncio.setString(4, anuncio.getContenidoTexto());
            stmtAnuncio.setString(5, anuncio.getUrlImagen());
            stmtAnuncio.setString(6, anuncio.getUrlVideo());
            stmtAnuncio.setDouble(7, anuncio.getPrecioPorDia());
            stmtAnuncio.setInt(8, anuncio.getDuracionDias());
            stmtAnuncio.setDate(9, java.sql.Date.valueOf(anuncio.getFechaInicio()));
            stmtAnuncio.setInt(10, anuncio.getIdAnuncio());

            // Ejecutar la inserción del anuncio
            int filasInsertadas = stmtAnuncio.executeUpdate();
            if (filasInsertadas > 0) {
                // Obtener el ID generado del anuncio
                try (ResultSet generatedKeys = stmtAnuncio.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int idAnuncioGenerado = generatedKeys.getInt(1);

                        // Insertar las relaciones con hobbies en la tabla Anuncio_Hobbies
                        if (anuncio.getHobbiesRelacionados() != null && !anuncio.getHobbiesRelacionados().isEmpty()) {
                            for (String hobbie : anuncio.getHobbiesRelacionados()) {
                                int idHobbie = obtenerIdHobbiePorNombre(hobbie, connection); // Método para obtener ID del hobbie
                                if (idHobbie != -1) {
                                    stmtAnuncioHobbies.setInt(1, idAnuncioGenerado);
                                    stmtAnuncioHobbies.setInt(2, idHobbie);
                                    stmtAnuncioHobbies.addBatch(); // Usar batch para hacer la insercion en bloque
                                }
                            }
                            stmtAnuncioHobbies.executeBatch(); // Ejecutar el batch de inserciones
                        }
                    }
                }
                return true; // Inserción exitosa
            } else {
                return false; // Inserción fallida
            }

        } catch (SQLException e) {
            System.out.println("Error al insertar el anuncio o hobbies: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

// Método auxiliar para obtener el ID del hobbie por su nombre
    private int obtenerIdHobbiePorNombre(String nombreHobbie, Connection connection) {
        String sqlBuscarHobbie = "SELECT ID_Hobbie FROM Hobbies WHERE Nombre = ?";
        try (PreparedStatement stmtBuscarHobbie = connection.prepareStatement(sqlBuscarHobbie)) {
            stmtBuscarHobbie.setString(1, nombreHobbie);
            try (ResultSet rs = stmtBuscarHobbie.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("ID_Hobbie");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener el ID del hobbie: " + e.getMessage());
            e.printStackTrace();
        }
        return -1; // Indica que no se encontró el hobbie
    }

    public List<String> obtenerTodosLosHobbies() {
        String sql = "SELECT Nombre FROM Hobbies";
        List<String> hobbies = new ArrayList<>();

        try (Connection connection = ConexionDB.getConnection(); 
                PreparedStatement stmt = connection.prepareStatement(sql); 
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                hobbies.add(rs.getString("Nombre"));
            }

        } catch (Exception e) {
            System.out.println("Error al obtener los hobbies: " + e.getMessage());
            e.printStackTrace();
        }
        return hobbies;
    }
}
