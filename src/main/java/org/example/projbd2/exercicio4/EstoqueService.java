package org.example.projbd2.exercicio4;

import org.springframework.stereotype.Service;

import java.util.List;

/** Responsabilidade única: devolver ao estoque os itens de um pedido cancelado. */
@Service("ex4EstoqueService")
public class EstoqueService {

    private final ProdutoRepository produtoRepository;

    public EstoqueService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    public void devolverItens(List<ItemPedido> itens) {
        for (ItemPedido item : itens) {
            Produto produto = item.getProduto();
            produto.devolverEstoque(item.getQuantidade());
            produtoRepository.save(produto);
        }
    }
}
