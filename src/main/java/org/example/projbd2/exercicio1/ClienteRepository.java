package org.example.projbd2.exercicio1;

import java.util.Optional;

/**
 * Abstração de persistência (DIP): o serviço de cadastro depende desta
 * interface, não de uma implementação concreta de acesso a dados.
 */
public interface ClienteRepository {

    boolean existsByEmail(String email);

    Cliente save(Cliente cliente);

    Optional<Cliente> findById(Long id);
}
