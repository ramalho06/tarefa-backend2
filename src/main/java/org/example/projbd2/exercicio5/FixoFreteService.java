package org.example.projbd2.exercicio5;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/** Implementação simples de apoio; num sistema real, consultaria uma transportadora. */
@Component
public class FixoFreteService implements FreteService {

    private static final BigDecimal FRETE_PADRAO = new BigDecimal("20.00");

    @Override
    public BigDecimal calcular(Endereco endereco, BigDecimal total) {
        return FRETE_PADRAO;
    }
}
