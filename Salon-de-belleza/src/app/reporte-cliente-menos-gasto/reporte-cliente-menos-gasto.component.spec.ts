import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ReporteClienteMenosGastoComponent } from './reporte-cliente-menos-gasto.component';

describe('ReporteClienteMenosGastoComponent', () => {
  let component: ReporteClienteMenosGastoComponent;
  let fixture: ComponentFixture<ReporteClienteMenosGastoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ReporteClienteMenosGastoComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ReporteClienteMenosGastoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
