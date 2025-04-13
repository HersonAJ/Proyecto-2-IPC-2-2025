import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { RestConstants } from './rest-constants';

// Modelo para citas
export interface Cita {
  idServicio: number;
  idCliente: number;
  idEmpleado: number;
  fechaCita: string;
  horaCita: string;
  estado: string;
}

// Modelo para servicios
export interface Servicio {
  idServicio: number;
  nombreServicio: string;
  descripcion: string;
  duracion: number;
  precio: number;
  estado: string;
  imagen: string | null;
  idEncargado: number;
  empleadosAsignados: string[]; 
}

// Modelo para bloques horarios ocupados
export interface BloqueOcupado {
  inicio: string;  // Formato HH:mm
  fin: string;     // Formato HH:mm
  estado: string;  // Siempre "ocupado" 
}

@Injectable({
  providedIn: 'root',
})
export class CitaService {
  private readonly apiURL: string;

  constructor(private http: HttpClient, private restConstants: RestConstants) {
    this.apiURL = this.restConstants.getApiURL();
  }

  // Método para crear una cita
  crearCita(cita: Cita): Observable<any> {
    const url = `${this.apiURL}citas/crear`;
    return this.http.post<any>(url, cita); 
  }
  

  // Método para obtener empleados por servicio
  obtenerEmpleadosPorServicio(idServicio: number): Observable<any> {
    const url = `${this.apiURL}citas/empleados/${idServicio}`;
    return this.http.get(url);
  }

  // Método para obtener servicios disponibles
  obtenerServicios(): Observable<Servicio[]> {
    const url = `${this.apiURL}citas/servicios`;
    return this.http.get<Servicio[]>(url);
  }
    //metodo para obtener los horarios ocupados de los empleados
    obtenerHorariosOcupados(idEmpleado: number, fecha: string): Observable<BloqueOcupado[]> {
      const url = `${this.apiURL}citas/horarios-ocupados/${idEmpleado}/${fecha}`;
      return this.http.get<BloqueOcupado[]>(url);
    }
}
