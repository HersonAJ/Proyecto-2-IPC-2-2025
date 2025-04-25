import { Component } from '@angular/core';
import { UsuarioService } from '../usuario.service';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-registrar-usuario',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './registrar-usuario.component.html',
  styleUrls: ['./registrar-usuario.component.css']
})
export class RegistrarUsuarioComponent {
  nombre: string = '';
  correo: string = '';
  contrasena: string = '';
  telefono: string = '';
  direccion: string = '';
  dpi: string = '';
  rol: string = ''; 
  descripcion: string = '';
  fotoPerfil?: File;

  constructor(private usuarioService: UsuarioService) {}

  onFileChange(event: any): void {
    this.fotoPerfil = event.target.files[0];
  }

  registrarUsuario(): void {
    if (!this.nombre.trim() || !this.dpi.trim() || !this.telefono.trim() ||
        !this.correo.trim() || !this.contrasena.trim() || !this.rol) {
      alert('Por favor, completa todos los campos obligatorios.');
    } else {
      const formData = new FormData();
      formData.append('nombre', this.nombre);
      formData.append('correo', this.correo);
      formData.append('contrasena', this.contrasena);
      formData.append('telefono', this.telefono);
      formData.append('direccion', this.direccion);
      formData.append('dpi', this.dpi);
      formData.append('rol', this.rol); 
      formData.append('estado', 'Activo'); 
      formData.append('descripcion', this.descripcion);
      
      if (this.fotoPerfil) {
        formData.append('Foto_Perfil', this.fotoPerfil);
      }

      this.usuarioService.registrarUsuario(formData).subscribe({
        next: () => {
          alert('Usuario registrado exitosamente.');
          this.limpiarFormulario();
        },
        error: () => {
          alert('Error al registrar el usuario.');
        }
      });
    }
  }

  limpiarFormulario(): void {
    this.nombre = '';
    this.correo = '';
    this.contrasena = '';
    this.telefono = '';
    this.direccion = '';
    this.dpi = '';
    this.rol = '';
    this.descripcion = '';
    this.fotoPerfil = undefined;
  }

  restrictInput(event: KeyboardEvent): boolean {
    const allowedKeys = ['Backspace', 'ArrowLeft', 'ArrowRight', 'Delete', 'Tab'];
    if (allowedKeys.indexOf(event.key) !== -1) {
      return true;
    }

    const inputElement = event.target as HTMLInputElement;
    const key = event.key;

    if (/^\d$/.test(key)) {
      return true;
    }

    if (key === '+') {
      if (inputElement.selectionStart === 0 && inputElement.value.indexOf('+') === -1) {
        return true;
      }
    }

    event.preventDefault();
    return false;
  }
}
