package org.example.projbd2.exercicio3;

import org.springframework.stereotype.Service;

/** Segundo ponto que antes duplicava a regra de frete. Agora só delega. */
@Service
public class CarrinhoService {

    private final PedidoFreteService pedidoFreteService;

    public CarrinhoService(PedidoFreteService pedidoFreteService) {
        this.pedidoFreteService = pedidoFreteService;
    }

    public void recalcularFrete(Pedido carrinho) {
        pedidoFreteService.aplicarPoliticaFrete(carrinho);
    }
}
