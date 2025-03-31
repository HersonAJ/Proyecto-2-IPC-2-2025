import { Component, OnInit } from '@angular/core';
import { UsuarioService } from '../usuario.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-gestion-usuarios',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './gestion-usuarios.component.html',
  styleUrl: './gestion-usuarios.component.css'
})
export class GestionUsuariosComponent implements OnInit {
  usuarios: any[] = [];
  loading: boolean = true;

  constructor(private usuarioService: UsuarioService) { }

  ngOnInit(): void {
    this.cargarUsuarios();
  }

  cargarUsuarios(): void {
    this.usuarioService.obtenerUsuarios().subscribe({
      next: (data) => {
        this.usuarios = data;
        this.loading = false;
      },
      error: (err) => {
        console.error('Error al cargar los usuarios:', err);
        alert('No se pudo cargar la lista de usuarios.');
        this.loading = false;
      }
    });
  }
  cambiarEstado(usuario: any): void {
    const nuevoEstado = usuario.estado === 'Activo' ? 'Inactivo' : 'Activo';
    this.usuarioService.modificarEstadoUsuario(usuario.idUsuario, nuevoEstado).subscribe({
      next: (response) => {
        usuario.estado = nuevoEstado; // Actualizar el estado en el frontend
        alert(response.message || `El estado del usuario se ha cambiado a ${nuevoEstado}.`);
      },
      error: (err) => {
        console.error('Error al cambiar el estado:', err);
        alert('No se pudo cambiar el estado del usuario.');
      }
    });
  }

}
