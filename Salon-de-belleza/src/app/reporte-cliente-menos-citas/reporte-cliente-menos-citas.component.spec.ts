import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ReporteClienteMenosCitasComponent } from './reporte-cliente-menos-citas.component';

describe('ReporteClienteMenosCitasComponent', () => {
  let component: ReporteClienteMenosCitasComponent;
  let fixture: ComponentFixture<ReporteClienteMenosCitasComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ReporteClienteMenosCitasComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ReporteClienteMenosCitasComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
