package org.example.projbd2.exercicio3;

import org.springframework.stereotype.Service;

/** Terceiro ponto que antes duplicava a regra de frete. Agora só delega. */
@Service
public class CheckoutService {

    private final PedidoFreteService pedidoFreteService;

    public CheckoutService(PedidoFreteService pedidoFreteService) {
        this.pedidoFreteService = pedidoFreteService;
    }

    public void finalizarCheckout(Pedido pedido) {
        pedidoFreteService.aplicarPoliticaFrete(pedido);
    }
}
