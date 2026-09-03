package org.example.projbd2.exercicio4;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class LogNotificacaoService implements NotificacaoService {

    private static final Logger log = LoggerFactory.getLogger(LogNotificacaoService.class);

    @Override
    public void enviarCancelamento(Pedido pedido) {
        log.info("Notificação de cancelamento enviada para o pedido {}", pedido.getId());
    }
}
