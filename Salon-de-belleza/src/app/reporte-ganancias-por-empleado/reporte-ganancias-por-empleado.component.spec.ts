import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ReporteGananciasPorEmpleadoComponent } from './reporte-ganancias-por-empleado.component';

describe('ReporteGananciasPorEmpleadoComponent', () => {
  let component: ReporteGananciasPorEmpleadoComponent;
  let fixture: ComponentFixture<ReporteGananciasPorEmpleadoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ReporteGananciasPorEmpleadoComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ReporteGananciasPorEmpleadoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
