import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ReporteMarketingAnunciosMasCompradosComponent } from './reporte-marketing-anuncios-mas-comprados.component';

describe('ReporteMarketingAnunciosMasCompradosComponent', () => {
  let component: ReporteMarketingAnunciosMasCompradosComponent;
  let fixture: ComponentFixture<ReporteMarketingAnunciosMasCompradosComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ReporteMarketingAnunciosMasCompradosComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ReporteMarketingAnunciosMasCompradosComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
