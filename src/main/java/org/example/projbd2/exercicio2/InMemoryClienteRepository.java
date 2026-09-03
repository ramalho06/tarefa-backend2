package org.example.projbd2.exercicio2;

import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository("ex2InMemoryClienteRepository")
public class InMemoryClienteRepository implements ClienteRepository {

    private final Map<Long, Cliente> clientes = new ConcurrentHashMap<>();

    @Override
    public Optional<Cliente> findById(Long id) {
        return Optional.ofNullable(clientes.get(id));
    }

    /** Utilitário de apoio para testes/demonstração. */
    public void salvar(Cliente cliente) {
        clientes.put(cliente.getId(), cliente);
    }
}
