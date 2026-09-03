package org.example.projbd2.exercicio3;

import org.springframework.stereotype.Service;

/**
 * O "ponto conceitual claro de responsabilidade" pedido no exercício:
 * este é o único lugar do sistema que decide se um pedido ganha frete
 * grátis. PedidoController, CarrinhoService e CheckoutService (as três
 * pontas que antes duplicavam a regra) passam a apenas chamar este serviço.
 */
@Service
public class PedidoFreteService {

    private final PoliticaFreteResolver politicaFreteResolver;

    public PedidoFreteService(PoliticaFreteResolver politicaFreteResolver) {
        this.politicaFreteResolver = politicaFreteResolver;
    }

    public void aplicarPoliticaFrete(Pedido pedido) {
        PoliticaFrete politica = politicaFreteResolver.resolverPara(pedido.getCliente().getTipo());
        if (politica.concedeFreteGratis(pedido.getTotal())) {
            pedido.zerarFrete();
        }
    }
}
