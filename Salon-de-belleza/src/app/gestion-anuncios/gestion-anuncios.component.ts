import { Component, OnInit } from '@angular/core';
import { AnuncioService } from '../anuncio.service';
import { AuthService } from '../auth.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-gestion-anuncios',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './gestion-anuncios.component.html',
  styleUrls: ['./gestion-anuncios.component.css']
})
export class GestionAnunciosComponent implements OnInit {
  anuncios: any[] = []; // Lista de anuncios
  mensaje: string = ''; // Mensaje de éxito o error
  cargando: boolean = false; // Estado de carga para mostrar indicadores

  private token: string = ''; // Token para autenticación
  private idUsuario: number | null = null; // ID del usuario que gestiona los anuncios

  constructor(private anuncioService: AnuncioService, private authService: AuthService) {}

  ngOnInit(): void {
    // Obtener el token y el ID del usuario desde el AuthService
    this.token = localStorage.getItem('token') || ''; 
    this.idUsuario = this.authService.getIdUsuarioFromToken();

    // Cargar la lista de anuncios al inicializar el componente
    this.obtenerTodosLosAnuncios();
  }

  // Método para obtener todos los anuncios
  obtenerTodosLosAnuncios(): void {
    this.cargando = true;
    this.anuncioService.obtenerTodosLosAnuncios(this.token).subscribe(
      (response) => {
        this.anuncios = response; // Asignar la lista de anuncios
        this.cargando = false;
      },
      (error) => {
        this.mensaje = 'Error al obtener los anuncios.';
        console.error(error);
        this.cargando = false;
      }
    );
  }

  // Método para reactivar un anuncio
  reactivarAnuncio(idAnuncio: number): void {
    this.cargando = true;
    this.anuncioService.reactivarAnuncio(idAnuncio, this.token).subscribe(
      (response) => {
        this.mensaje = 'Anuncio reactivado correctamente.';
        this.obtenerTodosLosAnuncios(); // Refrescar la lista de anuncios
        this.cargando = false;
      },
      (error) => {
        this.mensaje = 'Error al reactivar el anuncio.';
        console.error(error);
        this.cargando = false;
      }
    );
  }

  // Método para desactivar un anuncio
  desactivarAnuncio(idAnuncio: number): void {
    this.cargando = true;
    this.anuncioService.desactivarAnuncio(idAnuncio, this.token).subscribe(
      (response) => {
        this.mensaje = 'Anuncio desactivado correctamente.';
        this.obtenerTodosLosAnuncios(); // Refrescar la lista de anuncios
        this.cargando = false;
      },
      (error) => {
        this.mensaje = 'Error al desactivar el anuncio.';
        console.error(error);
        this.cargando = false;
      }
    );
  }

  // Método para actualizar el estado de un anuncio
  actualizarEstadoAnuncio(idAnuncio: number, nuevoEstado: string): void {
    this.cargando = true;
    this.anuncioService.actualizarEstadoAnuncio(idAnuncio, nuevoEstado, this.token).subscribe(
      (response) => {
        this.mensaje = 'Estado del anuncio actualizado correctamente.';
        this.obtenerTodosLosAnuncios(); // Refrescar la lista de anuncios
        this.cargando = false;
      },
      (error) => {
        this.mensaje = 'Error al actualizar el estado del anuncio.';
        console.error(error);
        this.cargando = false;
      }
    );
  }


  getEstadoClase(estado: string): string {
    switch (estado) {
      case 'Activo':
        return 'badge bg-success';
      case 'Inactivo':
        return 'badge bg-secondary';
      case 'Caducado':
        return 'badge bg-danger';
      default:
        return 'badge bg-light';
    }
  }
  
}
