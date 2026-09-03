package org.example.projbd2.exercicio5;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/** Responsabilidade única: somar os itens do checkout em um valor total, sem descontos ou frete. */
@Component
public class CalculadoraCheckoutService {

    public BigDecimal calcularTotal(CheckoutRequest request) {
        return request.getItens().stream()
                .map(ItemCheckout::calcularSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
