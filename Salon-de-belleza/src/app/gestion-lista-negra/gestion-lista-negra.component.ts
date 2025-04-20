import { Component, OnInit } from '@angular/core';
import { GestionListaNegraService, ListaNegra } from '../gestion-lista-negra.service';
import { AuthService } from '../auth.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-gestion-lista-negra',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './gestion-lista-negra.component.html',
  styleUrls: ['./gestion-lista-negra.component.css'],
})
export class GestionListaNegraComponent implements OnInit {
  listaNegra: ListaNegra[] = []; 
  mensaje: string = ''; 
  cargando: boolean = true; 
  token: string = ''; 

  constructor(
    private gestionListaNegraService: GestionListaNegraService,
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    this.token = localStorage.getItem('token') || '';
    if (this.token) {
      this.cargarListaNegra();
    } else {
      this.mensaje = 'Error: No se encontró un token válido.';
      this.cargando = false;
    }
  }

  // Cargar los clientes en la lista negra desde el servicio
  cargarListaNegra(): void {
    this.cargando = true;
    this.gestionListaNegraService.obtenerClientesEnListaNegra(this.token).subscribe({
      next: (clientes: ListaNegra[]) => {
        this.listaNegra = clientes;
        this.mensaje = ''; // Limpia cualquier mensaje previo
        this.cargando = false;
      },
      error: (err) => {
        if (err.status === 404 && err.error?.message) {
          this.listaNegra = [];
          this.mensaje = err.error.message;
        } else {
          this.mensaje = 'Error al cargar la lista negra. Inténtelo nuevamente.';
          console.error(err);
        }
        this.cargando = false;
      },
    });
  }
  
  
  // Cambiar el estado de un cliente en la lista negra
  sacarDeListaNegra(idLista: number): void {
    const confirmacion = window.confirm(
      '¿Está seguro de que desea sacar a este cliente de la lista negra?'
    );
    if (!confirmacion) return;

    this.gestionListaNegraService.cambiarEstadoListaNegra(this.token, idLista, 'Permitido').subscribe({
      next: () => {
        this.mensaje = 'El cliente fue removido de la lista negra exitosamente.';
        this.cargarListaNegra(); 
      },
      error: (err) => {
        this.mensaje = 'Error al cambiar el estado del cliente. Inténtelo nuevamente.';
        console.error(err);
      },
    });
  }
}
