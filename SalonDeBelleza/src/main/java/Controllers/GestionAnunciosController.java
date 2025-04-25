/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllers;

import BackendDB.GestionAnunciosDB;
import Modelos.Anuncio;
import Modelos.JWTHelper;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

/**
 *
 * @author herson
 */
@Path("/gestion-anuncios")
public class GestionAnunciosController {

    private final GestionAnunciosDB gestionAnunciosDB = new GestionAnunciosDB();
    private final JWTHelper jwtHelper = new JWTHelper(); 

    @GET
    @Path("/todos")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerTodosLosAnuncios(@Context HttpHeaders headers) {
        try {
            String token = headers.getHeaderString("Authorization");
            if (!jwtHelper.validateToken2(token)) {
                return Response.status(Response.Status.UNAUTHORIZED)
                        .entity("{\"message\": \"Token inválido o ausente.\"}")
                        .type(MediaType.APPLICATION_JSON)
                        .build();
            }

            List<Anuncio> anuncios = gestionAnunciosDB.obtenerTodosLosAnuncios();
            return Response.ok(anuncios).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"message\": \"Error al obtener los anuncios: " + e.getMessage() + "\"}")
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }
    }

    @PUT
    @Path("/actualizar-estado/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response actualizarEstadoAnuncio(@PathParam("id") int idAnuncio, @QueryParam("estado") String nuevoEstado, @Context HttpHeaders headers) {
        try {
            // Validar el token
            String token = headers.getHeaderString("Authorization");
            if (!jwtHelper.validateToken2(token)) {
                return Response.status(Response.Status.UNAUTHORIZED)
                        .entity("{\"message\": \"Token inválido o ausente.\"}")
                        .type(MediaType.APPLICATION_JSON)
                        .build();
            }

            boolean actualizado = gestionAnunciosDB.actualizarEstadoAnuncio(idAnuncio, nuevoEstado);

            if (actualizado) {
                return Response.ok("{\"message\": \"Estado del anuncio actualizado correctamente.\"}").build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"message\": \"No se encontró el anuncio o no se pudo actualizar el estado.\"}")
                        .type(MediaType.APPLICATION_JSON)
                        .build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"message\": \"Error al actualizar el estado del anuncio: " + e.getMessage() + "\"}")
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }
    }

    @PUT
    @Path("/reactivar/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response reactivarAnuncio(@PathParam("id") int idAnuncio, @Context HttpHeaders headers) {
        try {
            String token = headers.getHeaderString("Authorization");
            if (!jwtHelper.validateToken2(token)) {
                return Response.status(Response.Status.UNAUTHORIZED)
                        .entity("{\"message\": \"Token inválido o ausente.\"}")
                        .type(MediaType.APPLICATION_JSON)
                        .build();
            }

            boolean reactivado = gestionAnunciosDB.reactivarAnuncio(idAnuncio);

            if (reactivado) {
                return Response.ok("{\"message\": \"Anuncio reactivado correctamente.\"}").build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"message\": \"No se encontró el anuncio o no se pudo reactivar.\"}")
                        .type(MediaType.APPLICATION_JSON)
                        .build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"message\": \"Error al reactivar el anuncio: " + e.getMessage() + "\"}")
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }
    }

    @PUT
    @Path("/desactivar/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response desactivarAnuncio(@PathParam("id") int idAnuncio, @Context HttpHeaders headers) {
        try {
            String token = headers.getHeaderString("Authorization");
            if (!jwtHelper.validateToken2(token)) {
                return Response.status(Response.Status.UNAUTHORIZED)
                        .entity("{\"message\": \"Token inválido o ausente.\"}")
                        .type(MediaType.APPLICATION_JSON)
                        .build();
            }

            boolean desactivado = gestionAnunciosDB.desactivarAnuncio(idAnuncio);

            if (desactivado) {
                return Response.ok("{\"message\": \"Anuncio desactivado correctamente.\"}").build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"message\": \"No se encontró el anuncio o no se pudo desactivar.\"}")
                        .type(MediaType.APPLICATION_JSON)
                        .build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"message\": \"Error al desactivar el anuncio: " + e.getMessage() + "\"}")
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }
    }
}