package org.example.projbd2.exercicio5;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class LogAuditoriaService implements AuditoriaService {

    private static final Logger log = LoggerFactory.getLogger(LogAuditoriaService.class);

    @Override
    public void registrar(String evento, Long usuarioId) {
        log.info("Auditoria: {} (usuário {})", evento, usuarioId);
    }
}
