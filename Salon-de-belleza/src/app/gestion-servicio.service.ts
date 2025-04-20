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

// Método para gestionar un servicio
actualizarServicio(
  idServicio: number,
  servicio: any,
  nuevaImagen: File | null,
  catalogoPdf: File | null,
  nuevoEstado: string | null,
  empleadosIds: number[] | null
): Observable<any> {
  const token = localStorage.getItem('token');
  const formData = new FormData();

  if (servicio) {
    formData.append('servicio', JSON.stringify(servicio)); 
  }
  if (nuevaImagen) {
    formData.append('nuevaImagen', nuevaImagen, nuevaImagen.name); 
  }
  if (catalogoPdf) {
    formData.append('catalogoPdf', catalogoPdf, catalogoPdf.name);
  }
  if (nuevoEstado) {
    formData.append('nuevoEstado', nuevoEstado);
  }
  if (empleadosIds) {
    formData.append('empleadosIds', JSON.stringify(empleadosIds)); // Lista de IDs de empleados
  }

  const headers = new HttpHeaders({
    'Authorization': `Bearer ${token}`
  });
  return this.http.put(`${this.apiURL}/${idServicio}`, formData, { headers });
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
