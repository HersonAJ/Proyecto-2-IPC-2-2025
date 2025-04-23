import { Component, OnInit } from '@angular/core';
import { ReportesAdminService } from '../reportes-admin.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-reporte-cliente-mas-citas-atendidas',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './reporte-cliente-mas-citas-atendidas.component.html',
  styleUrls: ['./reporte-cliente-mas-citas-atendidas.component.css'],
})
export class ReporteClienteMasCitasAtendidasComponent implements OnInit {
  empleados: any[] = []; 
  reportes: any[] = []; 
  mensaje: string = '';
  fechaInicio: string = ''; 
  fechaFin: string = ''; 
  idEmpleado: number | null = null; 

  constructor(private reportesAdminService: ReportesAdminService) {}

  ngOnInit(): void {
    this.obtenerEmpleadosActivos();
  }

  // Obtener lista de empleados activos
  obtenerEmpleadosActivos(): void {
    this.reportesAdminService.obtenerEmpleadosActivos({}).subscribe({
      next: (data) => {
        this.empleados = data;
      },
      error: (error: HttpErrorResponse) => {
        console.error('Error al cargar empleados:', error);
        this.mensaje = 'Error al cargar la lista de empleados.';
      },
    });
  }

  // Obtener el reporte con filtros
  obtenerReporte(): void {
    const params: any = {};
    if (this.fechaInicio.trim()) {
      params.fechaInicio = this.fechaInicio;
    }
    if (this.fechaFin.trim()) {
      params.fechaFin = this.fechaFin;
    }
    if (this.idEmpleado !== null) {
      params.idEmpleado = this.idEmpleado;
    }

    this.reportesAdminService.obtenerReporteClientesMasCitasAtendidas(params).subscribe({
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

  // Reiniciar filtros y cargar todos los registros
  reiniciarFiltros(): void {
    this.fechaInicio = '';
    this.fechaFin = '';
    this.idEmpleado = null;
    this.obtenerReporte(); 
  }
}
