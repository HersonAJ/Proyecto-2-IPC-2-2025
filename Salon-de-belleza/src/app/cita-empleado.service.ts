import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { RestConstants } from './rest-constants';

// Modelo para Cita
export interface Cita {
  idCita: number;
  fechaCita: number[];
  horaCita: number[];
  idServicio: number;
  nombreServicio: string;
  descripcionServicio: string;
  duracionServicio: number;
  precioServicio: number;
  idCliente: number;
  nombreCliente: string;
  estado: string;
}


@Injectable({
  providedIn: 'root'
})
export class CitaEmpleadoService {
  private readonly apiURL: string;

  constructor(private http: HttpClient, private restConstants: RestConstants) {
    this.apiURL = this.restConstants.getApiURL();
  }

  obtenerCitasAsignadas(token: string): Observable<Cita[]> {
    const url = `${this.apiURL}gestionCitasEmpleado/asignadas`;
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.http.get<Cita[]>(url, { headers });
  }

  cambiarEstadoCita(token: string, idCita: number, nuevoEstado: string): Observable<any> {
    const url = `${this.apiURL}gestionCitasEmpleado/cambiarEstado/${idCita}?nuevoEstado=${nuevoEstado}`;
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.http.put(url, null, { headers });
  }
  
}