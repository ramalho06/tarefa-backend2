package org.example.projbd2.exercicio3;

import java.math.BigDecimal;

/**
 * Ponto único de verdade da regra "quando o pedido tem frete grátis".
 * <p>
 * Antes, a mesma condição (total >= 300) estava copiada em três lugares
 * (PedidoController, CarrinhoService, CheckoutService). Quando a política
 * mudou (frete grátis acima de R$ 500, e depois diferenciada por tipo de
 * cliente), seria preciso lembrar de alterar os três pontos.
 * <p>
 * Aqui a regra vira uma abstração (Strategy). Cada tipo de cliente tem sua
 * própria implementação, e adicionar um novo tipo de cliente no futuro
 * significa apenas criar uma nova classe - nenhum código existente precisa
 * ser modificado (Open/Closed Principle).
 */
public interface PoliticaFrete {

    boolean aplicavelPara(TipoCliente tipoCliente);

    boolean concedeFreteGratis(BigDecimal totalPedido);
}
