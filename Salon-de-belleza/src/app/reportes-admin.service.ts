import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { RestConstants } from './rest-constants';

@Injectable({
  providedIn: 'root',
})
export class ReportesAdminService {
  private readonly apiURL: string;

  constructor(private http: HttpClient, private restConstants: RestConstants) {
    this.apiURL = this.restConstants.getApiURL(); 
  }

  obtenerReporteGanancias(params: any): Observable<any> {
    const url = `${this.apiURL}admin/reportes/ganancias-servicio`;
    return this.http.get<any>(url, { params });
  }

    // Método para obtener el listado de servicios
    obtenerListadoServicios(): Observable<any[]> {
      const url = `${this.apiURL}admin/reportes/listado`;
      return this.http.get<any[]>(url);
    }
  
  // Método para obtener el reporte de anuncios más mostrados
  obtenerAnunciosMasMostrados(params: any): Observable<any[]> {
    const url = `${this.apiURL}admin/reportes/anuncios-mas-mostrados`;
    return this.http.get<any[]>(url, { params });
  }

    // Método para obtener el reporte de cita de clientes
  obtenerClientesMasCitas(params: any): Observable<any[]> {
    const url = `${this.apiURL}admin/reportes/clientes-mas-citas`;
    return this.http.get<any[]>(url, { params });
  }

  // Método para obtener el reporte de menos citas
  obtenerClientesMenosCitas(params: any): Observable<any[]> {
    const url = `${this.apiURL}admin/reportes/clientes-menos-citas`;
    return this.http.get<any[]>(url, { params });
  }

  // Método para obtener el reporte de menos citas
  obtenerReporteListaNegra(params: any): Observable<any[]> {
    const url = `${this.apiURL}admin/reportes/clientes-lista-negra`;
    return this.http.get<any[]>(url, { params });
  }

  // Método para obtener el reporte del cliente que mas gasto
  obtenerReporteClienteMasGasto(params: any): Observable<any[]> {
    const url = `${this.apiURL}admin/reportes/clientes-mas-gastadores`;
    return this.http.get<any[]>(url, { params });
  }
  
  // Método para obtener el reporte del cliente que menos gasto
  obtenerReporteClienteMenosGasto(params: any): Observable<any[]> {
    const url = `${this.apiURL}admin/reportes/clientes-menos-gasto`;
    return this.http.get<any[]>(url, { params });
  }

  // Método para obtener los empleados activos
  obtenerEmpleadosActivos(params: any): Observable<any[]> {
    const url = `${this.apiURL}admin/reportes/empleados-activos`;
    return this.http.get<any[]>(url, { params });
  }

  // Método para obtener el reporte de ganancias por empleado
  obtenerReporteGanaciasPorEmpleado(params: any): Observable<any[]> {
    const url = `${this.apiURL}admin/reportes/ganancias-por-empleado`;
    return this.http.get<any[]>(url, { params });
  }

  // Método para obtener el reporte de ganancias por empleado
  obtenerReporteClientesMasCitasAtendidas(params: any): Observable<any[]> {
    const url = `${this.apiURL}admin/reportes/clientes-mas-citas-atendidas`;
    return this.http.get<any[]>(url, { params });
  } 
  
  // Método para generar el reporte de ganancias por servicio en PDF
exportarReporteGananciasPDF(params: any): Observable<Blob> {
  const url = `${this.apiURL}admin/reportes/ganancias-servicio/pdf`;
  return this.http.get(url, { params, responseType: 'blob' }); 
}

  // Método para generar el reporte de anuncios mas vistos en PDF
  exportarReporteAnunciosMasMostradosPDF(params: any): Observable<Blob> {
    const url = `${this.apiURL}admin/reportes/anuncios-mas-mostrados/pdf`;
    return this.http.get(url, { params, responseType: 'blob' }); 
  }

  // Método para generar el reporte de ganancias por empleado en PDF
exportarReporteGananciasPorEmpleadoPDF(params: any): Observable<Blob> {
  const url = `${this.apiURL}admin/reportes/ganancias-por-empleado/pdf`;
  return this.http.get(url, { params, responseType: 'blob' });
}


}

