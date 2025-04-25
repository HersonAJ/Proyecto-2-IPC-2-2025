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
public class ObtenerAnunciosPorUsuarioDB {

// Método para obtener anuncios según los hobbies del usuario con paginación
public List<Anuncio> obtenerAnunciosPorUsuario(int idUsuario, int limit, int offset) {
    String sql = "SELECT A.* FROM Anuncios A "
               + "INNER JOIN Anuncio_Hobbies AH ON A.ID_Anuncio = AH.ID_Anuncio "
               + "INNER JOIN Usuario_Hobbies UH ON AH.ID_Hobbie = UH.ID_Hobbie "
               + "WHERE UH.ID_Usuario = ? AND A.Fecha_Fin > CURRENT_DATE() AND A.Estado = 'Activo' "
               + "GROUP BY A.ID_Anuncio "
               + "ORDER BY A.Fecha_Creacion DESC "
               + "LIMIT ? OFFSET ?";

    List<Anuncio> anuncios = new ArrayList<>();
    try (Connection connection = ConexionDB.getConnection();
         PreparedStatement stmt = connection.prepareStatement(sql)) {

        stmt.setInt(1, idUsuario);
        stmt.setInt(2, limit);
        stmt.setInt(3, offset);

        try (ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Anuncio anuncio = new Anuncio();
                anuncio.setIdAnuncio(rs.getInt("ID_Anuncio"));
                anuncio.setNombreAnunciante(rs.getString("Nombre_Anunciante"));
                anuncio.setContactoAnunciante(rs.getString("Contacto_Anunciante"));
                anuncio.setTipo(rs.getString("Tipo_Anuncio"));
                anuncio.setContenidoTexto(rs.getString("Contenido_Texto"));
                anuncio.setUrlImagen(rs.getString("URL_Imagen"));
                anuncio.setUrlVideo(rs.getString("URL_Video"));
                anuncio.setPrecioPorDia(rs.getDouble("Precio_Diario"));
                anuncio.setDuracionDias(rs.getInt("Tiempo_Duración"));
                anuncio.setEstado(rs.getString("Estado"));
                anuncio.setFechaInicio(rs.getDate("Fecha_Creacion").toLocalDate());
                anuncio.setFechaFin(rs.getDate("Fecha_Fin").toLocalDate());
                anuncios.add(anuncio);
            }

        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return anuncios;
}


    // Método para contar el total de anuncios disponibles para el usuario
    public int contarAnunciosPorUsuario(int idUsuario) {
        String sql = "SELECT COUNT(DISTINCT A.ID_Anuncio) AS total "
                   + "FROM Anuncios A "
                   + "INNER JOIN Anuncio_Hobbies AH ON A.ID_Anuncio = AH.ID_Anuncio "
                   + "INNER JOIN Usuario_Hobbies UH ON AH.ID_Hobbie = UH.ID_Hobbie "
                   + "WHERE UH.ID_Usuario = ? AND A.Fecha_Fin > CURRENT_DATE() AND A.Estado = 'Activo'";

        try (Connection connection = ConexionDB.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, idUsuario);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("total");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al contar anuncios: " + e.getMessage());
            e.printStackTrace();
        }
        return 0; 
    }
}


