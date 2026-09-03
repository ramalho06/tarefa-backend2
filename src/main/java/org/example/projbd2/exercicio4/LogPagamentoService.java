package org.example.projbd2.exercicio4;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("ex4LogPagamentoService")
public class LogPagamentoService implements PagamentoService {

    private static final Logger log = LoggerFactory.getLogger(LogPagamentoService.class);

    @Override
    public void estornar(Pedido pedido) {
        log.info("Pagamento do pedido {} estornado", pedido.getId());
    }
}
