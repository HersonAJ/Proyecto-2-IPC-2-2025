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
import java.sql.Statement;

/**
 *
 * @author herson
 */
public class RegistroDB {

    public static boolean insertarUsuario(Usuario usuario) {
        // Consulta para insertar en Usuarios (incluye Descripción)
        String query = "INSERT INTO Usuarios (Nombre, DPI, Telefono, Direccion, Correo, Contraseña, Foto_Perfil, Descripción, Rol, Estado) " +
                       "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            // Asignar parámetros usando los getters de Usuario
            stmt.setString(1, usuario.getNombre());
            stmt.setString(2, usuario.getDpi());
            stmt.setString(3, usuario.getTelefono());
            stmt.setString(4, usuario.getDireccion());
            stmt.setString(5, usuario.getCorreo());
            stmt.setString(6, usuario.getContrasena());
            if (usuario.getFotoPerfil() != null) {
                stmt.setBlob(7, new java.io.ByteArrayInputStream(usuario.getFotoPerfil()));
            } else {
                stmt.setNull(7, java.sql.Types.BLOB);
            }
            stmt.setString(8, usuario.getDescripcion()); // Agregamos la descripción
            stmt.setString(9, usuario.getRol());
            stmt.setString(10, usuario.getEstado());

            int filas = stmt.executeUpdate();
            if (filas > 0) {
                // Obtener el ID generado del usuario
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        int idUsuario = rs.getInt(1);
                        // Si el usuario tiene hobbies (cadena separada por comas)
                        if (usuario.getHobbies() != null && !usuario.getHobbies().trim().isEmpty()) {
                            String[] hobbiesArray = usuario.getHobbies().split(",");
                            for (String hobby : hobbiesArray) {
                                String hobbyName = hobby.trim();
                                if (!hobbyName.isEmpty()) {
                                    int idHobbie = getOrInsertHobbie(conn, hobbyName);
                                    if (idHobbie > 0) {
                                        // Insertar la asociación en la tabla Usuario_Hobbies
                                        String queryRelation = "INSERT INTO Usuario_Hobbies (ID_Usuario, ID_Hobbie) VALUES (?, ?)";
                                        try (PreparedStatement stmtRel = conn.prepareStatement(queryRelation)) {
                                            stmtRel.setInt(1, idUsuario);
                                            stmtRel.setInt(2, idHobbie);
                                            stmtRel.executeUpdate();
                                        }
                                    }
                                }
                            }
                        }
                        return true;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    // Método auxiliar para obtener (o insertar) un hobbie en la tabla Hobbies
    private static int getOrInsertHobbie(Connection conn, String hobbyName) throws SQLException {
        // Primero, intenta seleccionar el hobbie si ya existe
        String querySelect = "SELECT ID_Hobbie FROM Hobbies WHERE Nombre = ?";
        try (PreparedStatement stmtSelect = conn.prepareStatement(querySelect)) {
            stmtSelect.setString(1, hobbyName);
            try (ResultSet rs = stmtSelect.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("ID_Hobbie");
                }
            }
        }
        // Si no existe, inserta el nuevo hobbie
        String queryInsert = "INSERT INTO Hobbies (Nombre) VALUES (?)";
        try (PreparedStatement stmtInsert = conn.prepareStatement(queryInsert, Statement.RETURN_GENERATED_KEYS)) {
            stmtInsert.setString(1, hobbyName);
            int affectedRows = stmtInsert.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet rs = stmtInsert.getGeneratedKeys()) {
                    if (rs.next()) {
                        return rs.getInt(1);
                    }
                }
            }
        }
        return -1;  // Indica error si no se pudo insertar o recuperar el ID
    }
}



