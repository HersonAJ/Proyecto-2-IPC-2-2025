import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CrearCitaComponent } from './crear-cita.component';

describe('CrearCitaComponent', () => {
  let component: CrearCitaComponent;
  let fixture: ComponentFixture<CrearCitaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CrearCitaComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CrearCitaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
