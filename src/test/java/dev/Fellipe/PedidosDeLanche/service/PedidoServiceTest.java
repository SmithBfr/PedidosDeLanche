package dev.Fellipe.PedidosDeLanche.service;

import dev.Fellipe.PedidosDeLanche.infrastucture.entity.Pedido;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;


class PedidoServiceTest {

    private PedidoService pedidoService = new PedidoService(null, null);

    @Test
    void deveCalcularValorTotalSemDesconto() {
        Pedido pedido = Pedido.builder()
                .tipoLanche("Pastel")
                .proteina("Frango")
                .acompanhamento("Bacon")
                .quantidade(2)
                .bebida("Suco")
                .build();

        BigDecimal valorTotal = pedidoService.calcularValorTotal(pedido);
        assertEquals(new BigDecimal("30.00"), valorTotal);
    }

    @Test
    void deveCalcularValorTotalComDesconto() {
        Pedido pedido = Pedido.builder()
                .tipoLanche("Hamburguer")
                .proteina("Carne")
                .acompanhamento("Salada")
                .quantidade(2)
                .bebida("Coca")
                .build();

        BigDecimal valorTotal = pedidoService.calcularValorTotal(pedido);
        assertEquals(new BigDecimal("36.00"), valorTotal);
    }

    @Test
    void deveRetornarValorComDuasCasasDecimais() {
        Pedido pedido = Pedido.builder()
                .tipoLanche("Hamburguer")
                .proteina("Carne")
                .acompanhamento("Salada")
                .quantidade(1)
                .bebida("Coca")
                .build();

        BigDecimal valorTotal = pedidoService.calcularValorTotal(pedido);
        assertEquals(2, valorTotal.scale());
    }

}