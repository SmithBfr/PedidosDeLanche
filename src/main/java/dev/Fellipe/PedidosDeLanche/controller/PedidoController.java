package dev.Fellipe.PedidosDeLanche.controller;

import dev.Fellipe.PedidosDeLanche.service.PedidoService;
import dev.Fellipe.PedidosDeLanche.infrastucture.entity.Pedido;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/pedidos")
@RequiredArgsConstructor
public class PedidoController {

    private final PedidoService service;

    @PostMapping(consumes = "text/plain")
    public ResponseEntity<Pedido> criarPedido(@RequestBody String texto) {

        if(texto.length() != 40) {
            throw new IllegalArgumentException("O texto deve conter exatamente 40 caracteres.");
        }

        Pedido pedidoCriado = service.criarPedido(texto);

        return ResponseEntity.status(HttpStatus.CREATED).body(pedidoCriado);

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
