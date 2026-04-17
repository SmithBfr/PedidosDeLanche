package dev.Fellipe.PedidosDeLanche.controller;

import dev.Fellipe.PedidosDeLanche.business.PedidoService;
import dev.Fellipe.PedidosDeLanche.infrastucture.entity.Pedido;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedidos")
@RequiredArgsConstructor
public class PedidoController {

    private final PedidoService service;

    @PostMapping(consumes = "text/plain")
    public double criarPedido(@RequestBody String texto) {

        if(texto.length() != 40) {
            throw new IllegalArgumentException("O texto deve conter pelo exatamente 40 caracteres.");
        }

        String tipoLanche = texto.substring(0,10).trim();
        String proteina = texto.substring(10,20).trim();
        String acompanhamento = texto.substring(20,30).trim();
        int quantidade = Integer.parseInt(texto.substring(30,32).trim());
        String bebida = texto.substring(32,40).trim();

        Pedido pedido = Pedido.builder()
                .tipoLanche(tipoLanche)
                .proteina(proteina)
                .acompanhamento(acompanhamento)
                .quantidade(quantidade)
                .bebida(bebida)
                .criadoEm(java.time.LocalDateTime.now())
                .status("RECEBIDO")
                .build();

        return service.calcularValorTotal(pedido);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pedido> buscarPedidoPorId(@PathVariable Long id) {
        Pedido pedido = service.buscarPedidoPorId(id);
        return ResponseEntity.ok(pedido);

    }

    @GetMapping
    public ResponseEntity<List<Pedido>> buscarTodosPedidos() {
        List<Pedido> pedidos = service.buscarTodosPedidos();
        return ResponseEntity.ok(pedidos);
    }

}
