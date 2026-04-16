package dev.Fellipe.PedidosDeLanche.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tipoLanche;
    private String proteina;
    private String acompanhamento;
    private int quantidade;
    private String bebida;

    private double valorTotal;

    private String status;

    private LocalDateTime dataPedido = LocalDateTime.now();


}
