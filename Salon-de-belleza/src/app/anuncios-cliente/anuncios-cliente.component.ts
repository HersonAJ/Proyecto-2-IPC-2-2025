import { Component, OnInit, OnDestroy } from '@angular/core';
import { AuthService } from '../auth.service';
import { AnunciosClienteService } from '../anuncios-cliente.service';
import { DomSanitizer, SafeResourceUrl } from '@angular/platform-browser';
import { CommonModule } from '@angular/common';
import { Anuncio } from '../anuncios-cliente.service';
import { Subscription } from 'rxjs';
import { Router } from '@angular/router'; 

@Component({
  selector: 'app-anuncios-cliente',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './anuncios-cliente.component.html',
  styleUrls: ['./anuncios-cliente.component.css']
})
export class AnunciosClienteComponent implements OnInit, OnDestroy {

  anuncios: Anuncio[] = []; 
  paginaActual: number = 1; 
  anunciosPorPagina: number = 5; 
  totalAnuncios: number = 0; 
  private loginSubscription: Subscription = new Subscription; 

  constructor(
    private authService: AuthService,
    private anunciosClienteService: AnunciosClienteService,
    private sanitizer: DomSanitizer,
    private router: Router 
  ) {}

  ngOnInit(): void {
    this.loginSubscription = this.authService.loggedIn$.subscribe((isLoggedIn) => {
      if (isLoggedIn) {
        const token = localStorage.getItem('token');
        if (token) {
          this.cargarAnuncios(token); // Cargar anuncios después del login
        }
      }
    });
  }

  ngOnDestroy(): void {
    if (this.loginSubscription) {
      this.loginSubscription.unsubscribe();
    }
  }

  cargarAnuncios(token: string): void {
    this.anunciosClienteService.obtenerAnuncios(token, this.paginaActual, this.anunciosPorPagina).subscribe(
      (response) => {
        this.anuncios = response.anuncios.map((anuncio: Anuncio) => {
          if (anuncio.tipo === 'Video' && typeof anuncio.urlVideo === 'string') {
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
        this.reportarAnunciosMostrados();
      },
      (error) => {
        console.error('Error al cargar anuncios:', error);
      }
    );
  }

  cargarSiguientePagina(): void {
    if ((this.paginaActual * this.anunciosPorPagina) < this.totalAnuncios) {
      this.paginaActual++;
      const token = localStorage.getItem('token');
      if (token) {
        this.cargarAnuncios(token);
        this.reportarAnunciosMostrados(); 
      }
    }
  }

  cargarPaginaAnterior(): void {
    if (this.paginaActual > 1) {
      this.paginaActual--;
      const token = localStorage.getItem('token');
      if (token) {
        this.cargarAnuncios(token);
        this.reportarAnunciosMostrados();
      }
    }
  }

  reportarAnunciosMostrados(): void {
    const urlActual = this.router.url;
    const token = localStorage.getItem('token');
    if (!token) {
      console.error('No se encontró un token de autorización.');
      return;
    }
    this.anuncios.forEach(anuncio => {
      const idAnuncio = anuncio.idAnuncio;
      const urlMostrada = `http://localhost:4200${urlActual}`;
      this.anunciosClienteService.registrarVisualizacion(token, idAnuncio, urlMostrada).subscribe({
        next: () => {},
        error: (err) => console.error(`Error al registrar la visualización del anuncio con ID ${idAnuncio}:`, err)
      });
    });
  }
}
