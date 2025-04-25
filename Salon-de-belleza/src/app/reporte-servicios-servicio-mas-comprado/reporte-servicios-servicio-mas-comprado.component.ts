import { Component, OnInit } from '@angular/core';
import { ReporteServiciosService } from '../reporte-servicios.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-reporte-servicios-servicio-mas-comprado',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './reporte-servicios-servicio-mas-comprado.component.html',
  styleUrls: ['./reporte-servicios-servicio-mas-comprado.component.css']
})
export class ReporteServiciosServicioMasCompradoComponent implements OnInit {

  reportes: any[] = [];
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

    this.reporteServiciosService.obtenerServiciosMasComprados(params).subscribe({
      next: (data) => {
        if (data.length === 0) {
          this.mensajeError = 'No se encontraron datos para el intervalo de tiempo seleccionado.';
          this.reportes = []; 
        } else {
          this.reportes = data;
          this.mensajeError = null; 
        }
      },
      error: (error) => {
        if (error.status === 404) {
          this.mensajeError = 'No se encontraron datos para el reporte.';
        } else {
          console.error('Error al obtener el reporte:', error);
          this.mensajeError = 'Ocurrió un error al obtener el reporte. Por favor, inténtalo de nuevo.';
        }
        this.reportes = []; 
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
