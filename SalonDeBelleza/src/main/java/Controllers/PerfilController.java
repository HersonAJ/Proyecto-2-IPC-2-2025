/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllers;

import BackendDB.MiPerfilDB;
import Modelos.JWTHelper;
import Modelos.Usuario;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.Base64;

/**
 *
 * @author herson
 */
@Path("perfil")
public class PerfilController {


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerPerfil(@HeaderParam("Authorization") String authHeader) {
        try {
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return Response.status(Response.Status.UNAUTHORIZED)
                        .entity("{\"mensaje\": \"Token no proporcionado\"}")
                        .build();
            }
            String token = authHeader.substring("Bearer ".length());

            JWTHelper jwtHelper = new JWTHelper();
            if (!jwtHelper.validateToken(token)) {
                return Response.status(Response.Status.UNAUTHORIZED)
                        .entity("{\"mensaje\": \"Token no válido\"}")
                        .build();
            }
            String correo = jwtHelper.getCorreoFromToken(token);

            Usuario usuario = MiPerfilDB.getPerfilByCorreo(correo);
            if (usuario == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"mensaje\": \"Usuario no encontrado\"}")
                        .build();
            }
            
            String fotoBase64 = "";
            if (usuario.getFotoPerfil() != null) {
                fotoBase64 = Base64.getEncoder().encodeToString(usuario.getFotoPerfil());
            }

            String jsonResponse = String.format(
                "{\"idUsuario\": %d, \"nombre\": \"%s\", \"dpi\": \"%s\", \"telefono\": \"%s\", " +
                "\"direccion\": \"%s\", \"correo\": \"%s\", \"descripcion\": \"%s\", \"rol\": \"%s\", " +
                "\"estado\": \"%s\", \"hobbies\": \"%s\", \"fotoPerfil\": \"%s\"}",
                usuario.getIdUsuario(),
                usuario.getNombre(),
                usuario.getDpi(),
                usuario.getTelefono(),
                usuario.getDireccion(),
                usuario.getCorreo(),
                usuario.getDescripcion(),
                usuario.getRol(),
                usuario.getEstado(),
                usuario.getHobbies() != null ? usuario.getHobbies() : "",
                fotoBase64
            );
            return Response.ok(jsonResponse).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"mensaje\": \"Error en el servidor\"}")
                    .build();
        }
    }
}


