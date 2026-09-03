package org.example.projbd2.exercicio5;

/** Abstração (DIP) para o registro de auditoria. */
public interface AuditoriaService {
    void registrar(String evento, Long usuarioId);
}
