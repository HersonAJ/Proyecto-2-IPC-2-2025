import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { RestConstants } from './rest-constants';

// Modelo para Cita
export interface Cita {
  idCita: number;
  fechaCita: number[]; 
  horaCita: number[]; 
  estado: string;
  facturaGenerada: boolean;
  idServicio: number;
  nombreServicio: string;
  descripcionServicio: string;
  duracionServicio: number;
  precioServicio: number;
  idEmpleado: number;
  nombreEmpleado: string;
}


@Injectable({
  providedIn: 'root',
})
export class ObtenerCitaService {
  private readonly apiURL: string;

  constructor(private http: HttpClient, private restConstants: RestConstants) {
    this.apiURL = this.restConstants.getApiURL();
  }

  obtenerCitasPendientes(token: string, idCliente: number): Observable<Cita[]> {
    const url = `${this.apiURL}obtenerCita/pendientes/${idCliente}`;
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.http.get<Cita[]>(url, { headers });
  }
}
