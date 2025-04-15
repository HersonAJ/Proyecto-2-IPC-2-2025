import { Component, OnInit } from '@angular/core';
import { CitaService, Factura } from '../cita.service';
import { AuthService } from '../auth.service'; 
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-factura-cliente',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './factura-cliente.component.html',
  styleUrls: ['./factura-cliente.component.css']
})
export class FacturaClienteComponent implements OnInit {
  facturas: Factura[] = []; // Lista de facturas 
  mensaje: string = ''; 
  cargando: boolean = true; 

  constructor(private citaService: CitaService, private authService: AuthService) {} 

  ngOnInit(): void {
    const idCliente = this.authService.getIdUsuarioFromToken(); 
    if (idCliente !== null) {
      this.obtenerFacturas(idCliente); 
    } else {
      this.mensaje = 'Error: No se pudo identificar al cliente.';
      this.cargando = false;
    }
  }

  obtenerFacturas(idCliente: number): void {
    this.citaService.obtenerFacturasCliente(idCliente).subscribe({
      next: (facturas: Factura[] | null) => {
        this.facturas = facturas || []; 
        if (this.facturas.length === 0) {
          this.mensaje = 'No hay facturas disponibles.';
        }
        this.cargando = false;
      },
      error: (error) => {
        if (error.status === 204) {
          // Caso específico para 204 No Content
          this.facturas = []; 
          this.mensaje = 'No hay facturas disponibles.';
        } else {
          this.mensaje = 'Hubo un error al cargar las facturas. Por favor, inténtalo de nuevo más tarde.';
          console.error(error);
        }
        this.cargando = false;
      }
    });
  }

  facturaSeleccionada: number | null = null; // Controla qué factura está abierta

  toggleFactura(idFactura: number): void {
    this.facturaSeleccionada = this.facturaSeleccionada === idFactura ? null : idFactura;
  }
}
