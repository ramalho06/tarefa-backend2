package org.example.projbd2.exercicio5;

import java.math.BigDecimal;

public class ItemCheckout {

    private BigDecimal precoUnitario;
    private int quantidade;

    public ItemCheckout() {
    }

    public ItemCheckout(BigDecimal precoUnitario, int quantidade) {
        this.precoUnitario = precoUnitario;
        this.quantidade = quantidade;
    }

    public BigDecimal calcularSubtotal() {
        return precoUnitario.multiply(BigDecimal.valueOf(quantidade));
    }
}
