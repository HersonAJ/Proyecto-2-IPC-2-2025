/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllers;

import BackendDB.HistorialClienteDB;
import Modelos.Cita;
import Modelos.JWTHelper;
import com.auth0.jwt.exceptions.JWTVerificationException;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;


/**
 *
 * @author herson
 */
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;
import java.util.Map;

@Path("/historial")
public class HistorialClienteController {

    private final HistorialClienteDB historialClienteDB = new HistorialClienteDB();
    private final JWTHelper jwtHelper = new JWTHelper();

    @GET
    @Path("/atendidas")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerHistorialCitasAtendidas(@HeaderParam("Authorization") String token) {
        try {
            // Validar el token y obtener el ID del usuario
            int idCliente = jwtHelper.validateAndGetId(token);

            // Obtener las citas atendidas
            List<Cita> citasAtendidas = historialClienteDB.obtenerHistorialCitasAtendidas(idCliente);

            if (citasAtendidas.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND)
                               .entity("{\"message\": \"No se encontraron citas atendidas.\"}")
                               .type(MediaType.APPLICATION_JSON)
                               .build();
            }

            
            List<Map<String, Object>> respuestaFormateada = historialClienteDB.prepararRespuestaJson(citasAtendidas);

            return Response.ok(respuestaFormateada).build();
        } catch (JWTVerificationException e) {
            return Response.status(Response.Status.UNAUTHORIZED)
                           .entity("{\"message\": \"Token inválido o vencido.\"}")
                           .type(MediaType.APPLICATION_JSON)
                           .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("{\"message\": \"Error al obtener el historial de citas atendidas.\"}")
                           .type(MediaType.APPLICATION_JSON)
                           .build();
        }
    }

    @GET
    @Path("/canceladas")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerHistorialCitasCanceladas(@HeaderParam("Authorization") String token) {
        try {
            // Validar el token y obtener el ID del usuario
            int idCliente = jwtHelper.validateAndGetId(token);

            // Obtener las citas canceladas
            List<Cita> citasCanceladas = historialClienteDB.obtenerHistorialCitasCanceladas(idCliente);

            if (citasCanceladas.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND)
                               .entity("{\"message\": \"No se encontraron citas canceladas.\"}")
                               .type(MediaType.APPLICATION_JSON)
                               .build();
            }

        
            List<Map<String, Object>> respuestaFormateada = historialClienteDB.prepararRespuestaJson(citasCanceladas);

            return Response.ok(respuestaFormateada).build();
        } catch (JWTVerificationException e) {
            return Response.status(Response.Status.UNAUTHORIZED)
                           .entity("{\"message\": \"Token inválido o vencido.\"}")
                           .type(MediaType.APPLICATION_JSON)
                           .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("{\"message\": \"Error al obtener el historial de citas canceladas.\"}")
                           .type(MediaType.APPLICATION_JSON)
                           .build();
        }
    }
}


