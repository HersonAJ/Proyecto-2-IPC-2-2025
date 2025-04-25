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
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.HashMap;
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

            return Response.ok(reportes).build();
        } catch (Exception e) {
            e.printStackTrace(); 
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

            return Response.ok(servicios).build();
        } catch (Exception e) {
            e.printStackTrace();

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"message\":\"Ocurrió un error al obtener el listado de servicios.\"}")
                    .build();
        }
    }

    @GET
    @Path("/ganancias-servicio/pdf")
    @Produces("application/pdf")
    public Response generarReporteGananciasPDF(
            @QueryParam("fechaInicio") Date fechaInicio,
            @QueryParam("fechaFin") Date fechaFin,
            @QueryParam("idServicio") Integer idServicio) {
        try {
            // Obtener datos para el reporte
            List<ReporteAdmin> reportes = reporteGananciasDB.obtenerGananciasPorServicio(fechaInicio, fechaFin, idServicio);

            if (reportes.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"message\":\"No se encontraron registros para el reporte solicitado.\"}")
                        .build();
            }

            InputStream jasperStream = getClass().getResourceAsStream("/reports/reporte_ganancias_por_servicio.jasper");
            if (jasperStream == null) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity("{\"message\":\"No se encontró el archivo .jasper.\"}")
                        .build();
            }

            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(reportes);

            Map<String, Object> parametros = new HashMap<>();
            parametros.put("titulo", "Reporte de Ganancias por Servicio");

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperStream, parametros, dataSource);

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            JasperExportManager.exportReportToPdfStream(jasperPrint, out);

            return Response.ok(out.toByteArray())
                    .header("Content-Disposition", "attachment; filename=reporte_ganancias.pdf")
                    .build();

        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"message\":\"Ocurrió un error al generar el reporte.\"}")
                    .build();
        }
    }

}
