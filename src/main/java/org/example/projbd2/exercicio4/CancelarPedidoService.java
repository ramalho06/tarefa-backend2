package org.example.projbd2.exercicio4;

import org.example.projbd2.common.exception.RecursoNaoEncontradoException;
import org.springframework.stereotype.Service;

/**
 * Orquestrador do caso de uso "cancelar pedido".
 * <p>
 * Antes, o Controller decidia se o cancelamento era permitido, persistia o
 * novo status, devolvia estoque, estornava pagamento e notificava - tudo
 * misturado com o parsing/resposta HTTP.
 * <p>
 * Agora cada efeito colateral tem seu próprio colaborador (Estoque,
 * Pagamento, Notificação) e a regra de estado vive no próprio {@link Pedido}.
 * Este serviço apenas orquestra a sequência, sem conter a lógica.
 */
@Service
public class CancelarPedidoService {

    private final PedidoRepository pedidoRepository;
    private final EstoqueService estoqueService;
    private final PagamentoService pagamentoService;
    private final NotificacaoService notificacaoService;

    public CancelarPedidoService(PedidoRepository pedidoRepository,
                                  EstoqueService estoqueService,
                                  PagamentoService pagamentoService,
                                  NotificacaoService notificacaoService) {
        this.pedidoRepository = pedidoRepository;
        this.estoqueService = estoqueService;
        this.pagamentoService = pagamentoService;
        this.notificacaoService = notificacaoService;
    }

    public void cancelar(Long pedidoId) {
        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Pedido não encontrado"));

        pedido.cancelar();
        pedidoRepository.save(pedido);

        estoqueService.devolverItens(pedido.getItens());
        pagamentoService.estornar(pedido);
        notificacaoService.enviarCancelamento(pedido);
    }
}
