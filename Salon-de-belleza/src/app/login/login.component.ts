import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { RestConstants } from '../rest-constants';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [FormsModule, HttpClientModule],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  protected email: string = "";
  protected password: string = "";

  
  private restConstants: RestConstants = new RestConstants();

  constructor(private http: HttpClient) { }

  login(email: string, password: string): void {
    if (!email.trim() || !password.trim()) {
      alert("Debe ingresar un correo y una contraseña");
    } else {
      const loginPayload = {
        correo: email,
        contrasena: password
      };
  
      const url = this.restConstants.getApiURL() + "login";
  
      this.http.post(url, loginPayload).subscribe({
        next: (response: any) => {
          // Verifica si el mensaje del backend indica un usuario válido
          if (response && response.mensaje === "Usuario válido") {
            alert("Bienvenido!");
          } else {
            alert("Respuesta inesperada");
          }
        },
        error: (error) => {
          // Manejo de errores específicos
          if (error.status === 401) {
            // Cuando el backend devuelve 401, indica credenciales inválidas
            alert("Credenciales incorrectas");
          } else {
            // Otros errores (conexión, timeout, etc.)
            alert("Error en la conexión con el servidor");
          }
        }
      });
    }
  }
  
  
}
