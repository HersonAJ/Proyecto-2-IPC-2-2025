/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllers;

import BackendDB.ValidacionListaNegraDB;
import Modelos.JWTHelper;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/**
 *
 * @author herson
 */
@Path("/validacion")
public class ValidacionListaNegraController {

    private final ValidacionListaNegraDB validacionListaNegraDB = new ValidacionListaNegraDB();
    private final JWTHelper jwtHelper = new JWTHelper();

    @GET
    @Path("/permitido")
    @Produces(MediaType.APPLICATION_JSON)
    public Response esClientePermitido(@HeaderParam("Authorization") String token) {
        try {
            // Validar el token y limpiarlo
            if (!jwtHelper.validateToken2(token)) {
                return Response.status(Response.Status.UNAUTHORIZED)
                        .entity("{\"message\":\"Token inválido\"}")
                        .build();
            }

            // Obtener el ID del cliente desde el token
            String cleanedToken = jwtHelper.cleanToken(token);
            int idCliente = jwtHelper.getIdUsuarioFromToken(cleanedToken);

            // Validar si el cliente está permitido
            boolean permitido = validacionListaNegraDB.esClientePermitidoParaCita(idCliente);
            if (permitido) {
                return Response.ok("{\"permitido\": true, \"message\": \"El cliente puede agendar citas.\"}").build();
            } else {
                return Response.ok("{\"permitido\": false, \"message\": \"Lo sentimos por el momento no puedes agendar citas, intenta luego!.\"}").build();
            }

        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"message\":\"Ocurrió un error interno.\"}")
                    .build();
        }
    }
}

