/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllers;

import BackendDB.RegistroDB;
import Modelos.Codificador;
import Modelos.Usuario;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.io.IOException;
import java.io.InputStream;
import org.glassfish.jersey.media.multipart.FormDataParam;

/**
 *
 * @author herson
 */

@Path("usuarios")
public class UsuarioController {

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public Response registrarUsuario(
        @FormDataParam("nombre") String nombre,
        @FormDataParam("correo") String correo,
        @FormDataParam("contrasena") String contrasena,
        @FormDataParam("telefono") String telefono,
        @FormDataParam("direccion") String direccion,
        @FormDataParam("dpi") String dpi,
        @FormDataParam("rol") String rol,
        @FormDataParam("estado") String estado,
        @FormDataParam("Foto_Perfil") InputStream fotoPerfil,
        @FormDataParam("Hobbies") String hobbies,
        @FormDataParam("Descripcion") String descripcion
    ) {
        try {
            byte[] fotoBytes = null;
            if (fotoPerfil != null) {
                try {
                    fotoBytes = fotoPerfil.readAllBytes();
                } catch (IOException e) {
                    e.printStackTrace();
                    return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                                   .entity("{\"mensaje\": \"Error al procesar la foto de perfil\"}")
                                   .build();
                }
            }
            
            Codificador codificador = new Codificador();
            String contrasenaCodificada = codificador.codificar(contrasena);
            
            Usuario usuario = new Usuario();
            usuario.setNombre(nombre);
            usuario.setCorreo(correo);
            usuario.setContrasena(contrasenaCodificada);
            usuario.setTelefono(telefono);
            usuario.setDireccion(direccion);
            usuario.setDpi(dpi);
            usuario.setRol(rol);
            usuario.setEstado(estado);
            usuario.setFotoPerfil(fotoBytes);
            usuario.setHobbies(hobbies);          
            usuario.setDescripcion(descripcion);
            
            boolean exito = RegistroDB.insertarUsuario(usuario);

            if (exito) {
                return Response.ok("{\"mensaje\": \"Usuario registrado exitosamente\"}").build();
            } else {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                               .entity("{\"mensaje\": \"Error al registrar usuario\"}")
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

