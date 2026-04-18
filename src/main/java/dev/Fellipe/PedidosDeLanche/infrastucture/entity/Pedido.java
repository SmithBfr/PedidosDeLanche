package dev.Fellipe.PedidosDeLanche.infrastucture.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "pedidos")
@Entity
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pedidoID;

    private String tipoLanche;

    private String proteina;

    private String acompanhamento;

    private int quantidade;

    private String bebida;

    private Double valorTotal;

    private String status;

    private LocalDateTime criadoEm = LocalDateTime.now();


}
