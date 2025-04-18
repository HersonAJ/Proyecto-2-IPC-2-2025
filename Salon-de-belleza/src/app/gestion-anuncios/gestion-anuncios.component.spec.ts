import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GestionAnunciosComponent } from './gestion-anuncios.component';

describe('GestionAnunciosComponent', () => {
  let component: GestionAnunciosComponent;
  let fixture: ComponentFixture<GestionAnunciosComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [GestionAnunciosComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(GestionAnunciosComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
