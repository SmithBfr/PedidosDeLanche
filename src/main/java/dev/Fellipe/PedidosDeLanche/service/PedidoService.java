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

    public Pedido criarPedido(String pedido) {

        String tipoLanche = pedido.substring(0,10).trim();
        String proteina = pedido.substring(10,20).trim();
        String acompanhamento = pedido.substring(20,30).trim();
        int quantidade = Integer.parseInt(pedido.substring(30,32).trim());
        String bebida = pedido.substring(32,40).trim();

        Pedido pedidos = Pedido.builder()
                .tipoLanche(tipoLanche)
                .proteina(proteina)
                .acompanhamento(acompanhamento)
                .quantidade(quantidade)
                .bebida(bebida)
                .valorTotal(0.0)
                .status("RECEBIDO")
                .build();

        pedidos.setValorTotal(calcularValorTotal(pedidos));

        Pedido pedidoSalvo = repository.save(pedidos);
        return pedidoSalvo;
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
        pedidoProducer.enviarPedido(id);

    }

    public void enviarPedidoParaFila(Long id) {
        pedidoProducer.enviarPedido(id);
    }
}
