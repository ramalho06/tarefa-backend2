package org.example.projbd2.exercicio5;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class LogNotaFiscalService implements NotaFiscalService {

    private static final Logger log = LoggerFactory.getLogger(LogNotaFiscalService.class);

    @Override
    public void gerar(Pedido pedido) {
        log.info("Nota fiscal gerada para o pedido {}", pedido.getId());
    }
}
