package org.example.projbd2.exercicio1;

import org.example.projbd2.common.exception.RegraDeNegocioException;

public class EmailJaCadastradoException extends RegraDeNegocioException {

    public EmailJaCadastradoException() {
        super("Email já cadastrado");
    }
}
