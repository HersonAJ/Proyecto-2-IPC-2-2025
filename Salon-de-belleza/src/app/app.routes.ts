import { Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { RegistroClienteComponent } from './registro-cliente/registro-cliente.component';
import { MiPerfilComponent } from './mi-perfil/mi-perfil.component';
import { InicioComponent } from './inicio/inicio.component';

export const routes: Routes = [
    {path: 'login', component: LoginComponent},
    {path: 'registro-cliente', component: RegistroClienteComponent},
    {path: 'mi-perfil', component: MiPerfilComponent},
    {path: '', component: InicioComponent},
];
