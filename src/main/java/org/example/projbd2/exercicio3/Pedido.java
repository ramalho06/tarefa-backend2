package org.example.projbd2.exercicio3;

import java.math.BigDecimal;

public class Pedido {

    private Long id;
    private Cliente cliente;
    private BigDecimal total;
    private BigDecimal frete;

    public Pedido() {
    }

    public Pedido(Cliente cliente, BigDecimal total, BigDecimal frete) {
        this.cliente = cliente;
        this.total = total;
        this.frete = frete;
    }

    public void zerarFrete() {
        this.frete = BigDecimal.ZERO;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public BigDecimal getFrete() {
        return frete;
    }

    public void setFrete(BigDecimal frete) {
        this.frete = frete;
    }
}
