import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ReporteMarketingAnunciosMenosMostradosComponent } from './reporte-marketing-anuncios-menos-mostrados.component';

describe('ReporteMarketingAnunciosMenosMostradosComponent', () => {
  let component: ReporteMarketingAnunciosMenosMostradosComponent;
  let fixture: ComponentFixture<ReporteMarketingAnunciosMenosMostradosComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ReporteMarketingAnunciosMenosMostradosComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ReporteMarketingAnunciosMenosMostradosComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
