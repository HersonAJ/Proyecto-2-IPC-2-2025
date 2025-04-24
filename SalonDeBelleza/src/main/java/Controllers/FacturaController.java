/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllers;

import BackendDB.FacturaDB;
import BackendDB.GestionCitaEmpleadoDB;
import Modelos.Cita;
import Modelos.Factura;
import Modelos.JWTHelper;
import com.auth0.jwt.exceptions.JWTVerificationException;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.POST;
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
@Path("/facturas")
public class FacturaController {

    private final FacturaDB facturaDB = new FacturaDB();
    private final GestionCitaEmpleadoDB gestionCitaEmpleadoDB = new GestionCitaEmpleadoDB();
    private final JWTHelper jwtHelper = new JWTHelper();

    @POST
    @Path("/crear/{idCita}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response crearFactura(@HeaderParam("Authorization") String token, @PathParam("idCita") int idCita) {
        try {
            int idEmpleado = jwtHelper.validateAndGetId(token); // Validar el token y obtener el ID del empleado

            // Obtener la cita para validar el estado y generar la factura
            Cita cita = gestionCitaEmpleadoDB.obtenerCitaPorId(idCita);
            if (cita == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"message\": \"La cita no existe.\"}")
                        .type(MediaType.APPLICATION_JSON)
                        .build();
            }

            if (!cita.getEstado().equalsIgnoreCase("Atendida")) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("{\"message\": \"La factura solo puede generarse para citas en estado 'Atendida'.\"}")
                        .type(MediaType.APPLICATION_JSON)
                        .build();
            }

            // Construir descripción detallada de la factura
            String detallesFactura = "Servicio: " + cita.getNombreServicio()
                    + ", Duración: " + cita.getDuracionServicio() + " minutos"
                    + ", Precio: Q" + String.format("%.2f", cita.getPrecioServicio());

            // Crear el objeto Factura
            Factura nuevaFactura = new Factura(
                    0, // ID_Factura será autogenerado por la base de datos
                    cita.getIdCita(),
                    cita.getIdCliente(),
                    idEmpleado,
                    cita.getIdServicio(),
                    cita.getPrecioServicio(),
                    cita.getFechaCita(),
                    detallesFactura
            );

            // Insertar la factura en la base de datos
            boolean resultado = facturaDB.crearFactura(nuevaFactura);

            if (resultado) {
                return Response.status(Response.Status.CREATED)
                        .entity("{\"message\": \"Factura creada exitosamente.\"}")
                        .type(MediaType.APPLICATION_JSON)
                        .build();
            } else {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity("{\"message\": \"Error al crear la factura.\"}")
                        .type(MediaType.APPLICATION_JSON)
                        .build();
            }

        } catch (JWTVerificationException e) {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity("{\"message\": \"Token inválido o vencido.\"}")
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"message\": \"Error al procesar la solicitud: " + e.getMessage() + "\"}")
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }
    }

    @GET
    @Path("/cliente/{idCliente}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerFacturasPorCliente(@PathParam("idCliente") int idCliente) {
        try {
            List<Factura> facturas = facturaDB.obtenerFacturasPorCliente(idCliente);
            if (facturas.isEmpty()) {
                return Response.status(Response.Status.NO_CONTENT)
                        .entity("{\"message\": \"No hay facturas disponibles para este cliente.\"}")
                        .build();
            }
            return Response.ok(facturas).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"message\": \"Error al obtener las facturas: " + e.getMessage() + "\"}")
                    .build();
        }
    }
}
