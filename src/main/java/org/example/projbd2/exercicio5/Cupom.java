package org.example.projbd2.exercicio5;

import java.math.BigDecimal;

/** O cupom mantém consigo a regra de como calcular seu próprio desconto. */
public class Cupom {

    private String codigo;
    private boolean valido;
    private BigDecimal percentualDesconto;

    public Cupom() {
    }

    public Cupom(String codigo, boolean valido, BigDecimal percentualDesconto) {
        this.codigo = codigo;
        this.valido = valido;
        this.percentualDesconto = percentualDesconto;
    }

    public boolean isValido() {
        return valido;
    }

    public BigDecimal calcularDesconto(BigDecimal total) {
        return total.multiply(percentualDesconto);
    }

    public String getCodigo() {
        return codigo;
    }
}
