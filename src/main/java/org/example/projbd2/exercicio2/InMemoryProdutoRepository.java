package org.example.projbd2.exercicio2;

import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository("ex2InMemoryProdutoRepository")
public class InMemoryProdutoRepository implements ProdutoRepository {

    private final Map<Long, Produto> produtos = new ConcurrentHashMap<>();

    @Override
    public Optional<Produto> findById(Long id) {
        return Optional.ofNullable(produtos.get(id));
    }

    @Override
    public Produto save(Produto produto) {
        produtos.put(produto.getId(), produto);
        return produto;
    }
}
