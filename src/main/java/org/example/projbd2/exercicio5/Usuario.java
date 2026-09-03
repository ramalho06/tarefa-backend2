package org.example.projbd2.exercicio5;

public class Usuario {

    private Long id;
    private boolean ativo;

    public Usuario() {
    }

    public Usuario(Long id, boolean ativo) {
        this.id = id;
        this.ativo = ativo;
    }

    public Long getId() {
        return id;
    }

    public boolean isAtivo() {
        return ativo;
    }
}
