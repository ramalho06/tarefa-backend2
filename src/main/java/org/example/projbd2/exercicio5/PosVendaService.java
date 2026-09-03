package org.example.projbd2.exercicio5;

import org.springframework.stereotype.Service;

/**
 * Agrupa as três ações de pós-venda (nota fiscal, e-mail, auditoria), que
 * sempre acontecem juntas e nessa ordem após a criação do pedido. É, ela
 * mesma, um pequeno orquestrador - a "orquestração não significa fazer
 * tudo" também se aplica aqui: cada ação continua delegada a um
 * colaborador especializado.
 */
@Service
public class PosVendaService {

    private final NotaFiscalService notaFiscalService;
    private final EmailService emailService;
    private final AuditoriaService auditoriaService;

    public PosVendaService(NotaFiscalService notaFiscalService,
                            EmailService emailService,
                            AuditoriaService auditoriaService) {
        this.notaFiscalService = notaFiscalService;
        this.emailService = emailService;
        this.auditoriaService = auditoriaService;
    }

    public void processar(Pedido pedido) {
        notaFiscalService.gerar(pedido);
        emailService.enviar(pedido);
        auditoriaService.registrar("PEDIDO_CRIADO", pedido.getUsuarioId());
    }
}
