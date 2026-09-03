package org.example.projbd2.exercicio3;

public class Cliente {

    private Long id;
    private TipoCliente tipo;

    public Cliente() {
    }

    public Cliente(TipoCliente tipo) {
        this.tipo = tipo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TipoCliente getTipo() {
        return tipo;
    }

    public void setTipo(TipoCliente tipo) {
        this.tipo = tipo;
    }
}
