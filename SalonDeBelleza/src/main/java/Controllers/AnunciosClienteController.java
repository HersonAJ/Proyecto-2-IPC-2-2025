/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllers;

import BackendDB.ObtenerAnunciosPorUsuarioDB;
import Modelos.Anuncio;
import Modelos.JWTHelper;
import com.auth0.jwt.exceptions.JWTVerificationException;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author herson
 */
@Path("/anunciosParaClientes")
public class AnunciosClienteController {
    
    private final ObtenerAnunciosPorUsuarioDB obtenerAnunciosDB = new ObtenerAnunciosPorUsuarioDB();
    private final JWTHelper jwtHelper = new JWTHelper();

    @GET
    @Path("/obtener")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerAnuncios(
            @HeaderParam("Authorization") String token,
            @QueryParam("page") int page,
            @QueryParam("pageSize") int pageSize) {
        try {
            int idUsuario = jwtHelper.validateAndGetId(token);
            int offset = (page - 1) * pageSize;
            List<Anuncio> anuncios = obtenerAnunciosDB.obtenerAnunciosPorUsuario(idUsuario, pageSize, offset);
            int total = obtenerAnunciosDB.contarAnunciosPorUsuario(idUsuario);

            Map<String, Object> respuesta = new HashMap<>();
            respuesta.put("anuncios", anuncios);
            respuesta.put("totalAnuncios", total);
            respuesta.put("paginaActual", page);
            respuesta.put("anunciosPorPagina", pageSize);

            return Response.ok(respuesta).build();

        } catch (JWTVerificationException e) {
            return Response.status(Response.Status.UNAUTHORIZED)
                           .entity("{\"message\": \"Token inválido o vencido.\"}")
                           .type(MediaType.APPLICATION_JSON)
                           .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("{\"message\": \"Error interno: " + e.getMessage() + "\"}")
                           .type(MediaType.APPLICATION_JSON)
                           .build();
        }
    }
}