package org.example.projbd2.exercicio2;

import java.math.BigDecimal;

/**
 * O produto agora protege seu próprio invariante de estoque: ninguém de fora
 * consegue colocar o estoque em um valor negativo, porque a única forma de
 * reduzi-lo é chamando {@link #baixarEstoque(int)}, que valida antes de agir.
 * Antes, essa validação e a subtração viviam soltas dentro do laço do
 * PedidoService.
 */
public class Produto {

    private Long id;
    private String nome;
    private BigDecimal preco;
    private int estoque;

    public Produto() {
    }

    public Produto(Long id) {
        this.id = id;
    }

    public Produto(Long id, String nome, BigDecimal preco, int estoque) {
        this.id = id;
        this.nome = nome;
        this.preco = preco;
        this.estoque = estoque;
    }

    public void baixarEstoque(int quantidade) {
        if (quantidade > estoque) {
            throw new EstoqueInsuficienteException(nome);
        }
        this.estoque -= quantidade;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public int getEstoque() {
        return estoque;
    }

    public void setEstoque(int estoque) {
        this.estoque = estoque;
    }
}
