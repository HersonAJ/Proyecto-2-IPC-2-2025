import { Component, OnInit } from '@angular/core';
import { ReportesMarketingService } from '../reportes-marketing.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-reporte-marketing-anuncios-menos-mostrados',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './reporte-marketing-anuncios-menos-mostrados.component.html',
  styleUrls: ['./reporte-marketing-anuncios-menos-mostrados.component.css'],
})
export class ReporteMarketingAnunciosMenosMostradosComponent implements OnInit {
  reportes: any[] = [];
  mensaje: string = '';
  fechaInicio: string = '';
  fechaFin: string = '';

  constructor(private reportesMarketingService: ReportesMarketingService) {}

  ngOnInit(): void {
    this.obtenerReporte({});
  }

  obtenerReporte(params: any): void {
    this.reportesMarketingService.obtenerAnunciosMenosMostrados(params).subscribe({
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

  aplicarFiltros(): void {
    const params: any = {};
    if (this.fechaInicio.trim()) params.fechaInicio = this.fechaInicio;
    if (this.fechaFin.trim()) params.fechaFin = this.fechaFin;

    this.obtenerReporte(params);
  }

  reiniciarFiltros(): void {
    this.fechaInicio = '';
    this.fechaFin = '';
    this.obtenerReporte({});
  }
}
