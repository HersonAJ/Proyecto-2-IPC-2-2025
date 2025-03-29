/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllers;

import BackendDB.LoginDB;
import Modelos.Codificador;
import Modelos.JWTHelper;
import Modelos.Usuario;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/**
 * @author herson
 */
@Path("login")
public class LoginController {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(LoginRequest loginRequest) {
        try {
            // Instanciar codificador y codificar la contraseña recibida
            Codificador codificador = new Codificador();
            String contrasenaCodificada = codificador.codificar(loginRequest.getContrasena());

            // Autenticar el usuario y obtener el objeto Usuario (si existe)
            Usuario usuario = LoginDB.autenticarUsuario(loginRequest.getCorreo(), contrasenaCodificada);

            if (usuario != null) {
                // Generar el token JWT usando toda la información que retorna el objeto usuario
                JWTHelper jwtHelper = new JWTHelper();
                String token = jwtHelper.generateToken(usuario.getCorreo(), usuario.getRol(), usuario.getNombre());

                // Generar la respuesta JSON con token y datos relevantes del usuario
                String jsonResponse = String.format(
                        "{\"mensaje\": \"Usuario válido\", \"token\": \"%s\", \"usuario\": {\"id\": %d, \"correo\": \"%s\", \"nombre\": \"%s\", \"rol\": \"%s\"}}",
                        token, usuario.getIdUsuario(), usuario.getCorreo(), usuario.getNombre(), usuario.getRol()
                );
                return Response.ok(jsonResponse).build();
            } else {
                return Response.status(Response.Status.UNAUTHORIZED)
                        .entity("{\"mensaje\": \"Credenciales inválidas\"}")
                        .build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"mensaje\": \"Error en el servidor\"}")
                    .build();
        }
    }

    public static class LoginRequest {

        private String correo;
        private String contrasena;

        public String getCorreo() {
            return correo;
        }

        public void setCorreo(String correo) {
            this.correo = correo;
        }

        public String getContrasena() {
            return contrasena;
        }

        public void setContrasena(String contrasena) {
            this.contrasena = contrasena;
        }
    }
}
