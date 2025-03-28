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
public class LoginDB {

    // Método para validar si el usuario existe en la base de datos
    public static boolean validarUsuario(String correo, String contraseña) {
        String query = "SELECT COUNT(*) FROM Usuarios WHERE Correo = ? AND Contraseña = ?";
        
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            
            stmt.setString(1, correo);
            stmt.setString(2, contraseña);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                   
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al validar el usuario: " + e.getMessage());
        }
        return false;
    }
}

