import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EnvioTexto } from './envio-texto';

describe('EnvioTexto', () => {
  let component: EnvioTexto;
  let fixture: ComponentFixture<EnvioTexto>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EnvioTexto],
    }).compileComponents();

    fixture = TestBed.createComponent(EnvioTexto);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
