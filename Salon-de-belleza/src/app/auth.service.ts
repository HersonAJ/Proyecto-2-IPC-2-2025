import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  // Inicializamos con true si ya existe un token, false de lo contrario.
  private loggedInSubject = new BehaviorSubject<boolean>(!!localStorage.getItem('token'));
  public loggedIn$ = this.loggedInSubject.asObservable();


  login(token: string): void {
    localStorage.setItem('token', token);
    this.loggedInSubject.next(true);
  }

  logout(): void {
    localStorage.removeItem('token');
    this.loggedInSubject.next(false);
  }
}
