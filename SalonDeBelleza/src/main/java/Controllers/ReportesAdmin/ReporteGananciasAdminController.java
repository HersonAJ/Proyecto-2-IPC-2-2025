/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllers.ReportesAdmin;
import BackendDB.ReportesAdmin.ReporteGananciasDB;
import Modelos.ReporteAdmin;
import java.sql.Date;
import java.util.List;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
/**
 *
 * @author herson
 */
@Path("/admin/reportes")
public class ReporteGananciasAdminController {
    
    private final ReporteGananciasDB reporteGananciasDB = new ReporteGananciasDB();

    @GET
    @Path("/ganancias-servicio")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerReporteGanancias(
            @QueryParam("fechaInicio") Date fechaInicio,
            @QueryParam("fechaFin") Date fechaFin,
            @QueryParam("idServicio") Integer idServicio) {
        try {
            // Obtener el reporte de ganancias
            List<ReporteAdmin> reportes = reporteGananciasDB.obtenerGananciasPorServicio(fechaInicio, fechaFin, idServicio);

            // Verificar si hay resultados
            if (reportes.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"message\":\"No se encontraron registros para el reporte solicitado.\"}")
                        .build();
            }

            // Retornar el reporte en formato JSON
            return Response.ok(reportes).build();
        } catch (Exception e) {
            e.printStackTrace(); // Log del error
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"message\":\"Ocurrió un error al generar el reporte.\"}")
                    .build();
        }
    }
        @GET
    @Path("/listado")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerListadoServicios() {
        try {
            // Obtener el listado de servicios desde la base de datos
            List<ServicioParaReportes> servicios = reporteGananciasDB.obtenerListadoServicios();

            // Retornar la respuesta con el listado
            return Response.ok(servicios).build();
        } catch (Exception e) {
            e.printStackTrace();

            // Retornar una respuesta de error si algo falla
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"message\":\"Ocurrió un error al obtener el listado de servicios.\"}")
                    .build();
        }
    }
}

