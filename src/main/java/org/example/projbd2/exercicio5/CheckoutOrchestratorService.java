package org.example.projbd2.exercicio5;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * Orquestrador do caso de uso "finalizar checkout".
 * <p>
 * Antes desta refatoração, {@code CheckoutService.finalizar} concentrava
 * validação de usuário, resolução e validação de cupom, cálculo de total,
 * cálculo de frete, processamento de pagamento, criação de pedido, geração
 * de nota fiscal, envio de e-mail e registro de auditoria - nove
 * responsabilidades diferentes em um único método.
 * <p>
 * Cada uma delas passa a ter um colaborador dedicado, injetado por
 * interface (Dependency Inversion - este orquestrador não conhece
 * nenhum detalhe de como o pagamento é processado, como o frete é
 * calculado ou como a nota fiscal é emitida):
 * <ul>
 *   <li>{@link UsuarioValidator} - elegibilidade do usuário;</li>
 *   <li>{@link CupomService} - resolução, validação e desconto do cupom;</li>
 *   <li>{@link CalculadoraCheckoutService} - soma dos itens;</li>
 *   <li>{@link FreteService} - cálculo do frete;</li>
 *   <li>{@link PagamentoService} - processamento do pagamento;</li>
 *   <li>{@link PedidoService} - criação/persistência do pedido;</li>
 *   <li>{@link PosVendaService} - nota fiscal, e-mail e auditoria.</li>
 * </ul>
 * O papel desta classe é apenas orquestrar a sequência acima; ela não
 * contém nenhuma regra de negócio própria, o que a torna estável mesmo
 * quando as regras de cada etapa mudam.
 */
@Service
public class CheckoutOrchestratorService {

    private final UsuarioValidator usuarioValidator;
    private final CupomService cupomService;
    private final CalculadoraCheckoutService calculadoraCheckoutService;
    private final FreteService freteService;
    private final PagamentoService pagamentoService;
    private final PedidoService pedidoService;
    private final PosVendaService posVendaService;

    public CheckoutOrchestratorService(UsuarioValidator usuarioValidator,
                                        CupomService cupomService,
                                        CalculadoraCheckoutService calculadoraCheckoutService,
                                        FreteService freteService,
                                        PagamentoService pagamentoService,
                                        PedidoService pedidoService,
                                        PosVendaService posVendaService) {
        this.usuarioValidator = usuarioValidator;
        this.cupomService = cupomService;
        this.calculadoraCheckoutService = calculadoraCheckoutService;
        this.freteService = freteService;
        this.pagamentoService = pagamentoService;
        this.pedidoService = pedidoService;
        this.posVendaService = posVendaService;
    }

    public Pedido finalizar(CheckoutRequest request) {
        Usuario usuario = usuarioValidator.validarEBuscar(request.getUsuarioId());

        Cupom cupom = cupomService.resolver(request.getCupom());

        BigDecimal total = calculadoraCheckoutService.calcularTotal(request);
        total = cupomService.aplicarDesconto(total, cupom);

        BigDecimal frete = freteService.calcular(request.getEndereco(), total);
        total = total.add(frete);

        pagamentoService.processar(usuario, total);

        Pedido pedido = pedidoService.criar(usuario, request, total);

        posVendaService.processar(pedido);

        return pedido;
    }
}
