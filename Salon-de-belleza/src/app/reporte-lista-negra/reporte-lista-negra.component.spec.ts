import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ReporteListaNegraComponent } from './reporte-lista-negra.component';

describe('ReporteListaNegraComponent', () => {
  let component: ReporteListaNegraComponent;
  let fixture: ComponentFixture<ReporteListaNegraComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ReporteListaNegraComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ReporteListaNegraComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
