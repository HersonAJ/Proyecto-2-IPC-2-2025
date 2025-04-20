import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GestionListaNegraComponent } from './gestion-lista-negra.component';

describe('GestionListaNegraComponent', () => {
  let component: GestionListaNegraComponent;
  let fixture: ComponentFixture<GestionListaNegraComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [GestionListaNegraComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(GestionListaNegraComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
