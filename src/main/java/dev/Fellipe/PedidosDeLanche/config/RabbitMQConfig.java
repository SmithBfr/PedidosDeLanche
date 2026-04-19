package dev.Fellipe.PedidosDeLanche.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean
    public DirectExchange exchange() {
        return new DirectExchange("pedidos.exchange");
    }

    @Bean
    public Queue fila() {
        return new Queue("pedidos.fila", true);
    }

    @Bean
    public Binding binding(Queue fila, DirectExchange exchange) {
        return BindingBuilder
                .bind(fila)
                .to(exchange)
                .with("pedidos.recebidos");
    }

}
