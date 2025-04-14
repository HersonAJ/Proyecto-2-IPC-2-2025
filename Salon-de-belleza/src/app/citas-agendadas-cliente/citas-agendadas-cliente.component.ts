import { Component, OnInit } from '@angular/core';
import { ObtenerCitaService, Cita } from '../obtener-cita.service';
import { AuthService } from '../auth.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-citas-agendadas-cliente',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './citas-agendadas-cliente.component.html',
  styleUrl: './citas-agendadas-cliente.component.css'
})
export class CitasAgendadasClienteComponent implements OnInit{

  citas: Cita[] = [];
  mensaje: string = '';
  cargando: boolean = true;

  constructor(private obtenerCitaService: ObtenerCitaService, private authService: AuthService) {
  }

  ngOnInit(): void {
    this.cargarCitasAgendadas();
  }

  cargarCitasAgendadas(): void {
    const token = localStorage.getItem('token');
    const idCliente = this.authService.getIdUsuarioFromToken();

    if (!token || !idCliente) {
      this.mensaje = 'No se encontro un token valido o el ID del cliente no esta disponible';
      this.cargando = false;
      return;
    }

    this.obtenerCitaService.obtenerCitasPendientes(token, idCliente).subscribe({
      next: (data: Cita[]) => {
        this.citas = data;
        this,this.cargando = false;
      },
      error: (err) => {
        console.error('Error al obtener la citas pendientes:' ,err);
        this.mensaje = 'No se pudieron cargas las citas pendientes.';
        this.cargando = false;
      }
    });
  }

  transformarFecha(fecha: number[]): string {
    const [year, month, day] = fecha; // Desestructurar el arreglo
    return `${day.toString().padStart(2, '0')}/${month.toString().padStart(2, '0')}/${year}`;
  }
  
  transformarHora(hora: number[]): string {
    const [hour, minute] = hora; // Desestructurar el arreglo
    return `${hour.toString().padStart(2, '0')}:${minute.toString().padStart(2, '0')}`;
  }
}
