package org.example.projbd2.common.web;

import org.example.projbd2.common.exception.RecursoNaoEncontradoException;
import org.example.projbd2.common.exception.RegraDeNegocioException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Ponto único de tradução de exceções de domínio para respostas HTTP.
 * <p>
 * Isso é o que permite que os Controllers de cada exercício representem
 * apenas a fronteira HTTP (Single Responsibility): eles não precisam de um
 * try/catch para cada regra de negócio, porque a tradução "exceção de domínio
 * -> status HTTP" é responsabilidade deste componente, reutilizado por todos
 * os módulos.
 */
@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(RegraDeNegocioException.class)
    public ResponseEntity<String> tratarRegraDeNegocio(RegraDeNegocioException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(RecursoNaoEncontradoException.class)
    public ResponseEntity<String> tratarRecursoNaoEncontrado(RecursoNaoEncontradoException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> tratarValidacao(MethodArgumentNotValidException ex) {
        String mensagem = ex.getBindingResult().getFieldErrors().stream()
                .findFirst()
                .map(FieldError::getDefaultMessage)
                .orElse("Dados inválidos");
        return ResponseEntity.badRequest().body(mensagem);
    }
}
