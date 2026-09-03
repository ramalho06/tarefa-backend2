package org.example.projbd2.exercicio5;

import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class InMemoryUsuarioRepository implements UsuarioRepository {

    private final Map<Long, Usuario> usuarios = new ConcurrentHashMap<>();

    @Override
    public Optional<Usuario> findById(Long id) {
        return Optional.ofNullable(usuarios.get(id));
    }

    public void salvar(Usuario usuario) {
        usuarios.put(usuario.getId(), usuario);
    }
}
