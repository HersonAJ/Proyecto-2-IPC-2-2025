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
    this.apiURL = this.restConstant.getApiURL() + 'servicios';
  }

  // Método para crear un servicio
  crearServicio(servicio: any): Observable<any> {
    const token = localStorage.getItem('token');
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${localStorage.getItem('token')}`,
      'Content-Type': 'application/json'
    });
    
    return this.http.post(`${this.apiURL}/crear`, servicio, { headers });
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

