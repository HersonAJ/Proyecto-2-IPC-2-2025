/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllers;

import BackendDB.EditarPerfilDB;
import Modelos.Codificador;
import Modelos.JWTHelper;
import Modelos.Usuario;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/**
 *
 * @author herson
 */

/*
@Path("editar-perfil")
public class EditarPerfilController {
    
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response actualizarPerfil(@HeaderParam("Authorization") String authHeader, Usuario usuario) {
        try {
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return Response.status(Response.Status.UNAUTHORIZED)
                        .entity("{\"mensaje\": \"Token no proporcionado o invalido\"}")
                        .build();
            }
            
            String token = authHeader.substring("Bearer ".length());
            JWTHelper jwtHelper = new JWTHelper();
            if (!jwtHelper.validateToken(token)) {
                return Response.status(Response.Status.UNAUTHORIZED)
                        .entity("{\"mensaje\": \"Token no valido\"}")
                        .build();
            }
            
            int idUsuario = jwtHelper.getIdUsuarioFromToken(token);
            
            if( usuario.getContrasena() != null && !usuario.getContrasena().trim().isEmpty()) {
                Codificador codificador = new Codificador();
                usuario.setContrasena(codificador.codificar(usuario.getContrasena()));
            }
            
            boolean actualizado = EditarPerfilDB.actualizarPerfil(idUsuario, usuario);
            
            if (actualizado) {
                return Response.ok("{\"mensaje\": \"Perfil actualizado correctamente\"}").build();
            } else {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("{\"mensaje\": \"Error al actualizar perfil\"}")
                        .build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"mensaje\": \"Error en el servidor\"}")
                    .build();
        }
    }
    
}
*/

@Path("editar-perfil")
public class EditarPerfilController {
    
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response actualizarPerfil(@HeaderParam("Authorization") String authHeader, Usuario usuario) {
        try {
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return Response.status(Response.Status.UNAUTHORIZED)
                        .entity("{\"mensaje\": \"Token no proporcionado o inválido\"}")
                        .build();
            }
            
            String token = authHeader.substring("Bearer ".length());
            JWTHelper jwtHelper = new JWTHelper();
            if (!jwtHelper.validateToken(token)) {
                return Response.status(Response.Status.UNAUTHORIZED)
                        .entity("{\"mensaje\": \"Token no válido\"}")
                        .build();
            }
            
            int idUsuario = jwtHelper.getIdUsuarioFromToken(token);
            
            // Si la contraseña nueva está vacía o es nula, obtener la contraseña actual
            if (usuario.getContrasena() == null || usuario.getContrasena().trim().isEmpty()) {
                String contrasenaActual = EditarPerfilDB.obtenerContrasenaById(idUsuario);
                usuario.setContrasena(contrasenaActual);
            } else {
                // Si se ingresa una nueva contraseña, codificarla
                Codificador codificador = new Codificador();
                usuario.setContrasena(codificador.codificar(usuario.getContrasena()));
            }
            
            boolean actualizado = EditarPerfilDB.actualizarPerfil(idUsuario, usuario);
            
            if (actualizado) {
                return Response.ok("{\"mensaje\": \"Perfil actualizado correctamente\"}").build();
            } else {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("{\"mensaje\": \"Error al actualizar perfil\"}")
                        .build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"mensaje\": \"Error en el servidor\"}")
                    .build();
        }
    }
}
