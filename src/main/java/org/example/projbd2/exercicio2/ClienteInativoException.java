package org.example.projbd2.exercicio2;

import org.example.projbd2.common.exception.RegraDeNegocioException;

public class ClienteInativoException extends RegraDeNegocioException {

    public ClienteInativoException() {
        super("Cliente inativo");
    }
}
