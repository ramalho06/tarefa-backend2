package org.example.projbd2.exercicio5;

import java.math.BigDecimal;

/** Abstração (DIP) para o cálculo de frete a partir de um endereço. */
public interface FreteService {
    BigDecimal calcular(Endereco endereco, BigDecimal total);
}
