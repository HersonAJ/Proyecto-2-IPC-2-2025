import { Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { RegistroClienteComponent } from './registro-cliente/registro-cliente.component';
import { MiPerfilComponent } from './mi-perfil/mi-perfil.component';
import { InicioComponent } from './inicio/inicio.component';
import { RegistrarUsuarioComponent } from './registrar-usuario/registrar-usuario.component';
import { GestionUsuariosComponent } from './gestion-usuarios/gestion-usuarios.component';
import { CrearServicioComponent } from './crear-servicio/crear-servicio.component';
import { GestionServicioComponent } from './gestion-servicio/gestion-servicio.component';

export const routes: Routes = [
    {path: 'login', component: LoginComponent},
    {path: 'registro-cliente', component: RegistroClienteComponent},
    {path: 'mi-perfil', component: MiPerfilComponent},
    {path: '', component: InicioComponent},
    {path: 'registrar-usuario', component: RegistrarUsuarioComponent},
    {path: 'gestion-usuarios', component:GestionUsuariosComponent},
    {path: 'crear-servicio', component:CrearServicioComponent},
    {path: 'gestion-servicio', component:GestionServicioComponent},
];
