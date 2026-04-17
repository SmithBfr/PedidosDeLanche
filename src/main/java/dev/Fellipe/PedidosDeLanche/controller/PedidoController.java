package dev.Fellipe.PedidosDeLanche.controller;

import dev.Fellipe.PedidosDeLanche.business.PedidoService;
import dev.Fellipe.PedidosDeLanche.infrastucture.entity.Pedido;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pedidos")
@RequiredArgsConstructor
public class PedidoController {

    private final PedidoService service;

    @PostMapping
    public ResponseEntity<Void> criarPedido(@RequestBody Pedido pedido) {
        service.criarPedido(pedido);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<Pedido> buscarPedidoPorId(@RequestParam Long id) {
        Pedido pedido = service.buscarPedidoPorId(id);
        return ResponseEntity.ok(pedido);
    }



}
