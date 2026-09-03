package org.example.projbd2.exercicio4;

import org.example.projbd2.common.exception.RegraDeNegocioException;

public class CancelamentoNaoPermitidoException extends RegraDeNegocioException {

    public CancelamentoNaoPermitidoException(String message) {
        super(message);
    }
}
