import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GestionarCitasEmpleadoComponent } from './gestionar-citas-empleado.component';

describe('GestionarCitasEmpleadoComponent', () => {
  let component: GestionarCitasEmpleadoComponent;
  let fixture: ComponentFixture<GestionarCitasEmpleadoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [GestionarCitasEmpleadoComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(GestionarCitasEmpleadoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
