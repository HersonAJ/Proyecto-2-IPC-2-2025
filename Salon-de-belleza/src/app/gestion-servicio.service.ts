import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { RestConstants } from './rest-constants';

@Injectable({
  providedIn: 'root'
})
export class GestionServiciosService {
  private apiURL: string;

  constructor(private http: HttpClient, private restConstant: RestConstants) {
    this.apiURL = this.restConstant.getApiURL() + 'servicios';
  }

  // Método para gestionar un servicio (editar, cambiar estado, actualizar imagen, etc.)
  actualizarServicio(idServicio: number, datosGestion: any): Observable<any> {
    const token = localStorage.getItem('token');
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`,
      'Content-Type': 'application/json'
    });
    return this.http.put(`${this.apiURL}/${idServicio}`, datosGestion, { headers });
  }

  // Método para obtener empleados asignados a un servicio
  obtenerEmpleadosPorServicio(idServicio: number): Observable<any[]> {
    const token = localStorage.getItem('token');
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
    return this.http.get<any[]>(`${this.apiURL}/${idServicio}/empleados`, { headers });
  }

  // Método para obtener empleados asignados y no asignados a un servicio
  obtenerEmpleadosPorEstado(idServicio: number): Observable<{ asignados: any[]; no_asignados: any[] }> {
    const token = localStorage.getItem('token');
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
    return this.http.get<{ asignados: any[]; no_asignados: any[] }>(`${this.apiURL}/${idServicio}/empleados/estado`, { headers });
  }

  // Método para obtener todos los servicios
  obtenerServicios(): Observable<any[]> {
    const token = localStorage.getItem('token'); // Recupera el token del localStorage
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
    return this.http.get<any[]>(`${this.apiURL}`, { headers });
  }
}
