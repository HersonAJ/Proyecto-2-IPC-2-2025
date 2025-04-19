import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { RestConstants } from './rest-constants';
import { DomSanitizer, SafeResourceUrl } from '@angular/platform-browser';


export interface Anuncio {
  idAnuncio: number;
  nombreAnunciante: string;
  contactoAnunciante: string;
  tipo: 'Texto' | 'Imagen' | 'Video';
  contenidoTexto?: string;
  urlImagen?: string;
  urlVideo?: string | SafeResourceUrl; // Permitir URLs sanitizadas
  precioPorDia: number;
  duracionDias: number;
  fechaInicio: Date;
  fechaFin: Date;
}


@Injectable({
  providedIn: 'root'
})
export class AnunciosClienteService {
  private readonly apiURL: string; 

  constructor(private http: HttpClient, private restConstants: RestConstants) {
    this.apiURL = this.restConstants.getApiURL(); 
  }

  // Método para obtener anuncios paginados según los hobbies del usuario
  obtenerAnuncios(token: string, page: number, pageSize: number): Observable<any> {
    const url = `${this.apiURL}anunciosParaClientes/obtener`; 
    const headers = new HttpHeaders({ 'Authorization': `Bearer ${token}` });
    const params = { page: page.toString(), pageSize: pageSize.toString() };
    return this.http.get<any>(url, { headers, params });
  }

  registrarVisualizacion(token: string, idAnuncio: number, urlMostrada: string): Observable<any> {
    const url = `${this.apiURL}historial-anuncios/registrar`;
    const headers = new HttpHeaders({ 'Authorization': `Bearer ${token}` });
    const body = {
      idAnuncio: idAnuncio,
      urlMostrada: urlMostrada
    };
    return this.http.post<any>(url, body, { headers });
  }
}