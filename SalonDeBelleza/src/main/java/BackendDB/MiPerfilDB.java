/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BackendDB;

import Modelos.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author herson
 */
public class MiPerfilDB {

    public static Usuario getPerfilByCorreo(String correo) {
        String query = "SELECT u.ID_Usuario, u.Nombre, u.DPI, u.Telefono, u.Direccion, u.Correo, u.Contraseña, " +
                       "u.Foto_Perfil, u.Descripción, u.Rol, u.Estado, " +
                       "GROUP_CONCAT(h.Nombre SEPARATOR ', ') AS Hobbies " +
                       "FROM Usuarios u " +
                       "LEFT JOIN Usuario_Hobbies uh ON u.ID_Usuario = uh.ID_Usuario " +
                       "LEFT JOIN Hobbies h ON uh.ID_Hobbie = h.ID_Hobbie " +
                       "WHERE u.Correo = ? " +
                       "GROUP BY u.ID_Usuario, u.Nombre, u.DPI, u.Telefono, u.Direccion, u.Correo, u.Contraseña, u.Foto_Perfil, u.Descripción, u.Rol, u.Estado";
        
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
             
            stmt.setString(1, correo);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Usuario usuario = new Usuario();
                    usuario.setIdUsuario(rs.getInt("ID_Usuario"));
                    usuario.setNombre(rs.getString("Nombre"));
                    usuario.setDpi(rs.getString("DPI"));
                    usuario.setTelefono(rs.getString("Telefono"));
                    usuario.setDireccion(rs.getString("Direccion"));
                    usuario.setCorreo(rs.getString("Correo"));
                    usuario.setContrasena(rs.getString("Contraseña"));
                    usuario.setFotoPerfil(rs.getBytes("Foto_Perfil"));
                    usuario.setDescripcion(rs.getString("Descripción"));
                    usuario.setRol(rs.getString("Rol"));
                    usuario.setEstado(rs.getString("Estado"));
                    // El campo "Hobbies" se obtiene como una cadena separada por comas.
                    usuario.setHobbies(rs.getString("Hobbies"));
                    return usuario;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener el perfil: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
}

