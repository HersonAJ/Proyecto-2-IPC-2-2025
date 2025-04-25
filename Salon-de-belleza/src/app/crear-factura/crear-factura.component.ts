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
  @Input() idCita: number = 0; 
  @Input() nombreServicio: string = ''; 
  @Input() duracionServicio: number = 0; 
  @Input() precioServicio: number = 0;
  @Input() nombreCliente: string = ''; 

  @Output() facturaConfirmada = new EventEmitter<void>(); 

  mensaje: string = ''; 

  confirmarFactura(): void {
    this.facturaConfirmada.emit(); 
    this.mensaje = 'Factura confirmada exitosamente.';
  }
}

