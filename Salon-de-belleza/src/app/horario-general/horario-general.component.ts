import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { HorarioService } from '../horario.service';

@Component({
  selector: 'app-horario-general',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './horario-general.component.html',
  styleUrls: ['./horario-general.component.css']
})
export class HorarioGeneralComponent implements OnInit {
  diasSemana = ['Domingo', 'Lunes', 'Martes', 'Miércoles', 'Jueves', 'Viernes', 'Sábado'];
  horarios: { diaSemana: number; horaApertura: string; horaCierre: string }[] = Array.from(
    { length: 7 },
    (_, index) => ({
      diaSemana: index,
      horaApertura: '00:00',
      horaCierre: '00:00'
    })
  );
  
  constructor(private horarioService: HorarioService) {}

  ngOnInit(): void {
    this.obtenerHorarioGeneral();
  }

  // Método para obtener el horario general desde el servicio
  obtenerHorarioGeneral(): void {
    this.horarioService.obtenerHorarioGeneral().subscribe({
      next: (data) => {
        data.forEach((horario: any) => {
          this.horarios[horario.diaSemana] = {
            diaSemana: horario.diaSemana,
            horaApertura: `${horario.horaApertura[0].toString().padStart(2, '0')}:${horario.horaApertura[1].toString().padStart(2, '0')}`,
            horaCierre: `${horario.horaCierre[0].toString().padStart(2, '0')}:${horario.horaCierre[1].toString().padStart(2, '0')}`
          };
        });
      },
      error: (err) => {
        console.error('Error al obtener el horario general:', err);
        alert('No se pudo cargar el horario general.');
      }
    });
  }
  
  // Método para guardar/actualizar el horario general
  guardarHorarioGeneral(): void {
    const horariosParaGuardar = this.horarios.map(horario => ({
      diaSemana: horario.diaSemana,
      horaApertura: horario.horaApertura ? horario.horaApertura.split(':').map(Number) : [0, 0],
      horaCierre: horario.horaCierre ? horario.horaCierre.split(':').map(Number) : [0, 0],
    }));
    this.horarioService.establecerHorarioGeneral(horariosParaGuardar).subscribe({
      next: () => {
        alert('Horario general guardado con éxito.');
        this.obtenerHorarioGeneral(); // Refrescar la vista
      },
      error: (err) => {
        console.error('Error al guardar el horario general:', err);
        alert('No se pudo guardar el horario general.');
      }
    });
  }
}
