package org.example.projbd2.exercicio2;

import java.util.Optional;

public interface ClienteRepository {
    Optional<Cliente> findById(Long id);
}
