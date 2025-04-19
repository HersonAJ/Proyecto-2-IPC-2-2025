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

        String sqlInsertPagoAnuncio = "INSERT INTO Pagos_Anuncios (ID_Anuncio, Monto, Precio_Por_Dia_Aplicado, Fecha_Pago, Comprador) "
                + "VALUES (?, ?, ?, ?, ?)";

        String sqlObtenerPrecioDiario = "SELECT Precio_Diario FROM Precio_Diario_Anuncios WHERE Tipo_Anuncio = ?";

        Connection connection = null; 

        try {
            connection = ConexionDB.getConnection(); 
            connection.setAutoCommit(false); 

            try (PreparedStatement stmtAnuncio = connection.prepareStatement(sqlInsertAnuncio, PreparedStatement.RETURN_GENERATED_KEYS); 
                    PreparedStatement stmtAnuncioHobbies = connection.prepareStatement(sqlInsertAnuncioHobbies); 
                    PreparedStatement stmtPagoAnuncio = connection.prepareStatement(sqlInsertPagoAnuncio); 
                    PreparedStatement stmtObtenerPrecio = connection.prepareStatement(sqlObtenerPrecioDiario)) {

                // Obtener el precio diario del tipo de anuncio
                stmtObtenerPrecio.setString(1, anuncio.getTipo());
                try (ResultSet rsPrecio = stmtObtenerPrecio.executeQuery()) {
                    if (rsPrecio.next()) {
                        double precioDiario = rsPrecio.getDouble("Precio_Diario");
                        anuncio.setPrecioPorDia(precioDiario);
                    } else {
                        connection.rollback(); // Deshacer la transacción
                        return false;
                    }
                }

                // Asignar valores del anuncio
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
                    try (ResultSet generatedKeys = stmtAnuncio.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            int idAnuncioGenerado = generatedKeys.getInt(1);

                            // Insertar las relaciones con hobbies
                            if (anuncio.getHobbiesRelacionados() != null && !anuncio.getHobbiesRelacionados().isEmpty()) {
                                for (String hobbie : anuncio.getHobbiesRelacionados()) {
                                    int idHobbie = obtenerIdHobbiePorNombre(hobbie, connection);
                                    if (idHobbie != -1) {
                                        stmtAnuncioHobbies.setInt(1, idAnuncioGenerado);
                                        stmtAnuncioHobbies.setInt(2, idHobbie);
                                        stmtAnuncioHobbies.addBatch();
                                    }
                                }
                                stmtAnuncioHobbies.executeBatch(); 
                            }

                            // Registrar el pago en la tabla Pagos_Anuncios
                            double montoTotal = anuncio.getPrecioPorDia() * anuncio.getDuracionDias();
                            stmtPagoAnuncio.setInt(1, idAnuncioGenerado);
                            stmtPagoAnuncio.setDouble(2, montoTotal);
                            stmtPagoAnuncio.setDouble(3, anuncio.getPrecioPorDia());
                            stmtPagoAnuncio.setDate(4, java.sql.Date.valueOf(anuncio.getFechaInicio()));
                            stmtPagoAnuncio.setString(5, anuncio.getNombreAnunciante());
                            stmtPagoAnuncio.executeUpdate(); // Insertar el registro del pago

                        } else {
                            connection.rollback(); 
                            return false;
                        }
                    }
                } else {
                    connection.rollback(); 
                    return false;
                }

                // Confirmar la transacción si todo salió bien
                connection.commit();
                return true;

            }

        } catch (SQLException e) {
            e.printStackTrace();
            if (connection != null) { 
                try {
                    connection.rollback();
                } catch (SQLException rollbackEx) {
                    rollbackEx.printStackTrace();
                }
            }
            return false;
        } finally {
            if (connection != null) { 
                try {
                    connection.close();
                } catch (SQLException closeEx) {
                    closeEx.printStackTrace();
                }
            }
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

    public double obtenerPrecioPorTipo(String tipoAnuncio) {
        String sqlObtenerPrecio = "SELECT Precio_Diario FROM Precio_Diario_Anuncios WHERE Tipo_Anuncio = ?";

        try (Connection connection = ConexionDB.getConnection(); 
                PreparedStatement stmtObtenerPrecio = connection.prepareStatement(sqlObtenerPrecio)) {
            stmtObtenerPrecio.setString(1, tipoAnuncio);

            try (ResultSet rsPrecio = stmtObtenerPrecio.executeQuery()) {
                if (rsPrecio.next()) {
                    // Obtener y retornar el precio diario
                    return rsPrecio.getDouble("Precio_Diario");
                } else {
                    System.out.println("No se encontró el precio diario para el tipo de anuncio: " + tipoAnuncio);
                    return -1; // Indica que no se encontró el precio
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener el precio diario: " + e.getMessage());
            e.printStackTrace();
            return -1; 
        }
    }
}
