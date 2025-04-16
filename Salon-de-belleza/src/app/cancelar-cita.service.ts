import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { RestConstants } from './rest-constants';

@Injectable({
  providedIn: 'root',
})
export class CancelarCitaService {
  private apiUrl: string;

  constructor(private http: HttpClient, private restConstants: RestConstants) {
    this.apiUrl = this.restConstants.getApiURL();
  }

  cancelarCita(token: string, idCita: number): Observable<any> {
    const url = `${this.apiUrl}cancelarCita/cancelar/${idCita}`;
    const headers = new HttpHeaders({
      Authorization: `Bearer ${token}`,
    });

    return this.http.post<any>(url, null, { headers });
  }
}