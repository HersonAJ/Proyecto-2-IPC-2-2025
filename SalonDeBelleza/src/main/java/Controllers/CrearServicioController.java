/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllers;

import BackendDB.CrearServicioDB;
import Modelos.JWTHelper;
import Modelos.Servicio;
import Modelos.Usuario;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.glassfish.jersey.media.multipart.FormDataParam;

/**
 *
 * @author herson
 */
@Path("/serviciosNuevos")
public class CrearServicioController {

    private final CrearServicioDB crearServicioDB = new CrearServicioDB();
    private final JWTHelper jwtHelper = new JWTHelper();

    @POST
    @Path("/crear")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public Response crearServicio(
            @FormDataParam("nombreServicio") String nombreServicio,
            @FormDataParam("descripcion") String descripcion,
            @FormDataParam("duracion") int duracion,
            @FormDataParam("precio") double precio,
            @FormDataParam("estado") String estado,
            @FormDataParam("imagen") InputStream imagenInputStream,
            @FormDataParam("catalogoPdf") InputStream catalogoPdfInputStream,
            @FormDataParam("empleados") String empleadosJson,
            @HeaderParam("Authorization") String authToken) {
        try {
            // Validar el token JWT
            if (authToken == null || !jwtHelper.validateToken(authToken.replace("Bearer ", ""))) {
                return Response.status(Response.Status.UNAUTHORIZED)
                        .entity("{\"message\": \"Token inválido o no proporcionado.\"}")
                        .build();
            }

            // Validar si el rol del usuario es 'Servicios'
            String rol = jwtHelper.getRolFromToken(authToken.replace("Bearer ", ""));
            if (!"Servicios".equals(rol)) {
                return Response.status(Response.Status.FORBIDDEN)
                        .entity("{\"message\": \"Acceso denegado: no tiene permisos suficientes.\"}")
                        .build();
            }

            // Extraer el ID del usuario desde el token y asignarlo al servicio
            int idEncargado = jwtHelper.getIdUsuarioFromToken(authToken.replace("Bearer ", ""));

            // Crear el objeto Servicio y asignar los datos principales
            Servicio servicio = new Servicio();
            servicio.setNombreServicio(nombreServicio);
            servicio.setDescripcion(descripcion);
            servicio.setDuracion(duracion);
            servicio.setPrecio(precio);
            servicio.setEstado(estado);
            servicio.setIdEncargado(idEncargado);

            // Leer la imagen y el PDF como bytes
            byte[] imagenBytes = imagenInputStream != null ? imagenInputStream.readAllBytes() : null;
            byte[] catalogoPdfBytes = catalogoPdfInputStream != null ? catalogoPdfInputStream.readAllBytes() : null;

            servicio.setImagen(imagenBytes); 
            servicio.setCatalogoPdf(catalogoPdfBytes); 

            // Convertir el JSON de empleados a una lista de IDs
            ObjectMapper objectMapper = new ObjectMapper();
            List<Integer> empleadosIds = objectMapper.readValue(empleadosJson, new TypeReference<List<Integer>>() {
            });

            // Crear el servicio en la base de datos
            boolean creado = crearServicioDB.crearServicio(servicio, empleadosIds);
            if (creado) {
                return Response.status(Response.Status.CREATED)
                        .entity("{\"message\": \"Servicio creado exitosamente.\"}")
                        .build();
            } else {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("{\"message\": \"No se pudo crear el servicio.\"}")
                        .build();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"message\": \"Error al procesar los archivos (imagen o PDF).\"}")
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"message\": \"Ocurrió un error al crear el servicio.\"}")
                    .build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerServicios() {
        try {
            List<Servicio> servicios = crearServicioDB.obtenerServicios();

            // Convertir imágenes de byte[] a Base64 y enviarlas como parte de la respuesta
            List<Map<String, Object>> serviciosConImagenes = new ArrayList<>();
            for (Servicio servicio : servicios) {
                Map<String, Object> servicioMap = new HashMap<>();
                servicioMap.put("idServicio", servicio.getIdServicio());
                servicioMap.put("nombreServicio", servicio.getNombreServicio());
                servicioMap.put("descripcion", servicio.getDescripcion());
                servicioMap.put("duracion", servicio.getDuracion());
                servicioMap.put("precio", servicio.getPrecio());
                servicioMap.put("estado", servicio.getEstado());
                servicioMap.put("idEncargado", servicio.getIdEncargado());

                // Convertir imagen
                if (servicio.getImagen() != null) {
                    String imagenBase64 = "data:image/jpeg;base64," + Base64.getEncoder().encodeToString(servicio.getImagen());
                    servicioMap.put("imagen", imagenBase64); // Enviar como String
                } else {
                    servicioMap.put("imagen", null);
                }

                serviciosConImagenes.add(servicioMap);
            }

            return Response.ok(serviciosConImagenes).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"message\": \"Ocurrió un error al obtener los servicios.\"}")
                    .build();
        }
    }

    @GET
    @Path("/empleados")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerEmpleados(@HeaderParam("Authorization") String authToken) {
        try {
            // Validar el token JWT
            if (authToken == null || !jwtHelper.validateToken(authToken.replace("Bearer ", ""))) {
                return Response.status(Response.Status.UNAUTHORIZED)
                        .entity("{\"message\": \"Token inválido o no proporcionado.\"}")
                        .build();
            }

            // Validar si el rol del usuario es 'Servicios' (solo ellos pueden obtener la lista de empleados)
            String rol = jwtHelper.getRolFromToken(authToken.replace("Bearer ", ""));
            if (!"Servicios".equals(rol)) {
                return Response.status(Response.Status.FORBIDDEN)
                        .entity("{\"message\": \"Acceso denegado: no tiene permisos suficientes.\"}")
                        .build();
            }

            // Obtener la lista de empleados
            List<Usuario> empleados = crearServicioDB.obtenerEmpleados();

            return Response.ok(empleados).build();

        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"message\": \"Ocurrió un error al obtener los empleados.\"}")
                    .build();
        }
    }

}
