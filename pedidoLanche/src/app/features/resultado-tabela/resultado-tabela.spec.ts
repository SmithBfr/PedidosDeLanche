import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ResultadoTabela } from './resultado-tabela';

describe('ResultadoTabela', () => {
  let component: ResultadoTabela;
  let fixture: ComponentFixture<ResultadoTabela>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ResultadoTabela],
    }).compileComponents();

    fixture = TestBed.createComponent(ResultadoTabela);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
