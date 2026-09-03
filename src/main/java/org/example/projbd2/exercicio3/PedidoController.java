package org.example.projbd2.exercicio3;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** Um dos três pontos que antes continha a regra de frete duplicada. Agora só delega. */
@RestController("ex3PedidoController")
@RequestMapping("/pedidos")
public class PedidoController {

    private final PedidoFreteService pedidoFreteService;

    public PedidoController(PedidoFreteService pedidoFreteService) {
        this.pedidoFreteService = pedidoFreteService;
    }

    @PostMapping("/frete")
    public ResponseEntity<Pedido> calcularFrete(@RequestBody Pedido pedido) {
        pedidoFreteService.aplicarPoliticaFrete(pedido);
        return ResponseEntity.ok(pedido);
    }
}
