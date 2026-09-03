package org.example.projbd2.exercicio5;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("ex5LogEmailService")
public class LogEmailService implements EmailService {

    private static final Logger log = LoggerFactory.getLogger(LogEmailService.class);

    @Override
    public void enviar(Pedido pedido) {
        log.info("E-mail de confirmação do pedido {} enviado", pedido.getId());
    }
}
