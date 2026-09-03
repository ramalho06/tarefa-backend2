package org.example.projbd2.common.exception;

/**
 * Lançada quando uma busca por identificador (cliente, produto, pedido, etc.)
 * não encontra o recurso. Sempre resulta em HTTP 404.
 */
public class RecursoNaoEncontradoException extends RuntimeException {

    public RecursoNaoEncontradoException(String message) {
        super(message);
    }
}