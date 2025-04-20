import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { RestConstants } from './rest-constants';

@Injectable({
  providedIn: 'root'
})
export class ServiciosService {
  private apiURL: string;

  constructor(private http: HttpClient, private restConstant: RestConstants) { 
    this.apiURL = this.restConstant.getApiURL() + 'serviciosNuevos';
  }

// Método para crear un servicio
crearServicio(servicio: any, catalogoPdf: File, imagen: File): Observable<any> {
  const token = localStorage.getItem('token');
  const formData = new FormData();

  // Agregar campos al FormData
  formData.append('nombreServicio', servicio.nombreServicio);
  formData.append('descripcion', servicio.descripcion);
  formData.append('duracion', servicio.duracion.toString());
  formData.append('precio', servicio.precio.toString());
  formData.append('estado', servicio.estado);
  formData.append('imagen', imagen, imagen.name); 
  formData.append('catalogoPdf', catalogoPdf, catalogoPdf.name); 
  formData.append('empleados', JSON.stringify(servicio.empleados)); 

  const headers = new HttpHeaders({
    'Authorization': `Bearer ${token}`
  });
  return this.http.post(`${this.apiURL}/crear`, formData, { headers });
}

  // Método para obtener todos los servicios
  obtenerServicios(): Observable<any[]> {
    const token = localStorage.getItem('token'); // Recupera el token del localStorage
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
  
    return this.http.get<any[]>(`${this.apiURL}`, { headers });
  }

  // Método para obtener empleados con rol "Empleado"
  obtenerEmpleados(): Observable<any[]> {
    const token = localStorage.getItem('token'); // Recupera el token del localStorage
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });

    // Endpoint para obtener los empleados
    return this.http.get<any[]>(`${this.apiURL}/empleados`, { headers });
  }
}

