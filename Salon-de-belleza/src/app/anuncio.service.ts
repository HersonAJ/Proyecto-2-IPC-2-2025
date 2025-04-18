import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { RestConstants } from './rest-constants';

@Injectable({
  providedIn: 'root',
})
export class AnuncioService {

  constructor(private http: HttpClient, private restConstants: RestConstants) {}

  crearAnuncio(anuncio: any, token: string): Observable<any> {
    const url = this.restConstants.getApiURL() + 'anuncios/crear';
    const headers = new HttpHeaders({
      Authorization: `Bearer ${token}`,
      'Content-Type': 'application/json',
    });
    return this.http.post<any>(url, anuncio, { headers });
  }

  obtenerHobbies(token: string): Observable<any> {
    const url = this.restConstants.getApiURL() + 'anuncios/hobbies';
    const headers = new HttpHeaders({
      Authorization: `Bearer ${token}`,
      'Content-Type': 'application/json',
    });
    return this.http.get<any>(url, { headers });
  }

  // Método para obtener todos los anuncios con token
  obtenerTodosLosAnuncios(token: string): Observable<any> {
    const url = this.restConstants.getApiURL() + 'gestion-anuncios/todos';
    const headers = new HttpHeaders({
      Authorization: `Bearer ${token}`, // Token de autorización
    });
    return this.http.get<any>(url, { headers });
  }

  // Método para actualizar el estado de un anuncio
  actualizarEstadoAnuncio(idAnuncio: number, nuevoEstado: string, token: string): Observable<any> {
    const url = this.restConstants.getApiURL() + `gestion-anuncios/actualizar-estado/${idAnuncio}?estado=${nuevoEstado}`;
    const headers = new HttpHeaders({
      Authorization: `Bearer ${token}`, // Token de autorización
      'Content-Type': 'application/json',
    });
    return this.http.put<any>(url, {}, { headers });
  }

  // Método para reactivar un anuncio
  reactivarAnuncio(idAnuncio: number, token: string): Observable<any> {
    const url = this.restConstants.getApiURL() + `gestion-anuncios/reactivar/${idAnuncio}`;
    const headers = new HttpHeaders({
      Authorization: `Bearer ${token}`, // Token de autorización
      'Content-Type': 'application/json',
    });
    return this.http.put<any>(url, {}, { headers });
  }

  // Método para desactivar un anuncio
  desactivarAnuncio(idAnuncio: number, token: string): Observable<any> {
    const url = this.restConstants.getApiURL() + `gestion-anuncios/desactivar/${idAnuncio}`;
    const headers = new HttpHeaders({
      Authorization: `Bearer ${token}`, // Token de autorización
      'Content-Type': 'application/json',
    });
    return this.http.put<any>(url, {}, { headers });
  }
}