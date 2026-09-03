package org.example.projbd2.exercicio5;

import java.util.Optional;

public interface CupomRepository {
    Optional<Cupom> findByCodigo(String codigo);
}
