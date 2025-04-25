import { Component, OnInit } from '@angular/core';
import { ReporteServiciosService } from '../reporte-servicios.service'; 
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-reporte-servicios-servicio-mas-ingreso',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './reporte-servicios-servicio-mas-ingreso.component.html',
  styleUrls: ['./reporte-servicios-servicio-mas-ingreso.component.css']
})
export class ReporteServiciosServicioMasIngresoComponent implements OnInit {

  reporte: any = null; 
  mensajeError: string | null = null;
  fechaInicio: string = ''; 
  fechaFin: string = '';

  constructor(private reporteServiciosService: ReporteServiciosService) {}

  ngOnInit(): void {
    this.obtenerReporte();
  }

  obtenerReporte(): void {
    const params: any = {};
    if (this.fechaInicio) {
      params.fechaInicio = this.fechaInicio;
    }
    if (this.fechaFin) {
      params.fechaFin = this.fechaFin;
    }

    this.reporteServiciosService.obtenerServicioMasIngreso(params).subscribe({
      next: (data) => {
        this.reporte = data;
        this.mensajeError = null;
      },
      error: (error) => {
        if (error.status === 404) {
          this.mensajeError = 'No se encontraron registros para el intervalo de tiempo seleccionado.';
        } else {
          this.mensajeError = 'Ocurrió un error al obtener el reporte. Por favor, inténtalo de nuevo.';
        }
        this.reporte = null;
      },
      complete: () => {
        console.log('Operación completada');
      }
    });
  }

  aplicarFiltros(fechaInicio: string, fechaFin: string): void {
    this.fechaInicio = fechaInicio;
    this.fechaFin = fechaFin;
    this.obtenerReporte();
  }

  reiniciarFiltros(): void {
    this.fechaInicio = '';
    this.fechaFin = '';
    this.obtenerReporte();
  }
}
