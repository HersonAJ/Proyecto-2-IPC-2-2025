import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ReporteServiciosServicioMenosCompradoComponent } from './reporte-servicios-servicio-menos-comprado.component';

describe('ReporteServiciosServicioMenosCompradoComponent', () => {
  let component: ReporteServiciosServicioMenosCompradoComponent;
  let fixture: ComponentFixture<ReporteServiciosServicioMenosCompradoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ReporteServiciosServicioMenosCompradoComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ReporteServiciosServicioMenosCompradoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
