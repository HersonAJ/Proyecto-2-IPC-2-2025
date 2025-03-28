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
      alert("debe de ingresar un correo y una contraseña");
    } else {
      // Crea el payload (el objeto que será enviado como JSON)
      const loginPayload = {
        correo: email,
        contrasena: password
      };

      // Construye la URL usando las constantes
      const url = this.restConstants.getApiURL() + "login";

      // Realiza la petición POST al backend
      this.http.post(url, loginPayload).subscribe({
        next: (response: any) => {
          // espera un json
          if (response && response.mensaje === "bienvenido") {
            alert("Bienvenido!");
          } else {
            alert("Respuesta inesperada");
          }
        },
        error: (error) => {
          // En caso de error
          alert("Credenciales no validas");
        }
      });
    }
  }
}
