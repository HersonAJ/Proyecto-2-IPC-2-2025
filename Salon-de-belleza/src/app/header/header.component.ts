import { Component, OnInit, OnDestroy } from '@angular/core';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { AuthService } from '../auth.service';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit, OnDestroy {
  isLoggedIn: boolean = false;
  private authSubscription!: Subscription;

  constructor(private router: Router, private authService: AuthService) {}

  ngOnInit(): void {
    // Se suscribe al observable del AuthService para actualizar el estado en tiempo real
    this.authSubscription = this.authService.loggedIn$.subscribe(
      (state) => {
        this.isLoggedIn = state;
      }
    );
  }

  logout(): void {
    // Llama al método logout del AuthService, lo que actualiza el estado y elimina el token
    this.authService.logout();
    // Redirige a la página de login
    this.router.navigate(['/login']);
  }

  ngOnDestroy(): void {
    // Cancelar la suscripción para evitar fugas de memoria
    if (this.authSubscription) {
      this.authSubscription.unsubscribe();
    }
  }
}
