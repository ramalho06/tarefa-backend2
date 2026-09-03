package org.example.projbd2.exercicio2;

import java.util.Optional;

public interface ProdutoRepository {
    Optional<Produto> findById(Long id);

    Produto save(Produto produto);
}
