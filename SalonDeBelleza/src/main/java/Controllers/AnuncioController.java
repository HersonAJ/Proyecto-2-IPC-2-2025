/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllers;

import BackendDB.CrearAnuncioDB;
import Modelos.Anuncio;
import Modelos.JWTHelper;
import com.auth0.jwt.exceptions.JWTVerificationException;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.POST;
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
@Path("/anuncios")
public class AnuncioController {

    private final CrearAnuncioDB crearAnuncioDB = new CrearAnuncioDB();
    private final JWTHelper jwtHelper = new JWTHelper();

    @POST
    @Path("/crear")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response crearAnuncio(@HeaderParam("Authorization") String token, Anuncio anuncio) {
        try {
            // Validar el token y obtener el ID del usuario de marketing
            int idMarketing = jwtHelper.validateAndGetId(token);
            anuncio.setIdAnuncio(idMarketing);

            // Validaciones opcionales
            if (anuncio.getNombreAnunciante() == null || anuncio.getNombreAnunciante().isEmpty()) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("{\"message\": \"El nombre del anunciante es obligatorio.\"}")
                        .type(MediaType.APPLICATION_JSON)
                        .build();
            }

            // Intentar insertar el anuncio en la base de datos
            boolean resultado = crearAnuncioDB.insertarAnuncio(anuncio);

            if (resultado) {
                return Response.status(Response.Status.CREATED)
                        .entity("{\"message\": \"Anuncio creado exitosamente.\"}")
                        .type(MediaType.APPLICATION_JSON)
                        .build();
            } else {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity("{\"message\": \"Error al insertar el anuncio en la base de datos.\"}")
                        .type(MediaType.APPLICATION_JSON)
                        .build();
            }
        } catch (JWTVerificationException e) {
            // Token inválido o vencido
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity("{\"message\": \"Token inválido o vencido.\"}")
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        } catch (Exception e) {
            // Error inesperado
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"message\": \"Error interno al crear el anuncio: " + e.getMessage() + "\"}")
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }
    }

    @GET
    @Path("/hobbies")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerHobbies(@HeaderParam("Authorization") String token) {
        try {
            // Validar el token
            jwtHelper.validateToken2(token);

            // Obtener los hobbies desde la base de datos
            List<String> hobbies = crearAnuncioDB.obtenerTodosLosHobbies();

            return Response.ok(hobbies).build();
        } catch (JWTVerificationException e) {
            // Token inválido o vencido
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity("{\"message\": \"Token inválido o vencido.\"}")
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        } catch (Exception e) {
            // Error inesperado
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"message\": \"Error al obtener los hobbies: " + e.getMessage() + "\"}")
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }
    }

    @GET
    @Path("/precio/{tipoAnuncio}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerPrecioPorTipo(@HeaderParam("Authorization") String token, @PathParam("tipoAnuncio") String tipoAnuncio) {
        try {
            // Validar el token
            jwtHelper.validateToken2(token);

            // Consultar el precio según el tipo de anuncio
            double precioDiario = crearAnuncioDB.obtenerPrecioPorTipo(tipoAnuncio);

            if (precioDiario != -1) {
                // Retornar el precio en la respuesta
                return Response.ok("{\"tipoAnuncio\": \"" + tipoAnuncio + "\", \"precioDiario\": " + precioDiario + "}")
                        .type(MediaType.APPLICATION_JSON)
                        .build();
            } else {
                // Si no se encuentra el precio para el tipo de anuncio
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"message\": \"No se encontró el precio para el tipo de anuncio seleccionado.\"}")
                        .type(MediaType.APPLICATION_JSON)
                        .build();
            }
        } catch (JWTVerificationException e) {
            // Token inválido o vencido
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity("{\"message\": \"Token inválido o vencido.\"}")
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        } catch (Exception e) {
            // Error inesperado
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"message\": \"Error al obtener el precio: " + e.getMessage() + "\"}")
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }
    }
}
