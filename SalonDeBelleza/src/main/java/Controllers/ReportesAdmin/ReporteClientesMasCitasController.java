/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllers.ReportesAdmin;

import BackendDB.ReportesAdmin.ReporteClienteMasCitasDB;
import Modelos.Admin.ReporteClientes;
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
public class ReporteClientesMasCitasController {

    private final ReporteClienteMasCitasDB reporteDB = new ReporteClienteMasCitasDB();

    @GET
    @Path("/clientes-mas-citas-atendidas")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerClientesConMasCitasAtendidas(@QueryParam("fechaInicio") Date fechaInicio,
                                                        @QueryParam("fechaFin") Date fechaFin,
                                                        @QueryParam("idEmpleado") Integer idEmpleado) {
        try {
            List<ReporteClientes> reportes = reporteDB.obtenerClientesConMasCitasAtendidas(fechaInicio, fechaFin, idEmpleado);

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

