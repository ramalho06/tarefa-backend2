package org.example.projbd2.exercicio5;

import java.math.BigDecimal;

/** Abstração (DIP) para o sistema externo de pagamentos. */
public interface PagamentoService {
    void processar(Usuario usuario, BigDecimal total);
}
