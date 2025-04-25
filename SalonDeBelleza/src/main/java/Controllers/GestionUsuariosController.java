/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllers;

import BackendDB.GestionarUsuariosDB;
import Modelos.JWTHelper;
import Modelos.Usuario;
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
@Path("/gestion-usuarios")
public class GestionUsuariosController {

    private final GestionarUsuariosDB gestionUsuariosDB = new GestionarUsuariosDB();
    private final JWTHelper jwtHelper = new JWTHelper();

    @GET
    @Path("/gestion")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerUsuarios(@HeaderParam("Authorization") String authToken) {
        try {
            if (authToken == null || !jwtHelper.validateToken(authToken.replace("Bearer ", ""))) {
                return Response.status(Response.Status.UNAUTHORIZED)
                        .entity("Token inválido o no proporcionado. Acceso denegado.")
                        .build();
            }

            String rol = jwtHelper.getRolFromToken(authToken.replace("Bearer ", ""));
            if (!"Administrador".equals(rol)) {
                return Response.status(Response.Status.FORBIDDEN)
                        .entity("Acceso denegado: no tiene permisos suficientes.")
                        .build();
            }

            List<Usuario> usuarios = gestionUsuariosDB.obtenerUsuarios();
            return Response.ok(usuarios).build();

        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Ocurrió un error al obtener los usuarios.")
                    .build();
        }
    }

    @PUT
    @Path("/estado/{idUsuario}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response modificarEstadoUsuario(@PathParam("idUsuario") int idUsuario, Map<String, String> estadoData, @HeaderParam("Authorization") String authToken) {
        try {
            if (authToken == null || !jwtHelper.validateToken(authToken.replace("Bearer ", ""))) {
                return Response.status(Response.Status.UNAUTHORIZED)
                        .entity("{\"message\": \"Token inválido o no proporcionado.\"}")
                        .build();
            }

            String rol = jwtHelper.getRolFromToken(authToken.replace("Bearer ", ""));
            if (!"Administrador".equals(rol)) {
                return Response.status(Response.Status.FORBIDDEN)
                        .entity("{\"message\": \"Acceso denegado: no tiene permisos suficientes.\"}")
                        .build();
            }

            String nuevoEstado = estadoData.get("estado");
            boolean actualizado = gestionUsuariosDB.modificarEstadoUsuario(idUsuario, nuevoEstado);

            if (actualizado) {
                return Response.ok("{\"message\": \"Estado actualizado correctamente.\"}").build();
            } else {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("{\"message\": \"No se pudo actualizar el estado del usuario.\"}")
                        .build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"message\": \"Ocurrió un error al actualizar el estado del usuario.\"}")
                    .build();
        }
    }

}
