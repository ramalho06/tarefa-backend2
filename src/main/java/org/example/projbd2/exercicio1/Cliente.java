package org.example.projbd2.exercicio1;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

/**
 * Entidade Cliente.
 * <p>
 * No código original, o Controller decidia o estado inicial do cliente
 * (ativo = true, dataCadastro = agora). Essa é uma regra do próprio domínio
 * "Cliente" - todo cliente novo nasce ativo e com data de cadastro definida -
 * então ela passa a viver aqui, como um invariante da entidade, e não pode
 * mais ser esquecida ou duplicada por quem a cria.
 */
public class Cliente {

    private Long id;

    @NotBlank(message = "Nome obrigatório")
    private String nome;

    @NotBlank(message = "Email obrigatório")
    @Email(message = "Email inválido")
    private String email;

    private boolean ativo;
    private LocalDateTime dataCadastro;

    /** Define o estado inicial de um cliente recém-recebido pela API. */
    public void definirEstadoInicial() {
        this.ativo = true;
        this.dataCadastro = LocalDateTime.now();
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public LocalDateTime getDataCadastro() {
        return dataCadastro;
    }
}
