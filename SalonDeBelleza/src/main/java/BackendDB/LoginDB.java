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
public class LoginDB {

    // Método para validar si el usuario existe en la base de datos
    public static Usuario autenticarUsuario(String correo, String contrasena) {
        String query = "SELECT * FROM Usuarios WHERE Correo = ? AND Contraseña = ?";

        try (Connection conn = ConexionDB.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, correo);
            stmt.setString(2, contrasena);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    // Validar si el usuario está inactivo
                    String estado = rs.getString("Estado");
                    if ("Inactivo".equalsIgnoreCase(estado)) {
                        System.out.println("Usuario inactivo, no puede iniciar sesión.");
                        return null; // Retorna null si el usuario está inactivo
                    }

                    Usuario usuario = new Usuario();
                    usuario.setIdUsuario(rs.getInt("ID_Usuario"));
                    usuario.setNombre(rs.getString("Nombre"));
                    usuario.setDpi(rs.getString("DPI"));
                    usuario.setTelefono(rs.getString("Telefono"));
                    usuario.setDireccion(rs.getString("Direccion"));
                    usuario.setCorreo(rs.getString("Correo"));
                    usuario.setContrasena(rs.getString("Contraseña"));
                    usuario.setRol(rs.getString("Rol"));
                    usuario.setEstado(rs.getString("Estado"));
                    usuario.setDescripcion(rs.getString("Descripción"));
                    return usuario; 
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al autenticar el usuario: " + e.getMessage());
        }
        return null; 
    }

}
