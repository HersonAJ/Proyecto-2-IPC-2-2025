/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BackendDB;

/**
 *
 * @author herson
 */
import Modelos.Cita;
import java.sql.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HistorialClienteDB {

    // Formateadores de Fecha y Hora
    private static final DateTimeFormatter FECHA_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter HORA_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    public List<Cita> obtenerHistorialCitasAtendidas(int idCliente) {
        String sql = "SELECT c.ID_Cita, s.Nombre_Servicio, s.Precio AS Total, c.Fecha_Cita, c.Hora_Cita, c.Estado, "
                   + "u.Nombre AS Empleado, f.ID_Factura "
                   + "FROM Citas c "
                   + "JOIN Servicios s ON c.ID_Servicio = s.ID_Servicio "
                   + "JOIN Usuarios u ON c.ID_Empleado = u.ID_Usuario "
                   + "LEFT JOIN Facturas f ON c.ID_Cita = f.ID_Cita "
                   + "WHERE c.ID_Cliente = ? AND c.Estado = 'Atendida'";

        List<Cita> citasAtendidas = new ArrayList<>();

        try (Connection connection = ConexionDB.getConnection(); PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idCliente);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Cita cita = new Cita();
                cita.setIdCita(rs.getInt("ID_Cita"));
                cita.setNombreServicio(rs.getString("Nombre_Servicio"));
                cita.setPrecioServicio(rs.getDouble("Total"));

                cita.setFechaCita(rs.getDate("Fecha_Cita").toLocalDate());
                cita.setHoraCita(rs.getTime("Hora_Cita").toLocalTime());

                cita.setEstado(rs.getString("Estado"));
                cita.setNombreEmpleado(rs.getString("Empleado"));
                cita.setIdFactura(rs.getInt("ID_Factura"));
                citasAtendidas.add(cita);
            }

        } catch (SQLException e) {
            System.out.println("Error al obtener el historial de citas atendidas: " + e.getMessage());
            e.printStackTrace();
        }
        return citasAtendidas;
    }

    public List<Cita> obtenerHistorialCitasCanceladas(int idCliente) {
        String sql = "SELECT c.ID_Cita, s.Nombre_Servicio, c.Fecha_Cita, c.Hora_Cita, c.Estado, u.Nombre AS Empleado "
                   + "FROM Citas c "
                   + "JOIN Servicios s ON c.ID_Servicio = s.ID_Servicio "
                   + "JOIN Usuarios u ON c.ID_Empleado = u.ID_Usuario "
                   + "WHERE c.ID_Cliente = ? AND c.Estado = 'Cancelada'";

        List<Cita> citasCanceladas = new ArrayList<>();

        try (Connection connection = ConexionDB.getConnection(); PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idCliente);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Cita cita = new Cita();
                cita.setIdCita(rs.getInt("ID_Cita"));
                cita.setNombreServicio(rs.getString("Nombre_Servicio"));

                cita.setFechaCita(rs.getDate("Fecha_Cita").toLocalDate());
                cita.setHoraCita(rs.getTime("Hora_Cita").toLocalTime());

                cita.setEstado(rs.getString("Estado"));
                cita.setNombreEmpleado(rs.getString("Empleado"));
                citasCanceladas.add(cita);
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener el historial de citas canceladas: " + e.getMessage());
            e.printStackTrace();
        }
        return citasCanceladas;
    }


    public List<Map<String, Object>> prepararRespuestaJson(List<Cita> citas) {
        List<Map<String, Object>> respuesta = new ArrayList<>();

        for (Cita cita : citas) {
            Map<String, Object> datosCita = new HashMap<>();
            datosCita.put("idCita", cita.getIdCita());
            datosCita.put("nombreServicio", cita.getNombreServicio());
            datosCita.put("precioServicio", cita.getPrecioServicio());
            datosCita.put("fechaCita", cita.getFechaCita().format(FECHA_FORMATTER)); 
            datosCita.put("horaCita", cita.getHoraCita().format(HORA_FORMATTER));   
            datosCita.put("estado", cita.getEstado());
            datosCita.put("nombreEmpleado", cita.getNombreEmpleado());
            datosCita.put("idFactura", cita.getIdFactura());
            respuesta.add(datosCita);
        }

        return respuesta;
    }
}

