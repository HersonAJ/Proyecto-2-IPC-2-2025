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
import { GestionPrecioAnunciosComponent } from './gestion-precio-anuncios/gestion-precio-anuncios.component';
import { GestionListaNegraComponent } from './gestion-lista-negra/gestion-lista-negra.component';
import { ReporteGananciasComponent } from './reporte-ganancias/reporte-ganancias.component';
import { ReporteAnunciosAdminComponent } from './reporte-anuncios-admin/reporte-anuncios-admin.component';
import { ReporteClienteMasCitasComponent } from './reporte-cliente-mas-citas/reporte-cliente-mas-citas.component';
import { ReporteClienteMenosCitasComponent } from './reporte-cliente-menos-citas/reporte-cliente-menos-citas.component';
import { ReporteListaNegraComponent } from './reporte-lista-negra/reporte-lista-negra.component';
import { ReporteClienteMasGastoComponent } from './reporte-cliente-mas-gasto/reporte-cliente-mas-gasto.component';
import { ReporteClienteMenosGastoComponent } from './reporte-cliente-menos-gasto/reporte-cliente-menos-gasto.component';
import { ReporteGananciasPorEmpleadoComponent } from './reporte-ganancias-por-empleado/reporte-ganancias-por-empleado.component';
import { ReporteClienteMasCitasAtendidasComponent } from './reporte-cliente-mas-citas-atendidas/reporte-cliente-mas-citas-atendidas.component';
import { ReporteMarketingAnunciosMasMostradosComponent } from './reporte-marketing-anuncios-mas-mostrados/reporte-marketing-anuncios-mas-mostrados.component';
import { ReporteMarketingAnunciosMenosMostradosComponent } from './reporte-marketing-anuncios-menos-mostrados/reporte-marketing-anuncios-menos-mostrados.component';
import { ReporteMarketingAnunciosMasCompradosComponent } from './reporte-marketing-anuncios-mas-comprados/reporte-marketing-anuncios-mas-comprados.component';
import { ReporteServiciosServicioMasCompradoComponent } from './reporte-servicios-servicio-mas-comprado/reporte-servicios-servicio-mas-comprado.component';
import { ReporteServiciosServicioMenosCompradoComponent } from './reporte-servicios-servicio-menos-comprado/reporte-servicios-servicio-menos-comprado.component';

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
    {path: 'gestion-anuncios', component: GestionAnunciosComponent},
    {path: 'gestion-precio-anuncios', component: GestionPrecioAnunciosComponent},
    {path: 'gestion-lista-negra', component: GestionListaNegraComponent},
    {path: 'reporte-ganancias-admin', component: ReporteGananciasComponent},
    {path: 'reporte-anuncios-admin', component: ReporteAnunciosAdminComponent},
    {path: 'reporte-clientes-mas-citas', component: ReporteClienteMasCitasComponent},
    {path: 'reporte-clientes-menos-citas', component: ReporteClienteMenosCitasComponent},
    {path: 'reporte-lista-negra', component: ReporteListaNegraComponent},
    {path: 'reporte-clientes-mas-gasto', component: ReporteClienteMasGastoComponent},
    {path: 'reporte-clientes-menos-gasto', component: ReporteClienteMenosGastoComponent},
    {path: 'reporte-empleado-mas-ganancia', component: ReporteGananciasPorEmpleadoComponent},
    {path: 'reporte-cliente-mas-citas-atendidas', component: ReporteClienteMasCitasAtendidasComponent},
    {path: 'reporte-marketing-anuncios-mas-mostrados', component: ReporteMarketingAnunciosMasMostradosComponent},
    {path: 'reporte-marketing-anuncios-menos-mostrados', component: ReporteMarketingAnunciosMenosMostradosComponent},
    {path: 'reporte-marketing-anuncios-mas-comprados', component: ReporteMarketingAnunciosMasCompradosComponent},
    {path: 'reporte-servicios-servicio-mas-comprado', component: ReporteServiciosServicioMasCompradoComponent},
    {path: 'reporte-servicios-servicio-menos-comprado', component: ReporteServiciosServicioMenosCompradoComponent},
]
