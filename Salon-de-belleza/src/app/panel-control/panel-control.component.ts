import { Component, OnInit, OnDestroy } from '@angular/core';
import { AuthService } from '../auth.service';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-panel-control',
  standalone: true,
  imports: [RouterModule, CommonModule],
  templateUrl: './panel-control.component.html',
  styleUrls: ['./panel-control.component.css']
})
export class PanelControlComponent implements OnInit, OnDestroy {

  rolUsuario: string = "";
  isLoggedIn: boolean = false; // Estado de sesión
  private authSubscription!: Subscription; 

  constructor(private authService: AuthService) {}

  ngOnInit(): void {

    this.authSubscription = this.authService.loggedIn$.subscribe((isLoggedIn) => {
      this.isLoggedIn = isLoggedIn; // Actualiza el estado de sesión
      if (isLoggedIn) {
        // Si la sesión está activa, obtener el rol del usuario
        const token = localStorage.getItem('token');
        if (token) {
          this.rolUsuario = this.authService.getRolFromToken(token);
        }
      } else {
        // Si la sesión está cerrada, reiniciar rolUsuario
        this.rolUsuario = "";
      }
    });
  }

  ngOnDestroy(): void {
    // Cancelar la suscripción
    if (this.authSubscription) {
      this.authSubscription.unsubscribe();
    }
  }
}
