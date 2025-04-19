/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllers;

import BackendDB.HistorialAnuncioDB;
import Modelos.HistorialAnuncio;
import Modelos.JWTHelper;
import com.auth0.jwt.exceptions.JWTVerificationException;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.time.LocalDateTime;

/**
 *
 * @author herson
 */
@Path("/historial-anuncios")
public class HistorialAnuncioController {

    private final HistorialAnuncioDB historialAnuncioDB = new HistorialAnuncioDB();
    private final JWTHelper jwtHelper = new JWTHelper();

    @POST
    @Path("/registrar")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response registrarVisualizacion(
            @HeaderParam("Authorization") String token,
            HistorialAnuncio historialAnuncio) { 
        try {

            jwtHelper.validateToken2(token);

            if (historialAnuncio.getIdAnuncio() <= 0) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("{\"message\": \"El ID del anuncio debe ser mayor a 0.\"}")
                        .type(MediaType.APPLICATION_JSON)
                        .build();
            }
            if (historialAnuncio.getUrlMostrada() == null || historialAnuncio.getUrlMostrada().isEmpty()) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("{\"message\": \"La URL mostrada es obligatoria.\"}")
                        .type(MediaType.APPLICATION_JSON)
                        .build();
            }

            historialAnuncioDB.insertarHistorial(
                    historialAnuncio.getIdAnuncio(),
                    LocalDateTime.now(),
                    historialAnuncio.getUrlMostrada()
            );

            return Response.status(Response.Status.CREATED)
                    .entity("{\"message\": \"Visualización registrada exitosamente.\"}")
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        } catch (JWTVerificationException e) {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity("{\"message\": \"Token inválido o vencido.\"}")
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"message\": \"Error al registrar la visualización: " + e.getMessage() + "\"}")
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }
    }

}
