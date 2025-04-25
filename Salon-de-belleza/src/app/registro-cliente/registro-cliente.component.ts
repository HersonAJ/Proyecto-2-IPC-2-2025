import { Component } from '@angular/core';
import { UsuarioService } from '../usuario.service';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-registro-cliente',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './registro-cliente.component.html',
  styleUrls: ['./registro-cliente.component.css']
})
export class RegistroClienteComponent {
  protected nombre: string = '';
  protected correo: string = '';
  protected contrasena: string = '';
  protected telefono: string = '';
  protected direccion: string = '';
  protected dpi: string = '';
  protected hobbies: string = '';      
  protected descripcion: string = '';

  protected fotoPerfil?: File;

  constructor(private usuarioService: UsuarioService) {}

  onFileChange(event: any): void {
    this.fotoPerfil = event.target.files[0];
  }

  registrarCliente(): void {
    if (!this.nombre.trim() || !this.dpi.trim() || !this.telefono.trim() ||
        !this.correo.trim() || !this.contrasena.trim()) {
      alert('Por favor, completa todos los campos obligatorios.');
    } else {
      const formData = new FormData();
      formData.append('nombre', this.nombre);
      formData.append('correo', this.correo);
      formData.append('contrasena', this.contrasena);
      formData.append('telefono', this.telefono);
      formData.append('direccion', this.direccion);
      formData.append('dpi', this.dpi);
      formData.append('rol', 'Cliente');
      formData.append('estado', 'Activo'); 
      formData.append('Hobbies', this.hobbies);
      formData.append('Descripcion', this.descripcion);
      
      if(this.fotoPerfil) {
        formData.append('Foto_Perfil', this.fotoPerfil);
      }

      this.usuarioService.registrarUsuario(formData).subscribe({
        next: () => {
          alert('Cliente registrado exitosamente.');
          this.limpiarFormulario();
        },
        error: () => {
          alert('Error al registrar al cliente.');
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
    this.hobbies = '';
    this.descripcion = '';
    this.fotoPerfil = undefined;
  }

  //metodo que valida la entrada correcta en los numeros de telefono
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
