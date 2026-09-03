package org.example.projbd2.exercicio3;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/** Desafio adicional: clientes PREMIUM têm frete grátis a partir de R$ 200. */
@Component
public class FretePremiumPolicy implements PoliticaFrete {

    private static final BigDecimal LIMITE_FRETE_GRATIS = new BigDecimal("200");

    @Override
    public boolean aplicavelPara(TipoCliente tipoCliente) {
        return tipoCliente == TipoCliente.PREMIUM;
    }

    @Override
    public boolean concedeFreteGratis(BigDecimal totalPedido) {
        return totalPedido.compareTo(LIMITE_FRETE_GRATIS) >= 0;
    }
}
