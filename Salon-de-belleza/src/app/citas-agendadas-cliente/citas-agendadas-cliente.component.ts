import { Component, OnInit } from '@angular/core';
import { ObtenerCitaService, Cita } from '../obtener-cita.service';
import { AuthService } from '../auth.service';
import { CommonModule } from '@angular/common';
import { CancelarCitaService } from '../cancelar-cita.service';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-citas-agendadas-cliente',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './citas-agendadas-cliente.component.html',
  styleUrl: './citas-agendadas-cliente.component.css'
})
export class CitasAgendadasClienteComponent implements OnInit{
  citas: Cita[] = [];
  mensaje: string = '';
  cargando: boolean = true;
  selectedOrden: string = 'Fecha'; 

  constructor(
    private obtenerCitaService: ObtenerCitaService,
    private authService: AuthService,
    private cancelarCitaService: CancelarCitaService
  ) {}

  ngOnInit(): void {
    this.cargarCitasAgendadas();
  }

  cargarCitasAgendadas(): void {
    const token = localStorage.getItem('token');
    const idCliente = this.authService.getIdUsuarioFromToken();

    if (!token || !idCliente) {
      this.mensaje = 'No se encontró un token válido o el ID del cliente no está disponible.';
      this.cargando = false;
      return;
    }

    this.obtenerCitaService.obtenerCitasPendientes(token, idCliente).subscribe({
      next: (data: Cita[] | null) => {
        this.citas = data || [];
        this.ordenarCitas(); 
        this.cargando = false;
      },
      error: (err) => {
        console.error('Error al obtener las citas pendientes:', err);
        this.mensaje = 'No se pudieron cargar las citas pendientes.';
        this.cargando = false;
      }
    });
  }

  ordenarCitas(): void {
    switch (this.selectedOrden) {
      case 'Fecha':
        this.citas.sort((a, b) => {
          const fechaA = new Date(a.fechaCita[0], a.fechaCita[1] - 1, a.fechaCita[2]).getTime();
          const fechaB = new Date(b.fechaCita[0], b.fechaCita[1] - 1, b.fechaCita[2]).getTime();
          return fechaA - fechaB; // Orden ascendente
        });
        break;

      case 'Hora':
        this.citas.sort((a, b) => {
          const horaA = a.horaCita[0] * 60 + a.horaCita[1]; 
          const horaB = b.horaCita[0] * 60 + b.horaCita[1];
          return horaA - horaB; // Orden ascendente
        });
        break;

      case 'Precio':
        this.citas.sort((a, b) => a.precioServicio - b.precioServicio);
        break;

      default:
        break;
    }
  }

  transformarFecha(fecha: number[]): string {
    const [year, month, day] = fecha;
    return `${day.toString().padStart(2, '0')}/${month.toString().padStart(2, '0')}/${year}`;
  }

  transformarHora(hora: number[]): string {
    const [hour, minute] = hora;
    return `${hour.toString().padStart(2, '0')}:${minute.toString().padStart(2, '0')}`;
  }

  cancelarCita(idCita: number): void {
    const confirmacion = window.confirm('¿Estás seguro de que deseas cancelar esta cita?');
    if (!confirmacion) {
      return;
    }
    const token = localStorage.getItem('token');
    if (!token) {
      this.mensaje = 'No se encontró un token válido.';
      return;
    }
  
    this.cancelarCitaService.cancelarCita(token, idCita).subscribe({
      next: (response) => {
        this.mensaje = response.message;
        this.cargarCitasAgendadas(); 
      },
      error: (err) => {
        if (err.status === 401) {
          this.mensaje = 'Token inválido o vencido.';
        } else if (err.status === 400) {
          this.mensaje = 'No se pudo cancelar la cita. Verifica el ID de la cita.';
        } else {
          this.mensaje = 'Error al cancelar la cita. Inténtalo más tarde.';
        }
        console.error(err);
      }
    });
  }  
}
