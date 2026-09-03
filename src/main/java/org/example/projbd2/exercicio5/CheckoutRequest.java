package org.example.projbd2.exercicio5;

import java.util.List;

public class CheckoutRequest {

    private Long usuarioId;
    private String cupom;
    private Endereco endereco;
    private List<ItemCheckout> itens;

    public CheckoutRequest() {
    }

    public CheckoutRequest(Long usuarioId, String cupom, Endereco endereco, List<ItemCheckout> itens) {
        this.usuarioId = usuarioId;
        this.cupom = cupom;
        this.endereco = endereco;
        this.itens = itens;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public String getCupom() {
        return cupom;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public List<ItemCheckout> getItens() {
        return itens;
    }
}
