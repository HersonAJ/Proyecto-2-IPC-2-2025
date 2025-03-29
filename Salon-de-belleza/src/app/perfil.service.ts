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
    this.apiURL= this.restConstants.getApiURL();
  }

  getPerfil(): Observable<any> {
    const url = `${this.apiURL}perfil`;
    const token = localStorage.getItem('token') || "";
    const headers = { 
      'Authorization': `Bearer ${token}`,
      'Content-Type': 'application/json'
    };
    return this.http.get<any>(url, { headers });
  }
}
