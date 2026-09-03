package org.example.projbd2.exercicio5;

import org.example.projbd2.common.exception.RecursoNaoEncontradoException;
import org.springframework.stereotype.Component;

/** Responsabilidade única: buscar o usuário do checkout e garantir que está apto a comprar. */
@Component
public class UsuarioValidator {

    private final UsuarioRepository usuarioRepository;

    public UsuarioValidator(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public Usuario validarEBuscar(Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário não encontrado"));

        if (!usuario.isAtivo()) {
            throw new UsuarioInativoException();
        }

        return usuario;
    }
}
