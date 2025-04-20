import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ServiciosService } from '../servicios.service';

@Component({
  selector: 'app-inicio',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './inicio.component.html',
  styleUrls: ['./inicio.component.css']
})
export class InicioComponent implements OnInit {
  servicios: any[] = []; // Almacena los servicios recuperados
  servicioSeleccionado: any = null;

  constructor(private servicioService: ServiciosService) {}

  ngOnInit(): void {
    this.cargarServicios();
  }

  cargarServicios(): void {
    this.servicioService.obtenerServicios().subscribe({
      next: (data) => {
        this.servicios = data.map((servicio) => {
          // Verificar si el prefijo ya está presente para imagen
          const imagenBase64 = servicio.imagen?.startsWith('data:image/jpeg;base64,') 
            ? servicio.imagen 
            : `data:image/jpeg;base64,${servicio.imagen}`;
  
          // Verificar si el servicio tiene un PDF antes de procesarlo
          const pdfBase64 = servicio.catalogoPdf 
            ? (servicio.catalogoPdf.startsWith('data:application/pdf;base64,') 
              ? servicio.catalogoPdf 
              : `data:application/pdf;base64,${servicio.catalogoPdf}`)
            : null;
          return {
            ...servicio,
            imagen: imagenBase64,
            catalogoPdf: pdfBase64 
          };
        });
      },
      error: (err) => {
        console.error('Error al cargar los servicios:', err);
      }
    });
  }
  
  abrirServicio(servicio: any): void {
    this.servicioSeleccionado = servicio; 
  }

  cerrarServicio(): void {
    this.servicioSeleccionado = null; 
  }

  visualizarPdf(catalogoPdf: string): void {
    if (catalogoPdf) {
      const pdfWindow = window.open('');
      if (pdfWindow) {
        pdfWindow.document.write(
          `<iframe 
            src="${catalogoPdf}" 
            width="100%" 
            height="100%" 
            style="border:none;"></iframe>`
        );
      }
    } else {
      alert('Este servicio no tiene un catálogo PDF para visualizar.');
    }
  }
  
}
