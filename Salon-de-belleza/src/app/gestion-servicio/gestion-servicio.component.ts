import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';
import { GestionServiciosService } from '../gestion-servicio.service';
import { DomSanitizer, SafeResourceUrl } from '@angular/platform-browser'; 

@Component({
  selector: 'app-gestion-servicio',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './gestion-servicio.component.html',
  styleUrls: ['./gestion-servicio.component.css']
})
export class GestionServicioComponent implements OnInit {
  servicios: any[] = []; 
  empleadosAsignados: any[] = []; 
  empleadosNoAsignados: any[] = []; 
  gestionForm: FormGroup;
  servicioSeleccionado: any = null; 
  selectedServiceIndex: number | null = null; 

  constructor(
    private gestionServicioService: GestionServiciosService,
    private fb: FormBuilder,
    private sanitizer: DomSanitizer 
  ) {
    this.gestionForm = this.fb.group({
      nombreServicio: [''],
      descripcion: [''],
      duracion: [''],
      precio: [''],
      estado: [''],
      empleadosIds: [[]], 
      catalogoPdf: [null], 
      catalogoPdfFile: [null]
    });
  }

  ngOnInit(): void {
    this.cargarServicios();
  }

  // Método para cargar todos los servicios
  cargarServicios(): void {
    this.gestionServicioService.obtenerServicios().subscribe({
      next: (data) => {
        this.servicios = data.map(servicio => {
          let catalogoPdfUrl = null;
          if (servicio.catalogoPdf) {
            const byteCharacters = atob(servicio.catalogoPdf);
            const byteNumbers = Array.from(byteCharacters).map(char => char.charCodeAt(0));
            const byteArray = new Uint8Array(byteNumbers);
            const blob = new Blob([byteArray], { type: 'application/pdf' });
            catalogoPdfUrl = URL.createObjectURL(blob);
          }
          return {
            ...servicio,
            catalogoPdf: catalogoPdfUrl ? this.sanitizer.bypassSecurityTrustResourceUrl(catalogoPdfUrl) : null
          };
        });
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
      empleadosIds: [],
      catalogoPdf: servicio.catalogoPdf 
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

  guardarCambios(): void {
    if (!this.servicioSeleccionado) {
      alert('No se ha seleccionado ningún servicio.');
      return;
    }
  
    const servicio = {
      nombreServicio: this.gestionForm.value.nombreServicio,
      descripcion: this.gestionForm.value.descripcion,
      duracion: this.gestionForm.value.duracion,
      precio: this.gestionForm.value.precio
    };
  
    const nuevoEstado = this.gestionForm.value.estado;
    const empleadosIds = this.gestionForm.value.empleadosIds;
  
    const nuevaImagen = this.servicioSeleccionado.imagenFile || null;
    const catalogoPdf = this.gestionForm.value.catalogoPdfFile || null;
  
    this.gestionServicioService.actualizarServicio(
      this.servicioSeleccionado.idServicio,
      servicio,
      nuevaImagen,
      catalogoPdf,
      nuevoEstado,
      empleadosIds
    ).subscribe({
      next: () => {
        alert('El servicio ha sido actualizado con éxito.');
        this.cargarServicios();
        this.servicioSeleccionado = null;
        this.selectedServiceIndex = null;
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

  // Método para manejar cambios en el archivo PDF
  onFileChange(event: any, tipo: 'imagen' | 'catalogoPdf'): void {
    const file = event.target.files[0];
    if (file) {
      if (tipo === 'imagen') {
        this.servicioSeleccionado.imagenFile = file;
      } else if (tipo === 'catalogoPdf') {
        this.gestionForm.patchValue({
          catalogoPdfFile: file 
        });
      }
    } else {
    }
  }
}