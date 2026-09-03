package org.example.projbd2.exercicio4;

import java.util.Optional;

public interface PedidoRepository {
    Optional<Pedido> findById(Long id);

    Pedido save(Pedido pedido);
}
