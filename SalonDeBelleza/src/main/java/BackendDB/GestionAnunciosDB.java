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
public class GestionAnunciosDB {

    public List<Anuncio> obtenerTodosLosAnuncios() {
        String sql = "SELECT A.*, GROUP_CONCAT(H.Nombre) AS Hobbies_Relacionados "
                + "FROM Anuncios A "
                + "LEFT JOIN Anuncio_Hobbies AH ON A.ID_Anuncio = AH.ID_Anuncio "
                + "LEFT JOIN Hobbies H ON AH.ID_Hobbie = H.ID_Hobbie "
                + "GROUP BY A.ID_Anuncio";

        List<Anuncio> anuncios = new ArrayList<>();
        try (Connection connection = ConexionDB.getConnection(); PreparedStatement stmt = connection.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

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

                // Mapear la lista de hobbies relacionados
                String hobbiesConcatenados = rs.getString("Hobbies_Relacionados");
                if (hobbiesConcatenados != null && !hobbiesConcatenados.isEmpty()) {
                    List<String> hobbies = List.of(hobbiesConcatenados.split(","));
                    anuncio.setHobbiesRelacionados(hobbies);
                } else {
                    anuncio.setHobbiesRelacionados(new ArrayList<>());
                }

                anuncios.add(anuncio);
            }

        } catch (SQLException e) {
            System.out.println("Error al obtener todos los anuncios: " + e.getMessage());
            e.printStackTrace();
        }
        return anuncios;
    }

    // Método para actualizar el estado de un anuncio
    public boolean actualizarEstadoAnuncio(int idAnuncio, String nuevoEstado) {
        String sql = "UPDATE Anuncios SET Estado = ? WHERE ID_Anuncio = ?";

        try (Connection connection = ConexionDB.getConnection(); PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, nuevoEstado); 
            stmt.setInt(2, idAnuncio);

            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;

        } catch (SQLException e) {
            System.out.println("Error al actualizar el estado del anuncio: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    // Método para reactivar anuncios en estado 'Inactivo'
    public boolean reactivarAnuncio(int idAnuncio) {
        return actualizarEstadoAnuncio(idAnuncio, "Activo");
    }

    // Método para desactivar anuncios
    public boolean desactivarAnuncio(int idAnuncio) {
        return actualizarEstadoAnuncio(idAnuncio, "Inactivo");
    }

}
