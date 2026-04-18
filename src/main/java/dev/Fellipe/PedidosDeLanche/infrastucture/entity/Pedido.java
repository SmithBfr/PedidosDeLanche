package dev.Fellipe.PedidosDeLanche.infrastucture.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

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

    @Column(length = 20)
    private String tipoLanche;

    @Column(length = 20)
    private String proteina;

    @Column(length = 20)
    private String acompanhamento;

    @Column(length = 20)
    private int quantidade;

    @Column(length = 20)
    private String bebida;


    private Double valorTotal;

    @Column(length = 20)
    private String status;

    @CreationTimestamp
    private LocalDateTime criadoEm = LocalDateTime.now();


}
