/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllers;

import BackendDB.CancelarCitaDB;
import Modelos.JWTHelper;
import com.auth0.jwt.exceptions.JWTVerificationException;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/**
 *
 * @author herson
 */
@Path("/cancelarCita")
public class CancelarCitaController {
   
    private final JWTHelper jwtHelper = new JWTHelper(); 
    private final CancelarCitaDB cancelarCitaDB = new CancelarCitaDB(); 

    @POST
    @Path("/cancelar/{idCita}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response cancelarCita(@HeaderParam("Authorization") String token, @PathParam("idCita") int idCita) {
        try {
            // Validar el token y obtener el ID del usuario
            int idCliente = jwtHelper.validateAndGetId(token);

            // Cancelar la cita
            boolean resultado = cancelarCitaDB.cancelarCita(idCita);

            if (resultado) {
                return Response.ok("{\"message\": \"Cita cancelada exitosamente.\"}")
                               .type(MediaType.APPLICATION_JSON)
                               .build();
            } else {
                return Response.status(Response.Status.BAD_REQUEST)
                               .entity("{\"message\": \"No se pudo cancelar la cita. Verifica que el ID de la cita sea correcto.\"}")
                               .type(MediaType.APPLICATION_JSON)
                               .build();
            }
        } catch (JWTVerificationException e) {
            // Token inválido o vencido
            return Response.status(Response.Status.UNAUTHORIZED)
                           .entity("{\"message\": \"Token inválido o vencido.\"}")
                           .type(MediaType.APPLICATION_JSON)
                           .build();
        } catch (Exception e) {
            // Error general
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("{\"message\": \"Error al cancelar la cita: " + e.getMessage() + "\"}")
                           .type(MediaType.APPLICATION_JSON)
                           .build();
        }
    }
}
