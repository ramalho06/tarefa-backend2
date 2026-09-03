package org.example.projbd2.exercicio4;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** Controller = fronteira HTTP. Nenhuma decisão de negócio é tomada aqui. */
@RestController("ex4PedidoController")
@RequestMapping("/pedidos")
public class PedidoController {

    private final CancelarPedidoService cancelarPedidoService;

    public PedidoController(CancelarPedidoService cancelarPedidoService) {
        this.cancelarPedidoService = cancelarPedidoService;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelar(@PathVariable Long id) {
        cancelarPedidoService.cancelar(id);
        return ResponseEntity.noContent().build();
    }
}
