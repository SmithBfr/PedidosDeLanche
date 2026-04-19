import { Routes } from '@angular/router';
import { EnvioTexto } from './features/envio-texto/envio-texto';
import { ResultadoTabela } from './features/resultado-tabela/resultado-tabela';

export const routes: Routes = [
  {path: '', redirectTo: 'envio', pathMatch: 'full'},
  {path: '', component: EnvioTexto},
  {path: 'pedidos', component: ResultadoTabela}
  ];
