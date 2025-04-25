import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { RestConstants } from './rest-constants';

@Injectable({
  providedIn: 'root'
})
export class ReporteServiciosService {
  private readonly apiURL: string;

  constructor(private http: HttpClient, private restConstants: RestConstants) {
    this.apiURL = this.restConstants.getApiURL();
  }

  // Método para obtener el historial de servicios más comprados
  obtenerServiciosMasComprados(params: any): Observable<any[]> {
    const url = `${this.apiURL}reporte-servicios/servicios-mas-comprados`;
    return this.http.get<any[]>(url, { params });
  }
}
