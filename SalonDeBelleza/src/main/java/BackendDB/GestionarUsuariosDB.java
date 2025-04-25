/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BackendDB;

import Modelos.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author herson
 */
public class GestionarUsuariosDB {

    // Método para obtener todos los usuarios excepto los de tipo Cliente
    public List<Usuario> obtenerUsuarios() {
        List<Usuario> usuarios = new ArrayList<>();
        String query = "SELECT ID_Usuario, Nombre, DPI, Telefono, Direccion, Correo, Foto_Perfil, Descripción, Rol, Estado "
                + "FROM Usuarios WHERE Rol != 'Cliente'";

        try (Connection connection = ConexionDB.getConnection(); PreparedStatement statement = connection.prepareStatement(query); ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Usuario usuario = new Usuario();
                usuario.setIdUsuario(resultSet.getInt("ID_Usuario"));
                usuario.setNombre(resultSet.getString("Nombre"));
                usuario.setDpi(resultSet.getString("DPI"));
                usuario.setTelefono(resultSet.getString("Telefono"));
                usuario.setDireccion(resultSet.getString("Direccion"));
                usuario.setCorreo(resultSet.getString("Correo"));
                usuario.setFotoPerfil(resultSet.getBytes("Foto_Perfil")); 
                usuario.setDescripcion(resultSet.getString("Descripción"));
                usuario.setRol(resultSet.getString("Rol"));
                usuario.setEstado(resultSet.getString("Estado"));

                usuarios.add(usuario);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return usuarios;
    }

    public boolean modificarEstadoUsuario(int idUsuario, String nuevoEstado) {
        String query = "UPDATE Usuarios SET Estado = ? WHERE ID_Usuario = ?";
        try (Connection connection = ConexionDB.getConnection(); PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, nuevoEstado);
            statement.setInt(2, idUsuario);

            return statement.executeUpdate() > 0; 
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
