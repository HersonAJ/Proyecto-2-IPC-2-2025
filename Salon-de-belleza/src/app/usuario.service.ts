import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
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

  // Método para obtener usuarios
  obtenerUsuarios(): Observable<any[]> {
    const url = `${this.apiURL}usuarios`;
    return this.http.get<any[]>(url);
  }
}
