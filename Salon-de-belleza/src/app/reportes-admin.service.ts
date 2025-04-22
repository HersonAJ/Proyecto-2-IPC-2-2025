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

}

