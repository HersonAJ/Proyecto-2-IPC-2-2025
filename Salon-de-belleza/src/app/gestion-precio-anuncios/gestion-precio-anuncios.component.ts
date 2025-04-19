import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { GestionarPrecioAnunciosService } from '../gestionar-precio-anuncios.service';
import { AuthService } from '../auth.service'; 

@Component({
  selector: 'app-gestion-precio-anuncios',
  standalone: true,
  imports: [CommonModule, FormsModule], 
  templateUrl: './gestion-precio-anuncios.component.html',
  styleUrls: ['./gestion-precio-anuncios.component.css']
})
export class GestionPrecioAnunciosComponent implements OnInit {
  precios: any[] = []; 
  mensaje: string = ''; 

  constructor(
    private gestionarPrecioAnunciosService: GestionarPrecioAnunciosService,
    private authService: AuthService 
  ) {}

  ngOnInit(): void {
    this.obtenerTodosLosPrecios();
  }

  // Método para obtener todos los precios
  obtenerTodosLosPrecios(): void {
    const token = localStorage.getItem('token');
    if (token) {
      this.gestionarPrecioAnunciosService.obtenerTodosLosPrecios(token).subscribe({
        next: (data) => {
          this.precios = data;
        },
        error: (err) => {
          console.error('Error al obtener los precios:', err);
          this.mensaje = 'Error al cargar los precios.';
        }
      });
    } else {
      this.mensaje = 'No se encontró un token válido. Inicia sesión.';
    }
  }

  // Método para actualizar el precio de un tipo de anuncio
  actualizarPrecio(tipo: string, nuevoPrecio: number): void {
    const token = localStorage.getItem('token');
    if (!token) {
      this.mensaje = 'No se encontró un token válido. Inicia sesión.';
      return;
    }
    if (nuevoPrecio <= 0) {
      this.mensaje = 'El precio debe ser mayor a 0.';
      return;
    }
    this.gestionarPrecioAnunciosService.actualizarPrecio(token, tipo, nuevoPrecio).subscribe({
      next: (res) => {
        this.mensaje = res.message;
        this.obtenerTodosLosPrecios();
      },
      error: (err) => {
        console.error('Error al actualizar el precio:', err);
        this.mensaje = 'Error al actualizar el precio.';
      }
    });
  }
}
