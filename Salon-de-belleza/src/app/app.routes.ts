import { Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { RegistroClienteComponent } from './registro-cliente/registro-cliente.component';
import { MiPerfilComponent } from './mi-perfil/mi-perfil.component';
import { InicioComponent } from './inicio/inicio.component';
import { RegistrarUsuarioComponent } from './registrar-usuario/registrar-usuario.component';
import { GestionUsuariosComponent } from './gestion-usuarios/gestion-usuarios.component';
import { CrearServicioComponent } from './crear-servicio/crear-servicio.component';
import { GestionServicioComponent } from './gestion-servicio/gestion-servicio.component';
import { HorarioGeneralComponent } from './horario-general/horario-general.component';
import { CrearCitaComponent } from './crear-cita/crear-cita.component';
import { CitasAgendadasClienteComponent } from './citas-agendadas-cliente/citas-agendadas-cliente.component';
import { CitasAsignadasComponent } from './citas-asignadas/citas-asignadas.component';
import { GestionarCitasEmpleadoComponent } from './gestionar-citas-empleado/gestionar-citas-empleado.component';
import { CrearFacturaComponent } from './crear-factura/crear-factura.component';
import { FacturaClienteComponent } from './factura-cliente/factura-cliente.component';
import { HistorialClienteComponent } from './historial-cliente/historial-cliente.component';
import { CrearAnunciosComponent } from './crear-anuncios/crear-anuncios.component';
import { GestionAnunciosComponent } from './gestion-anuncios/gestion-anuncios.component';

export const routes: Routes = [
    {path: 'login', component: LoginComponent},
    {path: 'registro-cliente', component: RegistroClienteComponent},
    {path: 'mi-perfil', component: MiPerfilComponent},
    {path: '', component: InicioComponent},
    {path: 'registrar-usuario', component: RegistrarUsuarioComponent},
    {path: 'gestion-usuarios', component:GestionUsuariosComponent},
    {path: 'crear-servicio', component:CrearServicioComponent},
    {path: 'gestion-servicio', component:GestionServicioComponent},
    {path: 'horario-general', component:HorarioGeneralComponent},
    {path: 'crear-cita', component: CrearCitaComponent},
    {path: 'citas-agendadas-cliente', component: CitasAgendadasClienteComponent},
    {path: 'citas-asignadas-empleado', component: CitasAsignadasComponent},
    {path: 'gestionar-citas-empleado' , component : GestionarCitasEmpleadoComponent},
    {path: 'crear-factura' , component: CrearFacturaComponent},
    {path: 'factura-cliente', component: FacturaClienteComponent},
    {path: 'historial-cliente', component: HistorialClienteComponent},
    {path: 'crear-anuncio', component: CrearAnunciosComponent},
    {path: 'gestion-anuncios', component: GestionAnunciosComponent}
];
