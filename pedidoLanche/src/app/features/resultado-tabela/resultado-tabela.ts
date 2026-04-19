import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PedidoService } from '../../services/pedido';
import { Pedido } from '../../models/pedido';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-resultado-tabela',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './resultado-tabela.html'
})
export class ResultadoTabela implements OnInit {

  dados: Pedido[] = [];
  filtroStatus: string = '';
  dadosFiltrados: Pedido[] = [];

  constructor(private service: PedidoService) {}

  ngOnInit() {
    this.carregar();
  }

  carregar() {
    this.service.listar().subscribe((res: Pedido[]) => {
      this.dados = res;
      this.aplicarFiltro();
    });
  }

  atualizarPedido(id: number) {
    this.service.atualizar(id).subscribe(() => {
      console.log('Pedido enviado para fila');
    });
  }

  aplicarFiltro() {
    if (!this.filtroStatus) {
      this.dadosFiltrados = this.dados;
    } else {
      this.dadosFiltrados = this.dados.filter(
        p => p.status === this.filtroStatus
      );
    }
  }
}
