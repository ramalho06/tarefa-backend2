package org.example.projbd2.exercicio2;

import java.math.BigDecimal;
import java.util.List;

public class Pedido {

    private Long id;
    private Cliente cliente;
    private List<ItemPedido> itens;
    private BigDecimal total;
    private String status;

    public Pedido() {
    }

    public Pedido(Cliente cliente, List<ItemPedido> itens) {
        this.cliente = cliente;
        this.itens = itens;
    }

    /** Estado inicial de todo pedido recém-criado: total definido e status CRIADO. */
    public void finalizarCriacao(BigDecimal total) {
        this.total = total;
        this.status = "CRIADO";
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

    public List<ItemPedido> getItens() {
        return itens;
    }

    public void setItens(List<ItemPedido> itens) {
        this.itens = itens;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public String getStatus() {
        return status;
    }
}
