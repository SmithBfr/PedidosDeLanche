package dev.Fellipe.PedidosDeLanche.messaging;

import dev.Fellipe.PedidosDeLanche.service.PedidoService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class PedidoConsumer {

    private final PedidoService pedidoService;

    public PedidoConsumer(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @RabbitListener(queues = "pedidos.fila")
    public void consumir(String mensagem) {

        Long pedidoID = Long.valueOf(
                mensagem.replaceAll("\\D", "")
        );

        pedidoService.atualizarStatusPedido(pedidoID);
    }

}
