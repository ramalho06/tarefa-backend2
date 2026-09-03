package org.example.projbd2.exercicio3;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/** Regra vigente após a mudança solicitada: frete grátis acima de R$ 500 para clientes comuns. */
@Component
public class FretePadraoPolicy implements PoliticaFrete {

    private static final BigDecimal LIMITE_FRETE_GRATIS = new BigDecimal("500");

    @Override
    public boolean aplicavelPara(TipoCliente tipoCliente) {
        return tipoCliente == TipoCliente.COMUM;
    }

    @Override
    public boolean concedeFreteGratis(BigDecimal totalPedido) {
        return totalPedido.compareTo(LIMITE_FRETE_GRATIS) >= 0;
    }
}
