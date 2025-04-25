import { Component, OnInit } from '@angular/core';
import { AnuncioService } from '../anuncio.service';
import { AuthService } from '../auth.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-gestion-anuncios',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './gestion-anuncios.component.html',
  styleUrls: ['./gestion-anuncios.component.css']
})
export class GestionAnunciosComponent implements OnInit {
  anuncios: any[] = [];           
  anunciosFiltrados: any[] = [];     
  selectedEstado: string = 'Todos';  
  mensaje: string = '';   
  cargando: boolean = false; 

  private token: string = '';
  private idUsuario: number | null = null; 

  constructor(private anuncioService: AnuncioService, private authService: AuthService) {}

  ngOnInit(): void {
    this.token = localStorage.getItem('token') || ''; 
    this.idUsuario = this.authService.getIdUsuarioFromToken();
    this.obtenerTodosLosAnuncios();
  }

  // Método para obtener todos los anuncios
  obtenerTodosLosAnuncios(): void {
    this.cargando = true;
    this.anuncioService.obtenerTodosLosAnuncios(this.token).subscribe({
      next: (response) => {
        this.anuncios = response;
        this.anunciosFiltrados = response;
        this.cargando = false;
      },
      error: (error) => {
        this.mensaje = 'Error al obtener los anuncios.';
        console.error(error);
        this.cargando = false;
      },
    });
  }  

  onEstadoChange(): void {
    if (this.selectedEstado === 'Todos') {
      this.anunciosFiltrados = this.anuncios;
    } else {
      this.anunciosFiltrados = this.anuncios.filter(anuncio => anuncio.estado === this.selectedEstado);
    }
  }


  reactivarAnuncio(idAnuncio: number): void {
    this.cargando = true;
    this.anuncioService.reactivarAnuncio(idAnuncio, this.token).subscribe({
      next: (response) => {
        this.mensaje = 'Anuncio reactivado correctamente.';
        this.obtenerTodosLosAnuncios();
        this.cargando = false;
      },
      error: (error) => {
        this.mensaje = 'Error al reactivar el anuncio.';
        console.error(error);
        this.cargando = false;
      },
      complete: () => {}
    });
  }
  

  desactivarAnuncio(idAnuncio: number): void {
    this.cargando = true;
    this.anuncioService.desactivarAnuncio(idAnuncio, this.token).subscribe({
      next: (response) => {
        this.mensaje = 'Anuncio desactivado correctamente.';
        this.obtenerTodosLosAnuncios(); 
        this.cargando = false;
      },
      error: (error) => {
        this.mensaje = 'Error al desactivar el anuncio.';
        console.error(error);
        this.cargando = false;
      }
  });
  }

  actualizarEstadoAnuncio(idAnuncio: number, nuevoEstado: string): void {
    this.cargando = true;
    this.anuncioService.actualizarEstadoAnuncio(idAnuncio, nuevoEstado, this.token).subscribe({
      next: (response) => {
        this.mensaje = 'Estado del anuncio actualizado correctamente.';
        this.obtenerTodosLosAnuncios(); 
        this.cargando = false;
      },
      error:(error) => {
        this.mensaje = 'Error al actualizar el estado del anuncio.';
        console.error(error);
        this.cargando = false;
      }
  });
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
