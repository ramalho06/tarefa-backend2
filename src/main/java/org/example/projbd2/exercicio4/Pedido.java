package org.example.projbd2.exercicio4;

import java.util.List;

/**
 * O pedido passa a proteger sua própria máquina de estados: só ele decide
 * quando pode transicionar para CANCELADO. Antes, essa regra vivia como um
 * par de {@code if}s soltos no Controller, junto com todo o resto do fluxo.
 * As mensagens de erro são preservadas exatamente como no código original.
 */
public class Pedido {

    private Long id;
    private StatusPedido status;
    private List<ItemPedido> itens;

    public Pedido() {
    }

    public Pedido(Long id, StatusPedido status, List<ItemPedido> itens) {
        this.id = id;
        this.status = status;
        this.itens = itens;
    }

    public void cancelar() {
        if (status == StatusPedido.ENVIADO) {
            throw new CancelamentoNaoPermitidoException("Pedido já enviado");
        }
        if (status == StatusPedido.ENTREGUE) {
            throw new CancelamentoNaoPermitidoException("Pedido já entregue");
        }
        this.status = StatusPedido.CANCELADO;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public StatusPedido getStatus() {
        return status;
    }

    public List<ItemPedido> getItens() {
        return itens;
    }
}
