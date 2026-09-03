package org.example.projbd2.exercicio4;

/** Abstração (DIP) para o sistema externo de pagamentos. */
public interface PagamentoService {
    void estornar(Pedido pedido);
}
