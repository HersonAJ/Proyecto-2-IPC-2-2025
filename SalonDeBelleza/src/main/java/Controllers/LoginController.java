/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllers;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/**
 *
 * @author herson
 */
@Path("login")
public class LoginController {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(LoginRequest loginRequest) {
        // Validación simple: se compara con valores fijos
        if ("algo@gmail.com".equals(loginRequest.getCorreo()) && "123".equals(loginRequest.getContrasena())) {
            return Response.ok("{\"mensaje\": \"bienvenido\"}").build();
        } else {
            return Response.status(Response.Status.UNAUTHORIZED)
                           .entity("{\"mensaje\": \"Credenciales no validas\"}")
                           .build();
        }
    }
    
    // Clase interna para representar la solicitud de login
    public static class LoginRequest {
        private String correo;
        private String contrasena;

        // Constructor vacío obligatorio para la deserialización
        public LoginRequest() {
        }

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
