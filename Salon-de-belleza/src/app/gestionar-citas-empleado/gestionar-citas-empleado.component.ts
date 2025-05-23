import { Component, OnInit } from '@angular/core';
import { CitaEmpleadoService, Cita } from '../cita-empleado.service';
import { HttpErrorResponse } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { CrearFacturaComponent } from '../crear-factura/crear-factura.component';

@Component({
  selector: 'app-gestionar-citas-empleado',
  standalone: true,
  imports: [CommonModule, CrearFacturaComponent], 
  templateUrl: './gestionar-citas-empleado.component.html',
  styleUrls: ['./gestionar-citas-empleado.component.css']
})
export class GestionarCitasEmpleadoComponent implements OnInit {
  citasPorEstado: Record<string, Cita[]> = {};
  mensaje: string = '';
  cargando: boolean = true;
  token: string  = '';
  estados: string[] = [];
  estadoSeleccionado: string = 'Pendiente';
  mostrarFactura: boolean = false; 
  citaSeleccionada!: Cita; 

  constructor(private citaEmpleadoService: CitaEmpleadoService) {}


  estadosOrdenados: string[] = ['Pendiente', 'Atendida', 'Cancelada', 'No Presentado'];

  ngOnInit(): void {
    this.token = localStorage.getItem('token') || '';
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
        this.estados = this.estadosOrdenados.filter((estado) => agrupadas[estado]); 
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
  
    const confirmacion = window.confirm(`¿Está seguro de que desea cambiar el estado a "${nuevoEstado}"?`);
    if (!confirmacion) {
      return;
    }
  
    if (!this.token) {
      this.mensaje = 'Error: No se encontró un token válido.';
      return;
    }
    if (nuevoEstado === 'Cancelada') {
      this.mensaje = 'No tienes permiso para cancelar citas.';
      return;
    }
  
    if (nuevoEstado === 'No Presentado') {
      const cita = this.buscarCitaPorId(idCita);
      const motivo = 'No presentado';
  
      this.citaEmpleadoService.cambiarEstadoCita(this.token, idCita, nuevoEstado).subscribe({
        next: () => {
          this.citaEmpleadoService.agregarAListaNegra(this.token, cita.idCliente, idCita, motivo).subscribe({
            next: () => {
              this.mensaje = 'El cliente se agregó a la lista negra exitosamente y el estado de la cita se actualizó.';
              this.obtenerCitas(); 
            },
            error: (error: HttpErrorResponse) => {
              this.mensaje = 'Error al agregar el cliente a la lista negra. Inténtelo nuevamente.';
              console.error(error);
            }
          });
        },
        error: (error: HttpErrorResponse) => {
          this.mensaje = 'Error al cambiar el estado de la cita. Inténtelo nuevamente.';
          console.error(error);
        }
      });
  
      return; 
    }
  
    if (nuevoEstado === 'Atendida') {
      this.citaSeleccionada = this.buscarCitaPorId(idCita);
      this.mostrarFactura = true;
    } else {
      this.citaEmpleadoService.cambiarEstadoCita(this.token, idCita, nuevoEstado).subscribe({
        next: () => {
          this.mensaje = 'El estado de la cita se actualizó correctamente.';
          this.obtenerCitas();
        },
        error: (error: HttpErrorResponse) => {
          this.mensaje = 'Error al cambiar el estado de la cita. Inténtelo nuevamente.';
          console.error(error);
        }
      });
    }
}   

  buscarCitaPorId(idCita: number): Cita {
    for (const citas of Object.values(this.citasPorEstado)) {
      for (const cita of citas) {
        if (cita.idCita === idCita) {
          return cita;
        }
      }
    }
    throw new Error('Cita no encontrada'); 
  }

  confirmarFactura(): void {
    if (!this.token || !this.citaSeleccionada) {
      this.mensaje = 'Error: No se encontró un token válido o cita seleccionada.';
      return;
    }
  
    // Primero cambia el estado de la cita a "Atendida"
    this.citaEmpleadoService.cambiarEstadoCita(this.token, this.citaSeleccionada.idCita, 'Atendida').subscribe({
      next: () => {
        this.citaEmpleadoService.crearFactura(this.token, this.citaSeleccionada.idCita).subscribe({
          next: () => {
            this.mensaje = 'Factura generada y estado de la cita actualizado correctamente.';
            this.mostrarFactura = false; 
            this.obtenerCitas(); 
          },
          error: (error: HttpErrorResponse) => {
            this.mensaje = 'Error al generar la factura. Inténtelo nuevamente.';
            console.error(error);
          }
        });
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

