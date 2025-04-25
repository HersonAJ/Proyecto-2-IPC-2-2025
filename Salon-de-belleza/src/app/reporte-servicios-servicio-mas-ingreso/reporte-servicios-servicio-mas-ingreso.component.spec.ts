import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ReporteServiciosServicioMasIngresoComponent } from './reporte-servicios-servicio-mas-ingreso.component';

describe('ReporteServiciosServicioMasIngresoComponent', () => {
  let component: ReporteServiciosServicioMasIngresoComponent;
  let fixture: ComponentFixture<ReporteServiciosServicioMasIngresoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ReporteServiciosServicioMasIngresoComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ReporteServiciosServicioMasIngresoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
