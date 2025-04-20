import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { RestConstants } from './rest-constants';

export interface ListaNegra {
  idLista: number;
  idCliente: number;
  idCita: number;
  motivo: string;
  estado: string;
  nombreCliente: string;
}

@Injectable({
  providedIn: 'root',
})
export class GestionListaNegraService {
  private readonly apiURL: string;

  constructor(private http: HttpClient, private restConstants: RestConstants) {
    this.apiURL = this.restConstants.getApiURL();
  }

  // Obtener todos los clientes en la lista negra
  obtenerClientesEnListaNegra(token: string): Observable<ListaNegra[]> {
    const url = `${this.apiURL}lista-negra/clientes-en-lista`;
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.http.get<ListaNegra[]>(url, { headers });
  }

  // Cambiar el estado de un cliente en la lista negra
  cambiarEstadoListaNegra(token: string, idLista: number, nuevoEstado: string): Observable<any> {
    const url = `${this.apiURL}lista-negra/cambiar-estado/${idLista}/${nuevoEstado}`;
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.http.put(url, null, { headers });
  }
}
