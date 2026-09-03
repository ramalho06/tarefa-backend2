package org.example.projbd2.exercicio5;

import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository("ex5InMemoryPedidoRepository")
public class InMemoryPedidoRepository implements PedidoRepository {

    private final Map<Long, Pedido> pedidos = new ConcurrentHashMap<>();
    private final AtomicLong sequencia = new AtomicLong(0);

    @Override
    public Pedido save(Pedido pedido) {
        if (pedido.getId() == null) {
            pedido.setId(sequencia.incrementAndGet());
        }
        pedidos.put(pedido.getId(), pedido);
        return pedido;
    }
}
