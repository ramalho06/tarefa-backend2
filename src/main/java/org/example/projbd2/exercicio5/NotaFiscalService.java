package org.example.projbd2.exercicio5;

/** Abstração (DIP) para a emissão de nota fiscal. */
public interface NotaFiscalService {
    void gerar(Pedido pedido);
}
