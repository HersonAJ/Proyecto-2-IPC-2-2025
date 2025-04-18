import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CrearAnunciosComponent } from './crear-anuncios.component';

describe('CrearAnunciosComponent', () => {
  let component: CrearAnunciosComponent;
  let fixture: ComponentFixture<CrearAnunciosComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CrearAnunciosComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CrearAnunciosComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
