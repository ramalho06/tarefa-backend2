package org.example.projbd2.exercicio2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/** Implementação de apoio: apenas registra o envio, no lugar de um provedor real. */
@Component("ex2LogEmailService")
public class LogEmailService implements EmailService {

    private static final Logger log = LoggerFactory.getLogger(LogEmailService.class);

    @Override
    public void enviarConfirmacao(Pedido pedido) {
        log.info("E-mail de confirmação enviado para o pedido {}", pedido.getId());
    }
}
