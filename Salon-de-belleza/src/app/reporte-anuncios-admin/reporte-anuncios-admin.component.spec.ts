import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ReporteAnunciosAdminComponent } from './reporte-anuncios-admin.component';

describe('ReporteAnunciosAdminComponent', () => {
  let component: ReporteAnunciosAdminComponent;
  let fixture: ComponentFixture<ReporteAnunciosAdminComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ReporteAnunciosAdminComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ReporteAnunciosAdminComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
