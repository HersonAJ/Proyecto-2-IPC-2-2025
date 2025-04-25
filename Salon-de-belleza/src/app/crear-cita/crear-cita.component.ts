import { Component, OnInit } from '@angular/core';
import { CitaService, Cita, Servicio, BloqueOcupado } from '../cita.service';
import { AuthService } from '../auth.service'; 
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-crear-cita',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './crear-cita.component.html',
  styleUrls: ['./crear-cita.component.css'],
})
export class CrearCitaComponent implements OnInit {
  servicios: Servicio[] = [];
  empleados: any[] = [];
  horariosOcupados: BloqueOcupado[] = [];
  cita: Cita = {
    idServicio: 0,
    idCliente: 0, 
    idEmpleado: 0,
    fechaCita: '',
    horaCita: '',
    estado: 'Pendiente',
  };
  mensaje: string = '';
  servicioSeleccionado: Servicio | null = null;
  permitido: boolean = false; 

  constructor(private citaService: CitaService, private authService: AuthService) {}

  ngOnInit(): void {
    this.verificarPermisoCliente(); // Verificar si el cliente está permitido
  }

  cargarServicios(): void {
    this.citaService.obtenerServicios().subscribe({
      next: (data) => {
        this.servicios = data.map((servicio) => ({
          ...servicio,
          imagen: servicio.imagen ? `data:image/jpeg;base64,${servicio.imagen}` : null,
        }));
      },
      error: () => {
        this.mensaje = 'No se encontraron servicios disponibles.';
      },
    });
  }

  onServicioChange(): void {
    this.servicioSeleccionado = this.servicios.find((s) => s.idServicio === this.cita.idServicio) || null;
    this.cargarEmpleadosPorServicio();
  }

  cargarEmpleadosPorServicio(): void {
    this.empleados = [];
    this.cita.idEmpleado = 0;

    if (this.cita.idServicio) {
      this.citaService.obtenerEmpleadosPorServicio(this.cita.idServicio).subscribe({
        next: (data) => {
          this.empleados = data;
          if (this.empleados.length === 0) {
            this.mensaje = 'No se encontraron empleados para este servicio.';
          } else {
            this.mensaje = '';
          }
        },
        error: () => {
          this.mensaje = 'No se encontraron empleados para este servicio.';
        },
      });
    }
  }
  
  onEmpleadoYFechaChange(): void {
    if (this.cita.idEmpleado && this.cita.fechaCita) {
      this.cargarHorariosOcupados();
    }
  }

  cargarHorariosOcupados(): void {
    this.citaService.obtenerHorariosOcupados(this.cita.idEmpleado, this.cita.fechaCita).subscribe({
      next: (bloques) => {
        this.horariosOcupados = bloques;
      },
      error: () => {
        this.mensaje = 'Error al cargar horarios ocupados.';
      },
    });
  }

  crearCita(): void {
    const idCliente = this.authService.getIdUsuarioFromToken();
    if (!idCliente) {
      this.mensaje = 'Error: No se pudo identificar al cliente autenticado.';
      return;
    }

    this.cita.idCliente = idCliente;

    if (this.validarCita()) {
      this.citaService.crearCita(this.cita).subscribe({
        next: (response) => {
          this.mensaje = response?.message || 'La cita fue creada exitosamente.';
          this.resetFormulario();
        },
        error: (err) => {
          console.error('Error desde el backend:', err);
          this.mensaje = err.error?.message || 'Ocurrió un error al crear la cita.';
        },
        complete: () => {
          if (!this.mensaje) {
            this.mensaje = 'La cita fue creada exitosamente.';
          }
        },
      });
    }
  }

  validarCita(): boolean {
    if (!this.cita.idServicio || !this.cita.idEmpleado || !this.cita.fechaCita || !this.cita.horaCita) {
      this.mensaje = 'Por favor, completa todos los campos antes de enviar.';
      return false;
    }
    return true;
  }

  resetFormulario(): void {
    this.cita = {
      idServicio: 0,
      idCliente: 0,
      idEmpleado: 0,
      fechaCita: '',
      horaCita: '',
      estado: 'Pendiente',
    };
    this.horariosOcupados = [];
    this.empleados = [];
    this.mensaje = '';
  }

  verificarPermisoCliente(): void {
    const token = localStorage.getItem('token');
    if (!token) {
      this.mensaje = 'Error: No se pudo identificar al cliente autenticado.';
      return;
    }

    this.citaService.validarClientePermitido(token).subscribe({
      next: (respuesta) => {
        this.permitido = respuesta.permitido;
        if (!this.permitido) {
          this.mensaje = respuesta.message || 'No puede agendar citas debido a restricciones.';
        } else {
          this.cargarServicios(); 
        }
      },
      error: (err) => {
        console.error('Error al validar permisos del cliente:', err);
        this.mensaje = err.error?.message || 'Ocurrió un error al validar los permisos del cliente.';
      },
    });
  }
}
