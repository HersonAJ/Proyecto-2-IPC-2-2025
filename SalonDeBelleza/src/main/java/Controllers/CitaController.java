/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllers;

import BackendDB.CitaDB;
import Modelos.Cita;
import Modelos.Servicio;
import Modelos.Usuario;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

/**
 *
 * @author herson
 */
/*
@Path("/citas")
public class CitaController {

    private final CitaDB citaDB = new CitaDB();

    @POST
    @Path("/crear")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response crearCita(Cita cita) {
        try {
            // Registrar datos recibidos para depuración
            System.out.println("Datos recibidos: " + cita);

            // Validar horario de apertura
            if (!citaDB.validarHorarioApertura(cita.getHoraCita(), cita.getFechaCita())) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("{\"message\": \"La hora seleccionada está fuera del horario de apertura.\"}")
                        .build();
            }

            // Validar disponibilidad del trabajador
            if (!citaDB.validarDisponibilidadTrabajador(cita.getIdEmpleado(), cita.getFechaCita(), cita.getHoraCita())) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("{\"message\": \"El trabajador seleccionado no está disponible en la fecha y hora seleccionada.\"}")
                        .build();
            }

            // Lógica para crear la cita
            boolean resultado = citaDB.crearCita(cita);

            if (resultado) {
                return Response.status(Response.Status.CREATED)
                        .entity("{\"message\": \"Cita creada exitosamente\"}")
                        .build();
            } else {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity("{\"message\": \"Error al crear la cita.\"}")
                        .build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"message\": \"Ocurrió un error interno: " + e.getMessage() + "\"}")
                    .build();
        }
    }

    @GET
    @Path("/empleados/{idServicio}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerEmpleadosPorServicio(@PathParam("idServicio") int idServicio) {
        try {
            List<Usuario> empleados = citaDB.obtenerEmpleadoPorServicio(idServicio);

            if (empleados.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"message\": \"No se encontraron empleados para el servicio seleccionado. \"}")
                        .build();
            }
            return Response.ok(empleados).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"message\": \"Error al obtener a los empleados. \"}" + e.getMessage())
                    .build();
        }
    }

    @GET
    @Path("/servicios")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerServicios() {
        try {
            
            List<Servicio> servicios = citaDB.obtenerServicios();

            if (servicios.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"message\": \"No se encontraron servicios disponibles. \"}")
                        .build();
            }

            return Response.ok(servicios).build();

        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"message\": \"Error al obtener los servicios. \"}" + e.getMessage())
                    .build();
        }
    }

}
*/

@Path("/citas")
public class CitaController {

    private final CitaDB citaDB = new CitaDB();

@POST
@Path("/crear")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public Response crearCita(Cita cita) {
    try {
        // Validación de horario de apertura
        if (!citaDB.validarHorarioApertura(cita.getHoraCita(), cita.getFechaCita())) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"message\": \"La hora seleccionada está fuera del horario de apertura.\"}")
                    .build();
        }

        // Validación de disponibilidad del trabajador
        if (!citaDB.validarDisponibilidadTrabajador(cita.getIdEmpleado(), cita.getFechaCita(), cita.getHoraCita())) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"message\": \"El trabajador seleccionado no está disponible en la fecha y hora seleccionada.\"}")
                    .build();
        }

        // Intentar crear la cita
        boolean resultado = citaDB.crearCita(cita);

        if (resultado) {
            return Response.status(Response.Status.CREATED)
                    .entity("{\"message\": \"Cita creada exitosamente.\"}")
                    .build();
        } else {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"message\": \"Error al crear la cita por una razón desconocida.\"}")
                    .build();
        }
    } catch (Exception e) {
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("{\"message\": \"Ocurrió un error interno al procesar la solicitud: " + e.getMessage() + "\"}")
                .build();
    }
}


    @GET
    @Path("/empleados/{idServicio}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerEmpleadosPorServicio(@PathParam("idServicio") int idServicio) {
        try {
            List<Usuario> empleados = citaDB.obtenerEmpleadoPorServicio(idServicio);

            if (empleados.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"message\": \"No se encontraron empleados para el servicio seleccionado. \"}")
                        .build();
            }
            return Response.ok(empleados).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"message\": \"Error al obtener a los empleados. \"}" + e.getMessage())
                    .build();
        }
    }

    @GET
    @Path("/servicios")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerServicios() {
        try {
            
            List<Servicio> servicios = citaDB.obtenerServicios();

            if (servicios.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"message\": \"No se encontraron servicios disponibles. \"}")
                        .build();
            }

            return Response.ok(servicios).build();

        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"message\": \"Error al obtener los servicios. \"}" + e.getMessage())
                    .build();
        }
    }

@GET
@Path("/horarios-ocupados/{idEmpleado}/{fecha}")
@Produces(MediaType.APPLICATION_JSON)
public Response obtenerHorariosOcupados(
    @PathParam("idEmpleado") int idEmpleado,
    @PathParam("fecha") String fechaStr) {
    
    try {
        LocalDate fecha = LocalDate.parse(fechaStr);
        List<Map<String, String>> horarios = citaDB.obtenerHorariosOcupados(idEmpleado, fecha);
        
        return Response.ok(horarios).build();
        
    } catch (Exception e) {
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("{\"message\": \"Error al obtener horarios ocupados: " + e.getMessage() + "\"}")
                .build();
    }
}
    
}