import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CitaEmpleadoService, Cita } from '../cita-empleado.service';
import { AuthService } from '../auth.service';

@Component({
  selector: 'app-citas-asignadas',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './citas-asignadas.component.html',
  styleUrls: ['./citas-asignadas.component.css']
})
export class CitasAsignadasComponent implements OnInit {
  citas: Cita[] = [];
  mensaje: string = '';
  cargando: boolean = true;

  constructor(
    private citaEmpleadoService: CitaEmpleadoService,
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    this.cargarCitasAsignadas();
  }

  cargarCitasAsignadas(): void {
    const token = localStorage.getItem('token');
    if (!token) {
      this.mensaje = 'No se encontró un token válido para la autenticación.';
      this.cargando = false;
      return;
    }

    this.citaEmpleadoService.obtenerCitasAsignadas(token).subscribe({
      next: (data: Cita[]) => {
        this.citas = data;
        this.cargando = false;
      },
      error: (err) => {
        console.error('Error al cargar las citas asignadas:', err);
        this.mensaje = 'No se pudieron cargar las citas asignadas. Inténtelo más tarde.';
        this.cargando = false;
      },
    });
  }

  transformarFecha(fecha: number[]): string {
    const [year, month, day] = fecha; 
    return `${day.toString().padStart(2, '0')}/${month.toString().padStart(2, '0')}/${year}`;
  }
  
  transformarHora(hora: number[]): string {
    const [hour, minute] = hora; 
    return `${hour.toString().padStart(2, '0')}:${minute.toString().padStart(2, '0')}`;
  }
  
}
