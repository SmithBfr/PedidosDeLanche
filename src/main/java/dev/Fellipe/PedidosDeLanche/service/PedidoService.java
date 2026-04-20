package dev.Fellipe.PedidosDeLanche.service;

import dev.Fellipe.PedidosDeLanche.infrastucture.entity.Pedido;
import dev.Fellipe.PedidosDeLanche.infrastucture.parser.CnabParser;
import dev.Fellipe.PedidosDeLanche.infrastucture.repository.PedidoRepository;
import dev.Fellipe.PedidosDeLanche.messaging.PedidoProducer;
import jakarta.transaction.Transactional;
import lombok.val;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional
public class PedidoService {

    private final PedidoRepository repository;
    private final PedidoProducer pedidoProducer;
    private final CnabParser parser = new CnabParser();



    public PedidoService(PedidoRepository repository, PedidoProducer producer) {
        this.repository = repository;
        this.pedidoProducer = producer;
    }


    public Pedido criarPedido(String entrada) {

        Pedido pedido = parser.parse(entrada);
        pedido.setValorTotal(calcularValorTotal(pedido));
        pedido.setStatus("RECEBIDO");

        return repository.save(pedido);
    }

    public BigDecimal calcularValorTotal(Pedido pedido) {

        BigDecimal valorTipoLanche = new BigDecimal(0.0);

        String tipo = pedido.getTipoLanche();
        if (tipo != null) {
            tipo = tipo.toLowerCase();
        }

        if("hamburguer".equals(tipo)) {
            valorTipoLanche = new BigDecimal(20.0);
        } else if ("pastel".equals(tipo)) {
            valorTipoLanche = new BigDecimal(15.0);
        } else {
            valorTipoLanche = new BigDecimal(12.0);
        }

        if ("hamburguer".equalsIgnoreCase(pedido.getTipoLanche()) &&
                "carne".equalsIgnoreCase(pedido.getProteina()) &&
                "salada".equalsIgnoreCase(pedido.getAcompanhamento())) {

            valorTipoLanche = valorTipoLanche.subtract(
                    valorTipoLanche.multiply(BigDecimal.valueOf(0.10))
            );
        }

        valorTipoLanche = valorTipoLanche.multiply(BigDecimal.valueOf(pedido.getQuantidade()));

        valorTipoLanche = valorTipoLanche.setScale(2, BigDecimal.ROUND_HALF_UP);

        pedido.setValorTotal(valorTipoLanche);

        return pedido.getValorTotal();

    }

    public Pedido buscarPedidoPorId(Long id) {

        return repository.findById(id).orElseThrow(
                () -> new RuntimeException("Pedido não encontrado"));
    }

    public List<Pedido> buscarTodosPedidos() {
        return repository.findAll();
    }


    public void atualizarStatusPedido(Long id) {
        Pedido pedido = repository.findById(id).orElseThrow(
                () -> new RuntimeException("Pedido não encontrado"));

        pedido.setStatus("ENTREGUE");
        System.out.println("Atualizando status do pedido " + id + " para ENTREGUE");
        repository.save(pedido);

    }

    public void enviarParaFila(Long id) {
        Pedido pedido = repository.findById(id).orElseThrow(
                () -> new RuntimeException("Pedido não encontrado"));

        pedidoProducer.enviarPedido(pedido.getPedidoID());
    }

}
