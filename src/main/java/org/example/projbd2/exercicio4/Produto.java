package org.example.projbd2.exercicio4;

public class Produto {

    private Long id;
    private String nome;
    private int estoque;

    public Produto() {
    }

    public Produto(Long id, String nome, int estoque) {
        this.id = id;
        this.nome = nome;
        this.estoque = estoque;
    }

    /** Efeito colateral do cancelamento: devolve a quantidade cancelada ao estoque. */
    public void devolverEstoque(int quantidade) {
        this.estoque += quantidade;
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

    public int getEstoque() {
        return estoque;
    }

    public void setEstoque(int estoque) {
        this.estoque = estoque;
    }
}
