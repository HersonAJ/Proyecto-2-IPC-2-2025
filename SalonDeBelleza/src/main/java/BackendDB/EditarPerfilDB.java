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
public class EditarPerfilDB {

    public static boolean actualizarPerfil(int idUsuario, Usuario usuario) {
        String query = "UPDATE  Usuarios SET Nombre = ?, DPI = ?, Telefono = ?, Direccion = ?, Contraseña = ?, "
                + "Foto_Perfil = ?, Descripción = ? WHERE ID_Usuario = ?";

        try (Connection conn = ConexionDB.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, usuario.getNombre());
            stmt.setString(2, usuario.getDpi());
            stmt.setString(3, usuario.getTelefono());
            stmt.setString(4, usuario.getDireccion());
            stmt.setString(5, usuario.getContrasena());
            stmt.setBytes(6, usuario.getFotoPerfil());
            stmt.setString(7, usuario.getDescripcion());
            stmt.setInt(8, idUsuario);

            int rowAffected = stmt.executeUpdate();
            return rowAffected > 0;
        } catch (SQLException e) {
            System.out.println("Error al actualizar perfil" + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public static String obtenerContrasenaById(int idUsuario) {
        String query = "SELECT Contraseña FROM Usuarios WHERE ID_Usuario = ?";
        try (Connection conn = ConexionDB.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, idUsuario);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("Contraseña");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener la contraseña: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

}
