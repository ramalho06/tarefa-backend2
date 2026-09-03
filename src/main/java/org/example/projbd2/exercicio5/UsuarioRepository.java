package org.example.projbd2.exercicio5;

import java.util.Optional;

public interface UsuarioRepository {
    Optional<Usuario> findById(Long id);
}
