import { Component, OnInit } from '@angular/core';
import { ReportesAdminService } from '../reportes-admin.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-reporte-anuncios-admin',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './reporte-anuncios-admin.component.html',
  styleUrls: ['./reporte-anuncios-admin.component.css'],
})
export class ReporteAnunciosAdminComponent implements OnInit {
  reportes: any[] = []; 
  mensaje: string = ''; 
  fechaInicio: string = ''; 
  fechaFin: string = ''; 

  constructor(private reportesAdminService: ReportesAdminService) {}

  ngOnInit(): void {
    this.obtenerReporte(); 
  }

  // Método para obtener el reporte
  obtenerReporte(): void {
    const params: any = {};

    // Incluir fechas en los parámetros si están definidas
    if (this.fechaInicio.trim()) {
      params.fechaInicio = this.fechaInicio;
    }
    if (this.fechaFin.trim()) {
      params.fechaFin = this.fechaFin;
    }

    this.reportesAdminService.obtenerAnunciosMasMostrados(params).subscribe({
      next: (data) => {
        this.reportes = data;
        this.mensaje = this.reportes.length === 0 ? 'No se encontraron registros para el reporte solicitado.' : '';
      },
      error: (error: HttpErrorResponse) => {
        if (error.status === 404) {
          this.mensaje = 'No se encontraron registros para el reporte solicitado.';
        } else {
          console.error('Error al obtener el reporte:', error);
          this.mensaje = 'Ocurrió un error al cargar el reporte.';
        }
        this.reportes = [];
      },
    });
  }

  // Método para aplicar filtros
  aplicarFiltros(): void {
    this.obtenerReporte(); 
  }

  // Método para reiniciar filtros
  reiniciarFiltros(): void {
    this.fechaInicio = '';
    this.fechaFin = '';
    this.obtenerReporte(); 
  }
}
