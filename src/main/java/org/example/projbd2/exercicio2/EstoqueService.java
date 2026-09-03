package org.example.projbd2.exercicio2;

import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Responsabilidade única: aplicar a baixa de estoque dos itens de um pedido
 * e persistir o novo saldo. A validação "tem estoque suficiente?" fica no
 * próprio {@link Produto} (quem detém o dado, decide a regra sobre o dado).
 */
@Service("ex2EstoqueService")
public class EstoqueService {

    private final ProdutoRepository produtoRepository;

    public EstoqueService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    public void debitarEstoque(List<ItemPedido> itens) {
        for (ItemPedido item : itens) {
            Produto produto = item.getProduto();
            produto.baixarEstoque(item.getQuantidade());
            produtoRepository.save(produto);
        }
    }
}
