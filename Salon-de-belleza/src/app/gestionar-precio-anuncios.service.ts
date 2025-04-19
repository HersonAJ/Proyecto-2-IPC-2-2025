import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { RestConstants } from './rest-constants';

@Injectable({
  providedIn: 'root'
})
export class GestionarPrecioAnunciosService {

  private readonly apiUrl: string;

  constructor(private http: HttpClient, private restConstants: RestConstants) {
    this.apiUrl = this.restConstants.getApiURL(); 
  }

  // Método para obtener todos los precios de los tipos de anuncios
  obtenerTodosLosPrecios(token: string): Observable<any> {
    const url = `${this.apiUrl}gestion-precio-anuncios/obtener-todos-precios`;
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
    return this.http.get(url, { headers });
  }

  // Método para actualizar el precio de un tipo de anuncio
  actualizarPrecio(token: string, tipo: string, precioPorDia: number): Observable<any> {
    const url = `${this.apiUrl}gestion-precio-anuncios/actualizar-precio`;
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`,
      'Content-Type': 'application/json'
    });
    const body = {
      tipo: tipo,
      precioPorDia: precioPorDia
    };
    return this.http.put(url, body, { headers });
  }
}
