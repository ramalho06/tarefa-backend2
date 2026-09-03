package org.example.projbd2.exercicio2;

import org.example.projbd2.common.exception.RegraDeNegocioException;

public class EstoqueInsuficienteException extends RegraDeNegocioException {

    public EstoqueInsuficienteException(String nomeProduto) {
        super("Estoque insuficiente" + (nomeProduto != null ? " para o produto: " + nomeProduto : ""));
    }
}
