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

  constructor(private servicioService: ServiciosService) {}

  ngOnInit(): void {
    this.cargarServicios();
  }

  cargarServicios(): void {
    this.servicioService.obtenerServicios().subscribe({
      next: (data) => {
        this.servicios = data.map((servicio) => {
          // Verificar si el prefijo ya está presente
          const imagenBase64 = servicio.imagen.startsWith('data:image/jpeg;base64,') 
            ? servicio.imagen 
            : `data:image/jpeg;base64,${servicio.imagen}`;
  
          return {
            ...servicio,
            imagen: imagenBase64, // Usar la imagen
          };
        });
      },
      error: (err) => {
        console.error('Error al cargar los servicios:', err);
      }
    });
  }
  
}
