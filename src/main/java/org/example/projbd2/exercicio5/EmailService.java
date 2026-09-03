package org.example.projbd2.exercicio5;

/** Abstração (DIP) para o envio de e-mail. */
public interface EmailService {
    void enviar(Pedido pedido);
}
