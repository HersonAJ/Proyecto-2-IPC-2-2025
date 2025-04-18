import { Component, OnInit } from '@angular/core';
import { AuthService } from '../auth.service';
import { AnunciosClienteService } from '../anuncios-cliente.service';
import { DomSanitizer, SafeResourceUrl } from '@angular/platform-browser';
import { CommonModule } from '@angular/common';
import { Anuncio } from '../anuncios-cliente.service';

@Component({
  selector: 'app-anuncios-cliente',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './anuncios-cliente.component.html',
  styleUrls: ['./anuncios-cliente.component.css']
})
export class AnunciosClienteComponent implements OnInit {

  anuncios: Anuncio[] = []; // Lista de anuncios para mostrar
  paginaActual: number = 1; // Página actual
  anunciosPorPagina: number = 5; // Número de anuncios por página
  totalAnuncios: number = 0; // Total de anuncios disponibles

  constructor(
    private authService: AuthService,
    private anunciosClienteService: AnunciosClienteService,
    private sanitizer: DomSanitizer 
  ) {}

  ngOnInit(): void {
    this.cargarAnuncios(); 
  }

  cargarAnuncios(): void {
    const token = localStorage.getItem('token'); 
  
    if (!token) {
      console.error('No se encontró el token.');
      return;
    }
  
    this.anunciosClienteService.obtenerAnuncios(token, this.paginaActual, this.anunciosPorPagina).subscribe(
      (response) => {
        this.anuncios = response.anuncios.map((anuncio: Anuncio) => {
          if (anuncio.tipo === 'Video' && typeof anuncio.urlVideo === 'string') {
            // Extraer ID del video desde diferentes formatos de URL
            const videoIdMatch = anuncio.urlVideo.match(/(?:youtube\.com\/(?:live|watch)\?v=|youtu\.be\/|youtube\.com\/live\/)([\w-]+)/);
            const videoId = videoIdMatch ? videoIdMatch[1] : null;
  
            if (videoId) {
              const embedUrl = `https://www.youtube.com/embed/${videoId}`;
              anuncio.urlVideo = this.sanitizer.bypassSecurityTrustResourceUrl(embedUrl) as SafeResourceUrl;
            } else {
              console.warn('No se pudo extraer el ID del video para embebido:', anuncio.urlVideo);
              anuncio.urlVideo = undefined;
            }
          }
          return anuncio;
        });
        this.totalAnuncios = response.totalAnuncios; 
      },
      (error) => {
        console.error('Error al cargar anuncios:', error);
      }
    );
  }
  
  cargarSiguientePagina(): void {
    if ((this.paginaActual * this.anunciosPorPagina) < this.totalAnuncios) {
      this.paginaActual++;
      this.cargarAnuncios(); // Solicitar la siguiente página de anuncios
    }
  }

  cargarPaginaAnterior(): void {
    if (this.paginaActual > 1) {
      this.paginaActual--;
      this.cargarAnuncios(); // Solicitar la página anterior de anuncios
    }
  }
}
