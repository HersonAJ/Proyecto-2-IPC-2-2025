import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { RestConstants } from './rest-constants';

@Injectable({
  providedIn: 'root'
})
export class ReportesMarketingService {
  private readonly apiURL: string;

  constructor(private http: HttpClient, private restConstants: RestConstants) {
    this.apiURL = this.restConstants.getApiURL();
  }

  // Método para obtener el Top 5 de anuncios más mostrados
  obtenerAnunciosMasMostrados(params: any): Observable<any[]> {
    const url = `${this.apiURL}marketing/reportes/anuncios-mas-mostrados`;
    return this.http.get<any[]>(url, { params });
  }

  // Método para obtener el Top 5 de anuncios menos mostrados
  obtenerAnunciosMenosMostrados(params: any): Observable<any[]> {
    const url = `${this.apiURL}marketing/reportes/anuncios-menos-mostrados`;
    return this.http.get<any[]>(url, { params });
  }

  // Método para obtener el historial de anuncios más comprados
  obtenerAnunciosMasComprados(params: any): Observable<any[]> {
    const url = `${this.apiURL}marketing/reportes/anuncios-mas-comprados`;
    return this.http.get<any[]>(url, { params });
  }
}
