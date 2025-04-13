import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  // Inicializamos con true si ya existe un token, false de lo contrario.
  private loggedInSubject = new BehaviorSubject<boolean>(!!localStorage.getItem('token'));
  public loggedIn$ = this.loggedInSubject.asObservable();


  login(token: string): void {
    localStorage.setItem('token', token);
    this.loggedInSubject.next(true);
  }

  logout(): void {
    localStorage.removeItem('token');
    this.loggedInSubject.next(false);
  }

  // Método para obtener el rol del usuario a partir del token almacenado
  getRolFromToken(token: string): string {
    try {
      // Decodificar el token 
      const payload = JSON.parse(atob(token.split('.')[1]));
      return payload.rol;
    } catch (error) {
      console.error('Error al decodificar el token:', error);
      return ''; // Retorna un valor vacío en caso de error
    }
  }
  // Método para obtener el ID del usuario a partir del token almacenado
  getIdUsuarioFromToken(): number | null {
    try {
      const token = localStorage.getItem('token'); // Obtener el token del localStorage
      if (!token) return null;

      const payload = JSON.parse(atob(token.split('.')[1])); // Decodificar el token
      return payload.idUsuario; // Extraer el ID del usuario
    } catch (error) {
      console.error('Error al decodificar el token:', error);
      return null; // Retorna null en caso de error
    }
  }

}
