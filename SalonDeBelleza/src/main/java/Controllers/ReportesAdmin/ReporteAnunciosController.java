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
import java.sql.Date;
import java.util.List;
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
}

