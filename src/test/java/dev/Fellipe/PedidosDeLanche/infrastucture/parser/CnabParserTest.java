package dev.Fellipe.PedidosDeLanche.infrastucture.parser;

import dev.Fellipe.PedidosDeLanche.infrastucture.entity.Pedido;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CnabParserTest {

    private final CnabParser parser = new CnabParser();

    @Test
    void deveParsearPedidoCorretamente() {
        String texto = "HamburguerCarne     Salada    01Coca";
        Pedido pedido = parser.parse(texto);

        assertEquals("HAMBURGUER", pedido.getTipoLanche());
        assertEquals("CARNE", pedido.getProteina());
        assertEquals("SALADA", pedido.getAcompanhamento());
        assertEquals(1, pedido.getQuantidade());
        assertEquals("COCA", pedido.getBebida());
    }

    @Test
    void deveTratarQuantidadeInvalidaComoZero() {
        String entrada = "hamburguercarnesaladaXXcoca";

        Pedido pedido = parser.parse(entrada);

        assertEquals(0, pedido.getQuantidade());
    }

}