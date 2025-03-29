import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {
  isLoggedIn: boolean = false;

  constructor(private router: Router) {}

  ngOnInit(): void {
    // Verifica si ya existe un token en el localStorage
    this.isLoggedIn = !!localStorage.getItem('token');
  }

  logout(): void {
    // Remueve el token y actualiza el estado
    localStorage.removeItem('token');
    this.isLoggedIn = false;
    // Opcional: redirigir al login u otra página
    this.router.navigate(['/login']);
  }
}
