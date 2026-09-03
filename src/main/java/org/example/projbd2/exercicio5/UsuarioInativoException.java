package org.example.projbd2.exercicio5;

import org.example.projbd2.common.exception.RegraDeNegocioException;

public class UsuarioInativoException extends RegraDeNegocioException {

    public UsuarioInativoException() {
        super("Usuário inativo");
    }
}
