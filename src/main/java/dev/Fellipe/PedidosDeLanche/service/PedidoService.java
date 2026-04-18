package dev.Fellipe.PedidosDeLanche.service;

import dev.Fellipe.PedidosDeLanche.infrastucture.entity.Pedido;
import dev.Fellipe.PedidosDeLanche.infrastucture.repository.PedidoRepository;
import dev.Fellipe.PedidosDeLanche.messaging.PedidoProducer;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PedidoService {

    private final PedidoRepository repository;
    private final PedidoProducer pedidoProducer;



    public PedidoService(PedidoRepository repository, PedidoProducer producer) {
        this.repository = repository;
        this.pedidoProducer = producer;
    }

    public void criarPedido(Pedido pedido) {

        double valor = calcularValorTotal(pedido);

        Pedido pedidos = Pedido.builder()
                .tipoLanche(pedido.getTipoLanche())
                .proteina(pedido.getProteina())
                .acompanhamento(pedido.getAcompanhamento())
                .quantidade(pedido.getQuantidade())
                .status("RECEBIDO")
                .build();

        pedidos.setValorTotal(valor);

        Pedido pedidoSalvo = repository.save(pedidos);
        pedidoProducer.enviarPedido(pedidoSalvo.getPedidoID());
        System.out.println("SALVO ID: " + pedidoSalvo.getPedidoID());

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


        return valorTipoLanche * pedido.getQuantidade();

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
        repository.save(pedido);
    }

}
