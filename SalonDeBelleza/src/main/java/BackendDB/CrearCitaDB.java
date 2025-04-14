/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BackendDB;

import Modelos.Cita;
import Modelos.Servicio;
import Modelos.Usuario;
import java.sql.*;
import java.time.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author herson
 */
public class CrearCitaDB {

    public boolean crearCita(Cita cita) {
        LocalTime horaCita = cita.getHoraCita();
        LocalDate fechaCita = cita.getFechaCita();

        // Validar horario de apertura
        if (!validarHorarioApertura(horaCita, fechaCita)) {
            System.out.println("La hora seleccionada está fuera del horario de apertura.");
            return false;
        }

        // Validar disponibilidad del trabajador
        if (!validarDisponibilidadTrabajador(cita.getIdEmpleado(), fechaCita, horaCita)) {
            System.out.println("El trabajador seleccionado no está disponible en la fecha y hora seleccionada.");
            return false;
        }

        // Insertar la cita si las validaciones son exitosas
        String sql = "INSERT INTO Citas (ID_Servicio, ID_Cliente, ID_Empleado, Fecha_Cita, Hora_Cita, Estado, Factura_Generada) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = ConexionDB.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, cita.getIdServicio());
            preparedStatement.setInt(2, cita.getIdCliente());
            preparedStatement.setInt(3, cita.getIdEmpleado());
            preparedStatement.setDate(4, java.sql.Date.valueOf(fechaCita));
            preparedStatement.setTime(5, java.sql.Time.valueOf(horaCita));
            preparedStatement.setString(6, cita.getEstado());
            preparedStatement.setBoolean(7, false);

            int filasAfectadas = preparedStatement.executeUpdate();
            return filasAfectadas > 0;

        } catch (SQLException e) {
            System.out.println("Error al crear la cita: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    public List<Usuario> obtenerEmpleadoPorServicio(int idServicio) {
        String sql = "SELECT u.ID_Usuario, u.Nombre, u.Correo, u.Descripción "
                + "FROM Usuarios u "
                + "INNER JOIN Trabajadores_Servicios ts ON u.ID_Usuario = ts.ID_Empleado "
                + "WHERE ts.ID_Servicio = ?";

        List<Usuario> empleados = new ArrayList<>();

        try (Connection connection = ConexionDB.getConnection(); PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, idServicio);

            ResultSet resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                Usuario empleado = new Usuario();
                empleado.setIdUsuario(resultSet.getInt("ID_Usuario"));
                empleado.setNombre(resultSet.getString("Nombre"));
                empleado.setCorreo(resultSet.getString("Correo"));
                empleado.setDescripcion(resultSet.getString("Descripción"));
                empleados.add(empleado);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return empleados;
    }

    // Método para obtener solo los servicios (sin empleados)
    public List<Servicio> obtenerServicios() {
        String sqlServicios = "SELECT s.ID_Servicio, s.Nombre_Servicio, s.Descripción, s.Duración, s.Precio, s.Estado, s.Imagen, s.ID_Encargado "
                + "FROM Servicios s";

        List<Servicio> servicios = new ArrayList<>();

        try (Connection connection = ConexionDB.getConnection(); PreparedStatement stmtServicios = connection.prepareStatement(sqlServicios)) {

            ResultSet rsServicios = stmtServicios.executeQuery();

            while (rsServicios.next()) {
                Servicio servicio = new Servicio();
                servicio.setIdServicio(rsServicios.getInt("ID_Servicio"));
                servicio.setNombreServicio(rsServicios.getString("Nombre_Servicio"));
                servicio.setDescripcion(rsServicios.getString("Descripción"));
                servicio.setDuracion(rsServicios.getInt("Duración"));
                servicio.setPrecio(rsServicios.getDouble("Precio"));
                servicio.setEstado(rsServicios.getString("Estado"));
                servicio.setImagen(rsServicios.getBytes("Imagen"));
                servicio.setIdEncargado(rsServicios.getInt("ID_Encargado"));
                servicios.add(servicio);
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener los servicios: " + e.getMessage());
            e.printStackTrace();
        }

        return servicios;
    }

    public boolean validarHorarioApertura(LocalTime horaCita, LocalDate fechaCita) {
        // Obtener el día de la semana para la fecha de la cita
        DayOfWeek diaSemana = fechaCita.getDayOfWeek();
        int diaSemanaBD = diaSemana.getValue(); // devuelve los dias con numeros

        String sql = "SELECT Hora_Apertura, Hora_Cierre FROM Horarios WHERE Día_Semana = ? AND ID_Empleado IS NULL"; // Horario general

        try (Connection connection = ConexionDB.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, diaSemanaBD);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                LocalTime horaApertura = resultSet.getTime("Hora_Apertura").toLocalTime();
                LocalTime horaCierre = resultSet.getTime("Hora_Cierre").toLocalTime();

                // Validar que la hora esté dentro del rango
                if (!horaCita.isBefore(horaApertura) && !horaCita.isAfter(horaCierre)) {
                    return true;
                } else {
                    System.out.println("La hora está fuera del rango permitido.");
                    return false;
                }
            } else {
                System.out.println("No se encontraron horarios generales para el día: " + diaSemanaBD);
            }
        } catch (SQLException e) {
            System.out.println("Error al validar el horario de apertura: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    public boolean validarDisponibilidadTrabajador(int idEmpleado, LocalDate fechaCita, LocalTime horaCita) {
        // Obtener duración del servicio
        int duracionServicio = obtenerDuracionServicio(idEmpleado);
        if (duracionServicio <= 0) {
            System.out.println("No se pudo obtener la duración del servicio.");
            return false;
        }

        LocalTime horaFinCita = horaCita.plusMinutes(duracionServicio);

        String sql = "SELECT Hora_Cita, s.Duración "
                + "FROM Citas c "
                + "INNER JOIN Servicios s ON c.ID_Servicio = s.ID_Servicio "
                + "WHERE c.ID_Empleado = ? AND c.Fecha_Cita = ? AND c.Estado = 'Pendiente'";

        try (Connection connection = ConexionDB.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, idEmpleado);
            preparedStatement.setDate(2, java.sql.Date.valueOf(fechaCita));

            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                LocalTime horaExistente = rs.getTime("Hora_Cita").toLocalTime();
                int duracionExistente = rs.getInt("Duración");
                LocalTime horaFinExistente = horaExistente.plusMinutes(duracionExistente);

                // Verificar si hay solapamiento
                boolean seSolapan = !horaCita.isAfter(horaFinExistente.minusSeconds(1))
                        && !horaFinCita.isBefore(horaExistente.plusSeconds(1));

                if (seSolapan) {
                    System.out.println("El horario se solapa con otra cita existente.");
                    return false;
                }
            }

            return true;

        } catch (SQLException e) {
            System.out.println("Error al validar la disponibilidad del trabajador: " + e.getMessage());
            e.printStackTrace();
        }

        return false;
    }

    private int obtenerDuracionServicio(int idEmpleado) {
        String sql = "SELECT s.Duración FROM Trabajadores_Servicios ts "
                + "INNER JOIN Servicios s ON ts.ID_Servicio = s.ID_Servicio "
                + "WHERE ts.ID_Empleado = ? LIMIT 1";

        try (Connection connection = ConexionDB.getConnection(); PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, idEmpleado);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("Duración");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // Error
    }

    //metodo de prueba para enviar la disponibilidad
    public List<Map<String, String>> obtenerHorariosOcupados(int idEmpleado, LocalDate fecha) {
        List<Map<String, String>> horariosOcupados = new ArrayList<>();

        String sql = "SELECT c.Hora_Cita, s.Duración "
                + "FROM Citas c "
                + "JOIN Servicios s ON c.ID_Servicio = s.ID_Servicio "
                + "WHERE c.ID_Empleado = ? "
                + "AND c.Fecha_Cita = ? "
                + "AND c.Estado = 'Pendiente' "
                + "ORDER BY c.Hora_Cita";

        try (Connection connection = ConexionDB.getConnection(); PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, idEmpleado);
            stmt.setDate(2, java.sql.Date.valueOf(fecha));

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                LocalTime horaInicio = rs.getTime("Hora_Cita").toLocalTime();
                int duracion = rs.getInt("Duración");
                LocalTime horaFin = horaInicio.plusMinutes(duracion);

                Map<String, String> bloque = new HashMap<>();
                bloque.put("inicio", horaInicio.toString());
                bloque.put("fin", horaFin.toString());
                bloque.put("estado", "ocupado");

                horariosOcupados.add(bloque);
            }

        } catch (SQLException e) {
            System.out.println("Error al obtener horarios ocupados: " + e.getMessage());
            e.printStackTrace();
        }

        return horariosOcupados;
    }
}