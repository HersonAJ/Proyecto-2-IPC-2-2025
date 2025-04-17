import { Component, OnInit } from '@angular/core';
import { CitaService, Cita } from '../cita.service';
import { AuthService } from '../auth.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-historial-cliente',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './historial-cliente.component.html',
  styleUrls: ['./historial-cliente.component.css']
})
export class HistorialClienteComponent implements OnInit {
  citasAtendidas: Cita[] = [];
  citasCanceladas: Cita[] = [];
  loadingAtendidas: boolean = false;
  loadingCanceladas: boolean = false;
  errorAtendidas: string | null = null;
  errorCanceladas: string | null = null;

  mostrarAtendidas: boolean = false; 
  mostrarCanceladas: boolean = false; 

  constructor(private citaService: CitaService, private authService: AuthService) {}

  ngOnInit(): void {
    const token = localStorage.getItem('token');
    if (token) {
      this.obtenerCitasAtendidas(token);
      this.obtenerCitasCanceladas(token);
    } else {
      this.errorAtendidas = 'No se encontró un token de autenticación.';
      this.errorCanceladas = 'No se encontró un token de autenticación.';
    }
  }

  obtenerCitasAtendidas(token: string): void {
    this.loadingAtendidas = true;
    this.citaService.obtenerHistorialCitasAtendidas(token).subscribe({
      next: (citas) => {
        this.citasAtendidas = citas;
        this.loadingAtendidas = false;
      },
      error: (error) => {
        console.error('Error al obtener citas atendidas:', error);
        this.errorAtendidas = 'Error al cargar las citas atendidas.';
        this.loadingAtendidas = false;
      },
    });
  }

  obtenerCitasCanceladas(token: string): void {
    this.loadingCanceladas = true;
    this.citaService.obtenerHistorialCitasCanceladas(token).subscribe({
      next: (citas) => {
        this.citasCanceladas = citas;
        this.loadingCanceladas = false;
      },
      error: (error) => {
        console.error('Error al obtener citas canceladas:', error);
        this.errorCanceladas = 'Error al cargar las citas canceladas.';
        this.loadingCanceladas = false;
      },
    });
  }

  // Métodos para alternar entre las vistas
  mostrarHistorialAtendidas(): void {
    this.mostrarAtendidas = true;
    this.mostrarCanceladas = false;
  }

  mostrarHistorialCanceladas(): void {
    this.mostrarAtendidas = false;
    this.mostrarCanceladas = true;
  }  
}

