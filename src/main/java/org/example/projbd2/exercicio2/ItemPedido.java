package org.example.projbd2.exercicio2;

import java.math.BigDecimal;

/**
 * O item conhece o suficiente sobre si mesmo (produto + quantidade) para
 * calcular seu próprio subtotal. Isso evita que essa conta fique espalhada
 * dentro do laço de outra classe.
 */
public class ItemPedido {

    private Produto produto;
    private int quantidade;

    public ItemPedido() {
    }

    public ItemPedido(Produto produto, int quantidade) {
        this.produto = produto;
        this.quantidade = quantidade;
    }

    public BigDecimal calcularSubtotal() {
        return produto.getPreco().multiply(BigDecimal.valueOf(quantidade));
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
}
