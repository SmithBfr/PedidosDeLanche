package dev.Fellipe.PedidosDeLanche;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableRabbit
public class PedidosDeLancheApplication {

	public static void main(String[] args) {
		SpringApplication.run(PedidosDeLancheApplication.class, args);
	}

}
