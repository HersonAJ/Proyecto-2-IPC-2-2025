import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { RestConstants } from './rest-constants';

@Injectable({
  providedIn: 'root'
})
export class PerfilService {
  private apiURL: string;

  constructor(private http: HttpClient, private restConstants: RestConstants) { 
    this.apiURL = this.restConstants.getApiURL();
  }

  // Método para obtener el perfil
  getPerfil(): Observable<any> {
    const url = `${this.apiURL}perfil`;
    const token = localStorage.getItem('token') || "";
    const headers = { 
      'Authorization': `Bearer ${token}`,
      'Content-Type': 'application/json'
    };
    return this.http.get<any>(url, { headers });
  }

  // Método para actualizar el perfil
  actualizarPerfil(usuario: any): Observable<any> {
    const url = `${this.apiURL}editar-perfil`;
    const token = localStorage.getItem('token') || "";
    const headers = { 
      'Authorization': `Bearer ${token}`,
      'Content-Type': 'application/json'
    };
    return this.http.put<any>(url, usuario, { headers });
  }
}
