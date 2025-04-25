/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllers.ReportesAdmin;

import BackendDB.ReportesAdmin.ReporteAnunciosDB;
import Modelos.ReporteAdmin;
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
public class ReporteAnunciosController {

    private final ReporteAnunciosDB reporteDB = new ReporteAnunciosDB();

    @GET
    @Path("/anuncios-mas-mostrados")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerAnunciosMasMostrados(@QueryParam("fechaInicio") Date fechaInicio,
            @QueryParam("fechaFin") Date fechaFin) {
        try {
            // Llamar al método para obtener los anuncios más mostrados
            List<ReporteAdmin> reportes = reporteDB.obtenerAnunciosMasMostrados(fechaInicio, fechaFin);

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
                    .entity("{\"message\":\"Ocurrió un error al obtener el reporte de anuncios más mostrados.\"}")
                    .build();
        }
    }

    @GET
    @Path("/anuncios-mas-mostrados/pdf")
    @Produces("application/pdf")
    public Response descargarReporteAnunciosMasMostrados(@QueryParam("fechaInicio") Date fechaInicio,
            @QueryParam("fechaFin") Date fechaFin) {
        try {
            // Obtener los datos para el reporte
            List<ReporteAdmin> reportes = reporteDB.obtenerAnunciosMasMostrados(fechaInicio, fechaFin);

            if (reportes.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"message\":\"No se encontraron registros para el reporte solicitado.\"}")
                        .build();
            }

            InputStream jasperStream = getClass().getResourceAsStream("/reports/reporte_5_anuncios_mas_mostrados.jasper");
            if (jasperStream == null) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity("{\"message\":\"No se encontró el archivo reporte_5_anuncios_mas_mostrados.jasper.\"}")
                        .build();
            }

            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(reportes);

            Map<String, Object> parametros = new HashMap<>();
            parametros.put("fechaInicio", fechaInicio);
            parametros.put("fechaFin", fechaFin);

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperStream, parametros, dataSource);

            // Exportar el reporte a PDF
            ByteArrayOutputStream pdfOutput = new ByteArrayOutputStream();
            JasperExportManager.exportReportToPdfStream(jasperPrint, pdfOutput);

            return Response.ok(pdfOutput.toByteArray())
                    .header("Content-Disposition", "attachment; filename=reporte_5_anuncios_mas_mostrados.pdf")
                    .build();

        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"message\":\"Ocurrió un error al generar el reporte de anuncios más mostrados.\"}")
                    .build();
        }
    }

}
