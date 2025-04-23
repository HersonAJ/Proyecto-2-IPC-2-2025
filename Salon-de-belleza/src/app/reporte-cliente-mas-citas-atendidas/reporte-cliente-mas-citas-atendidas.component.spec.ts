import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ReporteClienteMasCitasAtendidasComponent } from './reporte-cliente-mas-citas-atendidas.component';

describe('ReporteClienteMasCitasAtendidasComponent', () => {
  let component: ReporteClienteMasCitasAtendidasComponent;
  let fixture: ComponentFixture<ReporteClienteMasCitasAtendidasComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ReporteClienteMasCitasAtendidasComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ReporteClienteMasCitasAtendidasComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
