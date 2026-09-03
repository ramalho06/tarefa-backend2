package org.example.projbd2.exercicio4;

import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository("ex4InMemoryProdutoRepository")
public class InMemoryProdutoRepository implements ProdutoRepository {

    private final Map<Long, Produto> produtos = new ConcurrentHashMap<>();

    @Override
    public Produto save(Produto produto) {
        produtos.put(produto.getId(), produto);
        return produto;
    }
}
