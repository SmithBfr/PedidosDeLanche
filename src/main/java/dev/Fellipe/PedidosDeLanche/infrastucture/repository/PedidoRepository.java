package dev.Fellipe.PedidosDeLanche.infrastucture.repository;

import dev.Fellipe.PedidosDeLanche.infrastucture.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
}
