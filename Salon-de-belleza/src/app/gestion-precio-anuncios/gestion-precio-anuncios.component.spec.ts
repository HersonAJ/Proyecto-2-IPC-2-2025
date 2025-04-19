import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GestionPrecioAnunciosComponent } from './gestion-precio-anuncios.component';

describe('GestionPrecioAnunciosComponent', () => {
  let component: GestionPrecioAnunciosComponent;
  let fixture: ComponentFixture<GestionPrecioAnunciosComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [GestionPrecioAnunciosComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(GestionPrecioAnunciosComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
