package org.example.projbd2.common.exception;

/**
 * Erro de regra de negócio (ex.: e-mail duplicado, estoque insuficiente,
 * cliente inativo). Sempre resulta em HTTP 400 - ver {@code ApiExceptionHandler}.
 * <p>
 * Cada exercício define suas próprias subclasses específicas do domínio.
 * Isso mantém o tratamento de erro em um único lugar (OCP): uma nova regra
 * de negócio não exige alterar o handler HTTP, apenas criar uma nova subclasse.
 */
public class RegraDeNegocioException extends RuntimeException {

    public RegraDeNegocioException(String message) {
        super(message);
    }
}
