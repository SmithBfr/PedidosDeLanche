package dev.Fellipe.PedidosDeLanche.service;

import dev.Fellipe.PedidosDeLanche.infrastucture.entity.Pedido;
import dev.Fellipe.PedidosDeLanche.infrastucture.parser.CnabParser;
import dev.Fellipe.PedidosDeLanche.infrastucture.repository.PedidoRepository;
import dev.Fellipe.PedidosDeLanche.messaging.PedidoProducer;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


class PedidoServiceTest {

    private PedidoService pedidoService = new PedidoService(null, null);
    private final CnabParser parser = new CnabParser();

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

    @Test
    void deveCriarPedido() {
        PedidoRepository repo = mock(PedidoRepository.class);
        PedidoProducer producer = mock(PedidoProducer.class);

        PedidoService service = new PedidoService(repo, producer);

        when(repo.save(any())).thenAnswer(i -> i.getArgument(0));

        Pedido pedido = service.criarPedido("hamburguercarne     salada    02coca");

        assertEquals("RECEBIDO", pedido.getStatus());
        assertEquals(2, pedido.getQuantidade());
    }

}