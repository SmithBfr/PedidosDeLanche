package dev.Fellipe.PedidosDeLanche.infrastucture.parser.model;

import dev.Fellipe.PedidosDeLanche.enums.TipoCampo;

import java.util.List;

public record CampoLayout(String nome, int tamanho, TipoCampo tipoCampo) {

    public static final List<CampoLayout> LAYOUT = List.of(
            new CampoLayout("tipoLanche", 10, TipoCampo.A),
            new CampoLayout("proteina", 10, TipoCampo.A),
            new CampoLayout("acompanhamento", 10, TipoCampo.A),
            new CampoLayout("quantidade", 2, TipoCampo.N),
            new CampoLayout("bebida", 8, TipoCampo.A)
    );
}
