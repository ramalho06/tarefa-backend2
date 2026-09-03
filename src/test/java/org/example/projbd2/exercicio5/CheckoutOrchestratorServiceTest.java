package org.example.projbd2.exercicio5;

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

class CheckoutOrchestratorServiceTest {

    private final UsuarioRepository usuarioRepository = mock(UsuarioRepository.class);
    private final CupomRepository cupomRepository = mock(CupomRepository.class);
    private final PedidoRepository pedidoRepository = mock(PedidoRepository.class);
    private final PagamentoService pagamentoService = mock(PagamentoService.class);
    private final FreteService freteService = mock(FreteService.class);
    private final NotaFiscalService notaFiscalService = mock(NotaFiscalService.class);
    private final EmailService emailService = mock(EmailService.class);
    private final AuditoriaService auditoriaService = mock(AuditoriaService.class);

    private final CheckoutOrchestratorService service = new CheckoutOrchestratorService(
            new UsuarioValidator(usuarioRepository),
            new CupomService(cupomRepository),
            new CalculadoraCheckoutService(),
            freteService,
            pagamentoService,
            new PedidoService(pedidoRepository),
            new PosVendaService(notaFiscalService, emailService, auditoriaService)
    );

    private Usuario usuarioAtivo;

    @BeforeEach
    void setUp() {
        usuarioAtivo = new Usuario(1L, true);
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuarioAtivo));
        when(pedidoRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));
        when(freteService.calcular(any(), any())).thenReturn(new BigDecimal("20.00"));
    }

    private CheckoutRequest request(String cupom) {
        return new CheckoutRequest(1L, cupom, new Endereco("00000-000"),
                List.of(new ItemCheckout(new BigDecimal("100.00"), 1)));
    }

    @Test
    void deveFinalizarCheckoutSemCupomProcessandoPagamentoEPosVenda() {
        Pedido pedido = service.finalizar(request(null));

        // 100.00 (itens) + 20.00 (frete) = 120.00
        assertThat(pedido.getTotal()).isEqualByComparingTo("120.00");
        assertThat(pedido.getStatus()).isEqualTo("CRIADO");
        verify(pagamentoService).processar(usuarioAtivo, new BigDecimal("120.00"));
        verify(notaFiscalService).gerar(pedido);
        verify(emailService).enviar(pedido);
        verify(auditoriaService).registrar("PEDIDO_CRIADO", 1L);
    }

    @Test
    void deveAplicarDescontoDoCupomAntesDoFrete() {
        Cupom cupom = new Cupom("DESC10", true, new BigDecimal("0.10"));
        when(cupomRepository.findByCodigo("DESC10")).thenReturn(Optional.of(cupom));

        Pedido pedido = service.finalizar(request("DESC10"));

        // 100.00 - 10% = 90.00; + 20.00 (frete) = 110.00
        assertThat(pedido.getTotal()).isEqualByComparingTo("110.00");
    }

    @Test
    void deveImpedirCheckoutDeUsuarioInativo() {
        usuarioAtivo = new Usuario(1L, false);
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuarioAtivo));

        assertThatThrownBy(() -> service.finalizar(request(null)))
                .isInstanceOf(UsuarioInativoException.class);
    }

    @Test
    void deveImpedirCheckoutComCupomInvalido() {
        Cupom cupomInvalido = new Cupom("EXPIRADO", false, BigDecimal.ZERO);
        when(cupomRepository.findByCodigo("EXPIRADO")).thenReturn(Optional.of(cupomInvalido));

        assertThatThrownBy(() -> service.finalizar(request("EXPIRADO")))
                .isInstanceOf(CupomInvalidoException.class);
    }
}
