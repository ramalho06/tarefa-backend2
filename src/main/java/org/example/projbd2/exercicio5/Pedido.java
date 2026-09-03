package org.example.projbd2.exercicio5;

import java.math.BigDecimal;

public class Pedido {

    private Long id;
    private Long usuarioId;
    private BigDecimal total;
    private String status;

    public Pedido() {
    }

    public Pedido(Long usuarioId, BigDecimal total) {
        this.usuarioId = usuarioId;
        this.total = total;
        this.status = "CRIADO";
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public String getStatus() {
        return status;
    }
}
