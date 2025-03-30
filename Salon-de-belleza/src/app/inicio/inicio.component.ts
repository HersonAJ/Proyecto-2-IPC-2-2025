/*import { Component } from '@angular/core';

@Component({
  selector: 'app-inicio',
  standalone: true,
  imports: [],
  templateUrl: './inicio.component.html',
  styleUrl: './inicio.component.css'
})
export class InicioComponent {

}
*/

import { Component } from '@angular/core';
import { CurrencyPipe } from '@angular/common';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-inicio',
  standalone: true,
  imports: [CurrencyPipe, CommonModule],
  templateUrl: './inicio.component.html',
  styleUrl: './inicio.component.css'
})
export class InicioComponent {
  // Simulación de servicios destacados
    servicios = [
      {
        nombre: "Corte de Cabello",
        descripcion: "Corte moderno y elegante.",
        precio: 25,
        imagen: "https://picsum.photos/seed/haircut/400/300"
      },
      {
        nombre: "Manicure y Pedicure",
        descripcion: "Tratamiento completo para manos y pies.",
        precio: 30,
        imagen: "https://picsum.photos/seed/nails/400/300"
      },
      {
        nombre: "Coloración",
        descripcion: "Actualiza tu look con colores vibrantes.",
        precio: 50,
        imagen: "https://picsum.photos/seed/haircolor/400/300"
      }
    ];
  }
  
