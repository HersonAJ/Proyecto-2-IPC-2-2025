import { Component, OnInit } from '@angular/core';
import { PerfilService } from '../perfil.service';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { EditarPerfilComponent } from '../editar-perfil/editar-perfil.component';

@Component({
  selector: 'app-mi-perfil',
  standalone: true,
  imports: [CommonModule, EditarPerfilComponent],
  templateUrl: './mi-perfil.component.html',
  styleUrls: ['./mi-perfil.component.css']
})
export class MiPerfilComponent implements OnInit {
  perfil: any;
  mostrarEditar: boolean = false;

  constructor(private perfilService: PerfilService, private router: Router) { }

  ngOnInit(): void {
    const token = localStorage.getItem('token');
    if (!token) {
      alert('Debe iniciar sesión para ver su perfil.');
      this.router.navigate(['/login']);
    } else {
      this.obtenerPerfil();
    }
  }

  obtenerPerfil(): void {
    this.perfilService.getPerfil().subscribe({
      next: (data) => {
        console.log('Perfil recibido:', data);
        this.perfil = data;
      },
      error: (err) => {
        console.error('Error al obtener el perfil:', err);
        alert('Error al obtener el perfil');
      }
    });
  }
  habilitarEdicion(): void {
    this.mostrarEditar = true;
  }

  finalizarEdicion(): void {
    this.mostrarEditar = false;
    this.obtenerPerfil();
  }
}
