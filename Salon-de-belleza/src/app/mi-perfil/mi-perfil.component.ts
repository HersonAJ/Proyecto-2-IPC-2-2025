import { Component, OnInit } from '@angular/core';
import { PerfilService } from '../perfil.service';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';

@Component({
  selector: 'app-mi-perfil',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './mi-perfil.component.html',
  styleUrls: ['./mi-perfil.component.css']
})
export class MiPerfilComponent implements OnInit {
  // Objeto que contendrá la información del perfil del usuario
  perfil: any;

  constructor(private perfilService: PerfilService, private router: Router) { }

  ngOnInit(): void {
    // Verifica si existe un token en el localStorage
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
}
