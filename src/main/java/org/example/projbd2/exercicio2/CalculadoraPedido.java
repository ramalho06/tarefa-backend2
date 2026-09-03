package org.example.projbd2.exercicio2;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

/**
 * Responsabilidade única: somar os subtotais dos itens em um total do
 * pedido. Não conhece estoque, cliente ou persistência - por isso é
 * trivialmente testável isoladamente, sem mocks de repositório.
 */
@Component
public class CalculadoraPedido {

    public BigDecimal calcularTotal(List<ItemPedido> itens) {
        return itens.stream()
                .map(ItemPedido::calcularSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
