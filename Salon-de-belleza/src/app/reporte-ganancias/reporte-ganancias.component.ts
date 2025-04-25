import { Component, OnInit } from '@angular/core';
import { ReportesAdminService } from '../reportes-admin.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-reporte-ganancias',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './reporte-ganancias.component.html',
  styleUrls: ['./reporte-ganancias.component.css'],
})
export class ReporteGananciasComponent implements OnInit {
  reportes: any[] = []; 
  servicios: any[] = [];
  mensaje: string = ''; 
  fechaInicio: string = ''; 
  fechaFin: string = ''; 
  idServicio: number | null = null; 
  totalGeneral: number = 0; 

  constructor(private reportesAdminService: ReportesAdminService) {}

  ngOnInit(): void {
    this.cargarServicios(); 
    this.obtenerReporte();
  }

  cargarServicios(): void {
    this.reportesAdminService.obtenerListadoServicios().subscribe({
      next: (data) => {
        this.servicios = data;
      },
      error: (error: HttpErrorResponse) => {
        console.error('Error al cargar el listado de servicios:', error);
        this.mensaje = 'Ocurrió un error al cargar el listado de servicios.';
      },
    });
  }

  obtenerReporte(): void {
    const params: any = {};
    if (this.fechaInicio.trim() && this.fechaFin.trim()) {
      params.fechaInicio = this.fechaInicio;
      params.fechaFin = this.fechaFin;
    }
    if (this.idServicio !== null) {
      params.idServicio = this.idServicio;
    }
    this.reportesAdminService.obtenerReporteGanancias(params).subscribe({
      next: (data) => {
        this.reportes = data;
        this.calcularTotalGeneral(); // Calcular el total general
        this.mensaje = this.reportes.length === 0 ? 'No se encontraron registros.' : '';
      },
      error: (error: HttpErrorResponse) => {
        if (error.status === 404) {
          this.mensaje = 'No se encontraron datos con los parámetros ingresados.';
        } else {
          console.error('Error al obtener el reporte:', error);
          this.mensaje = 'Ocurrió un error al cargar el reporte.';
        }
        this.reportes = []; 
      },
    });
  }

  // Método para calcular el total general de ganancias
  calcularTotalGeneral(): void {
    this.totalGeneral = this.reportes.reduce((sum, reporte) => {
      return sum + (reporte.totalIngresos || 0); // Sumar las ganancias totales de cada servicio
    }, 0);
  }

  aplicarFiltros(): void {
    if (!this.fechaInicio.trim() && !this.fechaFin.trim() && this.idServicio === null) {
      this.mensaje = 'Debe ingresar al menos un filtro para realizar la búsqueda.';
      return;
    }
    this.obtenerReporte();
  }

  reiniciarFiltros(): void {
    this.fechaInicio = '';
    this.fechaFin = '';
    this.idServicio = null;
    this.obtenerReporte();
  }
  exportarReportePDF(): void {
    const params: any = {};
    if (this.fechaInicio.trim() && this.fechaFin.trim()) {
      params.fechaInicio = this.fechaInicio;
      params.fechaFin = this.fechaFin;
    }
    if (this.idServicio !== null) {
      params.idServicio = this.idServicio;
    }

    this.reportesAdminService.exportarReporteGananciasPDF(params).subscribe({
      next: (pdf: Blob) => {
        const url = window.URL.createObjectURL(pdf);
        const a = document.createElement('a');
        a.href = url;
        a.download = 'reporte_ganancias.pdf';
        a.click();
        window.URL.revokeObjectURL(url);
      },
      error: (error: HttpErrorResponse) => {
        console.error('Error al exportar el reporte PDF:', error);
        this.mensaje = 'Ocurrió un error al exportar el reporte PDF.';
      },
    });
  }
}
