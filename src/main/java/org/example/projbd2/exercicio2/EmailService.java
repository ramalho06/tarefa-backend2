package org.example.projbd2.exercicio2;

/**
 * Abstração para o envio de e-mail (DIP): o caso de uso depende desta
 * interface, nunca de um provedor de e-mail concreto (SMTP, SES, etc.).
 */
public interface EmailService {
    void enviarConfirmacao(Pedido pedido);
}
