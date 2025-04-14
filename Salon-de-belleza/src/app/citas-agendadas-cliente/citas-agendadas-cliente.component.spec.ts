import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CitasAgendadasClienteComponent } from './citas-agendadas-cliente.component';

describe('CitasAgendadasClienteComponent', () => {
  let component: CitasAgendadasClienteComponent;
  let fixture: ComponentFixture<CitasAgendadasClienteComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CitasAgendadasClienteComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CitasAgendadasClienteComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
