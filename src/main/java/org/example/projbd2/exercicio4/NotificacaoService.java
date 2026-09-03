package org.example.projbd2.exercicio4;

/** Abstração (DIP) para o canal de notificação do cliente. */
public interface NotificacaoService {
    void enviarCancelamento(Pedido pedido);
}
