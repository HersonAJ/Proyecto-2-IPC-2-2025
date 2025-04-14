/*import { Component, OnInit } from '@angular/core';
import { CitaEmpleadoService, Cita } from '../cita-empleado.service';
import { HttpErrorResponse } from '@angular/common/http';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-gestionar-citas-empleado',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './gestionar-citas-empleado.component.html',
  styleUrls: ['./gestionar-citas-empleado.component.css']
})
export class GestionarCitasEmpleadoComponent implements OnInit {
  citas: Cita[] = []; // Lista de citas asignadas
  mensaje: string = ''; // Mensaje de error o éxito
  cargando: boolean = true; // Estado de carga
  token: string | null = ''; // Token JWT desde localStorage

  constructor(private citaEmpleadoService: CitaEmpleadoService) {}

  ngOnInit(): void {
    this.token = localStorage.getItem('token'); // Obtener el token JWT
    if (this.token) {
      this.obtenerCitas();
    } else {
      this.mensaje = 'Error: No se encontró un token válido.';
      this.cargando = false;
    }
  }

  obtenerCitas(): void {
    this.citaEmpleadoService.obtenerCitasAsignadas(this.token!).subscribe({
      next: (citas: Cita[]) => {
        this.citas = citas;
        this.cargando = false;
      },
      error: (error: HttpErrorResponse) => {
        this.mensaje = 'Error al cargar las citas. Inténtelo nuevamente más tarde.';
        console.error(error);
        this.cargando = false;
      }
    });
  }

  cambiarEstado(idCita: number, event: Event): void {
    const nuevoEstado = (event.target as HTMLSelectElement).value; // Conversión explícita
    if (!this.token) {
      this.mensaje = 'Error: No se encontró un token válido.';
      return;
    }
  
    this.citaEmpleadoService.cambiarEstadoCita(this.token, idCita, nuevoEstado).subscribe({
      next: () => {
        this.mensaje = 'El estado de la cita se actualizó correctamente.';
        this.obtenerCitas(); // Refrescar las citas después del cambio
      },
      error: (error: HttpErrorResponse) => {
        this.mensaje = 'Error al cambiar el estado de la cita. Inténtelo nuevamente.';
        console.error(error);
      }
    });
  }
  
}*/

import { Component, OnInit } from '@angular/core';
import { CitaEmpleadoService, Cita } from '../cita-empleado.service';
import { HttpErrorResponse } from '@angular/common/http';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-gestionar-citas-empleado',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './gestionar-citas-empleado.component.html',
  styleUrls: ['./gestionar-citas-empleado.component.css']
})
export class GestionarCitasEmpleadoComponent implements OnInit {
  citasPorEstado: Record<string, Cita[]> = {};
  mensaje: string = '';
  cargando: boolean = true;
  token: string | null = '';
  estados: string[] = [];
  estadoSeleccionado: string = 'Pendiente'

  constructor(private citaEmpleadoService: CitaEmpleadoService) {}

  estadosOrdenados: string[] = ['Pendiente', 'Atendida', 'Cancelada', 'No Presentado'];

  ngOnInit(): void {
    this.token = localStorage.getItem('token');
    if (this.token) {
      this.obtenerCitas();
    } else {
      this.mensaje = 'Error: No se encontró un token válido.';
      this.cargando = false;
    }
  }

  obtenerCitas(): void {
    this.citaEmpleadoService.obtenerCitasAsignadas(this.token!).subscribe({
      next: (citas: Cita[]) => {
        const agrupadas = this.agruparPorEstado(citas);
        this.estados = this.estadosOrdenados.filter((estado) => agrupadas[estado]); // Mantiene el orden definido
        this.citasPorEstado = agrupadas;
        this.cargando = false;
      },
      error: (error: HttpErrorResponse) => {
        this.mensaje = 'Error al cargar las citas. Inténtelo nuevamente más tarde.';
        console.error(error);
        this.cargando = false;
      }
    });
  }

  agruparPorEstado(citas: Cita[]): Record<string, Cita[]> {
    return citas.reduce((acc: Record<string, Cita[]>, cita: Cita) => {
      if (!acc[cita.estado]) {
        acc[cita.estado] = [];
      }
      acc[cita.estado].push(cita);
      return acc;
    }, {});
  }
  cambiarEstado(idCita: number, event: Event): void {
    const nuevoEstado = (event.target as HTMLSelectElement).value;
  
    // Confirmar antes de proceder
    const confirmacion = window.confirm(`¿Está seguro de que desea cambiar el estado a "${nuevoEstado}"?`);
    if (!confirmacion) {
      return; 
    }
  
    if (!this.token) {
      this.mensaje = 'Error: No se encontró un token válido.';
      return;
    }
  
    this.citaEmpleadoService.cambiarEstadoCita(this.token, idCita, nuevoEstado).subscribe({
      next: () => {
        this.mensaje = 'El estado de la cita se actualizó correctamente.';
        this.obtenerCitas(); // Refrescar las citas después del cambio
      },
      error: (error: HttpErrorResponse) => {
        this.mensaje = 'Error al cambiar el estado de la cita. Inténtelo nuevamente.';
        console.error(error);
      }
    });
  }
  
  seleccionarSeccion(estado: string): void {
    this.estadoSeleccionado = estado; 
  }

  formatearHora(hora: number[]): string {
    const [hours, minutes] = hora;
    return `${hours.toString().padStart(2, '0')}:${minutes.toString().padStart(2, '0')}`;
  }
  
}

