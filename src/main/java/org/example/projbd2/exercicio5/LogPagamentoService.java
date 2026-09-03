package org.example.projbd2.exercicio5;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component("ex5LogPagamentoService")
public class LogPagamentoService implements PagamentoService {

    private static final Logger log = LoggerFactory.getLogger(LogPagamentoService.class);

    @Override
    public void processar(Usuario usuario, BigDecimal total) {
        log.info("Pagamento de {} processado para o usuário {}", total, usuario.getId());
    }
}
