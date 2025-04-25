/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllers;

import BackendDB.GestionServicioDB;
import Modelos.Servicio;
import Modelos.Usuario;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.glassfish.jersey.media.multipart.FormDataParam;

/**
 *
 * @author herson
 */
@Path("/servicios")
public class GestionServicioController {

    private final GestionServicioDB gestionServicioDB = new GestionServicioDB();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerTodosLosServicios() {
        try {
            List<Servicio> servicios = gestionServicioDB.obtenerTodosLosServicios();

            if (servicios.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"message\": \"No se encontraron servicios disponibles.\"}")
                        .build();
            } else {
                return Response.ok(servicios).build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"message\": \"Ocurrió un error al obtener los servicios.\"}")
                    .build();
        }
    }

    @PUT
    @Path("/{idServicio}")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public Response gestionarServicio(
            @PathParam("idServicio") int idServicio,
            @FormDataParam("servicio") String servicioJson,
            @FormDataParam("nuevaImagen") InputStream nuevaImagenInputStream,
            @FormDataParam("catalogoPdf") InputStream catalogoPdfInputStream,
            @FormDataParam("nuevoEstado") String nuevoEstado,
            @FormDataParam("empleadosIds") String empleadosIdsJson) {

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Servicio servicio = servicioJson != null
                    ? objectMapper.readValue(servicioJson, Servicio.class)
                    : null;

            byte[] nuevaImagen = nuevaImagenInputStream != null
                    ? nuevaImagenInputStream.readAllBytes()
                    : null;

            byte[] catalogoPdf = catalogoPdfInputStream != null
                    ? catalogoPdfInputStream.readAllBytes()
                    : null;

            List<Integer> empleadosIds = empleadosIdsJson != null
                    ? objectMapper.readValue(empleadosIdsJson, new TypeReference<List<Integer>>() {
                    })
                    : null;

            boolean exito = gestionServicioDB.gestionarServicio(idServicio, servicio, nuevaImagen, catalogoPdf, nuevoEstado, empleadosIds);

            if (exito) {
                return Response.ok("{\"message\": \"Servicio actualizado con éxito.\"}").build();
            } else {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("{\"message\": \"No se pudo actualizar el servicio.\"}")
                        .build();
            }
        } catch (IOException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"message\": \"Ocurrió un error interno al gestionar el servicio.\"}")
                    .build();
        }
    }

    @GET
    @Path("/{idServicio}/empleados")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerEmpleadosPorServicio(@PathParam("idServicio") int idServicio) {
        try {
            List<Usuario> empleados = gestionServicioDB.obtenerEmpleadosPorServicio(idServicio);

            if (empleados.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"message\": \"No se encontraron empleados asignados para este servicio.\"}")
                        .build();
            } else {
                return Response.ok(empleados).build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"message\": \"Ocurrió un error interno al obtener los empleados.\"}")
                    .build();
        }
    }

    @GET
    @Path("/{idServicio}/empleados/estado")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerEmpleadosPorEstado(@PathParam("idServicio") int idServicio) {
        try {
            Map<String, List<Usuario>> empleadosPorEstado = new HashMap<>();

            List<Usuario> empleadosAsignados = gestionServicioDB.obtenerEmpleadosPorServicio(idServicio);
            empleadosPorEstado.put("asignados", empleadosAsignados);

            List<Usuario> empleadosNoAsignados = gestionServicioDB.obtenerEmpleadosNoAsignados(idServicio);
            empleadosPorEstado.put("no_asignados", empleadosNoAsignados);

            return Response.ok(empleadosPorEstado).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"message\": \"Ocurrió un error al obtener la información de los empleados.\"}")
                    .build();
        }
    }
}
