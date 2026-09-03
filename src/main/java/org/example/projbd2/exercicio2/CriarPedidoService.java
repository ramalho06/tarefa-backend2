package org.example.projbd2.exercicio2;

import org.example.projbd2.common.exception.RecursoNaoEncontradoException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * Orquestrador do caso de uso "criar pedido".
 * <p>
 * Antes desta refatoração, esta classe concentrava: busca e validação de
 * cliente, busca de produto, validação e baixa de estoque, cálculo de
 * subtotal/total, persistência do pedido e envio de e-mail - tudo em um
 * único método longo (uma "God Class").
 * <p>
 * Agora ela apenas coordena colaboradores especializados, cada um com um
 * motivo próprio para mudar:
 * <ul>
 *   <li>{@link ValidadorClientePedido} - regra de elegibilidade do cliente;</li>
 *   <li>{@link EstoqueService} / {@link Produto} - regra e efeito de estoque;</li>
 *   <li>{@link CalculadoraPedido} - cálculo do total;</li>
 *   <li>{@link EmailService} - notificação;</li>
 *   <li>os repositórios - persistência.</li>
 * </ul>
 * O comportamento observável do endpoint não muda.
 */
@Service
public class CriarPedidoService {

    private final ClienteRepository clienteRepository;
    private final ProdutoRepository produtoRepository;
    private final PedidoRepository pedidoRepository;
    private final ValidadorClientePedido validadorClientePedido;
    private final EstoqueService estoqueService;
    private final CalculadoraPedido calculadoraPedido;
    private final EmailService emailService;

    public CriarPedidoService(ClienteRepository clienteRepository,
                               ProdutoRepository produtoRepository,
                               PedidoRepository pedidoRepository,
                               ValidadorClientePedido validadorClientePedido,
                               EstoqueService estoqueService,
                               CalculadoraPedido calculadoraPedido,
                               EmailService emailService) {
        this.clienteRepository = clienteRepository;
        this.produtoRepository = produtoRepository;
        this.pedidoRepository = pedidoRepository;
        this.validadorClientePedido = validadorClientePedido;
        this.estoqueService = estoqueService;
        this.calculadoraPedido = calculadoraPedido;
        this.emailService = emailService;
    }

    public Pedido criarPedido(Pedido pedidoSolicitado) {
        Cliente cliente = clienteRepository.findById(pedidoSolicitado.getCliente().getId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Cliente não encontrado"));
        validadorClientePedido.validarElegivel(cliente);

        List<ItemPedido> itens = resolverItens(pedidoSolicitado.getItens());
        estoqueService.debitarEstoque(itens);

        BigDecimal total = calculadoraPedido.calcularTotal(itens);

        Pedido pedido = new Pedido(cliente, itens);
        pedido.finalizarCriacao(total);

        Pedido salvo = pedidoRepository.save(pedido);
        emailService.enviarConfirmacao(salvo);

        return salvo;
    }

    private List<ItemPedido> resolverItens(List<ItemPedido> itensSolicitados) {
        return itensSolicitados.stream()
                .map(item -> {
                    Produto produto = produtoRepository.findById(item.getProduto().getId())
                            .orElseThrow(() -> new RecursoNaoEncontradoException("Produto não encontrado"));
                    return new ItemPedido(produto, item.getQuantidade());
                })
                .toList();
    }
}
