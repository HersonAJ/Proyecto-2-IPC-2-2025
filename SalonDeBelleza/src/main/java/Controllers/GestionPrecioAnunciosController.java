/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllers;

import BackendDB.GestionPrecioAnunciosDB;
import Modelos.Anuncio;
import Modelos.JWTHelper;
import com.auth0.jwt.exceptions.JWTVerificationException;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;
import java.util.Map;

/**
 *
 * @author herson
 */
@Path("/gestion-precio-anuncios")
public class GestionPrecioAnunciosController {

    private final GestionPrecioAnunciosDB gestionPrecioAnunciosDB = new GestionPrecioAnunciosDB();
    private final JWTHelper jwtHelper = new JWTHelper();

    @GET
    @Path("/obtener-todos-precios")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerTodosLosPrecios(@HeaderParam("Authorization") String token) {
        try {
            jwtHelper.validateToken2(token);

            List<Map<String, Object>> precios = gestionPrecioAnunciosDB.obtenerTodosLosPrecios();

            if (precios.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"message\": \"No hay precios configurados para ningún tipo de anuncio.\"}")
                        .type(MediaType.APPLICATION_JSON)
                        .build();
            }

            return Response.ok(precios)
                    .type(MediaType.APPLICATION_JSON)
                    .build();

        } catch (JWTVerificationException e) {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity("{\"message\": \"Token inválido o vencido.\"}")
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"message\": \"Error al obtener los precios: " + e.getMessage() + "\"}")
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }
    }

    @PUT
    @Path("/actualizar-precio")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response actualizarPrecio(@HeaderParam("Authorization") String token, Anuncio anuncio) {

        try {
            if (!jwtHelper.validateToken2(token)) {
                System.err.println("Token inválido o vencido."); 
                return Response.status(Response.Status.UNAUTHORIZED)
                        .entity("{\"message\": \"Token inválido o vencido.\"}")
                        .type(MediaType.APPLICATION_JSON)
                        .build();
            }

            String rol = jwtHelper.getRolFromToken(jwtHelper.cleanToken(token));

            if (!"Administrador".equals(rol)) {
                return Response.status(Response.Status.FORBIDDEN)
                        .entity("{\"message\": \"No tienes permisos para realizar esta acción.\"}")
                        .type(MediaType.APPLICATION_JSON)
                        .build();
            }

            if (anuncio.getTipo() == null || anuncio.getTipo().isEmpty()) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("{\"message\": \"El tipo de anuncio es obligatorio.\"}")
                        .type(MediaType.APPLICATION_JSON)
                        .build();
            }

            if (anuncio.getPrecioPorDia() <= 0) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("{\"message\": \"El nuevo precio debe ser mayor a 0.\"}")
                        .type(MediaType.APPLICATION_JSON)
                        .build();
            }

            double nuevoPrecio = anuncio.getPrecioPorDia();
            gestionPrecioAnunciosDB.actualizarPrecio(anuncio.getTipo(), nuevoPrecio);

            return Response.ok("{\"message\": \"Precio actualizado correctamente para el tipo de anuncio: " + anuncio.getTipo() + ".\"}")
                    .type(MediaType.APPLICATION_JSON)
                    .build();

        } catch (JWTVerificationException e) {
            System.err.println("Error de validación del token: " + e.getMessage()); 
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity("{\"message\": \"Token inválido o vencido.\"}")
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        } catch (Exception e) {
            System.err.println("Error inesperado: " + e.getMessage()); 
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"message\": \"Error inesperado al actualizar el precio: " + e.getMessage() + "\"}")
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }
    }
}