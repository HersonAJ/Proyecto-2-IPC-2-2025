import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';
import { GestionServiciosService } from '../gestion-servicio.service';

@Component({
  selector: 'app-gestion-servicio',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './gestion-servicio.component.html',
  styleUrls: ['./gestion-servicio.component.css']
})
export class GestionServicioComponent implements OnInit {
  servicios: any[] = []; // Lista de servicios
  empleadosAsignados: any[] = []; // Empleados asignados al servicio
  empleadosNoAsignados: any[] = []; // Empleados no asignados al servicio
  gestionForm: FormGroup; // Formulario para editar/gestionar servicios
  servicioSeleccionado: any = null; // Servicio seleccionado para gestionar
  selectedServiceIndex: number | null = null; // Índice del servicio colapsado

  constructor(
    private gestionServicioService: GestionServiciosService,
    private fb: FormBuilder
  ) {
    this.gestionForm = this.fb.group({
      nombreServicio: [''],
      descripcion: [''],
      duracion: [''],
      precio: [''],
      estado: [''],
      empleadosIds: [[]] // Lista de IDs de empleados asignados
    });
  }

  ngOnInit(): void {
    this.cargarServicios();
  }

  // Método para cargar todos los servicios
  cargarServicios(): void {
    this.gestionServicioService.obtenerServicios().subscribe({
      next: (data) => {
        this.servicios = data;
      },
      error: (err) => {
        console.error('Error al cargar servicios:', err);
      }
    });
  }

  // Método para alternar el colapso y cargar datos del servicio
  toggleCollapse(index: number): void {
    this.selectedServiceIndex =
      this.selectedServiceIndex === index ? null : index;

    if (this.selectedServiceIndex !== null) {
      const servicio = this.servicios[this.selectedServiceIndex];
      this.seleccionarServicio(servicio);
    }
  }

  // Método para seleccionar un servicio y cargar sus datos en el formulario
  seleccionarServicio(servicio: any): void {
    if (!servicio || !servicio.idServicio) {
      console.error('Servicio inválido seleccionado.');
      return;
    }

    this.servicioSeleccionado = servicio;
    this.gestionForm.patchValue({
      nombreServicio: servicio.nombreServicio,
      descripcion: servicio.descripcion,
      duracion: servicio.duracion,
      precio: servicio.precio,
      estado: servicio.estado,
      empleadosIds: []
    });

    this.cargarEmpleadosPorEstado(servicio.idServicio);
  }

  // Método para cargar empleados asignados y no asignados
  cargarEmpleadosPorEstado(idServicio: number): void {
    this.gestionServicioService.obtenerEmpleadosPorEstado(idServicio).subscribe({
      next: (data) => {
        this.empleadosAsignados = data.asignados;
        this.empleadosNoAsignados = data.no_asignados;
      },
      error: (err) => {
        console.error('Error al cargar empleados por estado:', err);
      }
    });
  }

  // Método para guardar cambios en el servicio
  guardarCambios(): void {
    if (!this.servicioSeleccionado) {
      alert('No se ha seleccionado ningún servicio.');
      return;
    }
  
    const datosGestion = {
      servicio: {
        nombreServicio: this.gestionForm.value.nombreServicio,
        descripcion: this.gestionForm.value.descripcion,
        duracion: this.gestionForm.value.duracion,
        precio: this.gestionForm.value.precio
      },
      nuevaImagen: this.servicioSeleccionado.imagen, // Enviar la nueva imagen
      nuevoEstado: this.gestionForm.value.estado,
      empleadosIds: this.gestionForm.value.empleadosIds
    };
  
    this.gestionServicioService.actualizarServicio(this.servicioSeleccionado.idServicio, datosGestion).subscribe({
      next: () => {
        alert('El servicio ha sido actualizado con éxito.');
        const index = this.servicios.findIndex(s => s.idServicio === this.servicioSeleccionado.idServicio);
        if (index !== -1) {
          this.servicios[index] = { ...this.servicioSeleccionado, ...datosGestion.servicio, estado: datosGestion.nuevoEstado };
        }
        this.cargarEmpleadosPorEstado(this.servicioSeleccionado.idServicio);
      },
      error: (err) => {
        console.error('Error al actualizar el servicio:', err);
        alert('Ocurrió un error al actualizar el servicio.');
      }
    });
  }
  
  // Método para manejar cambios en los checkboxes de empleados
  onEmpleadoChange(event: any, idEmpleado: number): void {
    const empleadosIds = this.gestionForm.value.empleadosIds;

    if (event.target.checked) {
      this.gestionForm.patchValue({
        empleadosIds: [...empleadosIds, idEmpleado]
      });
    } else {
      this.gestionForm.patchValue({
        empleadosIds: empleadosIds.filter((id: number) => id !== idEmpleado)
      });
    }
  }
  onFileChange(event: any): void {
    const file = event.target.files[0];
    if (file) {
      const reader = new FileReader();
      reader.onload = () => {
        this.servicioSeleccionado.imagen = reader.result?.toString().split(',')[1]; // Convertir a base64
      };
      reader.readAsDataURL(file);
    }
  }
}

