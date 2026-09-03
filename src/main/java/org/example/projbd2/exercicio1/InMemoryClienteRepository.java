package org.example.projbd2.exercicio1;

import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Implementação em memória do repositório, apenas para o projeto ser
 * executável/testável de ponta a ponta sem depender de um banco de dados
 * real. Numa aplicação real, esta classe seria substituída por um adaptador
 * Spring Data JPA - o restante do sistema não muda, pois depende apenas de
 * {@link ClienteRepository}.
 */
@Repository("ex1InMemoryClienteRepository")
public class InMemoryClienteRepository implements ClienteRepository {

    private final Map<Long, Cliente> clientes = new ConcurrentHashMap<>();
    private final AtomicLong sequencia = new AtomicLong(0);

    @Override
    public boolean existsByEmail(String email) {
        return clientes.values().stream()
                .anyMatch(cliente -> cliente.getEmail().equalsIgnoreCase(email));
    }

    @Override
    public Cliente save(Cliente cliente) {
        if (cliente.getId() == null) {
            cliente.setId(sequencia.incrementAndGet());
        }
        clientes.put(cliente.getId(), cliente);
        return cliente;
    }

    @Override
    public Optional<Cliente> findById(Long id) {
        return Optional.ofNullable(clientes.get(id));
    }
}
