/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllers.ReportesAdmin;

import BackendDB.ReportesAdmin.ReporteClientesGastoDB;
import Modelos.Admin.ReporteClientesGastos;
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
public class ReporteClientesGastoController {

    private final ReporteClientesGastoDB reporteDB = new ReporteClientesGastoDB();

    @GET
    @Path("/clientes-mas-gastadores")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerClientesMasGastadores(@QueryParam("fechaInicio") Date fechaInicio,
                                                 @QueryParam("fechaFin") Date fechaFin) {
        try {
            List<ReporteClientesGastos> reportes = reporteDB.obtenerClientesMasGastadores(fechaInicio, fechaFin);

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

    @GET
    @Path("/clientes-menos-gasto")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerClientesConMenosGasto(@QueryParam("fechaInicio") Date fechaInicio,
                                                 @QueryParam("fechaFin") Date fechaFin) {
        try {
            List<ReporteClientesGastos> reportes = reporteDB.obtenerClientesConMenosGasto(fechaInicio, fechaFin);

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

