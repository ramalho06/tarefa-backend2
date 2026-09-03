package org.example.projbd2.exercicio5;

import org.example.projbd2.common.exception.RecursoNaoEncontradoException;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/** Responsabilidade única: resolver, validar e aplicar o desconto de um cupom. */
@Component
public class CupomService {

    private final CupomRepository cupomRepository;

    public CupomService(CupomRepository cupomRepository) {
        this.cupomRepository = cupomRepository;
    }

    /** Retorna {@code null} quando nenhum código de cupom foi informado (comportamento original). */
    public Cupom resolver(String codigo) {
        if (codigo == null) {
            return null;
        }

        Cupom cupom = cupomRepository.findByCodigo(codigo)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Cupom não encontrado"));

        if (!cupom.isValido()) {
            throw new CupomInvalidoException();
        }

        return cupom;
    }

    public BigDecimal aplicarDesconto(BigDecimal total, Cupom cupom) {
        if (cupom == null) {
            return total;
        }
        return total.subtract(cupom.calcularDesconto(total));
    }
}
