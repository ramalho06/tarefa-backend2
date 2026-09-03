package org.example.projbd2.exercicio5;

import org.example.projbd2.common.exception.RegraDeNegocioException;

public class CupomInvalidoException extends RegraDeNegocioException {

    public CupomInvalidoException() {
        super("Cupom inválido");
    }
}
