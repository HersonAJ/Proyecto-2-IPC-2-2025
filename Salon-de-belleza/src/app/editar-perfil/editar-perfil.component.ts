import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { PerfilService } from '../perfil.service';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-editar-perfil',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './editar-perfil.component.html',
  styleUrls: ['./editar-perfil.component.css']
})
export class EditarPerfilComponent implements OnInit {
  perfil: any = {};

  passwordActual: string = "";

  @Output() onGuardar = new EventEmitter<void>(); 

  constructor(private perfilService: PerfilService, private router: Router) {}

  ngOnInit(): void {
    const token = localStorage.getItem('token');
    if (!token) {
      alert('Debe iniciar sesión para editar su perfil.');
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
        this.passwordActual = data.contraseña;
      },
      error: (err) => {
        console.error('Error al obtener el perfil:', err);
        alert('Error al obtener el perfil');
      }
    });
  }

  actualizarPerfil(): void {

    if(!this.perfil.contraseña || this.perfil.contraseña.trinm() === '' ) {
      this.perfil.contraseña = this.passwordActual;
    }

    this.perfilService.actualizarPerfil(this.perfil).subscribe({
      next: (data) => {
        alert('Perfil actualizado correctamente.');
        this.onGuardar.emit();
      },
      error: (err) => {
        console.error('Error al actualizar perfil:', err);
        alert('Error al actualizar el perfil.');
      }
    });
  }

  onFileChange(event: any): void {
    if (event.target.files && event.target.files.length > 0) {
      const file = event.target.files[0];
      const reader = new FileReader();
      reader.onload = () => {
        const base64String = (reader.result as string).split(',')[1];
        this.perfil.fotoPerfil = base64String;
      };
      reader.readAsDataURL(file);
    }
  }
}
