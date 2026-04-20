package dev.Fellipe.PedidosDeLanche.infrastucture.parser;

import dev.Fellipe.PedidosDeLanche.enums.TipoCampo;
import dev.Fellipe.PedidosDeLanche.infrastucture.entity.Pedido;
import dev.Fellipe.PedidosDeLanche.infrastucture.parser.model.CampoLayout;

import static dev.Fellipe.PedidosDeLanche.infrastucture.parser.model.CampoLayout.LAYOUT;

public class CnabParser {

    private String limpar(String valor, TipoCampo tipo) {
        if (tipo == TipoCampo.N) {
            return valor.replaceAll("\\D", "");
        }
        return valor.trim();
    }

    private String preencher(String valor, int tamanho, TipoCampo tipo) {

        StringBuilder sb = new StringBuilder(valor);

        while (sb.length() < tamanho) {
            if (tipo == TipoCampo.N) {
                sb.insert(0, "0"); // numérico → esquerda
            } else {
                sb.append(" "); // alfanumérico → direita
            }
        }
        return sb.toString();
    }

    public String formatar(String entrada) {

        if (entrada == null) {
            entrada = "";
        }

        if (entrada.length() > 40) {
            throw new IllegalArgumentException("Linha deve ter no máximo 40 caracteres");
        }

        return preencher(entrada, 40, TipoCampo.A); // linha inteira como base A
    }

    public Pedido parse(String entrada) {

        String linha = formatar(entrada);

        int pos = 0;

        String tipoLanche = null;
        String proteina = null;
        String acompanhamento = null;
        int quantidade = 0;
        String bebida = null;

        for (CampoLayout campo : LAYOUT) {

            int fim = pos + campo.tamanho();

            String valor = linha.substring(pos, fim);

            valor = limpar(valor, campo.tipoCampo());

            switch (campo.nome()) {
                case "tipoLanche" -> tipoLanche = valor;
                case "proteina" -> proteina = valor;
                case "acompanhamento" -> acompanhamento = valor;
                case "quantidade" -> quantidade = Integer.parseInt(valor.isBlank() ? "0" : valor);
                case "bebida" -> bebida = valor;
            }

            pos += campo.tamanho();
        }

        return Pedido.builder()
                .tipoLanche(tipoLanche)
                .proteina(proteina)
                .acompanhamento(acompanhamento)
                .quantidade(quantidade)
                .bebida(bebida)
                .build();

    }
}
