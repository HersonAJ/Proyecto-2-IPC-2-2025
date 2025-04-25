/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllers;

import BackendDB.GestionCitaEmpleadoDB;
import Modelos.Cita;
import Modelos.JWTHelper;
import com.auth0.jwt.exceptions.JWTVerificationException;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

/**
 *
 * @author herson
 */
@Path("/gestionCitasEmpleado")
public class GestionCitaEmpleadoController {

    private final GestionCitaEmpleadoDB gestionCitaEmpleadoDB = new GestionCitaEmpleadoDB();
    private final JWTHelper jwtHelper = new JWTHelper();

    @GET
    @Path("/asignadas")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerCitasAsignadas(@HeaderParam("Authorization") String token) {
        try {
            int idEmpleado = jwtHelper.validateAndGetId(token);

            List<Cita> citasAsignadas = gestionCitaEmpleadoDB.obtenerCitasAsignadas(idEmpleado);

            if (citasAsignadas.isEmpty()) {
                return Response.status(Response.Status.NO_CONTENT)
                        .entity("{\"message\": \"No se encontraron citas asignadas para este empleado.\"}")
                        .type(MediaType.APPLICATION_JSON)
                        .build();
            }

            return Response.ok(citasAsignadas).build();

        } catch (JWTVerificationException e) {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity("{\"message\": \"Token inválido o vencido.\"}")
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"message\": \"Error al obtener las citas asignadas: " + e.getMessage() + "\"}")
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }
    }

    @PUT
    @Path("/cambiarEstado/{idCita}")
    public Response cambiarEstadoCita(@HeaderParam("Authorization") String token, @PathParam("idCita") int idCita, @QueryParam("nuevoEstado") String nuevoEstado) {
        try {
            int idEmpleado = jwtHelper.validateAndGetId(token); 

            boolean cambioExitoso = gestionCitaEmpleadoDB.cambiarEstadoCita(idCita, nuevoEstado);

            if (!cambioExitoso) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("{\"message\": \"No se puede cambiar el estado. La cita ya no es modificable.\"}")
                        .type(MediaType.APPLICATION_JSON)
                        .build();
            }

            return Response.ok("{\"message\": \"El estado de la cita se actualizó correctamente.\"}")
                    .type(MediaType.APPLICATION_JSON)
                    .build();

        } catch (JWTVerificationException e) {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity("{\"message\": \"Token inválido o vencido.\"}")
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"message\": \"Error al cambiar el estado: " + e.getMessage() + "\"}")
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }
    }
}

