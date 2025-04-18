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
}


