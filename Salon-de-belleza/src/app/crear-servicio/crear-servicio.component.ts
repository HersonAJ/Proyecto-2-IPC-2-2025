import { Component, OnInit } from '@angular/core';
import { ServiciosService } from '../servicios.service';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-crear-servicio',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './crear-servicio.component.html',
  styleUrls: ['./crear-servicio.component.css']
})
export class CrearServicioComponent implements OnInit {
  servicio = {
    nombreServicio: '',
    descripcion: '',
    duracion: 0,
    precio: 0,
    estado: 'Visible',
    imagen: null as string | ArrayBuffer | null,
    empleados: [] as number[] // Lista de IDs de empleados seleccionados
  };

  empleados: any[] = []; // Almacenar el listado de empleados

  constructor(private servicioService: ServiciosService) {}

  ngOnInit(): void {
    this.cargarEmpleados();
  }

  // Método para cargar el listado de empleados
  cargarEmpleados(): void {
    this.servicioService.obtenerEmpleados().subscribe({
      next: (data) => {
        this.empleados = data; // Guardar la lista de empleados
      },
      error: (err) => {
        console.error('Error al cargar empleados:', err);
      }
    });
  }

  // Método para manejar cambios en los checkboxes de empleados
  onEmpleadoChange(event: any, idEmpleado: number): void {
    if (event.target.checked) {
      // Agregar ID del empleado seleccionado
      this.servicio.empleados.push(idEmpleado);
    } else {
      // Eliminar ID del empleado desmarcado
      this.servicio.empleados = this.servicio.empleados.filter(id => id !== idEmpleado);
    }
    console.log('Empleados seleccionados:', this.servicio.empleados);
  }

  // Método para manejar el cambio del archivo de imagen
  onFileChange(event: any): void {
    const file = event.target.files[0];
    if (file) {
      const reader = new FileReader();
      reader.onload = () => {
        const base64String = (reader.result as string).split(',')[1]; 
        this.servicio.imagen = base64String; // Solo enviar la parte base64
      };
      reader.readAsDataURL(file);
    }
  }

  crearServicio(): void {
    console.log('Datos enviados:', this.servicio);
    this.servicioService.crearServicio(this.servicio).subscribe({
      next: (response) => {
        alert('Servicio creado exitosamente');
        console.log(response);
        this.limpiarFormulario();
      },
      error: (err) => {
        console.error('Error al crear el servicio:', err);
        alert('No se pudo crear el servicio');
      }
    });
  }

  limpiarFormulario(): void {
    this.servicio = {
      nombreServicio: '',
      descripcion: '',
      duracion: 0,
      precio: 0,
      estado: 'Visible',
      imagen: null,
      empleados: []
    };
  }
}

