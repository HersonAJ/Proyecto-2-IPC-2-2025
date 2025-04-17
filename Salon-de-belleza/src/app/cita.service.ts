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
  nombreServicio?: string; 
  nombreEmpleado?: string; 
  precioServicio?: string;
  idFactura?: string;
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

export interface Factura {
  idFactura: number;
  idCita: number;
  idCliente: number;
  nombreCliente: string;
  direccionCliente: string;
  numeroTelefonoCliente: string;
  idEmpleado: number;
  nombreEmpleado: string;
  idServicio: number;
  total: number;
  fechaFactura: string;
  detalles: string;
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

  // Método para obtener las facturas del cliente
  obtenerFacturasCliente(idCliente: number): Observable<Factura[]> {
    const url = `${this.apiURL}facturas/cliente/${idCliente}`;
    return this.http.get<Factura[]>(url);
  }

    //Método para obtener el historial de citas atendidas
    obtenerHistorialCitasAtendidas(token: string): Observable<Cita[]> {
      const url = `${this.apiURL}historial/atendidas`;
      const headers = { Authorization: `Bearer ${token}` };
      return this.http.get<Cita[]>(url, { headers });
    }
  
    //Método para obtener el historial de citas canceladas
    obtenerHistorialCitasCanceladas(token: string): Observable<Cita[]> {
      const url = `${this.apiURL}historial/canceladas`;
      const headers = { Authorization: `Bearer ${token}` };
      return this.http.get<Cita[]>(url, { headers });
    }
}
