package org.example.projbd2.exercicio2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CriarPedidoServiceTest {

    private final ClienteRepository clienteRepository = mock(ClienteRepository.class);
    private final ProdutoRepository produtoRepository = mock(ProdutoRepository.class);
    private final PedidoRepository pedidoRepository = mock(PedidoRepository.class);
    private final EmailService emailService = mock(EmailService.class);

    private final CriarPedidoService service = new CriarPedidoService(
            clienteRepository,
            produtoRepository,
            pedidoRepository,
            new ValidadorClientePedido(),
            new EstoqueService(produtoRepository),
            new CalculadoraPedido(),
            emailService
    );

    private Cliente clienteAtivo;
    private Produto produto;

    @BeforeEach
    void setUp() {
        clienteAtivo = new Cliente(1L);
        clienteAtivo.setAtivo(true);
        produto = new Produto(10L, "Mouse", new BigDecimal("50.00"), 5);

        when(clienteRepository.findById(1L)).thenReturn(Optional.of(clienteAtivo));
        when(produtoRepository.findById(10L)).thenReturn(Optional.of(produto));
        when(produtoRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));
        when(pedidoRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));
    }

    private Pedido pedidoSolicitado(int quantidade) {
        Pedido pedido = new Pedido(new Cliente(1L), List.of(new ItemPedido(new Produto(10L), quantidade)));
        return pedido;
    }

    @Test
    void deveCriarPedidoCalculandoTotalDebitandoEstoqueEEnviandoEmail() {
        Pedido salvo = service.criarPedido(pedidoSolicitado(2));

        assertThat(salvo.getTotal()).isEqualByComparingTo("100.00");
        assertThat(salvo.getStatus()).isEqualTo("CRIADO");
        assertThat(produto.getEstoque()).isEqualTo(3);
        verify(emailService).enviarConfirmacao(salvo);
    }

    @Test
    void deveImpedirPedidoDeClienteInativo() {
        clienteAtivo.setAtivo(false);

        assertThatThrownBy(() -> service.criarPedido(pedidoSolicitado(1)))
                .isInstanceOf(ClienteInativoException.class)
                .hasMessage("Cliente inativo");
    }

    @Test
    void deveImpedirPedidoComEstoqueInsuficiente() {
        assertThatThrownBy(() -> service.criarPedido(pedidoSolicitado(10)))
                .isInstanceOf(EstoqueInsuficienteException.class);
    }
}
