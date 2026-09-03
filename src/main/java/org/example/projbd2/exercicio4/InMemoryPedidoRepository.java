package org.example.projbd2.exercicio4;

import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository("ex4InMemoryPedidoRepository")
public class InMemoryPedidoRepository implements PedidoRepository {

    private final Map<Long, Pedido> pedidos = new ConcurrentHashMap<>();

    @Override
    public Optional<Pedido> findById(Long id) {
        return Optional.ofNullable(pedidos.get(id));
    }

    @Override
    public Pedido save(Pedido pedido) {
        pedidos.put(pedido.getId(), pedido);
        return pedido;
    }
}
