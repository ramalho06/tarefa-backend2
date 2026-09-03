package org.example.projbd2.exercicio4;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.example.projbd2.common.exception.RecursoNaoEncontradoException;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CancelarPedidoServiceTest {

    private final PedidoRepository pedidoRepository = mock(PedidoRepository.class);
    private final ProdutoRepository produtoRepository = mock(ProdutoRepository.class);
    private final PagamentoService pagamentoService = mock(PagamentoService.class);
    private final NotificacaoService notificacaoService = mock(NotificacaoService.class);

    private final CancelarPedidoService service = new CancelarPedidoService(
            pedidoRepository,
            new EstoqueService(produtoRepository),
            pagamentoService,
            notificacaoService
    );

    private Produto produto;
    private Pedido pedido;

    @BeforeEach
    void setUp() {
        produto = new Produto(1L, "Teclado", 3);
        pedido = new Pedido(100L, StatusPedido.CRIADO, List.of(new ItemPedido(produto, 2)));

        when(pedidoRepository.findById(100L)).thenReturn(Optional.of(pedido));
        when(pedidoRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));
        when(produtoRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));
    }

    @Test
    void deveCancelarPedidoDevolverEstoqueEstornarEnotificar() {
        service.cancelar(100L);

        assertThat(pedido.getStatus()).isEqualTo(StatusPedido.CANCELADO);
        assertThat(produto.getEstoque()).isEqualTo(5);
        verify(pagamentoService).estornar(pedido);
        verify(notificacaoService).enviarCancelamento(pedido);
    }

    @Test
    void naoDeveCancelarPedidoJaEnviado() {
        pedido = new Pedido(100L, StatusPedido.ENVIADO, List.of());
        when(pedidoRepository.findById(100L)).thenReturn(Optional.of(pedido));

        assertThatThrownBy(() -> service.cancelar(100L))
                .isInstanceOf(CancelamentoNaoPermitidoException.class)
                .hasMessage("Pedido já enviado");
    }

    @Test
    void naoDeveCancelarPedidoJaEntregue() {
        pedido = new Pedido(100L, StatusPedido.ENTREGUE, List.of());
        when(pedidoRepository.findById(100L)).thenReturn(Optional.of(pedido));

        assertThatThrownBy(() -> service.cancelar(100L))
                .isInstanceOf(CancelamentoNaoPermitidoException.class)
                .hasMessage("Pedido já entregue");
    }

    @Test
    void deveFalharQuandoPedidoNaoExiste() {
        when(pedidoRepository.findById(999L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.cancelar(999L))
                .isInstanceOf(RecursoNaoEncontradoException.class);
    }
}
