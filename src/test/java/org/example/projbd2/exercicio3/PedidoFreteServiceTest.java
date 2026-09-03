package org.example.projbd2.exercicio3;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class PedidoFreteServiceTest {

    private final PedidoFreteService service = new PedidoFreteService(
            new PoliticaFreteResolver(List.of(new FretePadraoPolicy(), new FretePremiumPolicy())));

    @Test
    void clienteComumComPedidoAcimaDe500GanhaFreteGratis() {
        Pedido pedido = new Pedido(new Cliente(TipoCliente.COMUM), new BigDecimal("500.00"), new BigDecimal("30.00"));

        service.aplicarPoliticaFrete(pedido);

        assertThat(pedido.getFrete()).isEqualByComparingTo("0");
    }

    @Test
    void clienteComumComPedidoAbaixoDe500NaoGanhaFreteGratis() {
        Pedido pedido = new Pedido(new Cliente(TipoCliente.COMUM), new BigDecimal("499.99"), new BigDecimal("30.00"));

        service.aplicarPoliticaFrete(pedido);

        assertThat(pedido.getFrete()).isEqualByComparingTo("30.00");
    }

    @Test
    void clientePremiumComPedidoAcimaDe200JaGanhaFreteGratis() {
        Pedido pedido = new Pedido(new Cliente(TipoCliente.PREMIUM), new BigDecimal("200.00"), new BigDecimal("30.00"));

        service.aplicarPoliticaFrete(pedido);

        assertThat(pedido.getFrete()).isEqualByComparingTo("0");
    }
}
