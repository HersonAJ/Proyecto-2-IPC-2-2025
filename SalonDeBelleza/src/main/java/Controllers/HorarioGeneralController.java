/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllers;

import BackendDB.HorarioGeneralDB;
import Modelos.HorarioGeneral;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

/**
 *
 * @author herson
 */
@Path("/horarios")
public class HorarioGeneralController {
    
    private final HorarioGeneralDB horarioGeneralDB = new HorarioGeneralDB();
    
    //endpoint para obtener el horario general
    @GET
    @Path("/general")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerHorarioGeneral() {
        try {
            List<HorarioGeneral> horarios = horarioGeneralDB.obtenerHorarioGeneral();
            
            if (horarios.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"message\": \"No se encontro el horario general.\"}")
                        .build();
            } else {
                return Response.ok(horarios).build();
            }
        }catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"message\": \"Ocurrio un error interno al obtener le horario general. \"}")
                    .build();
        }
    }
    
    //endopoint para establecer o actualizar el horario generao
    @PUT
    @Path("/general")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response establecerHorarioGeneral(List<HorarioGeneral> horarios) {
        try {
            boolean exito = horarioGeneralDB.crearHorarioGeneral(horarios);

            if (exito) {
                return Response.ok("{\"message\": \"Horario general establecido con éxito.\"}").build();
            } else {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("{\"message\": \"No se pudo establecer el horario general.\"}")
                        .build();
            }

        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"message\": \"Ocurrió un error interno al establecer el horario general.\"}")
                    .build();
        }
    }
}
