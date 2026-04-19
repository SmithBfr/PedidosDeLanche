package dev.Fellipe.PedidosDeLanche.messaging;

import dev.Fellipe.PedidosDeLanche.infrastucture.entity.Pedido;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;

@Component
public class PedidoProducer {

    private final RabbitTemplate rabbitTemplate;

    public PedidoProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void enviarPedido(Long id) {

        rabbitTemplate.convertAndSend(
                "pedidos.exchange",
                "pedidos.recebidos",
                "{\"pedidoId\": " + id + "}"
        );

        System.out.println("ENVIANDO: " + id);
    }

}
