import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FacturaClienteComponent } from './factura-cliente.component';

describe('FacturaClienteComponent', () => {
  let component: FacturaClienteComponent;
  let fixture: ComponentFixture<FacturaClienteComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FacturaClienteComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(FacturaClienteComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
