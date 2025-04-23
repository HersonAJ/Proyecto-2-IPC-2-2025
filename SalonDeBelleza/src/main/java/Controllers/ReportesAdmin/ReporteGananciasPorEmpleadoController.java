/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllers.ReportesAdmin;
import BackendDB.ReportesAdmin.ReporteGananciasPorEmpleadoDB;
import Modelos.Admin.ReporteGananciaEmpleado;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.sql.Date;
import java.util.List;

/**
 *
 * @author herson
 */
@Path("/admin/reportes")
public class ReporteGananciasPorEmpleadoController {

    private final ReporteGananciasPorEmpleadoDB reporteDB = new ReporteGananciasPorEmpleadoDB();


    @GET
    @Path("/empleados-activos")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerEmpleadosActivos() {
        try {
            List<ReporteGananciaEmpleado> empleados = reporteDB.obtenerEmpleadosActivos();

            // Verificar si no hay resultados
            if (empleados.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"message\":\"No se encontraron empleados activos.\"}")
                        .build();
            }

            return Response.ok(empleados).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"message\":\"Ocurrió un error al obtener la lista de empleados.\"}")
                    .build();
        }
    }


    @GET
    @Path("/ganancias-por-empleado")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerGananciasPorEmpleado(@QueryParam("fechaInicio") Date fechaInicio,
                                                 @QueryParam("fechaFin") Date fechaFin,
                                                 @QueryParam("idEmpleado") Integer idEmpleado) {
        try {
            List<ReporteGananciaEmpleado> reportes = reporteDB.obtenerGananciasPorEmpleado(fechaInicio, fechaFin, idEmpleado);

            // Verificar si no hay resultados
            if (reportes.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"message\":\"No se encontraron registros para el reporte solicitado.\"}")
                        .build();
            }

            return Response.ok(reportes).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"message\":\"Ocurrió un error al obtener el reporte.\"}")
                    .build();
        }
    }
}

