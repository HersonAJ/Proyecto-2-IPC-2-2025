/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllers;

import BackendDB.GestionListaNegraDB;
import Modelos.JWTHelper;
import Modelos.ListaNegra;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
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
@Path("/lista-negra")
public class GestionListaNegraController {

    private final GestionListaNegraDB gestionListaNegraDB = new GestionListaNegraDB();
    private final JWTHelper jwtHelper = new JWTHelper();

    @POST
    @Path("/agregar")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response agregarAListaNegra(@HeaderParam("Authorization") String token, ListaNegra listaNegra) {
        try {

            if (!jwtHelper.validateToken2(token)) {
                System.err.println("Error: Token inválido tras validación.");
                return Response.status(Response.Status.UNAUTHORIZED)
                        .entity("{\"message\":\"Token inválido\"}")
                        .build();
            }

            String cleanedToken = jwtHelper.cleanToken(token);
            String rol = jwtHelper.getRolFromToken(cleanedToken);

            if (!"Empleado".equals(rol)) {
                return Response.status(Response.Status.FORBIDDEN)
                        .entity("{\"message\":\"Acceso denegado\"}")
                        .build();
            }

            boolean exito = gestionListaNegraDB.agregarAListaNegra(listaNegra);
            if (exito) {
                return Response.ok("{\"message\":\"Cliente agregado a la lista negra exitosamente.\"}").build();
            } else {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity("{\"message\":\"Error al agregar cliente a la lista negra.\"}")
                        .build();
            }

        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"message\":\"Ocurrió un error interno.\"}")
                    .build();
        }
    }

    @PUT
    @Path("/cambiar-estado/{idLista}/{nuevoEstado}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response cambiarEstadoListaNegra(
            @HeaderParam("Authorization") String token,
            @PathParam("idLista") int idLista,
            @PathParam("nuevoEstado") String nuevoEstado) {

        try {
            if (!jwtHelper.validateToken2(token)) {
                return Response.status(Response.Status.UNAUTHORIZED)
                        .entity("{\"message\":\"Token inválido\"}")
                        .build();
            }

            String cleanedToken = jwtHelper.cleanToken(token);
            String rol = jwtHelper.getRolFromToken(cleanedToken);

            if (!"Administrador".equals(rol)) {
                return Response.status(Response.Status.FORBIDDEN)
                        .entity("{\"message\":\"Acceso denegado\"}")
                        .build();
            }

            boolean exito = gestionListaNegraDB.cambiarEstadoListaNegra(idLista, nuevoEstado);
            if (exito) {
                return Response.ok("{\"message\":\"Estado actualizado exitosamente.\"}").build();
            } else {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity("{\"message\":\"Error al actualizar estado.\"}")
                        .build();
            }

        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"message\":\"Ocurrió un error interno.\"}")
                    .build();
        }
    }

    @GET
    @Path("/clientes-en-lista")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerClientesEnListaNegra(@HeaderParam("Authorization") String token) {
        try {
            if (!jwtHelper.validateToken2(token)) {
                return Response.status(Response.Status.UNAUTHORIZED)
                        .entity("{\"message\":\"Token inválido\"}")
                        .build();
            }

            String cleanedToken = jwtHelper.cleanToken(token);
            String rol = jwtHelper.getRolFromToken(cleanedToken);

            if (!"Administrador".equals(rol)) {
                return Response.status(Response.Status.FORBIDDEN)
                        .entity("{\"message\":\"Acceso denegado\"}")
                        .build();
            }

            List<ListaNegra> listaNegra = gestionListaNegraDB.obtenerClientesEnListaNegra();
            if (listaNegra.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"message\":\"No hay clientes en la lista negra.\"}")
                        .build();
            }

            return Response.ok(listaNegra).build();

        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"message\":\"Ocurrió un error interno.\"}")
                    .build();
        }
    }

}
