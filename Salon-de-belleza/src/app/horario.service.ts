import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { RestConstants } from './rest-constants';

@Injectable({
  providedIn: 'root',
})
export class HorarioService {
  private apiUrl: string;

  constructor(private http: HttpClient, private restConstants: RestConstants) {
    
    this.apiUrl = `${this.restConstants.getApiURL()}horarios/general`;
  }

  // Método para obtener el horario general
  obtenerHorarioGeneral(): Observable<any> {
    return this.http.get(this.apiUrl);
  }

  // Método para establecer o actualizar el horario general
  establecerHorarioGeneral(horarios: any[]): Observable<any> {
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    return this.http.put(this.apiUrl, horarios, { headers });
  }
}
