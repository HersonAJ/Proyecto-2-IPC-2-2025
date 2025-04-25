import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ReporteServiciosServicioMasCompradoComponent } from './reporte-servicios-servicio-mas-comprado.component';

describe('ReporteServiciosServicioMasCompradoComponent', () => {
  let component: ReporteServiciosServicioMasCompradoComponent;
  let fixture: ComponentFixture<ReporteServiciosServicioMasCompradoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ReporteServiciosServicioMasCompradoComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ReporteServiciosServicioMasCompradoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
