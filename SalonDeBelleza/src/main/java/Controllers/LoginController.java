/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllers;

import BackendDB.LoginDB;
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
        // Valida si el usuario existe en la base de datos
        boolean usuarioValido = LoginDB.validarUsuario(loginRequest.getCorreo(), loginRequest.getContrasena());

        if (usuarioValido) {
            return Response.ok("{\"mensaje\": \"Usuario válido\"}").build();
        } else {
            return Response.status(Response.Status.UNAUTHORIZED)
                           .entity("{\"mensaje\": \"Credenciales inválidas\"}")
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
