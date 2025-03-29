import { Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { RegistroClienteComponent } from './registro-cliente/registro-cliente.component';

export const routes: Routes = [
    {path: 'login', component: LoginComponent},
    {path: 'registro-cliente', component: RegistroClienteComponent},
];
