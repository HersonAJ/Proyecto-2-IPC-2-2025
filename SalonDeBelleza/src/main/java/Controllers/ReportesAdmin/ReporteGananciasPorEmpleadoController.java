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
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

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

    @GET
    @Path("/ganancias-por-empleado/pdf")
    @Produces("application/pdf")
    public Response generarReporteGananciasPorEmpleadoPDF(@QueryParam("fechaInicio") Date fechaInicio,
            @QueryParam("fechaFin") Date fechaFin,
            @QueryParam("idEmpleado") Integer idEmpleado) {
        try {
          
            List<ReporteGananciaEmpleado> reportes = reporteDB.obtenerGananciasPorEmpleado(fechaInicio, fechaFin, idEmpleado);

            if (reportes.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"message\":\"No se encontraron registros para el reporte solicitado.\"}")
                        .build();
            }

            InputStream jasperStream = getClass().getResourceAsStream("/reports/reporte_ganancias_por_empleado.jasper");
            if (jasperStream == null) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity("{\"message\":\"No se encontró el archivo reporte_ganancias_por_empleado.jasper.\"}")
                        .build();
            }

            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(reportes);

            Map<String, Object> parametros = new HashMap<>();

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperStream, parametros, dataSource);

            // Exportar el reporte a PDF
            ByteArrayOutputStream pdfOutput = new ByteArrayOutputStream();
            JasperExportManager.exportReportToPdfStream(jasperPrint, pdfOutput);

            return Response.ok(pdfOutput.toByteArray())
                    .header("Content-Disposition", "attachment; filename=reporte_ganancias_por_empleado.pdf")
                    .build();

        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"message\":\"Ocurrió un error al generar el reporte.\"}")
                    .build();
        }
    }

}
