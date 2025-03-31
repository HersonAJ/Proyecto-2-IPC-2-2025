import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { RestConstants } from './rest-constants';

@Injectable({
  providedIn: 'root'
})
export class UsuarioService {
  private apiURL: string;

  constructor(private http: HttpClient, private restConstants: RestConstants) {
    this.apiURL = this.restConstants.getApiURL();
  }

  // Método para registrar un usuario
  registrarUsuario(usuario: any): Observable<any> {
    const url = `${this.apiURL}usuarios`;
    return this.http.post<any>(url, usuario);
  }


  //metodo para el login 
  login(credenciales: any): Observable<any> {
    const url = `${this.apiURL}login`;
    return this.http.post<any>(url, credenciales);
  }
  // Método para obtener usuarios (excluyendo los clientes)
  obtenerUsuarios(): Observable<any[]> {
    const url = `${this.apiURL}gestion-usuarios/gestion`;
    const token = localStorage.getItem('token'); // Recupera el token del localStorage
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.http.get<any[]>(url, { headers });
  }

  modificarEstadoUsuario(idUsuario: number, nuevoEstado: string): Observable<any> {
    const url = `${this.apiURL}gestion-usuarios/estado/${idUsuario}`;
    const token = localStorage.getItem('token'); // Recupera el token del localStorage
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);

    return this.http.put(url, { estado: nuevoEstado }, { headers });
  }
}
