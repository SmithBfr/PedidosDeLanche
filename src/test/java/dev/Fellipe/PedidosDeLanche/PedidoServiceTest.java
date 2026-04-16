package dev.Fellipe.PedidosDeLanche;

import dev.Fellipe.PedidosDeLanche.dto.PedidoDTO;
import dev.Fellipe.PedidosDeLanche.service.PedidoService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PedidoServiceTest {

    private final PedidoService service = new PedidoService();

    @Test
    void deveConverterStringPosicionalEmPedido() {

        String input =
                String.format("%-10s", "HAMBURGUER") +
                        String.format("%-10s", "FRANGO") +
                        String.format("%-10s", "BATATA") +
                        String.format("%05d", 2) +
                        String.format("%-5s", "COCA");

        PedidoDTO pedido = service.parseString(input);

        assertEquals("HAMBURGUER", pedido.getTipoLanche());
        assertEquals("FRANGO", pedido.getProteina());
        assertEquals("BATATA", pedido.getAcompanhamento());
        assertEquals(2, pedido.getQuantidade());
        assertEquals("COCA", pedido.getBebida());
    }

    @Test
    void deveLancarErroSeStringNaoTiver40Caracteres() {
        String input = "CURTA";

        Exception exception = assertThrows(
                IllegalArgumentException.class,
                () -> service.parseString(input)
        );

        assertEquals("String deve ter 40 caracteres", exception.getMessage());
    }
}
