package dev.Fellipe.PedidosDeLanche.messaging;

import dev.Fellipe.PedidosDeLanche.infrastucture.entity.Pedido;
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

        Long id = Long.valueOf(mensagem.replaceAll("\\D", ""));
        System.out.println("Mensagem recebida: " + id);
        pedidoService.atualizarStatusPedido(id);
    }

}
