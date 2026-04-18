package dev.Fellipe.PedidosDeLanche.messaging;

import dev.Fellipe.PedidosDeLanche.infrastucture.entity.Pedido;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class PedidoProducer {

    private final RabbitTemplate rabbitTemplate;

    public PedidoProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void enviarPedido(Long pedidoID) {

        String mensagem = "{\"pedidoId\": " + pedidoID + "}";


        rabbitTemplate.convertAndSend(
                "pedidos.exchange",
                "pedidos.recebidos",
                mensagem);

        System.out.println("ENVIANDO: " + mensagem);
    }

}
