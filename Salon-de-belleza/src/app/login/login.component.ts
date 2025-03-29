import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { UsuarioService } from '../usuario.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  protected email: string = "";
  protected password: string = "";

  constructor(private usuarioService: UsuarioService) { }

  login(): void {
    if (!this.email.trim() || !this.password.trim()) {
      alert("Debe ingresar un correo y una contraseña");
    } else {
      const loginPayload = {
        correo: this.email,
        contrasena: this.password
      };

      this.usuarioService.login(loginPayload).subscribe({
        next: (response: any) => {
          if (response && response.mensaje === "Usuario válido") {
            if(response.token) {
              // Almacenar el token el local starage
              localStorage.setItem('token', response.token);
            }
            alert("Bienvenido!");
          } else {
            alert("Respuesta inesperada");
          }
        },
        error: (error) => {
          if (error.status === 401) {
            alert("Credenciales incorrectas");
          } else {
            alert("Error en la conexión con el servidor");
          }
        }
      });
    }
  }
}
