/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllers;

import BackendDB.GestionServiciosDB;
import Modelos.JWTHelper;
import Modelos.Servicio;
import Modelos.Usuario;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author herson
 */
@Path("/servicios")
public class GestionServiciosController {

    private final GestionServiciosDB gestionServiciosDB = new GestionServiciosDB();
    private final JWTHelper jwtHelper = new JWTHelper();

    @POST
    @Path("/crear")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response crearServicio(Map<String, Object> requestBody, @HeaderParam("Authorization") String authToken) {
        try {
            // Extraer el servicio y la lista de empleados del cuerpo de la solicitud
            Servicio servicio = new Servicio();
            servicio.setNombreServicio((String) requestBody.get("nombreServicio"));
            servicio.setDescripcion((String) requestBody.get("descripcion"));
            servicio.setDuracion((Integer) requestBody.get("duracion"));
            servicio.setPrecio(Double.valueOf(requestBody.get("precio").toString()));
            servicio.setEstado((String) requestBody.get("estado"));
            servicio.setImagen(Base64.getDecoder().decode((String) requestBody.get("imagen")));

            List<Integer> empleadosIds = (List<Integer>) requestBody.get("empleados");

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
            servicio.setIdEncargado(idEncargado);

            // Crear el servicio en la base de datos
            boolean creado = gestionServiciosDB.crearServicio(servicio, empleadosIds);
            if (creado) {
                return Response.status(Response.Status.CREATED)
                        .entity("{\"message\": \"Servicio creado exitosamente.\"}")
                        .build();
            } else {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("{\"message\": \"No se pudo crear el servicio.\"}")
                        .build();
            }
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
            List<Servicio> servicios = gestionServiciosDB.obtenerServicios();

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
            List<Usuario> empleados = gestionServiciosDB.obtenerEmpleados();

            return Response.ok(empleados).build();

        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"message\": \"Ocurrió un error al obtener los empleados.\"}")
                    .build();
        }
    }

}
