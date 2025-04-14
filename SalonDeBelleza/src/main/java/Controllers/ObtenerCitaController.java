/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllers;

import BackendDB.ObtenerCitaDB;
import Modelos.Cita;
import Modelos.JWTHelper;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

/**
 *
 * @author herson
 */
@Path("/obtenerCita")
public class ObtenerCitaController {

    private final ObtenerCitaDB obtenerCitaDB = new ObtenerCitaDB();
    private final JWTHelper jwtHelper = new JWTHelper();

    @GET
    @Path("/pendientes/{idCliente}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerCitasPendientesPorCliente(
            @HeaderParam("Authorization") String token,
            @PathParam("idCliente") int idCliente) {

        try {
            // Validar que el token no sea nulo y eliminar el prefijo "Bearer "
            if (token == null || !token.startsWith("Bearer ")) {
                return Response.status(Response.Status.UNAUTHORIZED)
                        .entity("{\"message\": \"Token inválido o ausente.\"}")
                        .type(MediaType.APPLICATION_JSON)
                        .build();
            }

            token = token.substring(7); // Remover "Bearer " del token

            // Validar que el token sea válido y no esté vencido
            if (!jwtHelper.validateToken(token)) {
                return Response.status(Response.Status.UNAUTHORIZED)
                        .entity("{\"message\": \"Token inválido o vencido.\"}")
                        .type(MediaType.APPLICATION_JSON)
                        .build();
            }

            // Verificar que el ID del cliente coincide con el token
            int idUsuarioDelToken = jwtHelper.getIdUsuarioFromToken(token);
            if (idCliente != idUsuarioDelToken) {
                return Response.status(Response.Status.FORBIDDEN)
                        .entity("{\"message\": \"Acceso denegado. El ID del cliente no coincide con el token.\"}")
                        .type(MediaType.APPLICATION_JSON)
                        .build();
            }

            // Obtener las citas pendientes desde la base de datos
            List<Cita> citasPendientes = obtenerCitaDB.obtenerCitasPendientesPorCliente(idCliente);

            if (citasPendientes.isEmpty()) {
                return Response.status(Response.Status.NO_CONTENT)
                        .entity("{\"message\": \"No se encontraron citas pendientes para este cliente.\"}")
                        .type(MediaType.APPLICATION_JSON)
                        .build();
            }

            return Response.ok(citasPendientes).build();

        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"message\": \"Error al obtener las citas pendientes: " + e.getMessage() + "\"}")
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }
    }

}
