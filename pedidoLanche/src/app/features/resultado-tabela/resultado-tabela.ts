import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PedidoService } from '../../services/pedido';
import { Pedido } from '../../models/pedido';

@Component({
  selector: 'app-resultado-tabela',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './resultado-tabela.html'
})
export class ResultadoTabela implements OnInit {

  dados: Pedido[] = [];

  constructor(private service: PedidoService) {}

  ngOnInit() {
    this.carregar();
  }

  carregar() {
    this.service.listar().subscribe((res: Pedido[]) => {
      this.dados = res;
    });
  }

  atualizarPedido(id: number) {
    this.service.atualizar(id).subscribe(() => {
      console.log('Pedido enviado para fila');
    });
  }
}
