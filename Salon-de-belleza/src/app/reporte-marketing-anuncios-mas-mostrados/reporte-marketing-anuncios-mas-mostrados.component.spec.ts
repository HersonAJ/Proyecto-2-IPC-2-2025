import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ReporteMarketingAnunciosMasMostradosComponent } from './reporte-marketing-anuncios-mas-mostrados.component';

describe('ReporteMarketingAnunciosMasMostradosComponent', () => {
  let component: ReporteMarketingAnunciosMasMostradosComponent;
  let fixture: ComponentFixture<ReporteMarketingAnunciosMasMostradosComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ReporteMarketingAnunciosMasMostradosComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ReporteMarketingAnunciosMasMostradosComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
