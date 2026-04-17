package dev.Fellipe.PedidosDeLanche.business;

import dev.Fellipe.PedidosDeLanche.infrastucture.entity.Pedido;
import dev.Fellipe.PedidosDeLanche.infrastucture.repository.PedidoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PedidoService {

    private final PedidoRepository repository;

    public PedidoService(PedidoRepository repository) {
        this.repository = repository;
    }

    public void criarPedido(Pedido pedido) {
        Pedido pedidos = Pedido.builder()
                .tipoLanche(pedido.getTipoLanche())
                .proteina(pedido.getProteina())
                .acompanhamento(pedido.getAcompanhamento())
                .quantidade(pedido.getQuantidade())
                .valorTotal(calcularValorTotal(pedido))
                .status("RECEBIDO")
                .build();

        repository.save(pedidos);
    }

    public double calcularValorTotal(Pedido pedido) {

        double valorTipoLanche = 0.0;

        String tipo = pedido.getTipoLanche();
        if (tipo != null) {
            tipo = tipo.toLowerCase();
        }

        if("hamburguer".equals(tipo)) {
            valorTipoLanche = 20.0;
        } else if ("pastel".equals(tipo)) {
            valorTipoLanche = 15.0;
        } else {
            valorTipoLanche = 12.0;
        }

        if ("hamburguer".equalsIgnoreCase(pedido.getTipoLanche()) &&
                "carne".equalsIgnoreCase(pedido.getProteina()) &&
                "salada".equalsIgnoreCase(pedido.getAcompanhamento())) {

            valorTipoLanche -= valorTipoLanche * 0.10;
        }

        pedido.setValorTotal(valorTipoLanche * pedido.getQuantidade());

        repository.save(pedido);

        return pedido.getValorTotal();

    }

    public Pedido buscarPedidoPorId(Long id) {

        return repository.findById(id).orElseThrow(
                () -> new RuntimeException("Pedido não encontrado"));
    }

    public List<Pedido> buscarTodosPedidos() {
        return repository.findAll();
    }
}
