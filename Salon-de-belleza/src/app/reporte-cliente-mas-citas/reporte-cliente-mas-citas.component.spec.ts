import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ReporteClienteMasCitasComponent } from './reporte-cliente-mas-citas.component';

describe('ReporteClienteMasCitasComponent', () => {
  let component: ReporteClienteMasCitasComponent;
  let fixture: ComponentFixture<ReporteClienteMasCitasComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ReporteClienteMasCitasComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ReporteClienteMasCitasComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
