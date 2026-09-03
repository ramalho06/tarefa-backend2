package org.example.projbd2.exercicio5;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/** Responsabilidade única: criar e persistir o pedido resultante do checkout. */
@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;

    public PedidoService(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    public Pedido criar(Usuario usuario, CheckoutRequest request, BigDecimal total) {
        Pedido pedido = new Pedido(usuario.getId(), total);
        return pedidoRepository.save(pedido);
    }
}
