import { Component, Input, Output, EventEmitter } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-crear-factura',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './crear-factura.component.html',
  styleUrls: ['./crear-factura.component.css']
})
export class CrearFacturaComponent {
  @Input() idCita: number = 0; // ID de la cita
  @Input() nombreServicio: string = ''; // Nombre del servicio
  @Input() duracionServicio: number = 0; // Duración del servicio
  @Input() precioServicio: number = 0; // Precio del servicio
  @Input() nombreCliente: string = ''; // Nombre del cliente

  @Output() facturaConfirmada = new EventEmitter<void>(); // Evento para confirmar factura

  mensaje: string = ''; // Mensaje para mostrar éxito o error

  confirmarFactura(): void {
    this.facturaConfirmada.emit(); 
    this.mensaje = 'Factura confirmada exitosamente.';
  }
}

