import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ReporteClienteMasGastoComponent } from './reporte-cliente-mas-gasto.component';

describe('ReporteClienteMasGastoComponent', () => {
  let component: ReporteClienteMasGastoComponent;
  let fixture: ComponentFixture<ReporteClienteMasGastoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ReporteClienteMasGastoComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ReporteClienteMasGastoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
