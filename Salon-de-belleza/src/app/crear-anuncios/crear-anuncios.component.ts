import { Component, OnInit } from '@angular/core';
import { AuthService } from '../auth.service';
import { AnuncioService } from '../anuncio.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-crear-anuncios',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './crear-anuncios.component.html',
  styleUrls: ['./crear-anuncios.component.css']
})
export class CrearAnunciosComponent implements OnInit {
  anuncio = {
    nombreAnunciante: '',
    contactoAnunciante: '',
    tipo: 'Texto', 
    contenidoTexto: '',
    urlImagen: '',
    urlVideo: '',
    precioPorDia: 0,
    duracionDias: 0,
    fechaInicio: '',
    hobbiesRelacionados: [] as string[] // Arreglo para almacenar los hobbies seleccionados
  };

  hobbies: string[] = []; // Lista de hobbies disponibles
  idMarketing: number | null = null; 

  constructor(private authService: AuthService, private anuncioService: AnuncioService) {}

  ngOnInit(): void {
    // Obtener el ID del usuario (encargado de marketing) desde el token
    this.idMarketing = this.authService.getIdUsuarioFromToken();

    // Obtener la lista de hobbies desde el backend
    const token = localStorage.getItem('token');
    if (token) {
      this.anuncioService.obtenerHobbies(token).subscribe(
        (response) => {
          this.hobbies = response; // Almacenar los hobbies obtenidos
        },
        (error) => {
          console.error('Error al obtener los hobbies:', error);
        }
      );
    } else {
      console.error('No se encontró el token para autenticar.');
    }
  }

  crearAnuncio(): void {
    if (!this.anuncio.precioPorDia || this.anuncio.precioPorDia <= 0) {
      alert('El precio por día debe ser mayor que 0.');
      return;
    }
    if (!this.anuncio.duracionDias || this.anuncio.duracionDias < 1) {
      alert('La duración debe ser al menos de 1 día.');
      return;
    }

    const token = localStorage.getItem('token');
    if (!token) {
      console.error('No se encontró el token para autenticar.');
      return;
    }
  
    this.anuncioService.crearAnuncio(this.anuncio, token).subscribe(
      (response) => {
        alert(response.message || 'Anuncio creado exitosamente.');
        this.limpiarFormulario();
      },
      (error) => {
        console.error('Error al crear el anuncio:', error);
      }
    );
  }
  

  limpiarFormulario(): void {
    this.anuncio = {
      nombreAnunciante: '',
      contactoAnunciante: '',
      tipo: 'Texto', // Reiniciar al valor por defecto
      contenidoTexto: '',
      urlImagen: '',
      urlVideo: '',
      precioPorDia: 0,
      duracionDias: 0,
      fechaInicio: '',
      hobbiesRelacionados: []
    };

    // Limpiar los checkboxes de hobbies
    const checkboxes = document.querySelectorAll<HTMLInputElement>('input[type="checkbox"]');
    checkboxes.forEach((checkbox) => {
      checkbox.checked = false;
    });
  }

  // Manejar la selección y deselección de hobbies
  onHobbieChange(event: Event): void {
    const checkbox = event.target as HTMLInputElement;
    const hobbie = checkbox.value;

    if (checkbox.checked) {
      this.anuncio.hobbiesRelacionados.push(hobbie); // Agregar hobby seleccionado
    } else {
      const index = this.anuncio.hobbiesRelacionados.indexOf(hobbie);
      if (index > -1) {
        this.anuncio.hobbiesRelacionados.splice(index, 1); // Remover hobby deseleccionado
      }
    }
  }
}
